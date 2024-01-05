package com.xg7network.xg7randomkits.Region;

import com.xg7network.xg7randomkits.Configs.ConfigType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

import static com.xg7network.xg7randomkits.XG7RandomKits.configManager;

public class Region implements Listener {

    private String worldName;
    private int x1, y1, z1, x2, y2, z2;
    private int fy;

    public Region(Location l1, Location l2, int layer) {
        if (!l1.getWorld().equals(l2.getWorld())) throw new IllegalArgumentException("Locations must be on the same world");
        this.worldName = l1.getWorld().getName();
        this.x1 = Math.max(l1.getBlockX(), l2.getBlockX());
        this.y1 = Math.max(l1.getBlockY(), l2.getBlockY());
        this.z1 = Math.max(l1.getBlockZ(), l2.getBlockZ());
        this.x2 = Math.min(l1.getBlockX(), l2.getBlockX());
        this.y2 = Math.min(l1.getBlockY(), l2.getBlockY());
        this.z2 = Math.min(l1.getBlockZ(), l2.getBlockZ());

        this.fy = layer;
    }

    public World getWorld() {
        return Bukkit.getWorld(worldName);
    }

    public boolean isInRegion(Location pos) {

        return pos.getBlockX() <= this.x1 && pos.getBlockX() >= this.x2 &&
               pos.getBlockY() <= this.y1 && pos.getBlockY() >= this.y2 &&
               pos.getBlockZ() <= this.z1 && pos.getBlockZ() >= this.z2;

    }

    public Material getFloorBlock() {
        return Material.getMaterial(configManager.getConfig(ConfigType.CONFIG).getString("region.floor-block").toUpperCase());
    }

    public Location getTopPos() {
        return new Location(Bukkit.getWorld(worldName), x1, y1, z1);
    }

    public Location getBottomPos() {
        return new Location(Bukkit.getWorld(worldName), x2, y2, z2);
    }

    public Location getCornerFloorPos() {
        return new Location(Bukkit.getWorld(worldName), x1, fy, z1);
    }

    public Location getOtherCornerFloorPos() {
        return new Location(Bukkit.getWorld(worldName), x2, fy, z2);
    }


}
