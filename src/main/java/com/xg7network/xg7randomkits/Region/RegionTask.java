package com.xg7network.xg7randomkits.Region;

import com.xg7network.xg7randomkits.Configs.ConfigType;
import com.xg7network.xg7randomkits.Module.Module;
import com.xg7network.xg7randomkits.Region.Handler.RegionManager;
import com.xg7network.xg7randomkits.Utils.PluginUtil;
import com.xg7network.xg7randomkits.Utils.Text.TextUtil;
import com.xg7network.xg7randomkits.XG7RandomKits;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import static com.xg7network.xg7randomkits.XG7RandomKits.configManager;

public class RegionTask extends Module {

    private Timer timer;
    public RegionTask(XG7RandomKits plugin) {
        super(plugin);
    }

    public static void resetRegion(XG7RandomKits plugin) {

        if (RegionManager.getRegion() != null) {


            Bukkit.getConsoleSender().sendMessage("Reseting region...");

            Region region = RegionManager.getRegion();

            AtomicInteger layer = new AtomicInteger(region.getTopPos().getBlockY());
            AtomicInteger layerFloor = new AtomicInteger(region.getCornerFloorPos().getBlockY());
            Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                if (layer.get() != region.getCornerFloorPos().getBlockY()) {
                    for (double x = region.getTopPos().getX(); x >= region.getOtherCornerFloorPos().getBlockX(); x--) {
                        for (double z = region.getTopPos().getZ(); z >= region.getOtherCornerFloorPos().getBlockZ(); z--) {
                            int denominador = region.getTopPos().getBlockY() - region.getOtherCornerFloorPos().getBlockY();

                            double percentage = (denominador != 0) ? 100 - (((double) (layer.get() - region.getOtherCornerFloorPos().getBlockY()) / denominador)) * 100 : 0;
                            for (Player player : Bukkit.getOnlinePlayers()) if (PluginUtil.isInWorld(player))
                                TextUtil.sendActionBar(String.format("&cReseting region: &b%.2f%%", percentage), player);

                            Location block = new Location(region.getWorld(), (int) x, layer.get(), (int) z);

                            if (!block.getBlock().getType().equals(Material.AIR))
                                block.getBlock().setType(Material.AIR);
                        }
                    }
                    layer.getAndDecrement();
                }

                if (layer.get() <= region.getCornerFloorPos().getBlockY()) {
                    for (double x = region.getCornerFloorPos().getBlockX(); x >= region.getBottomPos().getBlockX(); x--) {
                        for (double z = region.getCornerFloorPos().getBlockZ(); z >= region.getBottomPos().getBlockZ(); z--) {

                            int denominador = region.getCornerFloorPos().getBlockY() - region.getBottomPos().getBlockY();
                            double percentage = (denominador != 0) ? 100 - (((double) (layerFloor.get() - region.getBottomPos().getBlockY()) / denominador) * 100) : 0;
                            for (Player player : Bukkit.getOnlinePlayers()) if (PluginUtil.isInWorld(player))
                                TextUtil.sendActionBar(String.format("&cReseting floor: &b%.2f%%", percentage), player);

                            Location block = new Location(region.getWorld(), (int) x, layerFloor.get(), (int) z);

                            if (!block.getBlock().getType().equals(region.getFloorBlock()))
                                block.getBlock().setType(region.getFloorBlock());
                        }
                    }
                    layerFloor.getAndDecrement();
                }

                if (layerFloor.get() < region.getBottomPos().getBlockY()) {
                    Bukkit.getScheduler().cancelTasks(XG7RandomKits.getPlugin());
                }


            }, 0, 10L);

            for (Player player : Bukkit.getOnlinePlayers()) if (PluginUtil.isInWorld(player))
                TextUtil.sendActionBar("&aRegion reseted!", player);

        }

    }

    @Override
    public void onEnable() {
        timer = new Timer();

        if (configManager.getConfig(ConfigType.CONFIG).getBoolean("region.auto-reset")) {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    resetRegion(getPlugin());

                }
            },0,60000 * configManager.getConfig(ConfigType.CONFIG).getLong("region.reset-cooldown"));
        }


    }

    @Override
    public void onDisable() {

    }
}
