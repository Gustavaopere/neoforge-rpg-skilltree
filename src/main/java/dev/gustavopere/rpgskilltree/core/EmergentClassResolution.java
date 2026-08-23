package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Calculated class identity for display/gameplay consumers.
 *
 * <p>This is deliberately not persisted. Primary/secondary identity is derived
 * from the current build so respec and new mastery can change it naturally.</p>
 */
public record EmergentClassResolution(
    Optional<String> primaryClassId,
    List<String> secondaryClassIds,
    List<ArchetypeMatch> orderedMatches
) {
    public EmergentClassResolution {
        Objects.requireNonNull(primaryClassId);
        Objects.requireNonNull(secondaryClassIds);
        Objects.requireNonNull(orderedMatches);
        secondaryClassIds = List.copyOf(secondaryClassIds);
        orderedMatches = List.copyOf(orderedMatches);
        if (primaryClassId.isEmpty() && !secondaryClassIds.isEmpty()) {
            throw new IllegalArgumentException("secondary classes require a primary class");
        }
        if (primaryClassId.isPresent() && secondaryClassIds.contains(primaryClassId.get())) {
            throw new IllegalArgumentException("primary class cannot also be secondary");
        }
    }

    public static EmergentClassResolution fromOrderedMatches(List<ArchetypeMatch> matches) {
        Objects.requireNonNull(matches);
        List<ArchetypeMatch> stable = List.copyOf(matches);
        if (stable.isEmpty()) return new EmergentClassResolution(Optional.empty(), List.of(), List.of());
        return new EmergentClassResolution(
            Optional.of(stable.getFirst().archetypeId()),
            stable.stream().skip(1).map(ArchetypeMatch::archetypeId).toList(),
            stable
        );
    }
}
