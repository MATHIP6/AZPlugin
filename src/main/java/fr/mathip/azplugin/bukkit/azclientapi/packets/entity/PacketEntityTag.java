package fr.mathip.azplugin.bukkit.azclientapi.packets.entity;

import fr.mathip.azplugin.bukkit.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import pactify.client.api.plprotocol.metadata.PactifyTagMetadata;
import pactify.client.api.plsp.packet.client.PLSPPacketEntityMeta;

public class PacketEntityTag {
    private static final fr.mathip.azplugin.bukkit.azclientapi.AZManager AZManager = Main.getInstance().getAZManager();

    public static void setNameTag(Entity entity, String tag) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setText(ChatColor.translateAlternateColorCodes('&', tag));
        EntityMetaPacket.setTag(entityTagPacket);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setSupTag(Entity entity, String tag) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyTagMetadata TagMetaData = new PactifyTagMetadata();
        TagMetaData.setText(ChatColor.translateAlternateColorCodes('&', tag));
        EntityMetaPacket.setSupTag(TagMetaData);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setSubTag(Entity entity, String tag) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyTagMetadata TagMetaData = new PactifyTagMetadata();
        TagMetaData.setText(ChatColor.translateAlternateColorCodes('&', tag));
        EntityMetaPacket.setSubTag(TagMetaData);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setScale(Entity entity, Float scale) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setScale(scale);
        EntityMetaPacket.setTag(entityTagPacket);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setSneakScale(Entity entity, Float scale) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setSneakScale(scale);
        EntityMetaPacket.setTag(entityTagPacket);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setDistance(Entity entity, Float distance) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setDistance(distance);
        EntityMetaPacket.setTag(entityTagPacket);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setSneakDistance(Entity entity, Float distance) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setSneakDistance(distance);
        EntityMetaPacket.setTag(entityTagPacket);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setTeamVisibility(Entity entity, Integer teamVisibility) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setTeamVisibility(teamVisibility);
        EntityMetaPacket.setTag(entityTagPacket);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setSneakTeamVisibility(Entity entity, Integer teamVisibility) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setSneakTeamVisibility(teamVisibility);
        EntityMetaPacket.setTag(entityTagPacket);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setPointedTeamVisibility(Entity entity, Integer pointedTeamVisibility) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setPointedTeamVisibility(pointedTeamVisibility);
        EntityMetaPacket.setTag(entityTagPacket);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setPointedScale(Entity entity, Float pointedScale) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setPointedScale(pointedScale);
        EntityMetaPacket.setTag(entityTagPacket);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }
}
