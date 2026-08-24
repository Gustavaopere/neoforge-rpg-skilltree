package dev.gustavopere.rpgskilltree.core;

import java.util.List;

/** P-0033 canonical sustain resolution and shared moving cap. */
public final class SustainResolverTest {
    public static void main(String[] args) {
        selectsOneHighestCoefficientAndOneResolution();
        nativeCorrelatedHealingIsCountedBeforeSkillTreeHealing();
        finalPostModifierCapMovesWithoutCarryOver();
        ambiguityProcDepthAndOwnershipFailClosed();
        System.out.println("SustainResolverTest: PASS");
    }

    private static void selectsOneHighestCoefficientAndOneResolution() {
        var resolver = new SustainResolver();
        var action = root("one");
        var result = resolver.resolve(request(action, 100.0D, 100.0D, 100.0D, 1.0D,
            SustainResolver.NativeCorrelation.NONE, 0.0D,
            List.of(candidate("A0082", 0.018D), candidate("A0084", 0.015D), candidate("A0086", 0.01D))), 0L);
        require(result.status() == SustainResolver.Status.AUTHORIZED, "first event authorized");
        require(close(result.selectedCoefficient(), 0.018D) && close(result.skillTreeHealing(), 1.8D), "highest coefficient, not sum");
        var duplicate = resolver.resolve(request(action.withSource("second-adapter"), 100, 100, 100, 1,
            SustainResolver.NativeCorrelation.NONE, 0, List.of(candidate("other", 0.018D))), 1L);
        require(duplicate.status() == SustainResolver.Status.DUPLICATE_EVENT && close(duplicate.skillTreeHealing(), 0.0D), "same event resolves once");
    }

    private static void nativeCorrelatedHealingIsCountedBeforeSkillTreeHealing() {
        var resolver = new SustainResolver();
        var exact = resolver.resolve(request(root("native"), 100, 100, 99, 1,
            SustainResolver.NativeCorrelation.EXACT_INTERCEPTED, 1.0D, List.of(candidate("A0082", 0.018D))), 0L);
        require(close(exact.nativeHealingCounted(), 1.0D), "concrete native heal counted");
        require(close(exact.skillTreeHealing(), 0.8D), "Skill Tree fills only coefficient remainder");
        var ambiguous = resolver.resolve(request(root("ambiguous-native"), 100, 100, 100, 1,
            SustainResolver.NativeCorrelation.AMBIGUOUS, 0.0D, List.of(candidate("A0082", 0.018D))), 1L);
        require(ambiguous.status() == SustainResolver.Status.AMBIGUOUS_NATIVE_FAIL_CLOSED, "ambiguous native integration cannot add second heal");
    }

    private static void finalPostModifierCapMovesWithoutCarryOver() {
        var resolver = new SustainResolver();
        var first = resolver.resolve(request(root("cap-1"), 100, 100, 100, 1.5D,
            SustainResolver.NativeCorrelation.NONE, 0, List.of(candidate("source", 0.02D))), 0L);
        require(close(first.skillTreeHealing(), 3.0D), "healing modifier applied before final 3% cap");
        var blocked = resolver.resolve(request(root("cap-2"), 100, 100, 100, 1,
            SustainResolver.NativeCorrelation.NONE, 0, List.of(candidate("source", 0.02D))), 19L);
        require(close(blocked.skillTreeHealing(), 0.0D), "shared cap has no extra consumer allowance");
        var moved = resolver.resolve(request(root("cap-3"), 100, 100, 100, 1,
            SustainResolver.NativeCorrelation.NONE, 0, List.of(candidate("source", 0.02D))), 20L);
        require(close(moved.skillTreeHealing(), 2.0D), "old payment expires; no unused carry-over exists");
    }

    private static void ambiguityProcDepthAndOwnershipFailClosed() {
        var resolver = new SustainResolver();
        require(resolver.resolve(request(root("proc").child("derived"), 100, 100, 100, 1,
            SustainResolver.NativeCorrelation.NONE, 0, List.of(candidate("A0085", 0.0105D))), 0L).status()
            == SustainResolver.Status.INELIGIBLE, "derived periodic damage cannot farm sustain");
        var noOwner = new SustainResolver.Request(root("owner"), true, true, false, 100, 100, 100, 100, 1,
            SustainResolver.NativeCorrelation.NONE, 0, List.of(candidate("A0082", 0.018D)));
        require(resolver.resolve(noOwner, 1L).status() == SustainResolver.Status.INELIGIBLE, "owner must be proven");
        var samePulse = root("periodic-pulse");
        require(resolver.resolve(request(samePulse, 100, 100, 100, 1, SustainResolver.NativeCorrelation.NONE, 0,
            List.of(candidate("A0085", 0.0105D))), 2L).status() == SustainResolver.Status.AUTHORIZED, "first periodic pulse");
        require(resolver.resolve(request(samePulse.withSource("second-target"), 100, 100, 100, 1,
            SustainResolver.NativeCorrelation.NONE, 0, List.of(candidate("A0085", 0.0105D))), 3L).status()
            == SustainResolver.Status.DUPLICATE_EVENT, "one application/pulse cannot multiply by targets/adapters");
    }

    private static SustainResolver.Request request(CanonicalActionIdentity action, double damage, double healthBefore,
        double missingAfterNative, double healingMultiplier, SustainResolver.NativeCorrelation nativeCorrelation,
        double nativeHealing, List<SustainResolver.Candidate> candidates) {
        return new SustainResolver.Request(action, true, true, true, damage, healthBefore, 100.0D,
            missingAfterNative, healingMultiplier, nativeCorrelation, nativeHealing, candidates);
    }
    private static SustainResolver.Candidate candidate(String id, double coefficient) {
        return new SustainResolver.Candidate(id, coefficient, true);
    }
    private static CanonicalActionIdentity root(String id) { return CanonicalActionIdentity.root("player", id, "test"); }
    private static boolean close(double a, double b) { return Math.abs(a - b) < 0.000001D; }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
