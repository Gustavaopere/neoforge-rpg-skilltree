package dev.gustavopere.rpgskilltree.core;

import java.util.List;

/** Chat 3 behavioral validation for the exact A0081-A0090 sustain/recovery batch. */
public final class A0081A0090Chat3ValidationMain {
    public static void main(String[] args) {
        sustainClipsOverkillAndMissingHealth();
        sustainCountsExactNativeHealingInsideSharedCap();
        sustainFailsClosedAndDeduplicatesPerActorRoot();
        sustainBucketsAreActorIsolatedAndClearable();
        recoveryClipsOverkillAndDeduplicatesRoots();
        recoveryUsesFrozenSnapshotAcrossExactlyFourInstallments();
        recoveryInvalidatesPendingInstallmentOnHostileDamage();
        System.out.println("A0081A0090Chat3ValidationMain: PASS");
    }

    private static void sustainClipsOverkillAndMissingHealth() {
        SustainResolver overkill = new SustainResolver();
        var clipped = overkill.resolve(request("p", "overkill", 100.0D, 10.0D, 100.0D, 100.0D,
            SustainResolver.NativeCorrelation.NONE, 0.0D, List.of(0.018D)), 0L);
        close(clipped.skillTreeHealing(), 0.18D, "overkill must use target pre-impact health");

        SustainResolver missing = new SustainResolver();
        var missingClipped = missing.resolve(request("p", "missing", 100.0D, 100.0D, 100.0D, 0.25D,
            SustainResolver.NativeCorrelation.NONE, 0.0D, List.of(0.018D)), 0L);
        close(missingClipped.skillTreeHealing(), 0.25D, "missing health must cap final Skill Tree heal");
    }

    private static void sustainCountsExactNativeHealingInsideSharedCap() {
        SustainResolver resolver = new SustainResolver();
        var result = resolver.resolve(request("p", "native", 100.0D, 100.0D, 100.0D, 100.0D,
            SustainResolver.NativeCorrelation.EXACT_INTERCEPTED, 2.0D, List.of(0.05D)), 0L);
        require(result.status() == SustainResolver.Status.AUTHORIZED, "exact native receipt should be authorized");
        close(result.nativeHealingCounted(), 2.0D, "native heal must be counted first");
        close(result.skillTreeHealing(), 1.0D, "Skill Tree may only fill remainder of 3% shared cap");
        close(result.bucketUsed(), 3.0D, "native plus Skill Tree healing share one bucket");
    }

    private static void sustainFailsClosedAndDeduplicatesPerActorRoot() {
        SustainResolver resolver = new SustainResolver();
        var ambiguous = resolver.resolve(request("p", "ambiguous", 100.0D, 100.0D, 100.0D, 100.0D,
            SustainResolver.NativeCorrelation.AMBIGUOUS, 0.0D, List.of(0.018D)), 0L);
        require(ambiguous.status() == SustainResolver.Status.AMBIGUOUS_NATIVE_FAIL_CLOSED,
            "ambiguous native lifesteal must fail closed");
        close(ambiguous.skillTreeHealing(), 0.0D, "ambiguous source cannot receive Skill Tree healing");

        var retry = resolver.resolve(request("p", "ambiguous", 100.0D, 100.0D, 100.0D, 100.0D,
            SustainResolver.NativeCorrelation.NONE, 0.0D, List.of(0.018D)), 1L);
        require(retry.status() == SustainResolver.Status.DUPLICATE_EVENT,
            "same causal root must not be reopened after ambiguous resolution");
    }

    private static void sustainBucketsAreActorIsolatedAndClearable() {
        SustainResolver resolver = new SustainResolver();
        var p1 = resolver.resolve(request("p1", "root", 100.0D, 100.0D, 100.0D, 100.0D,
            SustainResolver.NativeCorrelation.NONE, 0.0D, List.of(0.05D)), 0L);
        var p2 = resolver.resolve(request("p2", "root", 100.0D, 100.0D, 100.0D, 100.0D,
            SustainResolver.NativeCorrelation.NONE, 0.0D, List.of(0.05D)), 0L);
        close(p1.skillTreeHealing(), 3.0D, "p1 independent cap");
        close(p2.skillTreeHealing(), 3.0D, "p2 independent cap");

        resolver.clearActor("p1");
        var reopenedAfterLifecycleClear = resolver.resolve(request("p1", "root", 100.0D, 100.0D, 100.0D, 100.0D,
            SustainResolver.NativeCorrelation.NONE, 0.0D, List.of(0.01D)), 1L);
        require(reopenedAfterLifecycleClear.status() == SustainResolver.Status.AUTHORIZED,
            "explicit actor lifecycle clear must remove causal claims and bucket state");
    }

    private static void recoveryClipsOverkillAndDeduplicatesRoots() {
        CombatRecoveryService recovery = new CombatRecoveryService();
        var request = new CombatRecoveryService.DamageRequest(
            "p", "hit", true, true, true, true, true, 100.0D, 100.0D, 10.0D, 3
        );
        close(recovery.recordDamage(request, 0L), 2.5D, "recovery must clip overkill before rank coefficient");
        close(recovery.recordDamage(request, 1L), 0.0D, "recovery must claim one causal root once");
    }

    private static void recoveryUsesFrozenSnapshotAcrossExactlyFourInstallments() {
        CombatRecoveryService recovery = new CombatRecoveryService();
        close(recovery.recordDamage(new CombatRecoveryService.DamageRequest(
            "p", "hit-1", true, true, true, true, true, 100.0D, 32.0D, 32.0D, 3
        ), 0L), 8.0D, "reserve must cap at 8% max health");

        for (int i = 0; i < 4; i++) {
            long now = 3_000L + i * 1_000L;
            var installment = recovery.offerInstallment("p", 100.0D, 100.0D, now).orElseThrow();
            close(installment.attemptedHealing(), 2.0D, "each installment is 25% of frozen 8 HP snapshot");
            require(recovery.confirmHealed(installment, 2.0D), "offered installment must confirm once");
        }
        close(recovery.reserve("p", 6_000L), 0.0D, "four full installments must consume the reserve");
        require(recovery.offerInstallment("p", 100.0D, 100.0D, 7_000L).isEmpty(),
            "fifth installment is forbidden");
    }

    private static void recoveryInvalidatesPendingInstallmentOnHostileDamage() {
        CombatRecoveryService recovery = new CombatRecoveryService();
        recovery.recordDamage(new CombatRecoveryService.DamageRequest(
            "p", "hit-2", true, true, true, true, true, 100.0D, 20.0D, 20.0D, 3
        ), 0L);
        var stale = recovery.offerInstallment("p", 100.0D, 100.0D, 3_000L).orElseThrow();
        recovery.recordHostileDamage("p", true, 3_001L);
        require(!recovery.confirmHealed(stale, stale.attemptedHealing()),
            "hostile damage must invalidate a previously offered installment token");
        require(recovery.offerInstallment("p", 100.0D, 100.0D, 6_000L).isEmpty(),
            "new phase cannot restart before three full seconds without hostile damage");
        require(recovery.offerInstallment("p", 100.0D, 100.0D, 6_001L).isPresent(),
            "new phase may restart at the three-second boundary");
        close(recovery.reserve("p", 13_002L), 0.0D, "reserve must expire after ten seconds out of combat");
    }

    private static SustainResolver.Request request(
        String actor,
        String root,
        double postDamage,
        double targetHealthBefore,
        double maxHealth,
        double missingHealth,
        SustainResolver.NativeCorrelation nativeCorrelation,
        double nativeHealing,
        List<Double> candidates
    ) {
        return new SustainResolver.Request(
            actor, root, true, true, true, postDamage, targetHealthBefore, maxHealth, missingHealth,
            1.0D, nativeCorrelation, nativeHealing, candidates
        );
    }

    private static void close(double actual, double expected, String message) {
        if (Math.abs(actual - expected) > 1.0e-9D) {
            throw new AssertionError(message + ": " + actual + " != " + expected);
        }
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
