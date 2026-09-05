package dev.gustavopere.rpgskilltree.core.economy;

import java.util.Objects;

/** Immutable server-authoritative state for one colony economy. */
public record ColonyEconomyState(
    EconomyColonyKey colonyKey,
    long issuedSupply,
    long retiredSupply,
    long treasuryBalance,
    long reservedBalance,
    long activeCirculation,
    double priceIndex,
    double taxRate,
    long currentEconomicCapacity,
    long lastSettlementTick,
    int schemaVersion
) {
    public ColonyEconomyState {
        Objects.requireNonNull(colonyKey, "colonyKey");
        requireNonNegative(issuedSupply, "issuedSupply");
        requireNonNegative(retiredSupply, "retiredSupply");
        requireNonNegative(treasuryBalance, "treasuryBalance");
        requireNonNegative(reservedBalance, "reservedBalance");
        requireNonNegative(activeCirculation, "activeCirculation");
        requireNonNegative(currentEconomicCapacity, "currentEconomicCapacity");
        if (retiredSupply > issuedSupply) {
            throw new IllegalArgumentException("retiredSupply must not exceed issuedSupply");
        }
        if (!Double.isFinite(priceIndex) || priceIndex <= 0.0D) {
            throw new IllegalArgumentException("priceIndex must be finite and positive");
        }
        if (!Double.isFinite(taxRate) || taxRate < 0.0D || taxRate > 1.0D) {
            throw new IllegalArgumentException("taxRate must be within [0,1]");
        }
        if (schemaVersion <= 0) {
            throw new IllegalArgumentException("schemaVersion must be positive");
        }
        long effectiveSupply = issuedSupply - retiredSupply;
        long allocated = Math.addExact(Math.addExact(treasuryBalance, reservedBalance), activeCirculation);
        if (allocated > effectiveSupply) {
            throw new IllegalArgumentException("monetary buckets exceed effective supply");
        }
    }

    public long effectiveSupply() {
        return issuedSupply - retiredSupply;
    }

    public static ColonyEconomyState empty(EconomyColonyKey key) {
        return new ColonyEconomyState(key, 0L, 0L, 0L, 0L, 0L, 100.0D, 0.10D, 0L, 0L, 1);
    }

    private static void requireNonNegative(long value, String name) {
        if (value < 0L) {
            throw new IllegalArgumentException(name + " must be non-negative");
        }
    }
}
