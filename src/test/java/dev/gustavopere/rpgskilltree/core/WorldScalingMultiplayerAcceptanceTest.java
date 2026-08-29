package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalLong;

/** Acceptance coverage for solo/party world scaling with large participant-level disparity. */
public final class WorldScalingMultiplayerAcceptanceTest {
    public static void main(String[] args) {
        soloUsesOnlyTheLocalRelevantPlayer();
        partyCanRaiseTheLocalFloorWithoutLeakingAnUnrelatedVeteran();
        System.out.println("WorldScalingMultiplayerAcceptanceTest: PASS");
    }

    private static void soloUsesOnlyTheLocalRelevantPlayer() {
        WorldEntityScalingResult result = resolve(List.of(
            new RelevantPlayerCandidate("solo", 20L, 16L, true, false),
            new RelevantPlayerCandidate("global-veteran", 5_000L, 25L, false, false)
        ));

        eq(List.of("solo"), result.relevantPlayers().relevantCandidates().stream()
            .map(RelevantPlayerCandidate::playerId).toList());
        eq(OptionalLong.of(20L), result.relevantPlayers().relevantPlayerLevel());
        eq(20L, result.entityLevel().baseFloor());
        eq(20L, result.entityLevel().finalLevel());
    }

    private static void partyCanRaiseTheLocalFloorWithoutLeakingAnUnrelatedVeteran() {
        List<RelevantPlayerCandidate> spatial = List.of(
            new RelevantPlayerCandidate("local", 20L, 16L, true, false),
            new RelevantPlayerCandidate("global-veteran", 5_000L, 25L, false, false)
        );
        List<RelevantPlayerCandidate> party = List.of(
            new RelevantPlayerCandidate("remote-party", 200L, 1_000_000L, false, true)
        );

        List<RelevantPlayerCandidate> merged = RelevantPlayerCandidateMerger.merge(spatial, party, 4);
        WorldEntityScalingResult result = resolve(merged);

        eq(List.of("local", "remote-party"), result.relevantPlayers().relevantCandidates().stream()
            .map(RelevantPlayerCandidate::playerId).toList());
        eq(OptionalLong.of(200L), result.relevantPlayers().relevantPlayerLevel());
        eq(200L, result.entityLevel().baseFloor());
        eq(200L, result.entityLevel().finalLevel());
        if (result.entityLevel().finalLevel() >= 5_000L) {
            throw new AssertionError("unrelated global veteran leaked into local party scaling");
        }
    }

    private static WorldEntityScalingResult resolve(List<RelevantPlayerCandidate> candidates) {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        return WorldEntityScalingService.resolve(new WorldEntityScalingRequest(
            TerritoryKey.of("minecraft:overworld", 0L, 0L),
            NativeAreaLevelPlan.of(8L, List.of()),
            candidates,
            candidate -> candidate.engaged() || candidate.partyMember(),
            relevant -> OptionalLong.of(relevant.stream()
                .mapToLong(RelevantPlayerCandidate::level)
                .max()
                .orElseThrow()),
            EntityArchetype.HOSTILE,
            EntityLevelAdjustment.NONE,
            CanonicalStatSnapshot.of(Map.of(health, new BigDecimal("20"))),
            Map.of(EntityArchetype.HOSTILE, context -> Map.of(
                health, stat -> stat.providerValue()
            ))
        ));
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("expected=" + expected + " actual=" + actual);
        }
    }
}
