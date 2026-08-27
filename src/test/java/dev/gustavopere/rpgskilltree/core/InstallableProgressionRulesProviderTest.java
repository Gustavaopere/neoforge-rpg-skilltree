package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Optional;

public final class InstallableProgressionRulesProviderTest {
    public static void main(String[] args) {
        startsUnconfiguredWithoutImplicitRules();
        installPublishesExactSnapshot();
        replacementIsAtomicAtProviderBoundary();
        clearReturnsProviderToUnconfiguredState();
        nullInstallIsRejected();
        System.out.println("InstallableProgressionRulesProviderTest: PASS");
    }

    static void startsUnconfiguredWithoutImplicitRules() {
        InstallableProgressionRulesProvider provider = new InstallableProgressionRulesProvider();
        eq(Optional.empty(), provider.current());
        expect(IllegalStateException.class, provider::requireCurrent);
    }

    static void installPublishesExactSnapshot() {
        InstallableProgressionRulesProvider provider = new InstallableProgressionRulesProvider();
        ProgressionRulesSnapshot rules = rules(1L, "rpgskilltree:test_install_a", 100L);
        provider.install(rules);
        same(rules, provider.current().orElseThrow());
        same(rules, provider.requireCurrent());
    }

    static void replacementIsAtomicAtProviderBoundary() {
        InstallableProgressionRulesProvider provider = new InstallableProgressionRulesProvider();
        ProgressionRulesSnapshot first = rules(1L, "rpgskilltree:test_install_a", 100L);
        ProgressionRulesSnapshot second = rules(2L, "rpgskilltree:test_install_b", 200L);
        provider.install(first);
        Optional<ProgressionRulesSnapshot> previous = provider.install(second);
        same(first, previous.orElseThrow());
        same(second, provider.requireCurrent());
    }

    static void clearReturnsProviderToUnconfiguredState() {
        InstallableProgressionRulesProvider provider = new InstallableProgressionRulesProvider();
        ProgressionRulesSnapshot rules = rules(1L, "rpgskilltree:test_install_a", 100L);
        provider.install(rules);
        Optional<ProgressionRulesSnapshot> previous = provider.clear();
        same(rules, previous.orElseThrow());
        eq(Optional.empty(), provider.current());
        expect(IllegalStateException.class, provider::requireCurrent);
    }

    static void nullInstallIsRejected() {
        InstallableProgressionRulesProvider provider = new InstallableProgressionRulesProvider();
        expect(NullPointerException.class, () -> provider.install(null));
    }

    private static ProgressionRulesSnapshot rules(long version, String id, long baseXp) {
        return new ProgressionRulesSnapshot(
            version,
            id,
            List.of(new LevelCurveBand(0L, baseXp, 0L)),
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
