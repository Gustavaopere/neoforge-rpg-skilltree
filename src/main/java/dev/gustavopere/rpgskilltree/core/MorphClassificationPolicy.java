package dev.gustavopere.rpgskilltree.core;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Pure classification policy. Platform adapters supply the entity id/spawn category and datapack overrides. */
public final class MorphClassificationPolicy {
    private MorphClassificationPolicy() {}

    public static MorphFormCategory classify(
        String entityId,
        String spawnCategory,
        Map<String, MorphFormCategory> overrides
    ) {
        Objects.requireNonNull(entityId);
        Objects.requireNonNull(spawnCategory);
        Objects.requireNonNull(overrides);
        MorphFormCategory override = overrides.get(entityId);
        if (override != null) return override;

        return switch (spawnCategory.toLowerCase(Locale.ROOT)) {
            case "creature" -> MorphFormCategory.NATURAL_LAND;
            case "ambient" -> MorphFormCategory.NATURAL_FLYING;
            case "axolotls", "underground_water_creature", "water_creature", "water_ambient" ->
                MorphFormCategory.NATURAL_AQUATIC;
            case "monster" -> MorphFormCategory.MONSTER;
            default -> MorphFormCategory.TECHNICAL;
        };
    }

    public static MorphFormDescriptor describe(
        String entityId,
        String spawnCategory,
        Map<String, MorphFormCategory> overrides,
        Set<String> blacklist
    ) {
        Objects.requireNonNull(blacklist);
        boolean blocked = blacklist.contains(entityId);
        return new MorphFormDescriptor(
            entityId,
            classify(entityId, spawnCategory, overrides),
            blocked ? Set.of("rpgskilltree:morph_blacklist") : Set.of()
        );
    }
}
