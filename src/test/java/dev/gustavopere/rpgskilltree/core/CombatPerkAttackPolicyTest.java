package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;

public final class CombatPerkAttackPolicyTest {
    public static void main(String[] args) {
        swordOpeningConsumesMomentumAndAddsPenetration();
        axeRuptureConsumesFuryOnlyAgainstRelevantDefense();
        spearAndDaggerConsumeTheirOwnResources();
        hammerMaceAndScytheUsePerTargetPreparation();
        confirmedHitsGenerateOnlyTheirOwnResources();
        furyGenerationRequiresAnExplicitCanonicalBaseGain();
        System.out.println("CombatPerkAttackPolicyTest: PASS");
    }

    private static void swordOpeningConsumesMomentumAndAddsPenetration() {
        var state = new NotionCombatPerkState();
        state.addMomentum("p", 3, 1000L);
        var ranks = CombatPerkRanks.of(Map.of("A0001", 3, "A0005", 1));
        var ctx = context(WeaponFamily.SWORD, false, false, false, false, false, 1.0, 0.0, 1000L);
        var result = CombatPerkAttackPolicy.beforeHit(ctx, ranks, state);
        require(close(result.damageMultiplier(), 1.09), "sword training damage");
        require(close(result.armorNegationPoints(), 12.0), "A0005 armor negation");
        require(close(result.impactMultiplier(), 1.08), "A0005 impact");
        require(state.momentum("p") == 1, "A0005 consumes two momentum");
        require(!state.cooldownReady("p", "mob", "A0005", 2000L), "A0005 target cooldown");
    }

    private static void axeRuptureConsumesFuryOnlyAgainstRelevantDefense() {
        var ranks = CombatPerkRanks.of(Map.of("A0007", 1, "A0011", 2));
        var state = new NotionCombatPerkState();
        state.addFury("p", 50.0, 1000L);
        var guarded = context(WeaponFamily.AXE, true, false, false, false, false, 1.0, 0.0, 1000L);
        var result = CombatPerkAttackPolicy.beforeHit(guarded, ranks, state);
        require(close(result.damageMultiplier(), 1.03), "axe training damage");
        require(close(result.armorNegationPoints(), 10.0), "A0011 rank2 penetration");
        require(close(result.guardPressureMultiplier(), 1.35), "A0011 rank2 guard pressure");
        require(state.fury("p") == 30.0, "A0011 consumes 20 fury");

        var unguardedState = new NotionCombatPerkState();
        unguardedState.addFury("p", 50.0, 1000L);
        var unguarded = context(WeaponFamily.AXE, false, false, false, false, false, 1.0, 0.0, 1000L);
        var noRupture = CombatPerkAttackPolicy.beforeHit(unguarded, ranks, unguardedState);
        require(close(noRupture.armorNegationPoints(), 0.0), "A0011 no free penetration");
        require(unguardedState.fury("p") == 50.0, "A0011 no consumption without defense");
    }

    private static void spearAndDaggerConsumeTheirOwnResources() {
        var spearState = new NotionCombatPerkState();
        spearState.addDistanceControl("p", 1, 1000L);
        var spearRanks = CombatPerkRanks.of(Map.of("A0017", 2));
        var spear = context(WeaponFamily.SPEAR, false, false, true, true, false, 1.0, 0.0, 1000L);
        var intercept = CombatPerkAttackPolicy.beforeHit(spear, spearRanks, spearState);
        require(close(intercept.guardPressureMultiplier(), 1.35), "A0017 pressure");
        require(spearState.distanceControl("p") == 0, "A0017 consumes distance control");

        var daggerState = new NotionCombatPerkState();
        daggerState.addFlow("p", 2, 1000L);
        var daggerRanks = CombatPerkRanks.of(Map.of("A0023", 2));
        var dagger = context(WeaponFamily.DAGGER, false, false, false, false, true, 1.0, 0.0, 1000L, true);
        var blindSpot = CombatPerkAttackPolicy.beforeHit(dagger, daggerRanks, daggerState);
        require(close(blindSpot.damageMultiplier(), 1.25), "A0023 critical damage when hit is critical");
        require(close(blindSpot.armorNegationPoints(), 10.0), "A0023 penetration");
        require(daggerState.flow("p") == 0, "A0023 consumes flow");
    }

    private static void hammerMaceAndScytheUsePerTargetPreparation() {
        var hammerState = new NotionCombatPerkState();
        hammerState.addTargetCounter("p", "mob", NotionCombatPerkState.TargetCounter.SHOCK, 3, 3, 1000L, 6000L);
        var hammerRanks = CombatPerkRanks.of(Map.of("A0029", 2));
        var hammer = context(WeaponFamily.HAMMER, false, true, false, false, false, 1.0, 0.0, 1000L);
        var postureBreak = CombatPerkAttackPolicy.beforeHit(hammer, hammerRanks, hammerState);
        require(close(postureBreak.guardPressureMultiplier(), 1.45), "A0029 posture pressure");
        require(close(postureBreak.impactMultiplier(), 1.15), "A0029 impact");
        require(hammerState.targetCounter("p", "mob", NotionCombatPerkState.TargetCounter.SHOCK, 1000L) == 0, "A0029 consumes shock");

        var maceState = new NotionCombatPerkState();
        maceState.addTargetCounter("p", "mob", NotionCombatPerkState.TargetCounter.TRAUMA, 3, 3, 1000L, 8000L);
        var maceRanks = CombatPerkRanks.of(Map.of("A0035", 2));
        var mace = context(WeaponFamily.MACE, false, false, false, false, false, 1.0, 0.0, 1000L);
        CombatPerkAttackPolicy.beforeHit(mace, maceRanks, maceState);
        require(maceState.hasTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.ARMOR_CRACKED, 6999L), "A0035 armor crack active");
        var crackedHit = CombatPerkAttackPolicy.beforeHit(mace.withNowMillis(2000L), maceRanks, maceState);
        require(close(crackedHit.armorNegationPoints(), 9.0), "A0035 fallback penetration while cracked");

        var scytheState = new NotionCombatPerkState();
        scytheState.setTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.REAPING_MARK, 9000L);
        scytheState.setTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.REAPING_MATURE, 9000L);
        var scytheRanks = CombatPerkRanks.of(Map.of("A0041", 2));
        var scythe = context(WeaponFamily.SCYTHE, false, false, false, false, false, 0.4, 0.0, 1000L);
        var reap = CombatPerkAttackPolicy.beforeHit(scythe, scytheRanks, scytheState);
        require(close(reap.damageMultiplier(), 1.20), "A0041 damage");
        require(close(reap.impactMultiplier(), 1.25), "A0041 impact");
        require(!scytheState.hasTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.REAPING_MARK, 1000L), "A0041 consumes mark");
    }

    private static void confirmedHitsGenerateOnlyTheirOwnResources() {
        var state = new NotionCombatPerkState();
        var swordRanks = CombatPerkRanks.of(Map.of("A0004", 1));
        CombatPerkAttackPolicy.afterConfirmedHit(
            context(WeaponFamily.SWORD, false, false, false, false, false, 1.0, 0.0, 1000L), swordRanks, state);
        require(state.momentum("p") == 1, "A0004 momentum generation");
        require(state.flow("p") == 0, "sword cannot generate flow");

        var spearRanks = CombatPerkRanks.of(Map.of("A0016", 2));
        CombatPerkAttackPolicy.afterConfirmedHit(
            context(WeaponFamily.SPEAR, false, false, true, false, false, 1.0, 0.0, 2000L), spearRanks, state);
        require(state.distanceControl("p") == 1, "A0016 distance control");

        var daggerRanks = CombatPerkRanks.of(Map.of("A0022", 2));
        CombatPerkAttackPolicy.afterConfirmedHit(
            context(WeaponFamily.DAGGER, false, false, false, false, true, 1.0, 0.0, 3000L), daggerRanks, state);
        require(state.flow("p") == 1, "A0022 flow");

        var hammerRanks = CombatPerkRanks.of(Map.of("A0028", 2));
        CombatPerkAttackPolicy.afterConfirmedHit(
            context(WeaponFamily.HAMMER, false, false, false, false, false, 1.0, 0.0, 4000L), hammerRanks, state);
        require(state.targetCounter("p", "mob", NotionCombatPerkState.TargetCounter.SHOCK, 4000L) == 1, "A0028 shock");

        var maceRanks = CombatPerkRanks.of(Map.of("A0034", 2));
        CombatPerkAttackPolicy.afterConfirmedHit(
            context(WeaponFamily.MACE, true, false, false, false, false, 1.0, 0.0, 5000L), maceRanks, state);
        require(state.targetCounter("p", "mob", NotionCombatPerkState.TargetCounter.TRAUMA, 5000L) == 1, "A0034 trauma");

        var scytheRanks = CombatPerkRanks.of(Map.of("A0040", 2));
        CombatPerkAttackPolicy.afterConfirmedHit(
            context(WeaponFamily.SCYTHE, false, false, false, false, false, 0.4, 0.0, 6000L), scytheRanks, state);
        require(state.hasTargetFlag("p", "mob", NotionCombatPerkState.TargetFlag.REAPING_MATURE, 15000L), "A0040 mature mark");
    }

    private static void furyGenerationRequiresAnExplicitCanonicalBaseGain() {
        var ranks = CombatPerkRanks.of(Map.of("A0010", 2));
        var state = new NotionCombatPerkState();
        CombatPerkAttackPolicy.afterConfirmedHit(
            context(WeaponFamily.AXE, false, false, false, false, false, 1.0, 0.0, 1000L), ranks, state);
        require(close(state.fury("p"), 0.0), "no invented fury base gain");

        CombatPerkAttackPolicy.afterConfirmedHit(
            context(WeaponFamily.AXE, false, false, false, false, false, 1.0, 10.0, 2000L), ranks, state);
        require(close(state.fury("p"), 12.0), "rank2 fury multiplier");
        CombatPerkAttackPolicy.afterConfirmedHit(
            new CombatPerkAttackPolicy.AttackContext(
                "p", "other", WeaponFamily.AXE,
                true, true, false, false, false, false, false, false,
                1.0, false, 10.0, 3000L
            ),
            ranks,
            state
        );
        require(close(state.fury("p"), 30.0), "target switch gets +50% after rank multiplier");
    }

    private static CombatPerkAttackPolicy.AttackContext context(
        WeaponFamily family,
        boolean relevantDefense,
        boolean heavy,
        boolean idealRange,
        boolean advancing,
        boolean flank,
        double healthFraction,
        double baseFuryGain,
        long nowMillis
    ) {
        return context(family, relevantDefense, heavy, idealRange, advancing, flank, healthFraction, baseFuryGain, nowMillis, false);
    }

    private static CombatPerkAttackPolicy.AttackContext context(
        WeaponFamily family,
        boolean relevantDefense,
        boolean heavy,
        boolean idealRange,
        boolean advancing,
        boolean flank,
        double healthFraction,
        double baseFuryGain,
        long nowMillis,
        boolean critical
    ) {
        return new CombatPerkAttackPolicy.AttackContext(
            "p", "mob", family, true, true, relevantDefense, heavy, idealRange, advancing, flank,
            false, healthFraction, critical, baseFuryGain, nowMillis
        );
    }

    private static boolean close(double actual, double expected) {
        return Math.abs(actual - expected) < 0.000001;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
