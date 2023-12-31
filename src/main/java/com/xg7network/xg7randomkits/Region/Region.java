package com.xg7network.xg7randomkits.Region;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.nio.Buffer;

public class Region {

    private String worldName;
    private int x1, y1, z1, x2, y2, z2;

    public Region(Location l1, Location l2) {
        if (!l1.getWorld().equals(l2.getWorld())) throw new IllegalArgumentException("Locations must be on the same world");
        this.worldName = l1.getWorld().getName();
        this.x1 = Math.min(l1.getBlockX(), l2.getBlockX());
        this.y1 = Math.min(l1.getBlockY(), l2.getBlockY());
        this.z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());
        this.x2 = Math.max(l1.getBlockX(), l2.getBlockX());
        this.y2 = Math.max(l1.getBlockY(), l2.getBlockY());
        this.z2 = Math.max(l1.getBlockZ(), l2.getBlockZ());
    }

    public boolean isInRegion(Location pos) {

        return this.x1 < pos.getBlockX() && pos.getBlockZ() < this.x2 && this.y1 < pos.getBlockY() && pos.getBlockY() < this.y2 && this.z1 < pos.getBlockZ() && pos.getBlockZ() < this.z2;

    }

    public Location getTopPos() {
        return new Location(Bukkit.getWorld(worldName), x1, y1, z1);
    }

    public Location getBottomPos() {
        return new Location(Bukkit.getWorld(worldName), x2, y2, z2);
    }

}
