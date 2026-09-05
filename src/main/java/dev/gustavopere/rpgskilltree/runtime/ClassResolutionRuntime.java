package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CanonicalClassResolutionProjection;
import dev.gustavopere.rpgskilltree.core.ClassResolutionQueryService;
import dev.gustavopere.rpgskilltree.core.EmergentClassResolution;
import dev.gustavopere.rpgskilltree.core.InvestmentState;
import dev.gustavopere.rpgskilltree.core.MasteryInvestmentMetadata;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.runtime.data.ArchetypeCatalog;
import dev.gustavopere.rpgskilltree.runtime.data.ClassInvestmentMetadataCatalog;
import dev.gustavopere.rpgskilltree.runtime.data.MasteryInvestmentMetadataCatalog;
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
     * Projects the canonical progression snapshot through the current skill and Mastery metadata.
     * Both contribution sources are published runtime catalogs; callers do not infer or supply
     * Mastery-to-domain semantics on the canonical path.
     */
    public static CanonicalClassResolutionProjection resolveCanonical(ProgressionState state) {
        return resolveCanonical(state, MasteryInvestmentMetadataCatalog.current());
    }

    /**
     * Compatibility/testing boundary for explicitly supplied Mastery metadata.
     * Production callers should prefer {@link #resolveCanonical(ProgressionState)} so the
     * datapack-published Mastery catalog remains the canonical runtime source.
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
