package com.github.monoakuma.strykae.objects.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.github.monoakuma.strykae.Strykae.LOGGER;
import static com.github.monoakuma.strykae.Strykae.spellHandler;

public class SpellAxe extends ItemAxe implements ISpellItem {
    public SpellAxe(ToolMaterial toolMaterial) {
        super(toolMaterial);
    }
    public SpellAxe(ToolMaterial toolMaterial,float attack,float attackSpeed) {
        super(toolMaterial,attack,attackSpeed);
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
}
