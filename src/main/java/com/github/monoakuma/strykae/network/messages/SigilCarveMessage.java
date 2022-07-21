package com.github.monoakuma.strykae.network.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

import static com.github.monoakuma.strykae.Strykae.LOGGER;
import static com.github.monoakuma.strykae.Strykae.spellHandler;

public class SigilCarveMessage implements IMessage {
    private ItemStack tablet;
    private String spelltext;

    public SigilCarveMessage() {
    }

    public SigilCarveMessage(ItemStack tablet, String spelltext) {
        this.tablet=tablet;
        this.spelltext=spelltext;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.tablet=ByteBufUtils.readItemStack(buf);
        this.spelltext=ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf,this.tablet);
        ByteBufUtils.writeUTF8String(buf,this.spelltext);

    }
    public static class Handler implements IMessageHandler<SigilCarveMessage,IMessage> {
        @Override
        public IMessage onMessage(SigilCarveMessage message, MessageContext ctx) {
            LOGGER.info("carving");
            FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> {
                LOGGER.info("running with tablet:"+message.tablet.getDisplayName());
                ItemStack tablet = message.tablet;
                if (!tablet.isEmpty()) {
                    spellHandler.assignSpellNBT(tablet, message.spelltext);
                    LOGGER.info("carved");
                }
            });

            return null;
        }
    }
}
