package com.github.monoakuma.strykae.client.entity.render;

import com.github.monoakuma.strykae.client.entity.models.ModelMaskon;
import com.github.monoakuma.strykae.objects.magic.sahvic.familiars.EntityMaskon;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelIllager;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static com.github.monoakuma.strykae.Strykae.MOD_ID;
@SideOnly(Side.CLIENT)
public class RenderMaskon extends RenderLiving<EntityMaskon> {

    public RenderMaskon(RenderManager manager) {
        super(manager,new ModelMaskon(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityMaskon entityMaskon) {
        return new ResourceLocation(MOD_ID,"textures/entity/maskon.png");
    }
}
