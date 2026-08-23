package dev.gustavopere.rpgskilltree.runtime.compat.identity2;

import dev.gustavopere.rpgskilltree.core.MorphFactionRelations;
import dev.gustavopere.rpgskilltree.core.MorphFormCategory;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public final class MorphCategoryCatalog {
    public static final int DEFAULT_HOSTILITY_MEMORY_SECONDS = 45;

    private static volatile Map<String, MorphFormCategory> overrides = Map.of();
    private static volatile Set<String> blacklist = Set.of();
    private static volatile Map<String, Set<String>> factionsByEntity = Map.of();
    private static volatile Map<String, Set<String>> traitsByEntity = Map.of();
    private static volatile Map<String, MorphFactionRelations> factionRelations = Map.of();
    private static volatile int hostilityMemorySeconds = DEFAULT_HOSTILITY_MEMORY_SECONDS;

    private MorphCategoryCatalog() {}

    public static Map<String, MorphFormCategory> overrides() { return overrides; }
    public static Set<String> blacklist() { return blacklist; }
    public static Map<String, Set<String>> factionsByEntity() { return factionsByEntity; }
    public static Map<String, Set<String>> traitsByEntity() { return traitsByEntity; }
    public static Map<String, MorphFactionRelations> factionRelations() { return factionRelations; }
    public static int hostilityMemorySeconds() { return hostilityMemorySeconds; }

    static void replace(
        Map<String, MorphFormCategory> nextOverrides,
        Set<String> nextBlacklist,
        Map<String, Set<String>> nextFactionsByEntity,
        Map<String, Set<String>> nextTraitsByEntity,
        Map<String, MorphFactionRelations> nextFactionRelations,
        int nextHostilityMemorySeconds
    ) {
        if (nextHostilityMemorySeconds <= 0) {
            throw new IllegalArgumentException("hostility memory seconds must be > 0");
        }
        overrides = Map.copyOf(new LinkedHashMap<>(nextOverrides));
        blacklist = Set.copyOf(new LinkedHashSet<>(nextBlacklist));
        factionsByEntity = deepCopy(nextFactionsByEntity);
        traitsByEntity = deepCopy(nextTraitsByEntity);
        factionRelations = Map.copyOf(new LinkedHashMap<>(nextFactionRelations));
        hostilityMemorySeconds = nextHostilityMemorySeconds;
    }

    private static Map<String, Set<String>> deepCopy(Map<String, Set<String>> source) {
        Map<String, Set<String>> result = new LinkedHashMap<>();
        source.forEach((key, values) -> result.put(key, Set.copyOf(new LinkedHashSet<>(values))));
        return Map.copyOf(result);
    }
}
