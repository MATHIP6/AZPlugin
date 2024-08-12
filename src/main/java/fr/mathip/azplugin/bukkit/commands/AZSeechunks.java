package fr.mathip.azplugin.bukkit.commands;

import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.handlers.PLSPConfFlag;
import fr.mathip.azplugin.bukkit.packets.PacketConf;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AZSeechunks implements AZCommand{
    @Override
    public String name() {
        return "seechunks";
    }

    @Override
    public String permission() {
        return "azplugin.comand.seechunks";
    }

    @Override
    public String description() {
        return "Active ou non la vision des chunks à un joueur";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 2) {
            if (Bukkit.getPlayer(args[1]) != null) {
                Player target = Bukkit.getPlayer(args[1]);
                if (args.length >= 3) {
                    if (args[2].equalsIgnoreCase("on")) {
                        PacketConf.setFlag(target, PLSPConfFlag.SEE_CHUNKS, true);
                    } else if (args[2].equalsIgnoreCase("off")) {
                        PacketConf.setFlag(target, PLSPConfFlag.SEE_CHUNKS, false);
                    } else {
                        sender.sendMessage("§c/az seechunks <joueur> [on/off]");
                    }
                    return;
                }
                if (Main.getInstance().playersSeeChunks.contains(target)) {
                    PacketConf.setFlag(target, PLSPConfFlag.SEE_CHUNKS, false);
                    Main.getInstance().playersSeeChunks.remove(target);
                } else {
                    PacketConf.setFlag(target, PLSPConfFlag.SEE_CHUNKS, true);
                    Main.getInstance().playersSeeChunks.add(target);
                }

            } else {
                sender.sendMessage("§cErreur: Ce joueur est hors-ligne !");
            }
        } else {
            sender.sendMessage("§c/az seechunks <joueur> [on/off]");
        }
    }
}
