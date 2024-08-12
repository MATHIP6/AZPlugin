package fr.mathip.azplugin.bukkit;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    private Main main;

    private static ConfigManager instance;

    private boolean attackCooldown;
    private boolean playerPush;
    private boolean largeHitBox;
    private boolean swordBlocking;
    private boolean hitAndBlock;
    private boolean oldEnchantments;
    private boolean pvpHitPriority;
    private boolean seeChunks;
    private boolean sidebarScore;
    private boolean smoothExperienceBar;
    private boolean sortTabListByName;
    private boolean serverSideAnvil;
    private boolean pistonRetractEntities;
    private boolean hitIndicator;

    private int chatMaxMessageSize;
    private int maxBuildHeight;

    private ArrayList<String> joinWithAZCommands;
    private ArrayList<String> joinWithoutAZCommands;

    private ArrayList<String> specialInventoryCharacters;
private ArrayList<PacketUiComponent> UIComponents;

    private boolean updateMessage;

    public ConfigManager(Main main) {
        this.main = main;
        instance = this;
        joinWithAZCommands = new ArrayList<>();
        joinWithoutAZCommands = new ArrayList<>();
        specialInventoryCharacters = new ArrayList<>();
        UIComponents = new ArrayList<>();
        initConfig();
    }

    public void initConfig() {

        joinWithAZCommands = (ArrayList<String>) main.getConfig().get("join-with-az-commands");
        joinWithoutAZCommands = (ArrayList<String>) main.getConfig().get("join-without-az-commands");

        updateMessage = main.getConfig().getBoolean("update-message");
        attackCooldown = main.getConfig().getBoolean("attack_cooldown");
        playerPush = main.getConfig().getBoolean("player_push");
        largeHitBox = main.getConfig().getBoolean("large_hitbox");
        swordBlocking = main.getConfig().getBoolean("sword_blocking");
        hitAndBlock = main.getConfig().getBoolean("hit_and_block");
        oldEnchantments = main.getConfig().getBoolean("old_enchantments");
        pvpHitPriority = main.getConfig().getBoolean("pvp_hit_priority");
        seeChunks = main.getConfig().getBoolean("see_chunks");
        sidebarScore = main.getConfig().getBoolean("sidebar_scores");
        smoothExperienceBar = main.getConfig().getBoolean("smooth_experience_bar");
        sortTabListByName = main.getConfig().getBoolean("sort_tab_list_by_names");
        serverSideAnvil = main.getConfig().getBoolean("server_side_anvil");
        pistonRetractEntities = main.getConfig().getBoolean("pistons_retract_entities");
        hitIndicator = main.getConfig().getBoolean("hit_indicator");

        chatMaxMessageSize = main.getConfig().getInt("chat_message_max_size");
        maxBuildHeight = main.getConfig().getInt("max_build_height");

        specialInventoryCharacters = (ArrayList<String>) main.getConfig().get("special-transparent-inventory-character");
        new PopupConfig(Main.getInstance());
        UIComponents = new ArrayList<>();
        ConfigurationSection cs  = main.getConfig().getConfigurationSection("ui-buttons");
        for (String pathName : cs.getKeys(false)) {
            if (cs.getBoolean(pathName + ".enable")) {
                String name = "ui-component." + pathName;
                PacketUiComponent uiComponent = new PacketUiComponent(cs.getString(pathName + ".text"), pathName, cs.getString(pathName + ".hover-text"), cs.getString(pathName + ".command"));
                UIComponents.add(uiComponent);
            }
        }
        main.getLogger().info("Config loaded !");
    }

    public static ConfigManager getInstance() {
        return instance;
    }

    public ArrayList<String> getJoinWithAZCommands() {
        return joinWithAZCommands;
    }

    public ArrayList<String> getJoinWithoutAZCommands() {
        return joinWithoutAZCommands;
    }

    public ArrayList<String> getSpecialInventoryCharacters() {
        return specialInventoryCharacters;
    }

    public int getChatMaxMessageSize() {
        return chatMaxMessageSize;
    }

    public int getMaxBuildHeight() {
        return maxBuildHeight;
    }

    public boolean isUpdateMessage() {
        return updateMessage;
    }

    public boolean isLargeHitBox() {
        return largeHitBox;
    }

    public boolean isAttackCooldown() {
        return attackCooldown;
    }

    public boolean isHitAndBlock() {
        return hitAndBlock;
    }

    public boolean isOldEnchantments() {
        return oldEnchantments;
    }

    public boolean isPlayerPush() {
        return playerPush;
    }

    public boolean isPvpHitPriority() {
        return pvpHitPriority;
    }

    public boolean isSeeChunks() {
        return seeChunks;
    }

    public boolean isSwordBlocking() {
        return swordBlocking;
    }

    public boolean isHitIndicator() {
        return hitIndicator;
    }

    public boolean isSidebarScore() {
        return sidebarScore;
    }

    public boolean isPistonRetractEntities() {
        return pistonRetractEntities;
    }

    public boolean isSmoothExperienceBar() {
        return smoothExperienceBar;
    }

    public boolean isServerSideAnvil() {
        return serverSideAnvil;
    }

    public boolean isSortTabListByName() {
        return sortTabListByName;
    }

    public void setPistonRetractEnTities(boolean pistonRetractEnTities) {
        this.pistonRetractEntities = pistonRetractEntities;
    }

    public void setSortTabListByName(boolean sortTabListByName) {
        this.sortTabListByName = sortTabListByName;
    }

    public ArrayList<PacketUiComponent> getUIComponents() {
        return UIComponents;
    }
}
