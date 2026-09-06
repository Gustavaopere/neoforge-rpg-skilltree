package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Set;

/** Read-only result of projecting canonical progression into emergent-class investment state. */
public record CanonicalInvestmentProjection(
    InvestmentState investmentState,
    Set<String> missingNodeIds
) {
    public CanonicalInvestmentProjection {
        Objects.requireNonNull(investmentState, "investmentState");
        missingNodeIds = Set.copyOf(Objects.requireNonNull(missingNodeIds, "missingNodeIds"));
    }

    public boolean complete() {
        return missingNodeIds.isEmpty();
    }
}
