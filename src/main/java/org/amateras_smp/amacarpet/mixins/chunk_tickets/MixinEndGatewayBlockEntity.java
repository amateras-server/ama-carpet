package org.amateras_smp.amacarpet.mixins.chunk_tickets;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import org.amateras_smp.amacarpet.AmaCarpet;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndGatewayBlockEntity.class)
public class MixinEndGatewayBlockEntity {
    //#if MC < 12100
    // this was implemented in 1.21 so is not needed in 1.21
    @Inject
    (
            method = "tryTeleportingEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;teleport(DDD)V",
                    shift = At.Shift.AFTER
            )
    )
    private static void onEntityTeleport(World world, BlockPos pos, BlockState state, Entity entity, EndGatewayBlockEntity blockEntity, CallbackInfo ci, @Local(ordinal = 1) BlockPos blockPos) {
        if (!AmaCarpetSettings.endGatewayChunkLoad) return;
        AmaCarpet.LOGGER.info("debugging : onEntityTeleport");
        if (world instanceof ServerWorld serverWorld) {
            AmaCarpet.LOGGER.info("debugging : world is ServerWorld instance");
            serverWorld.getChunkManager().addTicket(ChunkTicketType.PORTAL, new ChunkPos(blockPos), 3, blockPos);
        }
    }
    //#endif
}
