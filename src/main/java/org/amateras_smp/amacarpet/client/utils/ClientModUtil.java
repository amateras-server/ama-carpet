// Copyright (c) 2025 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.client.utils;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBoolean;
import net.fabricmc.loader.api.FabricLoader;
import org.amateras_smp.amacarpet.AmaCarpet;

import java.lang.reflect.Field;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ClientModUtil {

    // used for almost all of malilib config boolean
    public static final ImmutableList<Restriction> genericRestrictions = ImmutableList.of(
            new Restriction(AmaCarpet.ModIds.amatweaks, "org.amateras_smp.amatweaks.config.Configs$Generic", ImmutableList.of(
                    "auto_eat_put_back_food",
                    "auto_glide_put_back_rocket"
            )),
            new Restriction(AmaCarpet.ModIds.litematica, "fi.dy.masa.litematica.config.Configs$Generic", ImmutableList.of(
                    "easy_place_mode",
                    "placement_restriction"
            )),
            new Restriction(AmaCarpet.ModIds.masaadditions, "com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended$Disable", "DISABLE_", ImmutableList.of(
                    "honey_block_slowdown",
                    "honey_block_jumping",
                    "slime_block_bouncing"
            )),
            new Restriction(AmaCarpet.ModIds.tweakermore, "me.fallenbreath.tweakermore.config.TweakerMoreConfigs", ImmutableList.of(
                    "auto_pick_schematic_block",
                    "disable_darkness_effect",
                    "disable_honey_block_effect",
                    "disable_slime_block_bouncing",
                    "fake_night_vision",
                    "schematic_block_placement_restriction",
                    "schematic_pro_place"
            )),
            new Restriction(AmaCarpet.ModIds.tweakeroo, "fi.dy.masa.tweakeroo.config.Configs$Disable", "DISABLE_", ImmutableList.of(
                    "slime_block_slowdown"
            ))
    );

    public static final Restriction amatweaksFeatureToggleRestriction = new Restriction(AmaCarpet.ModIds.amatweaks, "org.amateras_smp.amatweaks.config.FeatureToggle", "TWEAK_", ImmutableList.of(
            "auto_eat",
            "auto_firework_glide",
            "interaction_history",
            "prevent_breaking_adjacent_portal",
            "prevent_placement_on_portal_sides",
            "safe_step_protection",
            "selective_block_rendering",
            "selective_entity_rendering"
    ));

    public static final Restriction tweakerooFeatureToggleRestriction = new Restriction(AmaCarpet.ModIds.tweakeroo, "fi.dy.masa.tweakeroo.config.FeatureToggle", "TWEAK_", ImmutableList.of(
            // contains tweakfork
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
    ));


    private static boolean checkMalilibConfigBoolean(String className, String feature) {
        try {
            Class<?> clazz = Class.forName(className);
            Field field = clazz.getDeclaredField(feature);
            field.setAccessible(true);
            Object value = field.get(null);

            if (value instanceof IConfigBoolean configBoolean) {
                return configBoolean.getBooleanValue();
            }
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            AmaCarpet.LOGGER.error("can't get tweakeroo feature state for {}", feature);
        }
        return false;
    }

    private static void addFeatures(Map<String, Boolean> map, String modId, String className, String featurePrefix, List<String> features) {
        boolean isModLoaded = FabricLoader.getInstance().isModLoaded(modId);
        for (String feature : features) {
            boolean value = false;
            if (isModLoaded) {
                if (featurePrefix == null) featurePrefix = "";
                String featureFormatted = featurePrefix + feature.toUpperCase();
                value = checkMalilibConfigBoolean(className, featureFormatted);
            }
            map.put(feature, value);
        }
    }

    public static HashMap<String, Boolean> createClientConfigsDataMap() {
        HashMap<String, Boolean> map = new HashMap<>();
        for (Restriction restriction : genericRestrictions) {
            addFeatures(map, restriction.modId, restriction.className, restriction.featurePrefix, restriction.watchList);
        }
        addFeatures(map, amatweaksFeatureToggleRestriction.modId, amatweaksFeatureToggleRestriction.className, amatweaksFeatureToggleRestriction.featurePrefix, amatweaksFeatureToggleRestriction.watchList);
        addFeatures(map, tweakerooFeatureToggleRestriction.modId, tweakerooFeatureToggleRestriction.className, tweakerooFeatureToggleRestriction.featurePrefix, tweakerooFeatureToggleRestriction.watchList);
        return map;
    }

    public record Restriction(String modId, String className, String featurePrefix, ImmutableList<String> watchList) {
        Restriction(String modId, String className, ImmutableList<String> watchList) {
            this(modId, className, "", watchList);
        }
    }
}
