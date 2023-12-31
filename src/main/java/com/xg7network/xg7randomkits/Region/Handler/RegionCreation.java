package com.xg7network.xg7randomkits.Region.Handler;

import com.xg7network.xg7randomkits.Utils.PluginInventories.InventoryUtil;
import com.xg7network.xg7randomkits.Utils.PluginInventories.Item;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.xg7network.xg7randomkits.XG7RandomKits.prefix;

public class RegionCreation implements Listener {

    private static final HashMap<UUID, PlayerInventory> regionPlayers = new HashMap<>();


    //Region mode
    private static final Item set = new Item(Material.BLAZE_ROD, "&a&lSet region", "&bClick to set the region", true, 1, 1, null);
    private static final Item save = new Item(Material.PAPER, "&a&lSave region", "&bClick to save the region", true, 3, 1, null);
    private static final Item check = new Item(Material.WRITTEN_BOOK, "&a&lCheck", "&bClick to check if you are in the region", true, 5, 1, null);
    private static final Item delete = new Item(Material.WRITTEN_BOOK, "&a&lDelete", "&bClick to delete the current region", true, 7, 1, null);
    private static final Item exit = new Item(Material.REDSTONE_BLOCK, "&c&lExit", "&bClick to leave region mode", false, 9, 1, null);

    // Set Mode
    private static final Item region = new Item(Material.BLAZE_ROD, "&a&lSet REGION", "&bClick to set the region", true, 1, 1, null);
    private static final Item floor = new Item(Material.STICK, "&a&lSet FLOOR", "&bClick to set the region's floor", true, 2, 1, null);
    private static final Item sback = new Item(Material.REDSTONE_BLOCK, "&c&lBACK", "&bClick to back", false, 9, 9, null);

    //Delete mode
    private static final Item yes = new Item(Material.EMERALD, "&a&lYES", "", false, 9, 6, null);
    private static final Item no = new Item(Material.REDSTONE, "&c&lNO", "", false, 9, 4, null);
    private static final Item dback = new Item(Material.REDSTONE_BLOCK, "&c&lBACK", "&bClick to back", false, 9, 9, null);


    public static void addToRegionMode(Player player, RegionCase regionCase) {

        if (!regionPlayers.containsKey(player.getUniqueId())) regionPlayers.put(player.getUniqueId(), player.getInventory());

        player.getInventory().clear();

        switch (regionCase) {

            case DEFAULT:

                set.setItem(player);
                save.setItem(player);
                check.setItem(player);
                delete.setItem(player);
                exit.setItem(player);

            case SET:

                region.setItem(player);
                floor.setItem(player);
                sback.setItem(player);

            case REMOVE:
        }

    }

    public static void removeToRegionMode(Player player) {

        if (!regionPlayers.containsKey(player.getUniqueId())) player.sendMessage(prefix + ChatColor.RED + "You are not in region mode!");

        else {
            player.getInventory().setArmorContents(regionPlayers.get(player.getUniqueId()).getArmorContents());
            player.getInventory().setContents(regionPlayers.get(player.getUniqueId()).getContents());
            player.getInventory().setExtraContents(regionPlayers.get(player.getUniqueId()).getExtraContents());
            regionPlayers.remove(player.getUniqueId());
        }

    }

}
