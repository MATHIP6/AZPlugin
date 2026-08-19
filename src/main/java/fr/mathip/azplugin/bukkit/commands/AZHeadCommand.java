package fr.mathip.azplugin.bukkit.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.head.AZHeadManager;
import fr.mathip.azplugin.bukkit.head.HeadMenu;

public class AZHeadCommand implements AZCommand {

    @Override
    public String name() {
        return "head";
    }

    @Override
    public String permission() {
        return "azplugin.command.head";
    }

    @Override
    public String description() {
        return "Commande utilitaire sur les têtes custom";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cErreur: Vous devez être un joueur pour executer cette commande");
            return;
        }
        Player player = ((Player) sender);
        if (args.length == 1) {
            sender.sendMessage("§a[AZHead]§e Liste des commandes:");
            sender.sendMessage("§a /az head transparent :§e Rend transparent les pixels d'une tête");
            sender.sendMessage("§a /az head menu :§e Ouvre le menu de têtes transparentes");
            return;
        }
        if (args[1].equalsIgnoreCase("transparent")) {
            ItemStack item = player.getItemInHand();
            if (item == null) {
                player.sendMessage("§cErreur: Vous devez tenir une tête");
                return;
            }
            ItemStack head;
            try {
                head = AZHeadManager.convertHead(item);
                player.setItemInHand(head);
                player.sendMessage("§a[AZPlugin]§e Item modifié !");
                return;
            } catch (Exception e) {
                if (e.getMessage().equals("Head not found")) {
                    player.sendMessage("§cErreur: L'item que vous avez n'est pas une tête");
                    return;
                } else {
                    e.printStackTrace();
                }
            }
        } else if (args[1].equalsIgnoreCase("menu")) {
            HeadMenu headMenu = Main.getInstance().getHeadMenu();
            if (headMenu == null) {
                player.sendMessage("§cErreur: Le menu de têtes n'est pas disponible");
                return;
            }
            headMenu.open(player);
        }
    }

}
