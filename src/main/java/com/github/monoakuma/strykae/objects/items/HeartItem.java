package com.github.monoakuma.strykae.objects.items;

import com.github.monoakuma.strykae.core.CasterCap;
import com.github.monoakuma.strykae.core.ICasterCap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import java.util.UUID;

import static com.github.monoakuma.strykae.Strykae.TAB;
import static com.github.monoakuma.strykae.core.CasterCap.CDATA_CAPABILITY;

public class HeartItem extends ItemFood {

    public HeartItem()
    {
        super(5,0.5f,false);
        this.setAlwaysEdible();
        this.setMaxStackSize(1);
        this.setCreativeTab(TAB);
    }
    public void setPlayer(ItemStack stack, UUID playeruuid) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        stack.getTagCompound().setUniqueId("HeartOwner", playeruuid);  // shut up intellij this can't raise an exception
    }

    public UUID getPlayerUUID(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasUniqueId("HeartOwner")) {
            return stack.getTagCompound().getUniqueId("HeartOwner");
        } else {
            return null;
        }
    }
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            ICasterCap casterCap = player.getCapability(CDATA_CAPABILITY, EnumFacing.UP);
            if (casterCap!=null) {
                casterCap.setIsHuman(false);
                casterCap.setLives(casterCap.getLives()+1);
                casterCap.setHygiene(0);
                world.playSound(player,player.posX,player.posY,player.posZ, SoundEvents.ENTITY_WOLF_HOWL, SoundCategory.AMBIENT,1.0F,0.5F);
            }
        }
        return super.onItemUseFinish(stack,world,entity);
    }
}
