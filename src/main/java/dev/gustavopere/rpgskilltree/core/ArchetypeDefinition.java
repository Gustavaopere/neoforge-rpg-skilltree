package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public record ArchetypeDefinition(
    String id,
    int priority,
    int specificityScore,
    Map<ProgressionDomain, Integer> minimumDomainScores,
    Set<String> requiredTags,
    Set<String> forbiddenTags
) {
    public ArchetypeDefinition {
        Objects.requireNonNull(id);
        Objects.requireNonNull(minimumDomainScores);
        Objects.requireNonNull(requiredTags);
        Objects.requireNonNull(forbiddenTags);
        if (id.isBlank()) {
            throw new IllegalArgumentException("id must not be blank");
        }
        if (specificityScore < 0) {
            throw new IllegalArgumentException("specificityScore must be >= 0");
        }
        minimumDomainScores = Map.copyOf(minimumDomainScores);
        requiredTags = Set.copyOf(requiredTags);
        forbiddenTags = Set.copyOf(forbiddenTags);
    }

    /**
     * Compatibility constructor for prototype definitions that predate the
     * explicit master-design specificity score. Runtime/data definitions should
     * supply specificityScore directly once the archetype reloader is wired.
     */
    public ArchetypeDefinition(
        String id,
        int priority,
        Map<ProgressionDomain, Integer> minimumDomainScores,
        Set<String> requiredTags,
        Set<String> forbiddenTags
    ) {
        this(
            id,
            priority,
            minimumDomainScores.size() + requiredTags.size() + forbiddenTags.size(),
            minimumDomainScores,
            requiredTags,
            forbiddenTags
        );
    }

    /**
     * Backward-compatible semantic accessor. New code should use
     * {@link #specificityScore()}.
     */
    public int specificity() {
        return specificityScore;
    }
}
