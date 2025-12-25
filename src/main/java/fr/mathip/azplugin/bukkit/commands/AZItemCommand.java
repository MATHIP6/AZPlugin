package fr.mathip.azplugin.bukkit.commands;

import fr.mathip.azplugin.bukkit.commands.items.ItemCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.tr7zw.changeme.nbtapi.NBTItem;

import java.util.Map;

public class AZItemCommand implements AZCommand {

    @Override
    public String name() {
        return "item";
    }

    @Override
    public String permission() {
        return "azplugin.command.item";
    }

    @Override
    public String description() {
        return "Modifie un item";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player;
        if (sender instanceof Player) {
            player = (Player) sender;
        } else {
            sender.sendMessage("§cVous devez être un joueur pour exécuter cette commande");
            return;
        }
        if (args.length == 1) {
            sender.sendMessage("§a[AZPlugin]§e Liste des commandes d'items:");
            for (ItemCommand itemCommand : CommandManager.getInstance().getItemCommands().values()) {
                sender.sendMessage("§a /az item " + itemCommand.name() + " :§e " + itemCommand.description());
            }
            return;
        }
        for (Map.Entry<Class<? extends ItemCommand>, ItemCommand> itemCommand : CommandManager.getInstance()
                .getItemCommands().entrySet()) {
            if (args[1].equalsIgnoreCase(itemCommand.getValue().name())) {
                if (!sender.hasPermission(itemCommand.getValue().permission())) {
                    sender.sendMessage("§cVous n'avez pas la permission d'utiliser cette commande !");
                    return;
                }
                if (player.getItemInHand() == null) {
                    player.sendMessage("§cErreur: Vous devez porter un item !");
                    return;
                }
                NBTItem nbtItem = new NBTItem(player.getItemInHand());
                itemCommand.getValue().execute(player, nbtItem, args);
                player.sendMessage("§a[AZPlugin]§e Item modifié avec succes !");
                return;
            }
        }
        player.sendMessage("§cSous command inconnue !!!");
    }
}
