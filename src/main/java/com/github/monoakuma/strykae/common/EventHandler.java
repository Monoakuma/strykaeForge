package com.github.monoakuma.strykae.common;

import com.github.monoakuma.strykae.core.CasterCap;
import com.github.monoakuma.strykae.core.ICasterCap;
import com.github.monoakuma.strykae.objects.items.HeartItem;
import com.github.monoakuma.strykae.objects.magic.general.entities.AbstractFamiliar;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;
import java.util.Random;

import static com.github.monoakuma.strykae.Strykae.*;
import static com.github.monoakuma.strykae.common.Config.SConfig.healthyFoods;
import static com.github.monoakuma.strykae.common.Config.SConfig.unhealthyFoods;
import static com.github.monoakuma.strykae.core.CasterCap.CDATA_CAPABILITY;
import static com.github.monoakuma.strykae.core.CasterCap.getCaster;
import static com.github.monoakuma.strykae.core.StrykaeCore.getMoonphase;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class EventHandler {
    private static final ResourceLocation caster_data = new ResourceLocation(MOD_ID,"caster_data");
    @SubscribeEvent
    public static void attachCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(caster_data, new CasterCap.CasterProvider());
        }
    }
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void playerLogin(PlayerEvent.PlayerLoggedInEvent e)
    {
        ICasterCap casterCap = getCaster(e.player);
        if (casterCap!=null)
        {
            e.player.sendMessage(new TextComponentString(String.format("You wake up. You have %d mana",casterCap.getMana())));
        } else {e.player.sendMessage(new TextComponentString("You wake up."));}


    }
    @SubscribeEvent
    public void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event)
    {
        Capability<ICasterCap> capability = CDATA_CAPABILITY;
        Capability.IStorage<ICasterCap> storage = capability.getStorage();
        if (event.getOriginal().hasCapability(capability, null) && event.getEntityPlayer().hasCapability(capability, null))
        {
            NBTBase nbt = storage.writeNBT(capability, event.getOriginal().getCapability(capability, null), null);
            storage.readNBT(capability, event.getEntityPlayer().getCapability(capability, null), null, nbt);
        }

    }
    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        ICasterCap casterCap = getCaster(event.player);
        if (casterCap.getLives()<1) {
            event.player.setGameType(GameType.SPECTATOR);
        }
    }
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event)
    {
        if (event.getEntity() instanceof AbstractFamiliar) {
            AbstractFamiliar familiar = (AbstractFamiliar) event.getEntity();
            if (familiar.getExpiration()<=0 && familiar.expires && familiar.world.getWorldTime()%20==0) {
                familiar.attackEntityFrom(DamageSource.GENERIC,familiar.getMaxHealth()+3.18f);
            }
        }
    }


    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event)
    {
        if (event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            ICasterCap casterCap = getCaster(player);
            World world = player.getEntityWorld();
            try{
                ItemStack itemStack = new ItemStack(HeartItem.getByNameOrId(MOD_ID+":heart"),1);
                if (!itemStack.hasTagCompound()) {
                    itemStack.setTagCompound(new NBTTagCompound());
                }
                itemStack.getTagCompound().setUniqueId("HeartOwner", player.getUniqueID());
                casterCap.setLives(casterCap.getLives()-1);
                world.spawnEntity(new EntityItem(world,player.posX,player.posY,player.posZ,itemStack));
            } catch (NullPointerException e) {
                LOGGER.error(e);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        long ticks = event.player.world.getWorldTime();
        if (ticks%40==0) {
            ICasterCap casterCap = getCaster(event.player);
            if (casterCap.getIsHuman() && event.player.getEntityWorld().getWorldTime()%24000<12000 && casterCap.getMana()<casterCap.getManaCap() && event.side==Side.SERVER) {casterCap.regenMana();event.player.sendStatusMessage(new TextComponentString(String.format("Mana: %d",casterCap.getMana())),true);}
            else if (!casterCap.getIsHuman() && event.player.getEntityWorld().getWorldTime()%24000>12000 && getMoonphase(event.player.world)!=0 && casterCap.getMana()<casterCap.getManaCap() && event.side==Side.SERVER) {casterCap.regenMana();event.player.sendStatusMessage(new TextComponentString(String.format("Mana: %d",casterCap.getMana())),true);}

            if (casterCap.getHygiene()<-12 && (casterCap.getIsHuman()&& casterCap.getLives()<=3) && getMoonphase(event.player.world)!=0) {
                event.player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("hunger"),getMoonphase(event.player.world)*20,(int)Math.max(Math.min(getMoonphase(event.player.world)/4,2),0),false,false));
                if (casterCap.getHygiene()<-18) {
                    event.player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("weakness"),getMoonphase(event.player.world)*20,(int)Math.max(Math.min(getMoonphase(event.player.world)/4,2),0),false,false));
                }
                if ( (-new Random().nextInt(30)+12)>casterCap.getHygiene()&&ticks%120==0) {
                    casterCap.infectionEvent(event.player);
                    event.player.world.playSound(event.player, event.player.posX, event.player.posY, event.player.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS,1.0f,0.666f);
                }
            } else if (casterCap.getHygiene()>12 && (casterCap.getIsHuman())) {
                event.player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("haste"),40,0,false,false));
                if (casterCap.getHygiene()>18) {
                    event.player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("jump_boost"),40,0,false,false));
                }
            }
            if (!casterCap.getIsHuman() && event.player.getEntityWorld().getWorldTime()%24000>12000) {
                if (getMoonphase(event.player.world)==0) {
                    casterCap.setMana(0);
                }
                if (casterCap.getLives()>3) {
                    event.player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("night_vision"),240,0,false,false));
                    if (casterCap.getLives()>4) event.player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("strength"),120,0,false,false));
                    if (event.player.world.isRainingAt(new BlockPos(event.player))) event.player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("invisibility"),120,0,false,false));
                }
            }
            casterCap.syncPlayerCData(event.player);
        }
    }
    @SubscribeEvent
    public void playerEat(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity()instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)event.getEntity();
            ICasterCap casterCap = getCaster(player);
            if (casterCap.getIsHuman()) {
                for (String healthyFood : healthyFoods) {
                    if (Objects.equals(event.getItem().getItem().getTranslationKey(), healthyFood)) {
                        casterCap.raiseHygiene();
                    }
                }
                for (String unhealthyFood : unhealthyFoods) {
                    if (Objects.equals(event.getItem().getItem().getTranslationKey(), unhealthyFood)) {
                        casterCap.lowerHygiene();
                    }
                }
                casterCap.syncPlayerCData(player);
            }
        }
    }
}
