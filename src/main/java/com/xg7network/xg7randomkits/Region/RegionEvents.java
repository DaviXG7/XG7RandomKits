package com.xg7network.xg7randomkits.Region;

import com.xg7network.xg7randomkits.Region.Handler.RegionManager;
import com.xg7network.xg7randomkits.Utils.PluginUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public class RegionEvents implements Listener {

    private static HashMap<UUID, Boolean> falling = new HashMap<>();

    public static void putFall(Player player) {
        falling.put(player.getUniqueId(), true);
    }

    public static void removeFall(Player player) {
        falling.remove(player.getUniqueId());
    }

    public static boolean isFalling(Player player) {
        return falling.containsKey(player.getUniqueId());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (PluginUtil.isInWorld(player)) {
            if (!falling.containsKey(player.getUniqueId())) {
                if (!RegionManager.getRegion().isInRegion(event.getFrom()) && RegionManager.getRegion().isInRegion(event.getTo())) {
                    falling.put(player.getUniqueId(), true);
                }
            } else {
                if (player.isOnGround()) falling.remove(player.getUniqueId());
            }
        }
    }

    @EventHandler
    public void onFall(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (falling.containsKey(player.getUniqueId())) event.setCancelled(true);

        }
    }
}
