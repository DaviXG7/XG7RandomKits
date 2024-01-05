package com.xg7network.xg7randomkits.Region.Handler;

import com.xg7network.xg7randomkits.Configs.ErrorMessages;
import com.xg7network.xg7randomkits.Configs.PermissionType;
import com.xg7network.xg7randomkits.Region.RegionTask;
import com.xg7network.xg7randomkits.Utils.PluginUtil;
import com.xg7network.xg7randomkits.Utils.Text.TextUtil;
import com.xg7network.xg7randomkits.XG7RandomKits;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

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
            if (PluginUtil.hasPermission(player, PermissionType.REGION, ErrorMessages.NO_PEMISSION.getMessage())) RegionManager.setToRegionMode(player, RegionCase.DEFAULT);

            return true;
        }

        switch (strings[0]) {
            case "set" -> {
                if (PluginUtil.hasPermission(player, PermissionType.REGION_SET, ErrorMessages.NO_PEMISSION.getMessage())) {
                    RegionManager.setToRegionMode(player, RegionCase.SET);
                }
            }


            case "save" -> {
                if (PluginUtil.hasPermission(player, PermissionType.REGION_SET, ErrorMessages.NO_PEMISSION.getMessage())) {
                    RegionManager.setToRegionMode(player, RegionCase.SET);
                    RegionManager.action(RegionCase.SAVE, player);
                }
            }

            case "check" -> {
                if (PluginUtil.hasPermission(player, PermissionType.REGION_CHECK, ErrorMessages.NO_PEMISSION.getMessage())) {
                    RegionManager.action(RegionCase.CHECK, player);
                }
            }

            case "cancel" ->  {
                if (PluginUtil.hasPermission(player, PermissionType.REGION, ErrorMessages.NO_PEMISSION.getMessage())) {
                    if (RegionManager.contains(player))
                        RegionManager.removeToRegionMode(player);
                    else
                        TextUtil.send("&cYou are not in region mode!", player);
                }
            }

            case "delete" -> {
                if (PluginUtil.hasPermission(player, PermissionType.REGION_DELETE, ErrorMessages.NO_PEMISSION.getMessage())) {
                    if (RegionManager.getRegion() == null) {
                        TextUtil.sendActionBar("&cThe region does not exist!", player);
                        break;
                    }

                    RegionManager.action(RegionCase.REMOVE, player);
                }
            }

            case "reset" -> {
                if (PluginUtil.hasPermission(player, PermissionType.REGION_DELETE, ErrorMessages.NO_PEMISSION.getMessage())) {
                    RegionTask.resetRegion(XG7RandomKits.getPlugin());
                }
            }



        }
        return true;

    }




}
