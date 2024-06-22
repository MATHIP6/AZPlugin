package fr.mathip.azplugin.commands;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import fr.mathip.azplugin.Main;
import fr.speccy.azclientapi.bukkit.AZPlayer;
import fr.speccy.azclientapi.bukkit.handlers.PLSPConfFlag;
import fr.speccy.azclientapi.bukkit.handlers.PLSPConfInt;
import fr.speccy.azclientapi.bukkit.handlers.PLSPPlayerModel;
import fr.speccy.azclientapi.bukkit.handlers.PLSPWorldEnv;
import fr.speccy.azclientapi.bukkit.packets.player.*;
import fr.speccy.azclientapi.bukkit.utils.AZColor;
import fr.thebatteur.items.AZItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pactify.client.api.plsp.packet.client.PLSPPacketPlayerMeta;

import java.util.ArrayList;
import java.util.List;

public class AzCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (s.equalsIgnoreCase("az")){

            Main main = Main.getInstance();

            if (args.length == 0){
                commandSender.sendMessage("§ccheck, list, size, model, opacity, worldenv, vignette, tag, itemrender, reload, seechunks, subtag, suptag");
            } else if (args[0].equalsIgnoreCase("list")) {
                if (!commandSender.hasPermission("azplugin.command.list")) {
                    commandSender.sendMessage("§cErreur: Vous n'avez pas la permission d'utilisez cette commande !");
                    return true;
                }
                List<String> pactifyList = new ArrayList<>();
                List<String> vanillaList = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (AZPlayer.hasAZLauncher(player)) {
                        pactifyList.add(player.getName());
                        continue;
                    }
                    vanillaList.add(player.getName());
                }
                pactifyList.sort(String::compareToIgnoreCase);
                vanillaList.sort(String::compareToIgnoreCase);
                commandSender.sendMessage(ChatColor.YELLOW + "Les joueurs qui utilisent le AZ launcher: " + (
                        pactifyList.isEmpty() ? (ChatColor.GRAY + "(Aucun)") : (ChatColor.GREEN + String.join(", ", (Iterable)pactifyList))));
                commandSender.sendMessage(ChatColor.YELLOW + "Les joueurs qui n'utilisent pas le AZ launcher: " + (
                        vanillaList.isEmpty() ? (ChatColor.GRAY + "(Aucun)") : (ChatColor.RED + String.join(", ", (Iterable)vanillaList))));

            } else if (args[0].equalsIgnoreCase("reload")) {
                if (!commandSender.hasPermission("azplugin.command.reload")) {
                    commandSender.sendMessage("§cErreur: Vous n'avez pas la permission d'utilisez cette commande !");
                    return true;
                }
                Main.getInstance().initConfig();
                for (Player pl : Bukkit.getOnlinePlayers()){
                    if (AZPlayer.hasAZLauncher(pl)){
                        PacketConf.setFlag(pl, PLSPConfFlag.ATTACK_COOLDOWN, main.attackCooldown);
                        PacketConf.setFlag(pl, PLSPConfFlag.PLAYER_PUSH, main.playerPush);
                        PacketConf.setFlag(pl, PLSPConfFlag.LARGE_HITBOX, main.largeHitBox);
                        PacketConf.setFlag(pl, PLSPConfFlag.SWORD_BLOCKING, main.swordBlocking);
                        PacketConf.setFlag(pl, PLSPConfFlag.HIT_AND_BLOCK, main.hitAndBlock);
                        PacketConf.setFlag(pl, PLSPConfFlag.OLD_ENCHANTEMENTS, main.oldEnchantments);
                        PacketConf.setFlag(pl, PLSPConfFlag.SIDEBAR_SCORES, main.sidebarScore);
                        PacketConf.setFlag(pl, PLSPConfFlag.PVP_HIT_PRIORITY, main.pvpHitPriority);
                        PacketConf.setFlag(pl, PLSPConfFlag.SEE_CHUNKS, main.seeChunks);
                        PacketConf.setFlag(pl, PLSPConfFlag.SMOOTH_EXPERIENCE_BAR, main.smoothExperienceBar);
                        PacketConf.setFlag(pl, PLSPConfFlag.SORT_TAB_LIST_BY_NAMES, main.sortTabListByName);

                        PacketConf.setFlag(pl, PLSPConfFlag.SERVER_SIDE_ANVIL, main.serverSideAnvil);
                        PacketConf.setFlag(pl, PLSPConfFlag.PISTONS_RETRACT_ENTITIES, main.pistonRetractEnvities);
                        PacketConf.setFlag(pl, PLSPConfFlag.HIT_INDICATOR, main.hitIndicator);

                        PacketConf.setInt(pl, PLSPConfInt.CHAT_MESSAGE_MAX_SIZE, main.chatMaxMessageSize);
                        PacketConf.setInt(pl, PLSPConfInt.MAX_BUILD_HEIGHT, main.maxBuildHeight);
                    }
                }
                commandSender.sendMessage("§aLe plugin a reload !");

            } else if (args[0].equalsIgnoreCase("check")) {
                if (!commandSender.hasPermission("azplugin.command.checkt")) {
                    commandSender.sendMessage("§cErreur: Vous n'avez pas la permission d'utilisez cette commande !");
                    return true;
                }
                if (args.length == 1) {
                    commandSender.sendMessage(ChatColor.RED + "/az check <joueur>");
                    return true;
                }
                Player player = Bukkit.getPlayer(args[1]);
                if (player == null) {
                    commandSender.sendMessage(ChatColor.RED + "Erreur: Ce joueur est hors ligne");
                    return true;
                }
                commandSender.sendMessage(ChatColor.YELLOW + player.getName() + (
                        AZPlayer.hasAZLauncher(player) ? (ChatColor.GREEN + " utilise") : (ChatColor.RED + " n'utilise pas")) + ChatColor.YELLOW + " le AZ launcher");
            }else if (args[0].equalsIgnoreCase("size")){
                if (!commandSender.hasPermission("azplugin.command.size")) {
                    commandSender.sendMessage("§cErreur: Vous n'avez pas la permission d'utilisez cette commande !");
                    return true;
                }
                if (args.length > 2) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        Player target = Bukkit.getPlayer(args[1]);
                        try {
                            Float size = Float.parseFloat(args[2]);
                            if (size == 1) {
                                if (main.playersScale.containsKey(target)) {
                                    main.playersScale.remove(target);
                                }
                                PacketPlayerScale.reset(target);
                            }
                            if (main.playersScale.containsKey(target)) {
                                main.playersScale.replace(target, size);
                            } else {
                                main.playersScale.put(target, size);
                            }
                            //PacketEntityMeta.setPlayerScale(Bukkit.getPlayer(args[2]), size, size, size, size, size, true);
                            commandSender.sendMessage("§achangement de taille effectué !");
                        } catch (NumberFormatException e) {
                            commandSender.sendMessage("§cErreur : La taille n'est pas un nombre valide.");
                        }
                    } else {
                        commandSender.sendMessage("§cCe joueur est hors-ligne !");
                    }
                } else {
                    commandSender.sendMessage("§c/az size <joueur> <taille>");
                }
            } else if (args[0].equalsIgnoreCase("model")) {
                if (!commandSender.hasPermission("azplugin.command.model")) {
                    commandSender.sendMessage("§cErreur: Vous n'avez pas la permission d'utilisez cette commande !");
                    return true;
                }
                if (args.length > 2){
                    if (Bukkit.getPlayer(args[1]) != null){
                        Player target = Bukkit.getPlayer(args[1]);
                        if (args[2].equalsIgnoreCase("reset")){
                            if (main.playersModel.containsKey(target)){
                                main.playersModel.remove(target);
                                PacketPlayerModel.reset(target);
                            }
                            commandSender.sendMessage("§achangement de skin effectué !");
                            return true;
                        }
                        try {
                            PLSPPlayerModel plspPlayerModel = PLSPPlayerModel.valueOf(args[2].toUpperCase());
                            if (main.playersModel.containsKey(target)){
                                main.playersModel.replace(target, plspPlayerModel);
                            } else {
                                main.playersModel.put(target, plspPlayerModel);
                            }
                            //PacketPlayerModel.setPlayerModel(Bukkit.getPlayer(args[2]), id);
                            commandSender.sendMessage("§achangement de skin effectué !");
                        } catch (IllegalArgumentException e){
                            commandSender.sendMessage("§cErreur : La valeur est invalide !.");
                        }
                    } else {
                        commandSender.sendMessage("§cCe joueur est hors-ligne !");
                    }
                } else {
                    commandSender.sendMessage("§c/az model <joueur> <model> ou reset");
                }
            } else if (args[0].equalsIgnoreCase("opacity")) {
                if (!commandSender.hasPermission("azplugin.command.opacity")) {
                    commandSender.sendMessage("§cErreur: Vous n'avez pas la permission d'utilisez cette commande !");
                    return true;
                }
                if (args.length >= 2){
                    if (Bukkit.getPlayer(args[1]) != null){
                        Player target = Bukkit.getPlayer(args[1]);
                        try {
                            Float opacity = Float.parseFloat(args[2]);
                            if (opacity == -1) {
                                if (main.playersOpacity.containsKey(target)) {
                                    main.playersOpacity.remove(target);
                                }
                                PacketPlayerOpacity.resetPlayer(target);
                                PacketPlayerOpacity.resetNameTag(target);
                                PacketPlayerOpacity.resetSneakNameTag(target);
                            }
                            if (opacity <= 1 && opacity >= -1) {
                                if (main.playersOpacity.containsKey(target)){
                                    main.playersOpacity.replace(target, opacity);
                                } else {
                                    main.playersOpacity.put(target, opacity);
                                }
                                //PacketEntityMeta.setPlayerOpacity(Bukkit.getPlayer(args[2]), opacity);
                                //PacketEntityMeta.setNameTagOpacity(Bukkit.getPlayer(args[2]), opacity);
                                //PacketEntityMeta.setSneakNameTagOpacity(Bukkit.getPlayer(args[2]), opacity);
                                commandSender.sendMessage("§achangement de d'opacité effectué !");
                            } else {
                                commandSender.sendMessage("§cErreur: Vous devez mettre une valeur entre -1 et 1 !");
                            }
                        } catch (NumberFormatException e){
                            commandSender.sendMessage("§cErreur : La valeur est invalide !.");
                        }
                    } else {
                        commandSender.sendMessage("§cCe joueur est hors-ligne !");
                    }
                } else {
                    commandSender.sendMessage("§c/az opacity <joueur> <opacité>");
                }
            } else if (args[0].equalsIgnoreCase("worldenv")) {
                if (!commandSender.hasPermission("azplugin.command.worldenv")) {
                    commandSender.sendMessage("§cErreur: Vous n'avez pas la permission d'utilisez cette commande !");
                    return true;
                }
                if (args.length >= 2){
                    if (args[1].equals("NORMAL") || args[1].equals("NETHER") || args[1].equals("THE_END")){
                        for (Player pl : Bukkit.getOnlinePlayers()) {
                            PacketWorldEnv.setEnv(pl, PLSPWorldEnv.valueOf(args[1]));
                        }
                        commandSender.sendMessage("§achangement de d'environnement effectué !");
                    } else {
                        commandSender.sendMessage("§cErreur : La valeur est invalide !.");
                    }
                } else {
                    commandSender.sendMessage("§c/az worldenv <NORMAL, NETHER, THE_END>");
                }
            } else if (args[0].equalsIgnoreCase("vignette")) {
                if (!commandSender.hasPermission("azplugin.command.vignette")) {
                    commandSender.sendMessage("§cErreur: Vous n'avez pas la permission d'utilisez cette commande !");
                    return true;
                }
                if (args.length >= 3){
                    if (Bukkit.getPlayer(args[1]) != null){
                        Player target = Bukkit.getPlayer(args[1]);
                        if (args[2].equalsIgnoreCase("reset")) {
                            PacketVignette.reset(target);
                            commandSender.sendMessage("§achangement de d'environnement effectué !");
                            return true;
                        }
                        if (args.length >= 5) {
                            try {
                                int red = Integer.parseInt(args[2]);
                                int green = Integer.parseInt(args[3]);
                                int blue = Integer.parseInt(args[4]);
                                PacketVignette.setColor(target, red, green, blue);
                                commandSender.sendMessage("§achangement de d'environnement effectué !");
                            } catch (NumberFormatException e){
                                commandSender.sendMessage("§cErreur : Les valeur est invalide !.");
                            }
                        } else {
                            commandSender.sendMessage("§c/az vignette <joueur> (<red> <green> <blue>) ou reset");
                            commandSender.sendMessage("§aVous pouvez utiliser ce site pour faire des couleurs RGB https://htmlcolorcodes.com/fr/");
                        }
                    } else {
                        commandSender.sendMessage("§cCe joueur est hors-ligne !");
                    }
                } else {
                    commandSender.sendMessage("§c/az vignette <joueur> (<red> <green> <blue>) ou reset");
                    commandSender.sendMessage("§aVous pouvez utiliser ce site pour faire des couleurs RGB https://htmlcolorcodes.com/fr/");
                }
            } else if (args[0].equalsIgnoreCase("tag")) {
                if (!commandSender.hasPermission("azplugin.command.tag")) {
                    commandSender.sendMessage("§cErreur: Vous n'avez pas la permission d'utilisez cette commande !");
                    return true;
                }
                if (args.length > 2){
                    if (Bukkit.getPlayer(args[1]) != null){
                        Player target = Bukkit.getPlayer(args[1]);
                        /*if (args[2].equalsIgnoreCase("reset")) {
                              if (main.playersTag.containsKey(target)) {
                                    main.playersTag.remove(target);
                                    commandSender.sendMessage("§aTag supprimé !");
                                }
                                return true;
                            }*/
                        StringBuilder sb = new StringBuilder();
                        int count = 0;
                        sb.append(args[2]);
                        for (String arg : args) {
                            if (count > 2) {
                                sb.append(" " + arg);
                            }
                            count++;
                        }
                        if (main.playersTag.containsKey(target)){
                            main.playersTag.replace(target, sb.toString());
                        } else {
                            main.playersTag.put(target, sb.toString());
                        }
                        commandSender.sendMessage("§atag modifié !");
                    } else {
                        commandSender.sendMessage("§cCe joueur est hors-ligne !");
                    }
                } else {
                    commandSender.sendMessage("§c/az tag <joueur> <nom>");
                }
            } else if (args[0].equalsIgnoreCase("subtag")) {
                if (!commandSender.hasPermission("azplugin.command.subtag")) {
                    commandSender.sendMessage("§cErreur: Vous n'avez pas la permission d'utilisez cette commande !");
                    return true;
                }
                if (args.length > 2){
                    if (Bukkit.getPlayer(args[1]) != null){
                        Player target = Bukkit.getPlayer(args[1]);
                        /*if (args[2].equalsIgnoreCase("reset")) {
                               if (main.playersSubTag.containsKey(target)) {
                                   main.playersSubTag.remove(target);
                                    commandSender.sendMessage("§aTag supprimé !");
                                }
                                return true;
                            }*/
                        StringBuilder sb = new StringBuilder();
                        int count = 0;
                        sb.append(args[2]);
                        for (String arg : args) {
                            if (count > 2) {
                                sb.append(" " + arg);
                            }
                            count++;
                        }
                        if (main.playersSubTag.containsKey(target)){
                            main.playersSubTag.replace(target, sb.toString());
                        } else {
                            main.playersSubTag.put(target, sb.toString());
                        }
                        commandSender.sendMessage("§atag modifié !");
                    } else {
                        commandSender.sendMessage("§cCe joueur est hors-ligne !");
                    }
                } else {
                    commandSender.sendMessage("§c/az subtag <joueur> <nom>");
                }
            } else if (args[0].equalsIgnoreCase("suptag")) {
                if (!commandSender.hasPermission("azplugin.command.suptag")) {
                    commandSender.sendMessage("§cErreur: Vous n'avez pas la permission d'utilisez cette commande !");
                    return true;
                }
                if (args.length > 2){
                    if (Bukkit.getPlayer(args[1]) != null){
                        Player target = Bukkit.getPlayer(args[1]);
                        /*if (args[2].equalsIgnoreCase("reset")) {
                                if (main.playersSupTag.containsKey(target)) {
                                    main.playersSupTag.remove(target);
                                    commandSender.sendMessage("§aTag supprimé !");
                                }
                                return true;
                            }*/
                        StringBuilder sb = new StringBuilder();
                        int count = 0;
                        sb.append(args[2]);
                        for (String arg : args) {
                            if (count > 2) {
                                sb.append(" " + arg);
                            }
                            count++;
                        }
                        if (main.playersSupTag.containsKey(target)){
                            main.playersSupTag.replace(target, sb.toString());
                        } else {
                            main.playersSupTag.put(target, sb.toString());
                        }
                        commandSender.sendMessage("§atag modifié !");
                    } else {
                        commandSender.sendMessage("§cCe joueur est hors-ligne !");
                    }
                } else {
                    commandSender.sendMessage("§c/az suptag <joueur> args");
                }
            } else if (args[0].equalsIgnoreCase("seechunks")) {
                if (!commandSender.hasPermission("azplugin.command.seechunks")) {
                    commandSender.sendMessage("§cErreur: Vous n'avez pas la permission d'utilisez cette commande !");
                    return true;
                }
                if (args.length >= 2) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (main.playersSeeChunks.contains(target)) {
                            PacketConf.setFlag(target, PLSPConfFlag.SEE_CHUNKS, false);
                        } else {
                            PacketConf.setFlag(target, PLSPConfFlag.SEE_CHUNKS, true);
                        }
                    } else {
                        commandSender.sendMessage("§cErreur: Ce joueur est hors-ligne !");
                    }
                } else {
                    commandSender.sendMessage("§c/az seechunks <joueur>");
                }
            } else if (args[0].equalsIgnoreCase("itemrender")) {
                if (!commandSender.hasPermission("azplugin.command.itemrender")) {
                    commandSender.sendMessage("§cErreur: Vous n'avez pas la permission d'utilisez cette commande !");
                    return true;
                }
                Player p;
                if (commandSender instanceof Player) {
                    p = (Player) commandSender;
                } else {
                    commandSender.sendMessage("§cErreur: Vous devez être un joueur pour executer cette commande !");
                    return true;
                }
                if (args.length >= 3){
                    try {
                        /*AZItem azItem = new AZItem(p.getItemInHand());
                        azItem.addPacDisplay(new AZItem.PacDisplay().setColor(AZColor.get0xAARRGGBB(args[3])));
                        azItem.addPacRender(new AZItem.PacRender().setColor(AZColor.get0xAARRGGBB(args[3])).setScale(Float.parseFloat(args[2])));
                        p.setItemInHand(azItem.getItemStack());*/
                        NBTItem nbti = new NBTItem(p.getItemInHand());
                        nbti.mergeCompound(new NBTContainer("{PacRender: {Scale: "+Float.parseFloat(args[1])+", Color: "+ AZColor.get0xAARRGGBB(args[2])+"}, PacDisplay: {Color: "+AZColor.get0xAARRGGBB(args[2])+"}}"));
                        p.getItemInHand().setItemMeta(nbti.getItem().getItemMeta());
                    } catch (NumberFormatException e){
                        p.sendMessage("§cErreur : Les valeur est invalide !.");
                    }
                } else if (args.length == 2) {
                    try {
                        /*AZItem azItem = new AZItem(p.getItemInHand());
                        azItem.addPacRender(new AZItem.PacRender().setScale(Float.parseFloat(args[2])));
                        p.setItemInHand(azItem.getItemStack());*/
                        NBTItem nbti = new NBTItem(p.getItemInHand());
                        nbti.mergeCompound(new NBTContainer("{PacRender: {Scale: "+Float.parseFloat(args[1])+"}}"));
                        p.getItemInHand().setItemMeta(nbti.getItem().getItemMeta());
                    } catch (NumberFormatException e){
                        p.sendMessage("§cErreur : Les valeur est invalide !.");
                    }
                } else {
                    p.sendMessage("§c/az itemrender <taille> [couleur(Hex)]");
                    p.sendMessage("§aVous pouvez utiliser ce site pour faire des couleurs en Hexadécimal https://htmlcolorcodes.com/fr/");
                }
            } else {
                commandSender.sendMessage("§cErreur: Sous-commande introuvable !");
            }
        return true;
    }
    return false;
    }
}
