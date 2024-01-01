package com.xg7network.xg7randomkits.Region.Handler;

import com.xg7network.xg7randomkits.Configs.ErrorMessages;
import com.xg7network.xg7randomkits.Utils.PluginUtil;
import com.xg7network.xg7randomkits.Utils.Text.TextUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class RegionCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ErrorMessages.NOT_PLAYER.getMessage());
            return true;
        }

        Player player = (Player) commandSender;

        if (!PluginUtil.isInWorld(player)) {
            player.sendMessage(ErrorMessages.NOT_IN_WORLD.getMessage());
        }

        if (strings.length == 0) {
            RegionManager.setToRegionMode(player, RegionCase.DEFAULT);
            return true;
        }

        switch (strings[0]) {
            case "set" -> {
                RegionManager.setToRegionMode(player, RegionCase.SET);
            }
            case "save" -> {
                RegionManager.action(RegionCase.CHECK, player);
            }
            case "cancel" -> {
                RegionManager.removeToRegionMode(player);
            }
            case "delete" -> {
                RegionManager.setToRegionMode(player, RegionCase.REMOVE);
            }
        }
        return true;

    }




}
