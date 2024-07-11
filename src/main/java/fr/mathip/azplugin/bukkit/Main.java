package fr.mathip.azplugin.bukkit;

import fr.mathip.azplugin.bukkit.azclientapi.AZManager;
import fr.mathip.azplugin.bukkit.azclientapi.handlers.PLSPPlayerModel;
import fr.mathip.azplugin.bukkit.azclientapi.packets.player.*;
import fr.mathip.azplugin.bukkit.commands.AZTabComplete;
import fr.mathip.azplugin.bukkit.commands.AzCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Main extends JavaPlugin {

    static public Main instance;

    private static AZManager AZManager;
    public HashMap<Player, String> playersTag;
    public HashMap<Player, String> playersSupTag;
    public HashMap<Player, String> playersSubTag;
    public HashMap<Player, Float> playersScale;
    public HashMap<Player, Float> playersOpacity;
    public HashMap<Player, PLSPPlayerModel> playersModel;

    public List<Player> playersSeeChunks;
    private BukkitTask bukkitTask;

    public boolean isUpdate;

    public String specialInventoryCharacter;


    public boolean attackCooldown;
    public boolean playerPush;
    public boolean largeHitBox;
    public boolean swordBlocking;
    public boolean hitAndBlock;
    public boolean oldEnchantments;
    public boolean pvpHitPriority;
    public boolean seeChunks;
    public boolean sidebarScore;
    public boolean smoothExperienceBar;
    public boolean sortTabListByName;
    public boolean serverSideAnvil;
    public boolean pistonRetractEnvities;
    public boolean hitIndicator;

    public int chatMaxMessageSize;
    public int maxBuildHeight;
    public boolean updateMessage;

    public ArrayList<String> joinWithAZCommands;
    public ArrayList<String> joinWithoutAZCommands;



    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 21554);
        instance = this;
        saveDefaultConfig();
        initConfig();
        getServer().getPluginManager().registerEvents(new PacketWindow(this), this);
        AZManager = new AZManager(this);
        //System.out.println(getConfig().getBoolean("attack_cooldown"));
        getCommand("az").setExecutor(new AzCommand());
        getCommand("az").setTabCompleter(new AZTabComplete());
        Bukkit.getPluginManager().registerEvents(new AZListener(this), this);
        joinWithAZCommands = new ArrayList<>();
        joinWithoutAZCommands = new ArrayList<>();
        playersScale = new HashMap<>();
        playersOpacity = new HashMap<>();
        playersTag = new HashMap<>();
        playersSupTag = new HashMap<>();
        playersSubTag = new HashMap<>();
        playersModel = new HashMap<>();
        playersSeeChunks = new ArrayList<>();
        isUpdate = new AZUpdate(this, 115548).checkForUpdate();
        if (isUpdate) {
            getLogger().info("Une nouvelle version du plugin a été détecté !");
            getLogger().info("Il est recommendé de le mettre à jour ici: https://www.spigotmc.org/resources/azplugin.115548/");
        }


        /*this.bukkitTask = (new BukkitRunnable() {
            public void run() {
                for (Map.Entry<Player, Float> entry : playersScale.entrySet()) {
                    Player player = entry.getKey();
                    Float size = entry.getValue();
                    PacketPlayerScale.setScale(player, size, size, size, size, size, size);
                }for (Map.Entry<Player, Float> entry : playersOpacity.entrySet()) {
                    Player player = entry.getKey();
                    Float opacity = entry.getValue();
                    PacketPlayerOpacity.setPlayer(player, opacity);
                    PacketPlayerOpacity.setNameTag(player, opacity);
                    PacketPlayerOpacity.setSneakNameTag(player, opacity);
                }for (Map.Entry<Player, String> entry : playersTag.entrySet()) {
                    Player player = entry.getKey();
                    String tag = entry.getValue();
                    PacketPlayerTag.setNameTag(player, tag);
                }for (Map.Entry<Player, String> entry : playersSupTag.entrySet()) {
                    Player player = entry.getKey();
                    String tag = entry.getValue();
                    PacketPlayerTag.setSupTag(player, tag);
                }for (Map.Entry<Player, String> entry : playersSubTag.entrySet()) {
                    Player player = entry.getKey();
                    String tag = entry.getValue();
                    PacketPlayerTag.setSubTag(player, tag);
                }for (Map.Entry<Player, PLSPPlayerModel> entry : playersModel.entrySet()) {
                    Player player = entry.getKey();
                    PLSPPlayerModel plspPlayerModel = entry.getValue();
                    PacketPlayerModel.setModel(player, plspPlayerModel);
                }
            }
        }).runTaskTimer((Plugin)this, 1L, 1L);*/
    }
    public AZManager getAZManager() {
        return AZManager;
    }

    public String getPluginVersion() {
        return this.getDescription().getVersion();
    }


    public void initConfig() {
        joinWithAZCommands = (ArrayList<String>) getConfig().get("join-with-az-commands");
        joinWithoutAZCommands = (ArrayList<String>) getConfig().get("join-without-az-commands");

        updateMessage = getConfig().getBoolean("update-message");
        attackCooldown = getConfig().getBoolean("attack_cooldown");
        playerPush = getConfig().getBoolean("player_push");
        largeHitBox = getConfig().getBoolean("large_hitbox");
        swordBlocking = getConfig().getBoolean("sword_blocking");
        hitAndBlock = getConfig().getBoolean("hit_and_block");
        oldEnchantments = getConfig().getBoolean("old_enchantments");
        pvpHitPriority = getConfig().getBoolean("pvp_hit_priority");
        seeChunks = getConfig().getBoolean("see_chunks");
        sidebarScore = getConfig().getBoolean("sidebar_scores");
        smoothExperienceBar = getConfig().getBoolean("smooth_experience_bar");
        sortTabListByName = getConfig().getBoolean("sort_tab_list_by_names");
        serverSideAnvil = getConfig().getBoolean("server_side_anvil");
        pistonRetractEnvities = getConfig().getBoolean("pistons_retract_entities");
        hitIndicator = getConfig().getBoolean("hit_indicator");

        chatMaxMessageSize = getConfig().getInt("chat_message_max_size");
        maxBuildHeight = getConfig().getInt("max_build_height");

        specialInventoryCharacter = getConfig().getString("special-transparent-inventory-character");
        getLogger().info("Config loaded !");
    }

    public void sendPLSPPackets(Player player, Player target) {
        if (playersScale.containsKey(player)) {
            Float size = playersScale.get(player);
            //PacketPlayerScale.setScale(player, size, size, size, size, size, size);
            Bukkit.getScheduler().runTaskLater(instance, new Runnable() {
                @Override
                public void run() {
                    PacketPlayerScale.setScale(player, size, size, size, size, size, size, target);
                }
            }, 1);
        }
        if (playersModel.containsKey(player)) {
            PLSPPlayerModel plspPlayerModel = playersModel.get(player);
            //PacketPlayerModel.setModel(player, plspPlayerModel, target);
            Bukkit.getScheduler().runTaskLater(instance, new Runnable() {
                @Override
                public void run() {
                    PacketPlayerModel.setModel(player, plspPlayerModel, target);
                }
            }, 1);
        }
        if (playersOpacity.containsKey(player)) {
            Float opacity = playersOpacity.get(player);
            //PacketPlayerOpacity.setOpacity(player, opacity, target);
            Bukkit.getScheduler().runTaskLater(instance, new Runnable() {
                @Override
                public void run() {
                    PacketPlayerOpacity.setOpacity(player, opacity, target);
                }
            }, 1);
        }
        if (playersTag.containsKey(player)) {
            String tag = playersTag.get(player);
            //PacketPlayerTag.setNameTag(player, tag, target);
            Bukkit.getScheduler().runTaskLater(instance, new Runnable() {
                @Override
                public void run() {
                    PacketPlayerTag.setNameTag(player, tag, target);
                }
            }, 1);
        }
        if (playersSupTag.containsKey(player)) {
            String tag = playersSupTag.get(player);
            //PacketPlayerTag.setSupTag(player, tag, target);
            Bukkit.getScheduler().runTaskLater(instance, new Runnable() {
                @Override
                public void run() {
                    PacketPlayerTag.setSupTag(player, tag, target);
                }
            }, 1);
        }
        if (playersSubTag.containsKey(player)) {
            String tag = playersSubTag.get(player);
            //PacketPlayerTag.setSubTag(player, tag, target);
            Bukkit.getScheduler().runTaskLater(instance, new Runnable() {
                @Override
                public void run() {
                    PacketPlayerTag.setSubTag(player, tag, target);
                }
            }, 1);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
