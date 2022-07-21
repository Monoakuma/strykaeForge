package com.github.monoakuma.strykae.objects.blocks.gui;


import com.github.monoakuma.strykae.Strykae;
import com.github.monoakuma.strykae.objects.blocks.SigilTableBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;

import static com.github.monoakuma.strykae.Strykae.MOD_ID;

@SideOnly(Side.CLIENT)
public class GuiSigilTable extends GuiContainer implements IContainerListener {
    private static final ResourceLocation TEXTURES = new ResourceLocation(MOD_ID,"textures/gui/sigil_table.png");
    private final InventoryPlayer player;
    private final SigilTableBlock.ContainerSigilTable sigilTable;

    public GuiSigilTable(InventoryPlayer player, BlockPos pos, World world) {
        super(new SigilTableBlock.ContainerSigilTable(player,world,pos));
        this.sigilTable=(SigilTableBlock.ContainerSigilTable)this.inventorySlots;
        this.player=player;
    }
    @Override
    public void initGui() {
        super.initGui();
        this.inventorySlots.removeListener(this);
        this.inventorySlots.addListener(this);
    }
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        this.inventorySlots.removeListener(this);
    }
    @Override
    public void drawScreen(int x, int y, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(x, y, partialTicks);
        this.renderHoveredToolTip(x, y);
    }
    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i1) {
        GlStateManager.color(1.0f,1.0f,1.0f,1.0f);
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(this.guiLeft,this.guiTop,0,0,this.xSize,this.ySize);
    }
    @Override
    public void sendAllContents(@Nonnull Container container, @Nonnull NonNullList<ItemStack> nonNullList) {
        //this.sendSlotContents(container, 0, container.getSlot(0).getStack()); //from GuiRepair aka Anvil GUI.
    }
    @Override
    public void sendSlotContents(@Nonnull Container container, int i, @Nonnull ItemStack itemStack) {}
    @Override
    public void sendWindowProperty(@Nonnull Container container, int i, int i1) {}
    @Override
    public void sendAllWindowProperties(@Nonnull Container container, @Nonnull IInventory iInventory) {}
}
