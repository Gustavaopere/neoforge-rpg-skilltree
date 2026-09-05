package dev.gustavopere.rpgskilltree.runtime.economy;

/** Protocol-only validation for administrative monetary intents. */
public final class EconomyIntentLimits {
    /** Technical per-packet ceiling; colony balances and monetary policy remain long-valued. */
    public static final long MAX_MUTATION_AMOUNT = Integer.MAX_VALUE;

    private EconomyIntentLimits() {}

    public static Validation validateAmount(long amount) {
        if (amount <= 0L) {
            return Validation.INVALID_AMOUNT;
        }
        if (amount > MAX_MUTATION_AMOUNT) {
            return Validation.PROTOCOL_LIMIT_EXCEEDED;
        }
        return Validation.ACCEPTED;
    }

    public enum Validation {
        ACCEPTED,
        INVALID_AMOUNT,
        PROTOCOL_LIMIT_EXCEEDED
    }
}
