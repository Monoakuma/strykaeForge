package com.github.monoakuma.strykae.network.messages;

import com.github.monoakuma.strykae.network.NetworkHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import static com.github.monoakuma.strykae.Strykae.LOGGER;
import static com.github.monoakuma.strykae.Strykae.spellHandler;

public class SigilCarveMessage implements IMessage {
    private String spelltext;
    private ItemStack tablet;

    public SigilCarveMessage() {
    }

    public SigilCarveMessage(ItemStack tablet,String spelltext) {
        this.spelltext=spelltext;
        this.tablet=tablet;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.spelltext=ByteBufUtils.readUTF8String(buf);
        this.tablet=ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf,this.spelltext);
        ByteBufUtils.writeItemStack(buf,this.tablet);

    }
    public static class Handler implements IMessageHandler<SigilCarveMessage,IMessage> {
        @Override
        public IMessage onMessage(SigilCarveMessage message, MessageContext ctx) {
            LOGGER.info("carving at:"+ctx.side.name());
            LOGGER.info("carving 0");
            NetworkHelper.getThreadListener(ctx).addScheduledTask(() -> {
                ItemStack tablet = NetworkHelper.getSidedPlayer(ctx).getHeldItemMainhand();
                LOGGER.info("carving 1");
                if (!tablet.isEmpty()) {
                    spellHandler.assignSpellNBT(tablet, message.spelltext);
                    LOGGER.info("carving 2");
                }
            });
            return null;
        }
    }
}
