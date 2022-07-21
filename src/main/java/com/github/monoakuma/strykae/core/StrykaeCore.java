package com.github.monoakuma.strykae.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import toughasnails.api.temperature.Temperature;
import toughasnails.api.temperature.TemperatureHelper;

import java.util.Random;

import static com.github.monoakuma.strykae.common.Config.SConfig.moonPower;

public class StrykaeCore {
    public static long getDays(World world) {
        return(world.getTotalWorldTime()/24000);
    }
    public static int getMoonphase(World world) {return moonPower[world.getMoonPhase()];}
    public static void warmBody(EntityPlayer player, int warmth) {TemperatureHelper.getTemperatureData(player).addTemperature(new Temperature(warmth));}
    public static String getGenericName() {
        String[] names = {
            "Shinji","Izaya","Ryuugamine","Tria Quinquaginta","Atosis","Azeban","Kee-wakw","Kisosen","Kita-skog","Kchi-awasos","Mateguas","Nanom-keea-po-da","Niben","Pamola","Psonen","Padogiyik","Pebon","Siguan","Tabaldak","Wa-won-dee-a-megw","Wad-zoo-sen","Wassan-mon-ganeehla-ak","Oodzee-hozo","Tool-ba","Agaskw","Nokemis","Mool-sem","Batsolowanagwes","Tsa-tsamolee-as","Rei","Comet","Cosmo","Balthasar","Gremory","Sitri","Amon","Mammon","Gavriel","Kaji","Nagisa","Ramiel","Paimon","Asmodeus","Belial","Eligos","Astaroth","Slift","Tortuga","Crosa Rosa","Heilung","Gnulieh","Melchior","Casper","Ireul","Azanor","Vazkii","Emoniph","Watame","Kimaris","twitch.tv/westerncomet"
        };
        return names[new Random().nextInt(names.length)];
    }

}
