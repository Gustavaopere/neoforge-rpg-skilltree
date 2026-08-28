package dev.gustavopere.rpgskilltree.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class LegacyNodeAllocationMigrationTest {
    public static void main(String[] args) {
        declaredBasisCreatesPricedV5Allocation();
        aliasAndQuarantineAreExplicitMigrationChoices();
        missingBasisPreservesLegacyRankAsUnresolved();
        ambiguousTargetsAndInvalidBasisFailClosed();
        migrationIsDeterministicAndDoesNotMutateLegacyInput();
        System.out.println("LegacyNodeAllocationMigrationTest: PASS");
    }

    private static void declaredBasisCreatesPricedV5Allocation() {
        PassiveNodeProgress legacy = PassiveNodeProgress.of(Map.of("rpgskilltree:martial_001", 3));
        LegacyNodeAllocationMigrationBasis basis = new LegacyNodeAllocationMigrationBasis(
            "rpgskilltree:martial_001",
            4L,
            "rpgskilltree:core_progression",
            "rpgskilltree:main_tree",
            17L,
            LegacyNodeMigrationDisposition.ACTIVE,
            null
        );

        LegacyNodeAllocationMigrationResult result = LegacyNodeAllocationMigrationService.migrate(
            legacy,
            Map.of("rpgskilltree:martial_001", basis)
        );

        NodeAllocation migrated = result.allocations().active("rpgskilltree:martial_001").orElseThrow();
        eq(3, migrated.rank());
        eq(12L, migrated.paidCost());
        eq(1, migrated.batches().size());
        NodeAllocationBatch batch = migrated.batches().getFirst();
        eq(3, batch.rankCount());
        eq(4L, batch.paidCostPerRank());
        eq("rpgskilltree:core_progression", batch.currencyId());
        eq("rpgskilltree:main_tree", batch.sourceTreeId());
        eq(ProgressionProvenanceId.of("rpgskilltree:legacy_migration_inferred"), batch.provenance());
        eq(17L, batch.rulesVersion());
        eq(true, result.unresolvedLegacyRanks().isEmpty());
        eq(true, result.complete());
    }

    private static void aliasAndQuarantineAreExplicitMigrationChoices() {
        PassiveNodeProgress legacy = PassiveNodeProgress.of(Map.of("old_unscoped_node", 2));
        LegacyNodeAllocationMigrationBasis basis = new LegacyNodeAllocationMigrationBasis(
            "rpgskilltree:renamed_node",
            6L,
            "rpgskilltree:core_progression",
            "rpgskilltree:main_tree",
            21L,
            LegacyNodeMigrationDisposition.QUARANTINE,
            "removed_or_unavailable_definition"
        );

        LegacyNodeAllocationMigrationResult result = LegacyNodeAllocationMigrationService.migrate(
            legacy,
            Map.of("old_unscoped_node", basis)
        );

        eq(true, result.allocations().active("rpgskilltree:renamed_node").isEmpty());
        QuarantinedNodeAllocation quarantined = result.allocations()
            .quarantined("rpgskilltree:renamed_node")
            .orElseThrow();
        eq(2, quarantined.allocation().rank());
        eq(12L, quarantined.allocation().paidCost());
        eq("removed_or_unavailable_definition", quarantined.reason());
        eq(21L, quarantined.quarantinedAtRulesVersion());
        eq(true, result.complete());
    }

    private static void missingBasisPreservesLegacyRankAsUnresolved() {
        PassiveNodeProgress legacy = PassiveNodeProgress.of(Map.of(
            "rpgskilltree:known", 1,
            "legacy_unknown_raw_id", 4
        ));
        LegacyNodeAllocationMigrationBasis knownBasis = new LegacyNodeAllocationMigrationBasis(
            "rpgskilltree:known",
            2L,
            "rpgskilltree:core_progression",
            "rpgskilltree:main_tree",
            5L,
            LegacyNodeMigrationDisposition.ACTIVE,
            null
        );

        LegacyNodeAllocationMigrationResult result = LegacyNodeAllocationMigrationService.migrate(
            legacy,
            Map.of("rpgskilltree:known", knownBasis)
        );

        eq(1, result.allocations().active("rpgskilltree:known").orElseThrow().rank());
        eq(Map.of("legacy_unknown_raw_id", 4), result.unresolvedLegacyRanks());
        eq(false, result.complete());
        expect(UnsupportedOperationException.class, () -> result.unresolvedLegacyRanks().put("x", 1));
    }

    private static void ambiguousTargetsAndInvalidBasisFailClosed() {
        PassiveNodeProgress legacy = PassiveNodeProgress.of(Map.of("legacy_a", 1, "legacy_b", 1));
        LegacyNodeAllocationMigrationBasis first = new LegacyNodeAllocationMigrationBasis(
            "rpgskilltree:same_target",
            1L,
            "rpgskilltree:core_progression",
            "rpgskilltree:main_tree",
            1L,
            LegacyNodeMigrationDisposition.ACTIVE,
            null
        );
        LegacyNodeAllocationMigrationBasis second = new LegacyNodeAllocationMigrationBasis(
            "rpgskilltree:same_target",
            2L,
            "rpgskilltree:core_progression",
            "rpgskilltree:main_tree",
            2L,
            LegacyNodeMigrationDisposition.ACTIVE,
            null
        );

        expect(IllegalArgumentException.class, () -> LegacyNodeAllocationMigrationService.migrate(
            legacy,
            Map.of("legacy_a", first, "legacy_b", second)
        ));
        expect(IllegalArgumentException.class, () -> new LegacyNodeAllocationMigrationBasis(
            "not_namespaced",
            1L,
            "rpgskilltree:core_progression",
            "rpgskilltree:main_tree",
            1L,
            LegacyNodeMigrationDisposition.ACTIVE,
            null
        ));
        expect(IllegalArgumentException.class, () -> new LegacyNodeAllocationMigrationBasis(
            "rpgskilltree:target",
            -1L,
            "rpgskilltree:core_progression",
            "rpgskilltree:main_tree",
            1L,
            LegacyNodeMigrationDisposition.ACTIVE,
            null
        ));
        expect(IllegalArgumentException.class, () -> new LegacyNodeAllocationMigrationBasis(
            "rpgskilltree:target",
            1L,
            "rpgskilltree:core_progression",
            "rpgskilltree:main_tree",
            1L,
            LegacyNodeMigrationDisposition.QUARANTINE,
            null
        ));
        expect(IllegalArgumentException.class, () -> new LegacyNodeAllocationMigrationBasis(
            "rpgskilltree:target",
            1L,
            "rpgskilltree:core_progression",
            "rpgskilltree:main_tree",
            1L,
            LegacyNodeMigrationDisposition.ACTIVE,
            "unexpected_reason"
        ));
    }

    private static void migrationIsDeterministicAndDoesNotMutateLegacyInput() {
        LinkedHashMap<String, Integer> ranks = new LinkedHashMap<>();
        ranks.put("legacy_b", 2);
        ranks.put("legacy_a", 1);
        PassiveNodeProgress legacy = PassiveNodeProgress.of(ranks);

        Map<String, LegacyNodeAllocationMigrationBasis> bases = Map.of(
            "legacy_a",
            new LegacyNodeAllocationMigrationBasis(
                "rpgskilltree:a", 3L, "rpgskilltree:core_progression", "rpgskilltree:main_tree", 9L,
                LegacyNodeMigrationDisposition.ACTIVE, null
            ),
            "legacy_b",
            new LegacyNodeAllocationMigrationBasis(
                "rpgskilltree:b", 5L, "rpgskilltree:core_progression", "rpgskilltree:main_tree", 9L,
                LegacyNodeMigrationDisposition.ACTIVE, null
            )
        );

        LegacyNodeAllocationMigrationResult first = LegacyNodeAllocationMigrationService.migrate(legacy, bases);
        LegacyNodeAllocationMigrationResult second = LegacyNodeAllocationMigrationService.migrate(legacy, bases);
        eq(first.allocations().activeAllocations(), second.allocations().activeAllocations());
        eq(first.allocations().quarantinedAllocations(), second.allocations().quarantinedAllocations());
        eq(first.unresolvedLegacyRanks(), second.unresolvedLegacyRanks());
        eq(Map.of("legacy_a", 1, "legacy_b", 2), legacy.ranks());
    }

    private static void expect(Class<? extends Throwable> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable thrown) {
            if (type.isInstance(thrown)) return;
            throw new AssertionError("expected " + type.getSimpleName() + " but got " + thrown, thrown);
        }
        throw new AssertionError("expected " + type.getSimpleName());
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("expected=" + expected + " actual=" + actual);
        }
    }
}
