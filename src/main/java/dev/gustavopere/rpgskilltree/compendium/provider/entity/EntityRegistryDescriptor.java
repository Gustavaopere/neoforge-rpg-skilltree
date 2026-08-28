package dev.gustavopere.rpgskilltree.compendium.provider.entity;

import dev.gustavopere.rpgskilltree.compendium.entity.EntityBaseAttribute;
import dev.gustavopere.rpgskilltree.compendium.entity.EntityGameplayCategory;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public record EntityRegistryDescriptor(
    String resourceLocation,
    String sourceModId,
    String translationKey,
    String mobCategory,
    double hitboxWidth,
    double hitboxHeight,
    Set<EntityGameplayCategory> gameplayCategories,
    Map<EntityBaseAttribute, Double> defaultAttributes
) {
    private static final Pattern RESOURCE_LOCATION = Pattern.compile("[a-z0-9_.-]+:[a-z0-9_./-]+");

    public EntityRegistryDescriptor {
        resourceLocation = requireText(resourceLocation, "resourceLocation");
        if (!RESOURCE_LOCATION.matcher(resourceLocation).matches()) {
            throw new IllegalArgumentException("invalid resourceLocation: " + resourceLocation);
        }
        sourceModId = requireText(sourceModId, "sourceModId");
        translationKey = requireText(translationKey, "translationKey");
        mobCategory = requireText(mobCategory, "mobCategory");
        if (!Double.isFinite(hitboxWidth) || hitboxWidth < 0.0D) {
            throw new IllegalArgumentException("hitboxWidth must be non-negative and finite");
        }
        if (!Double.isFinite(hitboxHeight) || hitboxHeight < 0.0D) {
            throw new IllegalArgumentException("hitboxHeight must be non-negative and finite");
        }
        gameplayCategories = Set.copyOf(gameplayCategories == null ? Set.of() : gameplayCategories);
        gameplayCategories.forEach(category -> Objects.requireNonNull(category, "gameplay category"));

        EnumMap<EntityBaseAttribute, Double> attributes = new EnumMap<>(EntityBaseAttribute.class);
        if (defaultAttributes != null) {
            defaultAttributes.forEach((attribute, value) -> {
                Objects.requireNonNull(attribute, "attribute");
                Objects.requireNonNull(value, "attribute value");
                if (!Double.isFinite(value)) throw new IllegalArgumentException("attribute value must be finite");
                attributes.put(attribute, value);
            });
        }
        defaultAttributes = Map.copyOf(attributes);
    }

    private static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException(field + " must not be blank");
        return value.trim();
    }
}
