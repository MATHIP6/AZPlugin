package fr.mathip.azplugin.bukkit.commands;

import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.entity.AZPlayer;
import fr.mathip.azplugin.bukkit.entity.appearance.AZEntityModel;
import fr.mathip.azplugin.bukkit.handlers.PLSPPlayerModel;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AZModel implements AZCommand {
    @Override
    public String name() {
        return "model";
    }

    @Override
    public String permission() {
        return "azplugin.command.model";
    }

    @Override
    public String description() {
        return "Change l'apparence du joueur (en mob par exemple)";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§c/az model <model> [joueur] [nbt]");
            return;
        }
        Player target;
        if (args.length >= 3) {
            target = Bukkit.getPlayer(args[2]);
            if (target == null) {
                sender.sendMessage("§cCe joueur est hors-ligne !");
                return;
            }
        } else {
            target = (Player) sender;
            if (target == null) {
                sender.sendMessage("§cErreur: Vous devez être un joueur pour executer cette commande");
                return;
            }
        }
        AZPlayer azPlayer = Main.getAZManager().getPlayer(target);
        if (args[1].equalsIgnoreCase("reset")) {
            azPlayer.setModel(new AZEntityModel());
            azPlayer.flush();
            sender.sendMessage("§achangement de skin effectué !");
            return;
        }
        try {
            PLSPPlayerModel plspPlayerModel = PLSPPlayerModel.valueOf(args[1].toUpperCase());
            azPlayer.setModel(new AZEntityModel(plspPlayerModel));
            azPlayer.flush();
            sender.sendMessage("§a[AZPlugin]§e changement de skin effectué !");
        } catch (IllegalArgumentException e) {
            sender.sendMessage("§cErreur : La valeur est invalide !.");
        }
    }
}
