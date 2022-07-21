package com.github.monoakuma.strykae.objects.magic.general.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.registry.IThrowableEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.Objects;

import static com.github.monoakuma.strykae.Strykae.LOGGER;

public class AbstractSpell extends EntityArrow implements IThrowableEntity {
    private EntityLivingBase caster;

    public AbstractSpell(World world) {
        super(world);
        this.setSize(0.4096F, 0.4096F);
        this.setNoGravity(true);
        this.setDamage(0.0D);
    }

    public AbstractSpell(World world, EntityLivingBase sender, double x, double y, double z, double xa, double ya, double za) {
        super(world,sender);
        this.setSize(0.4096F, 0.4096F);
        this.setNoGravity(true);
        this.setLocationAndAngles(sender.posX, sender.posY, sender.posZ, sender.rotationYaw, sender.rotationPitch);
        this.setPosition(x,y,z);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public void readEntityFromNBT(@Nonnull NBTTagCompound nbtTagCompound) {
    }
    @Override
    public void writeEntityToNBT(@Nonnull NBTTagCompound nbtTagCompound) {
    }

    @Nonnull
    @Override
    protected ItemStack getArrowStack() {
        return ItemStack.EMPTY;
    }

    @Override
    public void onUpdate() {
        if (this.world.isBlockLoaded(new BlockPos(this))) {
           super.onUpdate();
            float f = this.getMotionFactor();
            if (this.isInWater()||this.inGround) {
                this.setDead();
            }
            this.world.spawnParticle(EnumParticleTypes.SPELL_WITCH, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
        } else {
            this.setDead();
        }

    }

    protected float getMotionFactor() {
        return 0.95F;
    }

    @Override
    protected void onHit(@Nonnull RayTraceResult rayTraceResult) {
    }
    public ArrayList<EntityLivingBase> getHitTargets() {
        return new ArrayList<>(this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.posX-2.0D,this.posY-2.0D,this.posZ-2.0D,this.posX+2.0D,this.posY+2.0D,this.posZ+2.0D)));
    }

    public float getBrightness() {
        return 1.0F;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return 15728880;
    }

    public void shoot(Entity hitEntity, float pitch, float yaw, float f3, float velocity, float acceleration) {
        float x = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float y = -MathHelper.sin(pitch * 0.017453292F);
        float z = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        this.shoot((double)x, (double)y, (double)z, velocity, acceleration);
        this.motionX += hitEntity.motionX;
        this.motionZ += hitEntity.motionZ;
        if (!hitEntity.onGround) {
            this.motionY += hitEntity.motionY;
        }

    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float acceleration) {
        LOGGER.info("shot");
        float f = MathHelper.sqrt(x * x + y * y + z * z);
        x /= (double)f;
        y /= (double)f;
        z /= (double)f;
        x += (double)acceleration;
        y += (double)acceleration;
        z += (double)acceleration;
        x *= (double)velocity;
        y *= (double)velocity;
        z *= (double)velocity;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
    }

    @Override
    public Entity getThrower() {
        return caster;
    }

    @Override
    public void setThrower(Entity entity) {
        if (entity instanceof EntityLivingBase) this.caster=(EntityLivingBase) entity;
    }
}
