package com.github.monoakuma.strykae.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.Set;

import static com.github.monoakuma.strykae.Strykae.MOD_ID;
@Mod.EventBusSubscriber(modid = MOD_ID)
public class SoundInit {
    public static final Set<SoundEvent> SOUNDS = new HashSet<>();
    public static SoundEvent ENTITY_PLAYER_SPELLCAST = create("entity.player.spellcast");
    public static SoundEvent BLOCK_SIGIL_TABLE_CRAFT = create("block.sigil_table.craft");
    public static SoundEvent ENTITY_ISAION_AMBIENT = create("entity.isaion.ambient");
    public static SoundEvent ENTITY_ISAION_DEATH = create("entity.isaion.death");
    public static SoundEvent ENTITY_ISAION_SCREAM = create("entity.isaion.scream");
    public static SoundEvent ENTITY_GUREON_AMBIENT = create("entity.gureon.ambient");
    public static SoundEvent ENTITY_GUREON_HURT = create("entity.gureon.hurt");
    public static SoundEvent ENTITY_GUREON_DEATH = create("entity.gureon.death");
    public static SoundEvent ENTITY_KANEON_ATTACK = create("entity.kaneon.attack");
    public static SoundEvent ENTITY_KANEON_HURT = create("entity.kaneon.hurt");
    public static SoundEvent ENTITY_KANEON_DEATH = create("entity.kaneon.death");

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        for (SoundEvent sound : SOUNDS) {
            event.getRegistry().register(sound);
        }
    }

    static SoundEvent create(String name) {
        ResourceLocation location = new ResourceLocation(MOD_ID,name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        SOUNDS.add(event);
        return event;
    }

}
