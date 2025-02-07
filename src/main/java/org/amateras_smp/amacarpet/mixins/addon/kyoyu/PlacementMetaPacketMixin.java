// Copyright (c) 2025 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.mixins.addon.kyoyu;

import carpet.utils.Translations;
import com.llamalad7.mixinextras.sugar.Local;
import com.vulpeus.kyoyu.CompatibleUtils;
import com.vulpeus.kyoyu.net.packets.PlacementMetaPacket;
import com.vulpeus.kyoyu.placement.KyoyuPlacement;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Restriction(require = @Condition(AmaCarpet.ModIds.kyoyu))
@Mixin(value = PlacementMetaPacket.class, remap = false)
public class PlacementMetaPacketMixin {
    @Shadow @Final
    private KyoyuPlacement kyoyuPlacement;

    @Inject(method = "onServer", at = @At("TAIL"))
    private void onAddKyoyuPlacement(CompatibleUtils.KyoyuPlayer player, CallbackInfo ci, @Local KyoyuPlacement preKyoyuPlacement) {
        if (!AmaCarpetSettings.notifyKyoyu || AmaCarpet.kIsClient) return;
        if (preKyoyuPlacement == null) { // means new placement, not modification
            Component message = Component.literal(player.getName()).withStyle(ChatFormatting.GREEN).append(
                    Component.literal(Translations.tr("ama.message.schematic.shared")).withStyle(ChatFormatting.WHITE)).append(
                    Component.literal(kyoyuPlacement.getName()).withStyle(ChatFormatting.YELLOW));
            AmaCarpetServer.MINECRAFT_SERVER.getPlayerList().broadcastSystemMessage(message, false);
        }
    }
}
