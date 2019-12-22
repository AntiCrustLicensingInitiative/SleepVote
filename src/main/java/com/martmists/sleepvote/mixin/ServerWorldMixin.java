package com.martmists.sleepvote.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Shadow
    private boolean allPlayersSleeping;

    @Final
    @Shadow
    private MinecraftServer server;

    @Inject(method="updatePlayersSleeping()V", at=@At("RETURN"))
    void updatePlayersSleeping(CallbackInfo ci) {
        List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
        players.removeIf((e)-> e.isSpectator() || (e.dimension != DimensionType.OVERWORLD));
        float total = players.size();
        float sleeping = players.stream().filter(PlayerEntity::isSleepingLongEnough).count();
        float treshold = Float.parseFloat(System.getProperty("sleepvote.treshold", "0.3"));
        allPlayersSleeping = (total/sleeping >= treshold);
    }
}
