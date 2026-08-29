package dev.gustavopere.rpgskilltree.compendium.api;

import java.util.Objects;

public record CompendiumFact<T>(
    String factKey,
    T value,
    String unit,
    FactSource source,
    FactConfidence confidence,
    FactVisibility visibility,
    Long capturedAtEpochMillis,
    String providerId
) {
    public CompendiumFact(
        String factKey,
        T value,
        String unit,
        FactSource source,
        FactConfidence confidence,
        FactVisibility visibility,
        Long capturedAtEpochMillis
    ) {
        this(factKey, value, unit, source, confidence, visibility, capturedAtEpochMillis, null);
    }

    public CompendiumFact {
        factKey = requireText(factKey, "factKey");
        unit = normalizeNullable(unit);
        Objects.requireNonNull(source, "source");
        Objects.requireNonNull(confidence, "confidence");
        Objects.requireNonNull(visibility, "visibility");
        if (confidence == FactConfidence.UNAVAILABLE) {
            if (value != null) throw new IllegalArgumentException("UNAVAILABLE fact must not carry a value: " + factKey);
        } else if (value == null) {
            throw new IllegalArgumentException("available fact requires a value: " + factKey);
        }
        if (capturedAtEpochMillis != null && capturedAtEpochMillis < 0L) {
            throw new IllegalArgumentException("capturedAtEpochMillis must be non-negative");
        }
        providerId = normalizeNullable(providerId);
    }

    public static CompendiumFact<Object> unavailable(
        String factKey,
        FactSource source,
        FactVisibility visibility
    ) {
        return new CompendiumFact<>(factKey, null, null, source, FactConfidence.UNAVAILABLE, visibility, null);
    }

    public boolean isConfirmed() {
        return confidence != FactConfidence.UNAVAILABLE && value != null;
    }

    public CompendiumFact<T> withProviderId(String providerId) {
        return new CompendiumFact<>(
            factKey,
            value,
            unit,
            source,
            confidence,
            visibility,
            capturedAtEpochMillis,
            providerId
        );
    }

    private static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException(field + " must not be blank");
        return value.trim();
    }

    private static String normalizeNullable(String value) {
        if (value == null) return null;
        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}
