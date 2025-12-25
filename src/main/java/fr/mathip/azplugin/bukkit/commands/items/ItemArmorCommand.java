package fr.mathip.azplugin.bukkit.commands.items;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.entity.Player;

public class ItemArmorCommand implements ItemCommand {
    @Override
    public String name() {
        return "armor";
    }

    @Override
    public String permission() {
        return "azplugin.command.item.armor";
    }

    @Override
    public String description() {
        return "Modifie les points d'armure";
    }

    @Override
    public void execute(Player player, NBTItem nbtItem, String[] args) {
        if (args.length < 3) {
            player.sendMessage("§c/az item armor <points d'armures>");
            return;
        }
        int armor;
        try {
            armor = Integer.parseInt(args[2]) * 100;
        } catch (NumberFormatException e) {
            player.sendMessage("§cVous devez mettre un nombre");
            return;
        }
        nbtItem.mergeCompound(new NBTContainer("{PacPropArmor: " + armor + "s}"));
        player.setItemInHand(nbtItem.getItem());
    }
}
