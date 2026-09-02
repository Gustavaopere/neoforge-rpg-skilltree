package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

/**
 * Data-driven safety/targeting metadata for one Iron's spell.
 *
 * <p>The profile deliberately does not contain damage, mana cost, spell level, cast time or
 * cooldown. Those values remain authoritative in Iron's Spells 'n Spellbooks.</p>
 */
public record BattleMageSpellProfile(
    ResourceLocation spellId,
    BattleMageTargetMode targetMode,
    int priority,
    double minRange,
    double maxRange,
    double friendlyFireRadius,
    boolean worldEffect,
    boolean allySafe
) {
    public BattleMageSpellProfile {
        Objects.requireNonNull(spellId, "spellId");
        Objects.requireNonNull(targetMode, "targetMode");
        if (priority < 0 || priority > 10_000) {
            throw new IllegalArgumentException("priority out of range: " + priority);
        }
        if (!Double.isFinite(minRange) || !Double.isFinite(maxRange)
            || minRange < 0.0 || maxRange < minRange) {
            throw new IllegalArgumentException("invalid range: " + minRange + ".." + maxRange);
        }
        if (!Double.isFinite(friendlyFireRadius) || friendlyFireRadius < 0.0) {
            throw new IllegalArgumentException("invalid friendly-fire radius: " + friendlyFireRadius);
        }
    }

    /** Parses a provider/config boundary value; runtime identity remains ResourceLocation-only. */
    static ResourceLocation parseNamespacedId(String raw) {
        Objects.requireNonNull(raw, "spellId");
        if (raw.indexOf(':') <= 0) {
            throw new IllegalArgumentException("spellId must be namespaced: " + raw);
        }
        ResourceLocation parsed = ResourceLocation.tryParse(raw);
        if (parsed == null) {
            throw new IllegalArgumentException("invalid spell id: " + raw);
        }
        return parsed;
    }
}
