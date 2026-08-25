package dev.gustavopere.rpgskilltree.core;

/** Safe numeric component projection for A0140; does not alter temperature, thresholds, states or damage. */
public final class FrozenAcclimationPolicy {
    private FrozenAcclimationPolicy() {}

    public static Effect hot(int hotCharges, boolean physiologyAdapterPresent, boolean thermalHotPresent) {
        if (hotCharges < 0 || hotCharges > AcclimationLedger.MAX_CHARGES) {
            throw new IllegalArgumentException("hotCharges must be in [0,5]");
        }
        boolean active = hotCharges > 0 && (physiologyAdapterPresent || thermalHotPresent);
        if (!active) return Effect.inactive();
        return new Effect(true,
            physiologyAdapterPresent ? 0.04D * hotCharges : 0.0D,
            thermalHotPresent ? 0.03D * hotCharges : 0.0D,
            false, false, false);
    }

    public static ColdEffect cold(
        int coldCharges,
        boolean physiologyAdapterPresent,
        boolean coldMetabolicComponentPresent
    ) {
        if (coldCharges < 0 || coldCharges > AcclimationLedger.MAX_CHARGES) {
            throw new IllegalArgumentException("coldCharges must be in [0,5]");
        }
        boolean active = coldCharges > 0
            && (physiologyAdapterPresent || coldMetabolicComponentPresent);
        if (!active) return ColdEffect.inactive();
        return new ColdEffect(true,
            physiologyAdapterPresent ? 0.04D * coldCharges : 0.0D,
            coldMetabolicComponentPresent ? 0.03D * coldCharges : 0.0D,
            false, false, false);
    }

    public record Effect(
        boolean active,
        double mappedPhysiologyReduction,
        double thermalHotHydrationReduction,
        boolean altersTemperature,
        boolean altersDamage,
        boolean altersThresholds
    ) {
        private static Effect inactive() {
            return new Effect(false, 0.0D, 0.0D, false, false, false);
        }
    }

    public record ColdEffect(
        boolean active,
        double mappedPhysiologyReduction,
        double coldMetabolicReduction,
        boolean altersTemperature,
        boolean altersDamage,
        boolean altersThresholds
    ) {
        private static ColdEffect inactive() {
            return new ColdEffect(false, 0.0D, 0.0D, false, false, false);
        }
    }
}
