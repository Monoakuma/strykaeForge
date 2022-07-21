package com.github.monoakuma.strykae.core;

import com.github.monoakuma.strykae.Strykae;
import com.github.monoakuma.strykae.network.messages.CasterCapSyncMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.Objects;

import static com.github.monoakuma.strykae.Strykae.LOGGER;

public class CasterCap  {
    @CapabilityInject(ICasterCap.class)
    public static final Capability<ICasterCap> CDATA_CAPABILITY = null;
    public static ICasterCap getCaster(Entity e) {
        return (e instanceof EntityPlayer) ? e.getCapability(CDATA_CAPABILITY, null) : null;
    }
    public static class CasterProvider implements ICapabilitySerializable<NBTTagCompound> {
        private final ICasterCap imp = new CasterImp();
        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing side) {
            return (CDATA_CAPABILITY.equals(capability));
        }
        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing side) {
            if (CDATA_CAPABILITY.equals(capability)) {
                T result = (T) imp;
                return result;
            }
            return null;
        }
        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setTag("CasterCap", CDATA_CAPABILITY.getStorage().writeNBT(CDATA_CAPABILITY, imp, null));
            return compound;
            //return (NBTTagCompound) CDATA_CAPABILITY.getStorage().writeNBT(CDATA_CAPABILITY,imp, null);
        }
        @Override
        public void deserializeNBT(NBTTagCompound compound) {
            NBTTagList list = (NBTTagList) compound.getTag("CasterCap");
            CDATA_CAPABILITY.getStorage().readNBT(CDATA_CAPABILITY, imp, null, list);
            //CDATA_CAPABILITY.getStorage().readNBT(CDATA_CAPABILITY,imp,null, compound);
        }
    }

    public static class CasterStorage implements Capability.IStorage<ICasterCap> {
        @Override
        public NBTBase writeNBT(Capability<ICasterCap> capability, ICasterCap casterCap, EnumFacing side) {
            NBTTagList list = new NBTTagList();
            NBTTagCompound casterDataCompound = new NBTTagCompound();
            casterDataCompound.setInteger("Mana", casterCap.getMana());
            casterDataCompound.setInteger("Lives", casterCap.getLives());
            casterDataCompound.setInteger("Hygiene", casterCap.getHygiene());
            casterDataCompound.setBoolean("Is_Human", casterCap.getIsHuman());
            list.appendTag(casterDataCompound);
            return list;
        }
        @Override
        public void readNBT(Capability<ICasterCap> capability, ICasterCap casterCap, EnumFacing side, NBTBase casterData) {
            NBTTagList list = (NBTTagList) casterData;
            LOGGER.info(casterData);
            if (list==null) {
                casterCap.setMana(0);
                casterCap.setLives(0);
                casterCap.setIsHuman(true);
                casterCap.setHygiene(1);
            }
            else {
                NBTTagCompound casterDataCompound = list.getCompoundTagAt(0);
                if (!casterDataCompound.isEmpty())
                {
                    casterCap.setMana(casterDataCompound.getInteger("Mana"));
                    casterCap.setLives(casterDataCompound.getInteger("Lives"));
                    casterCap.setHygiene(casterDataCompound.getInteger("Hygiene"));
                    casterCap.setIsHuman(casterDataCompound.getBoolean("Is_Human"));
                    LOGGER.info("POST:MANA: "+casterDataCompound.getInteger("Mana")+" | LIVES: "+casterDataCompound.getInteger("Lives")+" | HUMAN: "+casterDataCompound.getBoolean("Is_Human"));
                }
            }

        }
    }

    public static class CasterImp implements ICasterCap {
        private int mana;
        private int lives;
        private boolean isHuman;
        private int hygiene; //more negative means poor hygiene, below -12 is infection. lowered by eating rotten flesh and raw meat, raised by eating stew and soup. Caps at -30 and 30
        public CasterImp(int mana, int lives, boolean isHuman, int hygiene) {
            LOGGER.info("I AM CASTER");
            this.mana=mana;
            this.lives=lives;
            this.isHuman=isHuman;
            this.hygiene=hygiene;
        }
        public CasterImp() {
            LOGGER.info("I AM");
            this.mana=0;
            this.lives=0;
            this.isHuman=true;
            this.hygiene=0;
        }
        public void syncPlayerCData(EntityPlayer player) {
            if (player instanceof EntityPlayerMP) {
                ICasterCap casterCap = getCaster(player);
                NBTTagCompound data = ((NBTTagList) Objects.requireNonNull(CDATA_CAPABILITY.getStorage().writeNBT(CDATA_CAPABILITY, casterCap, null))).getCompoundTagAt(0);
                Strykae.network.sendTo(new CasterCapSyncMessage(CDATA_CAPABILITY.getName(),data),(EntityPlayerMP) player);
            }

        }
        public int getManaCap() {
            if (isHuman) {return 100;} else {return 70;}
        }
        public int getMana() {return this.mana;}
        public void resetMana() {this.mana = 0;}
        public void setMana(int v) {this.mana = v;}
        public void regenMana() { if (this.mana<getManaCap()) {this.mana++;}}
        public int getLives() {
            return lives;
        }
        public void setLives(int v) {
            lives=v;
        }
        public boolean getIsHuman() {
            return isHuman;
        }
        public void setIsHuman(boolean humanity) {
            isHuman=humanity;
        }
        public void lowerHygiene() {if (this.hygiene>-30) this.hygiene--;}
        public void raiseHygiene() {if (this.hygiene<30)this.hygiene++;}
        public void setHygiene(int hygiene) {this.hygiene = hygiene;}
        public int getHygiene() {return this.hygiene;}
        public void infectionEvent(EntityPlayer infected) {
            for (EntityPlayer e : infected.getEntityWorld().getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(infected.posX+4, infected.posY+4, infected.posZ+4, infected.posX-4, infected.posY-4, infected.posZ-4)))
            {
                if (infected.getDistanceSq(e)<=4) {
                    ICasterCap casterCap = getCaster(e);
                    if (casterCap!=null && e!=infected) {
                        casterCap.lowerHygiene();
                    }
                }
            }
        }
    }


}
