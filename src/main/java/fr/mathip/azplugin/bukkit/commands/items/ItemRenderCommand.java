package fr.mathip.azplugin.bukkit.commands.items;

import org.bukkit.entity.Player;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import fr.mathip.azplugin.bukkit.utils.AZColor;

public class ItemRenderCommand implements ItemCommand {

    @Override
    public String name() {
        return "render";
    }

    @Override
    public String permission() {
        return "azplugin.command.item.render";
    }

    @Override
    public String description() {
        return "Modifie le rendue d'un item";
    }

    @Override
    public void execute(Player player, NBTItem nbtItem, String[] args) {
        if (args.length >= 4) {
            try {
                nbtItem.mergeCompound(new NBTContainer("{PacRender: {Scale: " + Float.parseFloat(args[2]) + ", Color: "
                        + AZColor.get0xAARRGGBB(args[3]) + "}, PacDisplay: {Color: " + AZColor.get0xAARRGGBB(args[3])
                        + "}}"));
                player.getItemInHand().setItemMeta(nbtItem.getItem().getItemMeta());
            } catch (NumberFormatException e) {
                player.sendMessage("§cErreur : Les valeur est invalide !.");
            }
        } else if (args.length == 3) {
            try {
                nbtItem.mergeCompound(new NBTContainer("{PacRender: {Scale: " + Float.parseFloat(args[2]) + "}}"));
                player.getItemInHand().setItemMeta(nbtItem.getItem().getItemMeta());
            } catch (NumberFormatException e) {
                player.sendMessage("§cErreur : Les valeur est invalide !.");
            }
        } else {
            player.sendMessage("§c/az item render <taille> [couleur(Hex)]");
            player.sendMessage(
                    "§aVous pouvez utiliser ce site pour faire des couleurs en Hexadécimal https://htmlcolorcodes.com/fr/");
        }

    }

}
