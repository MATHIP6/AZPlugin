package fr.mathip.azplugin.bukkit.azclientapi.packets.player;

import fr.mathip.azplugin.bukkit.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pactify.client.api.plprotocol.metadata.PactifyTagMetadata;
import pactify.client.api.plsp.packet.client.PLSPPacketEntityMeta;

public class PacketPlayerTag {
    private static final fr.mathip.azplugin.bukkit.azclientapi.AZManager AZManager = Main.getInstance().getAZManager();

    public static void setNameTag(Player player, String tag, Player target) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(player.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setText(ChatColor.translateAlternateColorCodes('&', tag));
        EntityMetaPacket.setTag(entityTagPacket);

        AZManager.sendPLSPMessage(player, EntityMetaPacket);
        AZManager.sendPLSPMessage(target, EntityMetaPacket);
    }

    public static void setSupTag(Player player, String tag, Player target) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(player.getEntityId());
        PactifyTagMetadata TagMetaData = new PactifyTagMetadata();
        TagMetaData.setText(ChatColor.translateAlternateColorCodes('&', tag));
        EntityMetaPacket.setSupTag(TagMetaData);

        AZManager.sendPLSPMessage(player, EntityMetaPacket);
        AZManager.sendPLSPMessage(target, EntityMetaPacket);
    }

    public static void setSubTag(Player player, String tag, Player target) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(player.getEntityId());
        PactifyTagMetadata TagMetaData = new PactifyTagMetadata();
        TagMetaData.setText(ChatColor.translateAlternateColorCodes('&', tag));
        EntityMetaPacket.setSubTag(TagMetaData);

        AZManager.sendPLSPMessage(player, EntityMetaPacket);
        AZManager.sendPLSPMessage(target, EntityMetaPacket);
    }

    public static void setScale(Player player, Float scale) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(player.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setScale(scale);
        EntityMetaPacket.setTag(entityTagPacket);

        AZManager.sendPLSPMessage(player, EntityMetaPacket);
        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setSneakScale(Player player, Float scale) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(player.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setSneakScale(scale);
        EntityMetaPacket.setTag(entityTagPacket);

        AZManager.sendPLSPMessage(player, EntityMetaPacket);
        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setDistance(Player player, Float distance) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(player.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setDistance(distance);
        EntityMetaPacket.setTag(entityTagPacket);

        AZManager.sendPLSPMessage(player, EntityMetaPacket);
        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setSneakDistance(Player player, Float distance) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(player.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setSneakDistance(distance);
        EntityMetaPacket.setTag(entityTagPacket);

        AZManager.sendPLSPMessage(player, EntityMetaPacket);
        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setTeamVisibility(Player player, Integer teamVisibility) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(player.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setTeamVisibility(teamVisibility);
        EntityMetaPacket.setTag(entityTagPacket);

        AZManager.sendPLSPMessage(player, EntityMetaPacket);
        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setSneakTeamVisibility(Player player, Integer teamVisibility) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(player.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setSneakTeamVisibility(teamVisibility);
        EntityMetaPacket.setTag(entityTagPacket);

        AZManager.sendPLSPMessage(player, EntityMetaPacket);
        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setPointedTeamVisibility(Player player, Integer pointedTeamVisibility) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(player.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setPointedTeamVisibility(pointedTeamVisibility);
        EntityMetaPacket.setTag(entityTagPacket);

        AZManager.sendPLSPMessage(player, EntityMetaPacket);
        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setPointedScale(Player player, Float pointedScale) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(player.getEntityId());
        PactifyTagMetadata entityTagPacket = new PactifyTagMetadata();
        entityTagPacket.setPointedScale(pointedScale);
        EntityMetaPacket.setTag(entityTagPacket);

        AZManager.sendPLSPMessage(player, EntityMetaPacket);
        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }
}
