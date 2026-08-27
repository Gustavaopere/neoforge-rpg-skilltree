package dev.gustavopere.rpgskilltree.core;

import java.util.EnumSet;
import java.util.Objects;
import java.util.OptionalLong;

public final class WorldEntityLevelFoundationTest {
    public static void main(String[] args) {
        territoryKeysAreStableAndDimensionAware();
        nativeAreaLevelPolicyIsExplicitAndDeterministicForAKey();
        entityFloorUsesNativeAndRelevantPlayerLevel();
        varianceAndRarityApplyAfterTheBaseFloor();
        hugeLevelsUseCheckedArithmetic();
        approvedEntityArchetypesAreRepresented();
        System.out.println("WorldEntityLevelFoundationTest: PASS");
    }

    private static void territoryKeysAreStableAndDimensionAware() {
        TerritoryKey key = TerritoryKey.of("minecraft:overworld", 12L, -5L);
        eq("minecraft:overworld", key.dimensionId());
        eq(12L, key.cellX());
        eq(-5L, key.cellZ());
        eq(key, TerritoryKey.of("minecraft:overworld", 12L, -5L));
        neq(key, TerritoryKey.of("minecraft:the_nether", 12L, -5L));

        expect(IllegalArgumentException.class, () -> TerritoryKey.of("overworld", 0L, 0L));
        expect(IllegalArgumentException.class, () -> TerritoryKey.of("Minecraft:overworld", 0L, 0L));
        expect(IllegalArgumentException.class, () -> TerritoryKey.of("minecraft:Overworld", 0L, 0L));
        expect(IllegalArgumentException.class, () -> TerritoryKey.of("minecraft:bad space", 0L, 0L));
    }

    private static void nativeAreaLevelPolicyIsExplicitAndDeterministicForAKey() {
        TerritoryKey danger = TerritoryKey.of("minecraft:overworld", 4L, 9L);
        NativeAreaLevelPolicy policy = key -> key.equals(danger) ? 23L : 1L;

        eq(23L, NativeAreaLevelResolver.resolve(danger, policy));
        eq(23L, NativeAreaLevelResolver.resolve(danger, policy));
        eq(1L, NativeAreaLevelResolver.resolve(TerritoryKey.of("minecraft:overworld", 0L, 0L), policy));
        expect(IllegalArgumentException.class, () -> NativeAreaLevelResolver.resolve(danger, key -> -1L));
    }

    private static void entityFloorUsesNativeAndRelevantPlayerLevel() {
        EntityLevelResolution dangerousArea = EntityLevelService.resolve(
            EntityLevelContext.withRelevantPlayer(23L, 8L, EntityArchetype.HOSTILE),
            EntityLevelAdjustment.NONE
        );
        eq(23L, dangerousArea.baseFloor());
        eq(23L, dangerousArea.finalLevel());

        EntityLevelResolution returningVeteran = EntityLevelService.resolve(
            EntityLevelContext.withRelevantPlayer(1L, 50L, EntityArchetype.HOSTILE),
            EntityLevelAdjustment.NONE
        );
        eq(50L, returningVeteran.baseFloor());
        eq(50L, returningVeteran.finalLevel());

        EntityLevelResolution nativeOnly = EntityLevelService.resolve(
            EntityLevelContext.nativeOnly(12L, EntityArchetype.PASSIVE),
            EntityLevelAdjustment.NONE
        );
        eq(OptionalLong.empty(), nativeOnly.relevantPlayerLevel());
        eq(12L, nativeOnly.baseFloor());
        eq(12L, nativeOnly.finalLevel());
    }

    private static void varianceAndRarityApplyAfterTheBaseFloor() {
        EntityLevelResolution nativeAbovePlayer = EntityLevelService.resolve(
            EntityLevelContext.withRelevantPlayer(23L, 8L, EntityArchetype.HOSTILE),
            new EntityLevelAdjustment(-5L, 0L)
        );
        eq(23L, nativeAbovePlayer.baseFloor());
        eq(18L, nativeAbovePlayer.rolledLevel());
        eq(18L, nativeAbovePlayer.finalLevel());

        EntityLevelResolution playerFloorWins = EntityLevelService.resolve(
            EntityLevelContext.withRelevantPlayer(1L, 50L, EntityArchetype.HOSTILE),
            new EntityLevelAdjustment(-5L, 0L)
        );
        eq(45L, playerFloorWins.rolledLevel());
        eq(50L, playerFloorWins.finalLevel());

        EntityLevelResolution rarityRaisesLevel = EntityLevelService.resolve(
            EntityLevelContext.withRelevantPlayer(1L, 50L, EntityArchetype.HOSTILE),
            new EntityLevelAdjustment(2L, 5L)
        );
        eq(57L, rarityRaisesLevel.rolledLevel());
        eq(57L, rarityRaisesLevel.finalLevel());

        EntityLevelResolution noPlayerNeverGoesNegative = EntityLevelService.resolve(
            EntityLevelContext.nativeOnly(2L, EntityArchetype.PASSIVE),
            new EntityLevelAdjustment(-5L, 0L)
        );
        eq(-3L, noPlayerNeverGoesNegative.rolledLevel());
        eq(0L, noPlayerNeverGoesNegative.finalLevel());
    }

    private static void hugeLevelsUseCheckedArithmetic() {
        EntityLevelResolution huge = EntityLevelService.resolve(
            EntityLevelContext.withRelevantPlayer(5_000_000_000L, 4_000_000_000L, EntityArchetype.BOSS),
            new EntityLevelAdjustment(250L, 750L)
        );
        eq(5_000_001_000L, huge.finalLevel());

        expect(ArithmeticException.class, () -> EntityLevelService.resolve(
            EntityLevelContext.nativeOnly(Long.MAX_VALUE, EntityArchetype.BOSS),
            new EntityLevelAdjustment(1L, 0L)
        ));
    }

    private static void approvedEntityArchetypesAreRepresented() {
        eq(EnumSet.of(
            EntityArchetype.HOSTILE,
            EntityArchetype.NEUTRAL,
            EntityArchetype.PASSIVE,
            EntityArchetype.CIVILIAN,
            EntityArchetype.VILLAGER,
            EntityArchetype.COLONIST,
            EntityArchetype.GUARD,
            EntityArchetype.TAMED,
            EntityArchetype.COMPANION,
            EntityArchetype.SUMMON,
            EntityArchetype.BOSS,
            EntityArchetype.SPECIAL
        ), EnumSet.allOf(EntityArchetype.class));
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

    private static void neq(Object left, Object right) {
        if (Objects.equals(left, right)) throw new AssertionError("expected distinct values: " + left);
    }
}
