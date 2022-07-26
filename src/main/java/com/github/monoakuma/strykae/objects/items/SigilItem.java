package com.github.monoakuma.strykae.objects.items;


import com.github.monoakuma.strykae.Strykae;
import com.github.monoakuma.strykae.network.messages.ShowSigilGUIMessage;
import com.github.monoakuma.strykae.objects.items.gui.GuiSigilTablet;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import static com.github.monoakuma.strykae.Strykae.TAB;
import static com.github.monoakuma.strykae.Strykae.spellHandler;

public class SigilItem extends Item {
    public SigilItem()
    {
        this.setCreativeTab(TAB);
        this.setMaxStackSize(1);
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
        if (hand.equals(EnumHand.MAIN_HAND)&&!stack.hasTagCompound() && player instanceof EntityPlayerMP) {
            Strykae.network.sendTo(new ShowSigilGUIMessage(),(EntityPlayerMP) player);
        }
        return super.onItemRightClick(world, player, hand);
    }
    public static class InternalContainer extends Container {
        @Override
        public boolean canInteractWith(@Nonnull EntityPlayer entityPlayer) {
            return false;
        }
        public InternalContainer(@Nonnull InventoryPlayer player) {
            this.addSlotToContainer(new Slot(player, player.currentItem, 96,200) {
                @Override
                public boolean canTakeStack(@Nonnull EntityPlayer player) {
                    return false;
                }
            });
        }
        public void carve(String spelltext) {
            spellHandler.assignSpellNBT(this.getSlot(0).getStack(),spelltext);
        }
    }
}
