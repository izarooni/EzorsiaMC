package com.izarooni.events;

import com.izarooni.Ezorsia;
import org.bukkit.event.Listener;

public class PluginEventHandler implements Listener {

    private final Ezorsia ezorsia;

    public PluginEventHandler(Ezorsia ezorsia) {
        this.ezorsia = ezorsia;
    }

    public Ezorsia getEzorsia() {
        return ezorsia;
    }
}
