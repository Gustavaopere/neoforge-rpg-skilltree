package dev.gustavopere.rpgskilltree.runtime.economy;

/** Protocol and configured-policy validation for administrative monetary intents. */
public final class EconomyIntentLimits {
    /** Technical per-packet ceiling; colony balances and monetary policy remain long-valued. */
    public static final long MAX_MUTATION_AMOUNT = Integer.MAX_VALUE;

    private EconomyIntentLimits() {}

    public static Validation validateAmount(long amount) {
        return validateAmount(amount, Long.MAX_VALUE);
    }

    public static Validation validateAmount(long amount, long configuredMaxMutationAmount) {
        if (configuredMaxMutationAmount <= 0L) {
            throw new IllegalArgumentException("configuredMaxMutationAmount must be positive");
        }
        if (amount <= 0L) {
            return Validation.INVALID_AMOUNT;
        }
        if (amount > MAX_MUTATION_AMOUNT) {
            return Validation.PROTOCOL_LIMIT_EXCEEDED;
        }
        if (amount > configuredMaxMutationAmount) {
            return Validation.POLICY_LIMIT_EXCEEDED;
        }
        return Validation.ACCEPTED;
    }

    public enum Validation {
        ACCEPTED,
        INVALID_AMOUNT,
        PROTOCOL_LIMIT_EXCEEDED,
        POLICY_LIMIT_EXCEEDED
    }
}
