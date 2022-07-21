package com.github.monoakuma.strykae.objects.magic.general.entities;

import com.github.monoakuma.strykae.Strykae;
import com.github.monoakuma.strykae.core.CasterCap;
import com.github.monoakuma.strykae.core.ICasterCap;
import com.github.monoakuma.strykae.objects.magic.PotionSpell;
import com.github.monoakuma.strykae.objects.magic.Spell;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

import static com.github.monoakuma.strykae.Strykae.spellHandler;
import static com.github.monoakuma.strykae.init.SoundInit.ENTITY_PLAYER_SPELLCAST;

public class EntitySpell extends AbstractSpell {
    private Spell spell = new Spell(999,"null");
    private int cost = 0;
    private int spellpower=0;
    private EntityPlayer caster;
    private ICasterCap casterCap;
    public EntitySpell(World world) {super(world);}
    public EntitySpell(World world, EntityLivingBase sender, double x, double y, double z,double xa,double ya,double za) {super(world,sender, x, y, z,xa,ya,za);}
    public void setSpellProfile(int cost, Spell spell,int spellpower, EntityPlayer caster, ICasterCap castercap) {
        this.cost=cost;
        this.spell=spell;
        this.spellpower=spellpower;
        this.caster=caster;
        this.casterCap=castercap;
    }
    public Spell getSpell() {
        return this.spell;
    }
    @Override
    protected void onHit(@Nonnull RayTraceResult rayTraceResult) {
        if (!this.world.isRemote && this.caster != null) { //if caster is not null, then we have already set up a SpellProfile.
            if (spellHandler.findWorldSpell(spell.getName())!=spellHandler.nullSpell) {
                spellHandler.castWorld(this.cost,this.spell,getHitTargets(),this.caster,this.casterCap,this.getPosition());
            }
            else if (spell instanceof PotionSpell) {
                spellHandler.castPotion(this.cost, (PotionSpell) this.spell,getHitTargets(),this.caster,this.casterCap);
                EntityAreaEffectCloud zone = new EntityAreaEffectCloud(this.world);
                zone.addEffect(new PotionEffect(((PotionSpell)spell).getEffect(),Math.max((cost-spell.getCost()*spell.getCost()/9)*spellpower*10,10),Math.min(spellpower,2),false,false));
                zone.setParticle(EnumParticleTypes.SPELL_WITCH);
                zone.setRadius(2.0F);
                zone.setDuration(15);
                zone.setPosition(this.posX,this.posY,this.posZ);
                this.world.spawnEntity(zone);
            }
            world.spawnParticle(EnumParticleTypes.SPELL_WITCH,this.posX,this.posY,this.posZ,0.5D,0.5D,0.5D, 1);
            this.playSound(ENTITY_PLAYER_SPELLCAST,1.0f,0.9f);
            setDead();
        }
    }
}