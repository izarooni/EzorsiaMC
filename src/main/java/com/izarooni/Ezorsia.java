package com.izarooni;

import com.izarooni.commands.CommandHandler;
import com.izarooni.entity.User;
import com.izarooni.events.BlockHandler;
import com.izarooni.events.PlayerHandler;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ConcurrentHashMap;

public final class Ezorsia extends JavaPlugin {

    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>(11);

    @Override
    public void onEnable() {
        // create config files
        saveResource("config.yml", false);
        saveResource("veins.yml", false);

        CommandHandler.initialize(this);

        getServer().getPluginManager().registerEvents(new BlockHandler(this), this);
        getServer().getPluginManager().registerEvents(new PlayerHandler(this), this);

        Component message = Component
                .text("Ezorsia : Aaaand we back!")
                .color(TextColor.color(0x00FF00));
        getServer().getConsoleSender().sendMessage(message);
    }

    @Override
    public void onDisable() {
        for (User user : users.values()) {
            user.save(this);
        }

        Component message = Component
                .text("Ezorsia : Later!")
                .color(TextColor.color(0xFF0000));
        getServer().getConsoleSender().sendMessage(message);
    }

    public ConcurrentHashMap<String, User> getUsers() {
        return users;
    }
}
