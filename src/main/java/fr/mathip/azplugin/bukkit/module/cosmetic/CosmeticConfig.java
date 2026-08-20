package fr.mathip.azplugin.bukkit.module.cosmetic;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.entity.appearance.AZCosmeticEquipment.Slot;
import fr.mathip.azplugin.bukkit.entity.appearance.AZCosmeticEquipment.Symbol;
import lombok.Getter;

public class CosmeticConfig {

    private static final int MENU_SLOT_MAX = 26;

    private final File file;

    @Getter
    private Map<Slot, EquipmentConfig> equipments;

    public CosmeticConfig(Main main) {
        file = new File(main.getDataFolder(), "cosmetic-equipment.yml");
        if (!file.exists()) {
            main.saveResource("cosmetic-equipment.yml", true);
        }
        load();
    }

    public void load() {
        equipments = new HashMap<>();
        Set<Integer> usedMenuSlots = new HashSet<>();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("equipments");
        if (section == null) {
            return;
        }
        for (String slotName : section.getKeys(false)) {
            ConfigurationSection equipment = section.getConfigurationSection(slotName);
            if (!equipment.getBoolean("enable")) {
                continue;
            }

            Slot slot;
            try {
                slot = Slot.valueOf(slotName.toUpperCase().replace("-", "_"));
            } catch (IllegalArgumentException e) {
                Main.getInstance().getLogger()
                        .warning("[CosmeticEquipment] Slot inconnu: " + slotName + ", ignoré.");
                continue;
            }

            EquipmentConfig equipmentConfig = new EquipmentConfig();

            String symbolName = equipment.getString("symbol", "");
            if (!symbolName.isEmpty()) {
                try {
                    Symbol.valueOf(symbolName);
                } catch (IllegalArgumentException e) {
                    Main.getInstance().getLogger()
                            .warning("[CosmeticEquipment] Symbole inconnu: " + symbolName + " pour le slot " + slotName + ", ignoré.");
                    continue;
                }
                equipmentConfig.setSymbol(symbolName);
            }

            int menuSlot = equipment.getInt("menu-slot", -1);
            if (menuSlot < -1 || menuSlot > MENU_SLOT_MAX) {
                Main.getInstance().getLogger()
                        .warning("[CosmeticEquipment] menu-slot invalide: " + menuSlot + " pour le slot " + slotName + " (doit être entre 0 et " + MENU_SLOT_MAX + "), ignoré.");
                continue;
            }
            if (menuSlot >= 0) {
                if (!usedMenuSlots.add(menuSlot)) {
                    Main.getInstance().getLogger()
                            .warning("[CosmeticEquipment] menu-slot " + menuSlot + " déjà utilisé pour le slot " + slotName + ", ignoré.");
                    continue;
                }
            }
            equipmentConfig.setMenuSlot(menuSlot);

            ConfigurationSection tooltipPrefix = equipment.getConfigurationSection("tooltip-prefix");
            if (tooltipPrefix != null) {
                equipmentConfig.setTooltipPrefixText(tooltipPrefix.getString("text", null));
                equipmentConfig.setTooltipPrefixCommand(tooltipPrefix.getString("command", null));
            }

            ConfigurationSection tooltipSuffix = equipment.getConfigurationSection("tooltip-suffix");
            if (tooltipSuffix != null) {
                equipmentConfig.setTooltipSuffixText(tooltipSuffix.getString("text", null));
                equipmentConfig.setTooltipSuffixCommand(tooltipSuffix.getString("command", null));
            }

            equipments.put(slot, equipmentConfig);
        }
    }
}
