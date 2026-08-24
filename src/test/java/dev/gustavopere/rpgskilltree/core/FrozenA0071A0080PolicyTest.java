package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

/** RED contracts for frozen MARTIAL tactics A0071-A0080, including P-0028. */
public final class FrozenA0071A0080PolicyTest {
    public static void main(String[] args) {
        eliteRetaliationAndMovementUseOnlyCanonicalFacts();
        executionIsTwoStepPerTargetAndRefundsOnlyItsExactDebit();
        uncorrelatedAndAmbiguousDebitsCannotRefund();
        openingStancesRhythmAndDodgeAreBounded();
        System.out.println("FrozenA0071A0080PolicyTest: PASS");
    }

    private static void eliteRetaliationAndMovementUseOnlyCanonicalFacts() {
        var service = new FrozenMartialTacticsService();
        var ranks = FrozenCombatPerkRanks.of(Map.of("A0071", 5, "A0072", 3, "A0078", 3, "A0079", 3));
        service.confirmDirectHostileDamage("player", true, true, false, 1_000L);
        var effect = service.resolveAttack(request(root("facts"), "elite", 0.5D, true, false, true, true, true), ranks, 1_001L);
        require(close(effect.damageMultiplier(), 1.54D), "A71+A72+A78+A79 stack from explicit facts");
        var forged = service.resolveAttack(request(root("forged").child("proc"), "elite", 0.5D, true, false, true, true, true), ranks, 1_002L);
        require(!forged.active(), "proc depth cannot farm tactics");
        service.confirmDirectHostileDamage("player", true, false, false, 1_003L);
        var expired = service.resolveAttack(request(root("expired"), "normal", 0.5D, false, false, false, false, true), ranks, 4_004L);
        require(!expired.active(), "ineligible damage cannot renew retaliation");
    }

    private static void executionIsTwoStepPerTargetAndRefundsOnlyItsExactDebit() {
        var service = new FrozenMartialTacticsService();
        var ranks = FrozenCombatPerkRanks.of(Map.of("A0073", 1));
        var opener = service.resolveAttack(request(root("open"), "target", 0.19D, false, false, false, false, true), ranks, 10_000L);
        require(opener.executionPrimed() && close(opener.damageMultiplier(), 1.0D), "first hit only primes");
        var finalAction = root("final");
        var finisher = service.resolveAttack(request(finalAction, "target", 0.10D, false, true, false, false, true), ranks, 10_001L);
        require(finisher.executionConsumed() && close(finisher.damageMultiplier(), 1.18D) && close(finisher.impactBonus(), 0.20D), "distinct second hit consumes");

        var stamina = new CanonicalStaminaService(30_000L, 32);
        stamina.observe(new CanonicalStaminaService.CostObservation(finalAction, true, true, 7.5D, "modified-debit", CanonicalStaminaService.ObservationStage.POST_CONSUME_CONFIRMED), 10_001L);
        require(close(service.claimExecutionKillRefund("player", "target", finalAction, stamina, 10_002L).orElseThrow(), 0.75D), "10% of actual modified debit");
        require(service.claimExecutionKillRefund("player", "target", finalAction, stamina, 10_003L).isEmpty(), "duplicate callback and claim rejected");

        var otherService = new FrozenMartialTacticsService();
        otherService.resolveAttack(request(root("o2"), "t2", 0.10D, false, false, false, false, true), ranks, 20_000L);
        var own = root("own");
        otherService.resolveAttack(request(own, "t2", 0.10D, false, false, false, false, true), ranks, 20_001L);
        stamina.observe(new CanonicalStaminaService.CostObservation(root("wrong"), true, true, 20.0D, "wrong", CanonicalStaminaService.ObservationStage.POST_CONSUME_CONFIRMED), 20_001L);
        require(otherService.claimExecutionKillRefund("player", "t2", own, stamina, 20_002L).isEmpty(), "wrong or absent receipt gives zero");
    }

    private static void uncorrelatedAndAmbiguousDebitsCannotRefund() {
        var correlation = new ExactStaminaReceiptCorrelation(30_000L, 16);
        var uncorrelated = correlation.beginExecution("player", "skill", 1L);
        correlation.recordDebit(uncorrelated, new ExactStaminaReceiptCorrelation.DebitEvidence("d1", 4.0D, 5.0D, "call"), 2L);
        require(correlation.endExecution(uncorrelated, 3L) == ExactStaminaReceiptCorrelation.ExecutionStatus.EXACT_DEBIT_UNCORRELATED, "explicit uncorrelated status");
        var ambiguous = correlation.beginExecution("player", "skill", 4L);
        correlation.recordDebit(ambiguous, new ExactStaminaReceiptCorrelation.DebitEvidence("d2", 4.0D, 4.0D, "call"), 5L);
        require(correlation.recordDebit(ambiguous, new ExactStaminaReceiptCorrelation.DebitEvidence("d3", 3.0D, 3.0D, "call"), 6L) == ExactStaminaReceiptCorrelation.DebitStatus.AMBIGUOUS_MULTIPLE_DEBITS, "explicit ambiguous status");
        require(correlation.endExecution(ambiguous, 7L) == ExactStaminaReceiptCorrelation.ExecutionStatus.AMBIGUOUS_MULTIPLE_DEBITS, "ambiguous execution fails closed");
    }

    private static void openingStancesRhythmAndDodgeAreBounded() {
        var service = new FrozenMartialTacticsService();
        var ranks = FrozenCombatPerkRanks.of(Map.of("A0074", 1, "A0076", 1, "A0077", 1, "A0080", 1));
        var opening = service.resolveAttack(request(root("opening"), "target", 0.5D, false, true, false, false, true), ranks, 1_000L);
        require(opening.openingPrimed() && close(opening.damageMultiplier(), 1.0D), "opening hit cannot consume itself");
        var follow = service.resolveAttack(request(root("follow"), "target", 0.5D, false, true, false, false, true), ranks, 1_001L);
        require(opening.openingPrimed() && close(follow.damageMultiplier(), 1.10D), "distinct follow-up consumes opening");
        require(service.setStance("player", FrozenMartialTacticsService.Stance.AGGRESSIVE, true, true, 2_000L), "activate owned stance");
        require(!service.setStance("player", FrozenMartialTacticsService.Stance.CAUTIOUS, true, true, 3_499L), "1.5s switch lock");
        require(service.setStance("player", FrozenMartialTacticsService.Stance.CAUTIOUS, true, true, 3_500L), "exclusive switch after lock");
        require(service.stanceModifiers("player").damageMultiplier() == 0.95D, "cautious damage penalty");
        require(service.directPhysicalDamageTakenMultiplier("player") == 0.92D, "cautious resistance benefit");
        service.revalidateStance("player", true, false);
        require(service.stance("player") == FrozenMartialTacticsService.Stance.NONE, "lost rank clears persistent stance");
        require(!service.confirmDodge("player", true, true, false, 4_000L), "button press alone is not dodge");
        require(service.confirmDodge("player", true, true, true, 4_001L), "confirmed hostile avoided attack");
        var dodge = service.resolveAttack(request(root("dodge"), "other", 0.5D, false, true, false, false, true), ranks, 4_002L);
        require(close(dodge.damageMultiplier(), 1.15D), "next physical direct hit consumes opportunity");
        require(!service.confirmDodge("player", true, true, true, 4_003L), "cooldown after consume");

        var body = new CanonicalBodyTradeoffService(new AcceptingProvider());
        var rhythm = new MartialRhythmService(body);
        require(!rhythm.observe("player", "slash", true, true, 5_000L, 1), "one action");
        require(!rhythm.observe("player", "slash", true, true, 5_001L, 1), "repeated action ignored");
        require(!rhythm.observe("player", "kick", true, true, 5_002L, 1), "two distinct actions");
        require(rhythm.observe("player", "dash", true, true, 5_003L, 1), "three distinct actions activate with body costs");
        require(close(rhythm.staminaCostMultiplier("player", 5_004L), 0.90D), "benefit coupled to active body lease");
    }

    private static FrozenMartialTacticsService.AttackRequest request(CanonicalActionIdentity action, String target, double health, boolean elite, boolean impact, boolean sprint, boolean stationary, boolean hostile) {
        return new FrozenMartialTacticsService.AttackRequest(action, target, true, true, true, true, hostile, health, elite, false, impact, sprint, stationary);
    }
    private static CanonicalActionIdentity root(String id) { return CanonicalActionIdentity.root("player", id, "test"); }
    private static boolean close(double a, double b) { return Math.abs(a - b) < 0.000001D; }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }

    private static final class AcceptingProvider implements CanonicalBodyTradeoffService.Provider {
        public boolean acquire(String actorId, CanonicalBodyTradeoffService.LeaseRequest request) { return true; }
        public boolean maintain(String actorId, CanonicalBodyTradeoffService.LeaseRequest request) { return true; }
        public void release(String actorId, CanonicalBodyTradeoffService.LeaseRequest request) {}
    }
}
