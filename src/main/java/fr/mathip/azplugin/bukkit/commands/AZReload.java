package fr.mathip.azplugin.bukkit.commands;

import fr.mathip.azplugin.bukkit.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        ConfigManager.getInstance().loadConfig();
        for (Player player : Bukkit.getOnlinePlayers()) {
            ConfigManager.getInstance().getConFlags().applyFlags(player);
            ConfigManager.getInstance().applyUIComponents(player);
        }
        sender.sendMessage("§a[AZPlugin]§e Reload terminé !");
    }
}
