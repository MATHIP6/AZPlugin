package fr.mathip.azplugin.bukkit.module.cosmetic;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.entity.appearance.AZCosmeticEquipment.Slot;

public class CosmeticDataStorage {

    private final File folder;

    public CosmeticDataStorage(Main main) {
        folder = new File(main.getDataFolder(), "data/cosmetic");
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public Map<Slot, ItemStack> load(Player player) {
        Map<Slot, ItemStack> items = new HashMap<>();
        File file = getFile(player.getUniqueId());
        if (!file.exists()) {
            return items;
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (Slot slot : Slot.values()) {
            String key = slot.name();
            if (config.contains(key)) {
                items.put(slot, config.getItemStack(key));
            }
        }
        return items;
    }

    public void save(Player player, Map<Slot, ItemStack> items) {
        File file = getFile(player.getUniqueId());
        YamlConfiguration config = new YamlConfiguration();
        for (Map.Entry<Slot, ItemStack> entry : items.entrySet()) {
            config.set(entry.getKey().name(), entry.getValue());
        }
        try {
            config.save(file);
        } catch (IOException e) {
            Main.getInstance().getLogger()
                    .warning("[CosmeticData] Failed to save cosmetic data for " + player.getName() + ": " + e.getMessage());
        }
    }

    private File getFile(UUID uuid) {
        return new File(folder, uuid.toString() + ".yml");
    }
}
