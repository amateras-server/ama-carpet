// Copyright (c) 2025 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.client.mixins.addon.malilib;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.client.utils.ClientModUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Restriction(require = @Condition(AmaCarpet.ModIds.malilib))
@Mixin(value = ConfigBoolean.class, remap = false)
public class ConfigBooleanMixin {
    @Inject(method = "setBooleanValue", at = @At("HEAD"))
    private void onSetBooleanValue(boolean value, CallbackInfo ci) {
        AmaCarpet.LOGGER.debug("on set boolean value");
        if (value) {
            ConfigBoolean self = (ConfigBoolean) (Object) this;
            ClientModUtil.check(self.getName());
        }
    }
}
