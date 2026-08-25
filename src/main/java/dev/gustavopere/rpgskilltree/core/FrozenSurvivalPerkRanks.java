package dev.gustavopere.rpgskilltree.core;

import java.util.LinkedHashMap;
import java.util.Map;

/** Validated immutable rank projection for A0101-A0150. */
public final class FrozenSurvivalPerkRanks {
    private static final FrozenSurvivalPerkRanks EMPTY = new FrozenSurvivalPerkRanks(Map.of());
    private final Map<String, Integer> ranks;

    private FrozenSurvivalPerkRanks(Map<String, Integer> ranks) { this.ranks = ranks; }

    public static FrozenSurvivalPerkRanks empty() { return EMPTY; }

    public static FrozenSurvivalPerkRanks of(Map<String, Integer> source) {
        LinkedHashMap<String, Integer> copy = new LinkedHashMap<>();
        source.forEach((code, rank) -> {
            FrozenSurvivalPerkDefinition definition = FrozenA0101A0150Catalog.definition(code)
                .orElseThrow(() -> new IllegalArgumentException("unknown frozen perk: " + code));
            if (rank == null || rank < 0 || rank > definition.maxRank()) {
                throw new IllegalArgumentException("invalid rank for " + code + ": " + rank);
            }
            if (rank > 0) copy.put(code, rank);
        });
        return copy.isEmpty() ? EMPTY : new FrozenSurvivalPerkRanks(Map.copyOf(copy));
    }

    public int rank(String code) { return ranks.getOrDefault(code, 0); }

    public Map<String, Integer> asMap() { return ranks; }
}
