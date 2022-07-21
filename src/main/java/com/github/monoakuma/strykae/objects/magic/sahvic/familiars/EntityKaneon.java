package com.github.monoakuma.strykae.objects.magic.sahvic.familiars;

import com.github.monoakuma.strykae.objects.magic.IFamiliar;
import com.github.monoakuma.strykae.objects.magic.general.entities.AbstractFamiliar;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

import static com.github.monoakuma.strykae.init.ItemInit.ITEMS;
import static com.github.monoakuma.strykae.init.SoundInit.*;

public class EntityKaneon extends AbstractFamiliar {
    public EntityKaneon(World world) {
        super(world);
    }
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(19.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.33D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.3D);
    }

    public boolean attackEntityAsMob(@Nonnull Entity target) {
        if (!super.attackEntityAsMob(target)) {
            return false;
        } else {
            if (target instanceof EntityLivingBase) {
                ((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.WITHER, 100));
                ((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100));
            }

            return true;
        }
    }

    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {return ENTITY_KANEON_HURT;}
    protected SoundEvent getDeathSound() {return ENTITY_KANEON_DEATH;}
}
