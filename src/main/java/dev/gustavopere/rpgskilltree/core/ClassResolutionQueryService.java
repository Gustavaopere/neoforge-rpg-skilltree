package dev.gustavopere.rpgskilltree.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/** Read-only boundary for deterministic emergent-class resolution. */
public final class ClassResolutionQueryService {
    private ClassResolutionQueryService() {}

    public static EmergentClassResolution resolve(
        InvestmentState state,
        Collection<ArchetypeDefinition> definitions
    ) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(definitions, "definitions");
        List<ArchetypeDefinition> snapshot = List.copyOf(definitions);
        rejectDuplicateIds(snapshot);
        return ArchetypeResolver.resolveHierarchy(state, snapshot);
    }

    public static CanonicalClassResolutionProjection resolveCanonical(
        ProgressionState state,
        Map<String, NodeInvestmentMetadata> nodeMetadata,
        Collection<MasteryInvestmentMetadata> masteryMetadata,
        Collection<ArchetypeDefinition> definitions
    ) {
        Objects.requireNonNull(definitions, "definitions");
        List<ArchetypeDefinition> snapshot = List.copyOf(definitions);
        rejectDuplicateIds(snapshot);
        CanonicalInvestmentProjection projection = CanonicalInvestmentProjector.project(
            state, nodeMetadata, masteryMetadata
        );
        if (!projection.complete()) {
            return new CanonicalClassResolutionProjection(
                projection.investmentState(), projection.missingNodeIds(), Optional.empty()
            );
        }
        return new CanonicalClassResolutionProjection(
            projection.investmentState(), Set.of(),
            Optional.of(ArchetypeResolver.resolveHierarchy(projection.investmentState(), snapshot))
        );
    }

    private static void rejectDuplicateIds(List<ArchetypeDefinition> definitions) {
        Set<String> seen = new HashSet<>();
        for (ArchetypeDefinition definition : definitions) {
            if (!seen.add(definition.id())) {
                throw new IllegalArgumentException("duplicate archetype definition id: " + definition.id());
            }
        }
    }
}
