// Copyright (c) 2025 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.network.packets;

import net.minecraft.server.level.ServerPlayer;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.AmaCarpetServer;
import org.amateras_smp.amacarpet.network.IPacket;
import org.amateras_smp.amacarpet.network.PacketHandler;

import java.nio.charset.StandardCharsets;

public class HandshakePacket extends IPacket {
    private final String version;

    public HandshakePacket(String version) {
        this.version = version;
    }

    public HandshakePacket(byte[] bytes) {
        this.version = new String(bytes, StandardCharsets.UTF_8);
    }

    @Override
    public byte[] encode() {
        return version.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void onServer(ServerPlayer player) {
        AmaCarpetServer.LOGGER.info("[AmaCarpet] Player {} logged in with AmaCarpetClient, version {}.", player.getName().getString(), version);
        PacketHandler.sendS2C(new ModStatusQueryPacket(), player);
    }

    @Override
    public void onClient() {
        AmaCarpet.LOGGER.info("[AmaCarpet] Logging into AmaCarpetServer, version {}.", version);
        PacketHandler.sendC2S(new HandshakePacket(AmaCarpet.getVersion()));
    }
}
