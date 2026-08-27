package dev.gustavopere.rpgskilltree.core;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class CoreProgressionSyncStateTest {
    public static void main(String[] args) {
        projectionCarriesClientRelevantState();
        projectionSizeDoesNotTrackTransactionHistory();
        rulesMismatchIsRejected();
        technicalCeilingHasNoNextLevelCost();
        malformedPayloadIsRejected();
        System.out.println("CoreProgressionSyncStateTest: PASS");
    }

    private static ProgressionRulesSnapshot rules(long version, long baseXp) {
        return new ProgressionRulesSnapshot(
            version,
            "rpgskilltree:sync_test",
            List.of(
                new LevelCurveBand(0L, baseXp, 2L),
                new LevelCurveBand(100L, Math.max(400L, baseXp + 300L), 5L)
            ),
            new MainPerkBudget(30L)
        );
    }

    private static CorePointLedger populatedLedger(long rulesVersion) {
        CorePointLedger ledger = CorePointLedger.empty();
        for (int i = 0; i < 1_100; i++) {
            ledger = ledger.apply(CorePointTransaction.credit(
                "test:credit/" + i,
                CorePointTransactionKind.EARN,
                1L,
                "test:quest",
                rulesVersion
            ));
        }
        ledger = ledger.apply(CorePointTransaction.allocate(
            "test:spend/attribute",
            CorePointTransactionKind.SPEND,
            100L,
            "test:attribute",
            CorePointAllocation.ATTRIBUTE,
            rulesVersion
        ));
        ledger = ledger.apply(CorePointTransaction.allocate(
            "test:spend/main",
            CorePointTransactionKind.SPEND,
            20L,
            "test:main",
            CorePointAllocation.MAIN_PERK,
            rulesVersion
        ));
        return ledger;
    }

    private static void projectionCarriesClientRelevantState() {
        ProgressionRulesSnapshot rules = rules(21L, 100L);
        CorePointLedger ledger = populatedLedger(rules.version());
        CoreProgressionState state = CoreProgressionState.nativeState(
            new CharacterProgressionState(1_000_000L, 123L),
            ledger,
            rules
        );

        CoreProgressionSyncState sync = CoreProgressionSyncState.from(state, rules);
        eq(1_000_000L, sync.level());
        eq(123L, sync.xpIntoLevel());
        eq(rules.levelCurve().xpToNextLevel(1_000_000L), sync.xpToNextLevel());
        eq(1_100L, sync.totalCorePoints());
        eq(100L, sync.attributeAllocated());
        eq(20L, sync.mainPerkAllocated());
        eq(980L, sync.availableCorePoints());
        eq(30L, sync.mainPerkBudget());
        eq(rules.version(), sync.rulesVersion());
        eq(rules.fingerprint(), sync.rulesFingerprint());

        byte[] encoded = CoreProgressionSyncStateCodec.encode(sync);
        eq(sync, CoreProgressionSyncStateCodec.decode(encoded));
    }

    private static void projectionSizeDoesNotTrackTransactionHistory() {
        ProgressionRulesSnapshot rules = rules(21L, 100L);
        CoreProgressionState state = CoreProgressionState.nativeState(
            new CharacterProgressionState(5_000_000_000L, 0L),
            populatedLedger(rules.version()),
            rules
        );
        if (state.corePoints().transactions().size() != CorePointLedger.RECENT_TRANSACTION_LIMIT) {
            throw new AssertionError("test fixture must fill the replay window");
        }

        byte[] encoded = CoreProgressionSyncStateCodec.encode(CoreProgressionSyncState.from(state, rules));
        if (encoded.length >= 256) {
            throw new AssertionError("Core sync projection unexpectedly large: " + encoded.length);
        }
    }

    private static void rulesMismatchIsRejected() {
        ProgressionRulesSnapshot original = rules(21L, 100L);
        CoreProgressionState state = CoreProgressionState.nativeState(
            CharacterProgressionState.empty(),
            CorePointLedger.empty(),
            original
        );
        expect(IllegalStateException.class, () -> CoreProgressionSyncState.from(state, rules(21L, 120L)));
        expect(IllegalStateException.class, () -> CoreProgressionSyncState.from(state, rules(22L, 100L)));
    }

    private static void technicalCeilingHasNoNextLevelCost() {
        ProgressionRulesSnapshot rules = rules(21L, 100L);
        CoreProgressionState state = CoreProgressionState.nativeState(
            new CharacterProgressionState(Long.MAX_VALUE, 0L),
            CorePointLedger.empty(),
            rules
        );
        CoreProgressionSyncState sync = CoreProgressionSyncState.from(state, rules);
        eq(BigInteger.ZERO, sync.xpToNextLevel());
    }

    private static void malformedPayloadIsRejected() {
        ProgressionRulesSnapshot rules = rules(21L, 100L);
        byte[] valid = CoreProgressionSyncStateCodec.encode(CoreProgressionSyncState.from(
            CoreProgressionState.nativeState(CharacterProgressionState.empty(), CorePointLedger.empty(), rules),
            rules
        ));

        expect(IllegalArgumentException.class, () -> CoreProgressionSyncStateCodec.decode(new byte[0]));

        byte[] badVersion = valid.clone();
        badVersion[3] = 2;
        expect(IllegalArgumentException.class, () -> CoreProgressionSyncStateCodec.decode(badVersion));

        byte[] trailing = Arrays.copyOf(valid, valid.length + 1);
        expect(IllegalArgumentException.class, () -> CoreProgressionSyncStateCodec.decode(trailing));

        byte[] truncated = Arrays.copyOf(valid, valid.length - 1);
        expect(IllegalArgumentException.class, () -> CoreProgressionSyncStateCodec.decode(truncated));
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
