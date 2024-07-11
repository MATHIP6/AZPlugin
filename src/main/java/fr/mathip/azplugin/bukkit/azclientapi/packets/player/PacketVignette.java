package fr.mathip.azplugin.bukkit.azclientapi.packets.player;

import fr.mathip.azplugin.bukkit.Main;
import org.bukkit.entity.Player;
import pactify.client.api.plsp.packet.client.PLSPPacketVignette;

public class PacketVignette {
    private static final fr.mathip.azplugin.bukkit.azclientapi.AZManager AZManager = Main.getInstance().getAZManager();

    public static void reset(Player player) {
        PLSPPacketVignette PacketVignette = new PLSPPacketVignette();
        PacketVignette.setEnabled(false);

        AZManager.sendPLSPMessage(player, PacketVignette);
    }

    public static void setColor(Player player, Integer red, Integer green, Integer blue) {
        PLSPPacketVignette PacketVignette = new PLSPPacketVignette();
        PacketVignette.setRed((float) red / 255);
        PacketVignette.setGreen((float) green / 255);
        PacketVignette.setBlue((float) blue / 255);
        PacketVignette.setEnabled(true);

        AZManager.sendPLSPMessage(player, PacketVignette);
    }
}
