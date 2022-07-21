package com.github.monoakuma.strykae.client.entity.models;

import com.github.monoakuma.strykae.objects.magic.sahvic.familiars.EntityMaskon;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelMaskon extends ModelBiped {
    public ModelMaskon() {
        this(0.0F, false);
    }

    public ModelMaskon(float p_i1168_1_, boolean p_i1168_2_) {
        super(p_i1168_1_, 0.0F, 64, p_i1168_2_ ? 32 : 64);
    }

    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity entity) {
        super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, entity);
        EntityMaskon maskon = (EntityMaskon) entity;
        float armSwing = MathHelper.sin(this.swingProgress * 3.1415927F);
        float backSwing = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * 3.1415927F);
        if (maskon.isSummoning()) {
            this.bipedRightArm.rotateAngleZ = 0.15707964F;
            this.bipedLeftArm.rotateAngleZ = -0.15707964F;
            this.bipedRightArm.rotateAngleY = 0.15707964F;
            this.bipedLeftArm.rotateAngleY = -0.15707964F;
            ModelRenderer var10000;
            this.bipedRightArm.rotateAngleX = -1.8849558F + MathHelper.cos(p_78087_3_ * 0.09F) * 0.15F;
            this.bipedLeftArm.rotateAngleX = -1.8849558F + MathHelper.cos(p_78087_3_ * 0.09F) * 0.15F;
            var10000 = this.bipedRightArm;
            var10000.rotateAngleX += armSwing * 2.2F - backSwing * 0.4F;
            var10000 = this.bipedLeftArm;
            var10000.rotateAngleX += armSwing * 1.2F - backSwing * 0.4F;

        } else {
            this.bipedRightArm.rotateAngleZ = 0.0F;
            this.bipedLeftArm.rotateAngleZ = 0.0F;
            this.bipedRightArm.rotateAngleY = -(0.1F - armSwing * 0.6F);
            this.bipedLeftArm.rotateAngleY = 0.1F - armSwing * 0.6F;
            float armRot = -3.1415927F / 2.25F;
            this.bipedRightArm.rotateAngleX = armRot;
            this.bipedLeftArm.rotateAngleX = armRot;

            ModelRenderer var10000 = this.bipedRightArm;
            var10000.rotateAngleX += armSwing * 1.2F - backSwing * 0.4F;
            var10000 = this.bipedLeftArm;
            var10000.rotateAngleX += armSwing * 1.2F - backSwing * 0.4F;
            var10000 = this.bipedRightArm;
            var10000.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
            var10000 = this.bipedLeftArm;
            var10000.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
            var10000 = this.bipedRightArm;
            var10000.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
            var10000 = this.bipedLeftArm;
            var10000.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
        }
    }
}
