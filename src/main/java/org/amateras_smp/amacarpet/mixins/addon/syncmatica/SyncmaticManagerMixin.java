// Copyright (c) 2025 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.mixins.addon.syncmatica;

import carpet.utils.Translations;
import ch.endte.syncmatica.ServerPlacement;
import ch.endte.syncmatica.SyncmaticManager;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Restriction(require = @Condition(AmaCarpet.ModIds.syncmatica))
@Mixin(value = SyncmaticManager.class, remap = false)
public class SyncmaticManagerMixin {
    @Inject(method = "addPlacement", at = @At("HEAD"))
    private void onAddPlacement(ServerPlacement placement, CallbackInfo ci) {
        if (!AmaCarpetSettings.notifySyncmatica || AmaCarpet.kIsClient) return;
        Component message = Component.literal(placement.getOwner().getName()).withStyle(ChatFormatting.GREEN).append(
                Component.literal(Translations.tr("ama.message.schematic.shared")).withStyle(ChatFormatting.WHITE)).append(
                Component.literal(placement.getName()).withStyle(ChatFormatting.YELLOW)).append(
                Component.literal("\nDimension : " + placement.getDimension()).withStyle(ChatFormatting.WHITE));
        AmaCarpetServer.LOGGER.info(message.getString());
        AmaCarpetServer.MINECRAFT_SERVER.getPlayerList().broadcastSystemMessage(message, false);
    }
}
