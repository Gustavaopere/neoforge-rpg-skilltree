package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

public record NodePurchaseDefinition(
    String nodeId,
    int maxRank,
    int costPerRank,
    boolean startingPoint,
    ProgressionDomain finalTriadDomain,
    int finalTriadSlot
) {
    public NodePurchaseDefinition(String nodeId, int maxRank, int costPerRank, boolean startingPoint) {
        this(nodeId, maxRank, costPerRank, startingPoint, null, -1);
    }

    public NodePurchaseDefinition {
        Objects.requireNonNull(nodeId);
        if (nodeId.isBlank()) throw new IllegalArgumentException("nodeId must not be blank");
        if (maxRank <= 0) throw new IllegalArgumentException("maxRank must be positive");
        if (costPerRank <= 0) throw new IllegalArgumentException("costPerRank must be positive");
        if (finalTriadDomain == null && finalTriadSlot != -1) {
            throw new IllegalArgumentException("finalTriadSlot requires finalTriadDomain");
        }
        if (finalTriadDomain != null) {
            if (finalTriadSlot < 0 || finalTriadSlot >= 3) {
                throw new IllegalArgumentException("finalTriadSlot must be in 0..2");
            }
            if (maxRank != 3) {
                throw new IllegalArgumentException("final triad nodes must have maxRank 3");
            }
        }
    }

    public boolean finalTriadNode() {
        return finalTriadDomain != null;
    }
}
