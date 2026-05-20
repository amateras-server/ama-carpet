// Copyright (c) 2026 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.logging;

import carpet.logging.LoggerRegistry;
import carpet.logging.HUDLogger;
import net.minecraft.server.MinecraftServer;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.logging.loggers.AbstractHUDLogger;
// import org.amateras_smp.amacarpet.logging.loggers.EntityCounterLogger;
import org.amateras_smp.amacarpet.logging.loggers.ReplayLogger;

import java.lang.reflect.Field;

public class AmaCarpetLoggerRegistry {
    // public static boolean __entityCounter;
    public static boolean __replay;

    public static void registerLoggers() {
        // register(EntityCounterLogger.getInstance(), null, null);
        register(ReplayLogger.getInstance(), ReplayLogger.defaultOption(), ReplayLogger.options());
    }

    private static void register(AbstractHUDLogger logger, String defaultOption, String[] options) {
        LoggerRegistry.registerLogger(logger.getName(), standardHUDLogger(logger.getName(), defaultOption, options, logger.strictOptions()));
    }

    public static Field getLoggerField(String logName) {
        try {
            return AmaCarpetLoggerRegistry.class.getField("__" + logName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(String.format("Failed to get logger field \"%s\" @ %s", logName, AmaCarpet.kModName));
        }
    }

    public static HUDLogger standardHUDLogger(String logName, String def, String[] options, boolean strictOptions) {
        return new HUDLogger(getLoggerField(logName), logName, def, options, strictOptions);
    }

    public static void updateHUD(MinecraftServer server) {
        // doHudLogging(__entityCounter, EntityCounterLogger.getInstance());
        doHudLogging(__replay, ReplayLogger.getInstance());
    }

    private static void doHudLogging(boolean condition, AbstractHUDLogger logger) {
        if (condition) {
            LoggerRegistry.getLogger(logger.getName()).log(logger::onHudUpdate);
        }
    }
}
