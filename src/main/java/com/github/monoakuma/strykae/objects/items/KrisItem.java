package com.github.monoakuma.strykae.objects.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

import static com.github.monoakuma.strykae.Strykae.*;

public class KrisItem extends ItemSword {
    public KrisItem() {
        super(ToolMaterial.GOLD);
        this.setMaxDamage(18);
        this.setMaxStackSize(1);
        this.setCreativeTab(TAB);
        this.addPropertyOverride(new ResourceLocation("playerdistance"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            @Override
            public float apply(@Nonnull ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entityLivingBase) {
                if (entityLivingBase==null&&!stack.isOnItemFrame()) {
                    return 0.0f;
                } else {
                    Entity holder = (entityLivingBase!=null) ? entityLivingBase : stack.getItemFrame();
                    if (stack.hasTagCompound()) {
                        LOGGER.info((float) getDistanceToMarked(stack,holder));
                        return (float) getDistanceToMarked(stack,holder);
                    }
                }
                return 0.0f;
            }
            @SideOnly(Side.CLIENT)
            private double getDistanceToMarked(ItemStack stack,Entity haudr) { //haudr is exprish for hunter don't worry about it
                if (stack.getTagCompound().hasUniqueId("KrisMark")) {
                    UUID markedUUID = stack.getTagCompound().getUniqueId("KrisMark");
                    if (markedUUID != null) {
                        EntityPlayer markedEntity =haudr.world.getPlayerEntityByUUID(markedUUID);
                        if (markedEntity != null && markedEntity!=haudr) {
                            return Math.sqrt((markedEntity.posX-haudr.posX)*(markedEntity.posX-haudr.posX)+(markedEntity.posY-haudr.posY)*(markedEntity.posY-haudr.posY)+(markedEntity.posZ-haudr.posZ)*(markedEntity.posZ-haudr.posZ));
                        }
                    }
                }
                return 9001.0D; //dragon ball z reference
            }
        });
    }
    public boolean hitEntity(ItemStack stack, @Nonnull EntityLivingBase entity1, @Nonnull EntityLivingBase entity2) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        if (entity1 instanceof EntityPlayer) stack.getTagCompound().setUniqueId("KrisMark", entity1.getUniqueID());  // shut up intellij this can't raise an exception
        return super.hitEntity(stack, entity1, entity2);
    }
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        if (player.isSneaking()) stack.getTagCompound().setUniqueId("KrisMark", player.getUniqueID());  // shut up intellij this can't raise an exception
        return super.onItemRightClick(world,player,hand);
    }

}
