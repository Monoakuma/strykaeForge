package com.github.monoakuma.strykae.client;

import com.github.monoakuma.strykae.common.CommonProxy;
import static com.github.monoakuma.strykae.Strykae.MOD_ID;
import static com.github.monoakuma.strykae.init.ItemInit.ITEMS;

import com.github.monoakuma.strykae.init.EntityRenderInit;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = MOD_ID, value = Side.CLIENT)
public class ClientProxy extends CommonProxy {



    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {

        for  (Item item : ITEMS) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
    }

    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item,meta,new ModelResourceLocation(MOD_ID+":"+id,"inventory"));
    }

    @Override
    public void render() {}

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        EntityRenderInit.createRenders();
    }

}
