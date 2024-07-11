package fr.mathip.azplugin.bukkit.azclientapi.packets.player;

import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.azclientapi.AZManager;
import fr.mathip.azplugin.bukkit.azclientapi.handlers.PLSPConfFlag;
import fr.mathip.azplugin.bukkit.azclientapi.handlers.PLSPConfInt;
import org.bukkit.entity.Player;
import pactify.client.api.plsp.packet.client.PLSPPacketConfFlag;
import pactify.client.api.plsp.packet.client.PLSPPacketConfInt;

public class PacketConf {
    private static final AZManager AZManager = Main.getInstance().getAZManager();

    public static void setFlag(Player player, PLSPConfFlag flag, Boolean enabled) {
        PLSPPacketConfFlag PacketConfFlag = new PLSPPacketConfFlag();
        PacketConfFlag.setFlag(flag.name().toLowerCase());
        PacketConfFlag.setEnabled(enabled);

        AZManager.sendPLSPMessage(player, PacketConfFlag);
    }

    public static void setInt(Player player, PLSPConfInt param, Integer value) {
        PLSPPacketConfInt PacketConfInt = new PLSPPacketConfInt();
        PacketConfInt.setParam(param.name().toLowerCase());
        PacketConfInt.setValue(value);

        AZManager.sendPLSPMessage(player, PacketConfInt);
    }
}
