package de.lunaa.catan.events;

import de.lunaa.catan.methods.board.CreateBoard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.Objects;

public class PlayerToggleFlight implements Listener {

    @EventHandler
    public void PlayerToggleFlightEvent(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (CreateBoard.gamePlayerUUIDs.contains(player.getUniqueId()) && Objects.equals(CreateBoard.gameState, "RUNNING")) {
            player.setFlying(!player.isFlying());
        }
    }
}
