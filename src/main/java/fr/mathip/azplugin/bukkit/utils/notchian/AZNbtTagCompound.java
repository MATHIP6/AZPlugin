package fr.mathip.azplugin.bukkit.utils.notchian;

import fr.mathip.azplugin.bukkit.utils.BukkitUtil;
import fr.mathip.azplugin.bukkit.utils.NotchianPacketBufferDataOutput;
import pactify.client.api.mcprotocol.NotchianPacketBuffer;
import pactify.client.api.mcprotocol.model.NotchianNbtTagCompound;

public class AZNbtTagCompound implements NotchianNbtTagCompound {

    private Object nbt;

    public AZNbtTagCompound(Object nbt) {
        this.nbt = nbt;

    }

    @Override
    public NotchianNbtTagCompound deepClone() {
        return new AZNbtTagCompound(nbt);
    }

    @Override
    public NotchianNbtTagCompound shallowClone() {
        return new AZNbtTagCompound(nbt);
    }

    @Override
    public void write(NotchianPacketBuffer buf) {
        BukkitUtil.writeNBT(nbt, new NotchianPacketBufferDataOutput(buf));
    }

}
