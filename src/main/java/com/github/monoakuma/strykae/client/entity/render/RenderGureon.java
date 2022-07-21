package com.github.monoakuma.strykae.client.entity.render;

import com.github.monoakuma.strykae.client.entity.models.ModelGureon;
import com.github.monoakuma.strykae.objects.magic.galdic.familiars.EntityGureon;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.github.monoakuma.strykae.Strykae.MOD_ID;
@SideOnly(Side.CLIENT)
public class RenderGureon extends RenderLiving<EntityGureon> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(MOD_ID,"textures/entity/gureon.png");
    public RenderGureon(RenderManager manager) {
        super(manager, new ModelGureon(), 0.5F);
    }
    @Override
    protected ResourceLocation getEntityTexture(EntityGureon entityGureon) {
        return TEXTURES;
    }

    @Override
    protected void applyRotations(EntityGureon entityLiving,float pitch,float yaw,float partialticks)
    {
        super.applyRotations(entityLiving,pitch,yaw,partialticks);
    }
}
