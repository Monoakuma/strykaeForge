package com.github.monoakuma.strykae.objects.magic;

import net.minecraft.potion.Potion;

public class PotionSpell extends Spell{
    private final Potion effect;
    public PotionSpell(int cost, String name, Potion effect) {
        super(cost,name);

        this.effect=effect;
    }

    public Potion getEffect() {
        return effect;
    }
}
