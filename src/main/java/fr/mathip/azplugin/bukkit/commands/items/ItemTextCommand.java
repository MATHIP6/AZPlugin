package fr.mathip.azplugin.bukkit.commands.items;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.entity.Player;

public class ItemTextCommand implements ItemCommand {
    @Override
    public String name() {
        return "text";
    }

    @Override
    public String permission() {
        return "azplugin.command.item.text";
    }

    @Override
    public String description() {
        return "Transforme l'item en text";
    }

    @Override
    public void execute(Player player, NBTItem nbtItem, String[] args) {
        if (args.length < 3) {
            player.sendMessage("§c/az item text <text>");
            return;
        }
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (String arg : args) {
            if (count > 1) {
                sb.append(" ").append(arg);
            }
            count++;
        }
        nbtItem.mergeCompound(
                new NBTContainer("{PacDisplay: {Sprite: \"EMOJI\", SpriteData: \"" + sb.toString() + "\"}}"));
        player.getItemInHand().setItemMeta(nbtItem.getItem().getItemMeta());
    }
}
