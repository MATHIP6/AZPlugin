package fr.mathip.azplugin.bukkit.commands;

import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.entity.AZPlayer;
import fr.mathip.azplugin.bukkit.entity.appearance.AZEntityScale;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AZSize implements AZCommand {
    @Override
    public String name() {
        return "size";
    }

    @Override
    public String permission() {
        return "azplugin.command.size";
    }

    @Override
    public String description() {
        return "Change la taille d'un joueur";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§c/az size <taille> [joueur]");
            return;
        }
        Player target;
        Float size;
        try {
            size = Float.parseFloat(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cErreur : La taille n'est pas un nombre valide.");
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

        azPlayer.setScale(new AZEntityScale(size));
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> azPlayer.flush(), 1);
        sender.sendMessage("§a[AZPlugin]§e changement de taille effectué !");
    }
}
