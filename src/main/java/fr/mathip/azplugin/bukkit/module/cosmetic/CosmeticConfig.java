package fr.mathip.azplugin.bukkit.module.cosmetic;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.entity.appearance.AZCosmeticEquipment.Slot;
import lombok.Getter;

public class CosmeticConfig {

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
                        .warning("[CosmeticEquipment] Unknown slot: " + slotName + ", skipping.");
                continue;
            }

            EquipmentConfig config2 = new EquipmentConfig();
            config2.setSymbol(equipment.getString("symbol", ""));

            ConfigurationSection tooltipPrefix = equipment.getConfigurationSection("tooltip-prefix");
            if (tooltipPrefix != null) {
                config2.setTooltipPrefixText(tooltipPrefix.getString("text", null));
                config2.setTooltipPrefixCommand(tooltipPrefix.getString("command", null));
            }

            ConfigurationSection tooltipSuffix = equipment.getConfigurationSection("tooltip-suffix");
            if (tooltipSuffix != null) {
                config2.setTooltipSuffixText(tooltipSuffix.getString("text", null));
                config2.setTooltipSuffixCommand(tooltipSuffix.getString("command", null));
            }

            equipments.put(slot, config2);
        }
    }
}
