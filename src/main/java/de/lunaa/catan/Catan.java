package de.lunaa.catan;

import de.lunaa.catan.cmds.*;
import de.lunaa.catan.cmds.debug.*;
import de.lunaa.catan.events.*;
import de.lunaa.catan.methods.general.CreateBoardCooldown;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.file.Files;

public final class Catan extends JavaPlugin {

    public static Catan plugin;
    public static FileConfiguration config;

    @Override
    public void onEnable() {

        // Load Config
        saveDefaultConfig();
        config = getConfig();
        plugin = this;

        // Create Data Folder
        if (!Files.exists(this.getDataFolder().toPath())) {
            new File(String.valueOf(this.getDataFolder().toPath())).mkdirs();
        }

        // Implement Commands
        getCommand("newboard").setExecutor(new CreateNewBoard());
        getCommand("deleteboard").setExecutor(new DeleteBoard());
        getCommand("invitegame").setExecutor(new InviteGame());
        getCommand("joingame").setExecutor(new JoinGame());
        getCommand("startgame").setExecutor(new StartGame());
        getCommand("setresources").setExecutor(new SetResources());
        getCommand("addresources").setExecutor(new AddResources());
        getCommand("removeresources").setExecutor(new RemoveResources());
        getCommand("turn").setExecutor(new Turn());

        // Implement Events
        PluginManager plugman = getServer().getPluginManager();

        plugman.registerEvents(new InventoryClick(), this);
        plugman.registerEvents(new InventoryCreative(), this);
        plugman.registerEvents(new PlayerDrop(), this);
        plugman.registerEvents(new PlayerBlockPlace(), this);
        plugman.registerEvents(new PlayerBlockBreak(), this);
        plugman.registerEvents(new PlayerToggleFlight(), this);
        plugman.registerEvents(new PlayerInteractItem(), this);

        // Implement Methods
        CreateBoardCooldown.setupCooldown();

        // Initialize Repeating Tasks
        GameLogic.init();

        // Finish
        getLogger().info("Catan Plugin launched successfully!");
    }

    @Override
    public void onDisable() {

    }
}
