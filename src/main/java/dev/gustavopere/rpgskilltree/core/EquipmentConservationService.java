package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Post-native, once-per-wear conservation resolver for A0110/A0111. */
public final class EquipmentConservationService {
    private final CanonicalEventLedger claims;

    public EquipmentConservationService(int maxClaims) {
        claims = new CanonicalEventLedger(maxClaims);
    }

    public synchronized boolean conserve(
        Wear wear,
        FrozenSurvivalPerkRanks ranks,
        double randomDraw,
        long nowTick
    ) {
        Objects.requireNonNull(wear);
        Objects.requireNonNull(ranks);
        if (!Double.isFinite(randomDraw) || randomDraw < 0.0D || randomDraw >= 1.0D) {
            throw new IllegalArgumentException("randomDraw must be in [0,1)");
        }
        if (!wear.eligible() || wear.nativePrevented() || wear.indestructible()
            || wear.confirmedDecrement() <= 0
            || !ProcGuard.mayTriggerSecondaryEffect(wear.action().origin())) return false;
        double chance = switch (wear.family()) {
            case MANUAL_TOOL -> 0.01D * ranks.rank("A0110");
            case DURABLE_TECH -> 0.015D * ranks.rank("A0111");
            case OTHER -> 0.0D;
        };
        if (chance <= 0.0D) return false;
        if (!claims.claimPrimaryOnce(wear.action(), "equipment_conservation", nowTick, 1_200L)) return false;
        return randomDraw < chance;
    }

    public enum Family { MANUAL_TOOL, DURABLE_TECH, OTHER }

    public record Wear(
        CanonicalActionIdentity action,
        Family family,
        int confirmedDecrement,
        boolean eligible,
        boolean nativePrevented,
        boolean indestructible
    ) {
        public Wear {
            Objects.requireNonNull(action);
            Objects.requireNonNull(family);
            if (confirmedDecrement < 0) throw new IllegalArgumentException("confirmedDecrement must be non-negative");
        }
    }
}
