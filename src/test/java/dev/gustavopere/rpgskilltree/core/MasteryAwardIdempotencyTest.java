package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class MasteryAwardIdempotencyTest {
    public static void main(String[] args) {
        identicalSourceReplayIsANoOp();
        conflictingSourceReplayFailsClosed();
        batchDuplicatesDoNotDoubleAward();
        receiptsSurviveProgressionCodecRoundTrip();
        System.out.println("MasteryAwardIdempotencyTest: PASS");
    }

    private static void identicalSourceReplayIsANoOp() {
        MasteryAward award = new MasteryAward("martial:melee", 25, "semantic:combat/hit/0001");
        MasteryState first = MasteryAwardService.apply(MasteryState.empty(), List.of(award));
        eq(25, first.experience("martial:melee"));
        eq(new MasteryAwardReceipt("martial:melee", 25), first.creditedAwards().get(award.sourceId()));

        MasteryState replay = MasteryAwardService.apply(first, List.of(award));
        same(first, replay);
        eq(25, replay.experience("martial:melee"));
    }

    private static void conflictingSourceReplayFailsClosed() {
        MasteryAward firstAward = new MasteryAward("martial:melee", 25, "semantic:combat/hit/0002");
        MasteryState state = MasteryAwardService.apply(MasteryState.empty(), List.of(firstAward));

        expect(IllegalArgumentException.class, () -> MasteryAwardService.apply(
            state,
            List.of(new MasteryAward("martial:melee", 30, firstAward.sourceId()))
        ));
        expect(IllegalArgumentException.class, () -> MasteryAwardService.apply(
            state,
            List.of(new MasteryAward("arcane:fire", 25, firstAward.sourceId()))
        ));
    }

    private static void batchDuplicatesDoNotDoubleAward() {
        MasteryAward award = new MasteryAward("arcane:fire", 40, "semantic:spell/cast/0001");
        MasteryState state = MasteryAwardService.apply(MasteryState.empty(), List.of(award, award));
        eq(40, state.experience("arcane:fire"));
        eq(1, state.creditedAwards().size());
    }

    private static void receiptsSurviveProgressionCodecRoundTrip() {
        MasteryState mastery = MasteryAwardService.apply(
            MasteryState.of(Map.of("legacy:lane", 7)),
            List.of(new MasteryAward("arcane:fire", 55, "semantic:spell/cast/roundtrip"))
        );
        ProgressionState source = ProgressionState.empty().withMastery(mastery);
        ProgressionState decoded = ProgressionStateCodec.decode(ProgressionStateCodec.encode(source));

        eq(4, ProgressionStateCodec.CURRENT_VERSION);
        eq(2, CanonicalPlayerStateCodec.CURRENT_VERSION);
        eq(mastery.experience(), decoded.mastery().experience());
        eq(mastery.creditedAwards(), decoded.mastery().creditedAwards());
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

    private static void same(Object expected, Object actual) {
        if (expected != actual) throw new AssertionError("expected same instance");
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
