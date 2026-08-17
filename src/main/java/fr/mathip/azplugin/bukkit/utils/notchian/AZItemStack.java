package fr.mathip.azplugin.bukkit.utils.notchian;

import org.bukkit.inventory.ItemStack;

import fr.mathip.azplugin.bukkit.utils.BukkitUtil;
import pactify.client.api.mcprotocol.model.NotchianItemStack;
import pactify.client.api.mcprotocol.model.NotchianNbtTagCompound;

public class AZItemStack implements NotchianItemStack {

    private ItemStack itemStack;

    public AZItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public NotchianItemStack deepClone() {
        return new AZItemStack(itemStack);
    }

    @Override
    public int getCount() {
        return itemStack.getAmount();
    }

    @Override
    public int getDamage() {
        return itemStack.getDurability();
    }

    @Override
    public int getItemId() {
        return itemStack.getType().getId();
    }

    @Override
    public NotchianNbtTagCompound getTag() {
        Object tag = BukkitUtil.getItemNBT(itemStack);
        if (tag == null) {
            return null;
        }
        return new AZNbtTagCompound(tag);
    }

    @Override
    public NotchianItemStack shallowClone() {

        return new AZItemStack(itemStack);
    }

}
