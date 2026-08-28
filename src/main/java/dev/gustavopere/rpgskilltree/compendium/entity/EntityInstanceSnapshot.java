package dev.gustavopere.rpgskilltree.compendium.entity;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.List;
import java.util.Map;

/**
 * Bounded, explicitly whitelisted state from one already-existing server entity.
 * Raw NBT and arbitrary synchronized entity data are intentionally absent.
 */
public record EntityInstanceSnapshot(
    String entityId,
    float width,
    float height,
    Double currentHealth,
    Double maxHealth,
    Map<String, Double> currentAttributes,
    Boolean baby,
    Integer ageTicks,
    List<EntityEffectSnapshot> effects,
    Boolean tame,
    String ownerId,
    Boolean sitting,
    Boolean breedReady,
    boolean noAi,
    boolean invulnerable,
    boolean silent,
    boolean leashed
) {
    public EntityInstanceSnapshot {
        CompendiumEntryId.of(CompendiumEntryKind.ENTITY, entityId);
        requireFiniteNonNegative(width, "width");
        requireFiniteNonNegative(height, "height");
        requireFiniteNonNegative(currentHealth, "currentHealth");
        requireFiniteNonNegative(maxHealth, "maxHealth");
        currentAttributes = Map.copyOf(currentAttributes == null ? Map.of() : currentAttributes);
        effects = List.copyOf(effects == null ? List.of() : effects);
    }

    private static void requireFiniteNonNegative(float value, String label) {
        if (!Float.isFinite(value) || value < 0.0F) {
            throw new IllegalArgumentException(label + " must be finite and non-negative");
        }
    }

    private static void requireFiniteNonNegative(Double value, String label) {
        if (value != null && (!Double.isFinite(value) || value < 0.0D)) {
            throw new IllegalArgumentException(label + " must be finite and non-negative");
        }
    }
}
