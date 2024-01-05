package com.xg7network.xg7randomkits.Module;

import com.xg7network.xg7randomkits.Region.Handler.RegionManager;
import com.xg7network.xg7randomkits.Region.RegionTask;
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

        modules.add(new RegionManager(plugin));
        modules.add(new RegionTask(plugin));

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
