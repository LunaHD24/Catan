package de.lunaa.catan.cmds;

import de.lunaa.catan.GameLogic;
import de.lunaa.catan.items.blue.PlaceCityBlue;
import de.lunaa.catan.items.blue.PlaceSettlementBlue;
import de.lunaa.catan.items.general.Dice;
import de.lunaa.catan.items.red.PlaceCityRed;
import de.lunaa.catan.items.red.PlaceSettlementRed;
import de.lunaa.catan.items.white.PlaceCityWhite;
import de.lunaa.catan.items.white.PlaceSettlementWhite;
import de.lunaa.catan.items.yellow.PlaceCityYellow;
import de.lunaa.catan.items.yellow.PlaceSettlementYellow;
import de.lunaa.catan.methods.board.CreateBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;

import static org.bukkit.Bukkit.getLogger;

public class StartGame implements CommandExecutor {

    static Random rand = new Random();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (CreateBoard.gamePlayerUUIDs.size() >= 1) {
                for (int i=0; i<=(CreateBoard.gamePlayerUUIDs.size()-1); i++) {
                    if (Bukkit.getPlayer(CreateBoard.gamePlayerUUIDs.get(i)) != null) {
                    } else {
                        player.sendMessage(ChatColor.RED + "Cannot start the game as some players aren't online!");
                        return true;
                    }
                }

                CreateBoard.gameState = "STARTING";
                CreateBoard.gameData.set(3, CreateBoard.gameState);
                for (int i=0; i<5; i++) {
                    CreateBoard.gamePlayerResourceTemplate.add(0);
                }

                CreateBoard.buildingDataTemplate.add("");
                CreateBoard.buildingDataTemplate.add(-1);

                for (int i=0; i<CreateBoard.gamePlayerUUIDs.size(); i++) {
                    CreateBoard.playerVictoryPoints.add(0);
                    CreateBoard.gamePlayerResources.add(new ArrayList<>(CreateBoard.gamePlayerResourceTemplate));
                    CreateBoard.buildingData.add(new ArrayList<>(CreateBoard.buildingDataTemplateList));
                    Bukkit.getPlayer(CreateBoard.gamePlayerUUIDs.get(i)).setAllowFlight(true);
                }

                int startTeam = rand.nextInt(CreateBoard.gamePlayerUUIDs.size());
                CreateBoard.currentPlayer = CreateBoard.gamePlayerUUIDs.get(startTeam);
                Bukkit.getPlayer(CreateBoard.currentPlayer).getInventory().setItem(8, Dice.get());
                for (int i=0; i<(CreateBoard.gamePlayerUUIDs.size()); i++) {
                    Player player1 = Bukkit.getPlayer(CreateBoard.gamePlayerUUIDs.get(i));
                    PlayerInventory inventory = Bukkit.getServer().getPlayerExact(player1.getName()).getInventory();

                    if (startTeam == 0) {
                        inventory.setItem(0, PlaceSettlementBlue.get());
                        inventory.setItem(1, PlaceCityBlue.get());
                        CreateBoard.colorList.add(0);
                        startTeam++;
                    } else if (startTeam == 1) {
                        inventory.setItem(0, PlaceSettlementRed.get());
                        inventory.setItem(1, PlaceCityRed.get());
                        CreateBoard.colorList.add(1);
                        startTeam++;
                    } else if (startTeam == 2) {
                        inventory.setItem(0, PlaceSettlementYellow.get());
                        inventory.setItem(1, PlaceCityYellow.get());
                        CreateBoard.colorList.add(2);
                        startTeam++;
                    } else {
                        inventory.setItem(0, PlaceSettlementWhite.get());
                        inventory.setItem(1, PlaceCityWhite.get());
                        CreateBoard.colorList.add(3);
                        startTeam = 0;
                    }

                    player1.sendMessage(ChatColor.GREEN + "Game started successfully!");
                    player1.sendMessage(" ");
                    player1.sendMessage(ChatColor.GREEN + "The player to roll the highest number starts!");
                    player1.sendMessage(ChatColor.GOLD + Bukkit.getPlayer(CreateBoard.currentPlayer).getName() + ChatColor.GREEN + " begins!");
                }

            } else {
                sender.sendMessage(ChatColor.RED + "There aren't enough players to start the game!" + ChatColor.GRAY + " (" + CreateBoard.gamePlayerUUIDs.size() + "/4)");
                sender.sendMessage(ChatColor.RED + "A minimum of 2 players is required!");
            }

        } else if (sender instanceof ConsoleCommandSender) {
            getLogger().info("Command 'startgame' cannot be executed by the console!");
        }
        System.out.println(CreateBoard.gameState);
        System.out.println(CreateBoard.gameData);
        return true;
    }
    
}
