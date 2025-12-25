package fr.mathip.azplugin.bukkit.commands.items;

import java.util.List;

import org.bukkit.entity.Player;

import de.tr7zw.changeme.nbtapi.NBTItem;

public interface ItemCommand {
    public abstract String name();

    public abstract String permission();

    public abstract String description();

    public abstract void execute(Player player, NBTItem nbtItem, String[] args);

    default List<String> suggest(Player player, String[] args) {
        return null;
    }

}
