package fr.mathip.azplugin.bukkit.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.module.cosmetic.CosmeticMenu;
import fr.mathip.azplugin.bukkit.module.cosmetic.CosmeticModule;

public class AZCosmeticCommand implements AZCommand {

    @Override
    public String name() {
        return "cosmetic";
    }

    @Override
    public String permission() {
        return "azplugin.command.cosmetic";
    }

    @Override
    public String description() {
        return "Commande utilitaire sur les cosmetiques";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        CosmeticModule module = Main.getInstance().getModuleManager().getCosmeticModule();
        if (module == null || !module.isEnabled()) {
            sender.sendMessage("§cLe module cosmetic-equipment n'est pas activé !");
            sender.sendMessage("§cVous pouvez l'activer dans le fichier de configuration");
            return;
        }

        if (args.length == 1) {
            sender.sendMessage("§a[AZCosmetic]§e Liste des commandes:");
            sender.sendMessage("§a /az cosmetic menu :§e Ouvre le menu des cosmetiques");
            return;
        }

        if (args[1].equalsIgnoreCase("menu")) {

            if (!sender.hasPermission("azplugin.command.cosmetic.menu")) {
                sender.sendMessage("§cVous n'avez pas la permission d'utiliser cette commande !");
                return;
            }

            if (!(sender instanceof Player)) {
                sender.sendMessage("§cCette commande est réservée aux joueurs !");
                return;
            }

            Player player = (Player) sender;

            CosmeticMenu menu = module.getMenu();
            if (menu == null) {
                player.sendMessage("§cErreur lors de l'ouverture du menu cosmétique !");
                return;
            }
            menu.open(player);
        } else {
            sender.sendMessage("§cSous commande inconnue !");
        }
    }
}
