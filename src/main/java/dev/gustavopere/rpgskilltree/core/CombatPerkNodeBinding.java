package dev.gustavopere.rpgskilltree.core;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Explicit bridge between semantic A#### catalog codes and persistent physical node ids.
 *
 * <p>Legacy {@code rpgskilltree:martial_###} ids are intentionally not aliases.
 */
public final class CombatPerkNodeBinding {
    private static final String PREFIX = "rpgskilltree:combat/";

    private CombatPerkNodeBinding() {}

    public static String nodeId(String catalogCode) {
        CombatPerkDefinition definition = NotionCombatPerkCatalog.definition(catalogCode)
            .orElseThrow(() -> new IllegalArgumentException("unknown combat perk catalog code: " + catalogCode));
        return PREFIX + definition.code().toLowerCase(Locale.ROOT);
    }

    public static Optional<String> catalogCode(String nodeId) {
        if (nodeId == null || !nodeId.startsWith(PREFIX)) return Optional.empty();
        String suffix = nodeId.substring(PREFIX.length());
        if (suffix.length() != 5 || suffix.charAt(0) != 'a') return Optional.empty();
        String code = suffix.toUpperCase(Locale.ROOT);
        return NotionCombatPerkCatalog.definition(code).map(CombatPerkDefinition::code);
    }

    public static CombatPerkRanks ranks(PassiveNodeProgress progress) {
        Objects.requireNonNull(progress);
        Map<String, Integer> ranks = new LinkedHashMap<>();
        for (String nodeId : progress.learnedNodeIds()) {
            catalogCode(nodeId).ifPresent(code -> ranks.put(code, progress.rank(nodeId)));
        }
        return CombatPerkRanks.of(ranks);
    }
}
