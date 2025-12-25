package fr.mathip.azplugin.bukkit.commands;

import fr.mathip.azplugin.bukkit.commands.items.ItemCommand;
import fr.mathip.azplugin.bukkit.config.ConfigManager;
import fr.mathip.azplugin.bukkit.config.PopupConfig;
import fr.mathip.azplugin.bukkit.handlers.PLSPPlayerModel;
import fr.mathip.azplugin.bukkit.handlers.PLSPWorldEnv;
import fr.mathip.azplugin.bukkit.packets.PacketPopup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AZTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (s.equalsIgnoreCase("az") && commandSender.hasPermission("azplugin.*")) {
            if (args.length == 1) {
                List<String> completion = new ArrayList<>();
                List<String> commands = new ArrayList<>();
                for (AZCommand azCommand : CommandManager.getInstance().getCommands().values()) {
                    completion.add(azCommand.name());
                }
                for (String arg : commands) {
                    if (arg.startsWith(args[0])) {
                        completion.add(arg);
                    }
                }
                return completion;
            }
            if (args.length == 2 && args[0].equalsIgnoreCase("model")) {
                List<String> completion = new ArrayList<>();
                for (PLSPPlayerModel plspPlayerModel : PLSPPlayerModel.values()) {
                    if (plspPlayerModel.name().startsWith(args[1].toUpperCase())) {
                        completion.add(plspPlayerModel.name());
                    }
                }
                return completion;
            } else if (args.length == 2 && args[0].equalsIgnoreCase("worldenv")) {
                List<String> completion = new ArrayList<>();
                for (PLSPWorldEnv plspWorldEnv : PLSPWorldEnv.values()) {
                    if (plspWorldEnv.name().startsWith(args[1].toUpperCase())) {
                        completion.add(plspWorldEnv.name());
                    }
                }
                return completion;
            } else if (args.length == 2 && args[0].equalsIgnoreCase("summon")) {
                List<String> completion = new ArrayList<>();
                for (EntityType entityType : EntityType.values()) {
                    if (entityType.name().startsWith(args[1].toUpperCase())) {
                        completion.add(entityType.name());
                    }
                }
                return completion;
            } else if (args.length == 2 && args[0].equalsIgnoreCase("popup")) {
                List<String> completion = new ArrayList<>();
                for (PacketPopup popup : ConfigManager.getInstance().getPopupConfig().popups) {
                    if (popup.getName().startsWith(args[1])) {
                        completion.add(popup.getName());
                    }
                }
                return completion;
            } else if (args.length >= 2 && args[0].equalsIgnoreCase("item")) {
                List<String> completion = new ArrayList<>();
                if (args.length == 2) {
                    for (ItemCommand itemCommand : CommandManager.getInstance().getItemCommands().values()) {
                        if (itemCommand.name().startsWith(args[1])) {
                            completion.add(itemCommand.name());
                        }
                    }
                    return completion;
                }
                for (ItemCommand itemCommand : CommandManager.getInstance().getItemCommands().values()) {
                    if (itemCommand.name().equalsIgnoreCase(args[1])) {
                        return itemCommand.suggest((Player) commandSender, args);
                    }
                }
            }
        }
        return null;
    }
}

// default List<String> suggest(CommandSender sender, String[] args) {
// return List.of();
// }
