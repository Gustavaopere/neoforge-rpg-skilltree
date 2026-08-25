package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

public final class FrozenA0131A0140PolicyTest {
    public static void main(String[] args) {
        castAndCarryMapOnlyExactBodyChannels();
        thermalWorkRequiresCanonicalStateChargeAndExactComponent();
        hotHydrationUsesOnlyEligibleHotWorkBucketFraction();
        coldHydrationUsesRealWorkDebitWithoutInventedColdSurcharge();
        efficientMetabolismCountsCanonicalNodesAndRequiresStaminaCost();
        acclimationUsesFrozenTimingsAndNoOfflineProgress();
        desertAdaptationReadsChargesWithoutChangingThresholdsOrDamage();
        System.out.println("FrozenA0131A0140PolicyTest: PASS");
    }

    private static void castAndCarryMapOnlyExactBodyChannels() {
        var ranks = FrozenSurvivalPerkRanks.of(Map.of(
            "A0131", 4, "A0132", 4, "A0133", 4, "A0134", 4));
        source(ranks, BodyCostResolver.Channel.METABOLIC, BodyCostResolver.Cause.CAST, "A0131");
        source(ranks, BodyCostResolver.Channel.HYDRATION, BodyCostResolver.Cause.CAST, "A0132");
        source(ranks, BodyCostResolver.Channel.METABOLIC, BodyCostResolver.Cause.CARRY, "A0133");
        source(ranks, BodyCostResolver.Channel.HYDRATION, BodyCostResolver.Cause.CARRY, "A0134");
        require(FrozenBodyCostPolicy.savings(ranks, BodyCostResolver.Channel.METABOLIC,
            BodyCostResolver.Cause.BASAL).isEmpty(), "cast/carry ranks never reduce basal metabolism");
    }

    private static void thermalWorkRequiresCanonicalStateChargeAndExactComponent() {
        var ranks = FrozenSurvivalPerkRanks.of(Map.of("A0135", 4, "A0137", 4));
        var hot = FrozenThermalBodyPolicy.metabolicSavings(ranks,
            new FrozenThermalBodyPolicy.WorkFacts(AcclimationLedger.ThermalState.HOT,
                1, 0, true, true));
        require(hot.size() == 1 && hot.get(0).sourceId().equals("A0135"), "HOT exact component uses A0135");
        require(FrozenThermalBodyPolicy.metabolicSavings(ranks,
            new FrozenThermalBodyPolicy.WorkFacts(AcclimationLedger.ThermalState.HOT,
                0, 0, true, true)).isEmpty(), "zero HOT charges dynamically disables A0135");
        require(FrozenThermalBodyPolicy.metabolicSavings(ranks,
            new FrozenThermalBodyPolicy.WorkFacts(AcclimationLedger.ThermalState.COLD,
                0, 1, true, true)).get(0).sourceId().equals("A0137"), "COLD exact component uses A0137");
        require(FrozenThermalBodyPolicy.metabolicSavings(ranks,
            new FrozenThermalBodyPolicy.WorkFacts(AcclimationLedger.ThermalState.COLD,
                0, 1, true, false)).isEmpty(), "no exact thermal metabolic component fails closed");
    }

    private static void hotHydrationUsesOnlyEligibleHotWorkBucketFraction() {
        var ledger = new TfcExhaustionHydrationLedger(32);
        ledger.recordKnown("player", action("hot-work"), BodyCostResolver.Cause.MINE, 1.0D,
            AcclimationLedger.ThermalState.HOT, 0L);
        ledger.recordUnattributed("player", 1.0D);
        var allocation = ledger.consume("player", 2.0D, 10.0D, 8.0D);
        close(0.5D, allocation.eligibleHotWorkExhaustionFraction(), "only half the bucket is eligible HOT work");
        close(4.0D, allocation.eligibleHotWorkThermalHydrationCost(),
            "A0136 base is proportional THERMAL_HOT only");
        var ranks = FrozenSurvivalPerkRanks.of(Map.of("A0136", 4));
        var resolved = FrozenThermalBodyPolicy.hydrationSavings(ranks,
            new FrozenThermalBodyPolicy.HydrationFacts(AcclimationLedger.ThermalState.HOT,
                1, 0, true, allocation));
        require(resolved.size() == 1 && resolved.get(0).sourceId().equals("A0136"),
            "A0136 eligible saving exposed once");
        close(0.48D, allocation.eligibleHotWorkThermalHydrationCost() * resolved.get(0).fraction(),
            "A0136 saves twelve percent of eligible thermal lane only");
    }

    private static void coldHydrationUsesRealWorkDebitWithoutInventedColdSurcharge() {
        var ledger = new TfcExhaustionHydrationLedger(32);
        ledger.recordKnown("player", action("cold-work"), BodyCostResolver.Cause.FORESTRY, 1.0D,
            AcclimationLedger.ThermalState.COLD, 0L);
        ledger.recordUnattributed("player", 1.0D);
        var allocation = ledger.consume("player", 2.0D, 10.0D, 0.0D);
        close(5.0D, allocation.eligibleColdWorkBaseHydrationCost(),
            "A0138 sees only real base hydration correlated to cold work");
        close(0.0D, allocation.thermalHotHydrationCost(), "cold never invents thermal hydration");
        var savings = FrozenThermalBodyPolicy.hydrationSavings(
            FrozenSurvivalPerkRanks.of(Map.of("A0138", 4)),
            new FrozenThermalBodyPolicy.HydrationFacts(AcclimationLedger.ThermalState.COLD,
                0, 1, true, allocation));
        require(savings.size() == 1 && savings.get(0).sourceId().equals("A0138"),
            "real correlated cold work hydration uses A0138");
    }

    private static void efficientMetabolismCountsCanonicalNodesAndRequiresStaminaCost() {
        var invalidRoute = FrozenSurvivalPerkRanks.of(Map.of(
            "A0115", 4, "A0117", 4, "A0119", 4, "A0139", 1));
        require(!EfficientMetabolismPolicy.evaluate(invalidRoute, true, true).active(),
            "three movement nodes lack the professional/climatic route");
        var route = FrozenSurvivalPerkRanks.of(Map.of(
            "A0115", 4, "A0117", 4, "A0123", 1, "A0139", 1));
        require(!EfficientMetabolismPolicy.evaluate(route, false, true).active(),
            "missing natural stamina hook disables benefit and cost together");
        var noHydration = EfficientMetabolismPolicy.evaluate(route, true, false);
        require(noHydration.active(), "metabolic lane may remain with stamina tradeoff");
        close(0.12D, noHydration.metabolicSaving(), "A0139 metabolic saving");
        close(0.0D, noHydration.hydrationSaving(), "absent hydration provider omits only hydration lane");
        close(-0.08D, noHydration.naturalStaminaRegenMultiplierDelta(), "mandatory stamina cost");
        var full = EfficientMetabolismPolicy.evaluate(route, true, true);
        close(0.12D, full.hydrationSaving(), "real hydration provider enables hydration lane");
    }

    private static void acclimationUsesFrozenTimingsAndNoOfflineProgress() {
        var ledger = new AcclimationLedger(16);
        ledger.observe("player", AcclimationLedger.ThermalState.HOT, 0L);
        ledger.observe("player", AcclimationLedger.ThermalState.HOT, 11_999L);
        require(ledger.snapshot("player").hotCharges() == 0, "HOT gain waits ten full minutes");
        ledger.observe("player", AcclimationLedger.ThermalState.HOT, 12_000L);
        require(ledger.snapshot("player").hotCharges() == 1, "ten minutes gains one HOT charge");
        ledger.suspend("player");
        ledger.observe("player", AcclimationLedger.ThermalState.HOT, 100_000L);
        require(ledger.snapshot("player").hotCharges() == 1, "offline time grants no progress");
        ledger.observe("player", AcclimationLedger.ThermalState.HOT, 112_000L);
        require(ledger.snapshot("player").hotCharges() == 2, "new online ten-minute interval gains one");
        ledger.observe("player", AcclimationLedger.ThermalState.COLD, 112_000L);
        ledger.observe("player", AcclimationLedger.ThermalState.COLD, 118_000L);
        require(ledger.snapshot("player").hotCharges() == 1, "opposite extreme loses one in five minutes");
        ledger.observe("player", AcclimationLedger.ThermalState.NEUTRAL, 118_000L);
        ledger.observe("player", AcclimationLedger.ThermalState.NEUTRAL, 142_000L);
        require(ledger.snapshot("player").hotCharges() == 0, "outside range loses one in twenty minutes");
    }

    private static void desertAdaptationReadsChargesWithoutChangingThresholdsOrDamage() {
        var both = FrozenAcclimationPolicy.hot(5, true, true);
        close(0.20D, both.mappedPhysiologyReduction(), "five charges cap mapped numeric physiology at twenty percent");
        close(0.15D, both.thermalHotHydrationReduction(), "five charges cap real hot hydration at fifteen percent");
        require(!both.altersTemperature() && !both.altersDamage() && !both.altersThresholds(),
            "A0140 is not fire resistance or threshold manipulation");
        var hydrationOnly = FrozenAcclimationPolicy.hot(2, false, true);
        require(hydrationOnly.active(), "safe independent canonical component may remain");
        close(0.0D, hydrationOnly.mappedPhysiologyReduction(), "missing physiology adapter omits that component");
        require(!FrozenAcclimationPolicy.hot(5, false, false).active(),
            "no mapped component fails closed");
    }

    private static void source(FrozenSurvivalPerkRanks ranks, BodyCostResolver.Channel channel,
        BodyCostResolver.Cause cause, String expected) {
        var values = FrozenBodyCostPolicy.savings(ranks, channel, cause);
        require(values.size() == 1 && values.get(0).sourceId().equals(expected), expected + " mapping");
        close(0.12D, values.get(0).fraction(), expected + " rank scaling");
    }

    private static CanonicalActionIdentity action(String id) {
        return CanonicalActionIdentity.root("player", id, "tfc:exhaustion");
    }

    private static void close(double expected, double actual, String message) {
        if (Math.abs(expected - actual) > 0.000001D) {
            throw new AssertionError(message + ": " + expected + " != " + actual);
        }
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
