package com.github.monoakuma.strykae.client.entity.models;

// Made with Blockbench 4.2.5
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class ModelSpell extends ModelBase {
    private final ModelRenderer bb_main;

    public ModelSpell() {
        textureWidth = 16;
        textureHeight = 16;
        bb_main = new ModelRenderer(this);
        bb_main.setRotationPoint(0.0F, 0.0F, 0.0F);
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F, false)); //SPELL CORE
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 8, -2.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F, false)); //SPELL AURA
    }

    @Override
    public void render(@Nonnull Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        bb_main.render(f5);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
