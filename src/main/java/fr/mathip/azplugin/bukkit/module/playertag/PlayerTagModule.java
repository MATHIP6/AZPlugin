package fr.mathip.azplugin.bukkit.module.playertag;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.mathip.azplugin.bukkit.entity.AZPlayer;
import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.module.Module;
import pactify.client.api.plprotocol.metadata.PactifyTagMetadata;
import pactify.client.api.plprotocol.metadata.PactifyTagMetadata.Rarity;

public class PlayerTagModule implements Module, Listener {

    private boolean enable;
    private String CONFIG = "player-tag";

    public PlayerTagModule() {
        enable = false;
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    private void onJoint(PlayerJoinEvent event) {
        AZPlayer azPlayer = Main.getInstance().getAZManager().getPlayer(event.getPlayer());
        handleRarityTag(azPlayer);
    }

    private void handleRarityTag(AZPlayer azPlayer) {
        Player player = azPlayer.getPlayer();
        if (player.hasPermission("azplugin.rarity")) {
            azPlayer.getPlayerMeta().setTag(new PactifyTagMetadata());
        } else {
            return;
        }
        if (player.hasPermission("azplugin.rarity.mythic")) {
            azPlayer.getPlayerMeta().getTag().setRarity(Rarity.MYTHIC);
        } else if (player.hasPermission("azplugin.rarity.legendary")) {
            azPlayer.getPlayerMeta().getTag().setRarity(Rarity.LEGENDARY);
        } else if (player.hasPermission("azplugin.rarity.epic")) {
            azPlayer.getPlayerMeta().getTag().setRarity(Rarity.EPIC);
        } else if (player.hasPermission("azplugin.rarity.rare")) {
            azPlayer.getPlayerMeta().getTag().setRarity(Rarity.RARE);
        } else if (player.hasPermission("azplugin.rarity.uncommon")) {
            azPlayer.getPlayerMeta().getTag().setRarity(Rarity.UNCOMMON);
        }
        Bukkit.getScheduler().runTaskLaterAsynchronously(
                Main.getInstance(),
                () -> azPlayer.updateMeta(),
                10L);
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }

    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String getConfigSection() {
        return CONFIG;
    }
}
