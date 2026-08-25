package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Objects;

/** Dynamic acclimation gates for A0135-A0138; exact thermal/body components only. */
public final class FrozenThermalBodyPolicy {
    private FrozenThermalBodyPolicy() {}

    public static List<BodyCostResolver.Saving> metabolicSavings(
        FrozenSurvivalPerkRanks ranks,
        WorkFacts facts
    ) {
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(facts);
        if (!facts.physicalWork() || !facts.exactThermalMetabolicComponent()) return List.of();
        String code;
        if (facts.state() == AcclimationLedger.ThermalState.HOT && facts.hotCharges() > 0) code = "A0135";
        else if (facts.state() == AcclimationLedger.ThermalState.COLD && facts.coldCharges() > 0) code = "A0137";
        else return List.of();
        int rank = ranks.rank(code);
        return rank > 0 ? List.of(new BodyCostResolver.Saving(code, 0.03D * rank)) : List.of();
    }

    public static List<BodyCostResolver.Saving> hydrationSavings(
        FrozenSurvivalPerkRanks ranks,
        HydrationFacts facts
    ) {
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(facts);
        if (!facts.hydrationProviderPresent()) return List.of();
        String code;
        double eligibleCost;
        if (facts.state() == AcclimationLedger.ThermalState.HOT && facts.hotCharges() > 0) {
            code = "A0136";
            eligibleCost = facts.allocation().eligibleHotWorkThermalHydrationCost();
        } else if (facts.state() == AcclimationLedger.ThermalState.COLD && facts.coldCharges() > 0) {
            code = "A0138";
            eligibleCost = facts.allocation().eligibleColdWorkBaseHydrationCost();
        } else return List.of();
        int rank = ranks.rank(code);
        return rank > 0 && eligibleCost > 0.0D
            ? List.of(new BodyCostResolver.Saving(code, 0.03D * rank)) : List.of();
    }

    public record WorkFacts(
        AcclimationLedger.ThermalState state,
        int hotCharges,
        int coldCharges,
        boolean physicalWork,
        boolean exactThermalMetabolicComponent
    ) {
        public WorkFacts {
            Objects.requireNonNull(state);
            requireCharges(hotCharges, coldCharges);
        }
    }

    public record HydrationFacts(
        AcclimationLedger.ThermalState state,
        int hotCharges,
        int coldCharges,
        boolean hydrationProviderPresent,
        TfcExhaustionHydrationLedger.HydrationAllocation allocation
    ) {
        public HydrationFacts {
            Objects.requireNonNull(state);
            requireCharges(hotCharges, coldCharges);
            Objects.requireNonNull(allocation);
        }
    }

    private static void requireCharges(int hot, int cold) {
        if (hot < 0 || hot > AcclimationLedger.MAX_CHARGES
            || cold < 0 || cold > AcclimationLedger.MAX_CHARGES) {
            throw new IllegalArgumentException("charges must be in [0,5]");
        }
    }
}
