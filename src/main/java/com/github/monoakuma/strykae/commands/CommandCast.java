package com.github.monoakuma.strykae.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import static com.github.monoakuma.strykae.Strykae.LOGGER;
import static com.github.monoakuma.strykae.Strykae.spellHandler;

public class CommandCast extends CommandBase {
    @Override
    public String getName() {
        return "spellcast";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "spellcast <exaggerations as integers> <spellfocus+spellspecifier>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        LOGGER.info(args[0]+" "+args[1]);
        if (sender.getCommandSenderEntity() instanceof EntityPlayer) {
            if (spellHandler.spellCast(args[0]+" "+args[1],(EntityPlayer)sender.getCommandSenderEntity())) {
                sender.sendMessage(new TextComponentString("Spell success."));
            } else {
                sender.sendMessage(new TextComponentString("Spell failed."));
            }
        }

    }
}
