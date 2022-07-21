package com.github.monoakuma.strykae.init;

import com.github.monoakuma.strykae.objects.blocks.SigilTableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.github.monoakuma.strykae.Strykae.MOD_ID;
import static com.github.monoakuma.strykae.Strykae.TAB;
import static com.github.monoakuma.strykae.init.ItemInit.ITEMS;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class BlockInit {
    public static final Set<Block> BLOCKS = new HashSet<>();
    public static Block SIGIL_TABLE = create("sigil_table", new SigilTableBlock());

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        for (Block block : BLOCKS) {
            event.getRegistry().register(block);
        }
    }
    static Block create(String name, Block block) {
        block.setRegistryName(new ResourceLocation(MOD_ID, name));
        block.setTranslationKey(MOD_ID + "." + name);
        BLOCKS.add(block);
        return block;
    }
}
