package dev.gustavopere.rpgskilltree.core.economy;

import java.util.Objects;
import java.util.Optional;

/** Result of one canonical ledger mutation attempt. */
public record EconomyMutationResult(
    Status status,
    ColonyEconomyState state,
    EconomyTransaction transaction
) {
    public EconomyMutationResult {
        Objects.requireNonNull(status, "status");
        Objects.requireNonNull(state, "state");
        if (status == Status.APPLIED && transaction == null) {
            throw new IllegalArgumentException("applied result requires transaction");
        }
        if (status != Status.APPLIED && transaction != null) {
            throw new IllegalArgumentException("non-applied result must not contain transaction");
        }
    }

    public Optional<EconomyTransaction> appliedTransaction() {
        return Optional.ofNullable(transaction);
    }

    public static EconomyMutationResult rejected(Status status, ColonyEconomyState state) {
        if (status == Status.APPLIED) {
            throw new IllegalArgumentException("use applied result for APPLIED status");
        }
        return new EconomyMutationResult(status, state, null);
    }

    public static EconomyMutationResult applied(ColonyEconomyState state, EconomyTransaction transaction) {
        return new EconomyMutationResult(Status.APPLIED, state, transaction);
    }

    public enum Status {
        APPLIED,
        DUPLICATE,
        INVALID_AMOUNT,
        INSUFFICIENT_TREASURY,
        UNSUPPORTED_KIND,
        RETENTION_LIMIT_REACHED,
        OVERFLOW
    }
}
