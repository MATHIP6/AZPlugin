package fr.mathip.azplugin.bukkit.head;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTCompoundList;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTListCompound;

public class HeadItemFactory {

    public static ItemStack createFromBase64(String base64Value) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        NBTItem nbtItem = new NBTItem(item);

        NBTCompound skullOwner = nbtItem.getOrCreateCompound("SkullOwner");
        skullOwner.setUUID("Id", UUID.randomUUID());
        NBTCompound properties = skullOwner.getOrCreateCompound("Properties");
        NBTCompoundList textures = properties.getCompoundList("textures");
        NBTListCompound entry = textures.addCompound();
        entry.setString("Value", base64Value);

        return nbtItem.getItem();
    }
}
