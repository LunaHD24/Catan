package de.lunaa.catan.items.yellow;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PlaceSettlementYellow {

    public static ItemStack get() {

        ItemStack item = new ItemStack(Material.YELLOW_WOOL, 1);
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

        meta.setDisplayName(ChatColor.YELLOW + "Settlement");
        meta.setLocalizedName("PLACE_SETTLEMENT_YELLOW");
        lore.add("");
        lore.add(ChatColor.GREEN + "" + ChatColor.BOLD  + "Costs:");
        lore.add(ChatColor.RED + "1x Clay");
        lore.add(ChatColor.DARK_GREEN + "1x Wood");
        lore.add(ChatColor.WHITE + "1x Wool");
        lore.add(ChatColor.GOLD + "1x Wheat");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

}
