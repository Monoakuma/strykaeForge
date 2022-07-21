package com.github.monoakuma.strykae.objects.items;


import com.github.monoakuma.strykae.objects.items.gui.GuiSigilTablet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static com.github.monoakuma.strykae.Strykae.INSTANCE;
import static com.github.monoakuma.strykae.Strykae.TAB;

public class SigilItem extends Item {
    public SigilItem()
    {
        this.setCreativeTab(TAB);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(@Nonnull ItemStack stack) {
        if (stack.hasTagCompound()) {
            if (!stack.getTagCompound().getString("StrykaeSpell").equals("")) {
                return true;
            }
        }
        return false;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItemMainhand();
        if (hand.equals(EnumHand.MAIN_HAND)&&!stack.hasTagCompound()) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiSigilTablet(player,player.getHeldItemMainhand()));
        }
        return super.onItemRightClick(world, player, hand);
    }
}