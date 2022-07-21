package com.github.monoakuma.strykae.network.messages;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class Message implements IMessage {
    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }
    public static class MessageHandler implements IMessageHandler<Message,IMessage> {
        @Override
        public IMessage onMessage(Message message, MessageContext ctx) {
            return null;
        }
    }
}
