package dev.gustavopere.rpgskilltree.core;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Explicit contribution emitted when one canonical Mastery lane reaches a threshold. */
public record MasteryInvestmentMetadata(
    String laneId,
    int minimumExperience,
    Map<ProgressionDomain, Integer> domainWeights,
    Set<String> tags
) {
    public MasteryInvestmentMetadata {
        Objects.requireNonNull(laneId, "laneId");
        Objects.requireNonNull(domainWeights, "domainWeights");
        Objects.requireNonNull(tags, "tags");
        if (laneId.isBlank()) throw new IllegalArgumentException("mastery lane id must not be blank");
        if (minimumExperience <= 0) throw new IllegalArgumentException("minimumExperience must be positive");

        EnumMap<ProgressionDomain, Integer> weights = new EnumMap<>(ProgressionDomain.class);
        domainWeights.forEach((domain, weight) -> {
            Objects.requireNonNull(domain, "domain");
            Objects.requireNonNull(weight, "weight");
            if (weight <= 0) throw new IllegalArgumentException("mastery contribution weight must be positive");
            weights.put(domain, weight);
        });
        domainWeights = Map.copyOf(weights);

        for (String tag : tags) {
            Objects.requireNonNull(tag, "tag");
            if (tag.isBlank()) throw new IllegalArgumentException("mastery investment tag must not be blank");
        }
        tags = Set.copyOf(tags);
    }
}
