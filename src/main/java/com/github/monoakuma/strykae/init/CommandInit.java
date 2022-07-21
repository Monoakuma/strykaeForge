package com.github.monoakuma.strykae.init;

import com.github.monoakuma.strykae.commands.CommandCast;
import com.github.monoakuma.strykae.commands.CommandNotitia;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import static com.github.monoakuma.strykae.Strykae.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class CommandInit {
    public static void serverRegistries(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandNotitia());
        event.registerServerCommand(new CommandCast());
    }
}
