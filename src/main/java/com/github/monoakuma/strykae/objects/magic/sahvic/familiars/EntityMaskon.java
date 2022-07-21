package com.github.monoakuma.strykae.objects.magic.sahvic.familiars;

import com.github.monoakuma.strykae.objects.magic.general.entities.AbstractFamiliar;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class EntityMaskon extends AbstractFamiliar {
    private int limit=0;
    private boolean isSummoning=false;
    private int summonTimer = 200;
    private final Random rand = new Random();
    public EntityMaskon(World world) {
        super(world);
    }
    @Override
    public float getEyeHeight() {
        return 1.9F;
    }
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.300037D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }
    @Override
    protected void initEntityAI()
    {
        super.initEntityAI();
        this.tasks.addTask(2, new AIEntityMaskonSummon(this));
    }

    public boolean isSummoning() {
        return isSummoning;
    }

    public void setLimit(int limit) {
        this.limit = Math.min(limit,4);
    }

    public int getLimit() {
        return limit;
    }

    public void onUpdate() {
        super.onUpdate();
        if (summonTimer>0) summonTimer--;
        else if (limit>0) isSummoning=true;
        else if (!expires) {
            limit=1; //really hope this isn't too powerful.
        }

    }
    public class AIEntityMaskonSummon extends EntityAIBase {
        protected int warmup=0;
        protected EntityMaskon maskon;
        @Override
        public boolean shouldExecute() {
            return isSummoning;
        }

        protected AIEntityMaskonSummon(EntityMaskon entity) {
            maskon=entity;
        }
        public boolean shouldContinueExecuting() {
            return maskon.getAttackTarget() != null && this.warmup < 40 && isSummoning;
        }

        public void updateTask() {
            ++this.warmup;
            spawnParticles(EnumParticleTypes.SPELL_WITCH);
            if (this.warmup >= 40) {
                this.castSpell();
                maskon.playSound(SoundEvents.ENTITY_ZOMBIE_INFECT, 1.0F, 1.0F);
            }

        }

        @Override
        public void startExecuting() {
            warmup=0;
        }
        public void castSpell(){
            EntityMaskon kin = new EntityMaskon(maskon.getEntityWorld());
            kin.setLimit(limit-1);
            int expiration = (!this.maskon.expires) ? 600 : this.maskon.getExpiration();
            kin.setExpiration(expiration);
            kin.setPosition(maskon.posX,maskon.posY,maskon.posZ);
            if (getOwner() instanceof EntityPlayer) kin.setOwner((EntityPlayer) Objects.requireNonNull(getOwner()));
            kin.setAttackTarget(maskon.getAttackTarget());
            maskon.getEntityWorld().spawnEntity(kin);
            isSummoning=false;
            maskon.summonTimer=200;
            maskon.limit--;
        }
    }
}