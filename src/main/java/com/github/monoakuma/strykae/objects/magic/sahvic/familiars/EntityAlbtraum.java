package com.github.monoakuma.strykae.objects.magic.sahvic.familiars;

import com.github.monoakuma.strykae.Strykae;
import com.github.monoakuma.strykae.objects.magic.general.entities.AbstractFamiliar;
import net.minecraft.entity.*;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;


public class EntityAlbtraum extends AbstractFamiliar implements IJumpingMount {
    private static final IAttribute JUMP_STRENGTH = (new RangedAttribute((IAttribute)null, "horse.jumpStrength", 0.7D, 0.0D, 2.0D)).setDescription("Jump Strength").setShouldWatch(true);;
    protected float jumpPower;
    private int openMouthCounter;
    private int jumpRearingCounter;
    private float prevHeadLean;
    private float headLean;
    private float prevRearingAmount;
    private float rearingAmount;
    private float prevMouthOpenness;
    private float mouthOpenness;
    private boolean allowStandSliding;

    public EntityAlbtraum(World p_i1582_1_) {
        super(p_i1582_1_);
        this.setSize(1.3666F, 1.6F);
        this.stepHeight = 1.0F;
    }
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(JUMP_STRENGTH);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.34D);
        this.getEntityAttribute(JUMP_STRENGTH).setBaseValue(0.4000000059604645D);
    }

    protected boolean canFitPassenger(@Nonnull Entity passenger)
    {
        return this.getPassengers().size() < 2;
    }

    @Nullable
    @Override
    public Entity getControllingPassenger() {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : (Entity)list.get(0);
    }

    protected void mountTo(EntityPlayer player) {
        player.rotationYaw = this.rotationYaw;
        player.rotationPitch = this.rotationPitch;
        if (this.isBeingRidden()) return;
        if (!this.world.isRemote) {
            if (player.isRiding()) {
                player.dismountRidingEntity();
            }
                player.startRiding(this);
            }
        }
    private void makeHorseRear() {
        if (this.canPassengerSteer()) {
            this.jumpRearingCounter = 1;
            this.setRearing(true);
        }

    }

    @SideOnly(Side.CLIENT)
    public void setJumpPower(int jumpAmount) {
        if (jumpAmount < 0) {
            jumpAmount = 0;
        } else {
            this.allowStandSliding = true;
            this.makeHorseRear();
        }
        if (jumpAmount >= 90) {
            this.jumpPower = 1.0F;
        } else {
            this.jumpPower = (float)jumpAmount/90F;
        }
    }

    private void setRearing(boolean b) {
        jumpRearingCounter=1;
    }


    public double getHorseJumpStrength() {
        return this.getEntityAttribute(JUMP_STRENGTH).getAttributeValue();
    }
    public void travel(float x, float y, float z) {
        if (this.isBeingRidden()) {
            EntityLivingBase entitylivingbase = (EntityLivingBase)this.getControllingPassenger();
            getStepSound();
            this.rotationYaw = entitylivingbase.rotationYaw;
            this.prevRotationYaw = this.rotationYaw;
            this.rotationPitch = entitylivingbase.rotationPitch * 0.5F;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.renderYawOffset = this.rotationYaw;
            this.rotationYawHead = this.renderYawOffset;
            x = entitylivingbase.moveStrafing * 0.5F;
            z = entitylivingbase.moveForward;
            if (z <= 0.0F) {
                z *= 0.25F;
            }
            Strykae.LOGGER.info("ALBTRAUM:"+this.jumpPower+"|"+this.isAirBorne+"|"+this.onGround);
            if (this.jumpPower > 0.0F) {
                this.motionY += 0.6000000059604645D * (double)this.jumpPower;
                if (this.isPotionActive(MobEffects.JUMP_BOOST)) {
                    this.motionY += (double)((this.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F);
                }
                this.isAirBorne = true;
                if (z > 0.0F) {
                    float f = MathHelper.sin(this.rotationYaw * 0.017453292F);
                    float f1 = MathHelper.cos(this.rotationYaw * 0.017453292F);
                    this.motionX += (double)(-0.4F * f * this.jumpPower);
                    this.motionZ += (double)(0.4F * f1 * this.jumpPower);
                    this.playSound(SoundEvents.ENTITY_HORSE_JUMP, 0.4F, 1.0F);
                }
                this.jumpPower = 0.0F;
            }

            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;
            this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
            super.travel(x, y, z);

            if (this.onGround) {
                this.jumpPower = 0.0F;
                this.isAirBorne=false;
            }

            this.prevLimbSwingAmount = this.limbSwingAmount;
            double d1 = this.posX - this.prevPosX;
            double d0 = this.posZ - this.prevPosZ;
            float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
            if (f2 > 1.0F) {
                f2 = 1.0F;
            }

            this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
            this.limbSwing += this.limbSwingAmount;
        } else {
            this.jumpMovementFactor = 0.02F;
            super.travel(x, y, z);
        }

    }
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SKELETON_HORSE_AMBIENT;
    }
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SKELETON_HORSE_DEATH;
    }
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.ENTITY_SKELETON_HORSE_HURT;
    }
    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_SKELETON_STEP;
    }


    @Override
    public boolean canJump() {
        return true;
    }
    @Override
    public void handleStartJump(int i) {
        this.allowStandSliding = true;
        this.makeHorseRear();
        this.openHorseMouth();
    }

    @Override
    public void handleStopJump() {
    }

    @SideOnly(Side.CLIENT)
    public float getGrassEatingAmount(float f1) {
        return this.prevHeadLean + (this.headLean - this.prevHeadLean) * f1;
    }

    @SideOnly(Side.CLIENT)
    public float getRearingAmount(float f1) {
        return this.prevRearingAmount + (this.rearingAmount - this.prevRearingAmount) * f1;
    }

    @SideOnly(Side.CLIENT)
    public float getMouthOpennessAngle(float f1) {
        return this.prevMouthOpenness + (this.mouthOpenness - this.prevMouthOpenness) * f1;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.openMouthCounter > 0 && ++this.openMouthCounter > 30) {
            this.openMouthCounter = 0;
        }
        if (this.canPassengerSteer() && this.jumpRearingCounter > 0 && ++this.jumpRearingCounter > 20) {
            this.jumpRearingCounter = 0;
            this.setRearing(false);
        }

        this.prevHeadLean = this.headLean;
        this.headLean += (0.0F - this.headLean) * 0.4F - 0.05F;
        if (this.headLean < 0.0F) {
            this.headLean = 0.0F;
        }
        this.prevRearingAmount = this.rearingAmount;
        if (this.isRearing()) {
            this.headLean = 0.0F;
            this.prevHeadLean = this.headLean;
            this.rearingAmount += (1.0F - this.rearingAmount) * 0.4F + 0.05F;
            if (this.rearingAmount > 1.0F) {
                this.rearingAmount = 1.0F;
            }
        } else {
            this.allowStandSliding = false;
            this.rearingAmount += (0.8F * this.rearingAmount * this.rearingAmount * this.rearingAmount - this.rearingAmount) * 0.6F - 0.05F;
            if (this.rearingAmount < 0.0F) {
                this.rearingAmount = 0.0F;
            }
        }

        this.prevMouthOpenness = this.mouthOpenness;
        this.mouthOpenness += (0.0F - this.mouthOpenness) * 0.7F - 0.05F;
        if (this.mouthOpenness < 0.0F) {
            this.mouthOpenness = 0.0F;
        }


    }

    private boolean isRearing() {
        return this.rearingAmount>0.0F;
    }

    @Override
    protected boolean canBeRidden(Entity entity) {
        return true;
    }
    @Override
    public boolean canRiderInteract() {
        return true;
    }

    public void updatePassenger(Entity p_184232_1_) {
        super.updatePassenger(p_184232_1_);
        if (p_184232_1_ instanceof EntityLiving) {
            EntityLiving entityliving = (EntityLiving)p_184232_1_;
            this.renderYawOffset = entityliving.renderYawOffset;
        }

        if (this.prevRearingAmount > 0.0F) {
            float f3 = MathHelper.sin(this.renderYawOffset * 0.017453292F);
            float f = MathHelper.cos(this.renderYawOffset * 0.017453292F);
            float f1 = 0.7F * this.prevRearingAmount;
            float f2 = 0.15F * this.prevRearingAmount;
            p_184232_1_.setPosition(this.posX + (double)(f1 * f3), this.posY + this.getMountedYOffset() + p_184232_1_.getYOffset() + (double)f2, this.posZ - (double)(f1 * f));
            if (p_184232_1_ instanceof EntityLivingBase) {
                ((EntityLivingBase)p_184232_1_).renderYawOffset = this.renderYawOffset;
            }
        }

    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        if (!this.world.isRemote)
        {
            if (player.isSneaking())
            {
                for (Entity entity : this.getPassengers())
                {
                    if (!(entity instanceof EntityPlayer))
                    {
                        entity.dismountRidingEntity();
                        return true;
                    }
                }
            }
            else {
                mountTo(player);
            }
        }
        return true;
    }
    
    private void openHorseMouth() {
        if (!this.world.isRemote) {
            this.openMouthCounter = 1;
        }

    }

}
