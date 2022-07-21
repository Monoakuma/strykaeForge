package com.github.monoakuma.strykae.objects.magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nullable;
import java.util.UUID;

public interface IFamiliar {
    @Nullable
    UUID getOwnerId();
    @Nullable
    Entity getOwner();

    void setOwnerId(UUID ownerUUID);

    void setOwner(EntityPlayer owner);
}
