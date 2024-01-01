package com.xg7network.xg7randomkits.Utils.PluginInventories;


/*

    This class was made by DaviXG7 to facilitate
    the creation of plugin items and inventories.

    The class is free to use if this text is copied
    into your plugin and your plugin is free.

    Thanks for reading/using my code <3

 */

import com.xg7network.xg7randomkits.Utils.Text.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Item {

    private Action action;
    private ItemStack itemStack;
    private int slot;

    public Item(Player player, String material, String name, String lore, boolean glow, int slot, int ammount, Action action) {

        this.action = action;

        this.slot = slot - 1;

        ItemMeta meta;

        if (material.contains(", ")) {

            String[] materialByte = material.split(", ");

                String playername = materialByte[1].replace("OWNER=", "");

                boolean skull = Arrays.stream(Material.values())
                        .map(Material::name)
                        .collect(Collectors.toList())
                        .contains("PLAYER_HEAD");

                Material cabecatype = Material.matchMaterial(skull ? "PLAYER_HEAD" : "SKULL_ITEM");
                this.itemStack = new ItemStack(skull ? cabecatype : cabecatype, 1, (short) SkullType.PLAYER.ordinal());
                SkullMeta skullMeta = (SkullMeta) this.itemStack.getItemMeta();

                skullMeta.setOwner(playername.equals("THIS_PLAYER") ? Bukkit.getOfflinePlayer(player.getUniqueId()).getName() : playername);

                meta = skullMeta;

                this.itemStack.setItemMeta(meta);
        } else {
            this.itemStack = new ItemStack(Material.getMaterial(material), ammount);
        }

        meta = this.itemStack.getItemMeta();

        meta.setDisplayName(TextUtil.get(name, player));

        meta.setLore(Arrays.stream(TextUtil.get(lore, player).split(" /// ")).toList());
        if (glow) meta.addEnchant(Enchantment.DURABILITY, 1, true);
        itemStack.setItemMeta(meta);

        this.itemStack.setItemMeta(meta);


    }

    public Item(Material material, String name, String lore, boolean glow, int slot, int ammount, Action action) {

        this.action = action;

        this.slot = slot - 1;

        ItemMeta meta;

        this.itemStack = new ItemStack(material, ammount);

        meta = this.itemStack.getItemMeta();

        meta.setDisplayName(TextUtil.get(name));

        meta.setLore(Arrays.stream(TextUtil.get(lore).split(" /// ")).toList());
        if (glow) meta.addEnchant(Enchantment.DURABILITY, 1, true);
        itemStack.setItemMeta(meta);

        this.itemStack.setItemMeta(meta);


    }

    public void setItem(Player player) {
        player.getInventory().setItem(this.slot, this.itemStack);
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void execute() {

        if (action == null) return;

        action.execute();


    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getSlot() {
        return slot;
    }
}
