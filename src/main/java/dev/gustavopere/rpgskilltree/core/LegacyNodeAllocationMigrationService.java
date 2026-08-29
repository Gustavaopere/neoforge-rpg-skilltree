package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Pure rule-aware migration from legacy passive-node ranks to v5 priced allocations.
 * Unknown legacy facts are preserved rather than guessed or discarded.
 */
public final class LegacyNodeAllocationMigrationService {
    private static final ProgressionProvenanceId LEGACY_INFERRED_PROVENANCE =
        ProgressionProvenanceId.of("rpgskilltree:legacy_migration_inferred");

    private LegacyNodeAllocationMigrationService() {}

    public static LegacyNodeAllocationMigrationResult migrate(
        PassiveNodeProgress legacy,
        Map<String, LegacyNodeAllocationMigrationBasis> basesByLegacyNodeId
    ) {
        Objects.requireNonNull(legacy, "legacy");
        Objects.requireNonNull(basesByLegacyNodeId, "basesByLegacyNodeId");

        for (Map.Entry<String, LegacyNodeAllocationMigrationBasis> entry : basesByLegacyNodeId.entrySet()) {
            String rawId = Objects.requireNonNull(entry.getKey(), "legacy migration source id");
            if (rawId.isBlank()) throw new IllegalArgumentException("legacy migration source id must not be blank");
            Objects.requireNonNull(entry.getValue(), "legacy migration basis");
        }

        HashMap<String, NodeAllocation> active = new HashMap<>();
        HashMap<String, QuarantinedNodeAllocation> quarantined = new HashMap<>();
        HashMap<String, Integer> unresolved = new HashMap<>();
        Set<String> claimedTargets = new HashSet<>();

        List<Map.Entry<String, Integer>> legacyRanks = new ArrayList<>(legacy.ranks().entrySet());
        legacyRanks.sort(Map.Entry.comparingByKey());

        for (Map.Entry<String, Integer> entry : legacyRanks) {
            String rawLegacyId = entry.getKey();
            int rank = entry.getValue();
            LegacyNodeAllocationMigrationBasis basis = basesByLegacyNodeId.get(rawLegacyId);
            if (basis == null) {
                unresolved.put(rawLegacyId, rank);
                continue;
            }

            String targetNodeId = basis.targetNodeId();
            if (!claimedTargets.add(targetNodeId)) {
                throw new IllegalArgumentException(
                    "multiple legacy node ids resolve to the same v5 target: " + targetNodeId
                );
            }

            NodeAllocationBatch batch = new NodeAllocationBatch(
                rank,
                basis.inferredPaidCostPerRank(),
                basis.currencyId(),
                basis.sourceTreeId(),
                LEGACY_INFERRED_PROVENANCE,
                basis.rulesVersion()
            );
            NodeAllocation allocation = NodeAllocation.of(targetNodeId, List.of(batch));

            switch (basis.disposition()) {
                case ACTIVE -> active.put(targetNodeId, allocation);
                case QUARANTINE -> quarantined.put(
                    targetNodeId,
                    new QuarantinedNodeAllocation(
                        allocation,
                        basis.quarantineReason(),
                        basis.rulesVersion()
                    )
                );
            }
        }

        return new LegacyNodeAllocationMigrationResult(
            PersistedNodeAllocations.of(active, quarantined),
            unresolved
        );
    }
}
