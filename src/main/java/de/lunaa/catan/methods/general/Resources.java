package de.lunaa.catan.methods.general;

import de.lunaa.catan.methods.board.CreateBoard;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Resources {

    public static void addResources(Player player, int addWood, int addWool, int addWheat, int addClay, int addOre) {

        int playerIDPos = CreateBoard.gamePlayerUUIDs.indexOf(player.getUniqueId());

        ArrayList<ArrayList> gamePlayerResources = (ArrayList<ArrayList>) CreateBoard.gameData.get(4);
        ArrayList<Integer> playerResources = gamePlayerResources.get(playerIDPos);

        playerResources.set(0, playerResources.get(0) + addWood);
        playerResources.set(1, playerResources.get(1) + addWool);
        playerResources.set(2, playerResources.get(2) + addWheat);
        playerResources.set(3, playerResources.get(3) + addClay);
        playerResources.set(4, playerResources.get(4) + addOre);

        gamePlayerResources.set(playerIDPos, new ArrayList<>(playerResources));
        CreateBoard.gameData.set(4, new ArrayList<>(gamePlayerResources));
        System.out.println(CreateBoard.gameData);
    }

    public static void setResources(Player player, int setWood, int setWool, int setWheat, int setClay, int setOre) {

        int playerIDPos = CreateBoard.gamePlayerUUIDs.indexOf(player.getUniqueId());

        ArrayList<ArrayList> gamePlayerResources = (ArrayList<ArrayList>) CreateBoard.gameData.get(4);
        ArrayList<Integer> playerResources = gamePlayerResources.get(playerIDPos);

        playerResources.set(0, setWood);
        playerResources.set(1, setWool);
        playerResources.set(2, setWheat);
        playerResources.set(3, setClay);
        playerResources.set(4, setOre);

        gamePlayerResources.set(playerIDPos, playerResources);
        CreateBoard.gameData.set(4, gamePlayerResources);
        System.out.println(CreateBoard.gameData);
    }

    public static int getResourceWood(Player player) {

        int playerIDPos = CreateBoard.gamePlayerUUIDs.indexOf(player.getUniqueId());

        ArrayList<ArrayList> gamePlayerResources = (ArrayList<ArrayList>) CreateBoard.gameData.get(4);
        ArrayList<Integer> playerResources = gamePlayerResources.get(playerIDPos);

        return playerResources.get(0);
    }

    public static int getResourceWool(Player player) {

        int playerIDPos = CreateBoard.gamePlayerUUIDs.indexOf(player.getUniqueId());

        ArrayList<ArrayList> gamePlayerResources = (ArrayList<ArrayList>) CreateBoard.gameData.get(4);
        ArrayList<Integer> playerResources = gamePlayerResources.get(playerIDPos);

        return playerResources.get(1);
    }

    public static int getResourceWheat(Player player) {

        int playerIDPos = CreateBoard.gamePlayerUUIDs.indexOf(player.getUniqueId());

        ArrayList<ArrayList> gamePlayerResources = (ArrayList<ArrayList>) CreateBoard.gameData.get(4);
        ArrayList<Integer> playerResources = gamePlayerResources.get(playerIDPos);

        return playerResources.get(2);
    }

    public static int getResourceClay(Player player) {

        int playerIDPos = CreateBoard.gamePlayerUUIDs.indexOf(player.getUniqueId());

        ArrayList<ArrayList> gamePlayerResources = (ArrayList<ArrayList>) CreateBoard.gameData.get(4);
        ArrayList<Integer> playerResources = gamePlayerResources.get(playerIDPos);

        return playerResources.get(3);
    }

    public static int getResourceOre(Player player) {

        int playerIDPos = CreateBoard.gamePlayerUUIDs.indexOf(player.getUniqueId());

        ArrayList<ArrayList> gamePlayerResources = (ArrayList<ArrayList>) CreateBoard.gameData.get(4);
        ArrayList<Integer> playerResources = gamePlayerResources.get(playerIDPos);

        return playerResources.get(4);
    }

}
