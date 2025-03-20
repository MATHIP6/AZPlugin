package fr.mathip.azplugin.bukkit.config;

import fr.mathip.azplugin.bukkit.handlers.PLSPConfFlag;
import fr.mathip.azplugin.bukkit.handlers.PLSPConfInt;
import fr.mathip.azplugin.bukkit.packets.PacketConf;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@NoArgsConstructor
@Getter
public class ConfFlags {
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

    public void init(FileConfiguration config) {
        attackCooldown = config.getBoolean("attack_cooldown");
        playerPush = config.getBoolean("player_push");
        largeHitBox = config.getBoolean("large_hitbox");
        swordBlocking = config.getBoolean("sword_blocking");
        hitAndBlock = config.getBoolean("hit_and_block");
        oldEnchantments = config.getBoolean("old_enchantments");
        pvpHitPriority = config.getBoolean("pvp_hit_priority");
        seeChunks = config.getBoolean("see_chunks");
        sidebarScore = config.getBoolean("sidebar_scores");
        smoothExperienceBar = config.getBoolean("smooth_experience_bar");
        sortTabListByName = config.getBoolean("sort_tab_list_by_names");
        serverSideAnvil = config.getBoolean("server_side_anvil");
        pistonRetractEntities = config.getBoolean("pistons_retract_entities");
        hitIndicator = config.getBoolean("hit_indicator");

        chatMaxMessageSize = config.getInt("chat_message_max_size");
        maxBuildHeight = config.getInt("max_build_height");
    }

    public void applyFlags(Player player) {
        PacketConf.setFlag(player, PLSPConfFlag.ATTACK_COOLDOWN, attackCooldown);
        PacketConf.setFlag(player, PLSPConfFlag.PLAYER_PUSH, playerPush);
        PacketConf.setFlag(player, PLSPConfFlag.LARGE_HITBOX, largeHitBox);
        PacketConf.setFlag(player, PLSPConfFlag.SWORD_BLOCKING, swordBlocking);
        PacketConf.setFlag(player, PLSPConfFlag.HIT_AND_BLOCK, hitAndBlock);
        PacketConf.setFlag(player, PLSPConfFlag.OLD_ENCHANTEMENTS, oldEnchantments);
        PacketConf.setFlag(player, PLSPConfFlag.PVP_HIT_PRIORITY, pvpHitPriority);
        PacketConf.setFlag(player, PLSPConfFlag.SEE_CHUNKS, seeChunks);
        PacketConf.setFlag(player, PLSPConfFlag.SIDEBAR_SCORES, sidebarScore);
        PacketConf.setFlag(player, PLSPConfFlag.SMOOTH_EXPERIENCE_BAR, smoothExperienceBar);
        PacketConf.setFlag(player, PLSPConfFlag.SORT_TAB_LIST_BY_NAMES, sortTabListByName);
        PacketConf.setFlag(player, PLSPConfFlag.SERVER_SIDE_ANVIL, serverSideAnvil);
        PacketConf.setFlag(player, PLSPConfFlag.PISTONS_RETRACT_ENTITIES, pistonRetractEntities);
        PacketConf.setFlag(player, PLSPConfFlag.HIT_INDICATOR, hitIndicator);
        
        PacketConf.setInt(player, PLSPConfInt.CHAT_MESSAGE_MAX_SIZE, chatMaxMessageSize);
        PacketConf.setInt(player, PLSPConfInt.MAX_BUILD_HEIGHT, maxBuildHeight);
    }
}
