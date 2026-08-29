package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Explicit rule-aware basis used to convert one raw legacy node id into v5 economic facts. */
public record LegacyNodeAllocationMigrationBasis(
    String targetNodeId,
    long inferredPaidCostPerRank,
    String currencyId,
    String sourceTreeId,
    long rulesVersion,
    LegacyNodeMigrationDisposition disposition,
    String quarantineReason
) {
    public LegacyNodeAllocationMigrationBasis {
        targetNodeId = ProgressionProvenanceId.requireNamespacedId(targetNodeId, "target node id");
        if (inferredPaidCostPerRank < 0L) {
            throw new IllegalArgumentException("inferredPaidCostPerRank must be non-negative");
        }
        currencyId = ProgressionProvenanceId.requireNamespacedId(currencyId, "currency id");
        sourceTreeId = ProgressionProvenanceId.requireNamespacedId(sourceTreeId, "source tree id");
        if (rulesVersion <= 0L) throw new IllegalArgumentException("rulesVersion must be positive");
        Objects.requireNonNull(disposition, "disposition");

        switch (disposition) {
            case ACTIVE -> {
                if (quarantineReason != null) {
                    throw new IllegalArgumentException("active migration basis cannot define a quarantine reason");
                }
            }
            case QUARANTINE -> {
                if (quarantineReason == null || quarantineReason.isBlank()) {
                    throw new IllegalArgumentException("quarantine migration basis requires a reason");
                }
                if (quarantineReason.length() > QuarantinedNodeAllocation.MAX_REASON_LENGTH) {
                    throw new IllegalArgumentException("quarantine reason is too long");
                }
            }
        }
    }
}
