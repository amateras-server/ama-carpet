// Copyright (c) 2025 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet;

import org.amateras_smp.amacarpet.config.CheatRestrictionConfig;
import org.amateras_smp.amacarpet.network.AmaCarpetPayload;
import org.amateras_smp.amacarpet.network.CheckClient;
import org.amateras_smp.amacarpet.network.PacketHandler;

public class InitHandler {
    public static void init() {
        AmaCarpetServer.init();
        CheatRestrictionConfig.init();

        if (!AmaCarpet.isClient()) {
            AmaCarpetPayload.registerPayload();
            CheckClient.registerEvents();
            PacketHandler.registerC2SHandler();
        }
    }
}
