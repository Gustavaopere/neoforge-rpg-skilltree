package dev.gustavopere.rpgskilltree.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Pure core snapshot of runtime provider availability for class definitions.
 *
 * <p>The runtime data loader replaces this snapshot from the currently loaded datapack and mod
 * list. Classes absent from the snapshot remain available so provider-agnostic definitions and
 * isolated core tests do not acquire an implicit external dependency.</p>
 */
public final class ProviderClassAvailabilityRegistry {
    private static volatile Map<String, Boolean> availability = Map.of();

    private ProviderClassAvailabilityRegistry() {}

    public static synchronized void replace(Map<String, Boolean> next) {
        Objects.requireNonNull(next, "next");
        Map<String, Boolean> validated = new LinkedHashMap<>();
        next.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> {
                String classId = Objects.requireNonNull(entry.getKey(), "classId");
                Boolean available = Objects.requireNonNull(entry.getValue(), "available");
                if (classId.isBlank()) {
                    throw new IllegalArgumentException("class id must not be blank");
                }
                validated.put(classId, available);
            });
        availability = Map.copyOf(validated);
    }

    public static boolean isAvailable(String classId) {
        Objects.requireNonNull(classId, "classId");
        return availability.getOrDefault(classId, true);
    }

    public static Map<String, Boolean> snapshot() {
        return availability;
    }
}
