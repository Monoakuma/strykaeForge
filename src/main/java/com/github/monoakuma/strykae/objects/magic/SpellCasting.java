package com.github.monoakuma.strykae.objects.magic;

import com.github.monoakuma.strykae.core.ICasterCap;
import com.github.monoakuma.strykae.objects.magic.galdic.familiars.EntityGureon;
import com.github.monoakuma.strykae.objects.magic.galdic.familiars.EntityVulfon;
import com.github.monoakuma.strykae.objects.magic.general.entities.EntitySpell;
import com.github.monoakuma.strykae.objects.magic.sahvic.familiars.EntityAlbtraum;
import com.github.monoakuma.strykae.objects.magic.sahvic.familiars.EntityIsaion;

import com.github.monoakuma.strykae.objects.magic.sahvic.familiars.EntityKaneon;
import com.github.monoakuma.strykae.objects.magic.sahvic.familiars.EntityMaskon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.*;

import static com.github.monoakuma.strykae.Strykae.LOGGER;
import static com.github.monoakuma.strykae.common.Config.SConfig.moonPower;
import static com.github.monoakuma.strykae.core.CasterCap.getCaster;
import static com.github.monoakuma.strykae.core.StrykaeCore.warmBody;
import static com.github.monoakuma.strykae.init.SoundInit.ENTITY_PLAYER_SPELLCAST;

public class SpellCasting {
    public SpellCasting() {
        LOGGER.info("Spellcasting Initiated");
    }

    public final List<PotionSpell> GaldicPotionFocuses = new ArrayList<PotionSpell>(Arrays.asList(
            new PotionSpell(7 , "cagl", Potion.getPotionFromResourceLocation("speed")), //HUMAN //DONE
            new PotionSpell(12, "laur", Potion.getPotionFromResourceLocation("strength")), //DONE
            new PotionSpell(24, "heil", Potion.getPotionFromResourceLocation("regeneration")), //DONE
            new PotionSpell(7 , "buft", Potion.getPotionFromResourceLocation("haste")), //DONE
            new PotionSpell(11, "kann", Potion.getPotionFromResourceLocation("jump_boost")), //DONE
            new PotionSpell(30, "tati", Potion.getPotionFromResourceLocation("resistance")), //DONE
            new PotionSpell(14, "rual", Potion.getPotionFromResourceLocation("weakness")), //DONE
            new PotionSpell(4 , "ocud", Potion.getPotionFromResourceLocation("glowing")) //DONE
    ));
    public final List<PotionSpell> SahvicPotionFocuses = new ArrayList<>(Arrays.asList(
            new PotionSpell(1 , "kaha", Potion.getPotionFromResourceLocation("glowing")), //STRYKAE //DONE
            new PotionSpell(13, "ufra", Potion.getPotionFromResourceLocation("blindness")) //DONE
    ));
    public final List<Spell> GaldicWorldFocuses = new ArrayList<>(Arrays.asList(
            new Spell(20, "ragn"), //explosion //DONE
            new Spell(24, "gure"), //Golem familiar //ENTITY FAMILIAR //DONE
            new Spell(24, "vulf"), //Wolf familiar //ENTITY FAMILIAR //DONE
            new Spell(20, "laek"), //cure plague //ENTITIES //DONE
            new Spell(8 , "sunn"), //heat //ENTITIES (WARM) OR IGNITE BLOCKS //DONE
            new Spell(30, "tabi") //lightning //ENTITY //DONE
    ));
    public final List<Spell> SahvicWorldFocuses = new ArrayList<>(Arrays.asList(
            new Spell(8 , "menn"), //cool ENTITIES (COLD) OR FREEZE BLOCKS //DONE
            new Spell(69, "isai"), //summon Isaion familiar //ENTITY FAMILIAR //DONE
            new Spell(20, "kane"), //skeleton familiar //ENTITY FAMILIAR //DONE
            new Spell(30, "taut"), //plague //ENTITIES //DONE
            new Spell(13, "trau"), //albtraum familiar //ENTITY FAMILIAR //DONE
            new Spell(22, "mask"), //zombie familiar //ENTITY FAMILIAR //DONE
            new Spell(12, "ksam") //take control of an undead and turn it into your familiar //ENTITIES FAMILIAR
    ));
    private final ArrayList<String> specifierNames = new ArrayList<>(Arrays.asList("ia","at","ti","um","on"));
    public final PotionSpell nullSpell = new PotionSpell(999,"null",Potion.getPotionFromResourceLocation("luck"));

    public long getDays(World world) {
        return(world.getWorldTime()/24000);
    }
    public int getMoonphase(World world) {
        return moonPower[(int) (getDays(world)%8)];
    }

    public String generateRandomValidSpell(boolean galdic) {
        Random rand = new Random();
        List<Spell> spellList = new ArrayList<>();
        if (galdic) {
            spellList.addAll(GaldicWorldFocuses);
            spellList.addAll(GaldicPotionFocuses);
        } else {
            spellList.addAll(SahvicWorldFocuses);
            spellList.addAll(SahvicPotionFocuses);
        }
        Spell randSpell = spellList.get(rand.nextInt(spellList.size()));
        String price = "1";
        int lowest = randSpell.getCost();
        int limit = (galdic) ? 100 : 70;
        for (int i=0;i<9;++i) {
            for (int j=0;j<9;++j) {
                for (int l=0;l<9;++l) {
                    int interpret = interpretSpellCost(String.format("%d%d%d",i,j,l));
                    if (interpret>=lowest&&interpret<=limit) price=String.format("%d%d%d",i,j,l);
                }
            }
        }
        return price+" "+randSpell.getName()+specifierNames.get(rand.nextInt(specifierNames.size()));

    }

    public void assignSpellNBT(ItemStack tool, String spell) {
        if (!tool.hasTagCompound()) {
            tool.setTagCompound(new NBTTagCompound());
        }
        tool.getTagCompound().setString("StrykaeSpell",spell);

    }
    public ArrayList<String> getSpellnameList() {
        ArrayList<String> names = new ArrayList<>();
        for (Spell spell : GaldicPotionFocuses) {
            names.add(spell.getName());
        }
        for (Spell spell : GaldicWorldFocuses) {
            names.add(spell.getName());
        }
        for (Spell spell : SahvicPotionFocuses) {
            names.add(spell.getName());
        }
        for (Spell spell : SahvicWorldFocuses) {
            names.add(spell.getName());
        }
        return names;
    }

    public Spell findWorldSpell(String spell) {
        for (Spell galdicSpell : GaldicWorldFocuses) {
            if (spell.equals(galdicSpell.getName())) {
                return galdicSpell;
            }
        }
        for (Spell sahvicSpell : SahvicWorldFocuses) {
            if (spell.equals(sahvicSpell.getName())) {
                return sahvicSpell;
            }
        }
        return nullSpell;
    }
    public PotionSpell findPotionSpell(String spell) {
        for (PotionSpell galdicSpell : GaldicPotionFocuses) {
            if (spell.equals(galdicSpell.getName())) {
                return galdicSpell;
            }
        }
        for (PotionSpell sahvicSpell : SahvicPotionFocuses) {
            if (spell.equals(sahvicSpell.getName())) {
                return sahvicSpell;
            }
        }
        return nullSpell;
    }
    public Spell findSpell(String spell) {
        Spell wspell = findWorldSpell(spell);
        Spell pspell = findPotionSpell(spell);
        if (wspell!=nullSpell) return wspell;
        else return pspell;
    }
    public boolean isGaldic(Spell spell) {
        boolean worldbool = (GaldicWorldFocuses.contains(spell));
        boolean potionbool = findPotionSpell(spell.getName()) != nullSpell && (GaldicPotionFocuses.contains((PotionSpell) spell)); //if simplified will cause nullPointer
        return worldbool||potionbool;
    }
    private String[] parseSpelltext(String spelltext) {
        String[] spellComp = spelltext.split(" ",2);
        return new String[]{spellComp[0],spellComp[1].substring(0,4),spellComp[1].substring(4,6)};
    }
    private int interpretSpellCost(String exaggeration) {
        int cost = 1;
        for (char i : exaggeration.toCharArray()) {
            cost=cost*((int)i-48);
        }
        return cost;
    }
    private int getSpellPower(ICasterCap casterCap, World world) {
        int power = 0;
        if (!casterCap.getIsHuman()) {
            power = getMoonphase(world);
        } else {
            power = Math.max(Math.min(4-casterCap.getLives(),5),0);
        }
        return power;
    }

    public boolean spellCast(ItemStack tool, EntityPlayer caster) {
        ICasterCap casterCap = getCaster(caster);
        if (casterCap==null) return false;
        if (!tool.hasTagCompound()) {
            tool.setTagCompound(new NBTTagCompound());
        }
        if (caster instanceof EntityPlayerMP) casterCap.syncPlayerCData(caster);
        else return false;
        String spellname = tool.getTagCompound().getString("StrykaeSpell");
        if (spellname.equals("")) return false;
        String[] spellSections = parseSpelltext(spellname);
        int spellCost = interpretSpellCost(spellSections[0]);
        String spellFocus = spellSections[1];
        String spellSpecifier = spellSections[2];
        Spell worldSpell = findWorldSpell(spellFocus);
        PotionSpell potionSpell = findPotionSpell(spellFocus);


        ArrayList<EntityLivingBase> targets = new ArrayList<>();
        if (!Objects.equals(spellSpecifier,"on")) {
            targets = getTargets(spellCost, spellSpecifier, caster, casterCap);
        }
        if ((casterCap.getIsHuman()&&isGaldic(worldSpell)) || (casterCap.getIsHuman()&&isGaldic(potionSpell)) || (!casterCap.getIsHuman()&&!isGaldic(worldSpell)) || (!casterCap.getIsHuman()&&!isGaldic(potionSpell))) {
            if ((tool.getMaxDamage()-tool.getItemDamage())>=spellCost && casterCap.getMana()>=spellCost) {
                if (Objects.equals(spellSpecifier, "on")) {
                    Vec3d look = caster.getLookVec();
                    EntitySpell projectile = new EntitySpell(caster.getEntityWorld(),caster,caster.posX+look.x*1.5D,0.9+caster.posY,caster.posZ+look.z*1.5D,look.x*0.03,look.y*0.03,look.z*0.03);
                    Spell projSpell = (worldSpell!=nullSpell) ? worldSpell : potionSpell;
                    LOGGER.info(worldSpell.getName()+" | "+potionSpell.getName()+" | "+caster.world.isRemote+" | ");
                    projectile.setSpellProfile(spellCost,projSpell,getSpellPower(casterCap, caster.getEntityWorld()),caster,casterCap);
                    projectile.shoot(caster,caster.rotationPitch,caster.rotationYaw,0.0f,2.85f,0.0f);
                    serverSummon(projectile);
                }
                boolean spellSuccess = (castWorld(spellCost,worldSpell,targets,caster,casterCap)||castPotion(spellCost,potionSpell,targets,caster,casterCap)||Objects.equals(spellSpecifier, "on"));
                if (spellSuccess) {
                    tool.damageItem(spellCost,caster);
                    caster.swingArm(EnumHand.MAIN_HAND);
                    caster.playSound(ENTITY_PLAYER_SPELLCAST,1.0F,1.0F);
                    if (tool.getMaxDamage()-tool.getItemDamage()<=0) tool.shrink(1);
                }
                casterCap.setMana(casterCap.getMana()-spellCost);
                if (caster instanceof EntityPlayerMP) casterCap.syncPlayerCData(caster);
                return spellSuccess;
            } else if (casterCap.getMana()>=spellCost) {
                spellCost=(tool.getMaxDamage()-tool.getItemDamage());
                if (Objects.equals(spellSpecifier, "on")) {
                    Vec3d look = caster.getLookVec();
                    EntitySpell projectile = new EntitySpell(caster.getEntityWorld(),caster,caster.posX+look.x*1.5D,0.9+caster.posY,caster.posZ+look.z*1.5D,look.x*0.03,look.y*0.03,look.z*0.03);
                    Spell projSpell = (worldSpell!=nullSpell) ? worldSpell : potionSpell;
                    projectile.setSpellProfile(spellCost,projSpell,getSpellPower(casterCap, caster.getEntityWorld()),caster,casterCap);
                    projectile.shoot(caster,caster.rotationPitch,caster.rotationYaw,0.0f,2.85f,0.0f);
                    serverSummon(projectile);
                }
                boolean spellSuccess = (castWorld(spellCost,worldSpell,targets,caster,casterCap)||castPotion(spellCost,potionSpell,targets,caster,casterCap)||Objects.equals(spellSpecifier, "on"));
                if (spellSuccess) {
                    tool.damageItem(spellCost,caster);
                    if (tool.getMaxDamage()-tool.getItemDamage()<=0) tool.shrink(1);
                }
                casterCap.setMana(casterCap.getMana()-spellCost);
                if (caster instanceof EntityPlayerMP) casterCap.syncPlayerCData(caster);
                return spellSuccess;
            }
        }
        return false;
    }
    public boolean spellCast(String spellname, EntityPlayer caster) { //Spell Casting for Admins/Gana.
        ICasterCap casterCap = getCaster(caster);
        String[] spellSections = parseSpelltext(spellname);
        int spellCost = interpretSpellCost(spellSections[0]);
        String spellFocus = spellSections[1];
        String spellSpecifier = spellSections[2];
        Spell worldSpell = findWorldSpell(spellFocus);
        PotionSpell potionSpell = findPotionSpell(spellFocus);
        ArrayList<EntityLivingBase> targets;
        if (!Objects.equals(spellSpecifier,"on")) {
            targets = getTargets(spellCost, spellSpecifier, caster, casterCap);
        } else {
            Vec3d look = caster.getLookVec();
            EntitySpell projectile = new EntitySpell(caster.getEntityWorld(),caster,caster.posX+look.x*1.5D,0.9+caster.posY,caster.posZ+look.z*1.5D,look.x*0.03,look.y*0.03,look.z*0.03);
            Spell projSpell = (worldSpell!=nullSpell) ? worldSpell : potionSpell;
            projectile.setSpellProfile(spellCost,projSpell,getSpellPower(casterCap, caster.getEntityWorld()),caster,casterCap);
            projectile.shoot(caster,caster.rotationPitch,caster.rotationYaw,0.0f,2.85f,0.0f);
            caster.getEntityWorld().spawnEntity(projectile);
            return true;
        }
        caster.playSound(ENTITY_PLAYER_SPELLCAST,1.0F,1.0F);
        return (castWorld(spellCost,worldSpell,targets,caster,casterCap,null)||castPotion(spellCost,potionSpell,targets,caster,casterCap));
    }
    public ArrayList<EntityLivingBase> getTargets(int cost, String specifier, EntityPlayer caster, ICasterCap casterCap) {
        ArrayList<EntityLivingBase> targets = new ArrayList<>();
        if (Objects.equals(specifier, "ia")) {
            targets.add(caster);
        } else if (Objects.equals(specifier,"at")) {
            ArrayList<Entity> found = getNearbyEntities(caster,(double) ((cost/6)+getSpellPower(casterCap, caster.getEntityWorld())/4));
            for (Entity e : found) {
                if (e instanceof EntityPlayer) {
                    ICasterCap tCap = getCaster(e);
                    if (tCap!=null) {
                        if (!tCap.getIsHuman()) {
                            targets.add((EntityLivingBase) e);
                        }
                    }
                }
            }
        } else if (Objects.equals(specifier,"ti")) {
            ArrayList<Entity> found = getNearbyEntities(caster,(double) ((cost/6)+getSpellPower(casterCap, caster.getEntityWorld())/4));
            for (Entity e : found) {
                if (e instanceof EntityPlayer) {
                    ICasterCap tCap = getCaster(e);
                    if (tCap!=null) {
                        if (tCap.getIsHuman()) {
                            targets.add((EntityLivingBase) e);
                        }
                    }
                }
            }
        } else if (Objects.equals(specifier,"um")) {
            ArrayList<Entity> found = getNearbyEntities(caster,(double) ((cost/6)+getSpellPower(casterCap, caster.getEntityWorld())/4));
            for (Entity e : found) {
                if (e instanceof EntityLivingBase) targets.add((EntityLivingBase) e);
            }
        }
        return targets;
    }

    public boolean castPotion(int price, PotionSpell spell, ArrayList<EntityLivingBase> targets, EntityPlayer caster, ICasterCap casterCap) {
        if (Objects.equals(spell.getName(), "null")) {return false;}
        int budget = price/spell.getCost();
        int spread = targets.size();
        if (budget<spread) {
            if (targets.isEmpty()) return false;
            Random rand = new Random();
            for (int i=spread;i>budget;i--) targets.remove(rand.nextInt(targets.size()));
        }
        if (budget==0) return false;
        int potionDuration = Math.max((price-spell.getCost())*getSpellPower(casterCap,caster.getEntityWorld())*10,20);
        for (EntityLivingBase target : targets) {
            target.addPotionEffect(new PotionEffect(spell.getEffect(),potionDuration,Math.min(getSpellPower(casterCap,caster.getEntityWorld()),2),false,false));
        }
        return targets.size() > 0;
    }

    private ArrayList<Entity> getNearbyEntities(EntityPlayer caster,double range) {
        ArrayList<Entity> output = new ArrayList<>();
        for (Entity e : caster.getEntityWorld().getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(caster.posX+range, caster.posY+range, caster.posZ+range, caster.posX-range, caster.posY-range, caster.posZ-range)))
        {
            if (Math.sqrt((e.posX-caster.posX)*(e.posX-caster.posX)+(e.posY-caster.posY)*(e.posY-caster.posY)+(e.posZ-caster.posZ)*(e.posZ-caster.posZ))<=range) { //this is just so the range is a sphere instead of a cube so that movement along 2 axes is not less efficient than 1 axis.
                output.add(e);
            }
        }
        return output;
    }
    private boolean serverSummon(Entity e ) {
        if (!e.getEntityWorld().isRemote) return e.world.spawnEntity(e);
        return false;
    }

    public boolean castWorld(int price, Spell spell, ArrayList<EntityLivingBase> targets, EntityPlayer caster, ICasterCap casterCap) {
        return castWorld(price,spell,targets,caster,casterCap,null);
    }
    public boolean castWorld(int price, Spell spell, ArrayList<EntityLivingBase> targets, EntityPlayer caster, ICasterCap casterCap, @Nullable BlockPos blockPos) {
        if (Objects.equals(spell.getName(), "null")) {return false;}
        World world = caster.getEntityWorld();
        Vec3d pos =(blockPos!=null) ? new Vec3d(blockPos.getX(),blockPos.getY(),blockPos.getZ()) : new Vec3d(caster.posX,caster.posY,caster.posZ) ;
        int budget = price/spell.getCost();
        int spread = targets.size();
        if (budget<spread) {
            if (targets.isEmpty()) return false;
            Random rand = new Random();
            for (int i=spread;i>budget;i--) targets.remove(rand.nextInt(targets.size()));
        }
        if (budget==0||world.isRemote) return false;
        switch (spell.getName()) {
            case "ragn":
                for (EntityLivingBase target : targets) {
                    world.createExplosion(new EntityLightningBolt(world,target.posX,target.posY,target.posZ,true),target.posX,target.posY,target.posZ,(float)Math.min((getSpellPower(casterCap,world))/spread/(price/2),3),true);
                }
                if (targets.isEmpty()&&blockPos!=null) world.createExplosion(new EntityLightningBolt(world, pos.x, pos.y, pos.z,true),pos.x, pos.y, pos.z,(float)Math.min((getSpellPower(casterCap,world))/spread/(price/2),3),true);
                return true;
            case "gure":
                for (EntityLivingBase target : targets) {
                    EntityGureon summon = new EntityGureon(world);
                    summon.setOwner(caster);
                    summon.setPosition(target.posX,target.posY,target.posZ);
                    summon.setExpiration(Math.min(price*getSpellPower(casterCap,world)*40,12000));
                    serverSummon(summon);
                }
                if (targets.isEmpty()&&blockPos!=null) {
                    EntityGureon summon = new EntityGureon(world);
                    summon.setOwner(caster);
                    summon.setPosition(pos.x,pos.y,pos.z);
                    summon.setExpiration(Math.min(price*getSpellPower(casterCap,world)*40,12000));
                    serverSummon(summon);
                }
                return true;
            case "vulf":
                for (EntityLivingBase target : targets) {
                    EntityVulfon summon = new EntityVulfon(world);
                    summon.setOwner(caster);
                    summon.setPosition(target.posX,target.posY,target.posZ);
                    summon.setExpiration(Math.min(price*getSpellPower(casterCap,world)*40,12000));
                    serverSummon(summon);
                }
                if (targets.isEmpty()&&blockPos!=null) {
                    EntityVulfon summon = new EntityVulfon(world);
                    summon.setOwner(caster);
                    summon.setPosition(pos.x,pos.y,pos.z);
                    summon.setExpiration(Math.min(price*getSpellPower(casterCap,world)*40,12000));
                    serverSummon(summon);
                }
                return true;
            case "laek":
                for (EntityLivingBase target : targets) {
                    ICasterCap targetCap = getCaster(target);
                    if (targetCap!=null) {
                        for (int i=0;i<=Math.max(budget+getSpellPower(casterCap, caster.getEntityWorld()),1);i++){
                            if (targetCap.getHygiene()<0) targetCap.raiseHygiene();
                        }
                    }
                }
                return !targets.isEmpty();
            case "sunn":
                for (EntityLivingBase target : targets) {
                    if (target instanceof EntityPlayer) warmBody((EntityPlayer)target,Math.min(price/8,10));
                    else if (price>spell.getCost()*2) {
                        target.setFire(Math.min(getSpellPower(casterCap,world)+price/16,10));
                    }
                }
                if (targets.isEmpty() && blockPos!=null) {
                    if (world.isAirBlock(blockPos)) {
                        world.setBlockState(blockPos, Blocks.FIRE.getDefaultState());
                    }
                }
                return true;
            case "tabi":
                for (EntityLivingBase target : targets) {
                    serverSummon(new EntityLightningBolt(world,target.posX,target.posY,target.posZ,false));
                }
                if (targets.isEmpty() && blockPos!=null) serverSummon(new EntityLightningBolt(world,pos.x,pos.y,pos.z,false));
                return true;
            case "menn":
                for (EntityLivingBase target : targets) {
                    if (target instanceof EntityPlayer) warmBody((EntityPlayer)target,-Math.min(price/8,10));
                    target.extinguish();
                    if (price>spell.getCost()*2) {
                        target.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("slowness"),getMoonphase(world)*25,0,false,false));
                    }
                }
                return !targets.isEmpty();
            case "isai":
                for (EntityLivingBase target : targets) {
                    EntityIsaion summon = new EntityIsaion(world);
                    summon.setOwner(caster);
                    summon.setPosition(target.posX,target.posY,target.posZ);
                    summon.setExpiration(Math.min(spell.getCost()*price*getSpellPower(casterCap,world)*40,12000));
                    if (target!=caster && target instanceof EntityPlayer) {
                        summon.setIsaionTarget((EntityPlayer) target);
                    }
                    serverSummon(summon);
                }
                if (targets.isEmpty()&&blockPos!=null) {
                    EntityIsaion summon = new EntityIsaion(world);
                    summon.setOwner(caster);
                    summon.setPosition(pos.x,pos.y,pos.z);
                    summon.setExpiration(Math.min(spell.getCost()*price*getSpellPower(casterCap,world)*40,12000));
                    serverSummon(summon);
                }
                return true;
            case "kane":
                for (EntityLivingBase target : targets) {
                    EntityKaneon summon = new EntityKaneon(world);
                    summon.setOwner(caster);
                    summon.setPosition(target.posX,target.posY,target.posZ);
                    summon.setExpiration(Math.min(spell.getCost()*price*getSpellPower(casterCap,world)*40,12000));
                    serverSummon(summon);
                }
                if (targets.isEmpty()&&blockPos!=null) {
                    EntityKaneon summon = new EntityKaneon(world);
                    summon.setOwner(caster);
                    summon.setPosition(pos.x,pos.y,pos.z);
                    summon.setExpiration(Math.min(spell.getCost()*price*getSpellPower(casterCap,world)*40,12000));
                    serverSummon(summon);
                }
                return true;
            case "taut":
                for (EntityLivingBase target : targets) {
                    ICasterCap targetCap = getCaster(target);
                    if (targetCap!=null) {
                        for (int i=0;i<=Math.max(budget+getSpellPower(casterCap, caster.getEntityWorld()),1);i++) {
                            targetCap.lowerHygiene();
                        }
                    }
                }
                return !targets.isEmpty();
            case "trau":
                for (EntityLivingBase target : targets) {
                    EntityAlbtraum summon = new EntityAlbtraum(world);
                    summon.setOwner(caster);
                    summon.setPosition(target.posX,target.posY,target.posZ);
                    summon.setExpiration(Math.min(spell.getCost()*price*getSpellPower(casterCap,world)*40,12000));
                    if (serverSummon(summon)&&target==caster) caster.startRiding(summon);
                }
                if (targets.isEmpty()&&blockPos!=null) {
                    EntityAlbtraum summon = new EntityAlbtraum(world);
                    summon.setOwner(caster);
                    summon.setPosition(pos.x,pos.y,pos.z);
                    summon.setExpiration(Math.min(spell.getCost()*price*getSpellPower(casterCap,world)*40,12000));
                    serverSummon(summon);
                }
                return true;
            case "mask":
                for (EntityLivingBase target : targets) {
                    EntityMaskon summon = new EntityMaskon(world);
                    summon.setOwner(caster);
                    summon.setLimit(budget-1);
                    summon.setPosition(target.posX,target.posY,target.posZ);
                    summon.setExpiration(Math.min(spell.getCost()*price*getSpellPower(casterCap,world)*40,12000));
                    serverSummon(summon);
                }
                if (targets.isEmpty()&&blockPos!=null) {
                    EntityMaskon summon = new EntityMaskon(world);
                    summon.setOwner(caster);
                    summon.setLimit(budget-1);
                    summon.setPosition(pos.x,pos.y,pos.z);
                    summon.setExpiration(Math.min(spell.getCost()*price*getSpellPower(casterCap,world)*40,12000));
                    serverSummon(summon);
                }
                return true;
            case "ksam":
                for (EntityLivingBase target : targets) {
                    if (target instanceof EntityZombie || target instanceof EntityMaskon) {
                        EntityMaskon summon = new EntityMaskon(world);
                        summon.setOwner(caster);
                        summon.setLimit(budget/spread-1);
                        summon.setPosition(target.posX,target.posY,target.posZ);
                        world.removeEntity(target);
                        serverSummon(summon);
                        summon.setExpiration(Math.min(spell.getCost()*price*getSpellPower(casterCap,world)*40,12000));
                    } else {
                        target.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("weakness"),getMoonphase(world)*25,0,false,false));
                    }
                }
                return !targets.isEmpty();
            default:
                return false;
        }
    }
}

