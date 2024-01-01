package com.xg7network.xg7randomkits.Module.Region.Handler;

import com.xg7network.xg7randomkits.Configs.ErrorMessages;
import com.xg7network.xg7randomkits.Utils.PluginUtil;
import com.xg7network.xg7randomkits.Utils.Text.TextUtil;
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
            RegionManager.setToRegionMode(player, RegionCase.DEFAULT);
            return true;
        }

        switch (strings[0]) {
            case "set" -> RegionManager.setToRegionMode(player, RegionCase.SET);


            case "save" -> {
                RegionManager.setToRegionMode(player, RegionCase.SET);
                RegionManager.action(RegionCase.SAVE, player);
            }

            case "check" -> RegionManager.action(RegionCase.CHECK, player);

            case "cancel" ->  {
                if (RegionManager.contains(player))
                    RegionManager.removeToRegionMode(player);
                else
                    TextUtil.send("&cYou are not in region mode!", player);
            }

            case "delete" -> {
                if (RegionManager.getRegion() == null) {
                    TextUtil.sendActionBar("&cThe region does not exist!", player);
                    break;
                }
                RegionManager.action(RegionCase.REMOVE, player);
            }


        }
        return true;

    }




}
