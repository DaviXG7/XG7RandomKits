package com.xg7network.xg7randomkits.Module;

import com.xg7network.xg7randomkits.XG7RandomKits;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private XG7RandomKits plugin;
    private List<Module> modules = new ArrayList<>();

    public ModuleManager(XG7RandomKits plugin) {
        this.plugin = plugin;
    }

    public void loadModules() {

        for (Module module : modules) {
            module.onEnable();
        }
    }

    public void unloadModules() {
        for (Module module : modules) {
            module.onDisable();
        }
    }
}   
