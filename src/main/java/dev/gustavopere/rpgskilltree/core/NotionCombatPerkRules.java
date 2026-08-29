package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/** Pure coefficients from the fresh A0001-A0020 Notion snapshot. */
public final class NotionCombatPerkRules {
    private static final Map<WeaponFamily, String> DAMAGE = Map.of(
        WeaponFamily.SWORD, "A0001", WeaponFamily.AXE, "A0007", WeaponFamily.SPEAR, "A0013", WeaponFamily.DAGGER, "A0019");
    private static final Map<WeaponFamily, String> RHYTHM = Map.of(
        WeaponFamily.SWORD, "A0002", WeaponFamily.AXE, "A0008", WeaponFamily.SPEAR, "A0014", WeaponFamily.DAGGER, "A0020");
    private static final Map<WeaponFamily, String> CRITICAL = Map.of(
        WeaponFamily.SWORD, "A0003", WeaponFamily.AXE, "A0009", WeaponFamily.SPEAR, "A0015");

    public static final int MOMENTUM_CAP = 5;
    public static final long MOMENTUM_INACTIVITY_MILLIS = 5_000L;
    public static final long MOMENTUM_DECAY_INTERVAL_MILLIS = 1_000L;
    public static final int A0005_MIN_MOMENTUM = 3;
    public static final int A0005_MOMENTUM_COST = 2;
    public static final double A0005_IMPACT_MULTIPLIER = 1.08D;
    public static final double A0005_PENETRATION_FRACTION = 0.12D;
    public static final long A0005_TARGET_COOLDOWN_MILLIS = 6_000L;
    public static final double FURY_CAP = 100.0D;
    public static final double A0011_MIN_FURY = 40.0D;
    public static final double A0011_FURY_COST = 20.0D;
    public static final int DISTANCE_CONTROL_CAP = 3;
    public static final double SPEAR_IDEAL_MIN_FRACTION = 0.70D;
    public static final double SPEAR_IDEAL_MAX_FRACTION = 1.00D;
    public static final long A0017_WINDOW_MILLIS = 2_000L;
    public static final long A0018_TARGET_LOCKOUT_MILLIS = 8_000L;

    private NotionCombatPerkRules() {}

    public static double baseDamageMultiplier(WeaponFamily family, CombatPerkRanks ranks) {
        Objects.requireNonNull(family); Objects.requireNonNull(ranks);
        String code = DAMAGE.get(family);
        return code == null ? 1.0D : 1.0D + 0.03D * ranks.rank(code);
    }

    public static double rhythmBonus(WeaponFamily family, CombatPerkRanks ranks) {
        Objects.requireNonNull(family); Objects.requireNonNull(ranks);
        String code = RHYTHM.get(family);
        return code == null ? 0.0D : 0.02D * ranks.rank(code);
    }

    public static double criticalChanceBonus(WeaponFamily family, CombatPerkRanks ranks) {
        Objects.requireNonNull(family); Objects.requireNonNull(ranks);
        String code = CRITICAL.get(family);
        return code == null ? 0.0D : 0.03D * ranks.rank(code);
    }

    public static double axeFuryGain(int rank, boolean switchedTarget) {
        if (rank < 1 || rank > 2) return 0.0D;
        double ranked = 8.0D * (rank == 1 ? 1.10D : 1.20D);
        return ranked * (switchedTarget ? 1.50D : 1.0D);
    }

    public static double ruptureImpactMultiplier(int rank) { return rank >= 2 ? 1.35D : rank == 1 ? 1.20D : 1.0D; }
    public static double rupturePenetrationFraction(int rank) { return rank >= 2 ? 0.10D : rank == 1 ? 0.06D : 0.0D; }
    public static double interceptionImpactMultiplier(int rank) { return rank >= 2 ? 1.35D : rank == 1 ? 1.20D : 1.0D; }
    public static double interceptionDisplacementMultiplier(int rank) { return rank >= 2 ? 0.70D : rank == 1 ? 0.80D : 1.0D; }
    public static long distanceControlWindowMillis(int rank) { return rank >= 2 ? 7_000L : 5_000L; }
    public static long riposteCooldownMillis(int mastery) { return mastery >= 100 ? 8_000L : mastery >= 90 ? 9_000L : 10_000L; }
    public static long interceptionMasteryWindowMillis(int mastery) { return mastery >= 100 ? 4_000L : mastery >= 90 ? 3_500L : 3_000L; }
    public static long frenzyDropDurationMillis(int mastery) { return mastery >= 100 ? 4_000L : mastery >= 90 ? 5_000L : 6_000L; }

    /** A0012 is all-or-nothing for the baseline Frenzy benefit: thermal + hunger/exhaustion must both be causal. */
    public static boolean frenzyBaselineAvailable(boolean impact, boolean thermal, boolean exhaustion) {
        return impact && thermal && exhaustion;
    }

    /** Thirst is an optional extra tradeoff and can only participate through its own causal receipt. */
    public static boolean frenzyThirstSurchargeAvailable(boolean thirstReceipt) {
        return thirstReceipt;
    }

    public static Map<WeaponFamily, String> damageCodes() { return new EnumMap<>(DAMAGE); }
}
