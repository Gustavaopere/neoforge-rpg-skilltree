package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

public final class FrozenA0051A0060PolicyTest {
    public static void main(String[] args) {
        formulasStayOnTheirFrozenSemanticAxis();
        cadenceRequiresHitReloadAndSameStack();
        projectileEffectsResolveOnceAndFailClosedByComponent();
        adjustedMechanismConsumesThePreparedShotOnce();
        fistSequenceUsesCanonicalActionsAndExplicitProviderFacts();
        combinationFinalCarriesExactlyFiveReceiptActionsAndPersistentCooldown();
        System.out.println("FrozenA0051A0060PolicyTest: PASS");
    }

    private static void formulasStayOnTheirFrozenSemanticAxis() {
        var ranks = FrozenCombatPerkRanks.of(Map.of("A0051", 3, "A0055", 3, "A0056", 3, "A0057", 2));
        require(close(FrozenCombatOffensePolicy.crossbowCriticalChance(ranks), 0.09D), "A0051 crit");
        require(close(FrozenCombatOffensePolicy.fistDamageMultiplier(ranks), 1.09D), "A0055 damage");
        require(close(FrozenCombatOffensePolicy.fistAttackSpeedMultiplier(ranks, true), 1.06D), "A0056 speed");
        require(close(FrozenCombatOffensePolicy.fistAttackSpeedMultiplier(ranks, false), 1.0D),
            "A0056 must never fall back to stamina efficiency");
        require(close(FrozenCombatOffensePolicy.fistCriticalChance(ranks), 0.06D), "A0057 crit");
    }

    private static void cadenceRequiresHitReloadAndSameStack() {
        var service = new CrossbowCadenceService();
        var action = root("p", "shot-1");
        service.fire(shot(action, "projectile-1", "stack-a", 0, 0, false, false), 100L);
        require(!service.completeReload(reload("p", "stack-a", 2, 0, true, true), 200L).credited(),
            "reload before hit cannot credit");
        require(service.confirmHit("projectile-1", action, 300L), "first hit confirmation");
        require(!service.completeReload(reload("p", "stack-b", 2, 0, true, true), 400L).credited(),
            "different ItemStack identity cannot credit");
        var credited = service.completeReload(reload("p", "stack-a", 2, 0, true, true), 500L);
        require(credited.credited() && credited.charges() == 1, "same hit/reload cycle credits once");
        require(!service.completeReload(reload("p", "stack-a", 2, 0, true, true), 600L).credited(),
            "duplicate reload cannot credit");

        var action2 = root("p", "shot-2");
        service.fire(shot(action2, "projectile-2", "stack-a", 0, 0, false, false), 700L);
        service.confirmHit("projectile-2", action2, 800L);
        require(!service.completeReload(reload("p", "stack-a", 2, 0, true, false), 900L).credited(),
            "reload without native ammo consumption cannot credit");
        require(service.charges("p") == 1, "rejected reload keeps prior charge");
    }

    private static void projectileEffectsResolveOnceAndFailClosedByComponent() {
        var service = chargedService("p", "stack-a", 2, 0L);
        var noProvider = root("p", "no-provider");
        var inactive = service.fire(shot(noProvider, "p0", "stack-a", 2, 0, false, false), 2_000L);
        require(!inactive.active() && service.charges("p") == 2, "A0053 inactive cannot consume Cadence");
        service.confirmHit("p0", noProvider, 2_100L);

        var impactOnly = root("p", "impact-only");
        var effect = service.fire(shot(impactOnly, "p1", "stack-a", 2, 0, false, true), 2_200L);
        require(close(effect.penetrationBonus(), 0.0D) && close(effect.impactBonus(), 0.25D),
            "A0053 components fail closed independently");
        require(service.charges("p") == 0, "supported A0053 atomically consumes two charges");
        require(service.claimFirstImpact("p1", impactOnly, 2_300L).orElseThrow().active(), "first impact claim");
        require(service.claimFirstImpact("p1", impactOnly, 2_301L).isEmpty(), "piercing callback cannot resolve twice");
        require(service.fire(shot(impactOnly, "p2", "stack-a", 2, 0, true, true), 2_400L).duplicate(),
            "same canonical action cannot arm a second projectile");
        require(!service.fire(shot(impactOnly.child("proc"), "p3", "stack-a", 2, 0, true, true), 2_500L).active(),
            "derived projectile cannot consume Cadence");
    }

    private static void adjustedMechanismConsumesThePreparedShotOnce() {
        var service = chargedService("p", "stack-a", 3, 0L);
        var setup = root("p", "setup");
        service.fire(shot(setup, "setup-projectile", "stack-a", 0, 0, false, false), 4_000L);
        service.confirmHit("setup-projectile", setup, 4_100L);
        var armed = service.completeReload(reload("p", "stack-a", 2, 1, true, true, 100), 4_200L);
        require(armed.adjustedMechanismArmed() && armed.charges() == 3, "next reload at three charges arms A0054");

        var prepared = root("p", "prepared");
        var effect = service.fire(shot(prepared, "prepared-projectile", "stack-a", 0, 1, false, false), 4_300L);
        require(close(effect.damageBonus(), 0.15D) && service.charges("p") == 0, "A0054 prepared shot consumes all charges");
        require(service.claimFirstImpact("prepared-projectile", prepared, 4_400L).isPresent(), "prepared shot first impact");
        require(service.claimFirstImpact("prepared-projectile", prepared, 4_401L).isEmpty(), "prepared shot duplicate impact");
    }

    private static void fistSequenceUsesCanonicalActionsAndExplicitProviderFacts() {
        var service = new FistSequenceService();
        for (int i = 0; i < 3; i++) {
            var action = root("p", "fist-" + i);
            require(service.confirmedDirectHit(hit(action, 2), 1_000L + i * 100L) == i + 1, "sequence credit " + i);
            require(service.confirmedDirectHit(hit(action, 2), 1_001L + i * 100L) == i + 1, "duplicate hit dedup " + i);
        }
        var fakeHeavy = service.activate(finisher(root("p", "finalizer-false"), false, true, 2, 0), 1_500L);
        require(!fakeHeavy.active() && service.sequence("p") == 3, "damage coefficient cannot substitute heavy fact");
        var noProvider = service.activate(finisher(root("p", "finalizer-no-provider"), true, false, 2, 0), 1_600L);
        require(!noProvider.active() && service.sequence("p") == 3, "no safe A0059 component means no consumption");
        var impact = service.activate(finisher(root("p", "finalizer-impact"), true, true, 2, 0), 1_700L);
        require(close(impact.impactBonus(), 0.15D) && service.sequence("p") == 0, "A0059 explicit impact consumes three");
        require(service.confirmedDirectHit(hit(root("p", "derived").child("proc"), 2), 1_800L) == 0,
            "proc-depth cannot farm Sequence");
    }

    private static void combinationFinalCarriesExactlyFiveReceiptActionsAndPersistentCooldown() {
        var service = new FistSequenceService();
        for (int i = 0; i < 5; i++) service.confirmedDirectHit(hit(root("p", "receipt-" + i), 2), 5_000L + i * 100L);
        var activation = root("p", "combination");
        var result = service.activate(finisher(activation, true, true, 2, 1), 5_600L);
        require(result.combinationFinal() && result.receiptActions().size() == 5, "A0060 exact five-action receipt ledger");
        require(close(result.damageBonus(), 0.18D) && close(result.impactBonus(), 0.25D), "A0060 effect");
        require(service.activate(finisher(activation, true, true, 2, 1), 5_601L).duplicate(), "A0060 callback claim unique");
        service.clearTransient("p");
        require(!service.cooldownReady("p", 12_599L), "lifecycle clear cannot reset cooldown");
        require(service.cooldownReady("p", 12_600L), "mastery 90 seven-second cooldown");
    }

    private static CrossbowCadenceService chargedService(String actor, String stack, int target, long start) {
        var service = new CrossbowCadenceService();
        for (int i = 0; i < target; i++) {
            var action = root(actor, "charge-" + i);
            String projectile = "charge-projectile-" + i;
            service.fire(shot(action, projectile, stack, 0, 0, false, false), start + i * 300L);
            service.confirmHit(projectile, action, start + i * 300L + 100L);
            service.completeReload(reload(actor, stack, 2, 0, true, true), start + i * 300L + 200L);
        }
        require(service.charges(actor) == target, "fixture charge count");
        return service;
    }

    private static CrossbowCadenceService.ShotRequest shot(
        CanonicalActionIdentity action, String projectile, String stack, int a53, int a54, boolean penetration, boolean impact
    ) {
        return new CrossbowCadenceService.ShotRequest(action, projectile, stack, true, true, true, true, a53, a54, penetration, impact);
    }

    private static CrossbowCadenceService.ReloadRequest reload(
        String actor, String stack, int a52, int a54, boolean sameStack, boolean ammo
    ) { return reload(actor, stack, a52, a54, sameStack, ammo, 80); }

    private static CrossbowCadenceService.ReloadRequest reload(
        String actor, String stack, int a52, int a54, boolean sameStack, boolean ammo, int mastery
    ) {
        return new CrossbowCadenceService.ReloadRequest(actor, stack, true, true, sameStack, ammo, a52, a54, mastery);
    }

    private static FistSequenceService.HitRequest hit(CanonicalActionIdentity action, int rank) {
        return new FistSequenceService.HitRequest(action, true, true, true, true, true, rank);
    }

    private static FistSequenceService.FinisherRequest finisher(
        CanonicalActionIdentity action, boolean heavy, boolean impact, int a59, int a60
    ) {
        return new FistSequenceService.FinisherRequest(
            action, true, true, true, true, true, heavy, true, impact, impact, a59, a60, 90
        );
    }

    private static CanonicalActionIdentity root(String actor, String action) {
        return CanonicalActionIdentity.root(actor, action, "test");
    }

    private static boolean close(double actual, double expected) { return Math.abs(actual - expected) < 0.000001D; }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
