package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public final class TerritoryAreaLevelPolicyTest {
    public static void main(String[] args) {
        transitionPolicyClampsAndRateLimitsStatefulSources();
        blendDisabledUsesOnlyPrimaryTerritory();
        boundaryBlendIsDeterministicBoundedAndSmooth();
        cornerBlendSamplesAtMostFourTerritories();
        negativeCoordinatesUseTheSameBoundarySemantics();
        invalidInputsFailClosed();
        System.out.println("TerritoryAreaLevelPolicyTest: PASS");
    }

    private static void transitionPolicyClampsAndRateLimitsStatefulSources() {
        NativeAreaLevelTransitionPolicy policy = new NativeAreaLevelTransitionPolicy(10L, 100L, 5L, 3L);

        eq(10L, policy.initialize(0L));
        eq(100L, policy.initialize(250L));
        eq(55L, policy.transition(50L, 80L));
        eq(47L, policy.transition(50L, 10L));
        eq(100L, policy.transition(98L, 250L));
        eq(10L, policy.transition(12L, 0L));
        eq(50L, new NativeAreaLevelTransitionPolicy(0L, 100L, 0L, 0L).transition(50L, 90L));

        expect(IllegalArgumentException.class, () -> policy.transition(9L, 50L));
        expect(IllegalArgumentException.class, () -> policy.transition(101L, 50L));
        expect(IllegalArgumentException.class, () -> policy.transition(50L, -1L));
    }

    private static void blendDisabledUsesOnlyPrimaryTerritory() {
        TerritoryGrid grid = new TerritoryGrid(10L);
        AtomicInteger calls = new AtomicInteger();
        TerritoryAreaLevelResolution resolution = TerritoryAreaLevelResolver.resolve(
            "minecraft:overworld",
            9L,
            64L,
            0L,
            grid,
            0L,
            key -> {
                calls.incrementAndGet();
                return planFor(key);
            }
        );

        eq(TerritoryKey.of("minecraft:overworld", 0L, 0L), resolution.primaryTerritory());
        eq(10L, resolution.resolvedLevel());
        eq(1, resolution.samples().size());
        eq(1, calls.get());
        eq(1L, resolution.totalWeight());
    }

    private static void boundaryBlendIsDeterministicBoundedAndSmooth() {
        TerritoryGrid grid = new TerritoryGrid(10L);
        NativeAreaLevelPlanProvider provider = TerritoryAreaLevelPolicyTest::planFor;

        long beforeBoundary = TerritoryAreaLevelResolver.resolve(
            "minecraft:overworld", 9L, 64L, 0L, grid, 2L, provider
        ).resolvedLevel();
        long afterBoundary = TerritoryAreaLevelResolver.resolve(
            "minecraft:overworld", 10L, 64L, 0L, grid, 2L, provider
        ).resolvedLevel();
        long hardLeft = TerritoryAreaLevelResolver.resolve(
            "minecraft:overworld", 9L, 64L, 0L, grid, 0L, provider
        ).resolvedLevel();
        long hardRight = TerritoryAreaLevelResolver.resolve(
            "minecraft:overworld", 10L, 64L, 0L, grid, 0L, provider
        ).resolvedLevel();

        eq(18L, beforeBoundary);
        eq(22L, afterBoundary);
        eq(10L, hardLeft);
        eq(30L, hardRight);
        if ((afterBoundary - beforeBoundary) >= (hardRight - hardLeft)) {
            throw new AssertionError("boundary blending did not reduce the border jump");
        }

        TerritoryAreaLevelResolution repeated = TerritoryAreaLevelResolver.resolve(
            "minecraft:overworld", 9L, 64L, 0L, grid, 2L, provider
        );
        eq(beforeBoundary, repeated.resolvedLevel());
        if (repeated.samples().size() > 4) throw new AssertionError("sample budget exceeded");
        expect(UnsupportedOperationException.class, () -> repeated.samples().clear());
    }

    private static void cornerBlendSamplesAtMostFourTerritories() {
        TerritoryGrid grid = new TerritoryGrid(10L);
        AtomicInteger calls = new AtomicInteger();
        TerritoryAreaLevelResolution result = TerritoryAreaLevelResolver.resolve(
            "minecraft:overworld",
            9L,
            64L,
            9L,
            grid,
            2L,
            key -> {
                calls.incrementAndGet();
                if (key.cellX() == 0L && key.cellZ() == 0L) return NativeAreaLevelPlan.of(10L, List.of());
                if (key.cellX() == 1L && key.cellZ() == 0L) return NativeAreaLevelPlan.of(30L, List.of());
                if (key.cellX() == 0L && key.cellZ() == 1L) return NativeAreaLevelPlan.of(50L, List.of());
                if (key.cellX() == 1L && key.cellZ() == 1L) return NativeAreaLevelPlan.of(70L, List.of());
                throw new AssertionError("unexpected sampled territory: " + key);
            }
        );

        eq(34L, result.resolvedLevel());
        eq(4, result.samples().size());
        eq(4, calls.get());
        eq(25L, result.totalWeight());
        eq(List.of(9L, 6L, 6L, 4L), result.samples().stream().map(TerritoryAreaLevelSample::weight).toList());
    }

    private static void negativeCoordinatesUseTheSameBoundarySemantics() {
        TerritoryGrid grid = new TerritoryGrid(10L);
        TerritoryAreaLevelResolution result = TerritoryAreaLevelResolver.resolve(
            "minecraft:overworld",
            -1L,
            64L,
            0L,
            grid,
            2L,
            key -> {
                if (key.cellX() == -1L) return NativeAreaLevelPlan.of(10L, List.of());
                if (key.cellX() == 0L) return NativeAreaLevelPlan.of(30L, List.of());
                throw new AssertionError("unexpected sampled territory: " + key);
            }
        );
        eq(TerritoryKey.of("minecraft:overworld", -1L, 0L), result.primaryTerritory());
        eq(18L, result.resolvedLevel());
    }

    private static void invalidInputsFailClosed() {
        expect(IllegalArgumentException.class, () -> new NativeAreaLevelTransitionPolicy(-1L, 10L, 1L, 1L));
        expect(IllegalArgumentException.class, () -> new NativeAreaLevelTransitionPolicy(10L, 9L, 1L, 1L));
        expect(IllegalArgumentException.class, () -> new NativeAreaLevelTransitionPolicy(0L, 10L, -1L, 1L));
        expect(IllegalArgumentException.class, () -> new NativeAreaLevelTransitionPolicy(0L, 10L, 1L, -1L));

        TerritoryGrid grid = new TerritoryGrid(10L);
        expect(IllegalArgumentException.class, () -> TerritoryAreaLevelResolver.resolve(
            "minecraft:overworld", 0L, 0L, 0L, grid, -1L, TerritoryAreaLevelPolicyTest::planFor
        ));
        expect(IllegalArgumentException.class, () -> TerritoryAreaLevelResolver.resolve(
            "minecraft:overworld", 0L, 0L, 0L, grid, 5L, TerritoryAreaLevelPolicyTest::planFor
        ));
        expect(NullPointerException.class, () -> TerritoryAreaLevelResolver.resolve(
            "minecraft:overworld", 0L, 0L, 0L, grid, 0L, null
        ));
    }

    private static NativeAreaLevelPlan planFor(TerritoryKey key) {
        if (key.cellX() == 0L) return NativeAreaLevelPlan.of(10L, List.of());
        if (key.cellX() == 1L) return NativeAreaLevelPlan.of(30L, List.of());
        throw new AssertionError("unexpected sampled territory: " + key);
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
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("expected=" + expected + " actual=" + actual);
        }
    }
}
