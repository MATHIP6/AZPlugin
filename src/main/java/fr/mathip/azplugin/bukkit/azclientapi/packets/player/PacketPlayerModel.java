package fr.mathip.azplugin.bukkit.azclientapi.packets.player;

import fr.mathip.azplugin.bukkit.Main;
import fr.mathip.azplugin.bukkit.azclientapi.handlers.PLSPPlayerModel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pactify.client.api.plprotocol.metadata.PactifyModelMetadata;
import pactify.client.api.plsp.packet.client.PLSPPacketPlayerMeta;

public class PacketPlayerModel {
    private static final fr.mathip.azplugin.bukkit.azclientapi.AZManager AZManager = Main.getInstance().getAZManager();

    public static void reset(Player player) {
        PLSPPacketPlayerMeta PlayerMetaPacket = new PLSPPacketPlayerMeta(player.getUniqueId());
        PactifyModelMetadata model = new PactifyModelMetadata(-1);
        PlayerMetaPacket.setModel(model);

        AZManager.sendPLSPMessage(player, PlayerMetaPacket);
        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, PlayerMetaPacket);
        }
    }

    public static void setModel(Player player, PLSPPlayerModel entity, Player target) {
        PLSPPacketPlayerMeta PlayerMetaPacket = new PLSPPacketPlayerMeta(player.getUniqueId());
        PactifyModelMetadata model = new PactifyModelMetadata();
        model.setId(entity.getId());
        PlayerMetaPacket.setModel(model);

        AZManager.sendPLSPMessage(player, PlayerMetaPacket);
        AZManager.sendPLSPMessage(target, PlayerMetaPacket);
    }

    public static void setModel(Player player, PLSPPlayerModel entity, Float OffsetX, Float OffsetY, Float OffsetZ) {
        PLSPPacketPlayerMeta PlayerMetaPacket = new PLSPPacketPlayerMeta(player.getUniqueId());
        PactifyModelMetadata model = new PactifyModelMetadata();
        model.setId(entity.getId());
        model.setOffsetX(OffsetX);
        model.setOffsetY(OffsetY);
        model.setOffsetZ(OffsetZ);
        PlayerMetaPacket.setModel(model);

        AZManager.sendPLSPMessage(player, PlayerMetaPacket);
        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, PlayerMetaPacket);
        }
    }

    public static void setModel(Player player, PLSPPlayerModel entity, Float EyeHeightStand, Float EyeHeightElytra, Float EyeHeightSleep, Float EyeHeightSneak) {
        PLSPPacketPlayerMeta PlayerMetaPacket = new PLSPPacketPlayerMeta(player.getUniqueId());
        PactifyModelMetadata model = new PactifyModelMetadata();
        model.setId(entity.getId());
        model.setEyeHeightStand(EyeHeightStand);
        model.setEyeHeightElytra(EyeHeightElytra);
        model.setEyeHeightSleep(EyeHeightSleep);
        model.setEyeHeightSneak(EyeHeightSneak);
        PlayerMetaPacket.setModel(model);

        AZManager.sendPLSPMessage(player, PlayerMetaPacket);
        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, PlayerMetaPacket);
        }
    }

    public static void setModel(Player player, PLSPPlayerModel entity, Float OffsetX, Float OffsetY, Float OffsetZ, Float EyeHeightStand, Float EyeHeightElytra, Float EyeHeightSleep, Float EyeHeightSneak) {
        PLSPPacketPlayerMeta PlayerMetaPacket = new PLSPPacketPlayerMeta(player.getUniqueId());
        PactifyModelMetadata model = new PactifyModelMetadata();
        model.setId(entity.getId());
        model.setOffsetX(OffsetX);
        model.setOffsetY(OffsetY);
        model.setOffsetZ(OffsetZ);
        model.setEyeHeightStand(EyeHeightStand);
        model.setEyeHeightElytra(EyeHeightElytra);
        model.setEyeHeightSleep(EyeHeightSleep);
        model.setEyeHeightSneak(EyeHeightSneak);
        PlayerMetaPacket.setModel(model);

        AZManager.sendPLSPMessage(player, PlayerMetaPacket);
        for (Player players : Bukkit.getOnlinePlayers()) {
            AZManager.sendPLSPMessage(players, PlayerMetaPacket);
        }
    }
}
