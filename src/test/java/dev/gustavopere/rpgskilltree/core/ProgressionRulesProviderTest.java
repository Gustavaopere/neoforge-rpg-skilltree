package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Optional;

public final class ProgressionRulesProviderTest {
    public static void main(String[] args) {
        unconfiguredProviderHasNoImplicitSnapshot();
        fixedProviderReturnsExactlyInstalledSnapshot();
        requireCurrentFailsClosedWhenUnconfigured();
        nullProviderResultIsRejected();
        System.out.println("ProgressionRulesProviderTest: PASS");
    }

    static void unconfiguredProviderHasNoImplicitSnapshot() {
        ProgressionRulesProvider provider = ProgressionRulesProvider.unconfigured();
        eq(Optional.empty(), provider.current());
    }

    static void fixedProviderReturnsExactlyInstalledSnapshot() {
        ProgressionRulesSnapshot rules = rules();
        ProgressionRulesProvider provider = ProgressionRulesProvider.fixed(rules);
        same(rules, provider.current().orElseThrow());
        same(rules, provider.requireCurrent());
    }

    static void requireCurrentFailsClosedWhenUnconfigured() {
        ProgressionRulesProvider provider = ProgressionRulesProvider.unconfigured();
        expect(IllegalStateException.class, provider::requireCurrent);
    }

    static void nullProviderResultIsRejected() {
        ProgressionRulesProvider provider = () -> null;
        expect(IllegalStateException.class, provider::requireCurrent);
    }

    private static ProgressionRulesSnapshot rules() {
        return new ProgressionRulesSnapshot(
            7L,
            "rpgskilltree:test_rules_provider",
            List.of(new LevelCurveBand(0L, 100L, 0L)),
            new MainPerkBudget(0L)
        );
    }

    private static void expect(Class<? extends Throwable> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable thrown) {
            if (type.isInstance(thrown)) return;
            throw new AssertionError("expected " + type.getSimpleName() + " but got " + thrown, thrown);
        }
        throw new AssertionError("expected " + type.getSimpleName());
    }

    private static void same(Object expected, Object actual) {
        if (expected != actual) throw new AssertionError("expected same instance");
    }

    private static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError(expected + " != " + actual);
        }
    }
}
