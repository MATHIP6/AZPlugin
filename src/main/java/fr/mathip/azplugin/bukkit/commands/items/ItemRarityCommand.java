package fr.mathip.azplugin.bukkit.commands.items;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import fr.mathip.azplugin.bukkit.handlers.item.ItemRarity;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class ItemRarityCommand implements ItemCommand {
    @Override
    public String name() {
        return "rarity";
    }

    @Override
    public String permission() {
        return "azplugin.command.item.rarity";
    }

    @Override
    public String description() {
        return "Modifie la rareté d'un item";
    }

    @Override
    public void execute(Player player, NBTItem nbtItem, String[] args) {
        if (args.length < 3) {
            player.sendMessage("§c/az item rarity <Rareté>");
            return;
        }
        ItemRarity rarity;
        try {
            rarity = ItemRarity.valueOf(args[2]);
        } catch (IllegalArgumentException e) {
            player.sendMessage("§cRareté inconnu !");
            return;
        }
        nbtItem.mergeCompound(new NBTContainer("{PacDisplay:{Rarity: \"" + rarity.name() + "\"}}"));
        player.setItemInHand(nbtItem.getItem());
    }

    @Override
    public List<String> suggest(Player player, String[] args) {
        List<String> completion = new ArrayList<>();
        for (ItemRarity rarity : ItemRarity.values()) {
            if (rarity.name().startsWith(args[2])) {
                completion.add(rarity.name());
            }
        }
        return completion;
    }
}
