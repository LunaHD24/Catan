package de.lunaa.catan.methods.board;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.*;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import de.lunaa.catan.Catan;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.io.*;
import java.util.*;

public class CreateBoard {

    static Random rand = new Random();
    public static boolean gameExists = false;
    public static String gameState = "NONE";
    public static ArrayList<Integer> gameCoords = new ArrayList<>();
    public static ArrayList<String> gameFieldTypes = new ArrayList<>();
    public static ArrayList<Integer> gameFieldNumbers = new ArrayList<>();
    public static ArrayList<ArrayList> gameFieldData = new ArrayList<>();
    public static ArrayList<UUID> gamePlayerUUIDs = new ArrayList<>();
    public static ArrayList<Integer> gamePlayerResourceTemplate = new ArrayList<>();
    public static ArrayList<ArrayList> gamePlayerResources = new ArrayList<>();
    public static ArrayList<Object> gameData = new ArrayList<>();
    public enum boardPieces{DESERT,CLAY,ORE,WHEAT,SHEEP,FOREST,BOARD_LAYOUT}
    public static boardPieces boardPiece = boardPieces.BOARD_LAYOUT;
    public static int clayPieceCount = 0;
    public static int orePieceCount = 0;
    public static int wheatPieceCount = 0;
    public static int sheepPieceCount = 0;
    public static int forestPieceCount = 0;
    public static boolean desertSpawned = false;
    public static ArrayList<UUID> numberDisplayUUIDs = new ArrayList<>();
    public static ArrayList<ArrayList> buildingDataTemplateList = new ArrayList<>();
    public static ArrayList<Object> buildingDataTemplate = new ArrayList<>();
    public static ArrayList<ArrayList> buildingData = new ArrayList<>();
    public static ArrayList<Integer> playerVictoryPoints = new ArrayList<>();
    public static UUID currentPlayer;
    public static ArrayList<Integer> colorList;
    public static boolean isDiceRollable = false;

    public static int[][] boardPieceMap = {
            {22, -12},
            {22, 0},
            {22, 12},
            {11, -18},
            {11, -6},
            {11, 6},
            {11, 18},
            {0, -24},
            {0, -12},
            {0, 0},
            {0, 12},
            {0, 24},
            {-11, -18},
            {-11, -6},
            {-11, 6},
            {-11, 18},
            {-22, -12},
            {-22, 0},
            {-22, 12}
    };


    public static void newBoard(Player player) {

        // Create Game Data
        if (gameCoords.isEmpty()) {
            gameCoords.add(0, 0);
            gameCoords.add(1, 0);
            gameCoords.add(2, 0);
        }
        if (gameFieldData.isEmpty()) {
            gameFieldData.add(gameFieldTypes);
            gameFieldData.add(gameFieldNumbers);
        }
        if (gameData.isEmpty()) {
            gameData.add(gameCoords);
            gameData.add(gamePlayerUUIDs);
            gameData.add(gameFieldData);
            gameData.add(gameState);
            gameData.add(gamePlayerResources);
            gameData.add(buildingData);
            gameData.add(playerVictoryPoints);
            gameData.add(currentPlayer);
            gameData.add(isDiceRollable);
        }

        gameCoords.set(0, player.getLocation().getBlockX());
        gameCoords.set(1, player.getLocation().getBlockY());
        gameCoords.set(2, player.getLocation().getBlockZ());

        addPlayerToBoard(player);

        gameState = "IDLE";

        gameData.set(0, gameCoords);
        gameData.set(1, gamePlayerUUIDs);
        gameData.set(2, gameFieldData);
        gameData.set(3, gameState);
        gameData.set(4, gamePlayerResources);
        gameData.set(5, buildingData);
        gameData.set(6, playerVictoryPoints);
        gameData.set(7, currentPlayer);
        gameData.set(8, isDiceRollable);

        gameExists = true;
        loadBoard(player);
        System.out.println(gameData);
    }

    private static void addPlayerToBoard(Player player) {
        gamePlayerUUIDs.add(player.getUniqueId());
        gameData.set(1, gamePlayerUUIDs);
    }

    private static void loadBoard(Player player) {

        boardPiece = boardPieces.BOARD_LAYOUT;
        loadBoardPiece(boardPiece, player, 0, 0);

        for (int i1=0; i1<19; i1++) {
            int fieldNumber = rand.nextInt(12) + 1;
            if (fieldNumber == 7) {
                i1--;
            } else {
                if (fieldNumber >= 3 && fieldNumber <= 11) {
                    if (Collections.frequency(gameFieldNumbers, fieldNumber) < 2) {
                        gameFieldNumbers.add(fieldNumber);
                    } else {
                        i1--;
                    }
                } else {
                    if (Collections.frequency(gameFieldNumbers, fieldNumber) < 1) {
                        gameFieldNumbers.add(fieldNumber);
                    } else {
                        i1--;
                    }
                }
            }
        }

        for (int i=0; i < 19; i++) {
            int piece = rand.nextInt(6);

            switch (piece) {
                case(0):
                    boardPiece = boardPieces.CLAY;
                    if (clayPieceCount <= 2) {
                        loadBoardPiece(boardPiece, player, boardPieceMap[i][0], boardPieceMap[i][1]);
                        clayPieceCount++;
                        gameFieldTypes.add("CLAY");
                    } else {
                        i--;
                    }
                case(1):
                    boardPiece = boardPieces.ORE;
                    if (orePieceCount <= 2) {
                        loadBoardPiece(boardPiece, player, boardPieceMap[i][0], boardPieceMap[i][1]);
                        orePieceCount++;
                        gameFieldTypes.add("ORE");
                    } else {
                        i--;
                    }
                case(2):
                    boardPiece = boardPieces.WHEAT;
                    if (wheatPieceCount <= 3) {
                        loadBoardPiece(boardPiece, player, boardPieceMap[i][0], boardPieceMap[i][1]);
                        wheatPieceCount++;
                        gameFieldTypes.add("WHEAT");
                    } else {
                        i--;
                    }
                case(3):
                    boardPiece = boardPieces.SHEEP;
                    if (sheepPieceCount <= 3) {
                        loadBoardPiece(boardPiece, player, boardPieceMap[i][0], boardPieceMap[i][1]);
                        sheepPieceCount++;
                        gameFieldTypes.add("SHEEP");
                    } else {
                        i--;
                    }
                case(4):
                    boardPiece = boardPieces.FOREST;
                    if (forestPieceCount <= 3) {
                        loadBoardPiece(boardPiece, player, boardPieceMap[i][0], boardPieceMap[i][1]);
                        forestPieceCount++;
                        gameFieldTypes.add("FOREST");
                    } else {
                        i--;
                    }
                case(5):
                    boardPiece = boardPieces.DESERT;
                    if (!desertSpawned) {
                        loadBoardPiece(boardPiece, player, boardPieceMap[i][0], boardPieceMap[i][1]);
                        desertSpawned = true;
                        gameFieldTypes.add("DESERT");
                    } else {
                        i--;
                    }
            }
        }
        for (int i=0; i < 19; i++) {
            createFieldNumberDisplay(player.getLocation().getBlockX()+boardPieceMap[i][0], player.getLocation().getBlockY()+5, player.getLocation().getBlockZ()+boardPieceMap[i][1]+1.0f, gameFieldNumbers.get(i), player);
        }

        gameFieldData.set(0, gameFieldTypes);
        gameFieldData.set(1, gameFieldNumbers);
        gameData.set(2, gameFieldData);
        player.sendMessage(ChatColor.GREEN + "Successfully created a new board!");
    }

    public static void createFieldNumberDisplay(float x, float y, float z, int num, Player player) {
        World world = Bukkit.getPlayer(player.getUniqueId()).getWorld();
        Location location = new Location(world, x, y, z);
        Transformation transformation = new Transformation(new Vector3f(0.0f,0.0f,0.0f), new Quaternionf(0.0f,0.0f,0.0f,1.0f), new Vector3f(10.0f,10.0f,10.0f), new Quaternionf(0.0f,0.0f,0.0f,1.0f));
        world.spawn(location, TextDisplay.class, textDisplay -> {
        textDisplay.setText(ChatColor.LIGHT_PURPLE + String.valueOf(num));
        textDisplay.setBillboard(Display.Billboard.FIXED);
        textDisplay.setRotation(0,-90);
        textDisplay.setBackgroundColor(Color.fromARGB(0,0,0,0));
        textDisplay.setViewRange(2.5f);
        textDisplay.setBrightness(new Display.Brightness(15,15));
        textDisplay.setTransformation(transformation);
        numberDisplayUUIDs.add(textDisplay.getUniqueId());
        });
    }

    private static void loadBoardLayout(Player player) {

        Clipboard clipboard;
        World world = Bukkit.getPlayer(player.getUniqueId()).getWorld();
        com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(world);
        Location location = player.getLocation();
        File file = new File(Catan.plugin.getDataFolder() + File.separator + "schematics" + File.separator + "board" + File.separator + "board_layout.schem");

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
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(weWorld)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ()))
                    .ignoreAirBlocks(true)
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }

    }

    private static void loadBoardPiece(boardPieces boardPiece, Player player, int offsetX, int offsetZ) {
        Clipboard clipboard;
        World world = Bukkit.getPlayer(player.getUniqueId()).getWorld();
        com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(world);
        Location location = player.getLocation();
        File file = new File(Catan.plugin.getDataFolder() + File.separator + "schematics" + File.separator + "board" + File.separator + boardPiece.name().toLowerCase() + ".schem");

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
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(weWorld)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(location.getBlockX() + offsetX, location.getBlockY(), location.getBlockZ() + offsetZ))
                    .ignoreAirBlocks(true)
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }
    }


    private static void loadDesertPiece(Player player, int offsetX, int offsetZ) {

        Clipboard clipboard;
        World world = Bukkit.getPlayer(player.getUniqueId()).getWorld();
        com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(world);
        Location location = player.getLocation();
        File file = new File(Catan.plugin.getDataFolder() + File.separator + "schematics" + File.separator + "board" + File.separator + "desert.schem");

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
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(weWorld)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(location.getBlockX() + offsetX, location.getBlockY(), location.getBlockZ() + offsetZ))
                    .ignoreAirBlocks(true)
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }

    }

    private static void loadClayPiece(Player player, int offsetX, int offsetZ) {

        Clipboard clipboard;
        World world = Bukkit.getPlayer(player.getUniqueId()).getWorld();
        com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(world);
        Location location = player.getLocation();
        File file = new File(Catan.plugin.getDataFolder() + File.separator + "schematics" + File.separator + "board" + File.separator + "clay.schem");

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
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(weWorld)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(location.getBlockX() + offsetX, location.getBlockY(), location.getBlockZ() + offsetZ))
                    .ignoreAirBlocks(true)
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }

    }

    private static void loadForestPiece(Player player, int offsetX, int offsetZ) {

        Clipboard clipboard;
        World world = Bukkit.getPlayer(player.getUniqueId()).getWorld();
        com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(world);
        Location location = player.getLocation();
        File file = new File(Catan.plugin.getDataFolder() + File.separator + "schematics" + File.separator + "board" + File.separator +"forest.schem");

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
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(weWorld)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(location.getBlockX() + offsetX, location.getBlockY(), location.getBlockZ() + offsetZ))
                    .ignoreAirBlocks(true)
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }

    }

    private static void loadOrePiece(Player player, int offsetX, int offsetZ) {

        Clipboard clipboard;
        World world = Bukkit.getPlayer(player.getUniqueId()).getWorld();
        com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(world);
        Location location = player.getLocation();
        File file = new File(Catan.plugin.getDataFolder() + File.separator + "schematics" + File.separator + "board" + File.separator +"ore.schem");

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
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(weWorld)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(location.getBlockX() + offsetX, location.getBlockY(), location.getBlockZ() + offsetZ))
                    .ignoreAirBlocks(true)
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }

    }

    private static void loadSheepPiece(Player player, int offsetX, int offsetZ) {

        Clipboard clipboard;
        World world = Bukkit.getPlayer(player.getUniqueId()).getWorld();
        com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(world);
        Location location = player.getLocation();
        File file = new File(Catan.plugin.getDataFolder() + File.separator + "schematics" + File.separator + "board" + File.separator + "sheep.schem");

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
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(weWorld)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(location.getBlockX() + offsetX, location.getBlockY(), location.getBlockZ() + offsetZ))
                    .ignoreAirBlocks(true)
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }

    }

    private static void loadWheatPiece(Player player, int offsetX, int offsetZ) {

        Clipboard clipboard;
        World world = Bukkit.getPlayer(player.getUniqueId()).getWorld();
        com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(world);
        Location location = player.getLocation();
        File file = new File(Catan.plugin.getDataFolder() + File.separator + "schematics" + File.separator + "board" + File.separator + "wheat.schem");

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
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(weWorld)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(location.getBlockX() + offsetX, location.getBlockY(), location.getBlockZ() + offsetZ))
                    .ignoreAirBlocks(true)
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }

    }

}
