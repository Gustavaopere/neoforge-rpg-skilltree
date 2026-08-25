package dev.gustavopere.rpgskilltree.core;

import java.util.OptionalDouble;

public final class CanonicalFocusServiceTest {
    public static void main(String[] args) {
        unspecifiedA0046AmountsRemainDisabled();
        preparationRequiresStableAimDurationAndFocus();
        preparedShotConsumesFocusAtReleaseEvenWhenItMisses();
        onlyOneProjectileCanResolvePreparedShot();
        invalidProjectileOwnershipCannotCorrelate();
        System.out.println("CanonicalFocusServiceTest: PASS");
    }

    private static void unspecifiedA0046AmountsRemainDisabled() {
        var state = new NotionCombatPerkState();
        var service = new CanonicalFocusService(30_000L, 64);
        var request = new CanonicalFocusService.ProductionRequest(
            root("focus-hit"), true, true, true, OptionalDouble.empty(), 1.25D
        );

        require(service.produce(request, state, 1_000L)
            == CanonicalFocusService.ProductionStatus.UNSUPPORTED_UNSPECIFIED_AMOUNT,
            "A0046 rate and distant-hit amount cannot be invented");
        require(close(state.focus("p"), 0.0D), "disabled producer changes no Focus");
    }

    private static void preparationRequiresStableAimDurationAndFocus() {
        var state = new NotionCombatPerkState();
        state.addFocus("p", 100.0D, 500L);
        var service = new CanonicalFocusService(30_000L, 64);
        var preparation = root("aim-1");

        require(service.beginPreparation(preparation, true, true, 1_000L)
            == CanonicalFocusService.PreparationStatus.STARTED, "stable aim starts server-side");
        require(service.armPreparation(preparation, true, state, 2_249L)
            == CanonicalFocusService.PreparationStatus.TOO_EARLY, "A0048 requires one and a quarter seconds");
        require(service.armPreparation(preparation, true, state, 2_250L)
            == CanonicalFocusService.PreparationStatus.ARMED, "stable duration arms prepared shot");

        var lowFocus = new NotionCombatPerkState();
        lowFocus.addFocus("p", 79.0D, 500L);
        var other = new CanonicalFocusService(30_000L, 64);
        require(other.beginPreparation(root("aim-2"), true, true, 1_000L)
            == CanonicalFocusService.PreparationStatus.STARTED, "aim tracking may start before threshold");
        require(other.armPreparation(root("aim-2"), true, lowFocus, 2_250L)
            == CanonicalFocusService.PreparationStatus.INSUFFICIENT_FOCUS, "arming requires eighty Focus");
    }

    private static void preparedShotConsumesFocusAtReleaseEvenWhenItMisses() {
        var state = preparedState();
        var service = preparedService(state);
        var shot = root("shot-1");
        var request = shotRequest(shot);

        require(service.release(request, state, 2_300L) == CanonicalFocusService.ReleaseStatus.PREPARED_CONSUMED,
            "fully drawn release consumes prepared shot");
        require(close(state.focus("p"), 50.0D), "A0048 consumes fifty Focus before any damage event");
        require(service.release(request, state, 2_301L) == CanonicalFocusService.ReleaseStatus.DUPLICATE,
            "duplicate loose callback cannot consume again");
        require(service.release(shotRequest(root("shot-2")), state, 2_400L)
            == CanonicalFocusService.ReleaseStatus.NOT_PREPARED, "a miss cannot leave Focus prepared for reuse");
    }

    private static void onlyOneProjectileCanResolvePreparedShot() {
        var state = preparedState();
        var service = preparedService(state);
        var shot = root("shot-1");
        service.release(shotRequest(shot), state, 2_300L);

        require(service.attachProjectile(projectile(shot, "arrow-a", "p", true, true))
            == CanonicalFocusService.ProjectileStatus.ATTACHED, "first projectile attached");
        require(service.attachProjectile(projectile(shot, "arrow-b", "p", true, true))
            == CanonicalFocusService.ProjectileStatus.ATTACHED, "multishot projectile shares shot identity");
        require(service.projectileAction("arrow-a", 2_301L).orElseThrow().sameAction(shot),
            "projectile retains preparation to shot correlation");
        require(service.claimPreparedHit("arrow-a", 2_400L), "one projectile receives prepared-hit consumer");
        require(!service.claimPreparedHit("arrow-b", 2_401L), "other projectiles cannot resolve the same shot twice");
        require(!service.claimPreparedHit("arrow-a", 2_402L), "same projectile callback is idempotent");
    }

    private static void invalidProjectileOwnershipCannotCorrelate() {
        var state = preparedState();
        var service = preparedService(state);
        var shot = root("shot-1");
        service.release(shotRequest(shot), state, 2_300L);

        require(service.attachProjectile(projectile(shot, "ownerless", null, true, true))
            == CanonicalFocusService.ProjectileStatus.INELIGIBLE_OWNER, "ownerless projectile rejected");
        require(service.attachProjectile(projectile(shot, "fake", "p", false, true))
            == CanonicalFocusService.ProjectileStatus.INELIGIBLE_OWNER, "fake player projectile rejected");
        require(service.attachProjectile(projectile(shot, "summon", "p", true, false))
            == CanonicalFocusService.ProjectileStatus.NOT_DIRECT_PLAYER_PROJECTILE, "summon projectile rejected");
        require(service.attachProjectile(projectile(shot.child("rpgskilltree:proc"), "proc", "p", true, true))
            == CanonicalFocusService.ProjectileStatus.PROC_DEPTH_REJECTED, "proc projectile rejected");
    }

    private static NotionCombatPerkState preparedState() {
        var state = new NotionCombatPerkState();
        state.addFocus("p", 100.0D, 500L);
        return state;
    }

    private static CanonicalFocusService preparedService(NotionCombatPerkState state) {
        var service = new CanonicalFocusService(30_000L, 64);
        var preparation = root("aim-1");
        service.beginPreparation(preparation, true, true, 1_000L);
        service.armPreparation(preparation, true, state, 2_250L);
        return service;
    }

    private static CanonicalFocusService.ReleaseRequest shotRequest(CanonicalActionIdentity shot) {
        return new CanonicalFocusService.ReleaseRequest(shot, true, true, true, 8_000L);
    }

    private static CanonicalFocusService.ProjectileRequest projectile(
        CanonicalActionIdentity shot,
        String projectileId,
        String ownerActorId,
        boolean eligibleOwner,
        boolean directPlayerOwned
    ) {
        return new CanonicalFocusService.ProjectileRequest(
            shot, projectileId, ownerActorId, true, eligibleOwner, directPlayerOwned
        );
    }

    private static CanonicalActionIdentity root(String actionId) {
        return CanonicalActionIdentity.root("p", actionId, "neoforge:bow");
    }

    private static boolean close(double left, double right) {
        return Math.abs(left - right) < 0.000001D;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
