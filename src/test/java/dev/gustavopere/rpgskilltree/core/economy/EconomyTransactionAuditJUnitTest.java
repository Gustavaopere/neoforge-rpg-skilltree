package dev.gustavopere.rpgskilltree.core.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import org.junit.jupiter.api.Test;

final class EconomyTransactionAuditJUnitTest {
    private static final EconomyColonyKey COLONY = new EconomyColonyKey(
        UUID.fromString("00000000-0000-0000-0000-000000000711")
    );

    @Test
    void mintAuditEntryCarriesColonyCounterpartiesEffectiveSupplyAndMetadata() {
        ColonyEconomyLedger ledger = new ColonyEconomyLedger();
        EconomyMutationResult result = ledger.apply(
            ColonyEconomyState.empty(COLONY),
            new EconomyCommand(
                UUID.fromString("00000000-0000-0000-0000-000000000712"),
                "audit:mint",
                EconomyTransactionKind.MINT,
                20L
            ),
            7_000L
        );

        EconomyTransaction transaction = result.transaction();
        assertEquals(COLONY, transaction.colonyKey());
        assertEquals("monetary_authority", transaction.source());
        assertEquals("treasury", transaction.counterparty());
        assertEquals(20L, transaction.resultingEffectiveSupply());
        assertEquals(20L, transaction.resultingTreasuryBalance());
        assertTrue(transaction.metadata().isEmpty());
    }

    @Test
    void retireAuditEntryRecordsTreasuryAsSource() {
        ColonyEconomyLedger ledger = new ColonyEconomyLedger();
        ColonyEconomyState minted = ledger.apply(
            ColonyEconomyState.empty(COLONY),
            new EconomyCommand(
                UUID.fromString("00000000-0000-0000-0000-000000000713"),
                "audit:fund",
                EconomyTransactionKind.MINT,
                20L
            ),
            7_100L
        ).state();

        EconomyMutationResult result = ledger.apply(
            minted,
            new EconomyCommand(
                UUID.fromString("00000000-0000-0000-0000-000000000714"),
                "audit:retire",
                EconomyTransactionKind.RETIRE,
                5L
            ),
            7_200L
        );

        EconomyTransaction transaction = result.transaction();
        assertEquals(COLONY, transaction.colonyKey());
        assertEquals("treasury", transaction.source());
        assertEquals("monetary_authority", transaction.counterparty());
        assertEquals(15L, transaction.resultingEffectiveSupply());
        assertEquals(15L, transaction.resultingTreasuryBalance());
        assertTrue(transaction.metadata().isEmpty());
    }
}
