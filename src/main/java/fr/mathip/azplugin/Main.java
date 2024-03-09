package fr.mathip.azplugin;

import fr.mathip.azplugin.commands.AZTabComplete;
import fr.mathip.azplugin.commands.AzCommand;
import fr.speccy.azclientapi.bukkit.handlers.PLSPPlayerModel;
import fr.speccy.azclientapi.bukkit.packets.PacketEntityMeta;
import fr.speccy.azclientapi.bukkit.packets.PacketPlayerModel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public final class Main extends JavaPlugin {

    static public Main instance;
    public HashMap<Player, String> playersTag;
    public HashMap<Player, Float> playersScale;
    public HashMap<Player, Float> playersOpacity;
    public HashMap<Player, PLSPPlayerModel> playersModel;
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




    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        //saveDefaultConfig();
        initConfig();
        //System.out.println(getConfig().getBoolean("attack_cooldown"));
        getCommand("az").setExecutor(new AzCommand());
        getCommand("az").setTabCompleter(new AZTabComplete());
        Bukkit.getPluginManager().registerEvents(new AZListener(), this);
        playersScale = new HashMap<>();
        playersOpacity = new HashMap<>();
        playersTag = new HashMap<>();
        playersModel = new HashMap<>();


        this.bukkitTask = (new BukkitRunnable() {
            public void run() {
                for (Map.Entry<Player, Float> entry : playersScale.entrySet()) {
                    Player player = entry.getKey();
                    Float size = entry.getValue();
                    PacketEntityMeta.setPlayerScale(player, size, size, size, size, size, true);
                }for (Map.Entry<Player, Float> entry : playersOpacity.entrySet()) {
                    Player player = entry.getKey();
                    Float opacity = entry.getValue();
                    PacketEntityMeta.setPlayerOpacity(player, opacity);
                    PacketEntityMeta.setNameTagOpacity(player, opacity);
                    PacketEntityMeta.setSneakNameTagOpacity(player, opacity);
                }for (Map.Entry<Player, String> entry : playersTag.entrySet()) {
                    Player player = entry.getKey();
                    String tag = entry.getValue();
                    PacketEntityMeta.setNameTag(player, tag);
                }for (Map.Entry<Player, PLSPPlayerModel> entry : playersModel.entrySet()) {
                    Player player = entry.getKey();
                    PLSPPlayerModel plspPlayerModel = entry.getValue();
                    PacketPlayerModel.setPlayerModel(player, plspPlayerModel);
                }
            }
        }).runTaskTimer((Plugin)this, 1L, 1L);
    }

    public void initConfig() {
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
        getLogger().info("Config loaded !");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
