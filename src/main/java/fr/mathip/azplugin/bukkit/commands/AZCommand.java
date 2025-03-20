package fr.mathip.azplugin.bukkit.commands;


import org.bukkit.command.CommandSender;

public interface AZCommand {

    public abstract String name();

    public abstract String permission();

    public abstract String description();

    public abstract void execute(CommandSender sender, String[] args);


}
