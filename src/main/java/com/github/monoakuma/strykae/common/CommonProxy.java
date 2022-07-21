package com.github.monoakuma.strykae.common;

import com.github.monoakuma.strykae.Strykae;
import com.github.monoakuma.strykae.core.CasterCap;
import com.github.monoakuma.strykae.core.ICasterCap;
import com.github.monoakuma.strykae.network.messages.CasterCapSyncMessage;
import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;

import static com.github.monoakuma.strykae.Strykae.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, value = Side.SERVER)
public class CommonProxy
{

    public void render() {}
    public void preInit(FMLPreInitializationEvent e) {
        Strykae.network = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);
        Strykae.network.registerMessage(CasterCapSyncMessage.class,CasterCapSyncMessage.class,0, Side.CLIENT);
    }

    public static void init() {
        CapabilityManager.INSTANCE.register(ICasterCap.class,new CasterCap.CasterStorage(), CasterCap.CasterImp::new);
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(Strykae.INSTANCE,new GuiHandler());

    }

    public void postInit(FMLPostInitializationEvent e) {}

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
    }

}
