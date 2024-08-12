package fr.mathip.azplugin.bukkit.commands;

import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.AZPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pactify.client.api.plprotocol.metadata.ImmutablePactifyTagMetadata;
import pactify.client.api.plprotocol.metadata.PactifyTagMetadata;

public class AZTag implements AZCommand{
    @Override
    public String name() {
        return "tag";
    }

    @Override
    public String permission() {
        return "azplugin.command.tag";
    }

    @Override
    public String description() {
        return "Change le pseudo d'un joueur";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player target;
        if (args.length >= 3) {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("§cCe joueur est hors-ligne !");
                return;
            }
        } else {
            sender.sendMessage("§cUsage: /az tag <joueur> <tag>");
            return;
        }
        AZPlayer azPlayer = Main.getAZManager().getPlayer(target);
        if (args[2].equalsIgnoreCase("reset")) {
            azPlayer.getPlayerMeta().setTag(new ImmutablePactifyTagMetadata(""));
            azPlayer.updateMeta();
            sender.sendMessage("§a[AZPlugin]§e changement de tag effectué !");
            return;
        }
        StringBuilder sb = new StringBuilder();
        int count = 0;
        sb.append(args[2]);
        for (String arg : args) {
            if (count > 2) {
                sb.append(" ").append(arg);
            }
            count++;
        }
        PactifyTagMetadata tagMetadata = new PactifyTagMetadata();
        tagMetadata.setText(sb.toString());
        azPlayer.getPlayerMeta().setTag(tagMetadata);
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> azPlayer.updateMeta(), 1);
        sender.sendMessage("§a[AZPlugin]§e changement de tag effectué !");
    }
}
