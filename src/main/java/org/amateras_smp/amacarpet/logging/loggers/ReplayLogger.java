// Copyright (c) 2026 Amateras-Server
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.logging.loggers;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.level.ChunkPos;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.utils.TextUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.casual.arcade.replay.recorder.ReplayRecorder;
import net.casual.arcade.replay.recorder.chunk.ReplayChunkRecorder;
import net.casual.arcade.replay.recorder.chunk.ReplayChunkRecorders;
import net.casual.arcade.replay.recorder.player.ReplayPlayerRecorders;

public class ReplayLogger extends AbstractHUDLogger {
    private static final String kName = "replay";
    private static final boolean kStrictOptions = false;

    private static final String kServerReplayModName = "server-replay";
    private static final boolean kHasServerReplay = FabricLoader.getInstance().isModLoaded(kServerReplayModName);

    private static final ReplayLogger kInstance = new ReplayLogger(kName, kStrictOptions);

    public static ReplayLogger getInstance() {
        return kInstance;
    }

    public ReplayLogger(String name, boolean strictOptions) {
        super(name, strictOptions);
    }

    @Override
    public Component[] onHudUpdate(String option, Player player) {
        if (!kHasServerReplay) {
            return new Component[]{TextUtil.withFormat("ServerReplay mod is not loaded", ChatFormatting.GRAY)};
        } else {
            return ServerReplayStatusManager.currentStatus(option);
        }
    }

    private static class ServerReplayStatusManager {
        private static Component[] currentStatus(String elements) {
            String players = getStatusFor(/* "Players", */ ReplayPlayerRecorders.recorders(), elements);
            String chunks = getStatusFor(/* "Chunks", */ ReplayChunkRecorders.recorders(), elements);
            String savingPlayers = getStatusFor(/* "Saving (Players)", */ ReplayPlayerRecorders.closing(), elements);
            String savingChunks = getStatusFor(/* "Saving (Chunks)", */ ReplayChunkRecorders.closing(), elements);

            List<Component> result = new ArrayList<>();
            if (!players.isBlank()) {
                result.add(TextUtil.withFormat(players, ChatFormatting.AQUA));
            }
            if (!chunks.isBlank()) {
                result.add(TextUtil.withFormat(chunks, ChatFormatting.GREEN));
            }
            if (!savingPlayers.isBlank()) {
                result.add(TextUtil.withFormat(savingPlayers, ChatFormatting.YELLOW));
            }
            if (!savingChunks.isBlank()) {
                result.add(TextUtil.withFormat(savingChunks, ChatFormatting.YELLOW));
            }

            if (result.isEmpty()) {
                result.add(TextUtil.withFormat("Not recording", ChatFormatting.GRAY));
            }

            return result.toArray(new Component[0]);
        }

        private static String getStatusFor(/* String type, */ Collection<? extends ReplayRecorder> recorders, String elements) {
            if (recorders.isEmpty()) {
                return "";
            }
            // StringBuilder builder = new StringBuilder(String.format("%s: [", type));
            StringBuilder builder = new StringBuilder();

            boolean name = false;
            boolean range = false;
            boolean dim = false;
            for (String e : elements.split(",")) {
                switch (e) {
                    case "name": name = true; break;
                    case "range": range = true; break;
                    case "dim": dim = true; break;
                }
            }

            if (!name && !range && !dim) {
                return "";
            }

            for (ReplayRecorder recorder : recorders) {
                if (name) {
                    builder.append(String.format("\"%s\"", recorder.getName()));
                }
                if (recorder instanceof ReplayChunkRecorder chunkRecorder) {
                    String dimension = chunkRecorder.getLevel().dimension().identifier().toShortString();

                    AmaCarpet.LOGGER.info(chunkRecorder.getChunks().toString());
                    // final String kAllFormat = ": [%d, %d]..[%d, %d] of %s";
                    if (range || dim) {
                        builder.append(":");
                        if (range) {
                            ChunkPos from = chunkRecorder.getChunks().getFrom();
                            ChunkPos to = chunkRecorder.getChunks().getTo();
                            //#if MC >= 260000
                            builder.append(String.format(" [%d, %d]..[%d, %d]", from.x(), from.z(), to.x(), to.z()));
                            //#else
                            //$$ builder.append(String.format("[%d, %d]..[%d, %d]", from.x, from.z, to.x, to.z));
                            //#endif
                        }
                        if (dim) {
                            builder.append(String.format(" in %s", dimension));
                        }
                    }
                }
                builder.append(", ");
            }
            // Remove trailing comma and space.
            builder.delete(builder.length() - 2, builder.length());
            // builder.append("]");

            return builder.toString();
        }
    }
}
