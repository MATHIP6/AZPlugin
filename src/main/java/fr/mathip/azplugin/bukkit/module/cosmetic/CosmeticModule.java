package fr.mathip.azplugin.bukkit.module.cosmetic;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.entity.AZPlayer;
import fr.mathip.azplugin.bukkit.entity.appearance.AZCosmeticEquipment.Slot;
import fr.mathip.azplugin.bukkit.module.Module;
import lombok.Getter;

public class CosmeticModule implements Module, Listener {

    private boolean enable;
    private final String CONFIG = "cosmetic-equipment";
    private CosmeticConfig cosmeticConfig;

    @Getter
    private CosmeticDataStorage dataStorage;

    @Getter
    private CosmeticMenu menu;

    public CosmeticModule() {
        enable = false;
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        AZPlayer azPlayer = Main.getAZManager().getPlayer(event.getPlayer());
        if (azPlayer == null) {
            return;
        }

        Player player = event.getPlayer();
        Map<Slot, EquipmentConfig> equipments = cosmeticConfig.getEquipments();

        Map<Slot, ItemStack> savedItems = dataStorage.load(player);

        for (Map.Entry<Slot, EquipmentConfig> entry : equipments.entrySet()) {
            if (savedItems.containsKey(entry.getKey())) {
                ItemStack item = savedItems.get(entry.getKey());
                azPlayer.setCosmeticEquipment(entry.getKey(), entry.getValue().build(player, item));
            } else {
                azPlayer.setCosmeticEquipment(entry.getKey(), entry.getValue().build(player));
            }
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
            dataStorage = new CosmeticDataStorage(Main.getInstance());
            menu = new CosmeticMenu(this, dataStorage, cosmeticConfig.getEquipments());
            Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
            Bukkit.getPluginManager().registerEvents(menu, Main.getInstance());
        } else {
            HandlerList.unregisterAll(this);
            if (menu != null) {
                HandlerList.unregisterAll(menu);
            }
        }
    }

    @Override
    public String getConfigSection() {
        return CONFIG;
    }
}
