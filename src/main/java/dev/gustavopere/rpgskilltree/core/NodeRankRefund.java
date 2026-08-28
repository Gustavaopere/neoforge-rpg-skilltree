package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;

/** Exact historical one-rank refund plus the remaining allocation, if any. */
public record NodeRankRefund(
    Optional<NodeAllocation> remaining,
    NodeAllocationBatch refundedRank
) {
    public NodeRankRefund {
        Objects.requireNonNull(remaining, "remaining");
        Objects.requireNonNull(refundedRank, "refundedRank");
        if (refundedRank.rankCount() != 1) {
            throw new IllegalArgumentException("refundedRank must describe exactly one rank");
        }
    }
}
