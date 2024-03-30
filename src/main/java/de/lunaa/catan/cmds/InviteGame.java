package de.lunaa.catan.cmds;

import de.lunaa.catan.methods.board.CreateBoard;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class InviteGame implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;

        if (CreateBoard.gamePlayerUUIDs.contains(player.getUniqueId())) {
            if (CreateBoard.gamePlayerUUIDs.size() >= 4) {
                sender.sendMessage(ChatColor.RED + "Cannot invite more players as the game is already full!");
            } else {
                if (args.length > 0) {
                    if (Bukkit.getServer().getPlayerExact(args[0]) != null && Bukkit.getServer().getPlayerExact(args[0]).getName().equalsIgnoreCase(args[0])) {
                        Player invPlayer = Bukkit.getServer().getPlayerExact(args[0]);

                        sender.sendMessage(ChatColor.GREEN + "Invite sent to " + ChatColor.GOLD + invPlayer.getName());
                        invPlayer.sendMessage(ChatColor.GREEN + "You have been invited to join a game of Catan by " + ChatColor.GOLD + sender.getName() + ChatColor.GREEN + "!");
                        TextComponent invClickMsg = new TextComponent("HERE");
                        invClickMsg.setColor(ChatColor.YELLOW.asBungee());
                        invClickMsg.setBold(true);
                        invClickMsg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/joingame " + sender.getName()));
                        TextComponent invPlayerMsgBeginning = new TextComponent(ChatColor.GREEN + "Click ");
                        invPlayerMsgBeginning.addExtra(invClickMsg);
                        invPlayerMsgBeginning.addExtra(ChatColor.RESET + "" + ChatColor.GREEN + " to join!");
                        invPlayer.spigot().sendMessage(invPlayerMsgBeginning);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You cannot invite this player since they're not online!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Please provide a player name!");
                    sender.sendMessage(ChatColor.RED + "Usage: /invitegame <player>");
                }
            }
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "You don't have a game to invite players too!");
        }
        return true;
    }
}
