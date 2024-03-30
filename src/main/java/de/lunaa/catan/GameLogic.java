package de.lunaa.catan;

import de.lunaa.catan.methods.board.CreateBoard;
import de.lunaa.catan.methods.general.Resources;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Objects;

public class GameLogic {

    public static void init() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Catan.plugin, () -> {
            actionbarResourceDisplay();
        }, 0, 40);
    }


    public static void actionbarResourceDisplay() {

        if (Objects.equals(CreateBoard.gameState, "RUNNING")) {
            for (int i=0; i<CreateBoard.gamePlayerUUIDs.size(); i++) {
                Player player = Bukkit.getPlayer(CreateBoard.gamePlayerUUIDs.get(i));

                TextComponent woodActionbarTitle = new TextComponent(ChatColor.DARK_GREEN + String.valueOf(Resources.getResourceWood(player)));
                TextComponent woolActionbarTitle = new TextComponent(ChatColor.WHITE + String.valueOf(Resources.getResourceWool(player)));
                TextComponent wheatActionbarTitle = new TextComponent(ChatColor.GOLD + String.valueOf(Resources.getResourceWheat(player)));
                TextComponent clayActionbarTitle = new TextComponent(ChatColor.RED + String.valueOf(Resources.getResourceClay(player)));
                TextComponent oreActionbarTitle = new TextComponent(ChatColor.GRAY + String.valueOf(Resources.getResourceOre(player)));
                TextComponent actionbarTitle = new TextComponent();
                actionbarTitle.addExtra(woodActionbarTitle);
                actionbarTitle.addExtra(ChatColor.DARK_GRAY + " | ");
                actionbarTitle.addExtra(woolActionbarTitle);
                actionbarTitle.addExtra(ChatColor.DARK_GRAY + " | ");
                actionbarTitle.addExtra(wheatActionbarTitle);
                actionbarTitle.addExtra(ChatColor.DARK_GRAY + " | ");
                actionbarTitle.addExtra(clayActionbarTitle);
                actionbarTitle.addExtra(ChatColor.DARK_GRAY + " | ");
                actionbarTitle.addExtra(oreActionbarTitle);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, actionbarTitle);
            }
        }

    }

}
