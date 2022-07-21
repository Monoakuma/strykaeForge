package com.github.monoakuma.strykae.objects.blocks;

import com.github.monoakuma.strykae.init.BlockInit;
import com.github.monoakuma.strykae.init.ItemInit;
import com.github.monoakuma.strykae.init.SoundInit;
import com.github.monoakuma.strykae.objects.items.SigilItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import static com.github.monoakuma.strykae.Strykae.*;
public class SigilTableBlock extends Block {
    public SigilTableBlock() {
        super(Material.IRON);
        setCreativeTab(TAB);
    }
    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState blockState, @Nonnull Random random, int i) {
        return Item.getItemFromBlock(BlockInit.SIGIL_TABLE);
    }
    @Override
    public boolean onBlockActivated(World world, @Nonnull BlockPos blockPos, @Nonnull IBlockState blockState, @Nonnull EntityPlayer player, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!world.isRemote) {
            player.openGui(INSTANCE,0,world,blockPos.getX(),blockPos.getY(),blockPos.getZ());
        }
        return true;
    }
    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState blockState) {
        return super.createTileEntity(world, blockState);
    }
    @Override
    public boolean hasTileEntity(IBlockState blockState) {
        return true;
    }
    @Override
    public void onBlockPlacedBy(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState blockState, @Nonnull EntityLivingBase entity, @Nonnull ItemStack stack) {
        super.onBlockPlacedBy(world, pos, blockState, entity, stack);
    }
    @Nullable
    public TileEntity createNewTileEntity(@Nonnull World world, int i) {
        return new SigilTableInteract();
    }
//--------------------------------------------------------------------------------------------------------------------//
    public static class SigilTableInteract extends TileEntity implements IInteractionObject {
        public SigilTableInteract(){
        }
        @Nonnull
        @Override
        public Container createContainer(@Nonnull InventoryPlayer inventoryPlayer, @Nonnull EntityPlayer entityPlayer) {
            return new ContainerSigilTable(inventoryPlayer, entityPlayer.world,this.pos);
        }
        @Nonnull
        @Override
        public String getGuiID() {
            return "strykae:sigil_table";
        }
        @Nonnull
        @Override
        public String getName() {
            return "sigil_table";
        }
        @Override
        public boolean hasCustomName() {
            return false;
        }
        @Nonnull
        @Override
        public ITextComponent getDisplayName() {
            return new TextComponentTranslation(BlockInit.SIGIL_TABLE.getTranslationKey() + ".name", new Object[0]);
        }
    }
//--------------------------------------------------------------------------------------------------------------------//
    public static class ContainerSigilTable extends Container {
        private final World world;
        private IInventory input;
        private IInventory output;
        private final BlockPos pos;
        public ContainerSigilTable(InventoryPlayer inventoryPlayer, World world,BlockPos pos) {
            this.world=world;
            this.pos=pos;
            input=new InventoryBasic("Sigil",true,2);
            output=new InventoryCraftResult();
            this.addSlotToContainer(new Slot(this.input,0,17,17) {
                @Override
                public boolean isItemValid(@Nonnull ItemStack itemStack) {
                    return (itemStack.getItem()instanceof SigilItem);
                }
                @Override
                public void onSlotChanged() {
                    super.onSlotChanged();
                    onCraftMatrixChanged(input);
                }
            });
            this.addSlotToContainer(new Slot(this.input,1,17,39) {
                @Override
                public boolean isItemValid(@Nonnull ItemStack itemStack) {
                    return (itemStack.getItem()instanceof ItemSword);
                }
                @Override
                public void onSlotChanged() {
                    super.onSlotChanged();
                    onCraftMatrixChanged(input);
                }
            });
            this.addSlotToContainer(new Slot(this.output,2,17,61) {
                @Override
                public boolean isItemValid(@Nonnull ItemStack stack) {return false;}

                @Override
                public boolean canTakeStack(@Nonnull EntityPlayer player) {
                    return input.getStackInSlot(0).getItem() instanceof SigilItem&&input.getStackInSlot(1).getItem() instanceof ItemSword;
                }
                @Nonnull
                @Override
                public ItemStack onTake(@Nonnull EntityPlayer player, @Nonnull ItemStack stack) {
                    ContainerSigilTable.this.input.setInventorySlotContents(0,ItemStack.EMPTY);
                    ContainerSigilTable.this.input.setInventorySlotContents(1,ItemStack.EMPTY);
                    ContainerSigilTable.this.world.playSound(player,player.getPosition(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS,1.0f,0.43f);
                    return super.onTake(player, stack);
                }
                @Override
                public void onSlotChanged() {
                    super.onSlotChanged();
                    onCraftMatrixChanged(input);
                }
            });
            int k;
            for(k = 0; k < 3; ++k) {
                for(int j = 0; j < 9; ++j) {
                    this.addSlotToContainer(new Slot(inventoryPlayer, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
                }
            }
            for(k = 0; k < 9; ++k) {
                this.addSlotToContainer(new Slot(inventoryPlayer, k, 8 + k * 18, 142));
            }
        }
        @Override
        public boolean canInteractWith(@Nonnull EntityPlayer entityPlayer) {
            return this.input.isUsableByPlayer(entityPlayer);
        }
        @Override
        public void addListener(@Nonnull IContainerListener listener) {
            super.addListener(listener);
            listener.sendAllWindowProperties(this,this.input);
        }
        @Override
        public void detectAndSendChanges() {
            super.detectAndSendChanges();
            for (int i=0;i<this.listeners.size();i++) {
                IContainerListener listener = (IContainerListener) this.listeners.get(i);
            }
        }
        @Override
        public void onContainerClosed(@Nonnull EntityPlayer player) {
            super.onContainerClosed(player);
            if (!this.world.isRemote) {
                this.clearContainer(player, this.world, this.input);
            }

    }

        @Override
        public void onCraftMatrixChanged(@Nonnull IInventory inventory) {
            super.onCraftMatrixChanged(inventory);
            if (inventory==this.input) {
                ItemStack sigil = this.input.getStackInSlot(0);
                ItemStack tool = this.input.getStackInSlot(1);
                if (!sigil.isEmpty()&&!tool.isEmpty()) {
                    ItemStack spelltool = ItemStack.EMPTY;
                    if (sigil.hasTagCompound()) {
                        if (sigil.getTagCompound().hasKey("StrykaeSpell")) {
                            if (tool.getItem() == Items.GOLDEN_SWORD) {
                                spelltool=new ItemStack(ItemInit.GOLD_SPELLSWORD);
                            } else if (tool.getItem() == Items.IRON_SWORD) {
                                spelltool=new ItemStack(ItemInit.IRON_SPELLSWORD);
                            } else if (tool.getItem() == Items.DIAMOND_SWORD) {
                                spelltool=new ItemStack(ItemInit.DIAMOND_SPELLSWORD);
                            }
                            spellHandler.assignSpellNBT(spelltool,sigil.getTagCompound().getString("StrykaeSpell"));
                        }
                    }
                    this.output.setInventorySlotContents(2,spelltool);
                }
                else {
                    this.output.setInventorySlotContents(2,ItemStack.EMPTY);
                }
            }
        }
    }
}
