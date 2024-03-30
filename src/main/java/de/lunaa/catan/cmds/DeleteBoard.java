package de.lunaa.catan.cmds;

import de.lunaa.catan.methods.board.CreateBoard;
import de.lunaa.catan.methods.board.DiceMechanic;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import static org.bukkit.Bukkit.getLogger;

public class DeleteBoard implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!CreateBoard.gameExists) {

            if (sender instanceof ConsoleCommandSender) {
                getLogger().info("Cannot delete board as there is none!");
            } else {
                sender.sendMessage(ChatColor.RED + "Cannot delete board as there is none!");
            }
        } else {
            // Delete Board
            for (int i=0; i<CreateBoard.numberDisplayUUIDs.size(); i++) {
                Bukkit.getEntity(CreateBoard.numberDisplayUUIDs.get(i)).remove();
            }
            for (int i=0; i<CreateBoard.gamePlayerUUIDs.size(); i++) {
                if (Bukkit.getPlayer(CreateBoard.gamePlayerUUIDs.get(i)).getGameMode() != GameMode.CREATIVE && Bukkit.getPlayer(CreateBoard.gamePlayerUUIDs.get(i)).getGameMode() != GameMode.SPECTATOR) {
                    Bukkit.getPlayer(CreateBoard.gamePlayerUUIDs.get(i)).setAllowFlight(false);
                }
            }

            CreateBoard.numberDisplayUUIDs.clear();
            CreateBoard.gameCoords.clear();
            CreateBoard.gamePlayerUUIDs.clear();
            CreateBoard.gameFieldTypes.clear();
            CreateBoard.gameFieldNumbers.clear();
            CreateBoard.gameFieldData.clear();
            CreateBoard.gamePlayerResourceTemplate.clear();
            CreateBoard.gamePlayerResources.clear();
            CreateBoard.buildingData.clear();
            CreateBoard.buildingDataTemplate.clear();
            CreateBoard.buildingDataTemplateList.clear();
            CreateBoard.gameData.clear();
            CreateBoard.clayPieceCount = 0;
            CreateBoard.forestPieceCount = 0;
            CreateBoard.sheepPieceCount = 0;
            CreateBoard.wheatPieceCount = 0;
            CreateBoard.orePieceCount = 0;
            CreateBoard.desertSpawned = false;
            DiceMechanic.startingRolls.clear();
            CreateBoard.colorList.clear();
            CreateBoard.gameExists = false;
            CreateBoard.gameState = "NONE";
            sender.sendMessage(ChatColor.GREEN + "Successfully deleted the board!");
        }
        // Finish
        return true;
    }
}
