package fr.mathip.azplugin.bukkit.config;

import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.handlers.PopupType;
import fr.mathip.azplugin.bukkit.packets.PacketPopup;
import fr.mathip.azplugin.bukkit.utils.AZChatComponent;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class PopupConfig {
    private final File file;

    @Getter public List<PacketPopup> popups;


    public PopupConfig(Main main) {
        file = new File(main.getDataFolder(), "popups.yml");
        if (!file.exists()) {
            main.saveResource("popups.yml", true);
        }
        load();
    }

    public void load() {
        popups = new ArrayList<>();
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        for (String popupPath : yamlConfiguration.getKeys(false)) {
            ConfigurationSection popupConf = yamlConfiguration.getConfigurationSection(popupPath);
            PacketPopup popup = new PacketPopup(popupPath, PopupType.valueOf(popupConf.getString("type")));
            AZChatComponent textComonent = new AZChatComponent("");
            for (String text : popupConf.getStringList("content")) {
                textComonent.getExtra().add(new AZChatComponent(text + "\n"));
            }
            popup.setTextComponent(textComonent);
            ConfigurationSection okButton = popupConf.getConfigurationSection("ok-button");
            AZChatComponent okComponent = new AZChatComponent("");
            if (!okButton.getString("command").isEmpty()) {
                okComponent.setClickEvent(new AZChatComponent.ClickEvent("run_command", okButton.getString("command")));
            }
            popup.setOkComponent(okComponent);
            if (popupConf.getConfigurationSection("cancel-button") != null) {
                ConfigurationSection cancelButton = popupConf.getConfigurationSection("cancel-button");
                AZChatComponent cancelComponent = new AZChatComponent("");
                if (!cancelButton.getString("command").isEmpty()) {
                    cancelComponent.setClickEvent(new AZChatComponent.ClickEvent("run_command", cancelButton.getString("command")));
                }
                popup.setCancelComponent(cancelComponent);
            }
            if (popupConf.contains("default-value")) {
                popup.setDefaultValue(popupConf.getString("default-value"));
            }
            if (popupConf.contains("is-password")) {
                popup.setPassword(popupConf.getBoolean("is-password"));
            }
            popups.add(popup);
        }
    }

    public PacketPopup getPopupByName(String name) {
        for (PacketPopup popup : popups) {
            if (popup.getName().equals(name)) {
                return popup;
            }
        }
        return null;
    }

}
