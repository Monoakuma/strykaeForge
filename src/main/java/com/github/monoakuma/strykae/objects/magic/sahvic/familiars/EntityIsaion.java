package com.github.monoakuma.strykae.objects.magic.sahvic.familiars;

import com.github.monoakuma.strykae.objects.items.KrisItem;
import com.github.monoakuma.strykae.objects.magic.general.entities.AbstractFamiliar;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Objects;

import static com.github.monoakuma.strykae.init.SoundInit.*;

public class EntityIsaion extends AbstractFamiliar {
    private final EntityAIBase[] normalTasks = {
            new EntityAIFamiliarFollowOwner(this, 1.0D, 10.0F, 2.0F),
            new EntityAIOwnerHurtByTarget(this),
            new EntityAIOwnerHurtTarget(this),
            new EntityAIFamiliarHurtByTarget(this, true, new Class[0])
    };
    public EntityIsaion(World world) {
        super(world);
        this.setSize(0.6F, 1.8F);
    }
    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this,1.0D,true));
        this.tasks.addTask(3, new EntityAIFamiliarFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(6, new EntityAIOpenDoor(this, true));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIFamiliarHurtByTarget(this, true, new Class[0]));
    }
    public void setIsaionTarget (EntityPlayer target) {
        for (EntityAIBase task : normalTasks) {
            this.tasks.removeTask(task);
        }
        this.setOwner(target);
        this.setAttackTarget(target);
        this.setPositionNear(target);
        this.playSound(ENTITY_ISAION_AMBIENT,0.11020f,0.43f);
        for (ItemStack equipment : target.getEquipmentAndArmor()) {
            if (equipment.getItem() instanceof ItemArmor) {
                this.setItemStackToSlot(((ItemArmor)equipment.getItem()).armorType,equipment);
            }
        }
        this.targetTasks.addTask(1, new EntityAITargetOwner(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this,false, new Class[0]));
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack holding = player.getHeldItem(hand);
        if (!holding.isEmpty()) {
            if (holding.getItem() instanceof KrisItem) {
                if (holding.hasTagCompound()) {
                    if (holding.getTagCompound().hasUniqueId("KrisMark")) {
                        EntityPlayer marked = this.getEntityWorld().getPlayerEntityByUUID(holding.getTagCompound().getUniqueId("KrisMark"));
                        if (marked!=null) {
                            this.setIsaionTarget(marked);
                            holding.shrink(1);
                            player.playSound(SoundEvents.ENTITY_ILLAGER_CAST_SPELL,1.0f,1.0f);
                            return true;
                        }
                    }
                }
            }
        }
        return super.processInteract(player, hand);
    }



    private void setPositionNear(EntityPlayer target) {
        double startX = target.posX;
        double startY;
        double startZ = target.posZ;
        startX+=rand.nextBoolean() ? (double)rand.nextInt(6)*16:-(double)rand.nextInt(6)*16;
        startZ+=rand.nextBoolean() ? (double)rand.nextInt(6)*16:-(double)rand.nextInt(6)*16;
        startY=getSpawnY(this.world);
        setPosition(startX,startY,startZ);
    }
    public int getSpawnY(World world) {
        BlockPos pos = new BlockPos(this.posX, 256.0D, this.posZ);
        do {
            if (pos.getY() <= 0) {
                return 257;
            }
            pos = pos.down();
        } while(world.getBlockState(pos).getMaterial() == Material.AIR);
        return pos.getY() + 1;
    }

    public GameProfile getGameProfile() {

        return (getOwner() instanceof EntityPlayer) ? ((EntityPlayer)getOwner()).getGameProfile() : null;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.33D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.4D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(128);
    }

    protected SoundEvent getAmbientSound() {return (this.getAttackTarget()!=null) ? SoundEvents.BLOCK_GRASS_STEP : ENTITY_ISAION_AMBIENT;}
    protected SoundEvent getHurtSound(@Nonnull DamageSource p_184601_1_) {
        return SoundEvents.ENTITY_PLAYER_HURT;
    }
    protected SoundEvent getDeathSound() {return ENTITY_ISAION_DEATH;}

    public class EntityAITargetOwner extends EntityAIBase {
        AbstractFamiliar entity;
        public EntityAITargetOwner(AbstractFamiliar entity) {
            this.entity=entity;
        }
        @Override
        public boolean shouldExecute() {
            return this.entity.getAttackTarget()==null&&this.entity.getOwner()!=null;
        }
        @Override
        public boolean shouldContinueExecuting() {
            return this.entity.getAttackTarget()==null&&this.entity.getOwner()!=null;
        }
        @Override
        public void updateTask() {
            if (this.entity.getOwner()==null) setRandomOwner();
            this.entity.setAttackTarget(this.entity.getOwner());
        }
        private void setRandomOwner() {
            if (this.entity.world.playerEntities.size()>1)
            {
                EntityPlayer newOwner = this.entity.world.playerEntities.get(rand.nextInt(this.entity.world.playerEntities.size()));
                EntityPlayer oldOwner = ((EntityPlayer)this.entity.getOwner());
                if (newOwner==oldOwner) {
                    this.entity.setExpiration(1);
                } else {
                    this.entity.setOwner(newOwner);
                }
            }
        }
    }



}
