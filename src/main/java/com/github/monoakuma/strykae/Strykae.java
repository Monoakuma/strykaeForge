package com.github.monoakuma.strykae;

import com.github.monoakuma.strykae.common.CommonProxy;
import com.github.monoakuma.strykae.core.CasterCap;
import com.github.monoakuma.strykae.init.CommandInit;
import com.github.monoakuma.strykae.init.EntityInit;
import com.github.monoakuma.strykae.init.EntityRenderInit;
import com.github.monoakuma.strykae.objects.magic.SpellCasting;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Strykae.MOD_ID, name = Strykae.MOD_NAME, version = Strykae.VERSION, dependencies = "required-after:forge@14.23.5.2860;after:toughasnails", acceptedMinecraftVersions = "1.12.2")
public class Strykae {

    public static final String MOD_ID = "strykae";
    public static final String MOD_NAME = "Strykae";
    public static final String VERSION = "1.12.2-1.0_BETA";

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static Strykae INSTANCE;
    @SidedProxy(clientSide="com.github.monoakuma.strykae.client.ClientProxy",serverSide="com.github.monoakuma.strykae.common.CommonProxy")
    public static CommonProxy proxy;

    public Strykae () {}

    public static final CreativeTabs TAB = (new CreativeTabs("strykae.general") {
        @SideOnly(Side.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(com.github.monoakuma.strykae.init.ItemInit.HEART);
        }
    });


    public static Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static SpellCasting spellHandler = new SpellCasting();
    public static SimpleNetworkWrapper network;
    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("LOADING 0");
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("LOADING 1");
        proxy.preInit(event);
        LOGGER.info("LOADING 2");
        EntityInit.registerEntities();
        EntityRenderInit.createRenders();

    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        CommonProxy.init();
        LOGGER.info("LOADING 3");

    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LOGGER.info("LOADING 4");
    }

    @Mod.EventHandler
    public void serverInit(FMLServerStartingEvent event) {CommandInit.serverRegistries(event);}

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        LOGGER.info("Within the great swamp of Bou, Sah awakens...");
    }


    /**
     * This is a special class that listens to registry events, to allow creation of mod blocks and items at the proper time.
     *
    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {
        /**
         * Listen for the register event for creating custom items
         *
        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {
           /*
             event.getRegistry().register(new ItemBlock(Blocks.myBlock).setRegistryName(MOD_ID, "myBlock"));
             event.getRegistry().register(new MySpecialItem().setRegistryName(MOD_ID, "mySpecialItem"));
            *
        }

        /**
         * Listen for the register event for creating custom blocks
         *
        @SubscribeEvent
        public static void addBlocks(RegistryEvent.Register<Block> event) {
           /*
             event.getRegistry().register(new MySpecialBlock().setRegistryName(MOD_ID, "mySpecialBlock"));
            *
        }
    }
    /* EXAMPLE ITEM AND BLOCK - you probably want these in separate files
    public static class MySpecialItem extends Item {

    }

    public static class MySpecialBlock extends Block {

    }
    */
}
