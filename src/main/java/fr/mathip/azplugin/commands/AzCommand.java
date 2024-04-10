package fr.mathip.azplugin.commands;

import fr.mathip.azplugin.Main;
import fr.speccy.azclientapi.bukkit.AZClientPlugin;
import fr.speccy.azclientapi.bukkit.AZManager;
import fr.speccy.azclientapi.bukkit.AZPlayer;
import fr.speccy.azclientapi.bukkit.handlers.PLSPConfFlag;
import fr.speccy.azclientapi.bukkit.handlers.PLSPPlayerModel;
import fr.speccy.azclientapi.bukkit.handlers.PLSPWorldEnv;
import fr.speccy.azclientapi.bukkit.packets.*;
import fr.thebatteur.items.AZItem;
import fr.thebatteur.items.handlers.Sprite;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AzCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (s.equalsIgnoreCase("az")){

            Main main = Main.getInstance();

            Player p;
            if (commandSender instanceof Player) p = (Player) commandSender;
            else p = null;
            if (args.length == 0){
                commandSender.sendMessage("§ccheck, list, size, model, opacity, worldenv, vignette, tag, item, reload");
            } else if (args[0].equalsIgnoreCase("list")) {
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
                        pactifyList.isEmpty() ? (ChatColor.GRAY + "(none)") : (ChatColor.GREEN + String.join(", ", (Iterable)pactifyList))));
                commandSender.sendMessage(ChatColor.YELLOW + "Les joueurs qui n'utilisent pas le AZ launcher: " + (
                        vanillaList.isEmpty() ? (ChatColor.GRAY + "(none)") : (ChatColor.RED + String.join(", ", (Iterable)vanillaList))));

            } else if (args[0].equalsIgnoreCase("reload")) {
                Main.getInstance().initConfig();
                for (Player pl : Bukkit.getOnlinePlayers()){
                    if (AZPlayer.hasAZLauncher(pl)){
                        PacketConfFlag.setFlag(pl, PLSPConfFlag.ATTACK_COOLDOWN, main.attackCooldown);
                        PacketConfFlag.setFlag(pl, PLSPConfFlag.PLAYER_PUSH, main.playerPush);
                        PacketConfFlag.setFlag(pl, PLSPConfFlag.LARGE_HITBOX, main.largeHitBox);
                        PacketConfFlag.setFlag(pl, PLSPConfFlag.SWORD_BLOCKING, main.swordBlocking);
                        PacketConfFlag.setFlag(pl, PLSPConfFlag.HIT_AND_BLOCK, main.hitAndBlock);
                        PacketConfFlag.setFlag(pl, PLSPConfFlag.OLD_ENCHANTEMENTS, main.oldEnchantments);
                        PacketConfFlag.setFlag(pl, PLSPConfFlag.SIDEBAR_SCORES, main.sidebarScore);
                        PacketConfFlag.setFlag(pl, PLSPConfFlag.PVP_HIT_PRIORITY, main.pvpHitPriority);
                        PacketConfFlag.setFlag(pl, PLSPConfFlag.SEE_CHUNKS, main.seeChunks);
                        PacketConfFlag.setFlag(pl, PLSPConfFlag.SMOOTH_EXPERIENCE_BAR, main.smoothExperienceBar);
                        PacketConfFlag.setFlag(pl, PLSPConfFlag.SORT_TAB_LIST_BY_NAMES, main.sortTabListByName);
                    }
                }
                commandSender.sendMessage("§aLe plugin a reload !");

            } else if (args[0].equalsIgnoreCase("check")) {
                if (args.length == 0) {
                    commandSender.sendMessage(ChatColor.RED + "Usage: /az <player>");
                    return true;
                }
                Player player = Bukkit.getPlayer(args[1]);
                if (player == null) {
                    commandSender.sendMessage(ChatColor.RED + "Ce joueur est hors ligne");
                    return true;
                }
                commandSender.sendMessage(ChatColor.YELLOW + player.getName() + (
                        AZPlayer.hasAZLauncher(player) ? (ChatColor.GREEN + " utilise") : (ChatColor.RED + " n'utilise pas")) + ChatColor.YELLOW + " le AZ launcher");

            } else if (commandSender instanceof Player) {
                if (args[0].equalsIgnoreCase("size")){
                    if (args.length >= 3){
                        if (Bukkit.getPlayer(args[2]) != null){
                            try {
                                Float size = Float.parseFloat(args[1]);
                                if (main.playersScale.containsKey(Bukkit.getPlayer(args[2]))){
                                    main.playersScale.replace(Bukkit.getPlayer(args[2]), size);
                                } else {
                                    main.playersScale.put(Bukkit.getPlayer(args[2]), size);
                                }
                                //PacketEntityMeta.setPlayerScale(Bukkit.getPlayer(args[2]), size, size, size, size, size, true);
                                commandSender.sendMessage("§achangement de taille effectué !");
                            } catch (NumberFormatException e) {
                                commandSender.sendMessage("§cErreur : La taille n'est pas un nombre valide.");
                            }
                        } else {
                            commandSender.sendMessage("§cCe joueur est hors-ligne !");
                        }
                    } else if (args.length == 2){
                        try {
                            Float size = Float.parseFloat(args[1]);
                            if (main.playersScale.containsKey(p)){
                                main.playersScale.replace(p, size);
                            } else {
                                main.playersScale.put(p, size);
                            }
                            //PacketEntityMeta.setPlayerScale(p, size, size, size, size, size, true);
                            commandSender.sendMessage("§achangement de taille effectué !");
                        } catch (NumberFormatException e) {
                            commandSender.sendMessage("§cErreur : La taille n'est pas un nombre valide.");
                        }
                    } else {
                        commandSender.sendMessage("/az size <size> [player]");
                    }

                } else if (args[0].equalsIgnoreCase("model")) {
                    if (args.length >= 3){
                        if (Bukkit.getPlayer(args[2]) != null){
                            if (args[1].equalsIgnoreCase("reset")){
                                if (main.playersModel.containsKey(Bukkit.getPlayer(args[2]))){
                                    main.playersModel.remove(Bukkit.getPlayer(args[2]));
                                    PacketPlayerModel.resetPlayerModel(Bukkit.getPlayer(args[2]));
                                }
                                p.sendMessage("§achangement de skin effectué !");
                                return true;
                            }
                            try {
                                PLSPPlayerModel plspPlayerModel = PLSPPlayerModel.valueOf(args[1].toUpperCase());
                                if (main.playersModel.containsKey(Bukkit.getPlayer(args[2]))){
                                    main.playersModel.replace(Bukkit.getPlayer(args[2]), plspPlayerModel);
                                } else {
                                    main.playersModel.put(Bukkit.getPlayer(args[2]), plspPlayerModel);
                                }
                                //PacketPlayerModel.setPlayerModel(Bukkit.getPlayer(args[2]), id);
                                p.sendMessage("§achangement de skin effectué !");
                            } catch (IllegalArgumentException e){
                                p.sendMessage("§cErreur : La valeur est invalide !.");
                            }
                        } else {
                            p.sendMessage("§cCe joueur est hors-ligne !");
                        }
                    } else if (args.length == 2){
                        if (args[1].equalsIgnoreCase("reset")){
                            if (main.playersModel.containsKey(p)){
                                main.playersModel.remove(p);
                                PacketPlayerModel.resetPlayerModel(p);
                            }
                            p.sendMessage("§achangement de skin effectué !");
                            return true;
                        }

                        try {
                            PLSPPlayerModel plspPlayerModel = PLSPPlayerModel.valueOf(args[1].toUpperCase());
                            if (main.playersModel.containsKey(p)){
                                main.playersModel.replace(p, plspPlayerModel);
                            } else {
                                main.playersModel.put(p, plspPlayerModel);
                            }
                            //PacketPlayerModel.setPlayerModel(p, id);
                            p.sendMessage("§achangement de skin effectué !");
                        } catch (IllegalArgumentException e){
                            p.sendMessage("§cErreur : La valeur est invalide !.");
                        }
                    } else {
                        p.sendMessage("§c/az model <id> [player]");
                    }

                } else if (args[0].equalsIgnoreCase("opacity")) {
                    if (args.length >= 3){
                        if (Bukkit.getPlayer(args[2]) != null){
                            try {
                                Float opacity = Float.parseFloat(args[1]);
                                if (main.playersOpacity.containsKey(Bukkit.getPlayer(args[2]))){
                                    main.playersOpacity.replace(Bukkit.getPlayer(args[2]), opacity);
                                } else {
                                    main.playersOpacity.put(Bukkit.getPlayer(args[2]), opacity);
                                }
                                //PacketEntityMeta.setPlayerOpacity(Bukkit.getPlayer(args[2]), opacity);
                                //PacketEntityMeta.setNameTagOpacity(Bukkit.getPlayer(args[2]), opacity);
                                //PacketEntityMeta.setSneakNameTagOpacity(Bukkit.getPlayer(args[2]), opacity);
                                p.sendMessage("§achangement de d'opacité effectué !");
                            } catch (NumberFormatException e){
                                p.sendMessage("§cErreur : La valeur est invalide !.");
                            }
                        } else {
                            p.sendMessage("§cCe joueur est hors-ligne !");
                        }
                    } else if (args.length == 2){
                        try {
                            Float opacity = Float.parseFloat(args[1]);
                            if (main.playersOpacity.containsKey(p)){
                                main.playersOpacity.replace(p, opacity);
                            } else {
                                main.playersOpacity.put(p, opacity);
                            }
                            //PacketEntityMeta.setPlayerOpacity(p, opacity);
                            //PacketEntityMeta.setNameTagOpacity(p, opacity);
                            //PacketEntityMeta.setSneakNameTagOpacity(p, opacity);
                            p.sendMessage("§achangement de d'opacité effectué !");
                        } catch (NumberFormatException e){
                            p.sendMessage("§cErreur : La valeur est invalide !.");
                        }
                    } else {
                        p.sendMessage("/az opacity <velue> [player]");
                    }

                } else if (args[0].equalsIgnoreCase("worldenv")) {
                    if (args.length >= 2){
                        if (args[1].equals("NORMAL") || args[1].equals("NETHER") || args[1].equals("THE_END")){
                            for (Player pl : Bukkit.getOnlinePlayers()) {
                                PacketWorldEnv.setWorldEnv(pl, PLSPWorldEnv.valueOf(args[1]));
                            }
                            p.sendMessage("§achangement de d'environnement effectué !");
                        } else {
                            p.sendMessage("§cErreur : La valeur est invalide !.");
                        }
                    } else {
                        p.sendMessage("/az worldenv <NORMAL, NETHER, THE_END> [player]");
                    }
                } else if (args[0].equalsIgnoreCase("vignette")) {
                    if (args.length >= 2 && args[1].equalsIgnoreCase("reset")){
                        if (args.length >= 3 && Bukkit.getPlayer(args[2]) != null){
                            PacketVignette.resetVignette(Bukkit.getPlayer(args[2]));
                            p.sendMessage("§achangement de d'environnement effectué !");
                            return true;
                        }
                        PacketVignette.resetVignette(p);
                        p.sendMessage("§achangement de d'environnement effectué !");
                    }
                    if (args.length >= 5) {
                        if (Bukkit.getPlayer(args[4]) != null){
                            try {
                                int red = Integer.parseInt(args[1]);
                                int green = Integer.parseInt(args[2]);
                                int blue = Integer.parseInt(args[3]);
                                PacketVignette.setVignette(Bukkit.getPlayer(args[4]), red, green, blue);
                                p.sendMessage("§achangement de d'environnement effectué !");
                            } catch (NumberFormatException e){
                                p.sendMessage("§cErreur : Les valeur est invalide !.");
                            }
                        } else {
                            p.sendMessage("§cCe joueur est hors-ligne !");
                        }
                    } else if (args.length == 4){
                        try {
                            int red = Integer.parseInt(args[1]);
                            int green = Integer.parseInt(args[2]);
                            int blue = Integer.parseInt(args[3]);
                            PacketVignette.setVignette(p, red, green, blue);
                            p.sendMessage("§achangement de d'environnement effectué !");
                        } catch (NumberFormatException e){
                            p.sendMessage("§cErreur : Les valeur est invalide !.");
                        }
                    } else {
                        p.sendMessage("/az vignette <red> <green> <blue> [player]");
                    }
                } else if (args[0].equalsIgnoreCase("tag")) {
                    if (args.length >= 3){
                        if (Bukkit.getPlayer(args[2]) != null){
                            Player target = Bukkit.getPlayer(args[2]);
                            if (main.playersTag.containsKey(target)){
                                main.playersTag.replace(target, args[1]);
                            } else {
                                main.playersTag.put(target, args[1]);
                            }
                            //PacketEntityMeta.setNameTag(target, args[1]);
                            p.sendMessage("§achangement de d'environnement effectué !");
                        } else {
                            p.sendMessage("§cCe joueur est hors-ligne !");
                        }
                    } else if (args.length == 2){
                        //PacketEntityMeta.setNameTag(p, args[1]);
                        if (main.playersTag.containsKey(p)){
                            main.playersTag.replace(p, args[1]);
                        } else {
                            main.playersTag.put(p, args[1]);
                        }
                        p.sendMessage("§achangement de d'environnement effectué !");
                    } else {
                        p.sendMessage("/az tag <name> [player]");
                    }
                } else if (args[0].equalsIgnoreCase("item")) {
                    if (args.length == 1){
                        p.sendMessage("§c/az item render");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("render")){
                        if (args.length >= 4){
                            try {
                                AZItem azItem = new AZItem(p.getItemInHand());
                                azItem.addPacDisplay(new AZItem.PacDisplay().setColor(AZManager.getColor(args[3])));
                                azItem.addPacRender(new AZItem.PacRender().setColor(AZManager.getColor(args[3])).setScale(Float.parseFloat(args[2])));
                                p.setItemInHand(azItem.getItemStack());
                            } catch (NumberFormatException e){
                                p.sendMessage("§cErreur : Les valeur est invalide !.");
                            }
                        } else if (args.length == 3) {
                            try {
                                AZItem azItem = new AZItem(p.getItemInHand());
                                azItem.addPacRender(new AZItem.PacRender().setScale(Float.parseFloat(args[2])));
                                p.setItemInHand(azItem.getItemStack());
                            } catch (NumberFormatException e){
                                p.sendMessage("§cErreur : Les valeur est invalide !.");
                            }
                        } else {
                            p.sendMessage("§c/az item render <scale> [color(Hex)]");
                        }
                    }
                }

            } else {
                commandSender.sendMessage("§cVous devez etre joueur pour executer cette commande !");
            }
            return true;
        }
        return false;
    }
}
