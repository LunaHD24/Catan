package de.lunaa.catan.cmds;

import de.lunaa.catan.methods.board.CreateBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;


public class JoinGame implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;

        if (Objects.equals(CreateBoard.gameState, "IDLE")) {
            if (CreateBoard.gamePlayerUUIDs.size() >= 4) {
                player.sendMessage(ChatColor.RED + "You cannot join this player's game as the it's already full!");
            } else {
                if (args.length > 0) {
                    if (Bukkit.getServer().getPlayerExact(args[0]) != null && Bukkit.getServer().getPlayerExact(args[0]).getName().equalsIgnoreCase(args[0])) {
                        Player joinGamePlayer = Bukkit.getServer().getPlayerExact(args[0]);
                        if (CreateBoard.gamePlayerUUIDs.contains(joinGamePlayer.getUniqueId())) {
                            if (!CreateBoard.gamePlayerUUIDs.contains(player.getUniqueId())) {
                                CreateBoard.gamePlayerUUIDs.add(player.getUniqueId());
                                player.sendMessage(ChatColor.GREEN + "You have joined the game of " + ChatColor.GOLD + joinGamePlayer.getName() + ChatColor.GREEN + "!" + ChatColor.GRAY + " (" + CreateBoard.gamePlayerUUIDs.size() + "/4)");
                                joinGamePlayer.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.GREEN + " has joined the game!" + ChatColor.GRAY + " (" + CreateBoard.gamePlayerUUIDs.size() + "/4)");
                                CreateBoard.gameData.set(1, CreateBoard.gamePlayerUUIDs);
                            } else {
                                player.sendMessage(ChatColor.RED + "You are already in this game!");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "This player is not in a game!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You cannot join this player since they're not online!");
                    }
                    return true;
                } else {
                    player.sendMessage(ChatColor.RED + "Please provide a player name!");
                    player.sendMessage(ChatColor.RED + "Usage: /joingame <player>");
                }
            }
        } else if (Objects.equals(CreateBoard.gameState, "RUNNING")){
            player.sendMessage(ChatColor.RED + "You cannot join this game as it's already running!");
        }
        return true;
    }
}
