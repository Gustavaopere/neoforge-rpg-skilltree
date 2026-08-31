package dev.gustavopere.rpgskilltree.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Read-only boundary for deterministic emergent-class resolution.
 *
 * <p>The caller must provide an already-authoritative investment snapshot. This
 * service deliberately does not inspect persisted player state, purchased nodes,
 * providers, or event history. That keeps class resolution a pure projection and
 * prevents this boundary from becoming a second progression authority.</p>
 */
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

    private static void rejectDuplicateIds(List<ArchetypeDefinition> definitions) {
        Set<String> seen = new HashSet<>();
        for (ArchetypeDefinition definition : definitions) {
            if (!seen.add(definition.id())) {
                throw new IllegalArgumentException(
                    "duplicate archetype definition id: " + definition.id()
                );
            }
        }
    }
}
