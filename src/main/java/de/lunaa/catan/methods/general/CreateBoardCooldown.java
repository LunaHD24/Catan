package de.lunaa.catan.methods.general;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CreateBoardCooldown {

    public static HashMap<UUID, Double> cooldowns;

    public CreateBoardCooldown() {
    }

    public static void setupCooldown() {
        cooldowns = new HashMap();
    }

    public static void setCooldown(Player player, int sec) {
        double delay = (double)(System.currentTimeMillis() + (long)(sec * 1000));
        cooldowns.put(player.getUniqueId(), delay);
    }

    public static boolean checkCooldown(Player player) {
        return !cooldowns.containsKey(player.getUniqueId()) || (Double)cooldowns.get(player.getUniqueId()) <= (double)System.currentTimeMillis();
    }
}
