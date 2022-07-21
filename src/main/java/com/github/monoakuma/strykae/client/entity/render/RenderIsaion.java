package com.github.monoakuma.strykae.client.entity.render;

import com.github.monoakuma.strykae.objects.magic.sahvic.familiars.EntityIsaion;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import java.util.Map;
import java.util.Objects;

import static com.github.monoakuma.strykae.Strykae.MOD_ID;
import static net.minecraft.client.Minecraft.getMinecraft;

@SideOnly(Side.CLIENT)
public class RenderIsaion extends RenderLiving<EntityIsaion>
{
    public static final ResourceLocation TEXTURES = new ResourceLocation(MOD_ID,"textures/entity/isaion.png");
    public RenderIsaion(RenderManager manager) {
        super(manager, new ModelPlayer(0.0f,false), 0.5F);
        this.addLayer(new LayerBipedArmor(this));
        this.addLayer(new LayerHeldItem(this));
    }
    public ResourceLocation getEntityTexture(EntityIsaion entity) {
        if (entity.getGameProfile() == null) {
            return TEXTURES;
        }
        Map<MinecraftProfileTexture.Type,MinecraftProfileTexture> profileMap = getMinecraft().getSkinManager().loadSkinFromCache(entity.getGameProfile());
        if (profileMap.containsKey(MinecraftProfileTexture.Type.SKIN)) return getMinecraft().getSkinManager().loadSkin(profileMap.get(MinecraftProfileTexture.Type.SKIN),MinecraftProfileTexture.Type.SKIN);
        return TEXTURES;
    }
}
