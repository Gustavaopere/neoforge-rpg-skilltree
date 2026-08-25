package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Map;

public final class FrozenA0121A0130PolicyTest {
    public static void main(String[] args) {
        bodyPolicyMapsClimbWorkAndCombatWithoutCrossCategoryFallback();
        producerRequiresExactServerSidePlayerOwnedDebit();
        tfcLedgerAllocatesBaseHydrationByFifoBucketShares();
        tfcLedgerKeepsThermalHotSeparateAndUnknownUnattributed();
        tfcLedgerDeduplicatesAndSurvivesLifecycleBoundaries();
        System.out.println("FrozenA0121A0130PolicyTest: PASS");
    }

    private static void bodyPolicyMapsClimbWorkAndCombatWithoutCrossCategoryFallback() {
        var ranks = FrozenSurvivalPerkRanks.of(Map.ofEntries(
            Map.entry("A0121", 4), Map.entry("A0122", 4),
            Map.entry("A0123", 4), Map.entry("A0124", 4),
            Map.entry("A0125", 4), Map.entry("A0126", 4),
            Map.entry("A0127", 4), Map.entry("A0128", 4),
            Map.entry("A0129", 4), Map.entry("A0130", 4)));
        assertSource(ranks, BodyCostResolver.Channel.METABOLIC, BodyCostResolver.Cause.CLIMB, "A0121");
        assertSource(ranks, BodyCostResolver.Channel.HYDRATION, BodyCostResolver.Cause.CLIMB, "A0122");
        assertSource(ranks, BodyCostResolver.Channel.METABOLIC, BodyCostResolver.Cause.MINE, "A0123");
        assertSource(ranks, BodyCostResolver.Channel.HYDRATION, BodyCostResolver.Cause.MINE, "A0124");
        assertSource(ranks, BodyCostResolver.Channel.METABOLIC, BodyCostResolver.Cause.FORESTRY, "A0125");
        assertSource(ranks, BodyCostResolver.Channel.HYDRATION, BodyCostResolver.Cause.FORESTRY, "A0126");
        assertSource(ranks, BodyCostResolver.Channel.METABOLIC, BodyCostResolver.Cause.MELEE, "A0127");
        assertSource(ranks, BodyCostResolver.Channel.HYDRATION, BodyCostResolver.Cause.MELEE, "A0128");
        assertSource(ranks, BodyCostResolver.Channel.METABOLIC, BodyCostResolver.Cause.RANGED, "A0129");
        assertSource(ranks, BodyCostResolver.Channel.HYDRATION, BodyCostResolver.Cause.RANGED, "A0130");
        require(FrozenBodyCostPolicy.savings(ranks,
            BodyCostResolver.Channel.METABOLIC, BodyCostResolver.Cause.CAST).isEmpty(),
            "ranged ranks do not become cast savings");
    }

    private static void producerRequiresExactServerSidePlayerOwnedDebit() {
        var action = CanonicalActionIdentity.root("player", "mine", "tfc:exhaustion");
        var eligible = new BodyCostEventProducer.Facts(true, true, true, true, true);
        var request = BodyCostEventProducer.produce(action, BodyCostResolver.Channel.METABOLIC,
            BodyCostResolver.Cause.MINE, 2.0D, eligible).orElseThrow();
        close(2.0D, request.confirmedCost(), "producer forwards exact debit without estimating");
        require(BodyCostEventProducer.produce(action, BodyCostResolver.Channel.METABOLIC,
            BodyCostResolver.Cause.MINE, 2.0D,
            new BodyCostEventProducer.Facts(false, true, true, true, true)).isEmpty(),
            "client callback rejected");
        require(BodyCostEventProducer.produce(action, BodyCostResolver.Channel.METABOLIC,
            BodyCostResolver.Cause.MINE, 2.0D,
            new BodyCostEventProducer.Facts(true, false, true, true, true)).isEmpty(),
            "fake player or proxy owner rejected");
        require(BodyCostEventProducer.produce(action, BodyCostResolver.Channel.METABOLIC,
            BodyCostResolver.Cause.FORESTRY, 2.0D,
            new BodyCostEventProducer.Facts(true, true, true, false, true)).isEmpty(),
            "missing causal provider fails closed");
        require(BodyCostEventProducer.produce(action.child("perk:derived"),
            BodyCostResolver.Channel.METABOLIC, BodyCostResolver.Cause.MINE, 2.0D, eligible).isEmpty(),
            "derived action cannot produce a body debit event");
    }

    private static void tfcLedgerAllocatesBaseHydrationByFifoBucketShares() {
        var ledger = new TfcExhaustionHydrationLedger(64);
        require(ledger.recordKnown("player", action("mine"), BodyCostResolver.Cause.MINE, 2.0D, 0L),
            "known mining exhaustion recorded");
        ledger.recordUnattributed("player", 1.0D);
        require(ledger.recordKnown("player", action("melee"), BodyCostResolver.Cause.MELEE, 1.0D, 1L),
            "known melee exhaustion recorded after unknown share");

        var first = ledger.consume("player", 2.0D, 12.0D, 0.0D);
        require(first.baseShares().size() == 1, "first FIFO contribution alone fills first conversion");
        var mine = first.baseShares().get(0);
        require(mine.attribution() == BodyCostResolver.Attribution.EXACT
            && mine.cause() == BodyCostResolver.Cause.MINE, "mining identity preserved");
        close(12.0D, mine.hydrationCost(), "base hydration apportioned by exhaustion share");

        var second = ledger.consume("player", 2.0D, 8.0D, 0.0D);
        require(second.baseShares().size() == 2, "unknown then melee shares remain FIFO");
        require(second.baseShares().get(0).attribution() == BodyCostResolver.Attribution.UNATTRIBUTED,
            "unknown real bucket difference stays unattributed");
        require(second.baseShares().get(1).cause() == BodyCostResolver.Cause.MELEE,
            "later known contribution follows unknown bucket share");
        close(4.0D, second.baseShares().get(0).hydrationCost(), "unknown proportional base cost");
        close(4.0D, second.baseShares().get(1).hydrationCost(), "known proportional base cost");
    }

    private static void tfcLedgerKeepsThermalHotSeparateAndUnknownUnattributed() {
        var ledger = new TfcExhaustionHydrationLedger(16);
        ledger.recordKnown("player", action("work"), BodyCostResolver.Cause.MINE, 1.0D, 0L);
        var allocation = ledger.consume("player", 2.0D, 10.0D, 3.0D);
        require(allocation.baseShares().size() == 2, "missing real bucket contribution is synthesized as unknown");
        require(allocation.baseShares().get(1).attribution() == BodyCostResolver.Attribution.UNATTRIBUTED,
            "unrecorded difference never inherits a perk cause");
        close(3.0D, allocation.thermalHotHydrationCost(), "thermal hot surcharge is a separate lane");
        require(allocation.thermalCause() == BodyCostResolver.Cause.THERMAL_HOT,
            "hot surcharge never becomes base or cold hydration");
    }

    private static void tfcLedgerDeduplicatesAndSurvivesLifecycleBoundaries() {
        var ledger = new TfcExhaustionHydrationLedger(16);
        var action = action("same");
        require(ledger.recordKnown("player", action, BodyCostResolver.Cause.RANGED, 1.0D, 0L), "first record");
        require(!ledger.recordKnown("player", action, BodyCostResolver.Cause.RANGED, 1.0D, 1L),
            "duplicate provider callback cannot add bucket credit");
        ledger.clearTransient("player");
        var allocation = ledger.consume("player", 1.0D, 5.0D, 0.0D);
        require(allocation.baseShares().size() == 1
            && allocation.baseShares().get(0).cause() == BodyCostResolver.Cause.RANGED,
            "logout/dimension cannot erase a real outstanding bucket contribution");
    }

    private static void assertSource(FrozenSurvivalPerkRanks ranks, BodyCostResolver.Channel channel,
        BodyCostResolver.Cause cause, String expected) {
        List<BodyCostResolver.Saving> savings = FrozenBodyCostPolicy.savings(ranks, channel, cause);
        require(savings.size() == 1 && savings.get(0).sourceId().equals(expected),
            cause + "/" + channel + " maps to " + expected);
        close(0.12D, savings.get(0).fraction(), expected + " rank scaling");
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
