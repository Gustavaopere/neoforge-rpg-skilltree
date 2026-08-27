package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class CoreProgressionMutationServiceTest {
    public static void main(String[] args) {
        xpMutationDelegatesToCharacterProgressionAndPreservesAudit();
        pointMutationDelegatesToEconomyAndPreservesAudit();
        transactionRulesVersionMustMatchSnapshot();
        stateRulesMustMatchSnapshot();
        mainPerkBudgetIsEnforcedThroughMutationBoundary();
        attributeRankPurchaseIsAtomicAndPolicyPriced();
        attributeRankPurchaseRejectsInvalidCostAndInsufficientPoints();
        attributeRankPurchaseReplayIsIdempotent();
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

    private static void xpMutationDelegatesToCharacterProgressionAndPreservesAudit() {
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

    private static void attributeRankPurchaseIsAtomicAndPolicyPriced() {
        ProgressionRulesSnapshot rules = rules(7L, 3L);
        CoreProgressionState before = auditedState(rules);
        AttributeRankCostPolicy costPolicy = (attribute, currentRank) -> {
            eq(AttributeId.STRENGTH, attribute);
            eq(8L, currentRank);
            return 3L;
        };

        CoreProgressionState after = CoreProgressionMutationService.purchaseAttributeRank(
            before,
            AttributeId.STRENGTH,
            "attribute:strength/9",
            costPolicy,
            rules
        );

        eq(9L, after.attributeRanks().rank(AttributeId.STRENGTH));
        eq(5_000_000_000L, after.attributeRanks().rank(AttributeId.DETERMINATION));
        eq(3L, after.corePoints().allocated(CorePointAllocation.ATTRIBUTE));
        eq(7L, after.corePoints().available());
        eq(before.characterProgression(), after.characterProgression());
        auditUnchanged(before, after);
    }

    private static void attributeRankPurchaseRejectsInvalidCostAndInsufficientPoints() {
        ProgressionRulesSnapshot rules = rules(7L, 3L);
        CoreProgressionState state = auditedState(rules);

        expect(IllegalArgumentException.class, () -> CoreProgressionMutationService.purchaseAttributeRank(
            state,
            AttributeId.AGILITY,
            "attribute:agility/1",
            (attribute, currentRank) -> 0L,
            rules
        ));
        expect(IllegalArgumentException.class, () -> CoreProgressionMutationService.purchaseAttributeRank(
            state,
            AttributeId.AGILITY,
            "attribute:agility/1",
            (attribute, currentRank) -> 11L,
            rules
        ));
        eq(0L, state.attributeRanks().rank(AttributeId.AGILITY));
        eq(0L, state.corePoints().allocated(CorePointAllocation.ATTRIBUTE));
    }

    private static void attributeRankPurchaseReplayIsIdempotent() {
        ProgressionRulesSnapshot rules = rules(7L, 3L);
        CoreProgressionState initial = auditedState(rules);
        AttributeRankCostPolicy costPolicy = (attribute, currentRank) -> 2L;

        CoreProgressionState once = CoreProgressionMutationService.purchaseAttributeRank(
            initial,
            AttributeId.CHARISMA,
            "attribute:charisma/1",
            costPolicy,
            rules
        );
        CoreProgressionState replay = CoreProgressionMutationService.purchaseAttributeRank(
            once,
            AttributeId.CHARISMA,
            "attribute:charisma/1",
            costPolicy,
            rules
        );

        eq(1L, replay.attributeRanks().rank(AttributeId.CHARISMA));
        eq(2L, replay.corePoints().allocated(CorePointAllocation.ATTRIBUTE));
        eq(once, replay);
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
