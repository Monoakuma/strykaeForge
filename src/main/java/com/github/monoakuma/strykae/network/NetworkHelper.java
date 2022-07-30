package com.github.monoakuma.strykae.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NetworkHelper {
    public static EntityPlayer getSidedPlayer(MessageContext ctx)
    {
        return ctx.side == Side.SERVER ? ctx.getServerHandler().player : FMLClientHandler.instance().getClientPlayerEntity();
    }
    public static IThreadListener getThreadListener(MessageContext ctx) {
        return ctx.side == Side.SERVER ? FMLCommonHandler.instance().getWorldThread(ctx.netHandler) : getClientThreadListener();
    }

    @SideOnly(Side.CLIENT)
    public static IThreadListener getClientThreadListener() {
        return Minecraft.getMinecraft();
    }
}
