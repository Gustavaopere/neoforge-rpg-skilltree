package dev.gustavopere.rpgskilltree.core;

public final class ExactStaminaReceiptCorrelationTest {
    public static void main(String[] args) {
        debitBindsOnlyThroughItsOwnExecutionAndPlayback();
        twoExecutionsInSameTickRemainDistinct();
        reusedAnimationGetsNewPlaybackGeneration();
        multiHitAndMultiTargetReuseFirstCanonicalAction();
        duplicateEvidenceIsNotASecondPhysicalDebit();
        twoDistinctPositiveDebitsFailClosed();
        debitWithoutActionAnimationBecomesUncorrelated();
        unrelatedLaterAttackCannotBorrowUncorrelatedDebit();
        animationEndingBeforeActionBindingFailsClosed();
        actorCleanupDropsExecutionsPlaybacksAndBindings();
        expiredStateCannotCorrelate();
        System.out.println("ExactStaminaReceiptCorrelationTest: PASS");
    }

    private static void debitBindsOnlyThroughItsOwnExecutionAndPlayback() {
        var correlation = correlation();
        var execution = correlation.beginExecution("p", "epicfight:skill_a", 1_000L);
        require(correlation.recordDebit(execution, evidence("e1", 8.0D), 1_000L)
            == ExactStaminaReceiptCorrelation.DebitStatus.RECORDED, "first exact debit recorded");
        var playback = correlation.startAction(execution, "epicfight:anim_a", 1_001L).orElseThrow();
        require(playback.generation() == 1L, "first playback generation");
        correlation.endExecution(execution, 1_002L);

        var action = root("a1");
        var bound = correlation.bindCanonicalAction("p", "epicfight:anim_a", action, 1_003L);
        require(bound.status() == ExactStaminaReceiptCorrelation.BindStatus.CORRELATED, "same playback correlates");
        require(close(bound.correlatedDebit().orElseThrow().actualDebit(), 8.0D), "exact debit preserved");
        require(bound.correlatedDebit().orElseThrow().action().sameAction(action), "canonical identity attached");
    }

    private static void twoExecutionsInSameTickRemainDistinct() {
        var correlation = correlation();
        var first = correlation.beginExecution("p", "epicfight:first", 2_000L);
        var second = correlation.beginExecution("p", "epicfight:second", 2_000L);
        require(!first.executionId().equals(second.executionId()), "same-tick casts receive distinct execution ids");

        correlation.recordDebit(first, evidence("f", 3.0D), 2_000L);
        correlation.recordDebit(second, evidence("s", 7.0D), 2_000L);
        correlation.startAction(first, "epicfight:first_anim", 2_001L);
        correlation.startAction(second, "epicfight:second_anim", 2_001L);
        correlation.endExecution(first, 2_002L);
        correlation.endExecution(second, 2_002L);

        require(close(correlation.bindCanonicalAction("p", "epicfight:first_anim", root("f"), 2_003L)
            .correlatedDebit().orElseThrow().actualDebit(), 3.0D), "first cast keeps first debit");
        require(close(correlation.bindCanonicalAction("p", "epicfight:second_anim", root("s"), 2_003L)
            .correlatedDebit().orElseThrow().actualDebit(), 7.0D), "second cast keeps second debit");
    }

    private static void reusedAnimationGetsNewPlaybackGeneration() {
        var correlation = correlation();
        var first = correlation.beginExecution("p", "epicfight:a", 3_000L);
        correlation.recordDebit(first, evidence("e1", 2.0D), 3_000L);
        var playback1 = correlation.startAction(first, "epicfight:shared_anim", 3_001L).orElseThrow();
        correlation.endExecution(first, 3_002L);
        correlation.bindCanonicalAction("p", "epicfight:shared_anim", root("a1"), 3_003L);
        correlation.endAction("p", "epicfight:shared_anim", playback1.generation(), 3_004L);

        var second = correlation.beginExecution("p", "epicfight:a", 3_005L);
        correlation.recordDebit(second, evidence("e2", 5.0D), 3_005L);
        var playback2 = correlation.startAction(second, "epicfight:shared_anim", 3_006L).orElseThrow();
        require(playback2.generation() > playback1.generation(), "reused accessor gets a new generation");
        correlation.endExecution(second, 3_007L);
        var result = correlation.bindCanonicalAction("p", "epicfight:shared_anim", root("a2"), 3_008L);
        require(close(result.correlatedDebit().orElseThrow().actualDebit(), 5.0D), "second playback cannot reuse first debit");
    }

    private static void multiHitAndMultiTargetReuseFirstCanonicalAction() {
        var correlation = correlation();
        var execution = correlation.beginExecution("p", "epicfight:cleave", 4_000L);
        correlation.recordDebit(execution, evidence("e", 9.0D), 4_000L);
        correlation.startAction(execution, "epicfight:cleave_anim", 4_001L);
        correlation.endExecution(execution, 4_002L);

        var action = root("cleave-action");
        require(correlation.bindCanonicalAction("p", "epicfight:cleave_anim", action, 4_003L).status()
            == ExactStaminaReceiptCorrelation.BindStatus.CORRELATED, "first hit binds action");
        require(correlation.bindCanonicalAction("p", "epicfight:cleave_anim", action.withSource("epicfight:damage_post"), 4_004L).status()
            == ExactStaminaReceiptCorrelation.BindStatus.DUPLICATE_ACTION, "same action callback is duplicate");
        require(correlation.boundAction("p", "epicfight:cleave_anim", 4_005L).orElseThrow().sameAction(action),
            "multi-target lookup reuses the first canonical action");
    }

    private static void duplicateEvidenceIsNotASecondPhysicalDebit() {
        var correlation = correlation();
        var execution = correlation.beginExecution("p", "epicfight:a", 5_000L);
        var evidence = evidence("same-evidence", 4.0D);
        require(correlation.recordDebit(execution, evidence, 5_000L) == ExactStaminaReceiptCorrelation.DebitStatus.RECORDED,
            "first evidence recorded");
        require(correlation.recordDebit(execution, evidence, 5_001L) == ExactStaminaReceiptCorrelation.DebitStatus.DUPLICATE_EVIDENCE,
            "same evidence callback is idempotent");
        correlation.startAction(execution, "epicfight:a", 5_002L);
        correlation.endExecution(execution, 5_003L);
        require(correlation.bindCanonicalAction("p", "epicfight:a", root("a"), 5_004L).status()
            == ExactStaminaReceiptCorrelation.BindStatus.CORRELATED, "duplicate delivery did not make action ambiguous");
    }

    private static void twoDistinctPositiveDebitsFailClosed() {
        var correlation = correlation();
        var execution = correlation.beginExecution("p", "epicfight:a", 6_000L);
        require(correlation.recordDebit(execution, evidence("e1", 2.0D), 6_000L)
            == ExactStaminaReceiptCorrelation.DebitStatus.RECORDED, "first debit recorded");
        require(correlation.recordDebit(execution, evidence("e2", 3.0D), 6_001L)
            == ExactStaminaReceiptCorrelation.DebitStatus.AMBIGUOUS_MULTIPLE_DEBITS,
            "second physical debit poisons the execution");
        correlation.startAction(execution, "epicfight:a", 6_002L);
        correlation.endExecution(execution, 6_003L);
        require(correlation.bindCanonicalAction("p", "epicfight:a", root("a"), 6_004L).status()
            == ExactStaminaReceiptCorrelation.BindStatus.AMBIGUOUS_MULTIPLE_DEBITS,
            "ambiguous execution can never issue usable receipt");
    }

    private static void debitWithoutActionAnimationBecomesUncorrelated() {
        var correlation = correlation();
        var execution = correlation.beginExecution("p", "epicfight:hold", 7_000L);
        correlation.recordDebit(execution, evidence("hold", 2.0D), 7_000L);
        require(correlation.endExecution(execution, 7_001L)
            == ExactStaminaReceiptCorrelation.ExecutionStatus.EXACT_DEBIT_UNCORRELATED,
            "delayed/hold debit without START_ACTION fails closed");
    }

    private static void unrelatedLaterAttackCannotBorrowUncorrelatedDebit() {
        var correlation = correlation();
        var dodge = correlation.beginExecution("p", "epicfight:dodge", 8_000L);
        correlation.recordDebit(dodge, evidence("dodge", 4.0D), 8_000L);
        correlation.endExecution(dodge, 8_001L);

        var attack = correlation.beginExecution("p", "epicfight:attack", 8_002L);
        correlation.startAction(attack, "epicfight:attack_anim", 8_003L);
        correlation.endExecution(attack, 8_004L);
        require(correlation.bindCanonicalAction("p", "epicfight:attack_anim", root("attack"), 8_005L).status()
            == ExactStaminaReceiptCorrelation.BindStatus.NO_EXACT_DEBIT,
            "attack cannot borrow earlier dodge/guard debit");
    }

    private static void animationEndingBeforeActionBindingFailsClosed() {
        var correlation = correlation();
        var execution = correlation.beginExecution("p", "epicfight:a", 9_000L);
        correlation.recordDebit(execution, evidence("e", 3.0D), 9_000L);
        var playback = correlation.startAction(execution, "epicfight:a", 9_001L).orElseThrow();
        correlation.endExecution(execution, 9_002L);
        require(correlation.endAction("p", "epicfight:a", playback.generation(), 9_003L)
            == ExactStaminaReceiptCorrelation.ExecutionStatus.EXACT_DEBIT_UNCORRELATED,
            "ending playback without canonical action makes debit unusable");
        require(correlation.bindCanonicalAction("p", "epicfight:a", root("late"), 9_004L).status()
            == ExactStaminaReceiptCorrelation.BindStatus.NO_MATCH,
            "late damage cannot revive ended uncorrelated receipt");
    }

    private static void actorCleanupDropsExecutionsPlaybacksAndBindings() {
        var correlation = correlation();
        var execution = correlation.beginExecution("p", "epicfight:a", 10_000L);
        correlation.recordDebit(execution, evidence("e", 3.0D), 10_000L);
        correlation.startAction(execution, "epicfight:a", 10_001L);
        correlation.endExecution(execution, 10_002L);
        correlation.bindCanonicalAction("p", "epicfight:a", root("a"), 10_003L);
        correlation.clearActor("p");

        require(correlation.boundAction("p", "epicfight:a", 10_004L).isEmpty(), "logout/death/respawn drops action binding");
        require(correlation.bindCanonicalAction("p", "epicfight:a", root("other"), 10_004L).status()
            == ExactStaminaReceiptCorrelation.BindStatus.NO_MATCH, "cleanup drops active playback");
    }

    private static void expiredStateCannotCorrelate() {
        var correlation = new ExactStaminaReceiptCorrelation(10L, 64);
        var execution = correlation.beginExecution("p", "epicfight:a", 11_000L);
        correlation.recordDebit(execution, evidence("e", 3.0D), 11_000L);
        correlation.startAction(execution, "epicfight:a", 11_001L);
        correlation.endExecution(execution, 11_002L);
        require(correlation.bindCanonicalAction("p", "epicfight:a", root("late"), 11_020L).status()
            == ExactStaminaReceiptCorrelation.BindStatus.NO_MATCH, "expired receipt cannot correlate");
    }

    private static ExactStaminaReceiptCorrelation correlation() {
        return new ExactStaminaReceiptCorrelation(30_000L, 64);
    }

    private static ExactStaminaReceiptCorrelation.DebitEvidence evidence(String id, double amount) {
        return new ExactStaminaReceiptCorrelation.DebitEvidence(id, amount, amount, "test:consumer");
    }

    private static CanonicalActionIdentity root(String actionId) {
        return CanonicalActionIdentity.root("p", actionId, "epicfight:damage_pre");
    }

    private static boolean close(double left, double right) {
        return Math.abs(left - right) < 0.000001D;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
