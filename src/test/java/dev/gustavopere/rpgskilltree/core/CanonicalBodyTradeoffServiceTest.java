package dev.gustavopere.rpgskilltree.core;

/** P-0031: benefits and metabolic costs are one fail-closed lease. */
public final class CanonicalBodyTradeoffServiceTest {
    public static void main(String[] args) {
        providerMustAcceptCostsBeforeBenefitStarts();
        providerLossTerminatesBenefitImmediately();
        cooldownSurvivesTransientLifecycleClear();
        System.out.println("CanonicalBodyTradeoffServiceTest: PASS");
    }

    private static void providerMustAcceptCostsBeforeBenefitStarts() {
        var absent = new CanonicalBodyTradeoffService(null);
        require(!absent.activate("player", lease("A0075", 0.15D, 0.10D, 120L, 240L), 100L).active(), "absent provider");
        var rejecting = new FakeProvider(false);
        var rejected = new CanonicalBodyTradeoffService(rejecting);
        require(!rejected.activate("player", lease("A0075", 0.15D, 0.10D, 120L, 240L), 100L).active(), "provider rejection");
        require(!rejected.benefitActive("player", "A0075", 101L), "no free benefit");
        var provider = new FakeProvider(true);
        var accepted = new CanonicalBodyTradeoffService(provider);
        require(accepted.activate("player", lease("A0075", 0.15D, 0.10D, 120L, 240L), 100L).active(), "accepted costs activate benefit");
        require(accepted.benefitActive("player", "A0075", 101L), "lease active while costs remain enforceable");
    }

    private static void providerLossTerminatesBenefitImmediately() {
        var provider = new FakeProvider(true);
        var service = new CanonicalBodyTradeoffService(provider);
        service.activate("player", lease("A0087", 0.20D, 0.15D, 120L, 900L), 1_000L);
        provider.available = false;
        require(!service.benefitActive("player", "A0087", 1_001L), "runtime provider loss ends benefit");
        require(provider.released == 1, "lease released exactly once");
        require(!service.activate("player", lease("A0087", 0.20D, 0.15D, 120L, 900L), 1_002L).active(), "cooldown starts after forced termination");
    }

    private static void cooldownSurvivesTransientLifecycleClear() {
        var provider = new FakeProvider(true);
        var service = new CanonicalBodyTradeoffService(provider);
        service.activate("player", lease("A0075", 0.15D, 0.10D, 120L, 240L), 2_000L);
        service.clearTransient("player", 2_010L);
        require(!service.benefitActive("player", "A0075", 2_011L), "transient benefit cleared");
        require(!service.activate("player", lease("A0075", 0.15D, 0.10D, 120L, 240L), 2_012L).active(), "cooldown cannot be lifecycle-reset");
        require(service.activate("player", lease("A0075", 0.15D, 0.10D, 120L, 240L), 2_251L).active(), "reactivates after preserved cooldown");
    }

    private static CanonicalBodyTradeoffService.LeaseRequest lease(String id, double heat, double exhaustion, long duration, long cooldown) {
        return new CanonicalBodyTradeoffService.LeaseRequest(id, heat, exhaustion, duration, cooldown);
    }

    private static final class FakeProvider implements CanonicalBodyTradeoffService.Provider {
        boolean available;
        int released;
        FakeProvider(boolean available) { this.available = available; }
        public boolean acquire(String actorId, CanonicalBodyTradeoffService.LeaseRequest request) { return available; }
        public boolean maintain(String actorId, CanonicalBodyTradeoffService.LeaseRequest request) { return available; }
        public void release(String actorId, CanonicalBodyTradeoffService.LeaseRequest request) { released++; }
    }

    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
