// Copyright (c) 2026 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

//#if MC < 11900
//$$ import net.minecraft.network.chat.TextComponent;
//#endif

public class TextUtil {
    public static Component withFormat(String message, ChatFormatting formatting) {
        //#if MC >= 11900
        return Component.literal(message).withStyle(formatting);
        //#else
        //$$ return new TextComponent(message).withStyle(formatting);
        //#endif
    }
}
