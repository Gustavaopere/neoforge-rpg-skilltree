package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CanonicalClassResolutionProjection;
import dev.gustavopere.rpgskilltree.core.ClassResolutionQueryService;
import dev.gustavopere.rpgskilltree.core.EmergentClassResolution;
import dev.gustavopere.rpgskilltree.core.InvestmentState;
import dev.gustavopere.rpgskilltree.core.MasteryInvestmentMetadata;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.runtime.data.ArchetypeCatalog;
import dev.gustavopere.rpgskilltree.runtime.data.ClassInvestmentMetadataCatalog;
import dev.gustavopere.rpgskilltree.runtime.data.SkillTreeDataCatalog;
import java.util.Collection;
import java.util.Objects;

/** Runtime read boundary for deterministic, data-driven emergent class resolution. */
public final class ClassResolutionRuntime {
    private ClassResolutionRuntime() {}

    public static EmergentClassResolution resolve(InvestmentState state) {
        Objects.requireNonNull(state, "state");
        return ClassResolutionQueryService.resolve(state, ArchetypeCatalog.definitions());
    }

    /**
     * Projects the canonical progression snapshot through the current skill metadata.
     * Mastery contribution thresholds stay explicit at the call boundary until Stage 04.03
     * defines their canonical class semantics; this method never invents them.
     */
    public static CanonicalClassResolutionProjection resolveCanonical(
        ProgressionState state,
        Collection<MasteryInvestmentMetadata> masteryMetadata
    ) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(masteryMetadata, "masteryMetadata");

        var treeSnapshot = SkillTreeDataCatalog.current();
        var metadataSnapshot = ClassInvestmentMetadataCatalog.current();
        if (treeSnapshot.revision() != metadataSnapshot.skillTreeRevision()) {
            throw new IllegalStateException(
                "class investment metadata revision does not match skill-tree revision"
            );
        }

        return ClassResolutionQueryService.resolveCanonical(
            state,
            metadataSnapshot.nodeMetadata(),
            masteryMetadata,
            ArchetypeCatalog.definitions()
        );
    }
}
