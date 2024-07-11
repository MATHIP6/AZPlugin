package fr.mathip.azplugin.bukkit.azclientapi.packets.entity;

import fr.mathip.azplugin.bukkit.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import pactify.client.api.plprotocol.metadata.PactifyTagMetadata;
import pactify.client.api.plsp.packet.client.PLSPPacketEntityMeta;

public class PacketEntityOpacity {
    private static final fr.mathip.azplugin.bukkit.azclientapi.AZManager AZManager = Main.getInstance().getAZManager();
    private static final Float DEFAULT_OPACITY = -1.0F;

    public static void resetEntity(Entity entity) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        EntityMetaPacket.setOpacity(DEFAULT_OPACITY);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setEntity(Entity entity, Float opacity) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        EntityMetaPacket.setOpacity(opacity);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void resetNameTag(Entity entity) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setOpacity(DEFAULT_OPACITY);
        EntityMetaPacket.setTag(entityTagPacket);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setNameTag(Entity entity, Float opacity) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setOpacity(opacity);
        EntityMetaPacket.setTag(entityTagPacket);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void resetSneakNameTag(Entity entity) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setSneakOpacity(DEFAULT_OPACITY);
        EntityMetaPacket.setTag(entityTagPacket);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setSneakNameTag(Entity entity, Float opacity) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setSneakOpacity(opacity);
        EntityMetaPacket.setTag(entityTagPacket);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void resetPointed(Entity entity) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setPointedOpacity(DEFAULT_OPACITY);
        EntityMetaPacket.setTag(entityTagPacket);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setPointed(Entity entity, Float opacity) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setPointedOpacity(opacity);
        EntityMetaPacket.setTag(entityTagPacket);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void resetThroughWall(Entity entity) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setThroughWallOpacity(DEFAULT_OPACITY);
        EntityMetaPacket.setTag(entityTagPacket);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setThroughWall(Entity entity, Float opacity) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setThroughWallOpacity(opacity);
        EntityMetaPacket.setTag(entityTagPacket);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void resetSneakThroughWall(Entity entity) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setSneakThroughWallOpacity(DEFAULT_OPACITY);
        EntityMetaPacket.setTag(entityTagPacket);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setSneakThroughWall(Entity entity, Float opacity) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setSneakThroughWallOpacity(opacity);
        EntityMetaPacket.setTag(entityTagPacket);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }
}

