package dev.gustavopere.rpgskilltree.core;

public final class CanonicalStaminaServiceTest {
    public static void main(String[] args) {
        preConsumeIntentCannotAuthorizeRefund();
        exactPostConsumeEvidenceEnablesCausalRefund();
        refundIsCorrelatedAndIdempotent();
        fakeAndProcActionsCannotRecordCosts();
        System.out.println("CanonicalStaminaServiceTest: PASS");
    }

    private static void preConsumeIntentCannotAuthorizeRefund() {
        var service = new CanonicalStaminaService(30_000L, 64);
        var action = root("hammer-1");
        var observation = new CanonicalStaminaService.CostObservation(
            action, true, true, 30.0D, "epicfight:skill_consume",
            CanonicalStaminaService.ObservationStage.PRE_CONSUME_INTENT
        );

        require(service.observe(observation, 1_000L)
            == CanonicalStaminaService.CostStatus.UNSUPPORTED_PRE_CONSUME_ONLY,
            "Epic Fight public pre-consume event is not confirmed cost");
        require(service.refundAmount(action, "A0029", 0.10D, 1_001L).isEmpty(),
            "intent alone cannot refund stamina");
    }

    private static void exactPostConsumeEvidenceEnablesCausalRefund() {
        var service = new CanonicalStaminaService(30_000L, 64);
        var action = root("hammer-1");
        var observation = confirmed(action, 30.0D, "provider:receipt-17");

        require(service.observe(observation, 1_000L) == CanonicalStaminaService.CostStatus.RECORDED,
            "exact post-consume fact is recorded");
        require(close(service.refundAmount(action, "A0029", 0.10D, 1_001L).orElseThrow(), 3.0D),
            "refund derives from exact confirmed cost");
    }

    private static void refundIsCorrelatedAndIdempotent() {
        var service = new CanonicalStaminaService(30_000L, 64);
        var action = root("hammer-1");
        service.observe(confirmed(action, 25.0D, "provider:receipt-17"), 1_000L);

        require(close(service.refundAmount(action, "A0029", 0.10D, 1_001L).orElseThrow(), 2.5D),
            "first causal refund");
        require(service.refundAmount(action.withSource("epicfight:damage_post"), "A0029", 0.10D, 1_002L).isEmpty(),
            "same action and consumer cannot refund twice");
        require(service.refundAmount(root("hammer-2"), "A0029", 0.10D, 1_002L).isEmpty(),
            "different action cannot borrow another action's cost");
    }

    private static void fakeAndProcActionsCannotRecordCosts() {
        var service = new CanonicalStaminaService(30_000L, 64);
        var root = root("hammer-1");
        var fake = new CanonicalStaminaService.CostObservation(
            root, true, false, 30.0D, "provider:receipt-fake",
            CanonicalStaminaService.ObservationStage.POST_CONSUME_CONFIRMED
        );
        var proc = confirmed(root.child("rpgskilltree:proc"), 30.0D, "provider:receipt-proc");

        require(service.observe(fake, 1_000L) == CanonicalStaminaService.CostStatus.INELIGIBLE,
            "fake player evidence rejected");
        require(service.observe(proc, 1_000L) == CanonicalStaminaService.CostStatus.INELIGIBLE,
            "proc-depth evidence rejected");
    }

    private static CanonicalStaminaService.CostObservation confirmed(
        CanonicalActionIdentity action,
        double exactCost,
        String evidenceId
    ) {
        return new CanonicalStaminaService.CostObservation(
            action, true, true, exactCost, evidenceId,
            CanonicalStaminaService.ObservationStage.POST_CONSUME_CONFIRMED
        );
    }

    private static CanonicalActionIdentity root(String actionId) {
        return CanonicalActionIdentity.root("p", actionId, "epicfight:stamina");
    }

    private static boolean close(double left, double right) {
        return Math.abs(left - right) < 0.000001D;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
