package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Rank snapshot keyed by semantic A#### catalog code. */
public final class CombatPerkRanks {
    private final Map<String, Integer> ranks;

    private CombatPerkRanks(Map<String, Integer> ranks) {
        Map<String, Integer> copy = new HashMap<>();
        ranks.forEach((code, rank) -> {
            Objects.requireNonNull(code);
            Objects.requireNonNull(rank);
            CombatPerkDefinition definition = NotionCombatPerkCatalog.definition(code)
                .orElseThrow(() -> new IllegalArgumentException("unknown closed-batch combat perk code: " + code));
            if (rank <= 0 || rank > definition.maxRank()) throw new IllegalArgumentException("invalid rank for " + code + ": " + rank);
            copy.put(code, rank);
        });
        this.ranks = Map.copyOf(copy);
    }

    public static CombatPerkRanks empty() { return new CombatPerkRanks(Map.of()); }
    public static CombatPerkRanks of(Map<String, Integer> ranks) { return new CombatPerkRanks(Objects.requireNonNull(ranks)); }
    public int rank(String code) { return ranks.getOrDefault(code, 0); }
    public boolean learned(String code) { return rank(code) > 0; }
    public Map<String, Integer> ranks() { return ranks; }

    public boolean dependenciesSatisfied(CombatPerkDefinition definition) {
        Objects.requireNonNull(definition);
        return definition.dependencies().entrySet().stream().allMatch(entry -> rank(entry.getKey()) >= entry.getValue());
    }
}
