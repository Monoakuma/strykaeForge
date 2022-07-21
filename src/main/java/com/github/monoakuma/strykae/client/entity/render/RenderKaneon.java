package com.github.monoakuma.strykae.client.entity.render;

import com.github.monoakuma.strykae.client.entity.models.ModelKaneon;
import com.github.monoakuma.strykae.objects.magic.sahvic.familiars.EntityKaneon;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static com.github.monoakuma.strykae.Strykae.MOD_ID;
@SideOnly(Side.CLIENT)
public class RenderKaneon extends RenderLiving<EntityKaneon> {
    public RenderKaneon(RenderManager manager) {
        super(manager, new ModelKaneon(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityKaneon entityKaneon) {
        return new ResourceLocation(MOD_ID,"textures/entity/kaneon.png");
    }
}
