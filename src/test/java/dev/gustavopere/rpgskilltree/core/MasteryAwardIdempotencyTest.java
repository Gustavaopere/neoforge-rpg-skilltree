package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class MasteryAwardIdempotencyTest {
    public static void main(String[] args) {
        identicalReplayKeyIsANoOp();
        conflictingReplayKeyFailsClosed();
        batchDuplicatesDoNotDoubleAward();
        repeatableProvenanceStillAccumulates();
        receiptsSurviveProgressionCodecRoundTrip();
        System.out.println("MasteryAwardIdempotencyTest: PASS");
    }

    private static void identicalReplayKeyIsANoOp() {
        MasteryAward award = MasteryAward.replaySafe(
            "martial:melee", 25, "minecraft:player_attack", "semantic:combat/hit/0001");
        MasteryState first = MasteryAwardService.apply(MasteryState.empty(), List.of(award));
        eq(25, first.experience("martial:melee"));
        eq(new MasteryAwardReceipt("martial:melee", 25), first.creditedAwards().get(award.replayKey()));

        MasteryState replay = MasteryAwardService.apply(first, List.of(award));
        same(first, replay);
        eq(25, replay.experience("martial:melee"));
    }

    private static void conflictingReplayKeyFailsClosed() {
        MasteryAward firstAward = MasteryAward.replaySafe(
            "martial:melee", 25, "minecraft:player_attack", "semantic:combat/hit/0002");
        MasteryState state = MasteryAwardService.apply(MasteryState.empty(), List.of(firstAward));

        expect(IllegalArgumentException.class, () -> MasteryAwardService.apply(
            state,
            List.of(MasteryAward.replaySafe(
                "martial:melee", 30, "minecraft:player_attack", firstAward.replayKey()))
        ));
        expect(IllegalArgumentException.class, () -> MasteryAwardService.apply(
            state,
            List.of(MasteryAward.replaySafe(
                "arcane:fire", 25, "minecraft:player_attack", firstAward.replayKey()))
        ));
    }

    private static void batchDuplicatesDoNotDoubleAward() {
        MasteryAward award = MasteryAward.replaySafe(
            "arcane:fire", 40, "irons_spellbooks:fireball", "semantic:spell/cast/0001");
        MasteryState state = MasteryAwardService.apply(MasteryState.empty(), List.of(award, award));
        eq(40, state.experience("arcane:fire"));
        eq(1, state.creditedAwards().size());
    }

    private static void repeatableProvenanceStillAccumulates() {
        MasteryState state = MasteryAwardService.apply(MasteryState.empty(), List.of(
            new MasteryAward("magic:casting", 2, "irons_spellbooks:fireball"),
            new MasteryAward("irons:fire", 5, "irons_spellbooks:fireball"),
            new MasteryAward("irons:fire", 3, "addon:flame_wave")
        ));
        eq(2, state.experience("magic:casting"));
        eq(8, state.experience("irons:fire"));
        eq(0, state.creditedAwards().size());
    }

    private static void receiptsSurviveProgressionCodecRoundTrip() {
        MasteryState mastery = MasteryAwardService.apply(
            MasteryState.of(Map.of("legacy:lane", 7)),
            List.of(MasteryAward.replaySafe(
                "arcane:fire", 55, "irons_spellbooks:fireball", "semantic:spell/cast/roundtrip"))
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
