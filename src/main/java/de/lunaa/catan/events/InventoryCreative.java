package de.lunaa.catan.events;

import de.lunaa.catan.methods.board.CreateBoard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCreativeEvent;

import java.util.Objects;

public class InventoryCreative implements Listener {

    @EventHandler
    public void InventoryCreativeEvent(InventoryCreativeEvent event) {

        if (Objects.equals(CreateBoard.gameState, "RUNNING")) {
            Player player = (Player) event.getWhoClicked();

            if (CreateBoard.gamePlayerUUIDs.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }

        }

    }

}
