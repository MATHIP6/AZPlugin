package fr.mathip.azplugin.bukkit.module.cosmetic;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.entity.AZPlayer;
import fr.mathip.azplugin.bukkit.entity.appearance.AZCosmeticEquipment.Slot;
import fr.mathip.azplugin.bukkit.module.Module;

public class CosmeticModule implements Module, Listener {

    private boolean enable;
    private final String CONFIG = "cosmetic-equipment";
    private CosmeticConfig cosmeticConfig;

    public CosmeticModule() {
        enable = false;
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        AZPlayer azPlayer = Main.getAZManager().getPlayer(event.getPlayer());
        if (azPlayer == null) {
            return;
        }

        Map<Slot, EquipmentConfig> equipments = cosmeticConfig.getEquipments();
        for (Map.Entry<Slot, EquipmentConfig> entry : equipments.entrySet()) {
            azPlayer.setCosmeticEquipment(entry.getKey(), entry.getValue().build(event.getPlayer()));
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
            cosmeticConfig = new CosmeticConfig(Main.getInstance());
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
