package fr.mathip.azplugin.bukkit;

import fr.mathip.azplugin.bukkit.config.ConfigManager;
import fr.mathip.azplugin.bukkit.module.ModuleManager;
import fr.mathip.azplugin.bukkit.packets.PacketWindow;
import lombok.Getter;
import fr.mathip.azplugin.bukkit.commands.*;
import fr.mathip.azplugin.bukkit.commands.items.*;

import org.bukkit.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import pactify.client.api.plsp.packet.client.PLSPPacketEntityMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Main extends JavaPlugin {

    static public Main instance;

    private static AZManager AZManager;

    public List<Player> playersSeeChunks;
    private BukkitTask bukkitTask;

    public boolean isUpdate;

    private CommandManager commandManager;

    private ConfigManager configManager;

    @Getter
    private ModuleManager moduleManager;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 21554);
        instance = this;
        saveDefaultConfig();
        moduleManager = new ModuleManager(this);
        new ConfigManager(this);
        getServer().getPluginManager().registerEvents(new PacketWindow(this), this);
        AZManager = new AZManager(this);
        // System.out.println(getConfig().getBoolean("attack_cooldown"));
        commandManager = new CommandManager();
        getCommand("az").setExecutor(commandManager);
        getCommand("az").setTabCompleter(new AZTabComplete());
        Bukkit.getPluginManager().registerEvents(new AZListener(this), this);
        playersSeeChunks = new ArrayList<>();
        setCommands();
        isUpdate = new AZUpdate(this, 115548).checkForUpdate();
        if (isUpdate) {
            getLogger().info("Une nouvelle version du plugin a été détecté !");
            getLogger().info(
                    "Il est recommendé de le mettre à jour ici: https://www.spigotmc.org/resources/azplugin.115548/");
        }
    }

    private void setCommands() {
        commandManager.addCommand(new AZList());
        commandManager.addCommand(new AZSize());
        commandManager.addCommand(new AZModel());
        commandManager.addCommand(new AZOpacity());
        commandManager.addCommand(new AZWorldEnv());
        commandManager.addCommand(new AZVignette());
        commandManager.addCommand(new AZSeechunks());
        commandManager.addCommand(new AZTag());
        commandManager.addCommand(new AZSubTag());
        commandManager.addCommand(new AZSupTag());
        commandManager.addCommand(new AZSummon());
        commandManager.addCommand(new AZPopup());
        commandManager.addCommand(new AZReload());
        commandManager.addCommand(new AZItemCommand());
        commandManager.addItemCommand(new ItemRenderCommand());
        commandManager.addItemCommand(new ItemSpriteCommand());
        commandManager.addItemCommand(new ItemArmorCommand());
        commandManager.addItemCommand(new ItemTextCommand());
        commandManager.addItemCommand(new ItemRarityCommand());
    }

    public static AZManager getAZManager() {
        return AZManager;
    }

    public String getPluginVersion() {
        return this.getDescription().getVersion();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
