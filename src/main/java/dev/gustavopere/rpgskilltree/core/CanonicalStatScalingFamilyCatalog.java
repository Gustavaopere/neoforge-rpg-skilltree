package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Explicit semantic routing from canonical stats to independent scaling dimensions.
 *
 * <p>There are deliberately no name/path heuristics. Optional-provider stats must be registered
 * explicitly so a new or renamed stat cannot silently inherit an unrelated balance curve.</p>
 */
public final class CanonicalStatScalingFamilyCatalog {
    private final Map<CanonicalStatKey, ScalingCurveFamily> families;

    private CanonicalStatScalingFamilyCatalog(Map<CanonicalStatKey, ScalingCurveFamily> families) {
        this.families = Map.copyOf(families);
    }

    public static CanonicalStatScalingFamilyCatalog of(Map<CanonicalStatKey, ScalingCurveFamily> families) {
        Objects.requireNonNull(families, "families");
        HashMap<CanonicalStatKey, ScalingCurveFamily> copy = new HashMap<>();
        for (Map.Entry<CanonicalStatKey, ScalingCurveFamily> entry : families.entrySet()) {
            copy.put(
                Objects.requireNonNull(entry.getKey(), "canonical stat key"),
                Objects.requireNonNull(entry.getValue(), "scaling curve family")
            );
        }
        return new CanonicalStatScalingFamilyCatalog(copy);
    }

    public static CanonicalStatScalingFamilyCatalog vanillaDefaults() {
        return of(Map.of(
            CanonicalStatKey.of("minecraft:max_health"), ScalingCurveFamily.HEALTH,
            CanonicalStatKey.of("minecraft:attack_damage"), ScalingCurveFamily.DAMAGE,
            CanonicalStatKey.of("minecraft:armor"), ScalingCurveFamily.DEFENSE,
            CanonicalStatKey.of("minecraft:armor_toughness"), ScalingCurveFamily.DEFENSE,
            CanonicalStatKey.of("minecraft:knockback_resistance"), ScalingCurveFamily.DEFENSE,
            CanonicalStatKey.of("minecraft:attack_speed"), ScalingCurveFamily.UTILITY,
            CanonicalStatKey.of("minecraft:movement_speed"), ScalingCurveFamily.UTILITY,
            CanonicalStatKey.of("minecraft:luck"), ScalingCurveFamily.UTILITY
        ));
    }

    public CanonicalStatScalingFamilyCatalog extend(Map<CanonicalStatKey, ScalingCurveFamily> extensions) {
        Objects.requireNonNull(extensions, "extensions");
        HashMap<CanonicalStatKey, ScalingCurveFamily> combined = new HashMap<>(families);
        for (Map.Entry<CanonicalStatKey, ScalingCurveFamily> entry : extensions.entrySet()) {
            CanonicalStatKey key = Objects.requireNonNull(entry.getKey(), "canonical stat key");
            ScalingCurveFamily family = Objects.requireNonNull(entry.getValue(), "scaling curve family");
            if (combined.containsKey(key)) {
                throw new IllegalArgumentException("scaling family already registered: " + key.serializedId());
            }
            combined.put(key, family);
        }
        return new CanonicalStatScalingFamilyCatalog(combined);
    }

    public ScalingCurveFamily family(CanonicalStatKey key) {
        Objects.requireNonNull(key, "key");
        ScalingCurveFamily family = families.get(key);
        if (family == null) {
            throw new IllegalStateException("missing scaling curve family: " + key.serializedId());
        }
        return family;
    }

    public Map<CanonicalStatKey, ScalingCurveFamily> asMap() {
        return families;
    }
}
