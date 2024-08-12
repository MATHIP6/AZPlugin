package fr.mathip.azplugin.bukkit.commands;

import fr.mathip.azplugin.bukkit.PopupConfig;
import fr.mathip.azplugin.bukkit.packets.PacketPopup;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AZPopup implements AZCommand{
    @Override
    public String name() {
        return "popup";
    }

    @Override
    public String permission() {
        return "azplugin.command.popup";
    }

    @Override
    public String description() {
        return "Envoie un Popup à un joueur";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("§cUsage: /az popup <popup> <joueur>");
            return;
        }
        Player target = Bukkit.getPlayer(args[2]);
        if (target == null) {
            sender.sendMessage("§cCe joueur est hors-ligne !");
            return;
        }
        PacketPopup popup = PopupConfig.getInstance().getPopupByName(args[1]);
        if (popup != null) {
            popup.send(target);
            sender.sendMessage("§a[AZPlugin]§e popup envoyé ");
        } else {
            sender.sendMessage("§cErreur: Popup introuvable !");
        }
    }
}
