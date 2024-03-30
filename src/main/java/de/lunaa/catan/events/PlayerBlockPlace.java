package de.lunaa.catan.events;
import de.lunaa.catan.methods.board.CreateBoard;
import de.lunaa.catan.methods.board.CreateSettlement;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PlayerBlockPlace implements Listener {

    @EventHandler
    public void PlayerBlockPlaceEvent(BlockPlaceEvent event) {

        if (Objects.equals(CreateBoard.gameState, "RUNNING")) {
            Player player = event.getPlayer();

            if (CreateBoard.gamePlayerUUIDs.contains(player.getUniqueId())) {
                if (Objects.equals(CreateBoard.gameState, "STARTING")) {
                    player.getInventory().setItem(8, new ItemStack(Material.AIR));
                }
                switch (event.getBlockPlaced().getType()) {
                    case BLUE_WOOL:
                        CreateSettlement.newSettlement(player, event.getBlockPlaced().getX(), event.getBlockPlaced().getY(), event.getBlockPlaced().getZ(), 0);
                    case RED_WOOL:
                        CreateSettlement.newSettlement(player, event.getBlockPlaced().getX(), event.getBlockPlaced().getY(), event.getBlockPlaced().getZ(), 1);
                    case YELLOW_WOOL:
                        CreateSettlement.newSettlement(player, event.getBlockPlaced().getX(), event.getBlockPlaced().getY(), event.getBlockPlaced().getZ(), 2);
                    case WHITE_WOOL:
                        CreateSettlement.newSettlement(player, event.getBlockPlaced().getX(), event.getBlockPlaced().getY(), event.getBlockPlaced().getZ(), 3);
                }

            }

        }

    }
}
