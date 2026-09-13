package dev.gustavopere.rpgskilltree.core.economy;

import java.util.Objects;
import java.util.UUID;

/** Server-side intent for one monetary mutation. */
public record EconomyCommand(
    UUID transactionId,
    String causalKey,
    EconomyTransactionKind kind,
    long amount
) {
    public EconomyCommand {
        Objects.requireNonNull(transactionId, "transactionId");
        Objects.requireNonNull(kind, "kind");
        causalKey = Objects.requireNonNull(causalKey, "causalKey").trim();
        if (causalKey.isEmpty()) {
            throw new IllegalArgumentException("causalKey must not be blank");
        }
    }
}
