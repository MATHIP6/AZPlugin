package fr.mathip.azplugin.bukkit.head;

import java.util.Base64;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;

public class AZHeadManager {

    public static ItemStack convertHead(ItemStack item) throws Exception {
        if (!(item.getItemMeta() instanceof SkullMeta)) {
            throw new Exception("Head not found");
        }
        NBTItem nbtItem = new NBTItem(item);
        if (!nbtItem.hasTag("SkullOwner")) {
            throw new Exception("Head not found");
        }
        NBTCompound propertie = nbtItem.getCompound("SkullOwner").getCompound("Properties").getCompoundList("textures")
                .get(0);
        String encodedSkinValue = propertie.getString("Value");
        String decodedSkinValue = new String(Base64.getDecoder().decode(encodedSkinValue));
        JSONObject skinValue = (JSONObject) JSONValue.parse(decodedSkinValue);

        JSONObject texture = (JSONObject) skinValue.get("textures");
        JSONObject skin = (JSONObject) texture.get("SKIN");

        JSONObject metadata = (JSONObject) skin.get("metadata");
        if (metadata == null) {
            metadata = new JSONObject();
            skin.put("metadata", metadata);
        }
        metadata.put("transparent", true);
        String newEncodedSkinValue = new String(Base64.getEncoder().encode(skinValue.toString().getBytes()));
        propertie.setString("Value", newEncodedSkinValue);

        return nbtItem.getItem();
    }
}
