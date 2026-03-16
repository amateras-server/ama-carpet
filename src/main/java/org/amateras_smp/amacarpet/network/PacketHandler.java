// Copyright (c) 2025 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientLoginNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerLoginNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.client.AmaCarpetClient;
import org.amateras_smp.amacarpet.network.packets.EnableSpecifiedFeaturePacket;
import org.amateras_smp.amacarpet.network.packets.HandshakePacket;
import org.amateras_smp.amacarpet.network.packets.ModStatusQueryPacket;
import org.amateras_smp.amacarpet.network.packets.ModStatusResponsePacket;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class PacketHandler {
    private static final List<Packet> packetRegistry = new ArrayList<>();

    private record Packet(String key, Class<? extends IPacket> clazz) {
    }

    static {
        packetRegistry.add(new Packet("enable_specified_feature", EnableSpecifiedFeaturePacket.class));
        packetRegistry.add(new Packet("handshake", HandshakePacket.class));
        packetRegistry.add(new Packet("mod_status_query", ModStatusQueryPacket.class));
        packetRegistry.add(new Packet("mod_status_response", ModStatusResponsePacket.class));
    }

    private static IPacket decode(byte[] raw) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(raw); DataInputStream dis = new DataInputStream(byteArrayInputStream)) {
            String key = dis.readUTF();
            int len = dis.readInt();
            byte[] data = new byte[len];
            dis.readFully(data);

            for (Packet packet : packetRegistry)
                if (key.equals(packet.key)) {
                    try {
                        return packet.clazz.getConstructor(byte[].class).newInstance((Object) data);
                    } catch (Exception e) {
                        AmaCarpet.LOGGER.error("Failed to decode packet {}", key);
                        AmaCarpet.LOGGER.error(e);
                        return null;
                    }
                }
            AmaCarpet.LOGGER.error("Unknown Packet {}", key);
        } catch (IOException e) {
            AmaCarpet.LOGGER.error("Unknown Error: \n{}", e);
        }

        return null;
    }

    private static byte[] encode(IPacket packet) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); DataOutputStream dos = new DataOutputStream(byteArrayOutputStream)) {
            String key = null;
            for (Packet p : packetRegistry)
                if (packet.getClass() == p.clazz) {
                    key = p.key;
                    break;
                }
            if (key == null) return null;

            byte[] data = packet.encode();
            dos.writeUTF(key);
            dos.writeInt(data.length);
            dos.write(data);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            AmaCarpet.LOGGER.error(e);
            return null;
        }
    }

    public static void handleC2S(byte[] data, ServerPlayer player) {
        AmaCarpet.LOGGER.debug("handling c2s packet");
        IPacket packet = decode(data);
        if (packet == null) return;
        AmaCarpetServer.MINECRAFT_SERVER.execute(() -> packet.onServer(player));
    }

    public static void handleS2C(byte[] data) {
        AmaCarpet.LOGGER.debug("handling s2c packet");
        IPacket packet = decode(data);
        if (packet == null) return;
        Minecraft.getInstance().execute(packet::onClient);
    }

    public static void sendC2S(IPacket packet) {
        AmaCarpet.LOGGER.debug("sending c2s packet");
        AmaCarpetPayload packetPayload = new AmaCarpetPayload(encode(packet));
        packetPayload.sendC2S();
    }

    public static void sendS2C(IPacket packet, ServerPlayer player) {
        AmaCarpet.LOGGER.debug("sending s2c packet");
        AmaCarpetPayload packetPayload = new AmaCarpetPayload(encode(packet));
        packetPayload.sendS2C(player);
    }

    public static void registerC2SHandler() {
        //#if MC >= 12005
        ServerPlayNetworking.registerGlobalReceiver(
            AmaCarpetPayload.TYPE,
            (payload, context) -> {
                context.server().execute(() -> {
                    handleC2S(payload.content, context.player());
                });
            }
        );
        //#elseif MC >= 12002
        //$$ ServerPlayNetworking.registerGlobalReceiver(
        //$$     AmaCarpetPayload.TYPE,
        //$$     (payload, player, responseSender) -> {
        //$$         handleC2S(payload.content, player);
        //$$     }
        //$$ );
        //#else
        //$$ ServerPlayNetworking.registerGlobalReceiver(
        //$$     AmaCarpetPayload.identifier,
        //$$     (server, player, handler, buf, responseSender) -> {
        //$$         server.execute(() -> {
        //$$             handleC2S(buf.readByteArray(), player);
        //$$         });
        //$$     }
        //$$ );
        //#endif
    }

    public static void registerS2CHandler() {
        //#if MC >= 12005
        ClientPlayNetworking.registerGlobalReceiver(
            AmaCarpetPayload.TYPE,
            (payload, context) -> {
                context.client().execute(() -> {
                    handleS2C(payload.content);
                });
            }
        );
        //#elseif MC >= 12002
        //$$ ClientPlayNetworking.registerGlobalReceiver(
        //$$     AmaCarpetPayload.TYPE,
        //$$     (payload, player, responseSender) -> {
        //$$         handleS2C(payload.content);
        //$$     }
        //$$ );
        //#else
        //$$ ClientPlayNetworking.registerGlobalReceiver(
        //$$     AmaCarpetPayload.identifier,
        //$$     (client, handler, buf, responseSender) -> {
        //$$         client.execute(() -> {
        //$$             handleS2C(buf.readByteArray());
        //$$         });
        //$$     }
        //$$ );
        //#endif
    }
}
