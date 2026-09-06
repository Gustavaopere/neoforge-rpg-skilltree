package dev.gustavopere.rpgskilltree.core.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;
import org.junit.jupiter.api.Test;

final class ColonyEconomyLedgerRestoreJUnitTest {
    private static final EconomyColonyKey COLONY = new EconomyColonyKey(
        UUID.fromString("00000000-0000-0000-0000-000000001211")
    );

    @Test
    void restoredAuditHistoryPreservesTransactionAndCausalReplayProtection() {
        ColonyEconomyLedger original = new ColonyEconomyLedger();
        EconomyCommand firstCommand = new EconomyCommand(
            UUID.fromString("00000000-0000-0000-0000-000000001212"),
            "restart-safe:mint:1",
            EconomyTransactionKind.MINT,
            20L
        );
        ColonyEconomyState funded = original.apply(ColonyEconomyState.empty(COLONY), firstCommand, 1_000L).state();

        ColonyEconomyLedger restored = new ColonyEconomyLedger(original.transactions());

        EconomyMutationResult sameTransaction = restored.apply(funded, firstCommand, 2_000L);
        EconomyMutationResult sameCausalKey = restored.apply(
            funded,
            new EconomyCommand(
                UUID.fromString("00000000-0000-0000-0000-000000001213"),
                "restart-safe:mint:1",
                EconomyTransactionKind.MINT,
                20L
            ),
            2_001L
        );

        assertEquals(EconomyMutationResult.Status.DUPLICATE, sameTransaction.status());
        assertEquals(EconomyMutationResult.Status.DUPLICATE, sameCausalKey.status());
        assertEquals(1, restored.transactions().size());
        assertEquals(funded, sameTransaction.state());
        assertEquals(funded, sameCausalKey.state());
    }
}
