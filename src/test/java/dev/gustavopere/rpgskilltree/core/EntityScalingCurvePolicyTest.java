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

    private static void invalidCurveConfigurationFailsClosed() {
        expect(IllegalArgumentException.class, () -> curve("1", "0.01", "2", "1"));
        expect(NullPointerException.class, () -> CappedLinearScalingCurve.of(null, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE));
        expect(NullPointerException.class, () -> ScalingCurveSet.of(null));
        expect(NullPointerException.class, () -> EffectiveStatCurvePolicy.of(null));
        expect(IllegalArgumentException.class, () -> ScalingCurveSet.of(Map.of()));
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
