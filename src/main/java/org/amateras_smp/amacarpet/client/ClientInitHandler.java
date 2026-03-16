// Copyright (c) 2025 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.client;

import org.amateras_smp.amacarpet.network.AmaCarpetPayload;
import org.amateras_smp.amacarpet.network.PacketHandler;

public class ClientInitHandler {
    public static void init() {
        AmaCarpetPayload.registerPayload();
        PacketHandler.registerS2CHandler();
    }
}
