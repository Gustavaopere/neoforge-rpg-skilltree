package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Objects;

public final class RelevantPlayerCandidateSelectorTest {
    public static void main(String[] args) {
        nearestCandidatesAreDeterministicAndBounded();
        boundaryDistanceIsIncludedAndRemotePlayersAreExcluded();
        selectionIsImmutable();
        invalidSelectionInputsFailClosed();
        duplicatePlayerIdsFailClosed();
        System.out.println("RelevantPlayerCandidateSelectorTest: PASS");
    }

    private static void nearestCandidatesAreDeterministicAndBounded() {
        List<RelevantPlayerCandidate> selected = RelevantPlayerCandidateSelector.selectNearest(
            List.of(
                candidate("z-near", 20L, 25L),
                candidate("far", 99L, 81L),
                candidate("b-tie", 30L, 16L),
                candidate("a-tie", 10L, 16L)
            ),
            100L,
            3
        );

        eq(List.of("a-tie", "b-tie", "z-near"), selected.stream().map(RelevantPlayerCandidate::playerId).toList());
        eq(3, selected.size());
    }

    private static void boundaryDistanceIsIncludedAndRemotePlayersAreExcluded() {
        List<RelevantPlayerCandidate> selected = RelevantPlayerCandidateSelector.selectNearest(
            List.of(
                candidate("inside", 5L, 63L),
                candidate("edge", 6L, 64L),
                candidate("outside", 1000L, 65L)
            ),
            64L,
            10
        );

        eq(List.of("inside", "edge"), selected.stream().map(RelevantPlayerCandidate::playerId).toList());
    }

    private static void selectionIsImmutable() {
        List<RelevantPlayerCandidate> selected = RelevantPlayerCandidateSelector.selectNearest(
            List.of(candidate("one", 1L, 1L)),
            4L,
            1
        );
        expect(UnsupportedOperationException.class, () -> selected.add(candidate("two", 2L, 2L)));
    }

    private static void invalidSelectionInputsFailClosed() {
        expect(NullPointerException.class, () -> RelevantPlayerCandidateSelector.selectNearest(null, 1L, 1));
        expect(IllegalArgumentException.class, () -> RelevantPlayerCandidateSelector.selectNearest(List.of(), -1L, 1));
        expect(IllegalArgumentException.class, () -> RelevantPlayerCandidateSelector.selectNearest(List.of(), 1L, 0));
        expect(NullPointerException.class, () -> RelevantPlayerCandidateSelector.selectNearest(
            java.util.Arrays.asList(candidate("one", 1L, 1L), null), 4L, 2
        ));
    }

    private static void duplicatePlayerIdsFailClosed() {
        expect(IllegalArgumentException.class, () -> RelevantPlayerCandidateSelector.selectNearest(
            List.of(candidate("same", 1L, 1L), candidate("same", 2L, 2L)),
            4L,
            2
        ));
    }

    private static RelevantPlayerCandidate candidate(String id, long level, long distanceSquared) {
        return new RelevantPlayerCandidate(id, level, distanceSquared, true, false);
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
