package dev.gustavopere.rpgskilltree.core;

import java.util.Locale;

/** Canonical, provider-neutral contract for the combat_fist specialization and weapon classifier. */
public final class CombatFistPolicy {
    public static final String SPECIALIZATION_ID = "combat_fist";
    public static final String MASTERY_ID = "combat:fist";
    public static final int MINIMUM_LEVEL = 8;
    public static final int MINIMUM_MASTERY = 60;

    private CombatFistPolicy() {}

    public enum ProviderCategory { FIST, GENERIC_WEAPON, OTHER_SPECIFIC, UNKNOWN }

    public static boolean gateSatisfied(
        boolean registryDefinitionPresent,
        boolean specializationUnlocked,
        int characterLevel,
        int masteryExperience
    ) {
        return registryDefinitionPresent
            && specializationUnlocked
            && characterLevel >= MINIMUM_LEVEL
            && masteryExperience >= MINIMUM_MASTERY;
    }

    /** Empty hands are always rejected; an explicit curated tag outranks only generic/unknown categories. */
    public static boolean isFistWeapon(
        boolean stackEmpty,
        boolean curatedFistTag,
        ProviderCategory providerCategory
    ) {
        if (stackEmpty) return false;
        if (providerCategory == ProviderCategory.OTHER_SPECIFIC) return false;
        if (curatedFistTag) return true;
        return providerCategory == ProviderCategory.FIST;
    }

    public static ProviderCategory providerCategory(String rawCategory) {
        if (rawCategory == null || rawCategory.isBlank()) return ProviderCategory.UNKNOWN;
        String normalized = rawCategory.toLowerCase(Locale.ROOT);
        if (normalized.equals("fist") || normalized.equals("knuckle") || normalized.endsWith(":fist")
            || normalized.endsWith(":knuckle")) {
            return ProviderCategory.FIST;
        }
        if (normalized.equals("weapon") || normalized.equals("melee") || normalized.equals("generic_weapon")) {
            return ProviderCategory.GENERIC_WEAPON;
        }
        return ProviderCategory.OTHER_SPECIFIC;
    }
}
