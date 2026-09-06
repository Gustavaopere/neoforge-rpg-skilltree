package dev.gustavopere.volcanoes.pressure;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** One physical/logical source of one or more independent protection capabilities. */
public record ProtectionContribution(
        String sourceId,
        Map<ProtectionCapability, Double> ratings,
        Optional<ProtectionResourceConsumer> resourceConsumer,
        String resourceDebitKey
) {
    public ProtectionContribution {
        sourceId = Objects.requireNonNull(sourceId, "sourceId").trim();
        if (sourceId.isEmpty()) {
            throw new IllegalArgumentException("sourceId must not be blank");
        }
        Objects.requireNonNull(ratings, "ratings");
        Objects.requireNonNull(resourceConsumer, "resourceConsumer");
        resourceDebitKey = Objects.requireNonNull(resourceDebitKey, "resourceDebitKey").trim();
        if (resourceDebitKey.isEmpty()) {
            throw new IllegalArgumentException("resourceDebitKey must not be blank");
        }

        EnumMap<ProtectionCapability, Double> copy = new EnumMap<>(ProtectionCapability.class);
        for (Map.Entry<ProtectionCapability, Double> entry : ratings.entrySet()) {
            ProtectionCapability capability = Objects.requireNonNull(entry.getKey(), "capability");
            Double boxedRating = Objects.requireNonNull(entry.getValue(), "rating");
            double rating = boxedRating;
            if (!Double.isFinite(rating) || rating < 0.0) {
                throw new IllegalArgumentException("protection ratings must be finite and non-negative");
            }
            if (rating > 0.0) {
                copy.put(capability, rating);
            }
        }
        ratings = Map.copyOf(copy);
    }

    /** Backward-compatible constructor: source identity is also the debit identity. */
    public ProtectionContribution(
            String sourceId,
            Map<ProtectionCapability, Double> ratings,
            Optional<ProtectionResourceConsumer> resourceConsumer
    ) {
        this(sourceId, ratings, resourceConsumer, sourceId);
    }

    public static ProtectionContribution passive(String sourceId, Map<ProtectionCapability, Double> ratings) {
        return new ProtectionContribution(sourceId, ratings, Optional.empty(), sourceId);
    }

    /**
     * Backward-compatible consumable factory. Use the overload with resourceDebitKey when multiple
     * logical contributions draw from the same physical resource during one update.
     */
    public static ProtectionContribution consumable(
            String sourceId,
            Map<ProtectionCapability, Double> ratings,
            ProtectionResourceConsumer consumer
    ) {
        return consumable(sourceId, sourceId, ratings, consumer);
    }

    public static ProtectionContribution consumable(
            String sourceId,
            String resourceDebitKey,
            Map<ProtectionCapability, Double> ratings,
            ProtectionResourceConsumer consumer
    ) {
        return new ProtectionContribution(
                sourceId,
                ratings,
                Optional.of(Objects.requireNonNull(consumer, "consumer")),
                resourceDebitKey);
    }
}
