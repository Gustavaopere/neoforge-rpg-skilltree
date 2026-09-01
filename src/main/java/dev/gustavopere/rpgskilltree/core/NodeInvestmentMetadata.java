package dev.gustavopere.rpgskilltree.core;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Explicit read-only contribution metadata for one purchased skill-tree node. */
public record NodeInvestmentMetadata(
    Map<ProgressionDomain, Integer> domainWeightsPerRank,
    Set<String> tags
) {
    public NodeInvestmentMetadata {
        Objects.requireNonNull(domainWeightsPerRank, "domainWeightsPerRank");
        Objects.requireNonNull(tags, "tags");

        EnumMap<ProgressionDomain, Integer> weights = new EnumMap<>(ProgressionDomain.class);
        domainWeightsPerRank.forEach((domain, weight) -> {
            Objects.requireNonNull(domain, "domain");
            Objects.requireNonNull(weight, "weight");
            if (weight <= 0) throw new IllegalArgumentException("domain contribution weight must be positive");
            weights.put(domain, weight);
        });
        domainWeightsPerRank = Map.copyOf(weights);

        for (String tag : tags) {
            Objects.requireNonNull(tag, "tag");
            if (tag.isBlank()) throw new IllegalArgumentException("investment tag must not be blank");
        }
        tags = Set.copyOf(tags);
    }

    public static NodeInvestmentMetadata neutral() {
        return new NodeInvestmentMetadata(Map.of(), Set.of());
    }
}
