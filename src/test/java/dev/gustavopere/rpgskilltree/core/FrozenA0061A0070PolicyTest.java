package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

/** RED contract for the frozen universal MARTIAL offense cohort. */
public final class FrozenA0061A0070PolicyTest {
    public static void main(String[] args) {
        directPhysicalDamageUsesPreImpactFactsOnce();
        criticalChanceFeedsOnlyTheCanonicalDecision();
        criticalDamageRequiresTheCanonicalBoolean();
        providerBoundAxesFailClosedWithoutSubstitution();
        derivedAndDuplicateCallbacksCannotReapply();
        System.out.println("FrozenA0061A0070PolicyTest: PASS");
    }

    private static void directPhysicalDamageUsesPreImpactFactsOnce() {
        var ranks = FrozenCombatPerkRanks.of(Map.of("A0061", 5, "A0068", 3, "A0069", 3, "A0070", 5));
        var service = new FrozenMartialOffenseService();
        var woundedBoss = service.resolve(request(root("wounded"), 0.34D, true, false, true, true), ranks, 1_000L);
        require(close(woundedBoss.damageMultiplier(), 1.37D), "A0061 + A0068 + A0070 use pre-impact health");
        var intact = service.resolve(request(root("intact"), 0.86D, false, false, true, true), ranks, 1_001L);
        require(close(intact.damageMultiplier(), 1.22D), "A0061 + A0069 opening bonus");
        var boundary = service.resolve(request(root("boundary"), 0.35D, false, false, true, true), ranks, 1_002L);
        require(close(boundary.damageMultiplier(), 1.10D), "35% is not below and 85% is not above");
    }

    private static void criticalChanceFeedsOnlyTheCanonicalDecision() {
        var ranks = FrozenCombatPerkRanks.of(Map.of("A0062", 4));
        require(close(FrozenMartialOffenseService.criticalChanceBonus(ranks, true, true, true, true), 0.08D),
            "A0062 canonical chance input");
        require(close(FrozenMartialOffenseService.criticalChanceBonus(ranks, true, true, true, false), 0.0D),
            "non-physical attack gets no chance");
        require(close(FrozenMartialOffenseService.criticalChanceBonus(ranks, false, true, true, true), 0.0D),
            "client cannot resolve chance");
    }

    private static void criticalDamageRequiresTheCanonicalBoolean() {
        var ranks = FrozenCombatPerkRanks.of(Map.of("A0063", 3));
        var service = new FrozenMartialOffenseService();
        var normal = service.resolve(request(root("normal"), 0.5D, false, false, true, true), ranks, 2_000L);
        var critical = service.resolve(request(root("critical"), 0.5D, false, true, true, true), ranks, 2_001L);
        require(close(normal.criticalDamageMultiplier(), 1.0D), "A0063 cannot reclassify normal hit");
        require(close(critical.criticalDamageMultiplier(), 1.15D), "A0063 consumes canonical critical boolean");
    }

    private static void providerBoundAxesFailClosedWithoutSubstitution() {
        var ranks = FrozenCombatPerkRanks.of(Map.of("A0064", 4, "A0065", 4, "A0066", 4, "A0067", 4));
        require(close(FrozenMartialOffenseService.attackSpeedMultiplier(ranks, true), 1.08D), "A0064 safe speed provider");
        require(close(FrozenMartialOffenseService.attackSpeedMultiplier(ranks, false), 1.0D), "A0064 provider absent");
        require(close(FrozenMartialOffenseService.interruptionResistance(ranks, true, true), 0.16D), "A0067 during attack");
        require(close(FrozenMartialOffenseService.interruptionResistance(ranks, true, false), 0.0D), "A0067 outside attack");
        require(close(FrozenMartialOffenseService.interruptionResistance(ranks, false, true), 0.0D), "A0067 provider absent");

        var service = new FrozenMartialOffenseService();
        var absent = service.resolve(request(root("absent"), 0.5D, false, false, false, false), ranks, 3_000L);
        require(close(absent.damageMultiplier(), 1.0D), "A0065 never falls back to damage");
        require(close(absent.penetrationBonus(), 0.0D), "A0065 fail closed");
        require(close(absent.impactBonus(), 0.0D), "A0066 fail closed");
        var present = service.resolve(request(root("present"), 0.5D, false, false, true, true), ranks, 3_001L);
        require(close(present.penetrationBonus(), 0.08D), "A0065 armor-negation axis");
        require(close(present.impactBonus(), 0.12D), "A0066 impact axis");
    }

    private static void derivedAndDuplicateCallbacksCannotReapply() {
        var ranks = FrozenCombatPerkRanks.of(Map.of("A0061", 5));
        var service = new FrozenMartialOffenseService();
        var action = root("same-action");
        require(service.resolve(request(action, 0.5D, false, false, true, true), ranks, 4_000L).active(), "first callback");
        require(service.resolve(request(action.withSource("provider:duplicate"), 0.5D, false, false, true, true), ranks, 4_001L).duplicate(),
            "same action callback deduplicated");
        require(!service.resolve(request(action.child("proc"), 0.5D, false, false, true, true), ranks, 4_002L).active(),
            "derived damage cannot receive root modifiers");
    }

    private static FrozenMartialOffenseService.AttackRequest request(
        CanonicalActionIdentity action,
        double targetHealthBefore,
        boolean boss,
        boolean critical,
        boolean penetrationProvider,
        boolean impactProvider
    ) {
        return new FrozenMartialOffenseService.AttackRequest(
            action, true, true, true, true, true, targetHealthBefore, boss, critical,
            penetrationProvider, impactProvider
        );
    }

    private static CanonicalActionIdentity root(String id) { return CanonicalActionIdentity.root("player", id, "test"); }
    private static boolean close(double actual, double expected) { return Math.abs(actual - expected) < 0.000001D; }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
