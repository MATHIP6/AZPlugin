package fr.mathip.azplugin.bukkit.commands;

import fr.mathip.azplugin.bukkit.ConfigManager;
import fr.mathip.azplugin.bukkit.PopupConfig;
import org.bukkit.command.CommandSender;

public class AZReload implements AZCommand{
    @Override
    public String name() {
        return "reload";
    }

    @Override
    public String permission() {
        return "azplugin.command.reload";
    }

    @Override
    public String description() {
        return "Reload le plugin";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("§a[AZPlugin]§e Reload en cours...");
        ConfigManager.getInstance().initConfig();
        PopupConfig.getInstance().load();
        sender.sendMessage("§a[AZPlugin]§e Reload terminé !");
    }
}
