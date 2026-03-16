// Copyright (c) 2025 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.utils;

import carpet.utils.Translations;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.amateras_smp.amacarpet.AmaCarpet;

public class PlayerUtil {
    public static void onCatchCheater(ServerPlayer player, String cheatName) {
        player.connection.disconnect(Component.literal(String.format(Translations.tr("ama.message.on_cheat_dc"), cheatName)).withStyle(ChatFormatting.RED));
        AmaCarpet.LOGGER.info("disconnected player {} because they enabled {} which is prohibited by cheatRestriction", player.getDisplayName().getString(), cheatName);
    }
}
