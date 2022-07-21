package com.github.monoakuma.strykae.objects.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.github.monoakuma.strykae.Strykae.spellHandler;

public class SpellSword extends ItemSword implements ISpellItem {
    public SpellSword(ToolMaterial toolMaterial) {
        super(toolMaterial);
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.hasTagCompound()) {
            if (!stack.getTagCompound().getString("StrykaeSpell").equals("")) {
                spellHandler.spellCast(stack,player);
                player.getCooldownTracker().setCooldown(this,20);
            }
        }
        return super.onItemRightClick(world,player,hand);
    }

    @Override
    public void writeSpell(ItemStack stack, String spelldata) {
        spellHandler.assignSpellNBT(stack,spelldata);
    }

    @Override
    public String readSpell(ItemStack stack) {
        if (stack.hasTagCompound()) return stack.getTagCompound().getString("StrykaeSpell");
        return "";
    }
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }
}
