package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/**
 * Compact persisted acquisition facts for one or more adjacent node ranks bought under
 * identical economic and provenance terms.
 */
public record NodeAllocationBatch(
    int rankCount,
    long paidCostPerRank,
    String currencyId,
    String sourceTreeId,
    ProgressionProvenanceId provenance,
    long rulesVersion
) {
    public NodeAllocationBatch {
        if (rankCount <= 0) throw new IllegalArgumentException("rankCount must be positive");
        if (paidCostPerRank < 0L) throw new IllegalArgumentException("paidCostPerRank must be non-negative");
        currencyId = ProgressionProvenanceId.requireNamespacedId(currencyId, "currency id");
        sourceTreeId = ProgressionProvenanceId.requireNamespacedId(sourceTreeId, "source tree id");
        Objects.requireNonNull(provenance, "provenance");
        if (rulesVersion <= 0L) throw new IllegalArgumentException("rulesVersion must be positive");
        Math.multiplyExact(paidCostPerRank, (long) rankCount);
    }

    public long totalPaidCost() {
        return Math.multiplyExact(paidCostPerRank, (long) rankCount);
    }

    public boolean sameTerms(NodeAllocationBatch other) {
        Objects.requireNonNull(other, "other");
        return paidCostPerRank == other.paidCostPerRank
            && currencyId.equals(other.currencyId)
            && sourceTreeId.equals(other.sourceTreeId)
            && provenance.equals(other.provenance)
            && rulesVersion == other.rulesVersion;
    }

    public NodeAllocationBatch withAdditionalRanks(int additionalRanks) {
        if (additionalRanks <= 0) throw new IllegalArgumentException("additionalRanks must be positive");
        return new NodeAllocationBatch(
            Math.addExact(rankCount, additionalRanks),
            paidCostPerRank,
            currencyId,
            sourceTreeId,
            provenance,
            rulesVersion
        );
    }

    public NodeAllocationBatch oneRank() {
        return new NodeAllocationBatch(
            1,
            paidCostPerRank,
            currencyId,
            sourceTreeId,
            provenance,
            rulesVersion
        );
    }
}
