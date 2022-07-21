package com.github.monoakuma.strykae.objects.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public interface ISpellItem {
    ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand);

    void writeSpell(ItemStack stack, String spelldata);

    String readSpell(ItemStack stack);
}
