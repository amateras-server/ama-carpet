// Copyright (c) 2025 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.client.utils;

import com.google.common.collect.ImmutableSet;
import fi.dy.masa.malilib.config.IConfigBoolean;
import net.fabricmc.loader.api.FabricLoader;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.network.PacketHandler;
import org.amateras_smp.amacarpet.network.packets.EnableCertainFeaturePacket;
import org.amateras_smp.amacarpet.utils.StringUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ClientModUtil {
    public record Restriction(String modId, String className,
                              String featurePrefix,
                              ImmutableSet<String> watchList) {
        public Restriction(String modId, String className, ImmutableSet<String> watchList) {
            this(modId, className, "", watchList);
        }

        public void collectStates(Map<String, Boolean> targetMap) {
            boolean isLoaded = FabricLoader.getInstance().isModLoaded(modId);
            for (String feature : watchList) {
                boolean value = false;
                if (isLoaded) {
                    String fieldName = featurePrefix.toUpperCase() + feature.toUpperCase();
                    value = fetchConfigValue(className, fieldName);
                }
                targetMap.put(feature, value);
            }
        }
    }

    private static final ImmutableSet<Restriction> ALL_RESTRICTIONS;

    private static final ImmutableSet<String> FEATURE_SET;

    static {
        ImmutableSet.Builder<Restriction> restrictions = ImmutableSet.builder();

        restrictions.add(new Restriction(AmaCarpet.ModIds.amatweaks, "org.amateras_smp.amatweaks.config.Configs$Generic",
            ImmutableSet.of(
                "auto_eat_put_back_food",
                "auto_glide_put_back_rocket"
            )
        ));

        restrictions.add(new Restriction(AmaCarpet.ModIds.litematica, "fi.dy.masa.litematica.config.Configs$Generic",
            ImmutableSet.of(
                "easy_place_mode",
                "placement_restriction"
            )
        ));

        restrictions.add(new Restriction(AmaCarpet.ModIds.tweakermore, "me.fallenbreath.tweakermore.config.TweakerMoreConfigs",
            ImmutableSet.of(
                "auto_pick_schematic_block",
                "disable_darkness_effect",
                "disable_honey_block_effect",
                "disable_slime_block_bouncing",
                "fake_night_vision",
                "schematic_block_placement_restriction",
                "schematic_pro_place"
            )
        ));

        restrictions.add(new Restriction(AmaCarpet.ModIds.tweakeroo, "fi.dy.masa.tweakeroo.config.Configs$Disable", "disable_",
            ImmutableSet.of("slime_block_slowdown")));

        restrictions.add(new Restriction(AmaCarpet.ModIds.amatweaks, "org.amateras_smp.amatweaks.config.FeatureToggle", "tweak_",
            ImmutableSet.of(
                "auto_eat",
                "auto_firework_glide",
                "auto_restock_inventory",
                "hold_back",
                "hold_forward",
                "hold_left",
                "hold_right",
                "interaction_history",
                "prevent_breaking_adjacent_portal",
                "prevent_placement_on_portal_sides",
                "safe_step_protection",
                "selective_block_rendering",
                "selective_entity_rendering"
            )
        ));

        restrictions.add(new Restriction(AmaCarpet.ModIds.tweakeroo, "fi.dy.masa.tweakeroo.config.FeatureToggle", "tweak_",
            ImmutableSet.of(
                "accurate_block_placement",
                "block_reach_override",
                "container_scan",
                "fake_sneaking",
                "fast_block_placement",
                "flexible_block_placement",
                "free_camera",
                "gamma_override",
                "no_sneak_slowdown",
                "scaffold_place"
            )
        ));

        ALL_RESTRICTIONS = restrictions.build();

        ImmutableSet.Builder<String> lookupBuilder = ImmutableSet.builder();
        for (Restriction r : ALL_RESTRICTIONS) {
            for (String feature : r.watchList) {
                lookupBuilder.add(r.featurePrefix + feature);
            }
        }
        FEATURE_SET = lookupBuilder.build();
    }

    public static ImmutableSet<Restriction> allRestrictions() {
        return ALL_RESTRICTIONS;
    }

    public static HashMap<String, Boolean> createClientConfigsDataMap() {
        HashMap<String, Boolean> map = new HashMap<>();
        for (Restriction restriction : ALL_RESTRICTIONS) {
            restriction.collectStates(map);
        }
        return map;
    }

    public static void check(String changed) {
        String sneak_case_changed = StringUtil.camelToSneak(changed);
        AmaCarpet.LOGGER.debug("checking for {}", sneak_case_changed);

        if (FEATURE_SET.contains(sneak_case_changed)) {
            AmaCarpet.LOGGER.debug("sending enable certain feature packet for {}", sneak_case_changed);
            PacketHandler.sendC2S(new EnableCertainFeaturePacket(sneak_case_changed));
        }
    }

    private static final Map<String, Field> FIELD_CACHE = new HashMap<>();

    private static boolean fetchConfigValue(String className, String fieldName) {
        try {
            String cacheKey = className + "#" + fieldName;
            Field field = FIELD_CACHE.get(cacheKey);

            if (field == null) {
                Class<?> clazz = Class.forName(className);
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                FIELD_CACHE.put(cacheKey, field);
            }

            Object value = field.get(null);
            if (value instanceof IConfigBoolean config) {
                return config.getBooleanValue();
            }
        } catch (Exception e) {
            AmaCarpet.LOGGER.error("Failed to get config state: {} in {}", fieldName, className);
        }
        return false;
    }
}
