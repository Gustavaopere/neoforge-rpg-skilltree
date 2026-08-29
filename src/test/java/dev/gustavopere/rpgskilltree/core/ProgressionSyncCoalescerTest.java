package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public final class ProgressionSyncCoalescerTest {
    public static void main(String[] args) {
        dirtySetsAreImmutableAndUnionDeterministic();
        repeatedMutationsForOnePlayerCoalesce();
        playersStayIndependentAndDrainIsOneShot();
        emptyDirtySetDoesNotQueueWork();
        System.out.println("ProgressionSyncCoalescerTest: PASS");
    }

    private static void dirtySetsAreImmutableAndUnionDeterministic() {
        ProgressionDirtySet left = ProgressionDirtySet.of(
            ProgressionDirtyReason.PERSISTENT_STATE,
            ProgressionDirtyReason.MASTERY_DISPLAY
        );
        ProgressionDirtySet right = ProgressionDirtySet.of(
            ProgressionDirtyReason.EFFECTS,
            ProgressionDirtyReason.PERSISTENT_STATE
        );

        ProgressionDirtySet merged = left.merge(right);
        eq(Set.of(
            ProgressionDirtyReason.PERSISTENT_STATE,
            ProgressionDirtyReason.MASTERY_DISPLAY,
            ProgressionDirtyReason.EFFECTS
        ), merged.reasons());
        eq(2, left.reasons().size());
        expect(UnsupportedOperationException.class, () ->
            merged.reasons().remove(ProgressionDirtyReason.EFFECTS));
    }

    private static void repeatedMutationsForOnePlayerCoalesce() {
        ProgressionSyncCoalescer coalescer = new ProgressionSyncCoalescer();
        UUID player = UUID.randomUUID();

        isTrue(coalescer.mark(player, ProgressionDirtySet.of(ProgressionDirtyReason.PERSISTENT_STATE)));
        isTrue(coalescer.mark(player, ProgressionDirtySet.of(ProgressionDirtyReason.EFFECTS)));
        isFalse(coalescer.mark(player, ProgressionDirtySet.of(ProgressionDirtyReason.EFFECTS)));

        eq(1, coalescer.pendingPlayers());
        ProgressionDirtySet pending = coalescer.pending(player).orElseThrow();
        eq(Set.of(
            ProgressionDirtyReason.PERSISTENT_STATE,
            ProgressionDirtyReason.EFFECTS
        ), pending.reasons());
    }

    private static void playersStayIndependentAndDrainIsOneShot() {
        ProgressionSyncCoalescer coalescer = new ProgressionSyncCoalescer();
        UUID first = UUID.randomUUID();
        UUID second = UUID.randomUUID();

        coalescer.mark(first, ProgressionDirtySet.of(ProgressionDirtyReason.CLASS_RESOLUTION));
        coalescer.mark(second, ProgressionDirtySet.of(ProgressionDirtyReason.TREE_AVAILABILITY));

        ProgressionDirtySet drainedFirst = coalescer.drain(first).orElseThrow();
        eq(Set.of(ProgressionDirtyReason.CLASS_RESOLUTION), drainedFirst.reasons());
        isTrue(coalescer.drain(first).isEmpty());
        eq(1, coalescer.pendingPlayers());

        Map<UUID, ProgressionDirtySet> remaining = coalescer.drainAll();
        eq(1, remaining.size());
        eq(Set.of(ProgressionDirtyReason.TREE_AVAILABILITY), remaining.get(second).reasons());
        eq(0, coalescer.pendingPlayers());
        expect(UnsupportedOperationException.class, () -> remaining.clear());
    }

    private static void emptyDirtySetDoesNotQueueWork() {
        ProgressionSyncCoalescer coalescer = new ProgressionSyncCoalescer();
        UUID player = UUID.randomUUID();

        isFalse(coalescer.mark(player, ProgressionDirtySet.empty()));
        eq(0, coalescer.pendingPlayers());
        isTrue(coalescer.pending(player).isEmpty());
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

    private static void isTrue(boolean value) {
        if (!value) throw new AssertionError("expected true");
    }

    private static void isFalse(boolean value) {
        if (value) throw new AssertionError("expected false");
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
