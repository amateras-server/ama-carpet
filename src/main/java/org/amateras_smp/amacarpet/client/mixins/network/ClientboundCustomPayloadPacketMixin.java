// Copyright (c) 2025 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.client.mixins.network;

import org.spongepowered.asm.mixin.Mixin;

//#if 12002 <= MC && MC <= 12004
//$$ import net.minecraft.network.FriendlyByteBuf;
//$$ import net.minecraft.resources.ResourceLocation;
//$$ import org.amateras_smp.amacarpet.network.AmaCarpetPayload;
//$$ import org.spongepowered.asm.mixin.injection.Inject;
//$$ import org.spongepowered.asm.mixin.injection.At;
//$$ @Mixin(net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket.class)
//$$ public class ClientboundCustomPayloadPacketMixin {
//$$     @Inject(method = "readPayload", at = @At("HEAD"), cancellable = true)
//$$     private static void onReadPayload(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf, org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable<net.minecraft.network.protocol.common.custom.CustomPacketPayload> cir) {
//$$         if (resourceLocation.equals(AmaCarpetPayload.identifier)) {
//$$             cir.setReturnValue(new AmaCarpetPayload(friendlyByteBuf));
//$$         }
//$$     }
//$$ }
//#else
import org.amateras_smp.amacarpet.utils.compat.Dummy;
@Mixin(Dummy.class)
public class ClientboundCustomPayloadPacketMixin {
}
//#endif