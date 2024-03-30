package de.lunaa.catan.methods.general;

import de.lunaa.catan.items.general.Dice;
import de.lunaa.catan.methods.board.CreateBoard;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Turns {

    public static void setTurn(Player player) {
        Bukkit.getPlayer(CreateBoard.currentPlayer).getInventory().setItem(8, new ItemStack(Material.AIR));
        CreateBoard.currentPlayer = player.getUniqueId();
        player.getInventory().setItem(8, Dice.get());
    }

    public static void setTurn(UUID uuid) {
        Bukkit.getPlayer(CreateBoard.currentPlayer).getInventory().setItem(8, new ItemStack(Material.AIR));
        CreateBoard.currentPlayer = uuid;
        Bukkit.getPlayer(uuid).getInventory().setItem(8, Dice.get());
    }

    public static void setTurn(String name) {
        Bukkit.getPlayer(CreateBoard.currentPlayer).getInventory().setItem(8, new ItemStack(Material.AIR));
        CreateBoard.currentPlayer = Bukkit.getPlayer(name).getUniqueId();
        Bukkit.getPlayer(name).getInventory().setItem(8, Dice.get());
    }

    public static void goTurns(int steps) {
        int playerCount = CreateBoard.gamePlayerUUIDs.size();
        Bukkit.getPlayer(CreateBoard.currentPlayer).getInventory().setItem(8, new ItemStack(Material.AIR));
        for (int i=0; i<steps; i++) {
            if (playerCount-1 != CreateBoard.gamePlayerUUIDs.indexOf(CreateBoard.currentPlayer)) {
                CreateBoard.currentPlayer = CreateBoard.gamePlayerUUIDs.get(CreateBoard.gamePlayerUUIDs.indexOf(CreateBoard.currentPlayer)+1);
            } else {
                CreateBoard.currentPlayer = CreateBoard.gamePlayerUUIDs.get(0);
            }
        }
        Bukkit.getPlayer(CreateBoard.currentPlayer).getInventory().setItem(8, Dice.get());
    }

    public static void nextTurn() {
        int playerCount = CreateBoard.gamePlayerUUIDs.size();
        Bukkit.getPlayer(CreateBoard.currentPlayer).getInventory().setItem(8, new ItemStack(Material.AIR));
        if (playerCount-1 != CreateBoard.gamePlayerUUIDs.indexOf(CreateBoard.currentPlayer)) {
            CreateBoard.currentPlayer = CreateBoard.gamePlayerUUIDs.get(CreateBoard.gamePlayerUUIDs.indexOf(CreateBoard.currentPlayer)+1);
        } else {
            CreateBoard.currentPlayer = CreateBoard.gamePlayerUUIDs.get(0);
        }
        Bukkit.getPlayer(CreateBoard.currentPlayer).getInventory().setItem(8, Dice.get());
    }

}
