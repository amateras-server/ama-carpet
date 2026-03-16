package org.amateras_smp.amacarpet.network;

import carpet.utils.Translations;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.amateras_smp.amacarpet.network.packets.HandshakePacket;
import org.amateras_smp.amacarpet.utils.TextUtil;

public class CheckClient {
    private static Component getDcMsg() {
        return TextUtil.withFormat(Translations.tr("ama.message.on_missing_client_dc"), ChatFormatting.RED);
    }

    private static void logConnection(String name) {
        AmaCarpet.LOGGER.debug("[AmaCarpet] player joined with matching amacarpet client: {}", name);
    }

    private static void logDisconnection(String name) {
        AmaCarpet.LOGGER.debug("[AmaCarpet] disconnecting player because they have no matching client: {}", name);
    }

    public static void registerEvents() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            if (ServerPlayNetworking.canSend(handler.player, AmaCarpetPayload.identifier)) {
                AmaCarpet.LOGGER.debug("[AmaCarpet] found client");
                logConnection(handler.player.getDisplayName().getString());

                AmaCarpet.LOGGER.debug("sending AmaCarpet HandShake packet");
                PacketHandler.sendS2C(new HandshakePacket(AmaCarpet.getVersion()), handler.player);
            } else {
                AmaCarpet.LOGGER.debug("[AmaCarpet] not found client");
                if (AmaCarpetSettings.requireAmaCarpetClient) {
                    logDisconnection(handler.player.getDisplayName().getString());
                    handler.disconnect(getDcMsg());
                }
            }
        });
    }

}
