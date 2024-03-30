package de.lunaa.catan.methods.board;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import de.lunaa.catan.Catan;
import de.lunaa.catan.methods.general.Resources;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateSettlement {

    private static final int[][][] villagePlacePositions = {
            {{29,-13},{29,-12},{29,-11},{28,-13},{28,-12},{28,-11}},
            {{29,-1},{29,0},{29,1},{28,-1},{28,0},{28,1}},
            {{29,11},{29,12},{29,13},{28,11},{28,12},{28,13}},
            {{26,-19},{26,-18},{26,-17},{25,-19},{25,-18},{25,-17}},
            {{26,-7},{26,-6},{26,-5},{25,-7},{25,-6},{25,-5}},
            {{26,5},{26,6},{26,7},{25,5},{25,6},{25,7}},
            {{26,17},{26,18},{26,19},{25,17},{25,18},{25,19}},
            {{18,-19},{18,-18},{18,-17},{17,-19},{17,-18},{17,-17}},
            {{18,-7},{18,-6},{18,-5},{17,-7},{17,-6},{17,-5}},
            {{18,5},{18,6},{18,7},{17,5},{17,6},{17,7}},
            {{18,17},{18,18},{18,19},{17,17},{17,18},{17,19}},
            {{15,-25},{15,-24},{15,-23},{14,-25},{14,-24},{14,-23}},
            {{15,-13},{15,-12},{15,-11},{14,-13},{14,-12},{14,-11}},
            {{15,-1},{15,0},{15,1},{14,-1},{14,0},{14,1}},
            {{15,11},{15,12},{15,13},{14,11},{14,12},{14,13}},
            {{15,23},{15,24},{15,25},{14,23},{14,24},{14,25}},
            {{7,-25},{7,-24},{7,-23},{6,-25},{6,-24},{6,-23}},
            {{7,-13},{7,-12},{7,-11},{6,-13},{6,-12},{6,-11}},
            {{7,-1},{7,0},{7,1},{6,-1},{6,0},{6,1}},
            {{7,11},{7,12},{7,13},{6,11},{6,12},{6,13}},
            {{7,23},{7,24},{7,25},{6,23},{6,24},{6,25}},
            {{4,-31},{4,-30},{4,-29},{3,-31},{3,-30},{3,-29}},
            {{4,-19},{4,-18},{4,-17},{3,-19},{3,-18},{3,-17}},
            {{4,-7},{4,-6},{4,-5},{3,-7},{3,-6},{3,-5}},
            {{4,5},{4,6},{4,7},{3,5},{3,6},{3,7}},
            {{4,17},{4,18},{4,19},{3,17},{3,18},{3,19}},
            {{4,29},{4,30},{4,31},{3,29},{3,30},{3,31}},
            {{-4,-31},{-4,-30},{-4,-29},{-5,-31},{-5,-30},{-5,-29}},
            {{-4,-19},{-4,-18},{-4,-17},{-5,-19},{-5,-18},{-5,-17}},
            {{-4,-7},{-4,-6},{-4,-5},{-5,-7},{-5,-6},{-5,-5}},
            {{-4,5},{-4,6},{-4,7},{-5,5},{-5,6},{-5,7}},
            {{-4,17},{-4,18},{-4,19},{-5,17},{-5,18},{-5,19}},
            {{-4,29},{-4,30},{-4,31},{-5,29},{-5,30},{-5,31}},
            {{-7,-25},{-7,-24},{-7,-23},{-8,-25},{-8,-24},{-8,-23}},
            {{-7,-13},{-7,-12},{-7,-11},{-8,-13},{-8,-12},{-8,-11}},
            {{-7,-1},{-7,0},{-7,1},{-8,-1},{-8,0},{-8,1}},
            {{-7,11},{-7,12},{-7,13},{-8,11},{-8,12},{-8,13}},
            {{-7,23},{-7,24},{-7,25},{-8,23},{-8,24},{-8,25}},
            {{-15,-25},{-15,-24},{-15,-23},{-16,-25},{-16,-24},{-16,-23}},
            {{-15,-13},{-15,-12},{-15,-11},{-16,-13},{-16,-12},{-16,-11}},
            {{-15,-1},{-15,0},{-15,1},{-16,-1},{-16,0},{-16,1}},
            {{-15,11},{-15,12},{-15,13},{-16,11},{-16,12},{-16,13}},
            {{-15,23},{-15,24},{-15,25},{-16,23},{-16,24},{-16,25}},
            {{-18,-19},{-18,-18},{-18,-17},{-19,-19},{-19,-18},{-19,-17}},
            {{-18,-7},{-18,-6},{-18,-5},{-19,-7},{-19,-6},{-19,-5}},
            {{-18,5},{-18,6},{-18,7},{-19,5},{-19,6},{-19,7}},
            {{-18,17},{-18,18},{-18,19},{-19,17},{-19,18},{-19,19}},
            {{-26,-19},{-26,-18},{-26,-17},{-27,-19},{-27,-18},{-27,-17}},
            {{-26,-7},{-26,-6},{-26,-5},{-27,-7},{-27,-6},{-27,-5}},
            {{-26,5},{-26,6},{-26,7},{-27,5},{-27,6},{-27,7}},
            {{-26,17},{-26,18},{-26,19},{-27,17},{-27,18},{-27,19}},
            {{-29,-13},{-29,-12},{-29,-11},{-30,-13},{-30,-12},{-30,-11}},
            {{-29,-1},{-29,0},{-29,1},{-30,-1},{-30,0},{-30,1}},
            {{-29,11},{-29,12},{-29,13},{-30,11},{-30,12},{-30,13}}
    };

    public static int[][] placePositionYields = {
            {0},
            {1},
            {2},
            {0},
            {0,1},
            {1,2},
            {2},
            {0,3},
            {0,1,4},
            {1,2,5},
            {2,6},
            {3},
            {0,3,4},
            {1,4,5},
            {2,5,6},
            {6},
            {3,7},
            {3,4,8},
            {4,5,9},
            {5,6,10},
            {6,11},
            {7},
            {3,7,8},
            {4,8,9},
            {5,9,10},
            {6,10,11},
            {11},
            {7},
            {7,8,12},
            {8,9,13},
            {9,10,14},
            {10,11,15},
            {11},
            {7,12},
            {8,12,13},
            {9,13,14},
            {10,14,15},
            {11,15},
            {12},
            {12,13,16},
            {13,14,17},
            {14,15,18},
            {15},
            {12,16},
            {13,16,17},
            {14,17,18},
            {15,18},
            {16},
            {16,17},
            {17,18},
            {18},
            {16},
            {17},
            {18}
    };

    public static void newSettlement(Player player, int x, int y, int z, int color) {

        if (!CreateBoard.isDiceRollable) {
            int playerIDPos = CreateBoard.gamePlayerUUIDs.indexOf(player.getUniqueId());
            ArrayList<ArrayList> gameBuildingData = (ArrayList<ArrayList>) CreateBoard.gameData.get(5);
            ArrayList<ArrayList> playerBuildingDataList = gameBuildingData.get(playerIDPos);
            ArrayList<Integer> gameCoords = (ArrayList<Integer>) CreateBoard.gameData.get(0);

            int settlementCount = 0;
            for (int i=0; i<playerBuildingDataList.size(); i++) {
                ArrayList<Object> playerBuildingData = playerBuildingDataList.get(i);
                if (playerBuildingData.get(0) == "settlement") {
                    settlementCount++;
                }
                if (settlementCount >= Catan.config.getInt("buildings.maxSettlements")) {
                    player.sendMessage(ChatColor.RED + "You cannot place more than " + Catan.config.getString("buildings.maxSettlements") + " settlements!");
                    return;
                }
            }

            if (Resources.getResourceClay(player) >= 1 && Resources.getResourceWood(player) >= 1 && Resources.getResourceWool(player) >= 1 && Resources.getResourceWheat(player) >= 1) {
                buildingCheckLoop:
                for (int i = 0; i < villagePlacePositions.length; i++) {
                    int[][] villagePlacePosition = villagePlacePositions[i];
                    int[] buildingPos = {gameCoords.get(0) - x - 1, gameCoords.get(2) - z};
                    for (int i1 = 0; i1 < 6; i1++) {
                        int[] buildingCoordChecksList = villagePlacePosition[i1];
                        System.out.println(Arrays.toString(buildingCoordChecksList));
                        System.out.println(Arrays.toString(villagePlacePosition[i1]));
                        if (buildingPos[0] == buildingCoordChecksList[0] && buildingPos[1] == buildingCoordChecksList[1]) {
                            System.out.println("VILLAGE PLACED");
                            System.out.println(Arrays.toString(buildingPos));
                            CreateBoard.buildingDataTemplate.set(0, "settlement");
                            CreateBoard.buildingDataTemplate.set(1, i);
                            playerBuildingDataList.add(new ArrayList<>(CreateBoard.buildingDataTemplate));
                            gameBuildingData.set(playerIDPos, new ArrayList<>(playerBuildingDataList));
                            Resources.addResources(player, -1, -1, -1,  -1, 0);
                            CreateBoard.playerVictoryPoints.set(CreateBoard.gamePlayerUUIDs.indexOf(player.getUniqueId()), CreateBoard.playerVictoryPoints.get(CreateBoard.gamePlayerUUIDs.indexOf(player.getUniqueId())) + 1);

                            int finalI = i;
                            Bukkit.getScheduler().scheduleSyncDelayedTask(Catan.plugin, () -> placeSettlement(player, gameCoords, finalI, color),1);

                            break buildingCheckLoop;
                        } else if (i==villagePlacePositions.length-1 && i1==5) {
                            player.sendMessage(ChatColor.RED + "You cannot place a settlement here!");
                        }
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "You don't have the required materials to place this settlement!");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Roll the dice first!");
        }

    }


    private static void placeSettlement(Player player, ArrayList<Integer> coords, int i, int color){

        //Load clipboard and world
        Clipboard clipboard;
        World world = Bukkit.getPlayer(player.getUniqueId()).getWorld();
        com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(world);
        File file = new File(Catan.plugin.getDataFolder() + File.separator + "schematics" + File.separator + "buildings" + File.separator + "settlement" + color + ".schem");

        // Load schematic
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try {
            try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
                clipboard = reader.read();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Paste schematic
        int offsetX = villagePlacePositions[i][0][0];
        int offsetZ = villagePlacePositions[i][0][1];
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(weWorld)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(coords.get(0) - offsetX - 2, coords.get(1), coords.get(2) - offsetZ + 1))
                    .ignoreAirBlocks(true)
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }

    }

}
