package fr.mathip.azplugin.bukkit.entity.appearance;

import org.bukkit.entity.EntityType;

import fr.mathip.azplugin.bukkit.handlers.PLSPPlayerModel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import pactify.client.api.plprotocol.metadata.ImmutablePactifyModelMetadata;
import pactify.client.api.plprotocol.metadata.PactifyModelMetadata;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class AZEntityModel {

    private final int modelId;

    public AZEntityModel() {
        this.modelId = -1;
    }

    public AZEntityModel(EntityType entityType) {
        this.modelId = entityType.getTypeId();
    }

    public AZEntityModel(PLSPPlayerModel modelType) {
        this.modelId = modelType.getId();
    }

    public boolean isNull() {
        return modelId == -1;
    }

    public PactifyModelMetadata toPacMetadata() {
        if (!isNull()) {
            PactifyModelMetadata modelMetadata = new PactifyModelMetadata(modelId);
            return modelMetadata;
        } else {
            return new ImmutablePactifyModelMetadata();
        }
    }
}
