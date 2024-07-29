package com.izarooni.events;

import com.izarooni.Ezorsia;
import com.izarooni.entity.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class PlayerHandler extends PluginEventHandler {
    public PlayerHandler(Ezorsia ezorsia) {
        super(ezorsia);
    }

    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();

        User user = new User(player);
        user.load(getEzorsia());
        getEzorsia().getUsers().put(name, user);
    }

    @EventHandler
    public void onPlayerQuit(org.bukkit.event.player.PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();

        User user = getEzorsia().getUsers().remove(name);
        if (user != null) {
            user.save(getEzorsia());
        }
    }
}
