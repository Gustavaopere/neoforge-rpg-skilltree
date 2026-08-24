package dev.gustavopere.rpgskilltree.core;

public final class CanonicalActionCorrelationServiceTest {
    public static void main(String[] args) {
        providerCallbacksClaimTheMeleeDecisionOnce();
        oneShotCanCorrelateMultipleProjectiles();
        unknownAndCrossActorProjectilesCannotBorrowShots();
        actorCleanupRemovesPendingCorrelation();
        System.out.println("CanonicalActionCorrelationServiceTest: PASS");
    }

    private static void providerCallbacksClaimTheMeleeDecisionOnce() {
        var service = new CanonicalActionCorrelationService(2_000L, 64);
        var action = service.newRoot("player-a", "neoforge:critical_hit", 1_000L);
        service.recordMeleeDecision(action, "mob-a", 1_000L);

        require(service.claimMeleeForProvider("player-a", "mob-a", 1_001L).orElseThrow().sameAction(action),
            "Epic Fight PRE receives NeoForge critical identity");
        require(service.claimMeleeForProvider("player-a", "mob-a", 1_002L).isEmpty(),
            "the same pending melee decision cannot be claimed twice");
    }

    private static void oneShotCanCorrelateMultipleProjectiles() {
        var service = new CanonicalActionCorrelationService(2_000L, 64);
        var shot = service.newRoot("player-a", "neoforge:arrow_loose", 1_000L);
        service.recordShot(shot, 1_000L);

        require(service.correlateProjectile("player-a", "arrow-a", 1_001L).orElseThrow().sameAction(shot),
            "first projectile uses shot token");
        require(service.correlateProjectile("player-a", "arrow-b", 1_001L).orElseThrow().sameAction(shot),
            "multishot projectile uses the same token");
        require(service.projectileAction("arrow-a", 1_002L).orElseThrow().sameAction(shot),
            "damage callback can recover shot identity");
    }

    private static void unknownAndCrossActorProjectilesCannotBorrowShots() {
        var service = new CanonicalActionCorrelationService(2_000L, 64);
        var shot = service.newRoot("player-a", "neoforge:arrow_loose", 1_000L);
        service.recordShot(shot, 1_000L);

        require(service.correlateProjectile("player-b", "stolen", 1_001L).isEmpty(),
            "different actor has no pending shot");
        require(service.projectileAction("unknown", 1_001L).isEmpty(), "unknown projectile has no action");
        require(service.correlateProjectile("player-a", "late", 3_001L).isEmpty(),
            "expired pending shot cannot be reused");
    }

    private static void actorCleanupRemovesPendingCorrelation() {
        var service = new CanonicalActionCorrelationService(2_000L, 64);
        var shot = service.newRoot("player-a", "neoforge:arrow_loose", 1_000L);
        service.recordShot(shot, 1_000L);
        service.correlateProjectile("player-a", "arrow-a", 1_001L);

        service.clearActor("player-a");

        require(service.projectileAction("arrow-a", 1_002L).isEmpty(), "logout clears projectile correlation");
        require(service.correlateProjectile("player-a", "arrow-b", 1_002L).isEmpty(),
            "logout clears pending shot");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
