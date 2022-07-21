package com.github.monoakuma.strykae.common;

import com.github.monoakuma.strykae.objects.blocks.SigilTableBlock;
import com.github.monoakuma.strykae.objects.blocks.gui.GuiSigilTable;
import com.github.monoakuma.strykae.objects.items.gui.GuiSigilTablet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID==0) return new SigilTableBlock.ContainerSigilTable(player.inventory,world,new BlockPos(x,y,z));
        return null;
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID==0) return new GuiSigilTable(player.inventory,new BlockPos(x,y,z),world);
        if (ID==1) return new GuiSigilTablet(player,player.getHeldItemMainhand());
        return null;
    }
}
