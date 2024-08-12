package fr.mathip.azplugin.bukkit.commands;

import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.AZPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AZOpacity implements AZCommand{
    @Override
    public String name() {
        return "opacity";
    }

    @Override
    public String permission() {
        return "azplugin.command.opacity";
    }

    @Override
    public String description() {
        return "Change l'opacitée du joueur";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player target;
        float opacity;
        try {
            opacity = Float.parseFloat(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cErreur : L'opacité n'est pas un nombre valide.");
            return;
        }

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
        if (opacity > 1 || opacity < -1) {
            sender.sendMessage("§cErreur: L'opacité droit être entre 1 et -1");
            return;
        }
        azPlayer.getPlayerMeta().setOpacity(opacity);
        azPlayer.updateMeta();
        sender.sendMessage("§a[AZPlugin]§e changement de d'opacité effectué !");
    }
}
