package dev.gustavopere.rpgskilltree.compendium.discovery;

import java.util.Map;
import java.util.Objects;

/** Immutable completion summary derived from the currently loaded eligible catalog. */
public record DiscoveryCompletionSummary(
    DiscoveryCompletionCount global,
    Map<String, DiscoveryCompletionCount> byCategory,
    Map<String, DiscoveryCompletionCount> byNamespace
) {
    public DiscoveryCompletionSummary {
        Objects.requireNonNull(global, "global");
        byCategory = Map.copyOf(byCategory == null ? Map.of() : byCategory);
        byNamespace = Map.copyOf(byNamespace == null ? Map.of() : byNamespace);
    }
}
