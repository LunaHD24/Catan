package de.lunaa.catan.items.general;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;

public class EndTurn {

    public static ItemStack get(){

        ItemStack item = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

        meta.setDisplayName(ChatColor.RED + "End Turn");

        lore.add(ChatColor.YELLOW + "RIGHT CLICK " + ChatColor.GRAY + "to " + ChatColor.RED + "end " + ChatColor.GRAY + "your turn");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

}
