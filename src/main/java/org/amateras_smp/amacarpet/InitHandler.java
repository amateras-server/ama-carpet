// Copyright (c) 2025 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet;

import org.amateras_smp.amacarpet.config.CheatRestrictionConfig;
import org.amateras_smp.amacarpet.network.AmaCarpetPayload;

public class InitHandler {

    public static void init() {
        AmaCarpetServer.init();
        CheatRestrictionConfig.init();
        AmaCarpetPayload.registerPayload();
    }
}
