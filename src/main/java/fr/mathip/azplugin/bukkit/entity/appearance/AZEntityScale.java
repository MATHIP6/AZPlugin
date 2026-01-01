package fr.mathip.azplugin.bukkit.entity.appearance;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pactify.client.api.plprotocol.metadata.PactifyScaleMetadata;
import pactify.client.api.plsp.packet.client.PLSPPacketAbstractMeta;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public final class AZEntityScale {
    private Float bboxWidth;
    private Float bboxHeight;

    public AZEntityScale(Float scale) {
        this.bboxHeight = scale;
        this.bboxWidth = scale;
    }

    public boolean isNull() {
        return (bboxWidth == null &&
                bboxHeight == null);
    }

    public PactifyScaleMetadata toPacMetadata() {
        if (isNull()) {
            return PLSPPacketAbstractMeta.DEFAULT_SCALE;
        }
        PactifyScaleMetadata scaleMetadata = new PactifyScaleMetadata(bboxWidth, bboxHeight, bboxWidth, bboxWidth,
                bboxHeight, bboxWidth, bboxWidth, bboxHeight, bboxWidth);
        return scaleMetadata;
    }
}
