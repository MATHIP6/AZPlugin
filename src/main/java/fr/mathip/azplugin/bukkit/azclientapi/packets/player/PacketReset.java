package fr.mathip.azplugin.bukkit.azclientapi.packets.player;

import fr.mathip.azplugin.bukkit.Main;
import org.bukkit.entity.Player;
import pactify.client.api.plsp.packet.client.PLSPPacketReset;

public class PacketReset {
    private static final fr.mathip.azplugin.bukkit.azclientapi.AZManager AZManager = Main.getInstance().getAZManager();

    public static void reset(Player player) {
        AZManager.sendPLSPMessage(player, new PLSPPacketReset());
    }
}
