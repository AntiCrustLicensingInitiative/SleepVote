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

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;

import java.util.List;

import com.martmists.sleepvote.config.SleepVoteConfig;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
	@Shadow
	private boolean allPlayersSleeping;

	@Final
	@Shadow
	private MinecraftServer server;

	private SleepVoteConfig config = AutoConfig.getConfigHolder(SleepVoteConfig.class).getConfig();

	@Inject(method="updatePlayersSleeping()V", at=@At("RETURN"))
	void updatePlayersSleeping(CallbackInfo ci) {
		List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
		players.removeIf((e)-> e.isSpectator() || (e.dimension != DimensionType.OVERWORLD));
		if (players.isEmpty()){
			allPlayersSleeping = false;
			return;
		}
		double total = players.size();
		double sleeping = players.stream().filter(PlayerEntity::isSleepingLongEnough).count();
		double threshold = config.sleepVoteThreshold;
		allPlayersSleeping = (total/sleeping >= threshold);
	}
}
