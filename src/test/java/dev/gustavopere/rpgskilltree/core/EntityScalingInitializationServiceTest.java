package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;

public final class EntityScalingInitializationServiceTest {
    public static void main(String[] args) {
        composesNativePlayerVarianceAndRarityIntoPersistedState();
        playerFloorSurvivesNegativeVariance();
        optionalRarityAndHugeLevelsRemainExact();
        invalidOrOverflowingInputsFailClosed();
        System.out.println("EntityScalingInitializationServiceTest: PASS");
    }

    private static void composesNativePlayerVarianceAndRarityIntoPersistedState() {
        TerritoryKey territory = TerritoryKey.of("minecraft:overworld", 4L, -3L);
        MobRaritySelection elite = new MobRaritySelection(MobRarityKey.of("rpgskilltree:elite"), 5L);
        EntityScalingInitializationInput input = new EntityScalingInitializationInput(
            territory,
            23L,
            OptionalLong.of(8L),
            EntityArchetype.HOSTILE,
            2L,
            Optional.of(elite),
            0x1122334455667788L
        );

        EntityScalingState state = EntityScalingInitializationService.resolve(input);

        eq(territory, state.territory());
        eq(EntityArchetype.HOSTILE, state.archetype());
        eq(23L, state.levelResolution().baseFloor());
        eq(30L, state.levelResolution().rolledLevel());
        eq(30L, state.entityLevel());
        eq(2L, state.variance());
        eq(Optional.of(elite), state.rarity());
        eq(0x1122334455667788L, state.deterministicSeed());
    }

    private static void playerFloorSurvivesNegativeVariance() {
        EntityScalingState state = EntityScalingInitializationService.resolve(
            new EntityScalingInitializationInput(
                TerritoryKey.of("minecraft:overworld", 0L, 0L),
                1L,
                OptionalLong.of(50L),
                EntityArchetype.HOSTILE,
                -5L,
                Optional.empty(),
                7L
            )
        );

        eq(50L, state.levelResolution().baseFloor());
        eq(45L, state.levelResolution().rolledLevel());
        eq(50L, state.entityLevel());
        eq(Optional.empty(), state.rarity());
    }

    private static void optionalRarityAndHugeLevelsRemainExact() {
        long huge = 5_000_000_000L;
        MobRaritySelection champion = new MobRaritySelection(
            MobRarityKey.of("rpgskilltree:champion"),
            1_000L
        );
        EntityScalingState state = EntityScalingInitializationService.resolve(
            new EntityScalingInitializationInput(
                TerritoryKey.of("minecraft:the_end", -99L, 77L),
                huge,
                OptionalLong.of(huge - 1L),
                EntityArchetype.BOSS,
                24L,
                Optional.of(champion),
                Long.MIN_VALUE + 7L
            )
        );

        eq(huge, state.levelResolution().baseFloor());
        eq(huge + 1_024L, state.entityLevel());
        eq(Long.MIN_VALUE + 7L, state.deterministicSeed());
    }

    private static void invalidOrOverflowingInputsFailClosed() {
        TerritoryKey territory = TerritoryKey.of("minecraft:overworld", 0L, 0L);
        expect(IllegalArgumentException.class, () -> new EntityScalingInitializationInput(
            territory,
            -1L,
            OptionalLong.empty(),
            EntityArchetype.SPECIAL,
            0L,
            Optional.empty(),
            0L
        ));
        expect(IllegalArgumentException.class, () -> new EntityScalingInitializationInput(
            territory,
            1L,
            OptionalLong.of(-1L),
            EntityArchetype.SPECIAL,
            0L,
            Optional.empty(),
            0L
        ));
        expect(ArithmeticException.class, () -> EntityScalingInitializationService.resolve(
            new EntityScalingInitializationInput(
                territory,
                Long.MAX_VALUE,
                OptionalLong.empty(),
                EntityArchetype.BOSS,
                1L,
                Optional.empty(),
                0L
            )
        ));
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
