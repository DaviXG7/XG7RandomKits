package com.xg7network.xg7randomkits.Region;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.nio.Buffer;

public class Region {

    private String worldName;
    private int x1, y1, z1, x2, y2, z2;
    private int fx1,fy1,fz1,fx2,fy2,fz2;

    public Region(Location l1, Location l2, Location fl1, Location fl2) {
        if (!l1.getWorld().equals(l2.getWorld())) throw new IllegalArgumentException("Locations must be on the same world");
        this.worldName = l1.getWorld().getName();
        this.x1 = Math.max(l1.getBlockX(), l2.getBlockX());
        this.y1 = Math.max(l1.getBlockY(), l2.getBlockY());
        this.z1 = Math.max(l1.getBlockZ(), l2.getBlockZ());
        this.x2 = Math.min(l1.getBlockX(), l2.getBlockX());
        this.y2 = Math.min(l1.getBlockY(), l2.getBlockY());
        this.z2 = Math.min(l1.getBlockZ(), l2.getBlockZ());

        this.fx1 = Math.max(fl1.getBlockX(), fl2.getBlockX());
        this.fy1 = Math.max(fl1.getBlockY(), fl2.getBlockY());
        this.fz1 = Math.max(fl1.getBlockZ(), fl2.getBlockZ());
        this.fx2 = Math.min(fl1.getBlockX(), fl2.getBlockX());
        this.fy2 = Math.min(fl1.getBlockY(), fl2.getBlockY());
        this.fz2 = Math.min(fl1.getBlockZ(), fl2.getBlockZ());
    }

    public boolean isInRegion(Location pos) {

        return pos.getBlockX() <= this.x1 && pos.getBlockX() >= this.x2 &&
               pos.getBlockY() <= this.y1 && pos.getBlockY() >= this.y2 &&
               pos.getBlockZ() <= this.z1 && pos.getBlockZ() >= this.z2;

    }

    public Location getTopPos() {
        return new Location(Bukkit.getWorld(worldName), x1, y1, z1);
    }

    public Location getBottomPos() {
        return new Location(Bukkit.getWorld(worldName), x2, y2, z2);
    }

}
