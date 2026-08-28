package dev.gustavopere.rpgskilltree.runtime.effects;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Deduplicated runtime diagnostics for node effects whose concrete attribute target
 * cannot be resolved for a server player. Optional provider absence remains non-fatal,
 * but the resulting unavailable effect is observable instead of silently ignored.
 */
public final class AttributeEffectDiagnostics {
    public enum Reason {
        MISSING_REGISTRY_TARGET,
        MISSING_PLAYER_ATTRIBUTE
    }

    public record Entry(String effectId, String attributeId, Reason reason) {
        public Entry {
            Objects.requireNonNull(effectId, "effectId");
            Objects.requireNonNull(attributeId, "attributeId");
            Objects.requireNonNull(reason, "reason");
            if (effectId.isBlank()) throw new IllegalArgumentException("effectId must not be blank");
            if (attributeId.isBlank()) throw new IllegalArgumentException("attributeId must not be blank");
        }
    }

    private static final ConcurrentMap<String, Entry> ENTRIES = new ConcurrentHashMap<>();

    private AttributeEffectDiagnostics() {}

    /**
     * Records an unavailable target and returns true only for the first identical
     * effect/reason report since the last successful node-effect reload.
     */
    public static boolean report(String effectId, String attributeId, Reason reason) {
        Entry entry = new Entry(effectId, attributeId, reason);
        String key = effectId + '\u0000' + reason.name();
        return ENTRIES.putIfAbsent(key, entry) == null;
    }

    /** Returns a deterministic immutable snapshot suitable for diagnostics/debug UI. */
    public static List<Entry> snapshot() {
        return ENTRIES.values().stream()
            .sorted(Comparator.comparing(Entry::effectId).thenComparing(entry -> entry.reason().name()))
            .toList();
    }

    /** Clears stale availability observations after a successful effect-catalog reload. */
    public static void clear() {
        ENTRIES.clear();
    }
}
