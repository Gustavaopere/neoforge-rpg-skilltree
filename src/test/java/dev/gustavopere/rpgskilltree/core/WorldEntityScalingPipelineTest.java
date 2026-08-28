package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalLong;

public final class WorldEntityScalingPipelineTest {
    public static void main(String[] args) {
        pipelineComposesAreaPlayersLevelAndStatsInOneOrder();
        playerFloorStillWinsAfterNegativeVariance();
        requestOwnsImmutableInputs();
        missingArchetypePolicyFailsClosed();
        System.out.println("WorldEntityScalingPipelineTest: PASS");
    }

    private static void pipelineComposesAreaPlayersLevelAndStatsInOneOrder() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        WorldEntityScalingRequest request = new WorldEntityScalingRequest(
            TerritoryKey.of("minecraft:overworld", 4L, 9L),
            NativeAreaLevelPlan.of(5L, List.of(
                NativeAreaLevelContribution.of("rpgskilltree:structure", 20L)
            )),
            List.of(
                new RelevantPlayerCandidate("beginner", 10L, 25L, true, false),
                new RelevantPlayerCandidate("unrelated-veteran", 800L, 36L, false, false)
            ),
            RelevantPlayerCandidate::engaged,
            candidates -> OptionalLong.of(candidates.stream().mapToLong(RelevantPlayerCandidate::level).max().orElseThrow()),
            EntityArchetype.HOSTILE,
            new EntityLevelAdjustment(2L, 3L),
            CanonicalStatSnapshot.of(Map.of(health, new BigDecimal("20"))),
            Map.of(EntityArchetype.HOSTILE, context -> Map.of(
                health, stat -> stat.providerValue().add(BigDecimal.valueOf(context.entityLevel()))
            ))
        );

        WorldEntityScalingResult result = WorldEntityScalingService.resolve(request);
        eq(25L, result.nativeArea().resolvedLevel());
        eq(List.of("beginner"), result.relevantPlayers().relevantCandidates().stream().map(RelevantPlayerCandidate::playerId).toList());
        eq(OptionalLong.of(10L), result.relevantPlayers().relevantPlayerLevel());
        eq(25L, result.entityLevel().baseFloor());
        eq(30L, result.entityLevel().finalLevel());
        eq(30L, result.stats().entityLevel());
        decimalEq("50", result.stats().effectiveStats().value(health));
    }

    private static void playerFloorStillWinsAfterNegativeVariance() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        WorldEntityScalingResult result = WorldEntityScalingService.resolve(new WorldEntityScalingRequest(
            TerritoryKey.of("minecraft:overworld", 0L, 0L),
            NativeAreaLevelPlan.of(1L, List.of()),
            List.of(new RelevantPlayerCandidate("returning", 50L, 0L, true, true)),
            candidate -> true,
            candidates -> OptionalLong.of(50L),
            EntityArchetype.HOSTILE,
            new EntityLevelAdjustment(-5L, 0L),
            CanonicalStatSnapshot.of(Map.of(health, new BigDecimal("20"))),
            Map.of(EntityArchetype.HOSTILE, context -> Map.of(
                health, stat -> stat.providerValue()
            ))
        ));

        eq(50L, result.entityLevel().baseFloor());
        eq(45L, result.entityLevel().rolledLevel());
        eq(50L, result.entityLevel().finalLevel());
        eq(50L, result.stats().entityLevel());
    }

    private static void requestOwnsImmutableInputs() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        ArrayList<RelevantPlayerCandidate> players = new ArrayList<>();
        players.add(new RelevantPlayerCandidate("one", 5L, 0L, true, false));
        HashMap<EntityArchetype, EntityArchetypeStatPolicy> policies = new HashMap<>();
        policies.put(EntityArchetype.PASSIVE, context -> Map.of(health, stat -> stat.providerValue()));

        WorldEntityScalingRequest request = new WorldEntityScalingRequest(
            TerritoryKey.of("minecraft:overworld", 0L, 0L),
            NativeAreaLevelPlan.of(1L, List.of()),
            players,
            candidate -> true,
            candidates -> OptionalLong.of(5L),
            EntityArchetype.PASSIVE,
            EntityLevelAdjustment.NONE,
            CanonicalStatSnapshot.of(Map.of(health, new BigDecimal("10"))),
            policies
        );
        players.clear();
        policies.clear();

        eq(1, request.playerCandidates().size());
        eq(1, request.archetypeStatPolicies().size());
        expect(UnsupportedOperationException.class, () -> request.playerCandidates().clear());
        expect(UnsupportedOperationException.class, () -> request.archetypeStatPolicies().clear());
    }

    private static void missingArchetypePolicyFailsClosed() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        WorldEntityScalingRequest request = new WorldEntityScalingRequest(
            TerritoryKey.of("minecraft:overworld", 0L, 0L),
            NativeAreaLevelPlan.of(1L, List.of()),
            List.of(),
            candidate -> false,
            candidates -> OptionalLong.empty(),
            EntityArchetype.SPECIAL,
            EntityLevelAdjustment.NONE,
            CanonicalStatSnapshot.of(Map.of(health, new BigDecimal("20"))),
            Map.of()
        );

        expect(IllegalStateException.class, () -> WorldEntityScalingService.resolve(request));
    }

    private static void decimalEq(String expected, BigDecimal actual) {
        BigDecimal expectedDecimal = new BigDecimal(expected);
        if (expectedDecimal.compareTo(actual) != 0) throw new AssertionError(expectedDecimal + " != " + actual);
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
