package com.xg7network.xg7randomkits.Utils;


import com.xg7network.xg7randomkits.Configs.ConfigType;
import com.xg7network.xg7randomkits.Configs.PermissionType;
import com.xg7network.xg7randomkits.Utils.Text.TextUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static com.xg7network.xg7randomkits.XG7RandomKits.configManager;
import static com.xg7network.xg7randomkits.XG7RandomKits.placeholderapi;

public class PluginUtil {

    public static boolean isInWorld(World world) {
        return configManager.getConfig(ConfigType.CONFIG).getString("enabled-worlds").contains(world.getName());
    }
    public static boolean isInWorld(Player player) {
        return configManager.getConfig(ConfigType.CONFIG).getString("enabled-worlds").contains(player.getWorld().getName());
    }

    public static String setPlaceHolders(String s, Player p) {
        return placeholderapi ? PlaceholderAPI.setPlaceholders(p, s) : s;
    }

    public static boolean hasPermission(Player player, PermissionType permissionType, String message) {
        if (player.hasPermission(permissionType.getPerm())) return true;
        else {
            TextUtil.send(message, player);
            return false;
        }
    }

    public static boolean hasPermission(CommandSender sender, PermissionType permissionType, String message) {
        if (sender instanceof Player) {
            if (sender.hasPermission(permissionType.getPerm())) return true;
            else {
                TextUtil.send(message, (Player) sender);
                return false;
            }
        }
        return true;
    }
}
