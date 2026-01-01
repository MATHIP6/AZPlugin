package fr.mathip.azplugin.bukkit.entity.appearance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pactify.client.api.plprotocol.metadata.PactifyTagMetadata;
import pactify.client.api.plsp.packet.client.PLSPPacketAbstractMeta;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AZEntityTag {
    private String text;
    private Rarity rarity;
    private Float viewDistance;
    private Float opacity;
    private Float throughWallOpacity;
    private Float scale;
    private Visibility teamVisibility;
    private Float sneakViewDistance;
    private Float sneakOpacity;
    private Float sneakThroughWallOpacity;
    private Float sneakScale;
    private Visibility sneakTeamVisibility;
    private Float pointedOpacity;
    private Float pointedScale;
    private Visibility pointedTeamVisibility;

    public boolean isNull() {
        return (text == null &&
                rarity == null &&
                viewDistance == null &&
                opacity == null &&
                throughWallOpacity == null &&
                scale == null &&
                teamVisibility == null &&
                sneakViewDistance == null &&
                sneakOpacity == null &&
                sneakThroughWallOpacity == null &&
                sneakScale == null &&
                sneakTeamVisibility == null &&
                pointedOpacity == null &&
                pointedScale == null &&
                pointedTeamVisibility == null);
    }

    public PactifyTagMetadata toPacMetadata() {
        if (isNull()) {
            return PLSPPacketAbstractMeta.DEFAULT_TAG;
        }
        PactifyTagMetadata tag = new PactifyTagMetadata();
        tag.setText(text);
        tag.setRarity(PactifyTagMetadata.Rarity.LEGENDARY);
        //tag.setRarity(Rarity.convertNameTagRarity(rarity));
        tag.setDistance(convertFloat(viewDistance));
        tag.setOpacity(convertFloat(opacity));
        tag.setThroughWallOpacity(convertFloat(throughWallOpacity));
        tag.setScale(convertFloat(scale));
        tag.setTeamVisibility(Visibility.convertNameTagVisibility(teamVisibility));
        tag.setSneakDistance(convertFloat(sneakViewDistance));
        tag.setSneakOpacity(convertFloat(sneakOpacity));
        tag.setSneakThroughWallOpacity(convertFloat(sneakThroughWallOpacity));
        tag.setSneakScale(convertFloat(sneakScale));
        tag.setSneakTeamVisibility(Visibility.convertNameTagVisibility(sneakTeamVisibility));
        tag.setPointedOpacity(convertFloat(pointedOpacity));
        tag.setPointedScale(convertFloat(pointedScale));
        tag.setPointedTeamVisibility(Visibility.convertNameTagVisibility(pointedTeamVisibility));
        return tag;
    }

    private static float convertFloat(Float value) {
        return value == null ? -1.0F : value;
    }

    public enum Visibility {
        ALWAYS,
        NEVER,
        HIDE_FOR_OTHER_TEAMS,
        HIDE_FOR_OWN_TEAM;

        public static int convertNameTagVisibility(Visibility teamVisibility) {
            if (teamVisibility == null) {
                return 0;
            }
            switch (teamVisibility) {
                case ALWAYS:
                    return 1;
                case NEVER:
                    return 2;
                case HIDE_FOR_OTHER_TEAMS:
                    return 3;
                case HIDE_FOR_OWN_TEAM:
                    return 4;
                default:
                    throw new IllegalArgumentException("Unknown visibility: " + teamVisibility);
            }
        }
    }

    public enum Rarity {
        NONE,
        UNCOMMON,
        RARE,
        EPIC,
        LEGENDARY,
        MYTHIC;

        public static PactifyTagMetadata.Rarity convertNameTagRarity(Rarity rarity) {
            if (rarity == null) {
                return PactifyTagMetadata.Rarity.AUTO;
            }
            switch (rarity) {
                case NONE:
                    return PactifyTagMetadata.Rarity.NONE;
                case UNCOMMON:
                    return PactifyTagMetadata.Rarity.UNCOMMON;
                case RARE:
                    return PactifyTagMetadata.Rarity.RARE;
                case EPIC:
                    return PactifyTagMetadata.Rarity.EPIC;
                case LEGENDARY:
                    return PactifyTagMetadata.Rarity.LEGENDARY;
                case MYTHIC:
                    return PactifyTagMetadata.Rarity.MYTHIC;
                default:
                    throw new IllegalArgumentException("Unknown rarity: " + rarity);
            }
        }

    }

    public enum Slot {
        MAIN,
        SUP,
        SUB,
    }
}
