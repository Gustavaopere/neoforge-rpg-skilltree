package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class CoreProgressionMutationServiceTest {
    public static void main(String[] args) {
        xpMutationAwardsConfiguredLevelCorePointsAtomically();
        xpMutationWithoutConfiguredAwardsPreservesLedger();
        pointMutationDelegatesToEconomyAndPreservesAudit();
        transactionRulesVersionMustMatchSnapshot();
        stateRulesMustMatchSnapshot();
        mainPerkBudgetIsEnforcedThroughMutationBoundary();
        System.out.println("CoreProgressionMutationServiceTest: PASS");
    }

    private static ProgressionRulesSnapshot rules(long version, long mainPerkBudget) {
        return new ProgressionRulesSnapshot(
            version,
            "rpgskilltree:mutation_test",
            List.of(new LevelCurveBand(0L, 100L, 0L)),
            new MainPerkBudget(mainPerkBudget)
        );
    }

    private static ProgressionRulesSnapshot rules(
        long version,
        long mainPerkBudget,
        LevelCorePointAwardPolicy levelPointPolicy
    ) {
        return new ProgressionRulesSnapshot(
            version,
            "rpgskilltree:mutation_test",
            List.of(new LevelCurveBand(0L, 100L, 0L)),
            new MainPerkBudget(mainPerkBudget),
            levelPointPolicy
        );
    }

    private static CoreProgressionState auditedState(ProgressionRulesSnapshot rules) {
        CorePointLedger ledger = CorePointLedger.empty().apply(
            CorePointTransaction.credit("migration:seed", CorePointTransactionKind.MIGRATION, 10L, "legacy", rules.version())
        );
        AttributeRanks attributes = AttributeRanks.of(Map.of(
            AttributeId.STRENGTH, 8L,
            AttributeId.DETERMINATION, 5_000_000_000L
        ));
        return new CoreProgressionState(
            new CharacterProgressionState(2L, 50L),
            ledger,
            attributes,
            rules.version(),
            rules.fingerprint(),
            4,
            123L
        );
    }

    private static void xpMutationAwardsConfiguredLevelCorePointsAtomically() {
        ProgressionRulesSnapshot rules = rules(
            7L,
            3L,
            new PeriodicLevelCorePointAwardPolicy(3L, 2L, 2L)
        );
        CoreProgressionState before = auditedState(rules);
        CoreProgressionState after = CoreProgressionMutationService.grantXp(before, 250L, rules);

        eq(5L, after.characterProgression().level());
        eq(0L, after.characterProgression().xpIntoLevel());
        eq(14L, after.corePoints().totalCredits());
        eq(14L, after.corePoints().available());
        eq(4L, after.corePoints().creditTotalsBySource().get("character_level"));
        eq(before.corePoints().transactions().size() + 1, after.corePoints().transactions().size());

        CorePointTransaction award = after.corePoints().transactions().getLast();
        eq(CorePointTransactionKind.EARN, award.kind());
        eq(4L, award.amount());
        eq("character_level", award.sourceId());
        eq(CorePointAllocation.NONE, award.allocation());
        eq(rules.version(), award.rulesVersion());

        eq(before.attributeRanks(), after.attributeRanks());
        auditUnchanged(before, after);
    }

    private static void xpMutationWithoutConfiguredAwardsPreservesLedger() {
        ProgressionRulesSnapshot rules = rules(7L, 3L);
        CoreProgressionState before = auditedState(rules);
        CoreProgressionState after = CoreProgressionMutationService.grantXp(before, 250L, rules);

        eq(5L, after.characterProgression().level());
        eq(0L, after.characterProgression().xpIntoLevel());
        sameLedger(before.corePoints(), after.corePoints());
        eq(before.attributeRanks(), after.attributeRanks());
        auditUnchanged(before, after);
    }

    private static void pointMutationDelegatesToEconomyAndPreservesAudit() {
        ProgressionRulesSnapshot rules = rules(7L, 3L);
        CoreProgressionState before = auditedState(rules);
        CorePointTransaction transaction = CorePointTransaction.allocate(
            "perk:a0001",
            CorePointTransactionKind.SPEND,
            2L,
            "node:a0001",
            CorePointAllocation.MAIN_PERK,
            rules.version()
        );

        CoreProgressionState after = CoreProgressionMutationService.applyCorePointTransaction(before, transaction, rules);
        eq(2L, after.corePoints().allocated(CorePointAllocation.MAIN_PERK));
        eq(8L, after.corePoints().available());
        eq(before.characterProgression(), after.characterProgression());
        eq(before.attributeRanks(), after.attributeRanks());
        auditUnchanged(before, after);
    }

    private static void transactionRulesVersionMustMatchSnapshot() {
        ProgressionRulesSnapshot rules = rules(7L, 3L);
        CoreProgressionState state = auditedState(rules);
        CorePointTransaction stale = CorePointTransaction.credit(
            "quest:stale",
            CorePointTransactionKind.EARN,
            1L,
            "quest:stale",
            6L
        );
        expect(IllegalArgumentException.class, () ->
            CoreProgressionMutationService.applyCorePointTransaction(state, stale, rules));
    }

    private static void stateRulesMustMatchSnapshot() {
        ProgressionRulesSnapshot original = rules(7L, 3L);
        CoreProgressionState state = auditedState(original);
        ProgressionRulesSnapshot changed = rules(8L, 3L);

        expect(IllegalStateException.class, () -> CoreProgressionMutationService.grantXp(state, 1L, changed));
        CorePointTransaction transaction = CorePointTransaction.credit(
            "quest:new",
            CorePointTransactionKind.EARN,
            1L,
            "quest:new",
            changed.version()
        );
        expect(IllegalStateException.class, () ->
            CoreProgressionMutationService.applyCorePointTransaction(state, transaction, changed));
    }

    private static void mainPerkBudgetIsEnforcedThroughMutationBoundary() {
        ProgressionRulesSnapshot rules = rules(7L, 1L);
        CoreProgressionState state = auditedState(rules);
        CorePointTransaction tooMuch = CorePointTransaction.allocate(
            "perk:too_much",
            CorePointTransactionKind.SPEND,
            2L,
            "node:a0001",
            CorePointAllocation.MAIN_PERK,
            rules.version()
        );
        expect(IllegalArgumentException.class, () ->
            CoreProgressionMutationService.applyCorePointTransaction(state, tooMuch, rules));
    }

    private static void auditUnchanged(CoreProgressionState before, CoreProgressionState after) {
        eq(before.rulesVersion(), after.rulesVersion());
        eq(before.rulesFingerprint(), after.rulesFingerprint());
        eq(before.migrationSourceFormatVersion(), after.migrationSourceFormatVersion());
        eq(before.discardedLegacyCapXp(), after.discardedLegacyCapXp());
    }

    private static void sameLedger(CorePointLedger expected, CorePointLedger actual) {
        eq(expected.checkpoint(), actual.checkpoint());
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
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
