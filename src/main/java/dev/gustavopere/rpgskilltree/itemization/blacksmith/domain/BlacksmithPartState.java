package dev.gustavopere.rpgskilltree.itemization.blacksmith.domain;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/** Persistable physical identity of one Blacksmith part stack. */
public record BlacksmithPartState(
    ResourceLocation partDefinitionId,
    ResourceLocation materialProfileId,
    int schemaVersion,
    ProcessState processState,
    double workmanshipInput,
    BlacksmithProvenance provenance
) {
    public BlacksmithPartState {
        Objects.requireNonNull(partDefinitionId, "partDefinitionId");
        Objects.requireNonNull(materialProfileId, "materialProfileId");
        if (schemaVersion < 1) {
            throw new IllegalArgumentException("schemaVersion must be >= 1");
        }
        Objects.requireNonNull(processState, "processState");
        if (!Double.isFinite(workmanshipInput) || workmanshipInput < 0.0D) {
            throw new IllegalArgumentException("workmanshipInput must be finite and >= 0");
        }
        Objects.requireNonNull(provenance, "provenance");
    }
}
