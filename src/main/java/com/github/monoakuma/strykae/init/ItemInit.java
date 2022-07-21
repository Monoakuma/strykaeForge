package com.github.monoakuma.strykae.init;

import com.github.monoakuma.strykae.objects.items.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;
import java.util.Set;

import static com.github.monoakuma.strykae.Strykae.MOD_ID;
import static com.github.monoakuma.strykae.init.BlockInit.SIGIL_TABLE;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class ItemInit {
    public static final Set<Item> ITEMS = new HashSet<>();

    public static final Item SIGIL_TABLET = create("sigil_tablet", new SigilItem());
    //public static final Item SAHVIC_SIGIL = create("sahvic_sigil", new SigilItem());
    public static final Item HEART = create("heart", new HeartItem());
    public static final Item KRIS = create("kris", new KrisItem());
    public static final Item SIGIL_TABLE_ITEM = create("sigil_table", new ItemBlock(SIGIL_TABLE));

    public static final Item IRON_SPELLSWORD = create("iron_spellsword", new SpellSword(Item.ToolMaterial.IRON));
    public static final Item IRON_SPELLAXE = create("iron_spellaxe", new SpellAxe(Item.ToolMaterial.IRON));
    public static final Item IRON_SPELLHOE = create("iron_spellhoe", new SpellHoe(Item.ToolMaterial.IRON));
    public static final Item IRON_SPELLPICKAXE = create("iron_spellpickaxe", new SpellPickaxe(Item.ToolMaterial.IRON));
    public static final Item IRON_SPELLSHOVEL = create("iron_spellshovel", new SpellShovel(Item.ToolMaterial.IRON));

    public static final Item GOLD_SPELLSWORD = create("gold_spellsword", new SpellSword(Item.ToolMaterial.GOLD));
    public static final Item GOLD_SPELLAXE = create("gold_spellaxe", new SpellAxe(Item.ToolMaterial.GOLD));
    public static final Item GOLD_SPELLHOE = create("gold_spellhoe", new SpellHoe(Item.ToolMaterial.GOLD));
    public static final Item GOLD_SPELLPICKAXE = create("gold_spellpickaxe", new SpellPickaxe(Item.ToolMaterial.GOLD));
    public static final Item GOLD_SPELLSHOVEL = create("gold_spellshovel", new SpellShovel(Item.ToolMaterial.GOLD));

    public static final Item DIAMOND_SPELLSWORD = create("diamond_spellsword", new SpellSword(Item.ToolMaterial.DIAMOND));
    public static final Item DIAMOND_SPELLAXE = create("diamond_spellaxe", new SpellAxe(Item.ToolMaterial.DIAMOND));
    public static final Item DIAMOND_SPELLHOE = create("diamond_spellhoe", new SpellHoe(Item.ToolMaterial.DIAMOND));
    public static final Item DIAMOND_SPELLPICKAXE = create("diamond_spellpickaxe", new SpellPickaxe(Item.ToolMaterial.DIAMOND));
    public static final Item DIAMOND_SPELLSHOVEL = create("diamond_spellshovel", new SpellShovel(Item.ToolMaterial.DIAMOND));

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for (Item item : ITEMS) {
            event.getRegistry().register(item);
        }
    }

    static Item create(String name, Item item) {
        item.setRegistryName(new ResourceLocation(MOD_ID, name));
        item.setTranslationKey(MOD_ID + "." + name);
        ITEMS.add(item);
        return item;
    }
}
