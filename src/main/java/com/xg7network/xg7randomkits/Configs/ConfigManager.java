package com.xg7network.xg7randomkits.Configs;

import com.xg7network.xg7randomkits.XG7RandomKits;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class ConfigManager {

    private static FileConfiguration config = null;
    private static  FileConfiguration drops = null;
    private static FileConfiguration data = null;
    private static  FileConfiguration message = null;
    private static  FileConfiguration kits = null;

    private static File Cfile = null;
    private static File Dtfile = null;
    private static File Mfile = null;
    private static File Kfile = null;
    private static File Dfile = null;


    public void reloadConfig(ConfigType type) {

        switch (type) {
            case CONFIG:
                if (Cfile == null) loadConfig(type);

                config = YamlConfiguration.loadConfiguration(Cfile);

                return;

            case DATA:
                if (Dtfile == null) loadConfig(type);

                data = YamlConfiguration.loadConfiguration(Dtfile);

                return;

            case MESSAGES:
                if (Mfile == null) loadConfig(type);

                message = YamlConfiguration.loadConfiguration(Mfile);


                return;

            case DROPS:

                if (Dfile == null) loadConfig(type);

                drops = YamlConfiguration.loadConfiguration(Dfile);

            case KITS:

                if (Kfile == null) loadConfig(type);

                kits = YamlConfiguration.loadConfiguration(Kfile);

        }
    }

    public FileConfiguration getConfig(ConfigType type) {

        return switch (type) {
            case CONFIG -> {
                if (config == null) {
                    reloadConfig(type);
                }
                yield config;
            }
            case DATA -> {
                if (data == null) {
                    reloadConfig(type);
                }
                yield data;
            }
            case MESSAGES -> {
                if (Mfile == null) {
                    reloadConfig(type);
                }
                yield message;
            }
            case DROPS -> {
                if (drops == null) {
                    reloadConfig(type);
                }
                yield drops;
            }
            case KITS -> {
                if (kits == null) {
                    reloadConfig(type);
                }
                yield kits;
            }
        };
    }

    public void saveConfig(ConfigType type) {
        switch (type) {

            case CONFIG:

                if (config == null || Cfile == null)
                    return;
                try {
                    this.getConfig(type).save(Cfile);
                } catch (IOException e) {
                    Bukkit.getLogger().log(Level.SEVERE, "Não foi possível carregar o arquivo: " + Cfile, e);
                }

                return;

            case DATA:

                if (data == null || Dtfile == null)
                    return;
                try {
                    this.getConfig(type).save(Dtfile);
                } catch (IOException e) {
                    Bukkit.getLogger().log(Level.SEVERE, "Não foi possível carregar o arquivo: " + Dtfile, e);
                }

                return;

            case MESSAGES:

                if (Mfile == null || message == null)
                    return;
                try {
                    this.getConfig(type).save(Mfile);
                } catch (IOException e) {
                    Bukkit.getLogger().log(Level.SEVERE, "Não foi possível carregar o arquivo: " + Mfile, e);
                }

                return;

            case DROPS:

                if (drops == null || Dfile == null)
                    return;
                try {
                    this.getConfig(type).save(Dfile);
                } catch (IOException e) {
                    Bukkit.getLogger().log(Level.SEVERE, "Não foi possível carregar o arquivo: " + Dfile, e);
                }

                return;

            case KITS:

                if (Kfile == null || kits == null)
                    return;
                try {
                    this.getConfig(type).save(Kfile);
                } catch (IOException e) {
                    Bukkit.getLogger().log(Level.SEVERE, "Não foi possível carregar o arquivo: " + Kfile, e);
                }

        }
    }

    public void loadConfig(ConfigType type) {
        switch (type) {
            case CONFIG:

                if (Cfile == null) {
                    Cfile = new File(XG7RandomKits.getPlugin().getDataFolder(), type.getConfig());
                }
                if (!Cfile.exists()) {
                    XG7RandomKits.getPlugin().saveResource(type.getConfig(), false);
                }

                return;

            case DATA:

                if (Dtfile == null) {
                    Dtfile = new File(XG7RandomKits.getPlugin().getDataFolder(), type.getConfig());
                }
                if (!Dtfile.exists()) {
                    XG7RandomKits.getPlugin().saveResource(type.getConfig(), false);
                }

                return;

            case MESSAGES:

                if (Mfile == null) {
                    Mfile = new File(XG7RandomKits.getPlugin().getDataFolder(), type.getConfig());
                }
                if (!Mfile.exists()) {
                    XG7RandomKits.getPlugin().saveResource(type.getConfig(), false);
                }

                return;

            case DROPS:

                if (Dfile == null) {
                    Dfile = new File(XG7RandomKits.getPlugin().getDataFolder(), type.getConfig());
                }
                if (!Dfile.exists()) {
                    XG7RandomKits.getPlugin().saveResource(type.getConfig(), false);
                }

                return;

            case KITS:

                if (Kfile == null) {
                    Kfile = new File(XG7RandomKits.getPlugin().getDataFolder(), type.getConfig());
                }
                if (!Kfile.exists()) {
                    XG7RandomKits.getPlugin().saveResource(type.getConfig(), false);
                }

                return;

        }
    }
}
