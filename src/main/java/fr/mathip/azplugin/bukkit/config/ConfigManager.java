package fr.mathip.azplugin.bukkit.config;

import fr.mathip.azplugin.bukkit.AZManager;
import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.PacketUiComponent;
import fr.mathip.azplugin.bukkit.utils.AZChatComponent;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import pactify.client.api.plsp.packet.client.PLSPPacketUiComponent;

import java.util.ArrayList;

@Getter
public class ConfigManager {
    private FileConfiguration config;

    @Getter
    private static ConfigManager instance;

    private ConfFlags conFlags;

    private ArrayList<String> joinWithAZCommands;

    private ArrayList<String> joinWithoutAZCommands;

    private ArrayList<String> specialInventoryCharacters;

    private ArrayList<PacketUiComponent> UIComponents;

    private PopupConfig popupConfig;

    private boolean updateMessage;

    public ConfigManager(Main main) {
        this.config = main.getConfig();
        instance = this;
        joinWithAZCommands = new ArrayList<>();
        joinWithoutAZCommands = new ArrayList<>();
        specialInventoryCharacters = new ArrayList<>();
        UIComponents = new ArrayList<>();
        loadConfig();
    }

    public void loadConfig() {
        Main.getInstance().reloadConfig();
        config = Main.getInstance().getConfig();
        joinWithAZCommands = (ArrayList<String>) config.get("join-with-az-commands");
        joinWithoutAZCommands = (ArrayList<String>) config.get("join-without-az-commands");
        conFlags = new ConfFlags();
        conFlags.init(config);

        updateMessage = config.getBoolean("update-message");

        specialInventoryCharacters = (ArrayList<String>) config.get("special-transparent-inventory-character");

        popupConfig = new PopupConfig(Main.getInstance());

        UIComponents = new ArrayList<>();
        ConfigurationSection uiSection  = config.getConfigurationSection("ui-buttons");
        for (String pathName : uiSection.getKeys(false)) {
            if (uiSection.getBoolean(pathName + ".enable")) {
                ConfigurationSection uiComponentSection = uiSection.getConfigurationSection(pathName);
                PacketUiComponent uiComponent = new PacketUiComponent(uiComponentSection.getString("text"), pathName, uiComponentSection.getString("hover-text"), uiComponentSection.getString("command"));
                UIComponents.add(uiComponent);
            }
        }
        Main.getInstance().getLogger().info("Config loaded !");
    }

    public void applyUIComponents(Player player) {
        for (PacketUiComponent uiComponent : UIComponents) {
            AZChatComponent azChatComponent = new AZChatComponent(uiComponent.getText());
            if (!uiComponent.getHoverText().isEmpty()) {
                azChatComponent.setHoverEvent(new AZChatComponent.HoverEvent("show_text", uiComponent.getHoverText().replaceAll("%player%", player.getName())));
            }
            if (!uiComponent.getCommmand().isEmpty()) {
                azChatComponent.setClickEvent(new AZChatComponent.ClickEvent("run_command", uiComponent.getCommmand().replaceAll("%player%", player.getName())));
            }
            PLSPPacketUiComponent packetUiComponent = new PLSPPacketUiComponent(uiComponent.getName(), azChatComponent);
            AZManager.sendPLSPMessage(player, packetUiComponent);
        }
    }

}