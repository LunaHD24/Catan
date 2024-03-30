package de.lunaa.catan.cmds.debug;

import de.lunaa.catan.methods.general.Turns;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;


public class Turn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;
        if (args.length > 0) {
            if (Objects.equals(args[0], "next")) {
                Turns.nextTurn();
            } else if (Objects.equals(args[0], "set")) {
                if (args.length >= 2) {
                    Turns.setTurn(args[1]);
                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /turn <next|set|go> <player|steps>");
                }
            } else if (Objects.equals(args[0], "go")) {
                if (args.length >=2) {
                    try {
                        Turns.goTurns(Integer.parseInt(args[1]));
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "Usage: /turn <next|set|go> <player|steps>");
                    }
                }
            }
        } else {
            player.sendMessage(ChatColor.RED + "Usage: /turn <next|set|go> <player|steps>");
            return true;
        }
        return true;
    }
}
