package fr.mathip.azplugin.bukkit.azclientapi.packets.player;

import fr.mathip.azplugin.bukkit.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pactify.client.api.plprotocol.metadata.PactifyTagMetadata;
import pactify.client.api.plsp.packet.client.PLSPPacketEntityMeta;
import pactify.client.api.plsp.packet.client.PLSPPacketPlayerMeta;

public class PacketPlayerOpacity {
    private static final fr.mathip.azplugin.bukkit.azclientapi.AZManager AZManager = Main.getInstance().getAZManager();
    private static final Float DEFAULT_OPACITY = -1.0F;

    public static void resetPlayer(Player player) {
        PLSPPacketPlayerMeta PlayerMetaPacket = new PLSPPacketPlayerMeta(player.getUniqueId());
        PlayerMetaPacket.setOpacity(DEFAULT_OPACITY);

        AZManager.sendPLSPMessage(player, PlayerMetaPacket);
        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, PlayerMetaPacket);
        }
    }

    public static void setPlayer(Player player, Float opacity, Player target) {
        PLSPPacketPlayerMeta PlayerMetaPacket = new PLSPPacketPlayerMeta(player.getUniqueId());
        PlayerMetaPacket.setOpacity(opacity);

        AZManager.sendPLSPMessage(player, PlayerMetaPacket);
        AZManager.sendPLSPMessage(target, PlayerMetaPacket);
    }

    public static void resetNameTag(Player player) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(player.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setOpacity(DEFAULT_OPACITY);
        EntityMetaPacket.setTag(entityTagPacket);

        AZManager.sendPLSPMessage(player, EntityMetaPacket);
        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setNameTag(Player player, Float opacity, Player target) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(player.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setOpacity(opacity);
        EntityMetaPacket.setTag(entityTagPacket);

        AZManager.sendPLSPMessage(player, EntityMetaPacket);
        AZManager.sendPLSPMessage(target, EntityMetaPacket);
    }

    public static void resetSneakNameTag(Player player) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(player.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setSneakOpacity(DEFAULT_OPACITY);
        EntityMetaPacket.setTag(entityTagPacket);

        AZManager.sendPLSPMessage(player, EntityMetaPacket);
        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setSneakNameTag(Player player, Float opacity, Player target) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(player.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setSneakOpacity(opacity);
        EntityMetaPacket.setTag(entityTagPacket);

        AZManager.sendPLSPMessage(player, EntityMetaPacket);
        AZManager.sendPLSPMessage(target, EntityMetaPacket);
    }

    public static void setOpacity(Player player, Float opacity, Player target) {
        setPlayer(player, opacity, target);
        setNameTag(player, opacity, target);
        setSneakNameTag(player, opacity, target);
    }

    public static void resetPointed(Player player) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(player.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setPointedOpacity(DEFAULT_OPACITY);
        EntityMetaPacket.setTag(entityTagPacket);

        AZManager.sendPLSPMessage(player, EntityMetaPacket);
        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setPointed(Player player, Float opacity) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(player.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setPointedOpacity(opacity);
        EntityMetaPacket.setTag(entityTagPacket);

        AZManager.sendPLSPMessage(player, EntityMetaPacket);
        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void resetThroughWall(Player player) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(player.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setThroughWallOpacity(DEFAULT_OPACITY);
        EntityMetaPacket.setTag(entityTagPacket);

        AZManager.sendPLSPMessage(player, EntityMetaPacket);
        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setThroughWall(Player player, Float opacity) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(player.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setThroughWallOpacity(opacity);
        EntityMetaPacket.setTag(entityTagPacket);

        AZManager.sendPLSPMessage(player, EntityMetaPacket);
        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void resetSneakThroughWall(Player player) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(player.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setSneakThroughWallOpacity(DEFAULT_OPACITY);
        EntityMetaPacket.setTag(entityTagPacket);

        AZManager.sendPLSPMessage(player, EntityMetaPacket);
        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setSneakThroughWall(Player player, Float opacity) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(player.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setSneakThroughWallOpacity(opacity);
        EntityMetaPacket.setTag(entityTagPacket);

        AZManager.sendPLSPMessage(player, EntityMetaPacket);
        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }
}
