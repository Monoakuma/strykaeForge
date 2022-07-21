package com.github.monoakuma.strykae.client.entity.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class ModelGureon extends ModelBase {
    public ModelRenderer chest;
    public ModelRenderer pelvis;
    public ModelRenderer rightarm;
    public ModelRenderer leftleg;
    public ModelRenderer head;
    public ModelRenderer rightleg;
    public ModelRenderer leftarm;

    public ModelGureon() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.leftleg = new ModelRenderer(this, 37, 0);
        this.leftleg.setRotationPoint(-0.8F, 11.0F, 0.0F);
        this.leftleg.addBox(-3.5F, -3.0F, -2.8F, 3, 16, 5, 0.0F);
        this.pelvis = new ModelRenderer(this, 0, 70);
        this.pelvis.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.pelvis.addBox(-4.5F, 9.0F, -3.0F, 9, 6, 6, -0.1F);
        this.leftarm = new ModelRenderer(this, 60, 58);
        this.leftarm.setRotationPoint(-8.3F, -6.4F, -1.8F);
        this.leftarm.addBox(0.0F, -8.0F, 0.0F, 4, 28, 4, 0.0F);
        this.chest = new ModelRenderer(this, 0, 40);
        this.chest.setRotationPoint(-5.3F, -17.7F, -2.3F);
        this.chest.addBox(0.0F, 0.0F, 0.0F, 18, 18, 11, -1.5F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(-3.8F, -23.3F, -5.1F);
        this.head.addBox(0.0F, 0.0F, 0.0F, 8, 10, 8, -0.7F);
        this.rightleg = new ModelRenderer(this, 60, 0);
        this.rightleg.mirror = true;
        this.rightleg.setRotationPoint(1.9F, 11.0F, 0.0F);
        this.rightleg.addBox(-0.5F, -3.0F, -2.8F, 3, 16, 5, 0.0F);
        this.rightarm = new ModelRenderer(this, 60, 58);
        this.rightarm.setRotationPoint(4.5F, -6.4F, -1.8F);
        this.rightarm.addBox(0.0F, -8.0F, 0.0F, 4, 28, 4, 0.0F);
        this.setRotateAngle(head, 0.029321531433504733F, 0.0F, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f,f1,f2,f3,f4,f5,entity);
        this.leftleg.render(f5);
        this.pelvis.render(f5);
        this.leftarm.render(f5);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.chest.offsetX, this.chest.offsetY, this.chest.offsetZ);
        GlStateManager.translate(this.chest.rotationPointX * f5, this.chest.rotationPointY * f5, this.chest.rotationPointZ * f5);
        GlStateManager.scale(0.6D, 1.2D, 0.6D);
        GlStateManager.translate(-this.chest.offsetX, -this.chest.offsetY, -this.chest.offsetZ);
        GlStateManager.translate(-this.chest.rotationPointX * f5, -this.chest.rotationPointY * f5, -this.chest.rotationPointZ * f5);
        this.chest.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.head.offsetX, this.head.offsetY, this.head.offsetZ);
        GlStateManager.translate(this.head.rotationPointX * f5, this.head.rotationPointY * f5, this.head.rotationPointZ * f5);
        GlStateManager.scale(1.0D, 0.8D, 1.0D);
        GlStateManager.translate(-this.head.offsetX, -this.head.offsetY, -this.head.offsetZ);
        GlStateManager.translate(-this.head.rotationPointX * f5, -this.head.rotationPointY * f5, -this.head.rotationPointZ * f5);
        this.head.render(f5);
        GlStateManager.popMatrix();
        this.rightleg.render(f5);
        this.rightarm.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    public void setRotationAngles(float limbSwing,float limbSwingAmount,float ageInTicks,float netHeadYaw,float headPitch,float scaleFactor, Entity entityIn)
    {
        this.leftleg.rotateAngleX = headPitch*0.0001F+MathHelper.cos(limbSwing* 0.666F)*1.4F * limbSwingAmount;
        this.leftarm.rotateAngleX = headPitch*0.0001F+MathHelper.cos(limbSwing* 0.666F)*1.3F * limbSwingAmount;
        this.rightleg.rotateAngleX = headPitch*0.0001F+MathHelper.cos(limbSwing* 0.666F +(float)Math.PI)*1.4F * limbSwingAmount;
        this.rightarm.rotateAngleX = headPitch*0.0001F+MathHelper.cos(limbSwing* 0.666F +(float)Math.PI)*1.3F * limbSwingAmount;
        this.chest.rotateAngleX = headPitch*0.0002F;
        this.head.rotateAngleX = headPitch*0.00379F;
    }
}
