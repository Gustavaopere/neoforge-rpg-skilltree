package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;

public final class EntityRewardRiskPolicyTest {
    private static final MobRarityKey ELITE = MobRarityKey.of("rpgskilltree:elite");
    private static final MobRarityKey UNKNOWN = MobRarityKey.of("test:unknown");

    public static void main(String[] args) {
        rewardCurveTracksPersistedEntityRisk();
        rarityAndBossMultipliersComposeThenCap();
        selectedRarityRequiresExplicitRewardMapping();
        hugeLevelsAndAggressiveMultipliersRemainBounded();
        invalidRewardConfigurationFailsClosed();
        System.out.println("EntityRewardRiskPolicyTest: PASS");
    }

    private static void rewardCurveTracksPersistedEntityRisk() {
        EntityRewardRiskPolicy policy = policy("2.50");

        decimalEq("1.50", policy.multiplier(state(EntityArchetype.HOSTILE, 10L, Optional.empty())));
        decimalEq("2.00", policy.multiplier(state(
            EntityArchetype.HOSTILE,
            12L,
            Optional.of(new MobRaritySelection(ELITE, 2L))
        )));
    }

    private static void rarityAndBossMultipliersComposeThenCap() {
        EntityRewardRiskPolicy policy = policy("2.50");
        EntityScalingState boss = state(
            EntityArchetype.BOSS,
            12L,
            Optional.of(new MobRaritySelection(ELITE, 2L))
        );

        // Reward curve at level 12 = 1.60; elite = x1.25; boss = x1.50 => 3.00, capped to 2.50.
        decimalEq("2.50", policy.multiplier(boss));
        decimalEq("2.50", policy.multiplier(boss));
    }

    private static void selectedRarityRequiresExplicitRewardMapping() {
        EntityRewardRiskPolicy policy = policy("3.00");
        EntityScalingState unknown = state(
            EntityArchetype.HOSTILE,
            11L,
            Optional.of(new MobRaritySelection(UNKNOWN, 1L))
        );

        expect(IllegalStateException.class, () -> policy.multiplier(unknown));
    }

    private static void hugeLevelsAndAggressiveMultipliersRemainBounded() {
        ScalingCurveSet curves = curves();
        EntityRewardRiskPolicy policy = CappedEntityRewardRiskPolicy.of(
            curves,
            Map.of(ELITE, new BigDecimal("1000")),
            new BigDecimal("1000"),
            new BigDecimal("4.00")
        );

        EntityScalingState boss = state(
            EntityArchetype.BOSS,
            5_000_000_002L,
            Optional.of(new MobRaritySelection(ELITE, 2L))
        );
        decimalEq("4.00", policy.multiplier(boss));
    }

    private static void invalidRewardConfigurationFailsClosed() {
        ScalingCurveSet curves = curves();
        expect(NullPointerException.class, () -> CappedEntityRewardRiskPolicy.of(
            null,
            Map.of(),
            BigDecimal.ONE,
            new BigDecimal("2")
        ));
        expect(NullPointerException.class, () -> CappedEntityRewardRiskPolicy.of(
            curves,
            null,
            BigDecimal.ONE,
            new BigDecimal("2")
        ));
        expect(IllegalArgumentException.class, () -> CappedEntityRewardRiskPolicy.of(
            curves,
            Map.of(ELITE, new BigDecimal("-1")),
            BigDecimal.ONE,
            new BigDecimal("2")
        ));
        expect(IllegalArgumentException.class, () -> CappedEntityRewardRiskPolicy.of(
            curves,
            Map.of(),
            new BigDecimal("-1"),
            new BigDecimal("2")
        ));
        expect(IllegalArgumentException.class, () -> CappedEntityRewardRiskPolicy.of(
            curves,
            Map.of(),
            BigDecimal.ONE,
            new BigDecimal("-1")
        ));
    }

    private static EntityRewardRiskPolicy policy(String maximumMultiplier) {
        return CappedEntityRewardRiskPolicy.of(
            curves(),
            Map.of(ELITE, new BigDecimal("1.25")),
            new BigDecimal("1.50"),
            new BigDecimal(maximumMultiplier)
        );
    }

    private static ScalingCurveSet curves() {
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

    private static EntityScalingState state(
        EntityArchetype archetype,
        long finalLevel,
        Optional<MobRaritySelection> rarity
    ) {
        long rarityBonus = rarity.map(MobRaritySelection::levelBonus).orElse(0L);
        long nativeLevel = Math.subtractExact(finalLevel, rarityBonus);
        return new EntityScalingState(
            TerritoryKey.of("minecraft:overworld", 0L, 0L),
            new EntityLevelResolution(
                archetype,
                nativeLevel,
                OptionalLong.empty(),
                nativeLevel,
                finalLevel,
                finalLevel
            ),
            0L,
            rarity,
            1234L,
            Optional.empty(),
            MobAffixSelection.empty(),
            EntityBehaviorSelection.empty()
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
