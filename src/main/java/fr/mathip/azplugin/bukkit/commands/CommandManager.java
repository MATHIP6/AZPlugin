package fr.mathip.azplugin.bukkit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.mathip.azplugin.bukkit.commands.items.ItemCommand;

import java.util.*;

public class CommandManager implements CommandExecutor {

    private Map<Class<? extends AZCommand>, AZCommand> commands;
    private final Map<Class<? extends ItemCommand>, ItemCommand> itemCommands;
    private static CommandManager instance;

    public CommandManager() {
        commands = new HashMap<>();
        itemCommands = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        instance = this;
        if (s.equalsIgnoreCase("az")) {
            if (args.length == 0) {
                commandSender.sendMessage("§a[AZPlugin]§e Liste des commandes:");
                for (AZCommand azCommand : commands.values()) {
                    commandSender.sendMessage("§a /az " + azCommand.name() + " :§e " + azCommand.description());
                }
                return true;
            }
            for (Map.Entry<Class<? extends AZCommand>, AZCommand> azCommand : commands.entrySet()) {
                if (args[0].equalsIgnoreCase(azCommand.getValue().name())) {
                    if (!commandSender.hasPermission(azCommand.getValue().permission())) {
                        commandSender.sendMessage("§cVous n'avez pas la permission d'utiliser cette commande !");
                        return true;
                    }
                    azCommand.getValue().execute(commandSender, args);
                    return true;
                }
            }
            commandSender.sendMessage("§cCommand inconnu !");
            return true;
        }
        return false;
    }

    public static CommandManager getInstance() {
        return instance;
    }

    public void addCommand(AZCommand azCommand) {
        commands.put(azCommand.getClass(), azCommand);
    }

    public Map<Class<? extends AZCommand>, AZCommand> getCommands() {
        return commands;
    }

    public Map<Class<? extends ItemCommand>, ItemCommand> getItemCommands() {
        return itemCommands;
    }

    public void addItemCommand(ItemCommand itemCommand) {
        itemCommands.put(itemCommand.getClass(), itemCommand);
    }
}
