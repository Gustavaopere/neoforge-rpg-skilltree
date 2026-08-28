package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Preserved allocation that is intentionally excluded from live rule/effect resolution. */
public record QuarantinedNodeAllocation(
    NodeAllocation allocation,
    String reason,
    long quarantinedAtRulesVersion
) {
    public static final int MAX_REASON_LENGTH = 256;

    public QuarantinedNodeAllocation {
        Objects.requireNonNull(allocation, "allocation");
        Objects.requireNonNull(reason, "reason");
        if (reason.isBlank()) throw new IllegalArgumentException("quarantine reason must not be blank");
        if (reason.length() > MAX_REASON_LENGTH) throw new IllegalArgumentException("quarantine reason is too long");
        if (quarantinedAtRulesVersion <= 0L) {
            throw new IllegalArgumentException("quarantinedAtRulesVersion must be positive");
        }
    }
}
