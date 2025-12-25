package fr.mathip.azplugin.bukkit.commands.items;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import fr.mathip.azplugin.bukkit.handlers.item.Sprite;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class ItemSpriteCommand implements ItemCommand {
    @Override
    public String name() {
        return "sprite";
    }

    @Override
    public String permission() {
        return "azplugin.command.item.sprite";
    }

    @Override
    public String description() {
        return "Modifie l'apparence d'un item";
    }

    @Override
    public void execute(Player player, NBTItem nbtItem, String[] args) {
        if (args.length < 3) {
            player.sendMessage("§c/az item sprite <sprite>");
            return;
        }
        Sprite sprite;
        try {
            sprite = Sprite.valueOf(args[2].toUpperCase());
        } catch (IllegalArgumentException e) {
            player.sendMessage("§cSprite invalide !");
            return;
        }
        nbtItem.mergeCompound(new NBTContainer("{PacDisplay: {Sprite: \"" + sprite.name() + "\"}}"));
        player.setItemInHand(nbtItem.getItem());

    }

    @Override
    public List<String> suggest(Player player, String[] args) {
        List<String> completion = new ArrayList<>();
        for (Sprite sprite : Sprite.values()) {
            if (sprite.name().startsWith(args[2])) {
                completion.add(sprite.name());
            }
        }
        return completion;
    }

}
