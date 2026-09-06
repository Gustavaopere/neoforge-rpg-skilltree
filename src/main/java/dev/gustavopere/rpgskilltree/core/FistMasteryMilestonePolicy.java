package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/** Canonical finite-discovery mastery policy for Epic Fight fist/knuckle hits. */
public final class FistMasteryMilestonePolicy {
    public static final String CANONICAL_CATEGORY = "fist";
    public static final String CANONICAL_LANE = "combat:fist";
    private static final Set<String> PROVIDER_CATEGORIES = Set.of("fist", "knuckle");

    private FistMasteryMilestonePolicy() {}

    public static boolean supportsProviderCategory(String weaponCategory) {
        return weaponCategory != null && PROVIDER_CATEGORIES.contains(normalizeCategory(weaponCategory));
    }

    public static WeaponMasteryMilestonePolicy.Milestone confirmedHit(
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
        String normalized = normalizeCategory(weaponCategory);
        if (!PROVIDER_CATEGORIES.contains(normalized)) {
            throw new IllegalArgumentException("unsupported FIST provider category: " + weaponCategory);
        }
        if (!Double.isFinite(damage) || damage <= 0.0D) {
            throw new IllegalArgumentException("confirmed FIST mastery hit requires positive finite damage");
        }

        String discoveryKey = "mastery:combat:fist/hostile_type/" + targetType;
        String sourceId = "weapon_hit:" + targetType;
        CombatAction action = new CombatAction(
            new ActionOrigin(originId, 0),
            providerId,
            CANONICAL_CATEGORY,
            sourceId,
            Set.of("hit", "milestone"),
            damage
        );
        List<MasteryAward> awards = List.of(
            new MasteryAward("epicfight:weapon", 5, sourceId),
            new MasteryAward(CANONICAL_LANE, 10, sourceId)
        );
        return new WeaponMasteryMilestonePolicy.Milestone(discoveryKey, action, awards);
    }

    private static String normalizeCategory(String value) {
        return value.trim().toLowerCase(Locale.ROOT);
    }

    private static void requireNonBlank(String value, String field) {
        Objects.requireNonNull(value, field);
        if (value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
    }
}
