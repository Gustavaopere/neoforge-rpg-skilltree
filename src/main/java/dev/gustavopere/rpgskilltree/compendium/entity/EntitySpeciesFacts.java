package dev.gustavopere.rpgskilltree.compendium.entity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public record EntitySpeciesFacts(
    String resourceLocation,
    String sourceModId,
    String translationKey,
    Set<EntityGameplayCategory> gameplayCategories,
    String mobCategory,
    double hitboxWidth,
    double hitboxHeight,
    Map<String, Double> baseAttributes
) {
    private static final Pattern RESOURCE_LOCATION = Pattern.compile("[a-z0-9_.-]+:[a-z0-9_./-]+");
    private static final Pattern MOD_ID = Pattern.compile("[a-z0-9_.-]+");

    public EntitySpeciesFacts {
        resourceLocation = requireText(resourceLocation, "resourceLocation");
        if (!RESOURCE_LOCATION.matcher(resourceLocation).matches()) {
            throw new IllegalArgumentException("invalid resourceLocation: " + resourceLocation);
        }
        sourceModId = requireText(sourceModId, "sourceModId");
        if (!MOD_ID.matcher(sourceModId).matches()) {
            throw new IllegalArgumentException("invalid sourceModId: " + sourceModId);
        }
        translationKey = requireText(translationKey, "translationKey");
        gameplayCategories = Set.copyOf(gameplayCategories == null ? Set.of() : gameplayCategories);
        for (EntityGameplayCategory category : gameplayCategories) Objects.requireNonNull(category, "gameplay category");
        mobCategory = normalizeNullable(mobCategory);
        if (!Double.isFinite(hitboxWidth) || hitboxWidth < 0.0D) {
            throw new IllegalArgumentException("hitboxWidth must be non-negative and finite");
        }
        if (!Double.isFinite(hitboxHeight) || hitboxHeight < 0.0D) {
            throw new IllegalArgumentException("hitboxHeight must be non-negative and finite");
        }
        LinkedHashMap<String, Double> attributes = new LinkedHashMap<>();
        if (baseAttributes != null) {
            baseAttributes.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
                String key = requireText(entry.getKey(), "attribute key");
                Double value = Objects.requireNonNull(entry.getValue(), "attribute value");
                if (!Double.isFinite(value)) throw new IllegalArgumentException("attribute must be finite: " + key);
                attributes.put(key, value);
            });
        }
        baseAttributes = Map.copyOf(attributes);
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
