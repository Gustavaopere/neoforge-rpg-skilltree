package dev.gustavopere.rpgskilltree.itemization.blacksmith.domain;

import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Read-only physical projection for UI/integrations. Contains no RPG itemization state. */
public record BlacksmithCompositionSnapshot(
    ResourceLocation assemblyDefinitionId,
    Map<String, BlacksmithPartState> parts,
    List<ResourceLocation> resolvedMaterialIds,
    double workmanshipScore,
    WorkmanshipBand workmanshipBand,
    Set<ProcessState> processSummary,
    int schemaVersion
) {
    public BlacksmithCompositionSnapshot {
        Objects.requireNonNull(assemblyDefinitionId, "assemblyDefinitionId");
        parts = Collections.unmodifiableMap(new LinkedHashMap<>(Objects.requireNonNull(parts, "parts")));
        resolvedMaterialIds = List.copyOf(Objects.requireNonNull(resolvedMaterialIds, "resolvedMaterialIds"));
        if (!Double.isFinite(workmanshipScore) || workmanshipScore < 0.0D) {
            throw new IllegalArgumentException("workmanshipScore must be finite and >= 0");
        }
        Objects.requireNonNull(workmanshipBand, "workmanshipBand");
        processSummary = Collections.unmodifiableSet(
            new LinkedHashSet<>(Objects.requireNonNull(processSummary, "processSummary"))
        );
        if (schemaVersion < 1) {
            throw new IllegalArgumentException("schemaVersion must be >= 1");
        }
    }
}
