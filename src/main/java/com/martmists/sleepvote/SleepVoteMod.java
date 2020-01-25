package com.martmists.sleepvote;

import com.martmists.sleepvote.config.SleepVoteConfig;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.DedicatedServerModInitializer;

public class SleepVoteMod implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        AutoConfig.register(SleepVoteConfig.class, JanksonConfigSerializer::new);
        AutoConfig.getConfigHolder(SleepVoteConfig.class).getConfig();
    }
}
