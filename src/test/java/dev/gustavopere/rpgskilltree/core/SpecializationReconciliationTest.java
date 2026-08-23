package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Map;
import java.util.Set;

/** Regression coverage for mixed persistent and node-owned specialization state. */
public final class SpecializationReconciliationTest {
    public static void main(String[] args) {
        preservesPersistentSpecializations();
        revokesOnlyInactiveNodeOwnedSpecializations();
        restoresActiveNodeOwnedSpecializations();
        System.out.println("SpecializationReconciliationTest: PASS");
    }

    static void preservesPersistentSpecializations() {
        var state = ProgressionState.empty()
            .withSpecializations(SpecializationProgressionState.of(Set.of("industrialist", "prospector")));
        var grants = List.of(new NodeSpecializationGrant(
            "rpgskilltree:gateway/create_kinetics", "create_kinetics", 1));

        var reconciled = ProgressionService.reconcileNodeSpecializations(state, grants);

        eq(Set.of("industrialist", "prospector"), reconciled.specializations().unlockedSpecializationIds());
    }

    static void revokesOnlyInactiveNodeOwnedSpecializations() {
        var state = ProgressionState.empty()
            .withSpecializations(SpecializationProgressionState.of(Set.of("industrialist", "create_kinetics")));
        var grants = List.of(new NodeSpecializationGrant(
            "rpgskilltree:gateway/create_kinetics", "create_kinetics", 1));

        var reconciled = ProgressionService.reconcileNodeSpecializations(state, grants);

        eq(Set.of("industrialist"), reconciled.specializations().unlockedSpecializationIds());
    }

    static void restoresActiveNodeOwnedSpecializations() {
        var state = ProgressionState.empty()
            .withSpecializations(SpecializationProgressionState.of(Set.of("industrialist")))
            .withPassiveNodes(PassiveNodeProgress.of(Map.of("rpgskilltree:gateway/create_kinetics", 1)));
        var grants = List.of(new NodeSpecializationGrant(
            "rpgskilltree:gateway/create_kinetics", "create_kinetics", 1));

        var reconciled = ProgressionService.reconcileNodeSpecializations(state, grants);

        eq(Set.of("industrialist", "create_kinetics"), reconciled.specializations().unlockedSpecializationIds());
    }

    private static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError(expected + " != " + actual);
        }
    }
}
