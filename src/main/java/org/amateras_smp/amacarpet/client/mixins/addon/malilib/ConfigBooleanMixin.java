// Copyright (c) 2025 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.client.mixins.addon.malilib;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.client.utils.ClientModUtil;
import org.amateras_smp.amacarpet.network.PacketHandler;
import org.amateras_smp.amacarpet.network.packets.EnableSpecifiedFeaturePacket;
import org.amateras_smp.amacarpet.utils.StringUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Restriction(require = @Condition(AmaCarpet.ModIds.malilib))
@Mixin(value = ConfigBoolean.class, remap = false)
public class ConfigBooleanMixin {
    @Inject(method = "setBooleanValue", at = @At("HEAD"))
    private void onEnableFeature(boolean value, CallbackInfo ci) {
        AmaCarpet.LOGGER.debug("setBooleanValue to {}", value ? "true" : "false");
        if (!value) return;
        ConfigBoolean self = (ConfigBoolean)(Object) this;
        String changed = self.getName();
        String sneak_changed = StringUtil.camelToSneak(changed);

        for (ClientModUtil.Restriction restriction : ClientModUtil.genericRestrictions) {
            for (String feature : restriction.watchList()) {
                if (sneak_changed.equals(restriction.featurePrefix() + feature)) {
                    PacketHandler.sendC2S(new EnableSpecifiedFeaturePacket(feature));
                }
            }
        }
    }
}
