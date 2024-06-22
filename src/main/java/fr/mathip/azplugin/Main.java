package fr.mathip.azplugin;

import fr.mathip.azplugin.commands.AZTabComplete;
import fr.mathip.azplugin.commands.AzCommand;
import fr.speccy.azclientapi.bukkit.handlers.PLSPPlayerModel;
import fr.speccy.azclientapi.bukkit.packets.player.PacketPlayerModel;
import fr.speccy.azclientapi.bukkit.packets.player.PacketPlayerOpacity;
import fr.speccy.azclientapi.bukkit.packets.player.PacketPlayerScale;
import fr.speccy.azclientapi.bukkit.packets.player.PacketPlayerTag;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Main extends JavaPlugin {

    static public Main instance;
    public HashMap<Player, String> playersTag;
    public HashMap<Player, String> playersSupTag;
    public HashMap<Player, String> playersSubTag;
    public HashMap<Player, Float> playersScale;
    public HashMap<Player, Float> playersOpacity;
    public HashMap<Player, PLSPPlayerModel> playersModel;

    public List<Player> playersSeeChunks;
    private BukkitTask bukkitTask;


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
        //System.out.println(getConfig().getBoolean("attack_cooldown"));
        getCommand("az").setExecutor(new AzCommand());
        getCommand("az").setTabCompleter(new AZTabComplete());
        Bukkit.getPluginManager().registerEvents(new AZListener(), this);
        joinWithAZCommands = new ArrayList<>();
        joinWithoutAZCommands = new ArrayList<>();
        playersScale = new HashMap<>();
        playersOpacity = new HashMap<>();
        playersTag = new HashMap<>();
        playersSupTag = new HashMap<>();
        playersSubTag = new HashMap<>();
        playersModel = new HashMap<>();
        playersSeeChunks = new ArrayList<>();


        this.bukkitTask = (new BukkitRunnable() {
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
        }).runTaskTimer((Plugin)this, 1L, 1L);
    }

    public void initConfig() {
        joinWithAZCommands = (ArrayList<String>) getConfig().get("join-with-az-commands");
        joinWithoutAZCommands = (ArrayList<String>) getConfig().get("join-without-az-commands");


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
        getLogger().info("Config loaded !");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
