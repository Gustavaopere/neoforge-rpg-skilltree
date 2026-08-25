package dev.gustavopere.rpgskilltree.core;

public final class FrozenA0046FocusPolicyTest {
    public static void main(String[] args) {
        stableAimAndSprintUseHalfSecondIntervals();
        suddenAimChangeUsesFiveTickWindowAndInternalCooldown();
        distantProjectileCreditsOnceAndRequiresAuthorship();
        heavyImpactAndCancelledDrawApplyFrozenLosses();
        System.out.println("FrozenA0046FocusPolicyTest: PASS");
    }

    private static void stableAimAndSprintUseHalfSecondIntervals() {
        var state = new NotionCombatPerkState();
        var service = new CanonicalFocusService(30_000L, 64);
        var aim = CanonicalActionIdentity.root("p", "aim-1", "neoforge:arrow_nock");

        require(service.sampleAim(sample(aim, 1, false, 0.0D, 0.0D), state, 0L)
            == CanonicalFocusService.AimStatus.TRACKING, "first sample only starts the interval");
        require(service.sampleAim(sample(aim, 1, false, 0.0D, 0.0D), state, 499L)
            == CanonicalFocusService.AimStatus.TRACKING, "stable aim cannot credit early");
        require(service.sampleAim(sample(aim, 1, false, 0.0D, 0.0D), state, 500L)
            == CanonicalFocusService.AimStatus.STABLE_GAIN, "rank1 stable aim credits at 0.5 s");
        require(close(state.focus("p"), 4.0D), "rank1 stable aim is +4 per half-second");

        var rankTwo = new NotionCombatPerkState();
        service.clearActor("p");
        require(service.sampleAim(sample(aim, 2, false, 0.0D, 0.0D), rankTwo, 1_000L)
            == CanonicalFocusService.AimStatus.TRACKING, "new session tracks independently");
        require(service.sampleAim(sample(aim, 2, false, 0.0D, 0.0D), rankTwo, 1_500L)
            == CanonicalFocusService.AimStatus.STABLE_GAIN, "rank2 stable aim credits at interval");
        require(close(rankTwo.focus("p"), 5.0D), "rank2 gain is +10/s => +5 per interval");

        rankTwo.addFocus("p", 20.0D, 1_500L);
        require(service.sampleAim(sample(aim, 2, true, 0.0D, 0.0D), rankTwo, 2_000L)
            == CanonicalFocusService.AimStatus.SPRINT_DRAIN, "sprinting with bow in use drains instead of generating");
        require(close(rankTwo.focus("p"), 19.0D), "sprint drains 12/s => 6 per half-second");
    }

    private static void suddenAimChangeUsesFiveTickWindowAndInternalCooldown() {
        var state = new NotionCombatPerkState();
        state.addFocus("p", 40.0D, 0L);
        var service = new CanonicalFocusService(30_000L, 64);
        var aim = CanonicalActionIdentity.root("p", "aim-turn", "neoforge:arrow_nock");

        service.sampleAim(sample(aim, 1, false, 0.0D, 0.0D), state, 0L);
        service.sampleAim(sample(aim, 1, false, 12.0D, 0.0D), state, 50L);
        service.sampleAim(sample(aim, 1, false, 24.0D, 0.0D), state, 100L);
        service.sampleAim(sample(aim, 1, false, 36.0D, 0.0D), state, 150L);
        require(service.sampleAim(sample(aim, 1, false, 48.0D, 0.0D), state, 200L)
            == CanonicalFocusService.AimStatus.SUDDEN_CHANGE_DRAIN,
            "more than 45 degrees accumulated inside five server ticks triggers loss");
        require(close(state.focus("p"), 30.0D), "sudden change removes exactly 10 Focus");

        service.sampleAim(sample(aim, 1, false, 60.0D, 0.0D), state, 250L);
        require(close(state.focus("p"), 30.0D), "internal 0.5 s interval prevents repeated sudden-change drain");
    }

    private static void distantProjectileCreditsOnceAndRequiresAuthorship() {
        var service = new CanonicalFocusService(30_000L, 64);
        var state = new NotionCombatPerkState();
        var hit = CanonicalActionIdentity.root("p", "shot-1", "neoforge:projectile");

        require(service.creditDistantProjectileHit(
            new CanonicalFocusService.DistantHitRequest(hit, "arrow-1", true, true, true, 12.0D, 1),
            state, 1_000L
        ) == CanonicalFocusService.DistantHitStatus.APPLIED, "rank1 direct player-owned hit at 12 blocks credits Focus");
        require(close(state.focus("p"), 10.0D), "rank1 distant hit gives +10");
        require(service.creditDistantProjectileHit(
            new CanonicalFocusService.DistantHitRequest(hit.withSource("duplicate"), "arrow-1", true, true, true, 30.0D, 1),
            state, 1_001L
        ) == CanonicalFocusService.DistantHitStatus.DUPLICATE_PROJECTILE, "same projectile credits once");

        require(service.creditDistantProjectileHit(
            new CanonicalFocusService.DistantHitRequest(
                CanonicalActionIdentity.root("p", "shot-2", "neoforge:projectile"),
                "arrow-2", true, true, true, 11.999D, 2),
            state, 2_000L
        ) == CanonicalFocusService.DistantHitStatus.TOO_CLOSE, "distance threshold is inclusive at 12 blocks");

        require(service.creditDistantProjectileHit(
            new CanonicalFocusService.DistantHitRequest(
                CanonicalActionIdentity.root("p", "shot-3", "neoforge:projectile"),
                "arrow-3", true, false, true, 20.0D, 2),
            state, 3_000L
        ) == CanonicalFocusService.DistantHitStatus.INELIGIBLE, "projectile without proven player authorship gives zero Focus");

        var rankTwo = new NotionCombatPerkState();
        require(service.creditDistantProjectileHit(
            new CanonicalFocusService.DistantHitRequest(
                CanonicalActionIdentity.root("q", "shot-r2", "neoforge:projectile"),
                "arrow-r2", true, true, true, 12.0D, 2),
            rankTwo, 4_000L
        ) == CanonicalFocusService.DistantHitStatus.APPLIED, "rank2 distant hit applies");
        require(close(rankTwo.focus("q"), 12.5D), "rank2 distant hit gives +12.5");
    }

    private static void heavyImpactAndCancelledDrawApplyFrozenLosses() {
        var service = new CanonicalFocusService(30_000L, 64);
        var state = new NotionCombatPerkState();
        state.addFocus("p", 100.0D, 0L);

        require(service.applyHeavyImpactLoss("p", true, true, state, 1_000L), "eligible heavy impact applies");
        require(close(state.focus("p"), 75.0D), "heavy impact removes 25");
        require(!service.applyHeavyImpactLoss("p", true, false, state, 1_500L), "unproven heavy impact fails closed");
        require(close(state.focus("p"), 75.0D), "failed-closed impact changes nothing");

        require(service.applyCancelledDrawLoss("p", true, true, 0.80D, state, 2_000L), "cancel at 80% tension applies");
        require(close(state.focus("p"), 60.0D), "cancelled draw removes 15");
        require(!service.applyCancelledDrawLoss("p", true, true, 0.799D, state, 2_500L), "sub-threshold cancel does not drain");
    }

    private static CanonicalFocusService.AimSampleRequest sample(
        CanonicalActionIdentity action,
        int rank,
        boolean sprinting,
        double yaw,
        double pitch
    ) {
        return new CanonicalFocusService.AimSampleRequest(
            action, true, true, true, sprinting, rank, yaw, pitch, 1.0D, 1.0D
        );
    }

    private static boolean close(double left, double right) {
        return Math.abs(left - right) < 0.000001D;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
