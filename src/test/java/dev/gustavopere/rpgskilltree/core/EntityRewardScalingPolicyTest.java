package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class EntityRewardScalingPolicyTest {
    public static void main(String[] args) {
        rewardRiskMultiplierCombinesIndependentBoundedFactors();
        absentRarityUsesExplicitConfiguredFactor();
        unknownRarityAndIncompleteArchetypesFailClosed();
        rewardResultScalesNonNegativeBaseValues();
        System.out.println("EntityRewardScalingPolicyTest: PASS");
    }

    private static void rewardRiskMultiplierCombinesIndependentBoundedFactors() {
        MobRarityKey elite = MobRarityKey.of("rpgskilltree:elite");
        CappedEntityRewardScalingPolicy policy = CappedEntityRewardScalingPolicy.of(
            fixtureCurves(),
            completeArchetypeMultipliers(Map.of(EntityArchetype.BOSS, new BigDecimal("1.5"))),
            Map.of(elite, new BigDecimal("1.2")),
            new BigDecimal("0.9"),
            new BigDecimal("3.0")
        );

        EntityLevelResolution hostileLevel = EntityLevelService.resolve(
            EntityLevelContext.nativeOnly(10L, EntityArchetype.HOSTILE),
            EntityLevelAdjustment.NONE
        );
        EntityRewardScalingResult hostileElite = policy.resolve(new EntityRewardScalingContext(
            hostileLevel,
            Optional.of(new MobRaritySelection(elite, 0L))
        ));
        decimalEq("1.50", hostileElite.levelMultiplier());
        decimalEq("1", hostileElite.archetypeMultiplier());
        decimalEq("1.2", hostileElite.rarityMultiplier());
        decimalEq("1.800", hostileElite.uncappedMultiplier());
        decimalEq("1.800", hostileElite.finalMultiplier());

        EntityLevelResolution bossLevel = EntityLevelService.resolve(
            EntityLevelContext.nativeOnly(100L, EntityArchetype.BOSS),
            EntityLevelAdjustment.NONE
        );
        EntityRewardScalingResult bossElite = policy.resolve(new EntityRewardScalingContext(
            bossLevel,
            Optional.of(new MobRaritySelection(elite, 0L))
        ));
        decimalEq("2.0", bossElite.levelMultiplier());
        decimalEq("1.5", bossElite.archetypeMultiplier());
        decimalEq("1.2", bossElite.rarityMultiplier());
        decimalEq("3.600", bossElite.uncappedMultiplier());
        decimalEq("3.0", bossElite.finalMultiplier());
    }

    private static void absentRarityUsesExplicitConfiguredFactor() {
        CappedEntityRewardScalingPolicy policy = CappedEntityRewardScalingPolicy.of(
            fixtureCurves(),
            completeArchetypeMultipliers(Map.of()),
            Map.of(),
            new BigDecimal("0.8"),
            new BigDecimal("3")
        );
        EntityLevelResolution level = EntityLevelService.resolve(
            EntityLevelContext.nativeOnly(10L, EntityArchetype.PASSIVE),
            EntityLevelAdjustment.NONE
        );

        EntityRewardScalingResult result = policy.resolve(new EntityRewardScalingContext(level, Optional.empty()));
        decimalEq("1.50", result.levelMultiplier());
        decimalEq("0.8", result.rarityMultiplier());
        decimalEq("1.200", result.finalMultiplier());
    }

    private static void unknownRarityAndIncompleteArchetypesFailClosed() {
        MobRarityKey known = MobRarityKey.of("rpgskilltree:known");
        MobRarityKey unknown = MobRarityKey.of("rpgskilltree:unknown");
        CappedEntityRewardScalingPolicy policy = CappedEntityRewardScalingPolicy.of(
            fixtureCurves(),
            completeArchetypeMultipliers(Map.of()),
            Map.of(known, BigDecimal.ONE),
            BigDecimal.ONE,
            new BigDecimal("3")
        );
        EntityLevelResolution level = EntityLevelService.resolve(
            EntityLevelContext.nativeOnly(5L, EntityArchetype.HOSTILE),
            EntityLevelAdjustment.NONE
        );

        expect(IllegalStateException.class, () -> policy.resolve(new EntityRewardScalingContext(
            level,
            Optional.of(new MobRaritySelection(unknown, 0L))
        )));

        expect(IllegalArgumentException.class, () -> CappedEntityRewardScalingPolicy.of(
            fixtureCurves(),
            Map.of(EntityArchetype.HOSTILE, BigDecimal.ONE),
            Map.of(),
            BigDecimal.ONE,
            new BigDecimal("3")
        ));
        expect(NullPointerException.class, () -> CappedEntityRewardScalingPolicy.of(
            null,
            completeArchetypeMultipliers(Map.of()),
            Map.of(),
            BigDecimal.ONE,
            new BigDecimal("3")
        ));
        expect(IllegalArgumentException.class, () -> CappedEntityRewardScalingPolicy.of(
            fixtureCurves(),
            completeArchetypeMultipliers(Map.of()),
            Map.of(),
            new BigDecimal("-0.1"),
            new BigDecimal("3")
        ));
    }

    private static void rewardResultScalesNonNegativeBaseValues() {
        EntityRewardScalingResult result = new EntityRewardScalingResult(
            new BigDecimal("1.5"),
            BigDecimal.ONE,
            new BigDecimal("1.2"),
            new BigDecimal("1.8"),
            new BigDecimal("1.8")
        );
        decimalEq("180.0", result.scale(new BigDecimal("100")));
        decimalEq("0.0", result.scale(BigDecimal.ZERO));
        expect(IllegalArgumentException.class, () -> result.scale(new BigDecimal("-1")));
    }

    private static Map<EntityArchetype, BigDecimal> completeArchetypeMultipliers(
        Map<EntityArchetype, BigDecimal> overrides
    ) {
        EnumMap<EntityArchetype, BigDecimal> result = new EnumMap<>(EntityArchetype.class);
        for (EntityArchetype archetype : EntityArchetype.values()) {
            result.put(archetype, BigDecimal.ONE);
        }
        result.putAll(overrides);
        return Map.copyOf(result);
    }

    private static ScalingCurveSet fixtureCurves() {
        return ScalingCurveSet.of(Map.of(
            ScalingCurveFamily.HEALTH, curve("1", "0", "1", "1"),
            ScalingCurveFamily.DAMAGE, curve("1", "0", "1", "1"),
            ScalingCurveFamily.DEFENSE, curve("1", "0", "1", "1"),
            ScalingCurveFamily.UTILITY, curve("1", "0", "1", "1"),
            ScalingCurveFamily.REWARD, curve("1", "0.05", "1", "2")
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
