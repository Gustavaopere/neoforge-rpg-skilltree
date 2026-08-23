package dev.gustavopere.rpgskilltree.runtime.compat.identity2;

import dev.gustavopere.rpgskilltree.core.MorphFormCategory;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public final class MorphCategoryCatalog {
    private static volatile Map<String, MorphFormCategory> overrides = Map.of();
    private static volatile Set<String> blacklist = Set.of();

    private MorphCategoryCatalog() {}

    public static Map<String, MorphFormCategory> overrides() { return overrides; }
    public static Set<String> blacklist() { return blacklist; }

    static void replace(Map<String, MorphFormCategory> nextOverrides, Set<String> nextBlacklist) {
        overrides = Map.copyOf(new LinkedHashMap<>(nextOverrides));
        blacklist = Set.copyOf(new LinkedHashSet<>(nextBlacklist));
    }
}
