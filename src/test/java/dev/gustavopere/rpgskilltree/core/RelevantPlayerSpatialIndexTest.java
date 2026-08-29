package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class RelevantPlayerSpatialIndexTest {
    public static void main(String[] args) {
        localCandidatesAreDeterministicAndDistanceOrdered();
        farPlayersDoNotLeakIntoEncounter();
        outputAndCellWorkAreBounded();
        noPlayersIsStableAndCheap();
        extremeCoordinatesFailSafeInsteadOfOverflowing();
        invalidPoliciesAndDuplicateIdsFailClosed();
        System.out.println("RelevantPlayerSpatialIndexTest: PASS");
    }

    private static RelevantPlayerSearchPolicy policy() {
        return new RelevantPlayerSearchPolicy(32, 96, 48, 3, 10L);
    }

    private static void localCandidatesAreDeterministicAndDistanceOrdered() {
        RelevantPlayerSpatialIndex index = RelevantPlayerSpatialIndex.build(List.of(
            new RelevantPlayerPresence("z-near", 9L, 3, 64, 4),
            new RelevantPlayerPresence("a-near", 7L, 3, 64, 4),
            new RelevantPlayerPresence("middle", 11L, 20, 64, 0)
        ), 32);

        RelevantPlayerSpatialQuery query = index.query(0, 64, 0, policy());
        eq(List.of("a-near", "z-near", "middle"),
            query.candidates().stream().map(RelevantPlayerCandidate::playerId).toList());
        eq(List.of(25L, 25L, 400L),
            query.candidates().stream().map(RelevantPlayerCandidate::distanceSquared).toList());
        eq(true, query.candidates().get(0).engaged());
        eq(true, query.candidates().get(2).engaged());
        expect(UnsupportedOperationException.class, () -> query.candidates().clear());
    }

    private static void farPlayersDoNotLeakIntoEncounter() {
        RelevantPlayerSpatialIndex index = RelevantPlayerSpatialIndex.build(List.of(
            new RelevantPlayerPresence("local", 4L, 40, 64, 0),
            new RelevantPlayerPresence("edge-out", 999L, 97, 64, 0),
            new RelevantPlayerPresence("remote", 5_000L, 10_000, 64, 10_000)
        ), 32);

        RelevantPlayerSpatialQuery query = index.query(0, 64, 0, policy());
        eq(List.of("local"), query.candidates().stream().map(RelevantPlayerCandidate::playerId).toList());
        eq(4L, query.candidates().get(0).level());
        eq(true, query.candidates().get(0).engaged());
    }

    private static void outputAndCellWorkAreBounded() {
        ArrayList<RelevantPlayerPresence> presences = new ArrayList<>();
        for (int index = 0; index < 40; index++) {
            presences.add(new RelevantPlayerPresence(
                "player-%02d".formatted(index),
                index,
                index % 8,
                64,
                index / 8
            ));
        }
        RelevantPlayerSpatialIndex spatial = RelevantPlayerSpatialIndex.build(presences, 32);
        RelevantPlayerSearchPolicy bounded = new RelevantPlayerSearchPolicy(32, 96, 48, 5, 20L);
        RelevantPlayerSpatialQuery query = spatial.query(0, 64, 0, bounded);

        eq(5, query.candidates().size());
        if (query.visitedCells() > bounded.worstCaseVisitedCells()) {
            throw new AssertionError("visited more cells than policy bound");
        }
        if (query.visitedCells() > RelevantPlayerSearchPolicy.MAX_QUERY_CELLS) {
            throw new AssertionError("technical cell budget exceeded");
        }
        if (query.scannedPlayers() > query.indexedPlayers()) {
            throw new AssertionError("spatial query rescanned a player");
        }
    }

    private static void noPlayersIsStableAndCheap() {
        RelevantPlayerSpatialIndex empty = RelevantPlayerSpatialIndex.build(List.of(), 32);
        RelevantPlayerSpatialQuery query = empty.query(0, 64, 0, policy());
        eq(List.of(), query.candidates());
        eq(0, query.indexedPlayers());
        eq(0, query.scannedPlayers());
    }

    private static void extremeCoordinatesFailSafeInsteadOfOverflowing() {
        RelevantPlayerSpatialIndex index = RelevantPlayerSpatialIndex.build(List.of(
            new RelevantPlayerPresence("opposite-edge", 500L, Integer.MAX_VALUE, 0, Integer.MAX_VALUE),
            new RelevantPlayerPresence("same-edge", 12L, Integer.MIN_VALUE, 0, Integer.MIN_VALUE)
        ), 32);
        RelevantPlayerSpatialQuery query = index.query(Integer.MIN_VALUE, 0, Integer.MIN_VALUE, policy());
        eq(List.of("same-edge"), query.candidates().stream().map(RelevantPlayerCandidate::playerId).toList());
        eq(0L, query.candidates().get(0).distanceSquared());
    }

    private static void invalidPoliciesAndDuplicateIdsFailClosed() {
        expect(IllegalArgumentException.class, () -> new RelevantPlayerSearchPolicy(0, 96, 48, 3, 10L));
        expect(IllegalArgumentException.class, () -> new RelevantPlayerSearchPolicy(32, 48, 49, 3, 10L));
        expect(IllegalArgumentException.class, () -> new RelevantPlayerSearchPolicy(32, 96, 48, 0, 10L));
        expect(IllegalArgumentException.class, () -> new RelevantPlayerSearchPolicy(1, 10_000, 1, 1, 1L));
        expect(IllegalArgumentException.class, () -> RelevantPlayerSpatialIndex.build(List.of(
            new RelevantPlayerPresence("duplicate", 1L, 0, 0, 0),
            new RelevantPlayerPresence("duplicate", 2L, 1, 0, 1)
        ), 32));
        RelevantPlayerSpatialIndex index = RelevantPlayerSpatialIndex.build(List.of(), 32);
        expect(IllegalArgumentException.class, () -> index.query(
            0, 0, 0, new RelevantPlayerSearchPolicy(16, 32, 16, 2, 1L)
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
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("expected=" + expected + " actual=" + actual);
        }
    }
}
