package org.amateras_smp.amacarpet.client.mixins.network;

import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.network.ClientConnection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientLoginNetworkHandler.class)
public interface AccessorClientLoginNetworkingHandler {
    @Accessor
    ClientConnection getConnection();
}
