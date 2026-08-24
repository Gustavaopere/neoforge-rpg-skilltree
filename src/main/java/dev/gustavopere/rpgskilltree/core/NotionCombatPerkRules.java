package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/** Pure coefficient rules for the audited A0001-A0050 weapon-training perks. */
public final class NotionCombatPerkRules {
    private static final Map<WeaponFamily, String> DAMAGE = codes(
        "A0001", "A0007", "A0013", "A0019", "A0025", "A0031", "A0037", "A0043", "A0049"
    );
    private static final Map<WeaponFamily, String> RHYTHM = codes(
        "A0002", "A0008", "A0014", "A0020", "A0026", "A0032", "A0038", "A0044", "A0050"
    );
    private static final Map<WeaponFamily, String> CRITICAL = criticalCodes();

    private NotionCombatPerkRules() {}

    public static double baseDamageMultiplier(WeaponFamily family, CombatPerkRanks ranks) {
        Objects.requireNonNull(family);
        Objects.requireNonNull(ranks);
        return 1.0D + 0.03D * ranks.rank(DAMAGE.get(family));
    }

    public static double rhythmBonus(WeaponFamily family, CombatPerkRanks ranks) {
        Objects.requireNonNull(family);
        Objects.requireNonNull(ranks);
        return 0.02D * ranks.rank(RHYTHM.get(family));
    }

    public static double criticalChanceBonus(WeaponFamily family, CombatPerkRanks ranks) {
        Objects.requireNonNull(family);
        Objects.requireNonNull(ranks);
        String code = CRITICAL.get(family);
        return code == null ? 0.0D : 0.03D * ranks.rank(code);
    }

    private static Map<WeaponFamily, String> codes(String... codes) {
        WeaponFamily[] families = WeaponFamily.values();
        if (codes.length != families.length) throw new IllegalStateException("weapon family coefficient table mismatch");
        EnumMap<WeaponFamily, String> result = new EnumMap<>(WeaponFamily.class);
        for (int i = 0; i < families.length; i++) result.put(families[i], codes[i]);
        return Map.copyOf(result);
    }

    private static Map<WeaponFamily, String> criticalCodes() {
        EnumMap<WeaponFamily, String> result = new EnumMap<>(WeaponFamily.class);
        result.put(WeaponFamily.SWORD, "A0003");
        result.put(WeaponFamily.AXE, "A0009");
        result.put(WeaponFamily.SPEAR, "A0015");
        result.put(WeaponFamily.DAGGER, "A0021");
        result.put(WeaponFamily.HAMMER, "A0027");
        result.put(WeaponFamily.MACE, "A0033");
        result.put(WeaponFamily.SCYTHE, "A0039");
        result.put(WeaponFamily.BOW, "A0045");
        return Map.copyOf(result);
    }
}
