package de.lunaa.catan.cmds.debug;

import de.lunaa.catan.GameLogic;
import de.lunaa.catan.methods.general.Resources;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class AddResources implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;

        if (player.hasPermission("cmd.addresources")) {
            if (args.length == 6) {
                try {
                    Resources.addResources(player, Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                    GameLogic.actionbarResourceDisplay();
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Usage: /addresources <player> <wood> <wool> <wheat> <clay> <ore>");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /addresources <player> <wood> <wool> <wheat> <clay> <ore>");
            }
        } else {
            player.sendMessage(ChatColor.RED + "You don't have permission to execute this command!");
        }
        return true;
    }
}
