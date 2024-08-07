package fr.mathip.azplugin.bukkit.commands;

import fr.mathip.azplugin.bukkit.azclientapi.handlers.PLSPPlayerModel;
import fr.mathip.azplugin.bukkit.azclientapi.handlers.PLSPWorldEnv;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AZTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (s.equalsIgnoreCase("az") && commandSender.hasPermission("azplugin.*")){
            if (args.length == 1) {
                List<String> completion = new ArrayList<>();
                List<String> commands = Arrays.asList("check", "list", "reload", "size",
                        "model", "opacity", "tag", "subtag", "suptag", "vignette", "worldenv",
                        "seechunks", "itemrender");
                for (String arg : commands) {
                    if (arg.startsWith(args[0])) {
                        completion.add(arg);
                    }
                }
                return completion;
            }
            if (args.length == 3 && args[0].equalsIgnoreCase("model")){
                List<String> completion = new ArrayList<>();
                for (PLSPPlayerModel plspPlayerModel : PLSPPlayerModel.values()){
                    if (plspPlayerModel.name().startsWith(args[2].toUpperCase())){
                        completion.add(plspPlayerModel.name());
                    }
                }
                return completion;
            } else if (args.length == 2 && args[0].equalsIgnoreCase("worldenv")) {
                List<String> completion = new ArrayList<>();
                for (PLSPWorldEnv plspWorldEnv : PLSPWorldEnv.values()){
                    if (plspWorldEnv.name().startsWith(args[1].toUpperCase())){
                        completion.add(plspWorldEnv.name());
                    }
                }
                return completion;

            }
        }
        return null;
    }
}
