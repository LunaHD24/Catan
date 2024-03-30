package de.lunaa.catan.events;

import de.lunaa.catan.items.general.Dice;
import de.lunaa.catan.items.general.EndTurn;
import de.lunaa.catan.methods.board.CreateBoard;
import de.lunaa.catan.methods.board.DiceMechanic;
import de.lunaa.catan.methods.general.Turns;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class PlayerInteractItem implements Listener {

    @EventHandler
    public void ItemClickEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        CreateBoard.currentPlayer = player.getUniqueId();
        CreateBoard.isDiceRollable = true;
        if (Objects.equals(player.getInventory().getItemInMainHand(), Dice.get())) {
            DiceMechanic.rollDice(player);
        } else if (Objects.equals(player.getInventory().getItemInMainHand(), EndTurn.get())) {
            Turns.nextTurn();
        }
    }

}