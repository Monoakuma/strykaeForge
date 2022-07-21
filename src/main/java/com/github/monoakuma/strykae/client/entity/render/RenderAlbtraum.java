package com.github.monoakuma.strykae.client.entity.render;

import com.github.monoakuma.strykae.client.entity.models.ModelAlbtraum;
import com.github.monoakuma.strykae.objects.magic.sahvic.familiars.EntityAlbtraum;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static com.github.monoakuma.strykae.Strykae.MOD_ID;
@SideOnly(Side.CLIENT)
public class RenderAlbtraum extends RenderLiving<EntityAlbtraum> {

    public RenderAlbtraum(RenderManager manager) {
        super(manager, new ModelAlbtraum(),1.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityAlbtraum entityAlbtraum) {
        return new ResourceLocation(MOD_ID,"textures/entity/albtraum.png");
    }
}
