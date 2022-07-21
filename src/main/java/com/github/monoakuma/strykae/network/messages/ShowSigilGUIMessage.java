package com.github.monoakuma.strykae.network.messages;

import com.github.monoakuma.strykae.Strykae;
import com.github.monoakuma.strykae.core.ICasterCap;
import com.github.monoakuma.strykae.objects.items.gui.GuiSigilTablet;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import static com.github.monoakuma.strykae.core.CasterCap.CDATA_CAPABILITY;
import static com.github.monoakuma.strykae.core.CasterCap.getCaster;

public class ShowSigilGUIMessage implements IMessage {
    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }
    public static class Handler implements IMessageHandler<ShowSigilGUIMessage,IMessage> {
        @Override
        public IMessage onMessage(ShowSigilGUIMessage message, MessageContext ctx) {
            IThreadListener mainThread = Minecraft.getMinecraft();
            EntityPlayer player = Minecraft.getMinecraft().player;
            mainThread.addScheduledTask(() -> {
                player.openGui(Strykae.INSTANCE, 1, player.world, 0, 0, 0);
            });
            return null;
        }

    }
}
