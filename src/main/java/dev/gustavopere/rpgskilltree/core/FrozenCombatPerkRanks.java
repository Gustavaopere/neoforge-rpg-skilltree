package dev.gustavopere.rpgskilltree.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/** Validated rank snapshot for A0051-A0100 only. */
public final class FrozenCombatPerkRanks {
    private static final FrozenCombatPerkRanks EMPTY = new FrozenCombatPerkRanks(Map.of());
    private final Map<String, Integer> ranks;

    private FrozenCombatPerkRanks(Map<String, Integer> ranks) { this.ranks = ranks; }

    public static FrozenCombatPerkRanks empty() { return EMPTY; }

    public static FrozenCombatPerkRanks of(Map<String, Integer> source) {
        Objects.requireNonNull(source);
        LinkedHashMap<String, Integer> copy = new LinkedHashMap<>();
        source.forEach((code, rank) -> {
            FrozenCombatPerkDefinition definition = FrozenA0051A0100Catalog.definition(code)
                .orElseThrow(() -> new IllegalArgumentException("unknown frozen perk: " + code));
            if (rank == null || rank < 0 || rank > definition.maxRank()) {
                throw new IllegalArgumentException("invalid rank for " + code + ": " + rank);
            }
            if (rank > 0) copy.put(code, rank);
        });
        return copy.isEmpty() ? EMPTY : new FrozenCombatPerkRanks(Map.copyOf(copy));
    }

    public int rank(String code) { return ranks.getOrDefault(code, 0); }

    public boolean learned(String code) { return rank(code) > 0; }

    public Map<String, Integer> ranks() { return ranks; }
}
