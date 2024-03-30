package de.lunaa.catan.methods.board;

import de.lunaa.catan.items.blue.PlaceSettlementBlue;
import de.lunaa.catan.items.general.EndTurn;
import de.lunaa.catan.items.red.PlaceSettlementRed;
import de.lunaa.catan.items.white.PlaceSettlementWhite;
import de.lunaa.catan.items.yellow.PlaceSettlementYellow;
import de.lunaa.catan.methods.general.Turns;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class DiceMechanic {

    static int diceNum1 = 0;
    static int diceNum2 = 0;
    public static ArrayList<Integer> startingRolls;

    public static void rollDice(Player player) {
        if (player.getUniqueId() == CreateBoard.currentPlayer) {
            if (CreateBoard.isDiceRollable) {
                CreateBoard.isDiceRollable = false;
                diceNum1 = (int)Math.floor(Math.random() * (6 - 1 + 1) + 1);
                diceNum2 = (int)Math.floor(Math.random() * (6 - 1 + 1) + 1);
                player.sendTitle(ChatColor.GOLD + String.valueOf(diceNum1) + " ⚄ " + diceNum2, ChatColor.GREEN + String.valueOf(diceNum1+diceNum2));
                for (int i=0; i<CreateBoard.gamePlayerUUIDs.size(); i++) {
                    Bukkit.getPlayer(CreateBoard.gamePlayerUUIDs.get(i)).sendMessage(ChatColor.GOLD + player.getName() + ChatColor.GREEN + " rolled a " + ChatColor.GOLD + (diceNum1+diceNum2) + ChatColor.GREEN + "!");
                }
                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 0.1f, 5);
                if (Objects.equals(CreateBoard.gameState, "STARTING")) {
                    startingRolls.add(diceNum1+diceNum2);
                    if (startingRolls.size() == CreateBoard.gamePlayerUUIDs.size()) {
                        int startingPlayer = CreateBoard.gamePlayerUUIDs.indexOf(CreateBoard.gamePlayerUUIDs.get(startingRolls.indexOf(Collections.max(startingRolls))));
                        for (int i=0; i<CreateBoard.gamePlayerUUIDs.size(); i++) {
                            Bukkit.getPlayer(CreateBoard.gamePlayerUUIDs.get(i)).sendMessage(ChatColor.GOLD + Bukkit.getPlayer(CreateBoard.gamePlayerUUIDs.get(startingPlayer)).getName() + ChatColor.GREEN + " rolled the highest number and begins!");
                        }
                        Turns.setTurn(CreateBoard.gamePlayerUUIDs.get(startingPlayer));
                        Bukkit.getPlayer(CreateBoard.currentPlayer).getInventory().setItem(8, new ItemStack(Material.AIR));

                        int color = CreateBoard.colorList.get(CreateBoard.gamePlayerUUIDs.indexOf(CreateBoard.currentPlayer));
                        if (color == 0) {
                            Bukkit.getPlayer(CreateBoard.currentPlayer).getInventory().setItem(0, PlaceSettlementBlue.get());
                        } else if (color == 1) {
                            Bukkit.getPlayer(CreateBoard.currentPlayer).getInventory().setItem(0, PlaceSettlementRed.get());
                        } else if (color == 2) {
                            Bukkit.getPlayer(CreateBoard.currentPlayer).getInventory().setItem(0, PlaceSettlementYellow.get());
                        } else {
                            Bukkit.getPlayer(CreateBoard.currentPlayer).getInventory().setItem(0, PlaceSettlementWhite.get());
                        }
                    } else {
                        Turns.nextTurn();
                    }
                } else {
                    player.getInventory().setItem(8, EndTurn.get());
                }
            } else {
                player.sendMessage(ChatColor.RED + "You cannot currently roll the dice!");
            }
        } else {
            player.sendMessage(ChatColor.RED + "You cannot roll the dice as it is not your turn!");
        }
    }

}
