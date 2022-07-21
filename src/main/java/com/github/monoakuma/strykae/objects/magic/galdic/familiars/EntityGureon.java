package com.github.monoakuma.strykae.objects.magic.galdic.familiars;

import com.github.monoakuma.strykae.objects.magic.general.entities.AbstractFamiliar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nonnull;

import static com.github.monoakuma.strykae.init.SoundInit.*;

public class EntityGureon extends AbstractFamiliar {
    private final InventoryBasic gureonInventory;
    public EntityGureon(World world) {
        super(world);
        this.gureonInventory = new InventoryBasic("Items", false, 8);
        this.setCanPickUpLoot(true);
        this.setSize(0.9F,3.0F);
    }

    @Override
    protected void initEntityAI()
    {
        super.initEntityAI();
        this.tasks.addTask(6, new EntityGureonAIHarvestFarmland(this, 0.8D));
        this.tasks.addTask(7, new EntityGureonAIStoreInventory(this, 0.8D));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(37.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2037D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.3D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }

    protected void updateEquipmentIfNeeded(EntityItem p_175445_1_) {
        ItemStack itemstack = p_175445_1_.getItem();
        Item item = itemstack.getItem();
        if (canPickUpLoot()) {
            ItemStack itemstack1 = this.gureonInventory.addItem(itemstack);
            if (itemstack1.isEmpty()) {
                p_175445_1_.setDead();
            } else {
                itemstack.setCount(itemstack1.getCount());
            }
        }

    }

    public static class EntityGureonAIHarvestFarmland extends EntityAIMoveToBlock {
        private final EntityGureon gureon;
        private int currentTask;

        public EntityGureonAIHarvestFarmland(EntityGureon gureon, double p_i45889_2_) {
            super(gureon, p_i45889_2_, 16);
            this.gureon = gureon;
        }

        public boolean shouldExecute() {
            if (this.runDelay <= 0) {
                return false;
            }

            return super.shouldExecute();
        }

        public boolean shouldContinueExecuting() {
            return this.currentTask >= 0 && super.shouldContinueExecuting();
        }

        public void updateTask() {
            super.updateTask();
            this.gureon.getLookHelper().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.gureon.getVerticalFaceSpeed());
            if (this.getIsAboveDestination()) {
                World world = this.gureon.world;
                BlockPos blockpos = this.destinationBlock.up();
                IBlockState iblockstate = world.getBlockState(blockpos);
                Block block = iblockstate.getBlock();
                if (this.currentTask == 0 && block instanceof BlockCrops && ((BlockCrops)block).isMaxAge(iblockstate)) {
                    world.destroyBlock(blockpos, true);
                } else if (this.currentTask == 1 &&iblockstate.getMaterial() == Material.AIR) {
                    InventoryBasic inventorybasic = this.gureon.getInventory();

                    for(int i = 0; i < inventorybasic.getSizeInventory(); ++i) {
                        ItemStack itemstack = inventorybasic.getStackInSlot(i);
                        boolean flag = false;
                        if (!itemstack.isEmpty()) {
                            if (itemstack.getItem() == Items.WHEAT_SEEDS) {
                                world.setBlockState(blockpos, Blocks.WHEAT.getDefaultState(), 3);
                                flag = true;
                            } else if (itemstack.getItem() == Items.POTATO) {
                                world.setBlockState(blockpos, Blocks.POTATOES.getDefaultState(), 3);
                                flag = true;
                            } else if (itemstack.getItem() == Items.CARROT) {
                                world.setBlockState(blockpos, Blocks.CARROTS.getDefaultState(), 3);
                                flag = true;
                            } else if (itemstack.getItem() == Items.BEETROOT_SEEDS) {
                                world.setBlockState(blockpos, Blocks.BEETROOTS.getDefaultState(), 3);
                                flag = true;
                            } else if (itemstack.getItem() instanceof IPlantable && ((IPlantable)itemstack.getItem()).getPlantType(world, blockpos) == EnumPlantType.Crop) {
                                world.setBlockState(blockpos, ((IPlantable)itemstack.getItem()).getPlant(world, blockpos), 3);
                                flag = true;
                            }
                        }

                        if (flag) {
                            itemstack.shrink(1);
                            if (itemstack.isEmpty()) {
                                inventorybasic.setInventorySlotContents(i, ItemStack.EMPTY);
                            }
                            break;
                        }
                    }
                }

                this.currentTask = -1;
                this.runDelay = 10;
            }

        }

        protected boolean shouldMoveTo(World world, @Nonnull BlockPos blockPos) {
            Block block = world.getBlockState(blockPos).getBlock();
            if (block == Blocks.FARMLAND) {
                blockPos = blockPos.up();
                IBlockState iblockstate = world.getBlockState(blockPos);
                block = iblockstate.getBlock();
                if (block instanceof BlockCrops && ((BlockCrops)block).isMaxAge(iblockstate) && (this.currentTask == 0 || this.currentTask < 0)) {
                    this.currentTask = 0;
                    this.destinationBlock=blockPos;
                    return true;
                }

                if (iblockstate.getMaterial() == Material.AIR  && (this.currentTask == 1 || this.currentTask < 0)) {
                    this.currentTask = 1;
                    return true;
                }
            }

            return false;
        }
    }
    public class EntityGureonAIStoreInventory extends EntityAIMoveToBlock {
        private final EntityGureon gureon;
        private boolean chestFull=false;
        public EntityGureonAIStoreInventory(EntityCreature entity, double d0) {
            super(entity, d0, 16);
            this.gureon = (EntityGureon) entity;
        }

        @Override
        public void updateTask() {
            super.updateTask();
            this.gureon.getLookHelper().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.gureon.getVerticalFaceSpeed());
            if (world.getBlockState(this.destinationBlock).getBlock() == Blocks.CHEST && this.getIsAboveDestination()) {
                TileEntityChest chest = (TileEntityChest) world.getTileEntity(this.destinationBlock);
                int ticker=0;
                for (int i = 0; i < chest.getSizeInventory(); i++) {
                    for (int j = 0; j < this.gureon.gureonInventory.getSizeInventory(); j++) {
                        ItemStack itemStack = this.gureon.gureonInventory.getStackInSlot(j);
                        if (chest.getStackInSlot(i) == ItemStack.EMPTY) {
                            chest.setInventorySlotContents(i, itemStack);
                            this.gureon.gureonInventory.setInventorySlotContents(j,ItemStack.EMPTY);

                        }
                    }
                    if (chest.getStackInSlot(i)!=ItemStack.EMPTY) ticker++;
                }
                if (ticker==chest.getSizeInventory()) chestFull=true;
            }
        }
        @Override
        protected boolean shouldMoveTo(World world, @Nonnull BlockPos blockPos) {
            boolean flag = (world.getBlockState(blockPos).getBlock() == Blocks.CHEST && !gureonInventory.isEmpty()&&!chestFull);
            if (flag) destinationBlock=blockPos;
            return flag;
        }

        @Override
        public boolean shouldExecute() {
            return !gureonInventory.isEmpty();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return !chestFull||!gureonInventory.isEmpty();
        }
    }

    private InventoryBasic getInventory() {
        return gureonInventory;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        for(int i = 0; i < this.gureonInventory.getSizeInventory(); ++i) {
            ItemStack itemstack = this.gureonInventory.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                this.entityDropItem(itemstack, 0.0F);
            }
        }

    }

    @Override
    public float getEyeHeight() {
        return 2.9F;
    }

    protected SoundEvent getAmbientSound() {
        return ENTITY_GUREON_AMBIENT;
    }
    protected SoundEvent getHurtSound(@Nonnull DamageSource  source) {
        return ENTITY_GUREON_HURT;
    }
    protected SoundEvent getDeathSound() {
        return ENTITY_GUREON_DEATH;
    }
}
