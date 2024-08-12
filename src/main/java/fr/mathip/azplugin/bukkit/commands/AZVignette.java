package fr.mathip.azplugin.bukkit.commands;

import fr.mathip.azplugin.bukkit.AZManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pactify.client.api.plsp.packet.client.PLSPPacketVignette;

public class AZVignette implements AZCommand{
    @Override
    public String name() {
        return "vignette";
    }

    @Override
    public String permission() {
        return "azplugin.command.vignette";
    }

    @Override
    public String description() {
        return "Change l'environnement d'un joueur";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player target;

        if (args.length >= 5) {
            target = Bukkit.getPlayer(args[4]);
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

        if (args.length >= 4) {
            try {
                int red = Integer.parseInt(args[2]);
                int green = Integer.parseInt(args[3]);
                int blue = Integer.parseInt(args[4]);
                PLSPPacketVignette packetVignette = new PLSPPacketVignette(true, red, green, blue);
                AZManager.sendPLSPMessage(target, packetVignette);
                sender.sendMessage("§a[AZPlugin]§e Changement d'environnement effectué");
            } catch (NumberFormatException e) {
                sender.sendMessage("§cErreur: Les valeurs sont invalides ");
            }

        } else if (args.length >= 3 && args[2].equalsIgnoreCase("reset")) {
            PLSPPacketVignette packetVignette = new PLSPPacketVignette();
            packetVignette.setEnabled(false);
            AZManager.sendPLSPMessage(target, packetVignette);
        } else {
            sender.sendMessage("§cUsage: /az vignette <R> <G> <B> [joueur]");
            sender.sendMessage("§aVous pouvez utiliser ce site pour faire des couleurs RGB https://htmlcolorcodes.com/fr/");
            return;
        }


    }
}
