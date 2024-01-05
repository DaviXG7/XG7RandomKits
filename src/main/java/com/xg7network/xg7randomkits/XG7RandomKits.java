package com.xg7network.xg7randomkits;

import com.xg7network.xg7randomkits.Configs.ConfigManager;
import com.xg7network.xg7randomkits.Configs.ConfigType;
import com.xg7network.xg7randomkits.DefaultCommands.TabCompleter;
import com.xg7network.xg7randomkits.Module.ModuleManager;
import com.xg7network.xg7randomkits.Region.Handler.RegionCommand;
import com.xg7network.xg7randomkits.Region.Handler.RegionManager;
import com.xg7network.xg7randomkits.Region.Region;
import com.xg7network.xg7randomkits.Region.RegionEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class XG7RandomKits extends JavaPlugin {

    public static String prefix = ChatColor.BLUE + "[XG7 " + ChatColor.RED + "R" + ChatColor.GOLD + "K" + ChatColor.YELLOW + "] " + ChatColor.RESET;
    private static XG7RandomKits plugin;
    private static ModuleManager manager;


    public static ConfigManager configManager;
    public static boolean placeholderapi;



    @Override
    public void onEnable() {

        this.getServer().getConsoleSender().sendMessage(prefix + "Loading...");
        this.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "__   __  ___   ______     " + ChatColor.RED + " _____   " + ChatColor.GOLD + "_  __");
        this.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "\\ \\ / / / __| |___   /   " + ChatColor.RED + " |  __ \\ " + ChatColor.GOLD + "| |/ /");
        this.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + " \\ v / | |  _     / /    " + ChatColor.RED + " | |__) |" + ChatColor.GOLD + "|   / ");
        this.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + " / . \\ | |_| |   / /     " + ChatColor.RED + " |  _  / " + ChatColor.GOLD + "|   \\ ");
        this.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "/_/ \\_\\ \\____|  /_/      " + ChatColor.RED + " |_| \\_\\ " + ChatColor.GOLD + "|_|\\_\\");

        try {
            Class.forName("org.spigotmc.SpigotConfig");
        } catch (ClassNotFoundException var4) {
            this.getServer().getConsoleSender().sendMessage("                       SPIGOT NOT DETECTED                     ");
            this.getServer().getConsoleSender().sendMessage("THIS PLUGIN NEEDS SPIGOT TO WORK!                              ");
            this.getServer().getConsoleSender().sendMessage("DOWNLOAD HERE: https://www.spigotmc.org/wiki/spigot-installation/.");
            this.getServer().getConsoleSender().sendMessage("THE PLUGIN WILL DISABLE!                                         ");
            this.getPluginLoader().disablePlugin(this);
            return;
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {

            this.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "It's recommended to install PlaceholderAPI");
            this.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "to get more resourses!");


        }

        plugin = this;

        placeholderapi = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;


        this.getServer().getConsoleSender().sendMessage(prefix + "Loading configs... ");
        configManager = new ConfigManager();
        for (ConfigType type : ConfigType.values()) configManager.loadConfig(type);
        RegionManager.loadRegion();

        this.getServer().getConsoleSender().sendMessage(prefix + "Loading module... ");
        manager = new ModuleManager(this);
        manager.loadModules();

        this.getServer().getConsoleSender().sendMessage(prefix + "Loading events: ");
        this.getServer().getPluginManager().registerEvents(new RegionManager(this), this);
        this.getServer().getPluginManager().registerEvents(new RegionEvents(), this);

        this.getServer().getConsoleSender().sendMessage(prefix + "Loading commands: ");
        this.getCommand("xg7rkregion").setExecutor(new RegionCommand());
        this.getCommand("xg7rkregion").setTabCompleter(new TabCompleter());
        this.getServer().getPluginManager().registerEvents(new RegionManager(this), this);

        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static XG7RandomKits getPlugin() {
        return plugin;
    }
}
