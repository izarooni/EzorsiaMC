package com.izarooni;

import com.izarooni.events.BlockHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ezorsia extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BlockHandler(this), this);
        Component message = Component.text("Ezorsia : Aaaand we back!").color(TextColor.color(0x00FF00));

        getServer().getConsoleSender().sendMessage(message);
    }

    @Override
    public void onDisable() {
        Component message = Component.text("Ezorsia : Later!").color(TextColor.color(0xFF0000));
        getServer().getConsoleSender().sendMessage(message);
    }
}