// Copyright (c) 2025 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.resources.Identifier;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.utils.IdentifierUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import io.netty.buffer.Unpooled;

//#if MC >= 12005
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
//#elseif MC >= 11904
//$$ import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
//$$ import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
//$$ import net.fabricmc.fabric.api.networking.v1.FabricPacket;
//$$ import net.fabricmc.fabric.api.networking.v1.PacketType;
//#endif

//#if MC >= 12004
import org.jetbrains.annotations.NotNull;
//#endif

//#if MC >= 12005
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//#endif

public class AmaCarpetPayload
    //#if MC >= 12005
    implements CustomPacketPayload
    //#elseif MC >= 11904
    //$$ implements FabricPacket
    //#endif
{
    public byte[] content;

    public AmaCarpetPayload(byte[] content) {
        this.content = content;
    }

    public AmaCarpetPayload(FriendlyByteBuf input) {
        this.content = input.readByteArray();
    }

    public byte[] content() {
        return this.content;
    }

    public static final Identifier identifier = IdentifierUtil.of(AmaCarpet.kModId, "amacm");

    //#if MC >= 12005
    public static final Type<AmaCarpetPayload> TYPE = new Type<>(identifier);
    private static final StreamCodec<FriendlyByteBuf, AmaCarpetPayload> CODEC = StreamCodec.composite(
        ByteBufCodecs.BYTE_ARRAY, AmaCarpetPayload::content, AmaCarpetPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    //#elseif MC >= 11904
    //$$ public static final PacketType<AmaCarpetPayload> TYPE = PacketType.create(identifier, AmaCarpetPayload::new);
    //$$ @Override
    //$$ public PacketType<?> getType() {
    //$$     return TYPE;
    //$$ }
    //#endif

    public static void registerPayload() {
        //#if MC >= 260100
        PayloadTypeRegistry.serverboundPlay().register(TYPE, CODEC);
        PayloadTypeRegistry.clientboundPlay().register(TYPE, CODEC);
        //#elseif MC >= 12005
        //$$ PayloadTypeRegistry.playC2S().register(TYPE, CODEC);
        //$$ PayloadTypeRegistry.playS2C().register(TYPE, CODEC);
        //#endif
    }

    public void sendC2S() {
        ClientPacketListener networkHandler = Minecraft.getInstance().getConnection();
        if (AmaCarpet.kIsClient && networkHandler != null) {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            AmaCarpet.LOGGER.debug("making c2s packet, identifier : {}, content : {}", identifier, content);
            write(buf);

            //#if MC >= 12005
            networkHandler.send(new ServerboundCustomPayloadPacket(this));
            //#elseif MC >= 11904
            //$$ ClientPlayNetworking.send(this);
            //#else
            //$$ networkHandler.send(new ServerboundCustomPayloadPacket(identifier, buf));
            //#endif
        } else {
            AmaCarpet.LOGGER.debug("this is not client or client connection is null");
        }
    }

    public void sendS2C(ServerPlayer player) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        write(buf);

        //#if MC >= 12005
        ClientboundCustomPayloadPacket packet = new ClientboundCustomPayloadPacket(this);
        player.connection.send(packet);
        //#elseif MC >= 11904
        //$$ ServerPlayNetworking.send(player, this);
        //#else
        //$$ ClientboundCustomPayloadPacket packet = new ClientboundCustomPayloadPacket(identifier, buf);
        //$$ player.connection.send(packet);
        //#endif
    }

    //#if 11904 <= MC && MC <= 12004
    //$$ @Override
    //#endif
    public void write(FriendlyByteBuf output) {
        output.writeByteArray(content);
    }
}
