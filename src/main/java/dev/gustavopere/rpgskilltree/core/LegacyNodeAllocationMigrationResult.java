package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Pure migration result: migrated v5 allocations plus raw legacy ranks that still lack an explicit basis. */
public record LegacyNodeAllocationMigrationResult(
    PersistedNodeAllocations allocations,
    Map<String, Integer> unresolvedLegacyRanks
) {
    public LegacyNodeAllocationMigrationResult {
        Objects.requireNonNull(allocations, "allocations");
        Objects.requireNonNull(unresolvedLegacyRanks, "unresolvedLegacyRanks");
        HashMap<String, Integer> copy = new HashMap<>();
        unresolvedLegacyRanks.forEach((rawId, rank) -> {
            Objects.requireNonNull(rawId, "unresolved legacy node id");
            Objects.requireNonNull(rank, "unresolved legacy node rank");
            if (rawId.isBlank()) throw new IllegalArgumentException("unresolved legacy node id must not be blank");
            if (rank <= 0) throw new IllegalArgumentException("unresolved legacy node rank must be positive");
            if (copy.put(rawId, rank) != null) {
                throw new IllegalArgumentException("duplicate unresolved legacy node id: " + rawId);
            }
        });
        unresolvedLegacyRanks = Map.copyOf(copy);
    }

    public boolean complete() {
        return unresolvedLegacyRanks.isEmpty();
    }
}
