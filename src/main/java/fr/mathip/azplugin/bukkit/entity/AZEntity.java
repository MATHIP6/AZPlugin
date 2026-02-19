package fr.mathip.azplugin.bukkit.entity;

import fr.mathip.azplugin.bukkit.AZManager;
import fr.mathip.azplugin.bukkit.entity.appearance.AZEntityModel;
import fr.mathip.azplugin.bukkit.entity.appearance.AZEntityScale;
import fr.mathip.azplugin.bukkit.entity.appearance.AZEntityTag;
import lombok.Data;
import pactify.client.api.plprotocol.metadata.ImmutablePactifyModelMetadata;
import pactify.client.api.plsp.packet.client.PLSPPacketAbstractMeta;
import pactify.client.api.plsp.packet.client.PLSPPacketEntityMeta;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@Data
public class AZEntity {
    private Entity entity;
    private AZEntityScale scale;
    private AZEntityModel model;
    private AZEntityTag tag;
    private AZEntityTag subTag;
    private AZEntityTag supTag;
    private float opacity;

    // TODO add a getTrackers method instead of get all onlin players
    // public void getTrackers() {
    // ((CraftEntity)entity).getHandle().track
    // }

    public AZEntity(Entity entity) {
        this.entity = entity;
        this.scale = new AZEntityScale();
        this.model = new AZEntityModel();
        this.tag = new AZEntityTag();
        this.subTag = new AZEntityTag();
        this.supTag = new AZEntityTag();
        this.opacity = -1.0F;
    }

    protected PLSPPacketAbstractMeta createMetadataPacket() {
        PLSPPacketEntityMeta entityMeta = new PLSPPacketEntityMeta(entity.getEntityId());
        entityMeta.setScale(scale.toPacMetadata());
        entityMeta.setModel(model.toPacMetadata());
        entityMeta.setTag(tag.toPacMetadata());
        entityMeta.setSupTag(supTag.toPacMetadata());
        entityMeta.setSubTag(subTag.toPacMetadata());
        entityMeta.setOpacity(opacity);
        return entityMeta;
    }

    public void flush(Player player) {
        AZManager.sendPLSPMessage(player, createMetadataPacket());
    }

    public void flush() {
        Bukkit.getOnlinePlayers().forEach(player -> flush(player));
    }
}
