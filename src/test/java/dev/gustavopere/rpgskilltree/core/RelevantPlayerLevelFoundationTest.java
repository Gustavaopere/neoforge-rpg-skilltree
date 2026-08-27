package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.concurrent.atomic.AtomicBoolean;

public final class RelevantPlayerLevelFoundationTest {
    public static void main(String[] args) {
        candidatesAreValidatedAndStable();
        unrelatedGlobalHighLevelNeverLeaksThroughFilter();
        policyReceivesCanonicalImmutableRelevantCandidates();
        noRelevantCandidatesProduceNoPlayerFloor();
        policyCannotInventLevelOutsideRelevantRange();
        hugeRelevantLevelsRemainSupported();
        System.out.println("RelevantPlayerLevelFoundationTest: PASS");
    }

    private static void candidatesAreValidatedAndStable() {
        RelevantPlayerCandidate candidate = new RelevantPlayerCandidate("player-a", 5L, 144L, true, false);
        eq("player-a", candidate.playerId());
        eq(5L, candidate.level());
        eq(144L, candidate.distanceSquared());
        eq(true, candidate.engaged());
        eq(false, candidate.partyMember());

        expect(IllegalArgumentException.class, () -> new RelevantPlayerCandidate("", 1L, 0L, false, false));
        expect(IllegalArgumentException.class, () -> new RelevantPlayerCandidate("player", -1L, 0L, false, false));
        expect(IllegalArgumentException.class, () -> new RelevantPlayerCandidate("player", 1L, -1L, false, false));
    }

    private static void unrelatedGlobalHighLevelNeverLeaksThroughFilter() {
        List<RelevantPlayerCandidate> localCandidates = List.of(
            new RelevantPlayerCandidate("beginner", 5L, 25L, true, false),
            new RelevantPlayerCandidate("spectator-veteran", 800L, 36L, false, false)
        );

        RelevantPlayerLevelResolution resolution = RelevantPlayerLevelResolver.resolve(
            localCandidates,
            RelevantPlayerCandidate::engaged,
            candidates -> OptionalLong.of(candidates.stream().mapToLong(RelevantPlayerCandidate::level).max().orElseThrow())
        );

        eq(List.of(new RelevantPlayerCandidate("beginner", 5L, 25L, true, false)), resolution.relevantCandidates());
        eq(OptionalLong.of(5L), resolution.relevantPlayerLevel());
    }

    private static void policyReceivesCanonicalImmutableRelevantCandidates() {
        List<RelevantPlayerCandidate> candidates = List.of(
            new RelevantPlayerCandidate("z-player", 30L, 9L, true, false),
            new RelevantPlayerCandidate("a-player", 10L, 4L, false, true),
            new RelevantPlayerCandidate("m-player", 20L, 16L, false, false)
        );

        RelevantPlayerLevelResolution resolution = RelevantPlayerLevelResolver.resolve(
            candidates,
            candidate -> candidate.engaged() || candidate.partyMember(),
            relevant -> {
                eq(List.of("a-player", "z-player"), relevant.stream().map(RelevantPlayerCandidate::playerId).toList());
                expect(UnsupportedOperationException.class, () -> relevant.add(
                    new RelevantPlayerCandidate("inject", 999L, 0L, true, false)
                ));
                long sum = relevant.stream().mapToLong(RelevantPlayerCandidate::level).sum();
                return OptionalLong.of(sum / relevant.size());
            }
        );

        eq(OptionalLong.of(20L), resolution.relevantPlayerLevel());
    }

    private static void noRelevantCandidatesProduceNoPlayerFloor() {
        AtomicBoolean policyCalled = new AtomicBoolean(false);
        RelevantPlayerLevelResolution resolution = RelevantPlayerLevelResolver.resolve(
            List.of(new RelevantPlayerCandidate("far-away", 900L, 1_000_000L, false, false)),
            candidate -> false,
            relevant -> {
                policyCalled.set(true);
                return OptionalLong.of(900L);
            }
        );

        eq(List.of(), resolution.relevantCandidates());
        eq(OptionalLong.empty(), resolution.relevantPlayerLevel());
        eq(false, policyCalled.get());
    }

    private static void policyCannotInventLevelOutsideRelevantRange() {
        List<RelevantPlayerCandidate> relevant = List.of(
            new RelevantPlayerCandidate("one", 5L, 1L, true, false),
            new RelevantPlayerCandidate("two", 10L, 2L, true, false)
        );

        expect(IllegalStateException.class, () -> RelevantPlayerLevelResolver.resolve(
            relevant,
            candidate -> true,
            candidates -> OptionalLong.of(800L)
        ));
        expect(IllegalStateException.class, () -> RelevantPlayerLevelResolver.resolve(
            relevant,
            candidate -> true,
            candidates -> OptionalLong.of(4L)
        ));
        expect(IllegalArgumentException.class, () -> RelevantPlayerLevelResolver.resolve(
            relevant,
            candidate -> true,
            candidates -> OptionalLong.of(-1L)
        ));

        expect(IllegalArgumentException.class, () -> RelevantPlayerLevelResolver.resolve(
            List.of(
                new RelevantPlayerCandidate("duplicate", 5L, 1L, true, false),
                new RelevantPlayerCandidate("duplicate", 6L, 2L, true, false)
            ),
            candidate -> true,
            candidates -> OptionalLong.of(5L)
        ));
    }

    private static void hugeRelevantLevelsRemainSupported() {
        long huge = 5_000_000_000L;
        RelevantPlayerLevelResolution resolution = RelevantPlayerLevelResolver.resolve(
            List.of(
                new RelevantPlayerCandidate("owner", huge, 0L, true, true),
                new RelevantPlayerCandidate("ally", huge - 2L, 25L, true, true)
            ),
            candidate -> candidate.engaged(),
            candidates -> OptionalLong.of(candidates.stream().mapToLong(RelevantPlayerCandidate::level).max().orElseThrow())
        );
        eq(OptionalLong.of(huge), resolution.relevantPlayerLevel());
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
