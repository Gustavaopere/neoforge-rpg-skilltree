package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Exact post-debit resource receipt correlated to one canonical action. */
public record ResourceDebitReceipt(
    CanonicalActionIdentity action,
    Kind kind,
    String providerResourceId,
    double amountPaid,
    double minimumDebitUnit
) {
    public ResourceDebitReceipt {
        Objects.requireNonNull(action);
        Objects.requireNonNull(kind);
        Objects.requireNonNull(providerResourceId);
        if (providerResourceId.isBlank()) throw new IllegalArgumentException("providerResourceId must not be blank");
        if (!Double.isFinite(amountPaid) || amountPaid <= 0.0D
            || !Double.isFinite(minimumDebitUnit) || minimumDebitUnit <= 0.0D) {
            throw new IllegalArgumentException("receipt amounts must be finite and positive");
        }
    }

    public enum Kind { MANA }
}
