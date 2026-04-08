// Copyright (c) 2026 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.mixins.addon.carpet;

import carpet.logging.HUDController;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.amateras_smp.amacarpet.logging.AmaCarpetLoggerRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(HUDController.class)
public class HUDControllerMixin {
    @Inject(method = "update_hud", at = @At(value = "INVOKE", target = "Ljava/util/Map;keySet()Ljava/util/Set;"))
    private static void updateAmaCarpetHUDLoggers(MinecraftServer server, List<ServerPlayer> force, CallbackInfo ci) {
        AmaCarpetLoggerRegistry.updateHUD(server);
    }
}
