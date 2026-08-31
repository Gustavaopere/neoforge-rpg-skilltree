package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Canonical identity and award policy for one confirmed hostile weapon-hit milestone.
 *
 * <p>The discovery key intentionally does not include the observing provider. When NeoForge and
 * Epic Fight both observe the same semantic hit, they converge on one persisted anti-farm key.
 */
public final class WeaponMasteryMilestonePolicy {
    private static final Set<String> PHYSICAL_PROJECTILE_CATEGORIES = Set.of("bow", "crossbow");

    private WeaponMasteryMilestonePolicy() {}

    public static boolean acceptsPhysicalProjectileRelease(String weaponCategory, boolean hasAmmo) {
        if (weaponCategory == null || !PHYSICAL_PROJECTILE_CATEGORIES.contains(weaponCategory)) return false;
        return !"bow".equals(weaponCategory) || hasAmmo;
    }

    public static Milestone confirmedPhysicalProjectileHit(
        String originId,
        String providerId,
        String weaponCategory,
        String targetType,
        double damage,
        boolean correlatedRelease
    ) {
        requireNonBlank(weaponCategory, "weaponCategory");
        if (!correlatedRelease) {
            throw new IllegalArgumentException("physical projectile mastery requires a correlated release");
        }
        if (!PHYSICAL_PROJECTILE_CATEGORIES.contains(weaponCategory)) {
            throw new IllegalArgumentException("unsupported physical projectile mastery category: " + weaponCategory);
        }
        return confirmedHit(originId, providerId, weaponCategory, targetType, damage);
    }

    public static Milestone confirmedHit(
        String originId,
        String providerId,
        String weaponCategory,
        String targetType,
        double damage
    ) {
        requireNonBlank(originId, "originId");
        requireNonBlank(providerId, "providerId");
        requireNonBlank(weaponCategory, "weaponCategory");
        requireNonBlank(targetType, "targetType");
        if (!Double.isFinite(damage) || damage <= 0.0D) {
            throw new IllegalArgumentException("confirmed weapon mastery hit requires positive finite damage");
        }

        String discoveryKey = "mastery:epicfight:weapon/" + weaponCategory + "/hostile_type/" + targetType;
        CombatAction action = new CombatAction(
            new ActionOrigin(originId, 0),
            providerId,
            weaponCategory,
            "weapon_hit:" + targetType,
            Set.of("hit", "milestone"),
            damage
        );
        List<MasteryAward> awards = MasteryPolicies.forEpicFight(action);
        if (awards.isEmpty()) {
            throw new IllegalStateException("confirmed weapon mastery milestone produced no awards");
        }
        return new Milestone(discoveryKey, action, awards);
    }

    private static void requireNonBlank(String value, String field) {
        Objects.requireNonNull(value, field);
        if (value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
    }

    public record Milestone(String discoveryKey, CombatAction action, List<MasteryAward> awards) {
        public Milestone {
            requireNonBlank(discoveryKey, "discoveryKey");
            Objects.requireNonNull(action, "action");
            awards = List.copyOf(Objects.requireNonNull(awards, "awards"));
            if (awards.isEmpty()) throw new IllegalArgumentException("awards must not be empty");
        }
    }
}
