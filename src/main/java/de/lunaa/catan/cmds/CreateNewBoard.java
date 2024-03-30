package de.lunaa.catan.cmds;

import de.lunaa.catan.methods.board.CreateBoard;
import de.lunaa.catan.methods.general.CreateBoardCooldown;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getLogger;

public class CreateNewBoard implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (CreateBoardCooldown.checkCooldown(player.getPlayer())) {
                CreateBoardCooldown.setCooldown(player.getPlayer(), 10);

                if (CreateBoard.gameExists) {
                    sender.sendMessage(ChatColor.RED + "Cannot create more than 1 board in a world!");
                    return true;
                } else {
                    // Pass to CreateBoard Method
                    CreateBoard.newBoard(player);
                }
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "This command is on Cooldown! " + ChatColor.GRAY + "(" + Math.round(((Double) CreateBoardCooldown.cooldowns.get(player.getUniqueId()) - (double)System.currentTimeMillis()) / 1000.0D) + "s" + ")");
            }
            return true;
        } else if (sender instanceof ConsoleCommandSender) {
            getLogger().info("Command 'newboard' cannot be executed by the console!");
        }
        return  true;
    }
}
