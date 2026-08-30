package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.A0001A0020CombatPolicy.HitFacts;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class A0011A0020ImplementationContractJUnitTest {
    private static final double EPSILON = 1.0E-9D;

    @Test
    void spearAndDaggerTrainingUseOnlyTheirCanonicalFamilyRules() {
        var ranks = CombatPerkRanks.of(Map.of(
            "A0013", 3,
            "A0014", 3,
            "A0015", 3,
            "A0019", 3,
            "A0020", 3
        ));

        assertEquals(1.09D, NotionCombatPerkRules.baseDamageMultiplier(WeaponFamily.SPEAR, ranks), EPSILON);
        assertEquals(0.06D, NotionCombatPerkRules.rhythmBonus(WeaponFamily.SPEAR, ranks), EPSILON);
        assertEquals(0.09D, NotionCombatPerkRules.criticalChanceBonus(WeaponFamily.SPEAR, ranks), EPSILON);

        assertEquals(1.09D, NotionCombatPerkRules.baseDamageMultiplier(WeaponFamily.DAGGER, ranks), EPSILON);
        assertEquals(0.06D, NotionCombatPerkRules.rhythmBonus(WeaponFamily.DAGGER, ranks), EPSILON);
        assertEquals(0.0D, NotionCombatPerkRules.criticalChanceBonus(WeaponFamily.DAGGER, ranks), EPSILON,
            "A0021 is outside this lot, so A0011-A0020 must not invent dagger critical chance");
    }

    @Test
    void a0017FallbackConsumesItsWindowWithoutInventingDamageOrPenetration() {
        var ranks = CombatPerkRanks.of(Map.of("A0016", 2, "A0017", 2));
        var state = new NotionCombatPerkState();
        state.addDistanceControl("player", 1, 0L, 7_000L);

        A0001A0020CombatPolicy.onSpearRangeSample(
            "player", "target", false, false, ranks, state, 80, 100L);
        A0001A0020CombatPolicy.onSpearRangeSample(
            "player", "target", true, true, ranks, state, 80, 200L);

        var modifiers = A0001A0020CombatPolicy.beforeHit(
            facts("intercept", "target", 300L), ranks, state);

        assertEquals(1.0D, modifiers.damageMultiplier(), EPSILON,
            "A0017 fallback must not invent a damage component");
        assertEquals(1.35D, modifiers.impactMultiplier(), EPSILON);
        assertEquals(1.35D, modifiers.guardPressureMultiplier(), EPSILON);
        assertEquals(0.0D, modifiers.physicalPenetrationFraction(), EPSILON,
            "A0017 fallback must not invent penetration");
        assertEquals(0, state.distanceControl("player", 300L),
            "A0017 must consume exactly one Distance Control charge");
    }

    @Test
    void a0018ConsumesThreeChargesAndEnforcesSameTargetLockout() {
        var ranks = CombatPerkRanks.of(Map.of("A0016", 2, "A0017", 2, "A0018", 1));
        var state = new NotionCombatPerkState();
        state.addDistanceControl("player", 3, 0L, 7_000L);

        A0001A0020CombatPolicy.onSpearRangeSample(
            "player", "target", false, false, ranks, state, 80, 100L);
        A0001A0020CombatPolicy.onSpearRangeSample(
            "player", "target", true, true, ranks, state, 80, 200L);

        var first = A0001A0020CombatPolicy.beforeHit(
            facts("line-1", "target", 300L), ranks, state);
        assertEquals(1.15D, first.damageMultiplier(), EPSILON);
        assertEquals(1.40D, first.impactMultiplier(), EPSILON);
        assertEquals(1.40D, first.guardPressureMultiplier(), EPSILON);
        assertEquals(0, state.distanceControl("player", 300L));

        state.addDistanceControl("player", 3, 400L, 7_000L);
        A0001A0020CombatPolicy.onSpearRangeSample(
            "player", "target", false, false, ranks, state, 80, 500L);
        A0001A0020CombatPolicy.onSpearRangeSample(
            "player", "target", true, true, ranks, state, 80, 600L);

        var locked = A0001A0020CombatPolicy.beforeHit(
            facts("line-2", "target", 700L), ranks, state);
        assertEquals(1.0D, locked.damageMultiplier(), EPSILON,
            "same-target A0018 must remain locked for eight seconds");
        assertEquals(1.35D, locked.impactMultiplier(), EPSILON,
            "A0017 may still consume its independent intercept window during A0018 lockout");
    }

    private static HitFacts facts(String action, String target, long now) {
        return new HitFacts(
            "player",
            target,
            action,
            WeaponFamily.SPEAR,
            true,
            true,
            true,
            false,
            false,
            true,
            true,
            false,
            true,
            true,
            false,
            now
        );
    }
}
