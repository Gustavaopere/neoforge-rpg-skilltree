package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

public record NodeDisplayState(
    String nodeId,
    int rank,
    int maxRank,
    int costPerRank,
    boolean learned,
    boolean canPurchase,
    boolean canRespec,
    boolean finalTriadNode
) {
    public NodeDisplayState {
        Objects.requireNonNull(nodeId);
        if (rank < 0 || maxRank <= 0 || rank > maxRank) throw new IllegalArgumentException("invalid rank state");
        if (costPerRank <= 0) throw new IllegalArgumentException("costPerRank must be positive");
    }
}
