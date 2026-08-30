package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

final class BehaviorEffectReconciliationJUnitTest {
    @Test
    void identicalRefreshProducesNoDuplicateWork() {
        var active = effect("rpgskilltree:effect/a", "rpgskilltree:handler/a", 2);

        NodeBehaviorReconciliation result = NodeBehaviorEffectReconciler.reconcile(
            List.of(active),
            List.of(active),
            ignored -> true
        );

        assertEquals(List.of(), result.removals());
        assertEquals(List.of(), result.applications());
    }

    @Test
    void rankChangeRemovesOldStateBeforeApplyingNewState() {
        var previous = effect("rpgskilltree:effect/a", "rpgskilltree:handler/a", 1);
        var desired = effect("rpgskilltree:effect/a", "rpgskilltree:handler/a", 3);

        NodeBehaviorReconciliation result = NodeBehaviorEffectReconciler.reconcile(
            List.of(previous),
            List.of(desired),
            ignored -> true
        );

        assertEquals(List.of(previous), result.removals());
        assertEquals(List.of(desired), result.applications());
    }

    @Test
    void removedNodeProducesOneRemovalAndNoApplication() {
        var previous = effect("rpgskilltree:effect/a", "rpgskilltree:handler/a", 1);

        NodeBehaviorReconciliation result = NodeBehaviorEffectReconciler.reconcile(
            List.of(previous),
            List.of(),
            ignored -> true
        );

        assertEquals(List.of(previous), result.removals());
        assertEquals(List.of(), result.applications());
    }

    @Test
    void unavailableOptionalHandlerFailsSoftAndRemovesPreviouslyAppliedState() {
        var previous = effect("rpgskilltree:effect/a", "optionalmod:handler/a", 1);
        var desired = effect("rpgskilltree:effect/a", "optionalmod:handler/a", 2);

        NodeBehaviorReconciliation result = NodeBehaviorEffectReconciler.reconcile(
            List.of(previous),
            List.of(desired),
            handlerId -> !Set.of("optionalmod:handler/a").contains(handlerId)
        );

        assertEquals(List.of(previous), result.removals());
        assertEquals(List.of(), result.applications());
    }

    @Test
    void actionsAreSortedByEffectIdForDeterministicExecution() {
        var z = effect("rpgskilltree:effect/z", "rpgskilltree:handler/z", 1);
        var a = effect("rpgskilltree:effect/a", "rpgskilltree:handler/a", 1);

        NodeBehaviorReconciliation result = NodeBehaviorEffectReconciler.reconcile(
            List.of(),
            List.of(z, a),
            ignored -> true
        );

        assertEquals(List.of(a, z), result.applications());
    }

    private static ResolvedNodeBehaviorEffect effect(String effectId, String handlerId, int rank) {
        return new ResolvedNodeBehaviorEffect(effectId, "rpgskilltree:test/root", handlerId, rank);
    }
}
