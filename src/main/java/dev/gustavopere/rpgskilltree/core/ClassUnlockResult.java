package dev.gustavopere.rpgskilltree.core;

import java.util.Set;

public record ClassUnlockResult(
    boolean unlockable,
    boolean triadRequirementsMet,
    int bridgeCost,
    int missingBridgePoints,
    Set<ProgressionDomain> missingDomains
) {
    public ClassUnlockResult {
        if (bridgeCost < 0 || missingBridgePoints < 0) throw new IllegalArgumentException("costs must be >= 0");
        missingDomains = Set.copyOf(missingDomains);
    }

    /** Semantic alias used by confluence UI and diagnostics. */
    public Set<ProgressionDomain> missingCompletedDomains() {
        return missingDomains;
    }
}
