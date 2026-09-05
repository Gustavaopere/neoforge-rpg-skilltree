package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.runtime.CombatPerkAvailabilityRuntime;
import java.lang.reflect.Method;
import java.util.Map;
import org.junit.jupiter.api.Test;

final class A0051A0060Chat2ContractJUnitTest {
    @Test
    void crossbowDescendantsInheritA0050FailClosedAvailability() {
        assertFalse(CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0050"));
        assertFalse(CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0052"));
        assertFalse(CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0053"));
        assertFalse(CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0054"));
    }

    @Test
    void cadenceReloadRequiresTheSameCausalCrossbow() throws Exception {
        A0041A0060CombatState state = new A0041A0060CombatState();
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0052", 2));
        long now = 10_000L;

        Method record = A0041A0060CombatPolicy.class.getMethod(
            "recordCrossbowHit",
            String.class,
            String.class,
            String.class,
            CombatPerkRanks.class,
            A0041A0060CombatState.class,
            long.class
        );
        record.invoke(null, "p", "shot-1", "xbow-a", ranks, state, now);

        assertFalse(A0041A0060CombatPolicy.onCrossbowReloadComplete(
            "p", "xbow-b", ranks, state, true, now + 1_000L
        ));
        assertEquals(0, state.cadence("p"));
        assertTrue(A0041A0060CombatPolicy.onCrossbowReloadComplete(
            "p", "xbow-a", ranks, state, true, now + 1_100L
        ));
        assertEquals(1, state.cadence("p"));
    }

    @Test
    void multishotRootUsesSuccessWinsAndAtMostOneFailure() throws Exception {
        A0041A0060CombatState state = new A0041A0060CombatState();
        state.addCadence("p");
        state.addCadence("p");

        Method register;
        Method seal;
        Method failure;
        Method success;
        try {
            register = A0041A0060CombatState.class.getMethod(
                "registerCrossbowProjectile", String.class, String.class, String.class, long.class
            );
            seal = A0041A0060CombatState.class.getMethod(
                "sealCrossbowRoot", String.class, String.class, long.class
            );
            failure = A0041A0060CombatState.class.getMethod(
                "recordCrossbowProjectileFailure", String.class, String.class, String.class, long.class
            );
            success = A0041A0060CombatState.class.getMethod(
                "recordCrossbowProjectileSuccess", String.class, String.class, String.class, long.class
            );
        } catch (NoSuchMethodException missingAggregator) {
            throw new AssertionError("A0052 requires root-level Multishot success-wins aggregation", missingAggregator);
        }

        register.invoke(state, "p", "root-success", "arrow-a", 12_000L);
        register.invoke(state, "p", "root-success", "arrow-b", 12_001L);
        assertFalse((boolean) failure.invoke(state, "p", "root-success", "arrow-a", 12_100L));
        assertFalse((boolean) seal.invoke(state, "p", "root-success", 12_250L));
        assertFalse((boolean) success.invoke(state, "p", "root-success", "arrow-b", 12_400L));
        assertFalse((boolean) failure.invoke(state, "p", "root-success", "arrow-a", 12_500L));
        assertEquals(2, state.cadence("p"), "a later sibling hit must win over an earlier block impact");

        register.invoke(state, "p", "root-failure", "arrow-c", 13_000L);
        register.invoke(state, "p", "root-failure", "arrow-d", 13_001L);
        assertFalse((boolean) failure.invoke(state, "p", "root-failure", "arrow-c", 13_100L));
        assertFalse((boolean) failure.invoke(state, "p", "root-failure", "arrow-d", 13_110L));
        assertTrue((boolean) seal.invoke(state, "p", "root-failure", 13_250L));
        A0041A0060CombatPolicy.onCrossbowFailure("p", state);
        assertEquals(1, state.cadence("p"), "an all-failure root removes exactly one Cadence");
        assertFalse((boolean) seal.invoke(state, "p", "root-failure", 13_260L));
        assertFalse((boolean) failure.invoke(state, "p", "root-failure", "arrow-d", 13_270L));
        assertEquals(1, state.cadence("p"), "duplicate callbacks cannot settle a root twice");
    }

    @Test
    void piercingBoltReservesCadenceUntilProjectileRootExists() throws Exception {
        A0041A0060CombatState state = new A0041A0060CombatState();
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0053", 2));
        state.addCadence("p");
        state.addCadence("p");

        var reserved = A0041A0060CombatPolicy.tryPiercingBolt(
            "p", "bolt-1", ranks, state, true, true, false, 20_000L
        );
        assertTrue(reserved.applied());
        assertEquals(2, state.cadence("p"), "ArrowLoose may reserve but must not burn Cadence");

        Method commit = A0041A0060CombatPolicy.class.getMethod(
            "commitPiercingBolt",
            String.class,
            String.class,
            CombatPerkRanks.class,
            A0041A0060CombatState.class,
            long.class
        );
        assertTrue((boolean) commit.invoke(null, "p", "bolt-1", ranks, state, 20_050L));
        assertEquals(0, state.cadence("p"));
        assertFalse((boolean) commit.invoke(null, "p", "bolt-1", ranks, state, 20_060L));
    }

    @Test
    void adjustedMechanismConsumesCadenceOnlyWhenProjectileRootCommits() throws Exception {
        A0041A0060CombatState state = new A0041A0060CombatState();
        CombatPerkRanks ranks = CombatPerkRanks.of(Map.of("A0054", 1));
        state.addCadence("p");
        state.addCadence("p");
        state.addCadence("p");

        assertTrue(A0041A0060CombatPolicy.armAdjustedMechanismOnReload(
            "p", ranks, state, 80, true, 30_000L
        ));
        assertEquals(3, state.cadence("p"), "arming the window must not pre-consume Cadence");

        var reserved = A0041A0060CombatPolicy.tryAdjustedCrossbowShot(
            "p", "bolt-2", ranks, state, 30_100L
        );
        assertTrue(reserved.applied());
        assertEquals(3, state.cadence("p"), "ArrowLoose may reserve but must not burn the capstone");

        Method commit = A0041A0060CombatPolicy.class.getMethod(
            "commitAdjustedCrossbowShot",
            String.class,
            String.class,
            CombatPerkRanks.class,
            A0041A0060CombatState.class,
            long.class
        );
        assertTrue((boolean) commit.invoke(null, "p", "bolt-2", ranks, state, 30_150L));
        assertEquals(0, state.cadence("p"));
        assertFalse((boolean) commit.invoke(null, "p", "bolt-2", ranks, state, 30_160L));
    }

    @Test
    void rankReconciliationClearsOnlyOwnedCrossbowAndFistTransientState() throws Exception {
        A0041A0060CombatState state = new A0041A0060CombatState();
        state.addCadence("p");
        state.addSequence("p", 2, 40_000L);
        state.startFinalCombinationCooldown("p", 8_000L, 40_000L);

        Method reconcile = A0041A0060CombatState.class.getMethod(
            "reconcileForRanks", String.class, CombatPerkRanks.class, long.class
        );
        reconcile.invoke(state, "p", CombatPerkRanks.empty(), 40_100L);

        assertEquals(0, state.cadence("p"));
        assertEquals(0, state.sequence("p", 40_100L));
        assertTrue(state.finalCombinationReady("p", 40_100L));
    }
}
