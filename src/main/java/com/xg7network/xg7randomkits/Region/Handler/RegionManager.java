package com.xg7network.xg7randomkits.Region.Handler;

import com.xg7network.xg7randomkits.Configs.ConfigType;
import com.xg7network.xg7randomkits.Configs.PermissionType;
import com.xg7network.xg7randomkits.Module.Module;
import com.xg7network.xg7randomkits.Region.Region;
import com.xg7network.xg7randomkits.Region.RegionEvents;
import com.xg7network.xg7randomkits.Utils.PluginInventories.Item;
import com.xg7network.xg7randomkits.Utils.Text.TextUtil;
import com.xg7network.xg7randomkits.XG7RandomKits;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.UUID;

import static com.xg7network.xg7randomkits.XG7RandomKits.configManager;
import static com.xg7network.xg7randomkits.XG7RandomKits.prefix;

public class RegionManager extends Module implements Listener {

    private static final HashMap<UUID, PlayerInventory> regionPlayers = new HashMap<>();

    public static Region region;
    private static Location tempPos1;
    private static Location tempPos2;
    private static Location tempFloor;


    //Region modes
    private static final Item set = new Item(Material.BLAZE_ROD, "&a&lSet region", "&bClick to set the region", true, 1, 1, null);
    private static final Item check = new Item(Material.BOOK, "&a&lCheck", "&bClick to check if you are in the region", true, 4, 1, null);
    private static final Item delete = new Item(Material.BARRIER, "&a&lDelete", "&bClick to delete the current region", true, 6, 1, null);
    private static final Item exit = new Item(Material.REDSTONE_BLOCK, "&c&lExit", "&bClick to leave region mode", false, 9, 1, null);

    // Set Mode
    private static final Item regioni = new Item(Material.BLAZE_ROD, "&a&lSet REGION", "&bClick to set the region", true, 1, 1, null);
    private static final Item floor = new Item(Material.STICK, "&a&lSet FLOOR", "&bClick to set the region's floor", true, 2, 1, null);
    private static final Item saveRegion = new Item(Material.DIAMOND, "&a&lSave", "&bClick to save the region", true, 8, 1, null);
    private static final Item back = new Item(Material.REDSTONE_BLOCK, "&c&lCancel", "&bClick to cancel", false, 9, 1, null);

    //Delete mode
    private static final Item yes = new Item(Material.EMERALD, "&a&lYES", "", false, 4, 1, null);
    private static final Item no = new Item(Material.REDSTONE, "&c&lNO", "", false, 6, 1, null);

    public RegionManager(XG7RandomKits plugin) {
        super(plugin);
    }

    public static boolean isInRegionMode(Player player) {
        return regionPlayers.containsKey(player.getUniqueId());
    }

    public static void setToRegionMode(Player player, RegionCase regionCase) {

        if (!regionPlayers.containsKey(player.getUniqueId())) {
            regionPlayers.put(player.getUniqueId(), player.getInventory());
            TextUtil.sendActionBar(prefix + "&aYou are now in region mode!", player);
        }

        player.getInventory().clear();

        switch (regionCase) {

            case DEFAULT:

                set.setItem(player);
                check.setItem(player);
                delete.setItem(player);
                exit.setItem(player);

                return;

            case SET:

                regioni.setItem(player);
                floor.setItem(player);
                back.setItem(player);
                saveRegion.setItem(player);

                TextUtil.sendActionBar("&aYou are now in set region mode!", player);
                TextUtil.send("&bUse the blaze rod to set the region (Left click) pos 1 (Right click) pos 2;", player);
                TextUtil.send("&bUse the stick to set the region's floor layer (Left Click!);", player);
                TextUtil.send("&bThen use the diamond to save the region!", player);

                return;

            case REMOVE:

                yes.setItem(player);
                no.setItem(player);
                TextUtil.sendActionBar("&cAre you sure you want to delete the region?", player);


        }

    }

    public static void removeToRegionMode(Player player) {

        if (!regionPlayers.containsKey(player.getUniqueId())) player.sendMessage(prefix + ChatColor.RED + "You are not in region mode!");

        else {
            player.getInventory().clear();
            player.getInventory().setArmorContents(regionPlayers.get(player.getUniqueId()).getArmorContents());
            player.getInventory().setContents(regionPlayers.get(player.getUniqueId()).getContents());
            player.getInventory().setExtraContents(regionPlayers.get(player.getUniqueId()).getExtraContents());
            regionPlayers.remove(player.getUniqueId());

            TextUtil.sendActionBar(prefix + "&cYou are no longer in region mode!", player);
        }

    }

    public static boolean contains(Player player) {
        return regionPlayers.containsKey(player.getUniqueId());
    }

    public static void loadRegion() {

        Bukkit.getConsoleSender().sendMessage(prefix + "Loading region");

        FileConfiguration rgconf = configManager.getConfig(ConfigType.DATA);
        Location pos1 = null;
        Location pos2 = null;
        int fpos = 0;

        try {

            pos1 = new Location(
                    Bukkit.getWorld(rgconf.getString("region.world")),
                    rgconf.getInt("region.region.first.x"),
                    rgconf.getInt("region.region.first.y"),
                    rgconf.getInt("region.region.first.z")
            );

            pos2 = new Location(
                    Bukkit.getWorld(rgconf.getString("region.world")),
                    rgconf.getInt("region.region.second.x"),
                    rgconf.getInt("region.region.second.y"),
                    rgconf.getInt("region.region.second.z")
            );

            fpos = rgconf.getInt("region.floor.layer");

        } catch (Exception ignored) {}


        if (pos1 != null && pos2 != null && !(fpos <= 0)) {
            region = new Region(pos1, pos2, fpos);
        } else {
            region = null;
        }

    }
    
    public static void action(RegionCase regionCase, Player player) {
        switch (regionCase) {
            case SAVE:

                if (tempFloor != null && tempPos1 != null && tempPos2 != null) {

                    configManager.getConfig(ConfigType.DATA).set("region.world", tempPos1.getWorld().getName());

                    configManager.getConfig(ConfigType.DATA).set("region.region.first.x", tempPos1.getX());
                    configManager.getConfig(ConfigType.DATA).set("region.region.first.y", tempPos1.getY());
                    configManager.getConfig(ConfigType.DATA).set("region.region.first.z", tempPos1.getZ());

                    configManager.getConfig(ConfigType.DATA).set("region.region.second.x", tempPos2.getX());
                    configManager.getConfig(ConfigType.DATA).set("region.region.second.y", tempPos2.getY());
                    configManager.getConfig(ConfigType.DATA).set("region.region.second.z", tempPos2.getZ());

                    configManager.getConfig(ConfigType.DATA).set("region.floor.layer", tempFloor.getY());

                    configManager.saveConfig(ConfigType.DATA);

                    configManager.reloadConfig(ConfigType.DATA);

                    loadRegion();

                    TextUtil.send("&bThe region has been saved!", player);

                    tempFloor = null;
                    tempPos1 = null;
                    tempPos2 = null;

                } else {
                    TextUtil.send("&cCannot save a null location!", player);
                }

                return;

            case CHECK:

                if (region != null) {

                    TextUtil.sendActionBar(String.format("&aRegion: &fTop: &b%d&f, &b%d&f, &b%d&f &fBottom: &b%d&f, &b%d&f, &b%d&f &6Falling in region: &b" + RegionEvents.isFalling(player) + "&6In Region: &b" + region.isInRegion(player.getLocation()), (int) region.getTopPos().getX(), (int) region.getTopPos().getY(), (int) region.getTopPos().getZ(), (int) region.getBottomPos().getX(), (int) region.getBottomPos().getY(), (int) region.getBottomPos().getZ()), player);

                } else {
                    TextUtil.sendActionBar("&cThe region has not yet been placed!", player);
                }
                return;

            case REMOVE:
                configManager.getConfig(ConfigType.DATA).set("region.world", null);

                configManager.getConfig(ConfigType.DATA).set("region.region.first.x", null);
                configManager.getConfig(ConfigType.DATA).set("region.region.first.y", null);
                configManager.getConfig(ConfigType.DATA).set("region.region.first.z", null);

                configManager.getConfig(ConfigType.DATA).set("region.region.second.x", null);
                configManager.getConfig(ConfigType.DATA).set("region.region.second.y", null);
                configManager.getConfig(ConfigType.DATA).set("region.region.second.z", null);

                configManager.getConfig(ConfigType.DATA).set("region.floor.layer", null);

                configManager.saveConfig(ConfigType.DATA);

                loadRegion();

                TextUtil.send("&aThe region has been deleted!", player);

                return;
        }
    }

    public static Region getRegion() {
        return region;
    }

    @EventHandler
    public void onInventory(InventoryClickEvent event) {
        event.setCancelled(regionPlayers.containsKey(event.getWhoClicked().getUniqueId()));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (regionPlayers.containsKey(player.getUniqueId())) {
            event.setCancelled(true);

            if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

                if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {

                    if (player.getItemInHand().equals(regioni.getItemStack())) {
                        if (event.getClickedBlock() != null) {

                            tempPos1 = event.getClickedBlock().getLocation();

                            TextUtil.sendActionBar(String.format("&aPos 1 has been positioned on: %d, %d, %d", (int) tempPos1.getX(), (int) tempPos1.getY(), (int) tempPos1.getZ()), player);
                        } else return;
                    }

                } else {
                    if (event.getClickedBlock() != null) {
                        if (player.getItemInHand().equals(regioni.getItemStack())) {

                            tempPos2 = event.getClickedBlock().getLocation();

                            TextUtil.sendActionBar(String.format("&aPos 2 has been positioned on: %d, %d, %d", (int) tempPos2.getX(), (int) tempPos2.getY(), (int) tempPos2.getZ()), player);


                        }
                    } else return;

                }

            }

            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                if (player.getItemInHand().equals(set.getItemStack())) {

                    setToRegionMode(player, RegionCase.SET);

                } else if (player.getItemInHand().equals(check.getItemStack())) {

                    action(RegionCase.CHECK, player);


                } else if (player.getItemInHand().equals(delete.getItemStack())) {

                    if (region == null) {

                        TextUtil.sendActionBar("&cThe region does not exist!", player);

                    } else {
                        setToRegionMode(player, RegionCase.REMOVE);
                    }

                    
                } else if (player.getItemInHand().equals(exit.getItemStack())) {

                    removeToRegionMode(player);

                }


                else if (player.getItemInHand().equals(saveRegion.getItemStack())) {

                    action(RegionCase.SAVE, player);

                    if (player.hasPermission(PermissionType.REGION.getPerm())) setToRegionMode(player, RegionCase.DEFAULT);

                    else removeToRegionMode(player);


                } else if (player.getItemInHand().equals(back.getItemStack())) {
                    if (player.hasPermission(PermissionType.REGION.getPerm())) setToRegionMode(player, RegionCase.DEFAULT);

                    else removeToRegionMode(player);

                }



                else if (player.getItemInHand().equals(yes.getItemStack())) {

                    action(RegionCase.REMOVE, player);

                    setToRegionMode(player, RegionCase.DEFAULT);


                } else if (player.getItemInHand().equals(no.getItemStack())) {
                    setToRegionMode(player, RegionCase.DEFAULT);

                } else if (player.getItemInHand().equals(floor.getItemStack())) {

                    if (tempPos1 != null && tempPos2 != null) {

                        if (event.getClickedBlock().getY() < Math.min(tempPos1.getBlockY(), tempPos2.getBlockY())) {
                            TextUtil.sendActionBar("&cFloor layer cannot be less than bottom region layer!", player);
                        } else {
                            if (event.getClickedBlock() != null) {
                                tempFloor = event.getClickedBlock().getLocation();
                                TextUtil.sendActionBar(String.format("&aFloor layer has been positioned on layer: %d", (int) tempFloor.getY()), player);
                            } else return;
                        }

                    } else {
                        TextUtil.sendActionBar("&cSet the region first!", player);
                    }

                }

            }
        }


    }


    @Override
    public void onEnable() {

        loadRegion();

    }

    @Override
    public void onDisable() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            removeToRegionMode(player);
        }

    }
}
