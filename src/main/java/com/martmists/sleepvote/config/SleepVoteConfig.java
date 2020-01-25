package com.martmists.sleepvote.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;

@Config(name = "sleepvote")
public class SleepVoteConfig implements ConfigData {
    @Comment("The required percent of people in bed in order to sleep. 1.0 needs everyone to sleep, 0.5 needs 50% of everyone to sleep, 0.0 disables sleeping.")
    public double sleepVoteThreshold = 0.5;
}