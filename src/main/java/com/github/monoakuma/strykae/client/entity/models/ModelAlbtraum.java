package com.github.monoakuma.strykae.client.entity.models;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import com.github.monoakuma.strykae.objects.magic.sahvic.familiars.EntityAlbtraum;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelAlbtraum extends ModelBase {
    private final ModelRenderer head;
    private final ModelRenderer upperMouth;
    private final ModelRenderer lowerMouth;
    private final ModelRenderer horseLeftEar;
    private final ModelRenderer horseRightEar;
    private final ModelRenderer muleLeftEar;
    private final ModelRenderer muleRightEar;
    private final ModelRenderer neck;
    private final ModelRenderer mane;
    private final ModelRenderer body;
    private final ModelRenderer tailBase;
    private final ModelRenderer tailMiddle;
    private final ModelRenderer tailTip;
    private final ModelRenderer backLeftLeg;
    private final ModelRenderer backLeftShin;
    private final ModelRenderer backLeftHoof;
    private final ModelRenderer backRightLeg;
    private final ModelRenderer backRightShin;
    private final ModelRenderer backRightHoof;
    private final ModelRenderer frontLeftLeg;
    private final ModelRenderer frontLeftShin;
    private final ModelRenderer frontLeftHoof;
    private final ModelRenderer frontRightLeg;
    private final ModelRenderer frontRightShin;
    private final ModelRenderer frontRightHoof;
    private final ModelRenderer muleLeftChest;
    private final ModelRenderer muleRightChest;

    public ModelAlbtraum() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.body = new ModelRenderer(this, 0, 34);
        this.body.addBox(-5.0F, -8.0F, -19.0F, 10, 10, 24);
        this.body.setRotationPoint(0.0F, 11.0F, 9.0F);
        this.tailBase = new ModelRenderer(this, 44, 0);
        this.tailBase.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 3);
        this.tailBase.setRotationPoint(0.0F, 3.0F, 14.0F);
        this.tailBase.rotateAngleX = -1.134464F;
        this.tailMiddle = new ModelRenderer(this, 38, 7);
        this.tailMiddle.addBox(-1.5F, -2.0F, 3.0F, 3, 4, 7);
        this.tailMiddle.setRotationPoint(0.0F, 3.0F, 14.0F);
        this.tailMiddle.rotateAngleX = -1.134464F;
        this.tailTip = new ModelRenderer(this, 24, 3);
        this.tailTip.addBox(-1.5F, -4.5F, 9.0F, 3, 4, 7);
        this.tailTip.setRotationPoint(0.0F, 3.0F, 14.0F);
        this.tailTip.rotateAngleX = -1.3962634F;
        this.backLeftLeg = new ModelRenderer(this, 78, 29);
        this.backLeftLeg.addBox(-2.5F, -2.0F, -2.5F, 4, 9, 5);
        this.backLeftLeg.setRotationPoint(4.0F, 9.0F, 11.0F);
        this.backLeftShin = new ModelRenderer(this, 78, 43);
        this.backLeftShin.addBox(-2.0F, 0.0F, -1.5F, 3, 5, 3);
        this.backLeftShin.setRotationPoint(4.0F, 16.0F, 11.0F);
        this.backLeftHoof = new ModelRenderer(this, 78, 51);
        this.backLeftHoof.addBox(-2.5F, 5.1F, -2.0F, 4, 3, 4);
        this.backLeftHoof.setRotationPoint(4.0F, 16.0F, 11.0F);
        this.backRightLeg = new ModelRenderer(this, 96, 29);
        this.backRightLeg.addBox(-1.5F, -2.0F, -2.5F, 4, 9, 5);
        this.backRightLeg.setRotationPoint(-4.0F, 9.0F, 11.0F);
        this.backRightShin = new ModelRenderer(this, 96, 43);
        this.backRightShin.addBox(-1.0F, 0.0F, -1.5F, 3, 5, 3);
        this.backRightShin.setRotationPoint(-4.0F, 16.0F, 11.0F);
        this.backRightHoof = new ModelRenderer(this, 96, 51);
        this.backRightHoof.addBox(-1.5F, 5.1F, -2.0F, 4, 3, 4);
        this.backRightHoof.setRotationPoint(-4.0F, 16.0F, 11.0F);
        this.frontLeftLeg = new ModelRenderer(this, 44, 29);
        this.frontLeftLeg.addBox(-1.9F, -1.0F, -2.1F, 3, 8, 4);
        this.frontLeftLeg.setRotationPoint(4.0F, 9.0F, -8.0F);
        this.frontLeftShin = new ModelRenderer(this, 44, 41);
        this.frontLeftShin.addBox(-1.9F, 0.0F, -1.6F, 3, 5, 3);
        this.frontLeftShin.setRotationPoint(4.0F, 16.0F, -8.0F);
        this.frontLeftHoof = new ModelRenderer(this, 44, 51);
        this.frontLeftHoof.addBox(-2.4F, 5.1F, -2.1F, 4, 3, 4);
        this.frontLeftHoof.setRotationPoint(4.0F, 16.0F, -8.0F);
        this.frontRightLeg = new ModelRenderer(this, 60, 29);
        this.frontRightLeg.addBox(-1.1F, -1.0F, -2.1F, 3, 8, 4);
        this.frontRightLeg.setRotationPoint(-4.0F, 9.0F, -8.0F);
        this.frontRightShin = new ModelRenderer(this, 60, 41);
        this.frontRightShin.addBox(-1.1F, 0.0F, -1.6F, 3, 5, 3);
        this.frontRightShin.setRotationPoint(-4.0F, 16.0F, -8.0F);
        this.frontRightHoof = new ModelRenderer(this, 60, 51);
        this.frontRightHoof.addBox(-1.6F, 5.1F, -2.1F, 4, 3, 4);
        this.frontRightHoof.setRotationPoint(-4.0F, 16.0F, -8.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-2.5F, -10.0F, -1.5F, 5, 5, 7);
        this.head.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.head.rotateAngleX = 0.5235988F;
        this.upperMouth = new ModelRenderer(this, 24, 18);
        this.upperMouth.addBox(-2.0F, -10.0F, -7.0F, 4, 3, 6);
        this.upperMouth.setRotationPoint(0.0F, 3.95F, -10.0F);
        this.upperMouth.rotateAngleX = 0.5235988F;
        this.lowerMouth = new ModelRenderer(this, 24, 27);
        this.lowerMouth.addBox(-2.0F, -7.0F, -6.5F, 4, 2, 5);
        this.lowerMouth.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.lowerMouth.rotateAngleX = 0.5235988F;
        this.head.addChild(this.upperMouth);
        this.head.addChild(this.lowerMouth);
        this.horseLeftEar = new ModelRenderer(this, 0, 0);
        this.horseLeftEar.addBox(0.45F, -12.0F, 4.0F, 2, 3, 1);
        this.horseLeftEar.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.horseLeftEar.rotateAngleX = 0.5235988F;
        this.horseRightEar = new ModelRenderer(this, 0, 0);
        this.horseRightEar.addBox(-2.45F, -12.0F, 4.0F, 2, 3, 1);
        this.horseRightEar.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.horseRightEar.rotateAngleX = 0.5235988F;
        this.muleLeftEar = new ModelRenderer(this, 0, 12);
        this.muleLeftEar.addBox(-2.0F, -16.0F, 4.0F, 2, 7, 1);
        this.muleLeftEar.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.muleLeftEar.rotateAngleX = 0.5235988F;
        this.muleLeftEar.rotateAngleZ = 0.2617994F;
        this.muleRightEar = new ModelRenderer(this, 0, 12);
        this.muleRightEar.addBox(0.0F, -16.0F, 4.0F, 2, 7, 1);
        this.muleRightEar.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.muleRightEar.rotateAngleX = 0.5235988F;
        this.muleRightEar.rotateAngleZ = -0.2617994F;
        this.neck = new ModelRenderer(this, 0, 12);
        this.neck.addBox(-2.05F, -9.8F, -2.0F, 4, 14, 8);
        this.neck.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.neck.rotateAngleX = 0.5235988F;
        this.muleLeftChest = new ModelRenderer(this, 0, 34);
        this.muleLeftChest.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
        this.muleLeftChest.setRotationPoint(-7.5F, 3.0F, 10.0F);
        this.muleLeftChest.rotateAngleY = 1.5707964F;
        this.muleRightChest = new ModelRenderer(this, 0, 47);
        this.muleRightChest.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
        this.muleRightChest.setRotationPoint(4.5F, 3.0F, 10.0F);
        this.muleRightChest.rotateAngleY = 1.5707964F;
        this.mane = new ModelRenderer(this, 58, 0);
        this.mane.addBox(-1.0F, -11.5F, 5.0F, 2, 16, 4);
        this.mane.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.mane.rotateAngleX = 0.5235988F;
    }

    public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float f6) {
        this.backLeftLeg.render(f6);
        this.backLeftShin.render(f6);
        this.backLeftHoof.render(f6);
        this.backRightLeg.render(f6);
        this.backRightShin.render(f6);
        this.backRightHoof.render(f6);
        this.frontLeftLeg.render(f6);
        this.frontLeftShin.render(f6);
        this.frontLeftHoof.render(f6);
        this.frontRightLeg.render(f6);
        this.frontRightShin.render(f6);
        this.frontRightHoof.render(f6);
        this.body.render(f6);
        this.tailBase.render(f6);
        this.tailMiddle.render(f6);
        this.tailTip.render(f6);
        this.neck.render(f6);
        this.mane.render(f6);
        this.horseLeftEar.render(f6);
        this.horseRightEar.render(f6);
        this.head.render(f6);

    }

    private float updateHorseRotation(float p_110683_1_, float p_110683_2_, float p_110683_3_) {
        float lvt_4_1_;
        for(lvt_4_1_ = p_110683_2_ - p_110683_1_; lvt_4_1_ < -180.0F; lvt_4_1_ += 360.0F) {
        }

        while(lvt_4_1_ >= 180.0F) {
            lvt_4_1_ -= 360.0F;
        }

        return p_110683_1_ + p_110683_3_ * lvt_4_1_;
    }

    public void setLivingAnimations(EntityLivingBase entityB, float f1, float f2, float f3) {
        super.setLivingAnimations(entityB, f1, f2, f3);
        float lvt_5_1_ = this.updateHorseRotation(entityB.prevRenderYawOffset, entityB.renderYawOffset, f3);
        float lvt_6_1_ = this.updateHorseRotation(entityB.prevRotationYawHead, entityB.rotationYawHead, f3);
        float lvt_7_1_ = entityB.prevRotationPitch + (entityB.rotationPitch - entityB.prevRotationPitch) * f3;
        float lvt_8_1_ = lvt_6_1_ - lvt_5_1_;
        float lvt_9_1_ = lvt_7_1_ * 0.017453292F;
        if (lvt_8_1_ > 20.0F) {
            lvt_8_1_ = 20.0F;
        }

        if (lvt_8_1_ < -20.0F) {
            lvt_8_1_ = -20.0F;
        }

        if (f2 > 0.2F) {
            lvt_9_1_ += MathHelper.cos(f1 * 0.4F) * 0.15F * f2;
        }

        EntityAlbtraum lvt_10_1_ = (EntityAlbtraum) entityB;
        float lvt_11_1_ = lvt_10_1_.getGrassEatingAmount(f3);
        float lvt_12_1_ = lvt_10_1_.getRearingAmount(f3);
        float lvt_13_1_ = 1.0F - lvt_12_1_;
        float lvt_14_1_ = lvt_10_1_.getMouthOpennessAngle(f3);
        float lvt_18_1_ = (float)entityB.ticksExisted + f3;
        float lvt_19_1_ = MathHelper.cos(f1 * 0.6662F + 3.1415927F);
        float lvt_20_1_ = lvt_19_1_ * 0.8F * f2;
        this.head.rotationPointY = 4.0F;
        this.head.rotationPointZ = -10.0F;
        this.tailBase.rotationPointY = 3.0F;
        this.muleRightChest.rotationPointY = 3.0F;
        this.muleRightChest.rotationPointZ = 10.0F;
        this.body.rotateAngleX = 0.0F;
        this.head.rotateAngleX = 0.5235988F + lvt_9_1_;
        this.head.rotateAngleY = lvt_8_1_ * 0.017453292F;
        this.head.rotateAngleX = lvt_12_1_ * (0.2617994F + lvt_9_1_) + lvt_11_1_ * 2.1816616F + (1.0F - Math.max(lvt_12_1_, lvt_11_1_)) * this.head.rotateAngleX;
        this.head.rotateAngleY = lvt_12_1_ * lvt_8_1_ * 0.017453292F + (1.0F - Math.max(lvt_12_1_, lvt_11_1_)) * this.head.rotateAngleY;
        this.head.rotationPointY = lvt_12_1_ * -6.0F + lvt_11_1_ * 11.0F + (1.0F - Math.max(lvt_12_1_, lvt_11_1_)) * this.head.rotationPointY;
        this.head.rotationPointZ = lvt_12_1_ * -1.0F + lvt_11_1_ * -10.0F + (1.0F - Math.max(lvt_12_1_, lvt_11_1_)) * this.head.rotationPointZ;
        this.tailBase.rotationPointY = lvt_12_1_ * 9.0F + lvt_13_1_ * this.tailBase.rotationPointY;
        this.muleRightChest.rotationPointY = lvt_12_1_ * 5.5F + lvt_13_1_ * this.muleRightChest.rotationPointY;
        this.muleRightChest.rotationPointZ = lvt_12_1_ * 15.0F + lvt_13_1_ * this.muleRightChest.rotationPointZ;
        this.body.rotateAngleX = lvt_12_1_ * -0.7853982F + lvt_13_1_ * this.body.rotateAngleX;
        this.horseLeftEar.rotationPointY = this.head.rotationPointY;
        this.horseRightEar.rotationPointY = this.head.rotationPointY;
        this.muleLeftEar.rotationPointY = this.head.rotationPointY;
        this.muleRightEar.rotationPointY = this.head.rotationPointY;
        this.neck.rotationPointY = this.head.rotationPointY;
        this.upperMouth.rotationPointY = 0.02F;
        this.lowerMouth.rotationPointY = 0.0F;
        this.mane.rotationPointY = this.head.rotationPointY;
        this.horseLeftEar.rotationPointZ = this.head.rotationPointZ;
        this.horseRightEar.rotationPointZ = this.head.rotationPointZ;
        this.muleLeftEar.rotationPointZ = this.head.rotationPointZ;
        this.muleRightEar.rotationPointZ = this.head.rotationPointZ;
        this.neck.rotationPointZ = this.head.rotationPointZ;
        this.upperMouth.rotationPointZ = 0.02F - lvt_14_1_;
        this.lowerMouth.rotationPointZ = lvt_14_1_;
        this.mane.rotationPointZ = this.head.rotationPointZ;
        this.horseLeftEar.rotateAngleX = this.head.rotateAngleX;
        this.horseRightEar.rotateAngleX = this.head.rotateAngleX;
        this.muleLeftEar.rotateAngleX = this.head.rotateAngleX;
        this.muleRightEar.rotateAngleX = this.head.rotateAngleX;
        this.neck.rotateAngleX = this.head.rotateAngleX;
        this.upperMouth.rotateAngleX = -0.09424778F * lvt_14_1_;
        this.lowerMouth.rotateAngleX = 0.15707964F * lvt_14_1_;
        this.mane.rotateAngleX = this.head.rotateAngleX;
        this.horseLeftEar.rotateAngleY = this.head.rotateAngleY;
        this.horseRightEar.rotateAngleY = this.head.rotateAngleY;
        this.muleLeftEar.rotateAngleY = this.head.rotateAngleY;
        this.muleRightEar.rotateAngleY = this.head.rotateAngleY;
        this.neck.rotateAngleY = this.head.rotateAngleY;
        this.upperMouth.rotateAngleY = 0.0F;
        this.lowerMouth.rotateAngleY = 0.0F;
        this.mane.rotateAngleY = this.head.rotateAngleY;
        this.muleLeftChest.rotateAngleX = lvt_20_1_ / 5.0F;
        this.muleRightChest.rotateAngleX = -lvt_20_1_ / 5.0F;
        float lvt_21_2_ = 0.2617994F * lvt_12_1_;
        float lvt_22_1_ = MathHelper.cos(lvt_18_1_ * 0.6F + 3.1415927F);
        this.frontLeftLeg.rotationPointY = -2.0F * lvt_12_1_ + 9.0F * lvt_13_1_;
        this.frontLeftLeg.rotationPointZ = -2.0F * lvt_12_1_ + -8.0F * lvt_13_1_;
        this.frontRightLeg.rotationPointY = this.frontLeftLeg.rotationPointY;
        this.frontRightLeg.rotationPointZ = this.frontLeftLeg.rotationPointZ;
        this.backLeftShin.rotationPointY = this.backLeftLeg.rotationPointY + MathHelper.sin(1.5707964F + lvt_21_2_ + lvt_13_1_ * -lvt_19_1_ * 0.5F * f2) * 7.0F;
        this.backLeftShin.rotationPointZ = this.backLeftLeg.rotationPointZ + MathHelper.cos(-1.5707964F + lvt_21_2_ + lvt_13_1_ * -lvt_19_1_ * 0.5F * f2) * 7.0F;
        this.backRightShin.rotationPointY = this.backRightLeg.rotationPointY + MathHelper.sin(1.5707964F + lvt_21_2_ + lvt_13_1_ * lvt_19_1_ * 0.5F * f2) * 7.0F;
        this.backRightShin.rotationPointZ = this.backRightLeg.rotationPointZ + MathHelper.cos(-1.5707964F + lvt_21_2_ + lvt_13_1_ * lvt_19_1_ * 0.5F * f2) * 7.0F;
        float lvt_23_1_ = (-1.0471976F + lvt_22_1_) * lvt_12_1_ + lvt_20_1_ * lvt_13_1_;
        float lvt_24_1_ = (-1.0471976F - lvt_22_1_) * lvt_12_1_ + -lvt_20_1_ * lvt_13_1_;
        this.frontLeftShin.rotationPointY = this.frontLeftLeg.rotationPointY + MathHelper.sin(1.5707964F + lvt_23_1_) * 7.0F;
        this.frontLeftShin.rotationPointZ = this.frontLeftLeg.rotationPointZ + MathHelper.cos(-1.5707964F + lvt_23_1_) * 7.0F;
        this.frontRightShin.rotationPointY = this.frontRightLeg.rotationPointY + MathHelper.sin(1.5707964F + lvt_24_1_) * 7.0F;
        this.frontRightShin.rotationPointZ = this.frontRightLeg.rotationPointZ + MathHelper.cos(-1.5707964F + lvt_24_1_) * 7.0F;
        this.backLeftLeg.rotateAngleX = lvt_21_2_ + -lvt_19_1_ * 0.5F * f2 * lvt_13_1_;
        this.backLeftShin.rotateAngleX = -0.08726646F * lvt_12_1_ + (-lvt_19_1_ * 0.5F * f2 - Math.max(0.0F, lvt_19_1_ * 0.5F * f2)) * lvt_13_1_;
        this.backLeftHoof.rotateAngleX = this.backLeftShin.rotateAngleX;
        this.backRightLeg.rotateAngleX = lvt_21_2_ + lvt_19_1_ * 0.5F * f2 * lvt_13_1_;
        this.backRightShin.rotateAngleX = -0.08726646F * lvt_12_1_ + (lvt_19_1_ * 0.5F * f2 - Math.max(0.0F, -lvt_19_1_ * 0.5F * f2)) * lvt_13_1_;
        this.backRightHoof.rotateAngleX = this.backRightShin.rotateAngleX;
        this.frontLeftLeg.rotateAngleX = lvt_23_1_;
        this.frontLeftShin.rotateAngleX = (this.frontLeftLeg.rotateAngleX + 3.1415927F * Math.max(0.0F, 0.2F + lvt_22_1_ * 0.2F)) * lvt_12_1_ + (lvt_20_1_ + Math.max(0.0F, lvt_19_1_ * 0.5F * f2)) * lvt_13_1_;
        this.frontLeftHoof.rotateAngleX = this.frontLeftShin.rotateAngleX;
        this.frontRightLeg.rotateAngleX = lvt_24_1_;
        this.frontRightShin.rotateAngleX = (this.frontRightLeg.rotateAngleX + 3.1415927F * Math.max(0.0F, 0.2F - lvt_22_1_ * 0.2F)) * lvt_12_1_ + (-lvt_20_1_ + Math.max(0.0F, -lvt_19_1_ * 0.5F * f2)) * lvt_13_1_;
        this.frontRightHoof.rotateAngleX = this.frontRightShin.rotateAngleX;
        this.backLeftHoof.rotationPointY = this.backLeftShin.rotationPointY;
        this.backLeftHoof.rotationPointZ = this.backLeftShin.rotationPointZ;
        this.backRightHoof.rotationPointY = this.backRightShin.rotationPointY;
        this.backRightHoof.rotationPointZ = this.backRightShin.rotationPointZ;
        this.frontLeftHoof.rotationPointY = this.frontLeftShin.rotationPointY;
        this.frontLeftHoof.rotationPointZ = this.frontLeftShin.rotationPointZ;
        this.frontRightHoof.rotationPointY = this.frontRightShin.rotationPointY;
        this.frontRightHoof.rotationPointZ = this.frontRightShin.rotationPointZ;
        lvt_21_2_ = -1.3089969F + f2 * 1.5F;
        if (lvt_21_2_ > 0.0F) {
            lvt_21_2_ = 0.0F;
        }
        this.tailBase.rotateAngleY = 0.0F;


        this.tailMiddle.rotateAngleY = this.tailBase.rotateAngleY;
        this.tailTip.rotateAngleY = this.tailBase.rotateAngleY;
        this.tailMiddle.rotationPointY = this.tailBase.rotationPointY;
        this.tailTip.rotationPointY = this.tailBase.rotationPointY;
        this.tailMiddle.rotationPointZ = this.tailBase.rotationPointZ;
        this.tailTip.rotationPointZ = this.tailBase.rotationPointZ;
        this.tailBase.rotateAngleX = lvt_21_2_;
        this.tailMiddle.rotateAngleX = lvt_21_2_;
        this.tailTip.rotateAngleX = -0.2617994F + lvt_21_2_;
    }
}

