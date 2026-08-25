package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/** Stable provider/category and curated-tag mapping for A0001-A0050 weapon families. */
public final class CombatWeaponFamilyPolicy {
    private static final Map<String, WeaponFamily> EPIC_FIGHT = Map.ofEntries(
        Map.entry("sword", WeaponFamily.SWORD),
        Map.entry("uchigatana", WeaponFamily.SWORD),
        Map.entry("tachi", WeaponFamily.SWORD),
        Map.entry("longsword", WeaponFamily.SWORD),
        Map.entry("axe", WeaponFamily.AXE),
        Map.entry("spear", WeaponFamily.SPEAR),
        Map.entry("dagger", WeaponFamily.DAGGER)
    );

    private static final Map<String, WeaponFamily> FALLBACK_TAGS = Map.ofEntries(
        Map.entry("rpgskilltree:swords", WeaponFamily.SWORD),
        Map.entry("rpgskilltree:axes", WeaponFamily.AXE),
        Map.entry("rpgskilltree:spears", WeaponFamily.SPEAR),
        Map.entry("rpgskilltree:daggers", WeaponFamily.DAGGER),
        Map.entry("rpgskilltree:hammers", WeaponFamily.HAMMER),
        Map.entry("rpgskilltree:maces", WeaponFamily.MACE),
        Map.entry("rpgskilltree:scythes", WeaponFamily.SCYTHE),
        Map.entry("rpgskilltree:bows", WeaponFamily.BOW),
        Map.entry("rpgskilltree:crossbows", WeaponFamily.CROSSBOW)
    );

    private CombatWeaponFamilyPolicy() {}

    public static Optional<WeaponFamily> fromEpicFightCategory(String rawCategory) {
        if (rawCategory == null || rawCategory.isBlank()) return Optional.empty();
        String normalized = EpicFightWeaponCategory.normalize(rawCategory);
        int slash = normalized.lastIndexOf('/');
        if (slash >= 0 && slash + 1 < normalized.length()) normalized = normalized.substring(slash + 1);
        return Optional.ofNullable(EPIC_FIGHT.get(normalized));
    }

    public static Optional<WeaponFamily> fromFallbackTag(String rawTag) {
        if (rawTag == null || rawTag.isBlank()) return Optional.empty();
        return Optional.ofNullable(FALLBACK_TAGS.get(rawTag.trim().toLowerCase(Locale.ROOT)));
    }

    /**
     * Resolves one canonical family. Curated RPG tags are authoritative; conflicting curated tags
     * fail closed rather than guessing. Epic Fight is consulted only when no curated tag classified
     * the item, so a generic provider category can never overwrite a more specific RPG tag.
     */
    public static Optional<WeaponFamily> resolve(
        Set<WeaponFamily> explicitFamilies,
        Optional<WeaponFamily> providerFamily
    ) {
        Objects.requireNonNull(explicitFamilies);
        Objects.requireNonNull(providerFamily);
        if (explicitFamilies.size() > 1) return Optional.empty();
        if (explicitFamilies.size() == 1) return Optional.of(explicitFamilies.iterator().next());
        return providerFamily;
    }
}
