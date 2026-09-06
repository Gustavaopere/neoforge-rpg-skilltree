package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.SpecializationAvailability;
import dev.gustavopere.rpgskilltree.core.SpecializationDefinition;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** Runtime catalogue for data-driven specialization eligibility definitions. */
public final class SpecializationCatalog {
    private static volatile List<SpecializationDefinition> definitions = List.of();
    private static volatile Map<String, SpecializationAvailability> availability = Map.of();

    private SpecializationCatalog() {}

    /** Provider-agnostic compatibility path for isolated core/tests. */
    public static synchronized void replace(Collection<SpecializationDefinition> next) {
        Objects.requireNonNull(next);
        Map<String, SpecializationAvailability> internalAvailability = new HashMap<>();
        for (SpecializationDefinition definition : next) {
            internalAvailability.put(definition.specializationId(), SpecializationAvailability.internal());
        }
        replace(next, internalAvailability);
    }

    public static synchronized void replace(
        Collection<SpecializationDefinition> next,
        Map<String, SpecializationAvailability> nextAvailability
    ) {
        Objects.requireNonNull(next);
        Objects.requireNonNull(nextAvailability);
        var sorted = next.stream()
            .sorted(Comparator.comparing(SpecializationDefinition::specializationId))
            .toList();
        long unique = sorted.stream().map(SpecializationDefinition::specializationId).distinct().count();
        if (unique != sorted.size()) {
            throw new IllegalArgumentException("duplicate specialization definition id");
        }

        var definitionIds = sorted.stream()
            .map(SpecializationDefinition::specializationId)
            .collect(java.util.stream.Collectors.toUnmodifiableSet());
        if (!definitionIds.equals(nextAvailability.keySet())) {
            throw new IllegalArgumentException("specialization availability keys must match definition ids");
        }
        Map<String, SpecializationAvailability> validatedAvailability = new HashMap<>();
        for (Map.Entry<String, SpecializationAvailability> entry : nextAvailability.entrySet()) {
            validatedAvailability.put(
                Objects.requireNonNull(entry.getKey(), "specializationId"),
                Objects.requireNonNull(entry.getValue(), "availability")
            );
        }

        definitions = List.copyOf(sorted);
        availability = Map.copyOf(validatedAvailability);
    }

    public static List<SpecializationDefinition> definitions() {
        return definitions;
    }

    public static Optional<SpecializationDefinition> definition(String id) {
        Objects.requireNonNull(id);
        return definitions.stream()
            .filter(definition -> definition.specializationId().equals(id))
            .findFirst();
    }

    public static Optional<SpecializationAvailability> availability(String id) {
        Objects.requireNonNull(id);
        return Optional.ofNullable(availability.get(id));
    }

    /** Unknown or unavailable specialization ids fail closed. */
    public static boolean gatewayAvailable(String id) {
        Objects.requireNonNull(id);
        SpecializationAvailability current = availability.get(id);
        return current != null && current.gatewayAvailable();
    }

    public static int size() {
        return definitions.size();
    }
}
