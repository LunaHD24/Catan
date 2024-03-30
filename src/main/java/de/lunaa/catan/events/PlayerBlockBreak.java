package de.lunaa.catan.events;

import de.lunaa.catan.methods.board.CreateBoard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Objects;

public class PlayerBlockBreak implements Listener {

    @EventHandler
    public void PlayerBlockPlaceEvent(BlockPlaceEvent event) {

        if (Objects.equals(CreateBoard.gameState, "RUNNING") || Objects.equals(CreateBoard.gameState, "IDLE") || Objects.equals(CreateBoard.gameState, "STARTING")) {
            Player player = event.getPlayer();

            if (CreateBoard.gamePlayerUUIDs.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }

        }

    }

}
