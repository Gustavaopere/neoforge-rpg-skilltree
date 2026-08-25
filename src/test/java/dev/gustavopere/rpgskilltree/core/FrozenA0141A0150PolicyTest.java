package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.Map;

public final class FrozenA0141A0150PolicyTest {
    public static void main(String[] args) {
        borealAdaptationTouchesOnlyMappedColdComponents();
        frugalDigestionKeepsBothSidesAtomicAndRestCausal();
        nutritionQualityUsesTheWeakestNutrientAndNativePositiveRecovery();
        magicPowerResolvesOncePerCanonicalOutcome();
        manaPoliciesPreserveResourcesPoolsAndNativeBlocks();
        castSpeedUsesSpeedFormulaNotDirectTimeReduction();
        spellRecoveryUsesTwoDistinctCastsAndPersistentCooldown();
        resourceReceiptAndCastingStabilityAreExactAtomicAndSingleClaim();
        castingStabilityFailsClosedOnPhysiologyProviderAndUnavoidableControl();
        System.out.println("FrozenA0141A0150PolicyTest: PASS");
    }

    private static void borealAdaptationTouchesOnlyMappedColdComponents() {
        var both = FrozenAcclimationPolicy.cold(5, true, true);
        close(0.20D, both.mappedPhysiologyReduction(), "five COLD charges map twenty percent physiology");
        close(0.15D, both.coldMetabolicReduction(), "five COLD charges map fifteen percent cold metabolism");
        require(!both.altersTemperature() && !both.altersDamage() && !both.altersThresholds(),
            "A0141 never becomes ice resistance or threshold manipulation");
        require(!FrozenAcclimationPolicy.cold(5, false, false).active(),
            "missing mapped cold components fails closed");
    }

    private static void frugalDigestionKeepsBothSidesAtomicAndRestCausal() {
        var ranks = FrozenSurvivalPerkRanks.of(Map.of("A0115", 1, "A0123", 1, "A0142", 1));
        require(!FrugalDigestionPolicy.evaluate(ranks, true, false).active(),
            "saturation cost cannot operate without basal benefit hook");
        var policy = FrugalDigestionPolicy.evaluate(ranks, true, true);
        require(policy.active(), "two distinct canonical metabolic nodes satisfy structural route");
        var nativeFood = new FrugalDigestionPolicy.FoodRestoration(
            4.0D, 10.0D, Map.of("fruit", 0.2D), 3.0D, 1.0D);
        var adjusted = policy.adjustFood(nativeFood);
        close(4.0D, adjusted.hunger(), "hunger remains native");
        close(9.2D, adjusted.saturation(), "only saturation receives the eight-percent cost");
        require(adjusted.nutrients().equals(nativeFood.nutrients()), "nutrients remain native");
        close(3.0D, adjusted.water(), "food water remains native");
        close(1.0D, adjusted.toxicity(), "toxicity remains native");

        var rest = new PhysiologicalRestService(16);
        rest.invalidateLifecycle("player", 0L);
        require(!rest.resting("player", 199L), "lifecycle cannot fabricate two hundred rest ticks");
        require(rest.resting("player", 200L), "two hundred hostile-free ticks qualify");
        rest.recordPhysicalBodyCost("player", 200L);
        require(!rest.resting("player", 200L), "physical body event blocks the current tick only");
        require(rest.resting("player", 201L), "physical event does not invent a longer timer");
        close(0.85D, policy.basalMultiplier(rest.resting("player", 201L)), "resting basal benefit");
        rest.recordHostileCombat("player", 201L);
        close(1.0D, policy.basalMultiplier(rest.resting("player", 201L)), "new hostile action ends rest");
    }

    private static void nutritionQualityUsesTheWeakestNutrientAndNativePositiveRecovery() {
        var resolver = new NutritionQualityResolver();
        var good = resolver.resolve(new NutritionQualityResolver.TfcNutrients(0.9D, 0.7D, 0.6D, 0.8D, 1.0D));
        require(good.tier() == NutritionQualityResolver.Tier.GOOD, "minimum nutrient fixes tier");
        close(1.10D, good.multiplier(), "GOOD multiplier");
        close(11.0D, good.applyNaturalRecovery(10.0D, true), "positive native stamina/heal amplified");
        close(0.0D, good.applyNaturalRecovery(0.0D, true), "zero native recovery stays zero");
        close(10.0D, good.applyNaturalRecovery(10.0D, false), "missing component hook omits only component");
        var poor = resolver.resolve(new NutritionQualityResolver.TfcNutrients(0.9D, 0.39D, 1.0D, 1.0D, 1.0D));
        close(1.0D, poor.multiplier(), "below forty percent grants no bonus");
    }

    private static void magicPowerResolvesOncePerCanonicalOutcome() {
        var resolver = new MagicPowerResolver(32);
        var outcome = new CanonicalOutcomeIdentity(action("cast"), "outcome-1");
        var first = resolver.resolve(new MagicPowerResolver.Request(outcome, 100.0D, true, true), 5, 0L);
        close(110.0D, first.adjustedValue(), "A0144 rank five applies universal ten percent");
        require(first.applied(), "eligible outcome applied");
        var duplicate = resolver.resolve(new MagicPowerResolver.Request(outcome, 110.0D, true, true), 5, 1L);
        require(duplicate.duplicate() && !duplicate.applied(), "same outcome never receives power twice");
        close(110.0D, duplicate.adjustedValue(), "duplicate callback remains unchanged");
        var derived = resolver.resolve(new MagicPowerResolver.Request(
            new CanonicalOutcomeIdentity(action("derived").child("proc"), "outcome"),
            100.0D, true, true), 5, 2L);
        close(100.0D, derived.adjustedValue(), "derived result cannot re-enter magic power");
        close(100.0D, resolver.resolve(new MagicPowerResolver.Request(
            new CanonicalOutcomeIdentity(action("fake"), "outcome"), 100.0D, true, false), 5, 3L)
            .adjustedValue(), "fake/proxy owner rejected");
    }

    private static void manaPoliciesPreserveResourcesPoolsAndNativeBlocks() {
        close(9.0D, FrozenArcanePolicy.manaCost(10.0D, 0.0D, 5, true), "A0145 changes only mana cost");
        close(10.0D, FrozenArcanePolicy.manaCost(10.0D, 0.0D, 5, false), "non-mana resource unchanged");
        close(0.45D, FrozenArcanePolicy.manaCost(0.5D, 0.45D, 5, true), "provider minimum remains authoritative");
        var increase = FrozenArcanePolicy.reconcileManaPool(100.0D, 40.0D, 0, 5);
        close(110.0D, increase.maximum(), "A0146 max mana per provider");
        close(40.0D, increase.current(), "capacity increase never refills");
        var decrease = FrozenArcanePolicy.reconcileManaPool(100.0D, 108.0D, 5, 0);
        close(100.0D, decrease.maximum(), "removed capacity returns native max");
        close(100.0D, decrease.current(), "decrease clamps only value above new max");
        close(11.5D, FrozenArcanePolicy.manaRegen(10.0D, 5, true), "A0147 positive native regen");
        close(0.0D, FrozenArcanePolicy.manaRegen(0.0D, 5, true), "zero native regen remains zero");
        close(0.0D, FrozenArcanePolicy.manaRegen(10.0D, 5, false), "native provider block remains zero");
    }

    private static void castSpeedUsesSpeedFormulaNotDirectTimeReduction() {
        close(100.0D / 1.08D, FrozenArcanePolicy.castTime(100.0D, 4, true, true, 0.0D),
            "A0148 divides by speed multiplier");
        close(100.0D, FrozenArcanePolicy.castTime(100.0D, 4, false, true, 0.0D),
            "instant cast unchanged");
        close(95.0D, FrozenArcanePolicy.castTime(100.0D, 4, true, true, 95.0D),
            "provider cast-time floor wins");
    }

    private static void spellRecoveryUsesTwoDistinctCastsAndPersistentCooldown() {
        var service = new SpellRecoveryService(32);
        var firstAction = action("fire-1");
        require(service.onCast(new SpellRecoveryService.Cast(
            "player", firstAction, "fire", true, true, true), 1, 0L).cooldownMultiplier() == 1.0D,
            "first noninstant cast opens sequence only");
        require(service.onCast(new SpellRecoveryService.Cast(
            "player", firstAction, "fire", true, true, true), 1, 1L).duplicate(),
            "duplicate completion ignored");
        require(service.onCast(new SpellRecoveryService.Cast(
            "player", action("fire-2"), "fire", true, true, true), 1, 20L).cooldownMultiplier() == 1.0D,
            "same spell does not consume sequence");
        var second = service.onCast(new SpellRecoveryService.Cast(
            "player", action("ice"), "ice", false, true, true), 1, 40L);
        close(0.85D, second.cooldownMultiplier(), "different second spell reduces final cooldown once");
        require(second.consumedWindow(), "window consumed by valid second spell");
        service.clearTransient("player");
        require(service.onCast(new SpellRecoveryService.Cast(
            "player", action("early"), "arcane", true, true, true), 1, 100L).internalCooldown(),
            "lifecycle does not erase eight-second internal cooldown");
    }

    private static void resourceReceiptAndCastingStabilityAreExactAtomicAndSingleClaim() {
        var receipts = new ResourceDebitReceiptService(32);
        var service = new CastingStabilityService(32, receipts);
        var cast = action("stable-cast");
        require(receipts.record(new ResourceDebitReceipt(cast, ResourceDebitReceipt.Kind.MANA,
            "irons:mana", 1.0D, 1.0D), 0L), "post-debit mana receipt recorded");
        require(!receipts.record(new ResourceDebitReceipt(cast, ResourceDebitReceipt.Kind.MANA,
            "irons:mana", 2.0D, 1.0D), 1L), "same action/resource cannot record ambiguous second debit");
        var debits = new ArrayList<Double>();
        var converted = service.convert(request("player", cast, 100.0D, 1.0D),
            (resource, amount, adjustedTime) -> { debits.add(amount); return adjustedTime == 120.0D; }, 1, 2L);
        require(converted.converted(), "first real interruption converted");
        close(120.0D, converted.adjustedRemainingTime(), "remaining time multiplied by 1.20");
        close(1.0D, converted.extraDebit(), "integer provider quantizes positive eight percent upward");
        require(debits.equals(java.util.List.of(1.0D)), "extra resource debit executed atomically once");
        require(!service.convert(request("player", cast, 100.0D, 1.0D),
            (resource, amount, adjustedTime) -> true, 1, 3L).converted(), "same cast cannot convert twice");

        var fractional = action("fractional");
        receipts.record(new ResourceDebitReceipt(fractional, ResourceDebitReceipt.Kind.MANA,
            "ars:mana", 0.10D, 0.01D), 20L);
        var frac = service.convert(request("other", fractional, 10.0D, 1.0D),
            (resource, amount, adjustedTime) -> true, 1, 20L);
        close(0.01D, frac.extraDebit(), "fractional provider uses native minimum unit");

        var retry = action("retry");
        receipts.record(new ResourceDebitReceipt(retry, ResourceDebitReceipt.Kind.MANA,
            "ars:mana", 10.0D, 1.0D), 40L);
        require(!service.convert(request("retry-player", retry, 10.0D, 0.0D),
            (resource, amount, adjustedTime) -> true, 1, 40L).converted(), "insufficient current mana grants nothing");
        require(service.convert(request("retry-player", retry, 10.0D, 1.0D),
            (resource, amount, adjustedTime) -> true, 1, 40L).converted(), "failed balance gate does not consume receipt");
    }

    private static void castingStabilityFailsClosedOnPhysiologyProviderAndUnavoidableControl() {
        var receipts = new ResourceDebitReceiptService(32);
        var service = new CastingStabilityService(32, receipts);
        var lowThirst = action("low-thirst");
        receipts.record(new ResourceDebitReceipt(lowThirst, ResourceDebitReceipt.Kind.MANA,
            "mana", 10.0D, 1.0D), 0L);
        var blocked = new CastingStabilityService.Request("player", lowThirst, 10.0D,
            true, false, true, true, 19.0D, 20, false, true, false, 10.0D);
        require(!service.convert(blocked, (resource, amount, adjustedTime) -> true, 1, 0L).converted(),
            "TFC thirst below twenty blocks");
        var noThermalAdapter = action("thermal");
        receipts.record(new ResourceDebitReceipt(noThermalAdapter, ResourceDebitReceipt.Kind.MANA,
            "mana", 10.0D, 1.0D), 1L);
        var thermal = new CastingStabilityService.Request("thermal-player", noThermalAdapter, 10.0D,
            true, false, true, false, 100.0D, 20, true, false, false, 10.0D);
        require(!service.convert(thermal, (resource, amount, adjustedTime) -> true, 1, 1L).converted(),
            "active thermal provider without severe-stage adapter fails closed");
        var absolute = action("absolute");
        receipts.record(new ResourceDebitReceipt(absolute, ResourceDebitReceipt.Kind.MANA,
            "mana", 10.0D, 1.0D), 2L);
        var unavoidable = new CastingStabilityService.Request("absolute-player", absolute, 10.0D,
            true, true, true, false, 100.0D, 20, false, true, false, 10.0D);
        require(!service.convert(unavoidable, (resource, amount, adjustedTime) -> true, 1, 2L).converted(),
            "unavoidable interruption remains authoritative");
    }

    private static CastingStabilityService.Request request(
        String playerId, CanonicalActionIdentity action, double remaining, double balance) {
        return new CastingStabilityService.Request(playerId, action, remaining,
            true, false, true, false, 100.0D, 20,
            false, true, false, balance);
    }

    private static CanonicalActionIdentity action(String id) {
        String actor = id.equals("fractional") ? "other" : id.equals("retry") ? "retry-player" :
            id.equals("thermal") ? "thermal-player" : id.equals("absolute") ? "absolute-player" : "player";
        return CanonicalActionIdentity.root(actor, id, "server:cast");
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
