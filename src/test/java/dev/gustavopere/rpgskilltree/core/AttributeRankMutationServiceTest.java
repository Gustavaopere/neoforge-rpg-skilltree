package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Objects;

public final class AttributeRankMutationServiceTest {
    public static void main(String[] args) {
        purchaseUsesExternalRangeCostAndAllocatesAtomically();
        replayDoesNotApplyRanksTwice();
        insufficientPointsLeaveRanksUntouched();
        refundUsesSameRangePolicyAndReleasesAllocation();
        invalidCostFailsClosed();
        rulesMismatchIsRejected();
        System.out.println("AttributeRankMutationServiceTest: PASS");
    }

    private static ProgressionRulesSnapshot rules(long version) {
        return new ProgressionRulesSnapshot(
            version,
            "rpgskilltree:attribute_rank_mutation_test",
            List.of(new LevelCurveBand(0L, 100L, 0L)),
            new MainPerkBudget(30L)
        );
    }

    private static CoreProgressionState funded(ProgressionRulesSnapshot rules, long points) {
        CorePointLedger ledger = CorePointLedger.empty().apply(
            CorePointTransaction.credit(
                "test:funding",
                CorePointTransactionKind.EARN,
                points,
                "test:quest",
                rules.version()
            )
        );
        return CoreProgressionState.nativeState(CharacterProgressionState.empty(), ledger, rules);
    }

    private static AttributeRankCostPolicy triangularCost() {
        return (attribute, startRank, rankCount) -> {
            long total = 0L;
            for (long offset = 0L; offset < rankCount; offset++) {
                total = Math.addExact(total, Math.addExact(startRank, offset) + 1L);
            }
            return total;
        };
    }

    private static void purchaseUsesExternalRangeCostAndAllocatesAtomically() {
        ProgressionRulesSnapshot rules = rules(4L);
        CoreProgressionState before = funded(rules, 20L);

        CoreProgressionState after = AttributeRankMutationService.purchase(
            before,
            AttributeId.STRENGTH,
            3L,
            "attribute:strength:first_three",
            "ui:attributes",
            triangularCost(),
            rules
        );

        eq(3L, after.attributeRanks().rank(AttributeId.STRENGTH));
        eq(6L, after.corePoints().allocated(CorePointAllocation.ATTRIBUTE));
        eq(14L, after.corePoints().available());
    }

    private static void replayDoesNotApplyRanksTwice() {
        ProgressionRulesSnapshot rules = rules(4L);
        CoreProgressionState first = AttributeRankMutationService.purchase(
            funded(rules, 20L),
            AttributeId.INTELLIGENCE,
            2L,
            "attribute:intelligence:first_two",
            "ui:attributes",
            triangularCost(),
            rules
        );
        CoreProgressionState replay = AttributeRankMutationService.purchase(
            first,
            AttributeId.INTELLIGENCE,
            2L,
            "attribute:intelligence:first_two",
            "ui:attributes",
            triangularCost(),
            rules
        );

        eq(first, replay);
        eq(2L, replay.attributeRanks().rank(AttributeId.INTELLIGENCE));
        eq(3L, replay.corePoints().allocated(CorePointAllocation.ATTRIBUTE));
    }

    private static void insufficientPointsLeaveRanksUntouched() {
        ProgressionRulesSnapshot rules = rules(4L);
        CoreProgressionState before = funded(rules, 2L);

        expect(IllegalArgumentException.class, () -> AttributeRankMutationService.purchase(
            before,
            AttributeId.CONSTITUTION,
            2L,
            "attribute:constitution:first_two",
            "ui:attributes",
            triangularCost(),
            rules
        ));
        eq(0L, before.attributeRanks().rank(AttributeId.CONSTITUTION));
        eq(0L, before.corePoints().allocated(CorePointAllocation.ATTRIBUTE));
    }

    private static void refundUsesSameRangePolicyAndReleasesAllocation() {
        ProgressionRulesSnapshot rules = rules(4L);
        AttributeRankCostPolicy policy = triangularCost();
        CoreProgressionState purchased = AttributeRankMutationService.purchase(
            funded(rules, 20L),
            AttributeId.AGILITY,
            3L,
            "attribute:agility:first_three",
            "ui:attributes",
            policy,
            rules
        );

        CoreProgressionState refunded = AttributeRankMutationService.refund(
            purchased,
            AttributeId.AGILITY,
            2L,
            "attribute:agility:refund_top_two",
            "ui:attributes",
            policy,
            rules
        );

        eq(1L, refunded.attributeRanks().rank(AttributeId.AGILITY));
        eq(1L, refunded.corePoints().allocated(CorePointAllocation.ATTRIBUTE));
        eq(19L, refunded.corePoints().available());
    }

    private static void invalidCostFailsClosed() {
        ProgressionRulesSnapshot rules = rules(4L);
        CoreProgressionState before = funded(rules, 20L);
        AttributeRankCostPolicy zero = (attribute, startRank, rankCount) -> 0L;
        AttributeRankCostPolicy negative = (attribute, startRank, rankCount) -> -1L;

        expect(IllegalArgumentException.class, () -> AttributeRankMutationService.purchase(
            before, AttributeId.CHARISMA, 1L, "bad:zero", "test", zero, rules));
        expect(IllegalArgumentException.class, () -> AttributeRankMutationService.purchase(
            before, AttributeId.CHARISMA, 1L, "bad:negative", "test", negative, rules));
    }

    private static void rulesMismatchIsRejected() {
        ProgressionRulesSnapshot original = rules(4L);
        ProgressionRulesSnapshot changed = rules(5L);
        CoreProgressionState before = funded(original, 20L);
        expect(IllegalStateException.class, () -> AttributeRankMutationService.purchase(
            before,
            AttributeId.DETERMINATION,
            1L,
            "attribute:determination:first",
            "ui:attributes",
            triangularCost(),
            changed
        ));
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
