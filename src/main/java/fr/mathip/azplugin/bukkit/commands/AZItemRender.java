package fr.mathip.azplugin.bukkit.commands;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import fr.mathip.azplugin.bukkit.utils.AZColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AZItemRender implements AZCommand{
    @Override
    public String name() {
        return "itemrender";
    }

    @Override
    public String permission() {
        return "azplugin.command.itemrender";
    }

    @Override
    public String description() {
        return "Change la couleur et la taille de l'item porté";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p;
        if (sender instanceof Player) {
            p = (Player) sender;
        } else {
            sender.sendMessage("§cErreur: Vous devez être un joueur pour executer cette commande !");
            return;
        }
        if (args.length >= 3){
            try {
                        /*AZItem azItem = new AZItem(p.getItemInHand());
                        azItem.addPacDisplay(new AZItem.PacDisplay().setColor(AZColor.get0xAARRGGBB(args[3])));
                        azItem.addPacRender(new AZItem.PacRender().setColor(AZColor.get0xAARRGGBB(args[3])).setScale(Float.parseFloat(args[2])));
                        p.setItemInHand(azItem.getItemStack());*/
                if (p.getItemInHand() == null) {
                    p.sendMessage("§cErreur: Vous devez porter un item !");
                    return;
                }
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
    }
}
