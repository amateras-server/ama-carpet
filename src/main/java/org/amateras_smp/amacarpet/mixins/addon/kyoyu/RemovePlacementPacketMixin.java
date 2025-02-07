// Copyright (c) 2025 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.mixins.addon.kyoyu;

import carpet.utils.Translations;
import com.llamalad7.mixinextras.sugar.Local;
import com.vulpeus.kyoyu.CompatibleUtils;
import com.vulpeus.kyoyu.net.packets.RemovePlacementPacket;
import com.vulpeus.kyoyu.placement.KyoyuPlacement;
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

@Restriction(require = @Condition(AmaCarpet.ModIds.kyoyu))
@Mixin(value = RemovePlacementPacket.class, remap = false)
public class RemovePlacementPacketMixin {
    @Inject(method = "onServer", at = @At("TAIL"))
    private void onRemoveKyoyuPlacement(CompatibleUtils.KyoyuPlayer serverPlayer, CallbackInfo ci, @Local KyoyuPlacement kyoyuPlacement) {
        if (!AmaCarpetSettings.notifyKyoyu || kyoyuPlacement == null || AmaCarpet.kIsClient) return;
        Component message = Component.literal(serverPlayer.getName()).withStyle(ChatFormatting.RED).append(
                Component.literal(Translations.tr("ama.message.schematic.unshared")).withStyle(ChatFormatting.WHITE)).append(
                Component.literal(kyoyuPlacement.getName()).withStyle(ChatFormatting.YELLOW));
        AmaCarpetServer.MINECRAFT_SERVER.getPlayerList().broadcastSystemMessage(message, false);
    }
}
