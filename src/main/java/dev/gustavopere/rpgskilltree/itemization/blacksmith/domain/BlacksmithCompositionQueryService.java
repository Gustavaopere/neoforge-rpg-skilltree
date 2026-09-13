package dev.gustavopere.rpgskilltree.itemization.blacksmith.domain;

import java.util.Objects;

/** Query-only boundary for immutable Blacksmith composition projections. */
public final class BlacksmithCompositionQueryService {
    private BlacksmithCompositionQueryService() {}

    public static BlacksmithCompositionSnapshot snapshot(BlacksmithComposition composition) {
        Objects.requireNonNull(composition, "composition");
        return new BlacksmithCompositionSnapshot(
            composition.assemblyDefinitionId(),
            composition.parts(),
            composition.resolvedMaterialIds(),
            composition.workmanshipScore(),
            composition.workmanshipBand(),
            composition.processSummary(),
            composition.schemaVersion()
        );
    }
}
