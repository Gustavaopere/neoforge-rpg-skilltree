package dev.gustavopere.rpgskilltree.compendium.flora;

import java.util.Objects;
import java.util.Set;

/** Verified botanical facts before projection into a Compendium entry. */
public record FloraSpeciesFacts(
    String resourceLocation,
    String sourceModId,
    String translationKey,
    FloraKind floraKind,
    Set<String> categories,
    Set<String> relatedBlockIds,
    Set<String> relatedItemIds,
    Integer maxGrowthStage,
    Long deterministicGrowthTicks,
    String seedItemId,
    String produceItemId
) {
    public FloraSpeciesFacts {
        resourceLocation = resourceId(resourceLocation, "resourceLocation");
        sourceModId = token(sourceModId, "sourceModId");
        translationKey = token(translationKey, "translationKey");
        Objects.requireNonNull(floraKind, "floraKind");
        Objects.requireNonNull(categories, "categories");
        Objects.requireNonNull(relatedBlockIds, "relatedBlockIds");
        Objects.requireNonNull(relatedItemIds, "relatedItemIds");
        categories = categories.stream().map(value -> token(value, "category")).collect(java.util.stream.Collectors.toUnmodifiableSet());
        relatedBlockIds = relatedBlockIds.stream().map(value -> resourceId(value, "relatedBlockId")).collect(java.util.stream.Collectors.toUnmodifiableSet());
        relatedItemIds = relatedItemIds.stream().map(value -> resourceId(value, "relatedItemId")).collect(java.util.stream.Collectors.toUnmodifiableSet());
        if (maxGrowthStage != null && maxGrowthStage < 0) throw new IllegalArgumentException("maxGrowthStage must be >= 0");
        if (deterministicGrowthTicks != null && deterministicGrowthTicks <= 0L) throw new IllegalArgumentException("deterministicGrowthTicks must be > 0");
        if (seedItemId != null) seedItemId = resourceId(seedItemId, "seedItemId");
        if (produceItemId != null) produceItemId = resourceId(produceItemId, "produceItemId");
    }

    private static String resourceId(String value, String field) {
        String normalized = token(value, field);
        int colon = normalized.indexOf(':');
        if (colon <= 0 || colon == normalized.length() - 1) throw new IllegalArgumentException(field + " must be a resource id");
        return normalized;
    }

    private static String token(String value, String field) {
        Objects.requireNonNull(value, field);
        String normalized = value.trim();
        if (normalized.isEmpty()) throw new IllegalArgumentException(field + " must not be blank");
        return normalized;
    }
}
