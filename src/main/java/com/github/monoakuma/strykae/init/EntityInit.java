package com.github.monoakuma.strykae.init;

import com.github.monoakuma.strykae.Strykae;
import com.github.monoakuma.strykae.objects.magic.galdic.familiars.EntityGureon;
import com.github.monoakuma.strykae.objects.magic.galdic.familiars.EntityVulfon;
import com.github.monoakuma.strykae.objects.magic.general.entities.EntitySpell;
import com.github.monoakuma.strykae.objects.magic.sahvic.familiars.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import static com.github.monoakuma.strykae.Strykae.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class EntityInit {

    private static void create(String name, Class<? extends Entity> entity, int id,int range,int primaryColor, int secondaryColor)
    {
        EntityRegistry.registerModEntity(new ResourceLocation(MOD_ID+":"+name),entity,name,id, Strykae.INSTANCE,range,1,true,primaryColor,secondaryColor);
    }
    private static void createProjectile(String name, Class<? extends Entity> entity, int id)
    {
        EntityRegistry.registerModEntity(new ResourceLocation(MOD_ID+":"+name),entity,name,id, Strykae.INSTANCE,64,20,true);
    }
    public static void registerEntities()
    {
        // FAMILIARS //
        create("isaion",  EntityIsaion.class,  69,128,5898495,184);
        create("gureon",  EntityGureon.class,  12,32 ,4338176,1512448);
        create("albtraum",EntityAlbtraum.class,11,16 ,3640526,3612224);
        create("vulfon",  EntityVulfon.class,  10,32 ,1245183,1245183);
        create("maskon",  EntityMaskon.class,  9 ,32 ,1192448,1192448);
        create("kaneon",  EntityKaneon.class,  8 ,32 ,8729226,5898495);
        // SPECIAL MOB //
        //create("koschei", EntityKoschei.class,7,64,7340032,0); //scrapped entity for now, meant to be a Strykae mob that can use the Sahvic magic system.
        // UTILITY MOBS //
        createProjectile("spell", EntitySpell.class,18);
    }

}
