package dev.gustavopere.rpgskilltree.core;

import java.util.List;

/** Focused RED/GREEN contract for the canonical incoming mitigation combiner. */
public final class DamageMitigationResolverTest {
    public static void main(String[] args) {
        DamageMitigationResolver.Result result = DamageMitigationResolver.resolve(100.0D, List.of(
            new DamageMitigationResolver.Contribution("a0092", 0.08D),
            new DamageMitigationResolver.Contribution("a0096", 0.15D),
            new DamageMitigationResolver.Contribution("a0101", 0.08D),
            new DamageMitigationResolver.Contribution("a0101", 0.08D)
        ));
        checkClose(result.damage(), 100.0D * 0.92D * 0.85D * 0.92D, "multiplicative ordered reduction");
        check(result.appliedIds().size() == 3, "duplicate contributor must apply once");
        check(result.appliedIds().containsAll(List.of("a0092", "a0096", "a0101")), "applied ids");

        DamageMitigationResolver.Result clamped = DamageMitigationResolver.resolve(40.0D, List.of(
            new DamageMitigationResolver.Contribution("zero", -1.0D),
            new DamageMitigationResolver.Contribution("full", 2.0D)
        ));
        checkClose(clamped.damage(), 0.0D, "fractions clamp to [0,1]");
        checkClose(DamageMitigationResolver.resolve(-5.0D, List.of()).damage(), 0.0D, "incoming damage cannot go negative");
        System.out.println("DamageMitigationResolverTest: OK");
    }

    private static void checkClose(double actual, double expected, String message) {
        if (Math.abs(actual - expected) > 1.0E-9D) throw new AssertionError(message + ": expected=" + expected + ", actual=" + actual);
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
