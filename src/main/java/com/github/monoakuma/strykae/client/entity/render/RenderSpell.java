package com.github.monoakuma.strykae.client.entity.render;

import com.github.monoakuma.strykae.client.entity.models.ModelSpell;
import com.github.monoakuma.strykae.objects.magic.general.entities.EntitySpell;
import net.minecraft.client.model.ModelShulkerBullet;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderFireball;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderShulkerBullet;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static com.github.monoakuma.strykae.Strykae.MOD_ID;
import static com.github.monoakuma.strykae.Strykae.spellHandler;

@SideOnly(Side.CLIENT)
public class RenderSpell extends Render<EntitySpell> {
    private final ModelSpell model = new ModelSpell();
    private final ResourceLocation SAHVIC_TEXTURE = new ResourceLocation(MOD_ID,"textures/entity/spell/spellsahvic.png");
    private final ResourceLocation GALDIC_TEXTURE = new ResourceLocation(MOD_ID,"textures/entity/spell/spellgaldic.png");

    public RenderSpell(RenderManager manager) {
        super(manager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntitySpell entitySpell) {
        return spellHandler.isGaldic(entitySpell.getSpell()) ? GALDIC_TEXTURE : SAHVIC_TEXTURE;
    }

    private float rotLerp(float x, float y, float z) {
        float rot;
        for(rot = y - x; rot < -180.0F; rot += 360.0F) {
        }

        while(rot >= 180.0F) {
            rot -= 360.0F;
        }

        return x + z * rot;
    }

    @Override
    public void doRender(EntitySpell spell, double x, double y, double z, float pitch, float yaw) {
        GlStateManager.pushMatrix();
        float yawLerp = this.rotLerp(spell.prevRotationYaw, spell.rotationYaw, yaw);
        float newPitch = spell.prevRotationPitch + (spell.rotationPitch - spell.prevRotationPitch) * yaw;
        float age = (float)spell.ticksExisted;
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.4096f, 0.4096f, 0.4096f);
        this.bindEntityTexture(spell);
        this.model.render(spell, (float)x, (float)y, (float)z, newPitch, yawLerp, 0.4096F);
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        super.doRender(spell, x, y, z, pitch, yaw);
    }
}
