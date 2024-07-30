package com.izarooni.commands;

import com.izarooni.Ezorsia;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.registrar.ReloadableRegistrarEvent;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.plugin.Plugin;

public class CommandHandler {
    public static void initialize(final Ezorsia ezorsia) {
        LifecycleEventManager<Plugin> manager = ezorsia.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            registerCommands(ezorsia, event);
        });

        Component message = Component.text("Ezorsia : Commands registered").color(TextColor.color(0x00FF00));
        ezorsia.getServer().getConsoleSender().sendMessage(message);
    }

    private static void registerCommands(Ezorsia ezorsia, ReloadableRegistrarEvent<Commands> event) {
        Commands commands = event.registrar();

        commands.register(
                "reload",
                "Reloads the plugin configuration",
                new ReloadCommand(ezorsia)
        );
    }
}
