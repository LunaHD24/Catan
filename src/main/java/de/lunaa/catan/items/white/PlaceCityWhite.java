package de.lunaa.catan.items.white;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PlaceCityWhite {

    public static ItemStack get() {

        ItemStack item = new ItemStack(Material.WHITE_TERRACOTTA, 1);
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

        meta.setDisplayName(ChatColor.WHITE + "City");
        meta.setLocalizedName("PLACE_CITY_WHITE");

        lore.add("");
        lore.add(ChatColor.GREEN + "" + ChatColor.BOLD  + "Costs:");
        lore.add(ChatColor.GOLD + "3x Wheat");
        lore.add(ChatColor.GRAY + "2x Ore");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

}
