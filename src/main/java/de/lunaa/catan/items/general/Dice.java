package de.lunaa.catan.items.general;

import net.md_5.bungee.api.chat.KeybindComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Dice {

    public static ItemStack get(){

        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        ItemMeta meta = item.getItemMeta();
        SkullMeta skullMeta = (SkullMeta) meta;
        URL skinURL;
        try {
            skinURL = new URL("http://textures.minecraft.net/texture/5945f90ef5d82414bcf854cb8eb87d0e3e57a5fa6cf4c5bba19ccc436e780dea");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> lore = new ArrayList<>();

        PlayerProfile profile = Bukkit.createPlayerProfile("DICE-ITEM");
        PlayerTextures textures = profile.getTextures();
        textures.setSkin(skinURL);
        profile.setTextures(textures);
        TextComponent displayName = new TextComponent(ChatColor.WHITE + "Dice");
        displayName.setItalic(false);
        skullMeta.setDisplayName(displayName.getText());
        skullMeta.setLocalizedName("DICE");

        lore.add(ChatColor.YELLOW + "RIGHT CLICK " + ChatColor.GRAY + "to roll the dice");

        skullMeta.setOwnerProfile(profile);
        skullMeta.setLore(lore);
        item.setItemMeta(skullMeta);

        return item;
    }

}
