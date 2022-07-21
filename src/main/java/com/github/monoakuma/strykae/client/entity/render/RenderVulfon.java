package com.github.monoakuma.strykae.client.entity.render;

import com.github.monoakuma.strykae.client.entity.models.ModelVulfon;
import com.github.monoakuma.strykae.objects.magic.galdic.familiars.EntityVulfon;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static com.github.monoakuma.strykae.Strykae.MOD_ID;
@SideOnly(Side.CLIENT)
public class RenderVulfon extends RenderLiving<EntityVulfon> {
    public RenderVulfon(RenderManager manager) {
        super(manager, new ModelVulfon(), 0.3F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityVulfon entityVulfon) {
        return new ResourceLocation(MOD_ID,"textures/entity/vulfon.png");
    }
}
