package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/** Immutable semantic definition for one audited A#### combat perk. */
public record CombatPerkDefinition(
    String code,
    String name,
    WeaponFamily weaponFamily,
    EffectKind effectKind,
    int maxRank,
    int rankCost,
    Map<String, Integer> dependencies
) {
    private static final Pattern CODE = Pattern.compile("A\\d{4}");

    public CombatPerkDefinition {
        Objects.requireNonNull(code);
        Objects.requireNonNull(name);
        Objects.requireNonNull(weaponFamily);
        Objects.requireNonNull(effectKind);
        Objects.requireNonNull(dependencies);
        if (!CODE.matcher(code).matches()) throw new IllegalArgumentException("invalid catalog code: " + code);
        if (name.isBlank()) throw new IllegalArgumentException("name must not be blank");
        if (maxRank <= 0) throw new IllegalArgumentException("maxRank must be positive");
        if (rankCost <= 0) throw new IllegalArgumentException("rankCost must be positive");

        Map<String, Integer> copy = new HashMap<>();
        dependencies.forEach((dependencyCode, requiredRank) -> {
            Objects.requireNonNull(dependencyCode);
            Objects.requireNonNull(requiredRank);
            if (!CODE.matcher(dependencyCode).matches()) {
                throw new IllegalArgumentException("invalid dependency catalog code: " + dependencyCode);
            }
            if (requiredRank <= 0) throw new IllegalArgumentException("dependency rank must be positive");
            if (dependencyCode.equals(code)) throw new IllegalArgumentException("perk cannot depend on itself: " + code);
            copy.put(dependencyCode, requiredRank);
        });
        dependencies = Map.copyOf(copy);
    }

    public enum WeaponFamily {
        SWORD,
        AXE,
        SPEAR,
        DAGGER,
        HAMMER,
        MACE,
        SCYTHE,
        BOW,
        CROSSBOW
    }

    public enum EffectKind {
        DAMAGE_TRAINING,
        RHYTHM_TRAINING,
        CRITICAL_TRAINING,
        MOMENTUM_GENERATION,
        GUARD_OPENING,
        PERFECT_RIPOSTE,
        FURY_GENERATION,
        GUARD_RUPTURE,
        REAVER_FRENZY,
        DISTANCE_CONTROL,
        INTERCEPTION,
        INTERCEPTION_MASTERY,
        FLOW_GENERATION,
        BLIND_SPOT,
        SHADOW_DANCE,
        SHOCK_GENERATION,
        POSTURE_BREAK,
        DEMOLISHER,
        TRAUMA_GENERATION,
        ARMOR_CRACK,
        BONE_BREAKER,
        REAPING_MARK,
        REAPING_CUT,
        BATTLE_HARVEST,
        FOCUS_GENERATION,
        RANGE_MASTERY,
        PREPARED_SHOT
    }
}
