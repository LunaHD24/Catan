package de.lunaa.catan.events;

import de.lunaa.catan.methods.board.CreateBoard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.Objects;

public class PlayerDrop implements Listener {

    @EventHandler
    public void PlayerDropItemEvent(PlayerDropItemEvent event) {

        if (Objects.equals(CreateBoard.gameState, "RUNNING")) {
            Player player = (Player) event.getPlayer();

            if (CreateBoard.gamePlayerUUIDs.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }

        }

    }

}
