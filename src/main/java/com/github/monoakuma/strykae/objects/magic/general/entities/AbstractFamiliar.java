package com.github.monoakuma.strykae.objects.magic.general.entities;

import com.github.monoakuma.strykae.core.StrykaeCore;
import com.github.monoakuma.strykae.objects.items.HeartItem;
import com.github.monoakuma.strykae.objects.magic.IFamiliar;
import com.google.common.base.Optional;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class AbstractFamiliar extends EntityTameable implements IFamiliar {
    private int expiration;
    public boolean expires;
    private boolean HOSTILE;
    public AbstractFamiliar(World world) {
        super(world);
        this.setSize(0.9F,2.0F);
        expires=false;
        HOSTILE=true;
    }
    protected void entityInit() {
        super.entityInit();
    }
    public void gainLife() {
        this.expires=false;
        this.addPotionEffect(new PotionEffect(MobEffects.REGENERATION)); //Permanent Familiars regen health.
        this.enablePersistence();
        this.setCustomNameTag(StrykaeCore.getGenericName());
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue()*2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(8.0D);
    }
    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack holding = player.getHeldItem(hand);
        if (!holding.isEmpty()) {
            if (holding.getItem()instanceof HeartItem && player.isSneaking() && !this.expires) {
                this.gainLife();
                holding.shrink(1);
            }
        }
        return super.processInteract(player, hand);
    }
    public void setExpiration(int expiration) {
        this.expiration = expiration;
        this.expires=true;
    }
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (expiration>-1)--expiration; //WORKS!!!
    }
    @Override
    protected boolean canDropLoot() {
        return false;
    }
    @Override
    public boolean attackEntityAsMob(@Nonnull Entity attacked) {
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        int i = 0;
        if (attacked instanceof EntityLivingBase) {
            f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase)attacked).getCreatureAttribute());
            i += EnchantmentHelper.getKnockbackModifier(this);
        }
        boolean flag = attacked.attackEntityFrom(DamageSource.causeMobDamage(this), f);
        if (flag) {
            if (i > 0) {
                ((EntityLivingBase)attacked).knockBack(this, (float)i * 0.5F, (double) MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);
            if (j > 0) {
                attacked.setFire(j * 4);
            }

            if (attacked instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer)attacked;
                ItemStack itemstack = this.getHeldItemMainhand();
                ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;
                if (!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.getItem().canDisableShield(itemstack, itemstack1, entityplayer, this) && itemstack1.getItem().isShield(itemstack1, entityplayer)) {
                    float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;
                    if (this.rand.nextFloat() < f1) {
                        entityplayer.getCooldownTracker().setCooldown(itemstack1.getItem(), 100);
                        this.world.setEntityState(entityplayer, (byte)30);
                    }
                }
            }

            this.applyEnchantments(this, attacked);
        }
        return flag;
    }
    @Override
    public void setOwner(EntityPlayer owner) {
        setOwnerId(owner.getUniqueID());
        setTamedBy(owner);
        HOSTILE=false;
    }

    @Override
    public boolean shouldAttackEntity(@Nonnull EntityLivingBase attackingEntity,@Nonnull EntityLivingBase attackedEntity) {
        if (attackingEntity instanceof AbstractFamiliar) {
            AbstractFamiliar familiar = (AbstractFamiliar)attackingEntity;
            if (attackedEntity instanceof AbstractFamiliar) {
                AbstractFamiliar other_familiar = (AbstractFamiliar) attackedEntity;
                if (familiar.getOwner()==other_familiar.getOwner()) {
                    return false;
                }
            }
            if (familiar.getOwner() == attackedEntity) {
                return false;
            }
        }
        return super.shouldAttackEntity(attackingEntity,attackedEntity);
    }
    @Override
    public void setOwnerId(UUID ownerUUID) {
        if (OWNER_UNIQUE_ID != null) {
            this.dataManager.set(OWNER_UNIQUE_ID, Optional.fromNullable(ownerUUID));
        }
    }
    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0,new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIFamiliarFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, true));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIFamiliarHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(4, new EntityAIFamiliarHostileTarget(this, true));

    }
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    }
    @SideOnly(Side.CLIENT)
    protected void spawnParticles(EnumParticleTypes particle) {
        for(int i = 0; i < 5; ++i) {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            world.spawnParticle(particle, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2, new int[0]);
        }
    }
    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable entityAgeable) {
        return null;
    }
    public int getExpiration() {
        return this.expiration;
    }
    public void setHostile(boolean hostile) {
        this.HOSTILE=hostile;
    }

    public boolean getHostile() {
        return this.HOSTILE;
    }

    public static class EntityAIFamiliarHurtByTarget extends EntityAITarget {
        private final boolean entityCallsForHelp;
        private int revengeTimerOld;
        private final Class<?>[] excludedReinforcementTypes;

        public EntityAIFamiliarHurtByTarget(EntityCreature familiar, boolean reinforcements, Class<?>... p_i45885_3_) {
            super(familiar, true);
            this.entityCallsForHelp = reinforcements;
            this.excludedReinforcementTypes = p_i45885_3_;
            this.setMutexBits(1);
        }

        public boolean shouldExecute() {
            int lvt_1_1_ = this.taskOwner.getRevengeTimer();
            EntityLivingBase lvt_2_1_ = this.taskOwner.getRevengeTarget();
            return lvt_1_1_ != this.revengeTimerOld && lvt_2_1_ != null && this.isSuitableTarget(lvt_2_1_, false);
        }

        public void startExecuting() {
            this.taskOwner.setAttackTarget(this.taskOwner.getRevengeTarget());
            this.target = this.taskOwner.getAttackTarget();
            this.revengeTimerOld = this.taskOwner.getRevengeTimer();
            this.unseenMemoryTicks = 300;
            if (this.entityCallsForHelp) {
                this.alertOthers();
            }

            super.startExecuting();
        }

        protected void alertOthers() {
            double distance = this.getTargetDistance();
            List<EntityCreature> creatureList = this.taskOwner.world.getEntitiesWithinAABB(this.taskOwner.getClass(), (new AxisAlignedBB(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0D, this.taskOwner.posY + 1.0D, this.taskOwner.posZ + 1.0D)).grow(distance, 10.0D, distance));
            Iterator var4 = creatureList.iterator();

            while(true) {
                EntityCreature reinforcement;
                do {
                    do {
                        do {
                            do {
                                if (!var4.hasNext()) {
                                    return;
                                }

                                reinforcement = (EntityCreature)var4.next();
                            } while(this.taskOwner == reinforcement);
                        } while(reinforcement.getAttackTarget() != null);
                    } while(this.taskOwner instanceof EntityTameable && ((EntityTameable)this.taskOwner).getOwner() != ((EntityTameable)reinforcement).getOwner());
                } while(reinforcement.isOnSameTeam(this.taskOwner.getRevengeTarget()));

                boolean lvt_6_1_ = false;
                Class[] var7 = this.excludedReinforcementTypes;
                int var8 = var7.length;

                for(int var9 = 0; var9 < var8; ++var9) {
                    Class<?> lvt_10_1_ = var7[var9];
                    if (reinforcement.getClass() == lvt_10_1_) {
                        lvt_6_1_ = true;
                        break;
                    }
                }

                if (!lvt_6_1_) {
                    this.setEntityAttackTarget(reinforcement, this.taskOwner.getRevengeTarget());
                }
            }
        }

        protected void setEntityAttackTarget(EntityCreature familiar, EntityLivingBase target) {
            if (familiar instanceof AbstractFamiliar) {
                if (((AbstractFamiliar) familiar).getOwner()!=target) familiar.setAttackTarget(target);
            }
            familiar.setAttackTarget(target);
        }
    }
    public static class EntityAIFamiliarHostileTarget extends EntityAITarget {
        private final Sorter sorter;
        private AbstractFamiliar familiar;
        public EntityAIFamiliarHostileTarget(AbstractFamiliar familiar, boolean requireSight) {
            super(familiar, requireSight);
            this.sorter= new Sorter(familiar);
            this.familiar=familiar;
        }
        @Override
        public boolean shouldExecute() {
            List<EntityLivingBase> targetables = this.taskOwner.world.getEntitiesWithinAABB(EntityLivingBase.class, this.taskOwner.getEntityBoundingBox().grow(16.0D));
            targetables.remove(this.familiar);
            List<EntityLivingBase> removals = new ArrayList<>();
            for (int i=0;i<targetables.size();i++) {
                EntityLivingBase e = targetables.get(i);
                if (e instanceof EntityPlayer) {
                    if (((EntityPlayer)e).isSpectator()||((EntityPlayer)e).isCreative()) {
                        removals.add(e);
                    }
                }
                if (e instanceof AbstractFamiliar) {
                    if (((AbstractFamiliar)e).HOSTILE) {
                        removals.add(e);
                    }
                }
            }
            for (EntityLivingBase e : removals) {
                targetables.remove(e);
            }
            if (targetables.isEmpty() || !this.familiar.HOSTILE) {
                return false;
            } else {
                Collections.sort(targetables,this.sorter);
                this.target=targetables.get(0);
                return true;
            }
        }
        public void startExecuting() {
            this.familiar.setAttackTarget(this.target);
            super.startExecuting();
        }
        public static class Sorter implements Comparator<Entity> {
            private final Entity entity;
            public Sorter(Entity e) {
                this.entity = e;
            }
            public int compare(Entity p_compare_1_, Entity p_compare_2_) {
                double dist0 = this.entity.getDistanceSq(p_compare_1_);
                double dist1 = this.entity.getDistanceSq(p_compare_2_);
                if (dist0 < dist1) {
                    return -1;
                } else {
                    return dist0 > dist1 ? 1 : 0;
                }
            }
        }
    }
    public class EntityAIFamiliarFollowOwner extends EntityAIFollowOwner {
        private final EntityTameable tameable;
        private EntityLivingBase owner;
        World world;
        private final double followSpeed;
        private final PathNavigate petPathfinder;
        private int timeToRecalcPath;
        float maxDist;
        float minDist;

        public EntityAIFamiliarFollowOwner(EntityTameable familiar, double followSpeed, float minimumDistance, float maximumDistance) {
            super(familiar,followSpeed,minimumDistance,maximumDistance);
            this.tameable = familiar;
            this.world = familiar.world;
            this.followSpeed = followSpeed;
            this.petPathfinder = familiar.getNavigator();
            this.minDist = minimumDistance;
            this.maxDist = maximumDistance;
            this.setMutexBits(3);
            if (!(familiar.getNavigator() instanceof PathNavigateGround) && !(familiar.getNavigator() instanceof PathNavigateFlying)) {
                throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
            }
        }

        @Override
        public boolean shouldExecute() {
            super.shouldExecute();
            EntityLivingBase master = this.tameable.getOwner();
            if (master == null) {
                return false;
            } else if (master instanceof EntityPlayer && ((EntityPlayer)master).isSpectator()) {
                return false;
            } else if (this.tameable.isDead) {
                return false;
            } else if (this.tameable.getDistanceSq(master) < (double)(this.minDist * this.minDist)) {
                return false;
            } else {
                this.owner = master;
                return true;
            }
        }

        @Override
        public void updateTask() {
            if (!this.tameable.isSitting()) {
                if (--this.timeToRecalcPath <= 0) {
                    this.timeToRecalcPath = 10;
                    if (!this.petPathfinder.tryMoveToEntityLiving(this.owner, this.followSpeed) && !expires) {
                        if (!this.tameable.getLeashed() && !this.tameable.isRiding()) {
                            if (!(this.tameable.getDistanceSq(this.owner) < 144.0D)) {
                                int posX = MathHelper.floor(this.owner.posX) - 2;
                                int posZ = MathHelper.floor(this.owner.posZ) - 2;
                                int posY = MathHelper.floor(this.owner.getEntityBoundingBox().minY);

                                for(int i = 0; i <= 4; ++i) {
                                    for(int j = 0; j <= 4; ++j) {
                                        if ((i < 1 || j < 1 || i > 3 || j > 3) && this.isTeleportFriendlyBlock(posX, posZ, posY, i, j)) {
                                            this.tameable.setLocationAndAngles((double)((float)(posX + i) + 0.5F), (double)posY, (double)((float)(posZ + j) + 0.5F), this.tameable.rotationYaw, this.tameable.rotationPitch);
                                            this.petPathfinder.clearPath();
                                            return;
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

}
