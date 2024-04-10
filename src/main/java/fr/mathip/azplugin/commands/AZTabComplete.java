package fr.mathip.azplugin.commands;

import fr.speccy.azclientapi.bukkit.handlers.PLSPPlayerModel;
import fr.speccy.azclientapi.bukkit.handlers.PLSPWorldEnv;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class AZTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (s.equalsIgnoreCase("az") && commandSender.hasPermission("azplugin.*")){
            if (args.length == 2 && args[0].equalsIgnoreCase("model")){
                List<String> completion = new ArrayList<>();
                for (PLSPPlayerModel plspPlayerModel : PLSPPlayerModel.values()){
                    if (plspPlayerModel.name().startsWith(args[1].toUpperCase())){
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
