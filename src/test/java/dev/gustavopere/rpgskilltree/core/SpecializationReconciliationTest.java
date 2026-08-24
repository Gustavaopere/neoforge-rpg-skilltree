package dev.gustavopere.rpgskilltree.core;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Regression coverage for mixed persistent, node-owned and eligibility-owned specialization state. */
public final class SpecializationReconciliationTest {
    public static void main(String[] args) {
        preservesPersistentSpecializations();
        revokesOnlyInactiveNodeOwnedSpecializations();
        restoresActiveNodeOwnedSpecializations();
        unlocksEligibleTagGatedSpecialization();
        revokesIneligibleCatalogSpecializationWithoutErasingUnmanagedState();
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

    static void unlocksEligibleTagGatedSpecialization() {
        var state = ProgressionState.empty()
            .withMastery(MasteryState.of(Map.of("epicfight:sword", 60)))
            .withSpecializations(SpecializationProgressionState.of(Set.of("industrialist")));
        var definition = new SpecializationDefinition(
            "epic_sword",
            Set.of(),
            Map.of("epicfight:sword", 60),
            Set.of("gateway:epic_sword")
        );
        var investment = InvestmentState.of(List.of(new NodeInvestment(
            "rpgskilltree:martial_000",
            Map.of(ProgressionDomain.MARTIAL, 1),
            Set.of("gateway:epic_sword")
        )));

        var reconciled = reconcileEligible(state, List.of(definition), investment);

        eq(Set.of("industrialist", "epic_sword"), reconciled.specializations().unlockedSpecializationIds());
    }

    static void revokesIneligibleCatalogSpecializationWithoutErasingUnmanagedState() {
        var state = ProgressionState.empty()
            .withMastery(MasteryState.of(Map.of("epicfight:sword", 60)))
            .withSpecializations(SpecializationProgressionState.of(Set.of("industrialist", "epic_sword")));
        var definition = new SpecializationDefinition(
            "epic_sword",
            Set.of(),
            Map.of("epicfight:sword", 60),
            Set.of("gateway:epic_sword")
        );

        var reconciled = reconcileEligible(state, List.of(definition), InvestmentState.of(List.of()));

        eq(Set.of("industrialist"), reconciled.specializations().unlockedSpecializationIds());
    }

    private static ProgressionState reconcileEligible(
        ProgressionState state,
        List<SpecializationDefinition> definitions,
        InvestmentState investment
    ) {
        try {
            var method = ProgressionService.class.getMethod(
                "reconcileEligibleSpecializations",
                ProgressionState.class,
                java.util.Collection.class,
                InvestmentState.class
            );
            return (ProgressionState) method.invoke(null, state, definitions, investment);
        } catch (NoSuchMethodException missingFeature) {
            throw new AssertionError("ProgressionService.reconcileEligibleSpecializations is missing", missingFeature);
        } catch (IllegalAccessException inaccessible) {
            throw new AssertionError(inaccessible);
        } catch (InvocationTargetException failed) {
            Throwable cause = failed.getCause();
            if (cause instanceof RuntimeException runtime) throw runtime;
            if (cause instanceof Error error) throw error;
            throw new AssertionError(cause);
        }
    }

    private static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError(expected + " != " + actual);
        }
    }
}
