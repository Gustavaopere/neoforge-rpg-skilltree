package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.Set;

/** Extensible evidence captured by an adapter without embedding NeoForge types in the Core. */
public record SemanticActionContext(
    OptionalLong packedBlockPosition,
    Map<String, Double> metrics,
    Set<String> tags
) {
    public SemanticActionContext {
        Objects.requireNonNull(packedBlockPosition, "packedBlockPosition");
        Objects.requireNonNull(metrics, "metrics");
        Objects.requireNonNull(tags, "tags");
        for (var entry : metrics.entrySet()) {
            if (entry.getKey() == null || entry.getKey().isBlank()) {
                throw new IllegalArgumentException("semantic metric key must not be blank");
            }
            if (entry.getValue() == null || !Double.isFinite(entry.getValue())) {
                throw new IllegalArgumentException("semantic metric value must be finite");
            }
        }
        for (String tag : tags) {
            if (tag == null || tag.isBlank()) throw new IllegalArgumentException("semantic tag must not be blank");
        }
        metrics = Map.copyOf(metrics);
        tags = Set.copyOf(tags);
    }

    public static SemanticActionContext empty() {
        return new SemanticActionContext(OptionalLong.empty(), Map.of(), Set.of());
    }

    public static SemanticActionContext atBlock(long packedBlockPosition) {
        return new SemanticActionContext(OptionalLong.of(packedBlockPosition), Map.of(), Set.of());
    }
}
