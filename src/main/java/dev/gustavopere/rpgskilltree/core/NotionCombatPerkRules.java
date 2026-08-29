package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/** Pure coefficients from the fresh currently closed A0001-A0040 Notion snapshot. */
public final class NotionCombatPerkRules {
    private static final Map<WeaponFamily, String> DAMAGE = Map.of(
        WeaponFamily.SWORD,"A0001", WeaponFamily.AXE,"A0007", WeaponFamily.SPEAR,"A0013", WeaponFamily.DAGGER,"A0019",
        WeaponFamily.HAMMER,"A0025", WeaponFamily.MACE,"A0031", WeaponFamily.SCYTHE,"A0037");
    private static final Map<WeaponFamily, String> RHYTHM = Map.of(
        WeaponFamily.SWORD,"A0002", WeaponFamily.AXE,"A0008", WeaponFamily.SPEAR,"A0014", WeaponFamily.DAGGER,"A0020",
        WeaponFamily.HAMMER,"A0026", WeaponFamily.MACE,"A0032", WeaponFamily.SCYTHE,"A0038");
    private static final Map<WeaponFamily, String> CRITICAL = Map.of(
        WeaponFamily.SWORD,"A0003", WeaponFamily.AXE,"A0009", WeaponFamily.SPEAR,"A0015", WeaponFamily.DAGGER,"A0021",
        WeaponFamily.HAMMER,"A0027", WeaponFamily.MACE,"A0033", WeaponFamily.SCYTHE,"A0039");

    public static final int MOMENTUM_CAP=5; public static final long MOMENTUM_INACTIVITY_MILLIS=5_000L; public static final long MOMENTUM_DECAY_INTERVAL_MILLIS=1_000L;
    public static final int A0005_MIN_MOMENTUM=3, A0005_MOMENTUM_COST=2; public static final double A0005_IMPACT_MULTIPLIER=1.08D, A0005_PENETRATION_FRACTION=0.12D; public static final long A0005_TARGET_COOLDOWN_MILLIS=6_000L;
    public static final double FURY_CAP=100.0D, A0011_MIN_FURY=40.0D, A0011_FURY_COST=20.0D;
    public static final int DISTANCE_CONTROL_CAP=3; public static final double SPEAR_IDEAL_MIN_FRACTION=0.70D, SPEAR_IDEAL_MAX_FRACTION=1.00D; public static final long A0017_WINDOW_MILLIS=2_000L, A0018_TARGET_LOCKOUT_MILLIS=8_000L;

    public static final int FLOW_CAP=4; public static final long A0022_REPOSITION_WINDOW_MILLIS=2_500L, A0022_IDLE_BEFORE_DECAY_MILLIS=3_000L;
    public static final double A0022_FALLBACK_MIN_DISPLACEMENT=1.5D, A0022_FALLBACK_MIN_ANGLE_DEGREES=60.0D;
    public static final long A0023_TARGET_COOLDOWN_MILLIS=4_000L; public static final int A0023_FLOW_COST=2;
    public static final long A0024_ACTIVATION_REPOSITION_WINDOW_MILLIS=2_000L;

    public static final int ABALO_CAP=3; public static final long ABALO_DURATION_MILLIS=6_000L;
    public static final int TRAUMA_CAP=3;
    public static final double REAP_MATURE_HEALTH_FRACTION=0.50D;

    private NotionCombatPerkRules() {}

    public static double baseDamageMultiplier(WeaponFamily family, CombatPerkRanks ranks) { Objects.requireNonNull(family); Objects.requireNonNull(ranks); String code=DAMAGE.get(family); return code==null?1.0D:1.0D+0.03D*ranks.rank(code); }
    public static double rhythmBonus(WeaponFamily family, CombatPerkRanks ranks) { Objects.requireNonNull(family); Objects.requireNonNull(ranks); String code=RHYTHM.get(family); return code==null?0.0D:0.02D*ranks.rank(code); }
    public static double criticalChanceBonus(WeaponFamily family, CombatPerkRanks ranks) { Objects.requireNonNull(family); Objects.requireNonNull(ranks); String code=CRITICAL.get(family); return code==null?0.0D:0.03D*ranks.rank(code); }

    public static double axeFuryGain(int rank, boolean switchedTarget){ if(rank<1||rank>2)return 0.0D; double ranked=8.0D*(rank==1?1.10D:1.20D); return ranked*(switchedTarget?1.50D:1.0D); }
    public static double ruptureImpactMultiplier(int rank){return rank>=2?1.35D:rank==1?1.20D:1.0D;} public static double rupturePenetrationFraction(int rank){return rank>=2?0.10D:rank==1?0.06D:0.0D;}
    public static double interceptionImpactMultiplier(int rank){return rank>=2?1.35D:rank==1?1.20D:1.0D;} public static double interceptionDisplacementMultiplier(int rank){return rank>=2?0.70D:rank==1?0.80D:1.0D;}
    public static long distanceControlWindowMillis(int rank){return rank>=2?7_000L:5_000L;} public static long riposteCooldownMillis(int mastery){return mastery>=100?8_000L:mastery>=90?9_000L:10_000L;} public static long interceptionMasteryWindowMillis(int mastery){return mastery>=100?4_000L:mastery>=90?3_500L:3_000L;} public static long frenzyDropDurationMillis(int mastery){return mastery>=100?4_000L:mastery>=90?5_000L:6_000L;}
    public static boolean frenzyBaselineAvailable(boolean impact,boolean thermal,boolean exhaustion){return impact&&thermal&&exhaustion;} public static boolean frenzyThirstSurchargeAvailable(boolean thirstReceipt){return thirstReceipt;}

    public static long flowDurationMillis(int rank){return rank>=2?7_000L:5_000L;}
    public static double blindSpotCriticalDamageMultiplier(int rank){return rank>=2?1.25D:rank==1?1.15D:1.0D;}
    public static double blindSpotPenetrationFraction(int rank){return rank>=2?0.10D:rank==1?0.06D:0.0D;}
    public static long shadowDanceDurationMillis(int mastery){return mastery>=100?5_000L:mastery>=90?4_500L:4_000L;}
    public static double abaloPressurePerCharge(int rank){return rank>=2?0.12D:rank==1?0.08D:0.0D;}
    public static double postureBreakPressureMultiplier(int rank){return rank>=2?1.45D:rank==1?1.30D:1.0D;}
    public static double postureBreakImpactMultiplier(int rank){return rank>=2?1.15D:rank==1?1.10D:1.0D;}
    public static long demolitionCooldownMillis(int mastery){return mastery>=100?10_000L:mastery>=90?11_000L:12_000L;}
    public static long traumaDurationMillis(int rank){return rank>=2?8_000L:6_000L;}
    public static double sunderArmorFraction(int rank){return rank>=2?0.12D:rank==1?0.08D:0.0D;}
    public static long sunderDurationMillis(int rank){return rank>=2?6_000L:4_000L;}
    public static long bonebreakerCooldownMillis(int mastery){return mastery>=100?10_000L:mastery>=90?11_000L:12_000L;}
    public static long reapingMarkDurationMillis(int rank){return rank>=2?10_000L:8_000L;}

    public static Map<WeaponFamily,String> damageCodes(){return new EnumMap<>(DAMAGE);}
}
