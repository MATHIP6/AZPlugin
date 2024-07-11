package fr.mathip.azplugin.bukkit.azclientapi.packets.player;

import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.azclientapi.handlers.PLSPWorldEnv;
import org.bukkit.World;
import org.bukkit.entity.Player;
import pactify.client.api.plsp.packet.client.PLSPPacketWorldEnv;

public class PacketWorldEnv {
    private static final fr.mathip.azplugin.bukkit.azclientapi.AZManager AZManager = Main.getInstance().getAZManager();

    public static void reset(Player player) {
        PLSPPacketWorldEnv WorldEnvPacket = new PLSPPacketWorldEnv();
        World world = player.getWorld();
        WorldEnvPacket.setName(world.getName());
        WorldEnvPacket.setType(world.getWorldType().name());

        AZManager.sendPLSPMessage(player, WorldEnvPacket);
    }

    public static void setEnv(Player player, PLSPWorldEnv env) {
        PLSPPacketWorldEnv WorldEnvPacket = new PLSPPacketWorldEnv();
        WorldEnvPacket.setName(player.getWorld().getName());
        WorldEnvPacket.setType(env.name());

        AZManager.sendPLSPMessage(player, WorldEnvPacket);
    }
}
