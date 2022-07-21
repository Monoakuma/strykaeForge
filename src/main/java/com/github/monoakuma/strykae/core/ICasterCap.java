package com.github.monoakuma.strykae.core;

import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public interface ICasterCap {
     void syncPlayerCData(EntityPlayer player);
     int getManaCap();
     int getMana();
     void resetMana();
     void setMana(int v);
     void regenMana();
     int getLives();
     void setLives(int v);
     boolean getIsHuman();
     void setIsHuman(boolean humanity);
     void lowerHygiene();
     void raiseHygiene();
     void setHygiene(int hygiene);
     int getHygiene();
     void infectionEvent(EntityPlayer infected);
}
