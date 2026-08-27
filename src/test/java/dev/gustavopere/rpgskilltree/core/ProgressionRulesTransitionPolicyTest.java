package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Optional;

public final class ProgressionRulesTransitionPolicyTest {
    public static void main(String[] args) {
        initialInstallIsAllowed();
        exactReloadIsIdempotent();
        sameVersionDifferentFingerprintRequiresVersionBump();
        differentVersionRequiresExplicitMigrationBoundary();
        rulesIdChangeRequiresExplicitMigrationBoundary();
        System.out.println("ProgressionRulesTransitionPolicyTest: PASS");
    }

    static void initialInstallIsAllowed() {
        ProgressionRulesSnapshot next = rules(1L, "rpgskilltree:core", 100L, 30L);
        eq(ProgressionRulesTransition.INITIAL_INSTALL,
            ProgressionRulesTransitionPolicy.classify(Optional.empty(), next));
    }

    static void exactReloadIsIdempotent() {
        ProgressionRulesSnapshot current = rules(1L, "rpgskilltree:core", 100L, 30L);
        ProgressionRulesSnapshot identical = rules(1L, "rpgskilltree:core", 100L, 30L);
        eq(ProgressionRulesTransition.IDENTICAL_RELOAD,
            ProgressionRulesTransitionPolicy.classify(Optional.of(current), identical));
        ProgressionRulesTransitionPolicy.requireLiveReloadSafe(Optional.of(current), identical);
    }

    static void sameVersionDifferentFingerprintRequiresVersionBump() {
        ProgressionRulesSnapshot current = rules(1L, "rpgskilltree:core", 100L, 30L);
        ProgressionRulesSnapshot changed = rules(1L, "rpgskilltree:core", 110L, 30L);
        eq(ProgressionRulesTransition.SAME_VERSION_CONTENT_CHANGED,
            ProgressionRulesTransitionPolicy.classify(Optional.of(current), changed));
        expect(IllegalStateException.class,
            () -> ProgressionRulesTransitionPolicy.requireLiveReloadSafe(Optional.of(current), changed));
    }

    static void differentVersionRequiresExplicitMigrationBoundary() {
        ProgressionRulesSnapshot current = rules(1L, "rpgskilltree:core", 100L, 30L);
        ProgressionRulesSnapshot changed = rules(2L, "rpgskilltree:core", 110L, 30L);
        eq(ProgressionRulesTransition.VERSION_CHANGED,
            ProgressionRulesTransitionPolicy.classify(Optional.of(current), changed));
        expect(IllegalStateException.class,
            () -> ProgressionRulesTransitionPolicy.requireLiveReloadSafe(Optional.of(current), changed));
    }

    static void rulesIdChangeRequiresExplicitMigrationBoundary() {
        ProgressionRulesSnapshot current = rules(1L, "rpgskilltree:core", 100L, 30L);
        ProgressionRulesSnapshot changed = rules(1L, "rpgskilltree:alternate", 100L, 30L);
        eq(ProgressionRulesTransition.RULES_ID_CHANGED,
            ProgressionRulesTransitionPolicy.classify(Optional.of(current), changed));
        expect(IllegalStateException.class,
            () -> ProgressionRulesTransitionPolicy.requireLiveReloadSafe(Optional.of(current), changed));
    }

    private static ProgressionRulesSnapshot rules(long version, String id, long baseXp, long budget) {
        return new ProgressionRulesSnapshot(
            version,
            id,
            List.of(new LevelCurveBand(0L, baseXp, 0L)),
            new MainPerkBudget(budget)
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

    private static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError(expected + " != " + actual);
        }
    }
}
