package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public final class EntityScalingCurvePolicyTest {
    public static void main(String[] args) {
        familiesRemainExplicitAndComplete();
        cappedLinearCurveSupportsIndependentGrowthAndCaps();
        effectiveStatPolicyUsesConfiguredCurveWithoutHiddenDefaults();
        vanillaStatFamiliesAreExplicitWithoutPathHeuristics();
        providerExtensionsMustBeRegisteredExplicitly();
        curveBackedArchetypePolicyRoutesIndependentFamilies();
        unknownProviderStatsFailClosedUntilRegistered();
        invalidCurveConfigurationFailsClosed();
        System.out.println("EntityScalingCurvePolicyTest: PASS");
    }

    private static void familiesRemainExplicitAndComplete() {
        eq(ScalingCurveFamily.HEALTH, ScalingCurveFamily.valueOf("HEALTH"));
        eq(ScalingCurveFamily.DAMAGE, ScalingCurveFamily.valueOf("DAMAGE"));
        eq(ScalingCurveFamily.DEFENSE, ScalingCurveFamily.valueOf("DEFENSE"));
        eq(ScalingCurveFamily.UTILITY, ScalingCurveFamily.valueOf("UTILITY"));
        eq(ScalingCurveFamily.REWARD, ScalingCurveFamily.valueOf("REWARD"));

        EnumMap<ScalingCurveFamily, CappedLinearScalingCurve> curves = new EnumMap<>(ScalingCurveFamily.class);
        curves.put(ScalingCurveFamily.HEALTH, curve("1", "0.02", "1", "2"));
        curves.put(ScalingCurveFamily.DAMAGE, curve("1", "0.01", "1", "1.5"));
        curves.put(ScalingCurveFamily.DEFENSE, curve("1", "0.005", "1", "1.25"));
        curves.put(ScalingCurveFamily.UTILITY, curve("1", "0.002", "0.8", "1.1"));
        curves.put(ScalingCurveFamily.REWARD, curve("1", "0.015", "1", "1.75"));

        ScalingCurveSet set = ScalingCurveSet.of(curves);
        eq(5, set.asMap().size());
        eq(curves.get(ScalingCurveFamily.HEALTH), set.curve(ScalingCurveFamily.HEALTH));
        eq(curves.get(ScalingCurveFamily.REWARD), set.curve(ScalingCurveFamily.REWARD));
        expect(UnsupportedOperationException.class, () -> set.asMap().put(
            ScalingCurveFamily.HEALTH,
            curve("1", "0", "1", "1")
        ));

        curves.remove(ScalingCurveFamily.REWARD);
        expect(IllegalArgumentException.class, () -> ScalingCurveSet.of(curves));
    }

    private static void cappedLinearCurveSupportsIndependentGrowthAndCaps() {
        CappedLinearScalingCurve health = curve("1", "0.02", "1", "2");
        CappedLinearScalingCurve damage = curve("1", "0.01", "1", "1.5");
        CappedLinearScalingCurve utility = curve("1", "-0.01", "0.75", "1.1");

        decimalEq("1", health.multiplier(0L));
        decimalEq("1.20", health.multiplier(10L));
        decimalEq("2", health.multiplier(5_000_000_000L));
        decimalEq("1.10", damage.multiplier(10L));
        decimalEq("1.5", damage.multiplier(5_000_000_000L));
        decimalEq("0.90", utility.multiplier(10L));
        decimalEq("0.75", utility.multiplier(5_000_000_000L));
        decimalEq("48.00", health.apply(new BigDecimal("40"), 10L));
        expect(IllegalArgumentException.class, () -> health.multiplier(-1L));
    }

    private static void effectiveStatPolicyUsesConfiguredCurveWithoutHiddenDefaults() {
        CanonicalStatKey health = CanonicalStatKey.of("minecraft:max_health");
        EffectiveStatPolicy policy = EffectiveStatCurvePolicy.of(curve("1", "0.025", "1", "1.75"));
        EffectiveStatContext context = new EffectiveStatContext(health, new BigDecimal("20"), 12L);

        decimalEq("26.000", policy.resolve(context));
    }

    private static void vanillaStatFamiliesAreExplicitWithoutPathHeuristics() {
        CanonicalStatScalingFamilyCatalog catalog = CanonicalStatScalingFamilyCatalog.vanillaDefaults();
        eq(ScalingCurveFamily.HEALTH, catalog.family(CanonicalStatKey.of("minecraft:max_health")));
        eq(ScalingCurveFamily.DAMAGE, catalog.family(CanonicalStatKey.of("minecraft:attack_damage")));
        eq(ScalingCurveFamily.DEFENSE, catalog.family(CanonicalStatKey.of("minecraft:armor")));
        eq(ScalingCurveFamily.DEFENSE, catalog.family(CanonicalStatKey.of("minecraft:armor_toughness")));
        eq(ScalingCurveFamily.DEFENSE, catalog.family(CanonicalStatKey.of("minecraft:knockback_resistance")));
        eq(ScalingCurveFamily.UTILITY, catalog.family(CanonicalStatKey.of("minecraft:attack_speed")));
        eq(ScalingCurveFamily.UTILITY, catalog.family(CanonicalStatKey.of("minecraft:movement_speed")));
        eq(ScalingCurveFamily.UTILITY, catalog.family(CanonicalStatKey.of("minecraft:luck")));
        eq(8, catalog.asMap().size());
        expect(UnsupportedOperationException.class, () -> catalog.asMap().clear());
        expect(IllegalStateException.class, () -> catalog.family(CanonicalStatKey.of("custom:max_health")));
    }

    private static void providerExtensionsMustBeRegisteredExplicitly() {
        CanonicalStatKey mana = CanonicalStatKey.of("irons:max_mana");
        CanonicalStatScalingFamilyCatalog catalog = CanonicalStatScalingFamilyCatalog.vanillaDefaults()
            .extend(Map.of(mana, ScalingCurveFamily.UTILITY));
        eq(ScalingCurveFamily.UTILITY, catalog.family(mana));
        expect(IllegalArgumentException.class, () -> catalog.extend(Map.of(
            CanonicalStatKey.of("minecraft:max_health"),
            ScalingCurveFamily.DAMAGE
        )));
    }

    private static void curveBackedArchetypePolicyRoutesIndependentFamilies() {
        ScalingCurveSet curves = distinctFixtureCurves();
        CanonicalStatScalingFamilyCatalog families = CanonicalStatScalingFamilyCatalog.vanillaDefaults();
        EntityArchetypeStatPolicy policy = CurveBackedEntityArchetypeStatPolicy.of(families, curves);

        CanonicalStatKey health = CanonicalStatKey.of("minecraft:max_health");
        CanonicalStatKey damage = CanonicalStatKey.of("minecraft:attack_damage");
        CanonicalStatKey armor = CanonicalStatKey.of("minecraft:armor");
        CanonicalStatKey movement = CanonicalStatKey.of("minecraft:movement_speed");
        CanonicalStatSnapshot provider = CanonicalStatSnapshot.of(Map.of(
            health, new BigDecimal("20"),
            damage, new BigDecimal("5"),
            armor, new BigDecimal("10"),
            movement, new BigDecimal("0.1")
        ));
        EntityLevelResolution level = EntityLevelService.resolve(
            EntityLevelContext.nativeOnly(10L, EntityArchetype.HOSTILE),
            EntityLevelAdjustment.NONE
        );

        EntityStatScalingResult result = EntityStatScalingService.resolve(
            level,
            provider,
            Map.of(EntityArchetype.HOSTILE, policy)
        );
        decimalEq("22.0", result.effectiveStats().value(health));
        decimalEq("6.0", result.effectiveStats().value(damage));
        decimalEq("13.0", result.effectiveStats().value(armor));
        decimalEq("0.140", result.effectiveStats().value(movement));
    }

    private static void unknownProviderStatsFailClosedUntilRegistered() {
        CanonicalStatKey mana = CanonicalStatKey.of("irons:max_mana");
        CanonicalStatSnapshot provider = CanonicalStatSnapshot.of(Map.of(mana, new BigDecimal("100")));
        EntityLevelResolution level = EntityLevelService.resolve(
            EntityLevelContext.nativeOnly(10L, EntityArchetype.HOSTILE),
            EntityLevelAdjustment.NONE
        );
        ScalingCurveSet curves = distinctFixtureCurves();

        EntityArchetypeStatPolicy vanillaOnly = CurveBackedEntityArchetypeStatPolicy.of(
            CanonicalStatScalingFamilyCatalog.vanillaDefaults(),
            curves
        );
        expect(IllegalStateException.class, () -> EntityStatScalingService.resolve(
            level,
            provider,
            Map.of(EntityArchetype.HOSTILE, vanillaOnly)
        ));

        EntityArchetypeStatPolicy extended = CurveBackedEntityArchetypeStatPolicy.of(
            CanonicalStatScalingFamilyCatalog.vanillaDefaults().extend(Map.of(mana, ScalingCurveFamily.UTILITY)),
            curves
        );
        EntityStatScalingResult resolved = EntityStatScalingService.resolve(
            level,
            provider,
            Map.of(EntityArchetype.HOSTILE, extended)
        );
        decimalEq("140.0", resolved.effectiveStats().value(mana));
    }

    private static void invalidCurveConfigurationFailsClosed() {
        expect(IllegalArgumentException.class, () -> curve("1", "0.01", "2", "1"));
        expect(NullPointerException.class, () -> CappedLinearScalingCurve.of(null, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE));
        expect(NullPointerException.class, () -> ScalingCurveSet.of(null));
        expect(NullPointerException.class, () -> EffectiveStatCurvePolicy.of(null));
        expect(IllegalArgumentException.class, () -> ScalingCurveSet.of(Map.of()));
        expect(NullPointerException.class, () -> CanonicalStatScalingFamilyCatalog.of(null));
        expect(NullPointerException.class, () -> CurveBackedEntityArchetypeStatPolicy.of(null, distinctFixtureCurves()));
    }

    private static ScalingCurveSet distinctFixtureCurves() {
        return ScalingCurveSet.of(Map.of(
            ScalingCurveFamily.HEALTH, curve("1", "0.01", "1", "10"),
            ScalingCurveFamily.DAMAGE, curve("1", "0.02", "1", "10"),
            ScalingCurveFamily.DEFENSE, curve("1", "0.03", "1", "10"),
            ScalingCurveFamily.UTILITY, curve("1", "0.04", "1", "10"),
            ScalingCurveFamily.REWARD, curve("1", "0.05", "1", "10")
        ));
    }

    private static CappedLinearScalingCurve curve(String base, String perLevel, String minimum, String maximum) {
        return CappedLinearScalingCurve.of(
            new BigDecimal(base),
            new BigDecimal(perLevel),
            new BigDecimal(minimum),
            new BigDecimal(maximum)
        );
    }

    private static void decimalEq(String expected, BigDecimal actual) {
        BigDecimal expectedDecimal = new BigDecimal(expected);
        if (expectedDecimal.compareTo(actual) != 0) {
            throw new AssertionError(expectedDecimal + " != " + actual);
        }
    }

    private static void expect(Class<? extends Throwable> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable thrown) {
            if (type.isInstance(thrown)) return;
            throw new AssertionError("expected " + type.getSimpleName() + " but got " + thrown, thrown);
        }
        throw new AssertionError("expected " + type.getSimpleName());
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
