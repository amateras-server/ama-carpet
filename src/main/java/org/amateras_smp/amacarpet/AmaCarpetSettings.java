// Copyright (c) 2025 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet;

import carpet.api.settings.CarpetRule;
import carpet.api.settings.Rule;
import carpet.api.settings.Validator;
import net.minecraft.commands.CommandSourceStack;
import org.jetbrains.annotations.Nullable;

import static carpet.api.settings.RuleCategory.*;

public class AmaCarpetSettings {

    private static final String AMA = "AMA";

    @Rule(categories = { AMA, SURVIVAL })
    public static boolean cheatRestriction = false;

    @Rule(categories = { AMA, COMMAND, SURVIVAL })
    public static String commandListRestriction = "true";

    @Rule(categories = { AMA, COMMAND, SURVIVAL })
    public static String commandRestriction = "ops";

    @Rule(categories = { AMA })
    public static boolean debugModeAmaCarpet = false;

    @Rule(categories = { AMA, OPTIMIZATION, SURVIVAL })
    public static boolean disableAnimalSpawnOnChunkGen = false;

    @Rule(categories = { AMA, CREATIVE, OPTIMIZATION, SURVIVAL })
    public static boolean disableSoundEngine = false;

    //#if MC < 12100
    @Rule(categories = { AMA, SURVIVAL })
    public static boolean endGatewayChunkLoad = false;

    @Rule(categories = { AMA, SURVIVAL })
    public static boolean endPortalChunkLoad = false;
    //#endif

    @Rule(categories = { AMA, SURVIVAL })
    public static boolean notifyKyoyu = false;

    @Rule(categories = { AMA, SURVIVAL })
    public static boolean notifySyncmatica = false;

    @Rule(categories = { AMA, SURVIVAL })
    public static boolean reloadPortalTicket = false;

    @Rule(categories = { AMA })
    public static boolean requireAmaCarpetClient = false;

    @Rule(categories = { AMA }, options = {"3", "5", "10"}, strict = false, validators = TimeoutSecondsValidator.class)
    public static int requireAmaCarpetClientTimeoutSeconds = 5;

    private static class TimeoutSecondsValidator extends Validator<Integer> {
        @Override
        public Integer validate(@Nullable CommandSourceStack commandSourceStack, CarpetRule<Integer> carpetRule, Integer newValue, String s) {
            if (newValue >= 1 && newValue <= 180) {
                return newValue;
            } else {
                AmaCarpet.LOGGER.debug("Invalid value specified for TimeoutSeconds({}); Adopted current value of {}", newValue, carpetRule.value());
                return carpetRule.value();
            }
        }
    }
}
