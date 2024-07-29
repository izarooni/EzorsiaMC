package com.izarooni.entity;

import com.izarooni.Ezorsia;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class User {

    private final Player player;

    public User(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void load(Ezorsia ezorsia) {
        File file = new File(ezorsia.getDataFolder(), player.getUniqueId().toString());
        if (!file.exists()) return;
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        // load data from config
    }

    public void save(Ezorsia ezorsia) {
        File file = new File(ezorsia.getDataFolder(), player.getUniqueId().toString());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                Component message = Component.text("Ezorsia : Failed to create user file for " + player.getName()).color(TextColor.color(0xFF0000));
                ezorsia.getServer().getConsoleSender().sendMessage(message);
                return;
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        // save data to config
        try {
            config.save(file);
        } catch (IOException e) {
            Component message = Component.text("Ezorsia : Failed to save user file for " + player.getName()).color(TextColor.color(0xFF0000));
            ezorsia.getServer().getConsoleSender().sendMessage(message);
        }
    }
}
