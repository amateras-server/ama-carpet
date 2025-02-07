// Copyright (c) 2025 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.client.mixins.addon.amatweaks;

import org.amateras_smp.amatweaks.config.FeatureToggle;
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

@Restriction(require = @Condition(AmaCarpet.ModIds.amatweaks))
@Mixin(value = FeatureToggle.class, remap = false)
public class FeatureToggleMixin {
    @Inject(method = "setBooleanValue(Z)V", at = @At("HEAD"))
    private void onSetBooleanValue(boolean value, CallbackInfo ci) {
        // FeatureToggle of amatweaks overrides setBooleanValue() so this is needed.
        AmaCarpet.LOGGER.debug("setBooleanValue to {} in FeatureToggle", value ? "true" : "false");
        if (!value) return;
        FeatureToggle self = (FeatureToggle)(Object) this;
        String changed = self.getName();
        String sneak_changed = StringUtil.camelToSneak(changed);
        for (String feature : ClientModUtil.amatweaksFeatureToggleRestriction.watchList()){
            if (sneak_changed.equals(ClientModUtil.amatweaksFeatureToggleRestriction.featurePrefix() + feature)) {
                PacketHandler.sendC2S(new EnableSpecifiedFeaturePacket(feature));
            }
        }
    }
}
