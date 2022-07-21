package com.github.monoakuma.strykae.objects.magic;

import net.minecraft.potion.Potion;

public class Spell {
    private int cost;
    private String name;
    public Spell(int cost, String name) {
        this.cost=cost;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }
}
