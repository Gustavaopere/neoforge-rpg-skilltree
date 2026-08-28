package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Objects;

public final class ProgressionStateV5AllocationFoundationTest {
    public static void main(String[] args) {
        acquisitionBatchesPreserveExactHistoricalTerms();
        refundIsLifoAndUsesHistoricalCost();
        quarantinePreservesUnknownAllocationHistory();
        invalidOrUnsafeEconomicFactsFailClosed();
        System.out.println("ProgressionStateV5AllocationFoundationTest: PASS");
    }

    private static void acquisitionBatchesPreserveExactHistoricalTerms() {
        ProgressionProvenanceId purchase = ProgressionProvenanceId.of("rpgskilltree:purchase");
        NodeAllocationBatch originalTerms = new NodeAllocationBatch(
            2,
            3L,
            "rpgskilltree:core_progression",
            "rpgskilltree:main_tree",
            purchase,
            11L
        );
        NodeAllocation allocation = NodeAllocation.of(
            "rpgskilltree:martial_001",
            List.of(originalTerms)
        );

        eq(2, allocation.rank());
        eq(6L, allocation.paidCost());
        eq(1, allocation.batches().size());

        allocation = allocation.acquire(new NodeAllocationBatch(
            1,
            3L,
            "rpgskilltree:core_progression",
            "rpgskilltree:main_tree",
            purchase,
            11L
        ));
        eq(3, allocation.rank());
        eq(9L, allocation.paidCost());
        eq(1, allocation.batches().size());
        eq(3, allocation.batches().getFirst().rankCount());

        allocation = allocation.acquire(new NodeAllocationBatch(
            1,
            5L,
            "rpgskilltree:core_progression",
            "rpgskilltree:main_tree",
            purchase,
            12L
        ));
        eq(4, allocation.rank());
        eq(14L, allocation.paidCost());
        eq(2, allocation.batches().size());
        eq(11L, allocation.batches().getFirst().rulesVersion());
        eq(12L, allocation.batches().getLast().rulesVersion());
    }

    private static void refundIsLifoAndUsesHistoricalCost() {
        ProgressionProvenanceId purchase = ProgressionProvenanceId.of("rpgskilltree:purchase");
        NodeAllocation allocation = NodeAllocation.of(
            "rpgskilltree:arcane_001",
            List.of(
                new NodeAllocationBatch(
                    2,
                    2L,
                    "rpgskilltree:core_progression",
                    "rpgskilltree:main_tree",
                    purchase,
                    5L
                ),
                new NodeAllocationBatch(
                    1,
                    9L,
                    "rpgskilltree:core_progression",
                    "rpgskilltree:main_tree",
                    purchase,
                    8L
                )
            )
        );

        NodeRankRefund first = allocation.refundLastRank();
        eq(9L, first.refundedRank().paidCostPerRank());
        eq(8L, first.refundedRank().rulesVersion());
        eq(2, first.remaining().orElseThrow().rank());
        eq(4L, first.remaining().orElseThrow().paidCost());

        NodeRankRefund second = first.remaining().orElseThrow().refundLastRank();
        eq(2L, second.refundedRank().paidCostPerRank());
        eq(5L, second.refundedRank().rulesVersion());
        eq(1, second.remaining().orElseThrow().rank());

        NodeRankRefund third = second.remaining().orElseThrow().refundLastRank();
        eq(2L, third.refundedRank().paidCostPerRank());
        eq(true, third.remaining().isEmpty());
    }

    private static void quarantinePreservesUnknownAllocationHistory() {
        NodeAllocation allocation = NodeAllocation.of(
            "future_provider:removed_node",
            List.of(new NodeAllocationBatch(
                3,
                7L,
                "rpgskilltree:core_progression",
                "future_provider:specialist_tree",
                ProgressionProvenanceId.of("rpgskilltree:legacy_migration"),
                19L
            ))
        );
        PersistedNodeAllocations active = PersistedNodeAllocations.empty().withActive(allocation);

        PersistedNodeAllocations quarantined = active.quarantine(
            allocation.nodeId(),
            "missing_definition",
            22L
        );
        eq(true, quarantined.active(allocation.nodeId()).isEmpty());
        QuarantinedNodeAllocation retained = quarantined.quarantined(allocation.nodeId()).orElseThrow();
        eq(allocation, retained.allocation());
        eq("missing_definition", retained.reason());
        eq(22L, retained.quarantinedAtRulesVersion());
        eq(21L, retained.allocation().paidCost());

        PersistedNodeAllocations restored = quarantined.restore(allocation.nodeId());
        eq(allocation, restored.active(allocation.nodeId()).orElseThrow());
        eq(true, restored.quarantined(allocation.nodeId()).isEmpty());
    }

    private static void invalidOrUnsafeEconomicFactsFailClosed() {
        ProgressionProvenanceId purchase = ProgressionProvenanceId.of("rpgskilltree:purchase");

        expect(IllegalArgumentException.class, () -> ProgressionProvenanceId.of("purchase"));
        expect(IllegalArgumentException.class, () -> ProgressionProvenanceId.of("RPG:purchase"));
        expect(IllegalArgumentException.class, () -> new NodeAllocationBatch(
            0, 1L, "rpgskilltree:core_progression", "rpgskilltree:main_tree", purchase, 1L
        ));
        expect(IllegalArgumentException.class, () -> new NodeAllocationBatch(
            1, -1L, "rpgskilltree:core_progression", "rpgskilltree:main_tree", purchase, 1L
        ));
        expect(IllegalArgumentException.class, () -> new NodeAllocationBatch(
            1, 1L, "core_progression", "rpgskilltree:main_tree", purchase, 1L
        ));
        expect(IllegalArgumentException.class, () -> new NodeAllocationBatch(
            1, 1L, "rpgskilltree:core_progression", "main_tree", purchase, 1L
        ));
        expect(IllegalArgumentException.class, () -> new NodeAllocationBatch(
            1, 1L, "rpgskilltree:core_progression", "rpgskilltree:main_tree", purchase, 0L
        ));
        expect(ArithmeticException.class, () -> new NodeAllocationBatch(
            2,
            Long.MAX_VALUE,
            "rpgskilltree:core_progression",
            "rpgskilltree:main_tree",
            purchase,
            1L
        ));
        expect(IllegalArgumentException.class, () -> NodeAllocation.of("not_namespaced", List.of(
            new NodeAllocationBatch(
                1, 1L, "rpgskilltree:core_progression", "rpgskilltree:main_tree", purchase, 1L
            )
        )));
        expect(IllegalArgumentException.class, () -> NodeAllocation.of("rpgskilltree:empty", List.of()));
        expect(IllegalArgumentException.class, () -> PersistedNodeAllocations.empty().quarantine(
            "rpgskilltree:missing", "missing_definition", 1L
        ));
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
