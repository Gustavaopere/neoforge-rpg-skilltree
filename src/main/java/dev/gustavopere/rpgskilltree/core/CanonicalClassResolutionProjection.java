package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/** Fail-closed result for resolving emergent archetypes from canonical progression. */
public record CanonicalClassResolutionProjection(
    InvestmentState investmentState,
    Set<String> missingNodeIds,
    Optional<EmergentClassResolution> resolution
) {
    public CanonicalClassResolutionProjection {
        Objects.requireNonNull(investmentState, "investmentState");
        missingNodeIds = Set.copyOf(Objects.requireNonNull(missingNodeIds, "missingNodeIds"));
        Objects.requireNonNull(resolution, "resolution");
        if (!missingNodeIds.isEmpty() && resolution.isPresent()) {
            throw new IllegalArgumentException("incomplete investment projection cannot expose class resolution");
        }
    }

    public boolean complete() {
        return missingNodeIds.isEmpty();
    }
}
