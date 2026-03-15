// Copyright (c) 2025 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.client.mixins.network;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;

//#if 12002 <= MC && MC <= 12004
//$$ import net.minecraft.network.FriendlyByteBuf;
//$$ import net.minecraft.resources.ResourceLocation;
//$$ import org.amateras_smp.amacarpet.network.AmaCarpetPayload;
//$$ import org.spongepowered.asm.mixin.injection.Inject;
//$$ import org.spongepowered.asm.mixin.injection.At;
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//$$ import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//#endif

@Mixin(ClientboundCustomPayloadPacket.class)
public class ClientboundCustomPayloadPacketMixin {
//#if 12002 <= MC && MC <= 12004
//$$     @Inject(method = "readPayload", at = @At("HEAD"), cancellable = true)
//$$     private static void onReadPayload(ResourceLocation identifier, FriendlyByteBuf friendlyByteBuf, CallbackInfoReturnable<CustomPacketPayload> cir) {
//$$         if (identifier.equals(AmaCarpetPayload.identifier)) {
//$$             cir.setReturnValue(new AmaCarpetPayload(friendlyByteBuf));
//$$         }
//$$     }
//#endif
}
