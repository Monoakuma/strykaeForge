package com.github.monoakuma.strykae.init;

import com.github.monoakuma.strykae.client.entity.render.*;
import com.github.monoakuma.strykae.objects.magic.galdic.familiars.EntityGureon;
import com.github.monoakuma.strykae.objects.magic.galdic.familiars.EntityVulfon;
import com.github.monoakuma.strykae.objects.magic.general.entities.EntitySpell;
import com.github.monoakuma.strykae.objects.magic.sahvic.familiars.EntityAlbtraum;
import com.github.monoakuma.strykae.objects.magic.sahvic.familiars.EntityIsaion;
import com.github.monoakuma.strykae.objects.magic.sahvic.familiars.EntityKaneon;
import com.github.monoakuma.strykae.objects.magic.sahvic.familiars.EntityMaskon;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityRenderInit {
    public static void createRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityIsaion.class, new IRenderFactory<EntityIsaion>() {
            @Override
            public Render<? super EntityIsaion> createRenderFor(RenderManager manager) {
                return new RenderIsaion(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityGureon.class, new IRenderFactory<EntityGureon>() {
            @Override
            public Render<? super EntityGureon> createRenderFor(RenderManager manager) {
                return new RenderGureon(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityVulfon.class, new IRenderFactory<EntityVulfon>() {
            @Override
            public Render<? super EntityVulfon> createRenderFor(RenderManager manager) {
                return new RenderVulfon(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityAlbtraum.class, new IRenderFactory<EntityAlbtraum>() {
            @Override
            public Render<? super EntityAlbtraum> createRenderFor(RenderManager manager) {
                return new RenderAlbtraum(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityKaneon.class, new IRenderFactory<EntityKaneon>() {
            @Override
            public Render<? super EntityKaneon> createRenderFor(RenderManager manager) {
                return new RenderKaneon(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityMaskon.class, new IRenderFactory<EntityMaskon>() {
            @Override
            public Render<? super EntityMaskon> createRenderFor(RenderManager manager) {
                return new RenderMaskon(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntitySpell.class, new IRenderFactory<EntitySpell>() {
            @Override
            public Render<? super EntitySpell> createRenderFor(RenderManager manager) {
                return new RenderSpell(manager);
            }
        });
    }
}
