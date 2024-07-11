package fr.mathip.azplugin.bukkit.azclientapi.packets.entity;

import fr.mathip.azplugin.bukkit.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import pactify.client.api.plprotocol.metadata.PactifyScaleMetadata;
import pactify.client.api.plsp.packet.client.PLSPPacketEntityMeta;

public class PacketEntityScale {
    private static final fr.mathip.azplugin.bukkit.azclientapi.AZManager AZManager = Main.getInstance().getAZManager();

    public static void reset(Entity entity) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyScaleMetadata scaleMetadata = new PactifyScaleMetadata();
        scaleMetadata.setDefined(false);
        EntityMetaPacket.setScale(scaleMetadata);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setScale(Entity entity, Float BboxH, Float BboxW, Float RenderD, Float RenderH, Float RenderW, Float Tags) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyScaleMetadata scaleMetadata = new PactifyScaleMetadata();
        scaleMetadata.setBboxH(BboxH);
        scaleMetadata.setBboxW(BboxW);
        scaleMetadata.setRenderD(RenderD);
        scaleMetadata.setRenderH(RenderH);
        scaleMetadata.setRenderW(RenderW);
        scaleMetadata.setTags(Tags);
        scaleMetadata.setDefined(true);
        EntityMetaPacket.setScale(scaleMetadata);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }

    public static void setScale(Entity entity, Float BboxH, Float BboxW, Float RenderD, Float RenderH, Float RenderW, Float ItemD, Float ItemH, Float ItemW, Float Tags) {
        PLSPPacketEntityMeta EntityMetaPacket = new PLSPPacketEntityMeta(entity.getEntityId());
        PactifyScaleMetadata scaleMetadata = new PactifyScaleMetadata();
        scaleMetadata.setBboxH(BboxH);
        scaleMetadata.setBboxW(BboxW);
        scaleMetadata.setRenderD(RenderD);
        scaleMetadata.setRenderH(RenderH);
        scaleMetadata.setRenderW(RenderW);
        scaleMetadata.setItemD(ItemD);
        scaleMetadata.setItemH(ItemH);
        scaleMetadata.setItemW(ItemW);
        scaleMetadata.setTags(Tags);
        scaleMetadata.setDefined(true);
        EntityMetaPacket.setScale(scaleMetadata);

        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, EntityMetaPacket);
        }
    }
}
