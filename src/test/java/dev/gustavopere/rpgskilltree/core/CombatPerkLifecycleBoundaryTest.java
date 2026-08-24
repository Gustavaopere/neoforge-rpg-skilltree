package dev.gustavopere.rpgskilltree.core;

/** Explicit P-0013 lifecycle matrix. */
public final class CombatPerkLifecycleBoundaryTest {
    public static void main(String[] args) {
        transientBoundary(CombatPerkLifecyclePolicy.Boundary.DEATH, "death");
        transientBoundary(CombatPerkLifecyclePolicy.Boundary.RESPAWN, "respawn");
        transientBoundary(CombatPerkLifecyclePolicy.Boundary.PLAYER_RECREATION, "clone/recreation");
        transientBoundary(CombatPerkLifecyclePolicy.Boundary.DIMENSION_CHANGE, "dimension change");
        fullBoundary(CombatPerkLifecyclePolicy.Boundary.LOGOUT, "logout");
        fullBoundary(CombatPerkLifecyclePolicy.Boundary.LOGIN, "login after logout");
        System.out.println("CombatPerkLifecycleBoundaryTest: PASS");
    }

    private static void transientBoundary(CombatPerkLifecyclePolicy.Boundary boundary, String label) {
        require(CombatPerkLifecyclePolicy.cleanupMode(boundary)
            == CombatPerkLifecyclePolicy.CleanupMode.TRANSIENT_PRESERVE_GUARDS,
            label + " must preserve cooldown/claim antiabuse guards");
    }

    private static void fullBoundary(CombatPerkLifecyclePolicy.Boundary boundary, String label) {
        require(CombatPerkLifecyclePolicy.cleanupMode(boundary)
            == CombatPerkLifecyclePolicy.CleanupMode.FULL_SESSION,
            label + " must use full session teardown");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
