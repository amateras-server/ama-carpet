package org.amateras_smp.amacarpet.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import org.amateras_smp.amacarpet.AmaCarpetSettings;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnderDragon.class)
public class EnderDragonMixin {
    //#if MC >= 260100
    @ModifyExpressionValue(
        method = "tickDeath",
        at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;dragonDeathTime:I", ordinal = 6, opcode = Opcodes.GETFIELD)
    )
    private int bringBackDeadDragonXpFarm(int original) {
        if (!AmaCarpetSettings.antiDeadDragonFix) return original;
        return original == 200 ? original : 0;
    }
    //#endif
}
