package com.github.monoakuma.strykae.commands;

import com.github.monoakuma.strykae.core.CasterCap;
import com.github.monoakuma.strykae.core.ICasterCap;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextComponentString;

import java.util.Objects;

import static com.github.monoakuma.strykae.Strykae.LOGGER;
import static com.github.monoakuma.strykae.core.CasterCap.CDATA_CAPABILITY;
import static com.github.monoakuma.strykae.core.CasterCap.getCaster;


public class CommandNotitia extends CommandBase {
    @Override
    public String getName() {
        return "notitia";
    }

    @Override
    public String getUsage(ICommandSender iCommandSender) {
        return "returns a caster's data.";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        try
        {
            if (sender.getCommandSenderEntity() instanceof EntityPlayer) {
                EntityPlayer caster = (EntityPlayer)sender.getCommandSenderEntity();
                ICasterCap casterCap = getCaster(caster);
                if (Objects.equals(args[0], "check")){
                    sender.sendMessage(new TextComponentString(String.format("Mana:%d",casterCap.getMana())));
                    sender.sendMessage(new TextComponentString(String.format("Lives:%d",casterCap.getLives())));
                    sender.sendMessage(new TextComponentString("Human:"+casterCap.getIsHuman()));
                    sender.sendMessage(new TextComponentString(String.format("Hygiene:%d",casterCap.getHygiene())));
                }
                else if (Objects.equals(args[0], "set")) {
                    if (Objects.equals(args[1],"mana")) casterCap.setMana(Integer.parseInt(args[2]));
                    if (Objects.equals(args[1],"lives")) casterCap.setLives(Integer.parseInt(args[2]));
                    if (Objects.equals(args[1],"hygiene")) casterCap.setHygiene(Integer.parseInt(args[2]));
                    if (Objects.equals(args[1],"human")) casterCap.setIsHuman(Boolean.parseBoolean(args[2]));
                    sender.sendMessage(new TextComponentString(String.format("Mana:%d",casterCap.getMana())));
                    sender.sendMessage(new TextComponentString(String.format("Lives:%d",casterCap.getLives())));
                    sender.sendMessage(new TextComponentString("Human:"+casterCap.getIsHuman()));
                }
            }
        } catch (NullPointerException e){
            LOGGER.info("no caster data found");
            LOGGER.error(e);
        }
    }
}
