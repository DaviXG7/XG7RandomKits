package com.xg7network.xg7randomkits.Module;

import com.xg7network.xg7randomkits.XG7RandomKits;
import org.bukkit.event.Listener;

public abstract class Module implements Listener {

    private XG7RandomKits plugin;


    public Module(XG7RandomKits plugin) {
        this.plugin = plugin;
    }

    public XG7RandomKits getPlugin() {
        return this.plugin;
    }

    public abstract void onEnable();

    public abstract void onDisable();
}