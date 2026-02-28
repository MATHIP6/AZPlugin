package fr.mathip.azplugin.bukkit.module.playertag;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.mathip.azplugin.bukkit.entity.AZPlayer;
import fr.mathip.azplugin.bukkit.entity.appearance.AZEntityTag;
import fr.mathip.azplugin.bukkit.entity.appearance.AZEntityTag.Rarity;
import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.module.Module;

public class PlayerTagModule implements Module, Listener {

    private boolean enable;
    private String CONFIG = "player-tag";

    public PlayerTagModule() {
        enable = false;
    }

    @EventHandler
    private void onJoint(PlayerJoinEvent event) {
        AZPlayer azPlayer = Main.getInstance().getAZManager().getPlayer(event.getPlayer());
        handleRarityTag(azPlayer);
    }

    private void handleRarityTag(AZPlayer azPlayer) {
        Player player = azPlayer.getPlayer();
        if (player.hasPermission("azplugin.rarity")) {
        } else {
            return;
        }
        if (player.hasPermission("azplugin.rarity.mythic")) {
            azPlayer.setTag(AZEntityTag.builder().rarity(Rarity.MYTHIC).build());
        } else if (player.hasPermission("azplugin.rarity.legendary")) {
            azPlayer.setTag(AZEntityTag.builder().rarity(Rarity.LEGENDARY).build());
        } else if (player.hasPermission("azplugin.rarity.epic")) {
            azPlayer.setTag(AZEntityTag.builder().rarity(Rarity.EPIC).build());
        } else if (player.hasPermission("azplugin.rarity.rare")) {
            azPlayer.setTag(AZEntityTag.builder().rarity(Rarity.RARE).build());
        } else if (player.hasPermission("azplugin.rarity.uncommon")) {
            azPlayer.setTag(AZEntityTag.builder().rarity(Rarity.UNCOMMON).build());
        }
        Bukkit.getScheduler().runTaskLaterAsynchronously(
                Main.getInstance(),
                () -> azPlayer.flush(),
                10L);
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }

    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
        if (enable) {
            Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
        } else {
            HandlerList.unregisterAll(this);
        }
    }

    @Override
    public String getConfigSection() {
        return CONFIG;
    }
}
