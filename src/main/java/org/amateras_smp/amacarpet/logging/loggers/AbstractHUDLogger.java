// Copyright (c) 2026 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.logging.loggers;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public abstract class AbstractHUDLogger {
    private final String name;
    private final boolean strictOptions;

    public AbstractHUDLogger(String name, boolean strictOptions) {
        this.name = name;
        this.strictOptions = strictOptions;
    }

    public String getName() {
        return this.name;
    }

    public boolean strictOptions() {
        return this.strictOptions;
    }

    public abstract Component[] onHudUpdate(String option, Player player);
}
