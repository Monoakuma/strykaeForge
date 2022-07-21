package com.github.monoakuma.strykae.common;

import net.minecraftforge.common.config.Config.Comment;

import static com.github.monoakuma.strykae.Strykae.MOD_ID;

@net.minecraftforge.common.config.Config(modid = MOD_ID)
public class Config {
    public static class SConfig
    {
        @Comment({"The power multiplier for Sahvic magic based on the moon's phase {FullMoon,WaningGibbous,WaningHalfMoon,WaningCrescentMoon,NewMoon,WaxingCrescentMoon,WaxingHalfMoon,WaxingGibbous}"})
        public static int[] moonPower = new int[]{16,8,4,2,0,2,4,8};

        @Comment({"List of food items that increase a player's hygiene score."})
        public static String[] healthyFoods = new String[] {
                "item.mushroomStew",
                "item.appleGold",
                "item.rabbitStew",
                "item.beetroot_soup"
        };
        @Comment({"List of food items that decrease a player's hygiene score."})
        public static String[] unhealthyFoods = new String[] {
                "item.rottenFlesh",
                "item.beefRaw",
                "item.muttonRaw",
                "item.rabbitRaw",
                "item.porkchopRaw",
                "item.chickenRaw"
        };
    }

}
