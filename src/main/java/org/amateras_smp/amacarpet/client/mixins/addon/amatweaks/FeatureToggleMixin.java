// Copyright (c) 2025 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.client.mixins.addon.amatweaks;

import org.amateras_smp.amatweaks.config.FeatureToggle;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.client.utils.ClientModUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Restriction(require = @Condition(AmaCarpet.ModIds.amatweaks))
@Mixin(value = FeatureToggle.class, remap = false)
public class FeatureToggleMixin {
    @Shadow
    private boolean valueBoolean;

    @Inject(method = "onValueChanged", at = @At("HEAD"))
    private void onToggleFeature(CallbackInfo ci) {
        AmaCarpet.LOGGER.debug("on value changed");
        if (!this.valueBoolean) return;
        FeatureToggle self = (FeatureToggle) (Object) this;
        ClientModUtil.check(self.getName());
    }
}
