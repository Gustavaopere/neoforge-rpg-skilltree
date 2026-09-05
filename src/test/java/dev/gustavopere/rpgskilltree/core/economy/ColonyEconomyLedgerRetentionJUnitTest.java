package dev.gustavopere.rpgskilltree.core.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;

final class ColonyEconomyLedgerRetentionJUnitTest {
    private static final EconomyColonyKey COLONY = new EconomyColonyKey(
        UUID.fromString("00000000-0000-0000-0000-000000001811")
    );

    @Test
    void monetaryMutationsFailClosedWhenBoundedAuditRetentionIsFull() {
        List<EconomyTransaction> history = fullHistory();
        ColonyEconomyLedger ledger = new ColonyEconomyLedger(history);
        ColonyEconomyState state = stateAt(history.size());

        EconomyMutationResult blocked = ledger.apply(
            state,
            new EconomyCommand(
                UUID.fromString("00000000-0000-0000-0000-000000001812"),
                "retention:overflow:new",
                EconomyTransactionKind.MINT,
                1L
            ),
            50_000L
        );

        assertEquals(EconomyMutationResult.Status.RETENTION_LIMIT_REACHED, blocked.status());
        assertSame(state, blocked.state());
        assertEquals(ColonyEconomyLedger.MAX_RETAINED_TRANSACTIONS, ledger.transactions().size());
    }

    @Test
    void duplicateRemainsDuplicateAtRetentionLimitAndOversizedRestoreIsRejected() {
        List<EconomyTransaction> history = fullHistory();
        ColonyEconomyLedger ledger = new ColonyEconomyLedger(history);
        ColonyEconomyState state = stateAt(history.size());
        EconomyTransaction first = history.get(0);

        EconomyMutationResult duplicate = ledger.apply(
            state,
            new EconomyCommand(first.transactionId(), first.causalKey(), EconomyTransactionKind.MINT, 1L),
            50_001L
        );

        assertEquals(EconomyMutationResult.Status.DUPLICATE, duplicate.status());

        List<EconomyTransaction> oversized = new ArrayList<>(history);
        oversized.add(transaction(ColonyEconomyLedger.MAX_RETAINED_TRANSACTIONS));
        assertThrows(IllegalArgumentException.class, () -> new ColonyEconomyLedger(oversized));
    }

    private static List<EconomyTransaction> fullHistory() {
        List<EconomyTransaction> history = new ArrayList<>(ColonyEconomyLedger.MAX_RETAINED_TRANSACTIONS);
        for (int i = 0; i < ColonyEconomyLedger.MAX_RETAINED_TRANSACTIONS; i++) {
            history.add(transaction(i));
        }
        return history;
    }

    private static EconomyTransaction transaction(int index) {
        long resulting = index + 1L;
        UUID id = new UUID(0x1811000000000000L, resulting);
        return new EconomyTransaction(
            id,
            COLONY,
            "retention:" + index,
            EconomyTransactionKind.MINT,
            1L,
            "monetary_authority",
            "treasury",
            index,
            resulting,
            0L,
            resulting,
            resulting,
            Map.of()
        );
    }

    private static ColonyEconomyState stateAt(long amount) {
        return new ColonyEconomyState(COLONY, amount, 0L, amount, 0L, 0L, 100.0D, 0.10D, 1L, 0L, 1);
    }
}
