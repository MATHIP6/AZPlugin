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
        return "Ouvre le menu des cosmetics";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cCette commande est réservée aux joueurs !");
            return;
        }

        Player player = (Player) sender;
        CosmeticModule module = Main.getInstance().getModuleManager().getCosmeticModule();
        if (module == null || !module.isEnabled()) {
            player.sendMessage("§cLe module cosmetic-equipment n'est pas activé !");
            return;
        }

        CosmeticMenu menu = module.getMenu();
        if (menu == null) {
            player.sendMessage("§cErreur lors de l'ouverture du menu cosmétique !");
            return;
        }

        menu.open(player);
    }
}
