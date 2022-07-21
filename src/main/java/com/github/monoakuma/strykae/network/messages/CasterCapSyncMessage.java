package com.github.monoakuma.strykae.network.messages;

import com.github.monoakuma.strykae.core.ICasterCap;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import static com.github.monoakuma.strykae.Strykae.LOGGER;
import static com.github.monoakuma.strykae.core.CasterCap.CDATA_CAPABILITY;
import static com.github.monoakuma.strykae.core.CasterCap.getCaster;

public class CasterCapSyncMessage implements IMessage {
    public String identifier;
    public NBTTagCompound data;

    public CasterCapSyncMessage() {}

    public CasterCapSyncMessage(String identifier,NBTTagCompound data) {
        if (data == null) {
            throw new IllegalArgumentException("NBTData cannot be null!");
        }
        this.identifier=identifier;
        this.data=data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.identifier = ByteBufUtils.readUTF8String(buf);
        this.data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.identifier);
        ByteBufUtils.writeTag(buf, this.data);
    }
    //handler
    public static class Handler implements IMessageHandler<CasterCapSyncMessage,IMessage> {
        @Override
        public IMessage onMessage(CasterCapSyncMessage message, MessageContext ctx) {
            IThreadListener mainThread = Minecraft.getMinecraft();
            EntityPlayer player = Minecraft.getMinecraft().player;
            mainThread.addScheduledTask(() -> {
                ICasterCap found = getCaster(player);
                NBTTagList list = new NBTTagList();
                list.appendTag(message.data);
                CDATA_CAPABILITY.getStorage().readNBT(CDATA_CAPABILITY, found, null, list);
            });

            return null;
        }
    }


}