package dev.gustavopere.rpgskilltree.core.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.UUID;
import org.junit.jupiter.api.Test;

final class ColonyEconomyLedgerJUnitTest {
    private static final EconomyColonyKey COLONY = new EconomyColonyKey(
        UUID.fromString("00000000-0000-0000-0000-000000000111")
    );

    @Test
    void mintCreatesSupplyAndCreditsTreasuryExactlyOnce() {
        ColonyEconomyLedger ledger = new ColonyEconomyLedger();
        ColonyEconomyState initial = ColonyEconomyState.empty(COLONY);
        EconomyCommand command = command("00000000-0000-0000-0000-000000000201", "mint:period:1", EconomyTransactionKind.MINT, 20L);

        EconomyMutationResult result = ledger.apply(initial, command, 100L);

        assertEquals(EconomyMutationResult.Status.APPLIED, result.status());
        assertEquals(20L, result.state().issuedSupply());
        assertEquals(20L, result.state().treasuryBalance());
        assertEquals(20L, result.state().effectiveSupply());
        assertEquals(1, ledger.transactions().size());
    }

    @Test
    void retireConsumesTreasuryAndReducesEffectiveSupply() {
        ColonyEconomyLedger ledger = new ColonyEconomyLedger();
        ColonyEconomyState minted = ledger.apply(
            ColonyEconomyState.empty(COLONY),
            command("00000000-0000-0000-0000-000000000202", "mint:period:2", EconomyTransactionKind.MINT, 20L),
            100L
        ).state();

        EconomyMutationResult result = ledger.apply(
            minted,
            command("00000000-0000-0000-0000-000000000203", "retire:period:2", EconomyTransactionKind.RETIRE, 5L),
            120L
        );

        assertEquals(EconomyMutationResult.Status.APPLIED, result.status());
        assertEquals(20L, result.state().issuedSupply());
        assertEquals(5L, result.state().retiredSupply());
        assertEquals(15L, result.state().treasuryBalance());
        assertEquals(15L, result.state().effectiveSupply());
    }

    @Test
    void duplicateTransactionIdIsRejectedWithoutMutation() {
        ColonyEconomyLedger ledger = new ColonyEconomyLedger();
        ColonyEconomyState initial = ColonyEconomyState.empty(COLONY);
        EconomyCommand command = command("00000000-0000-0000-0000-000000000204", "mint:period:3", EconomyTransactionKind.MINT, 7L);
        ColonyEconomyState applied = ledger.apply(initial, command, 100L).state();

        EconomyMutationResult duplicate = ledger.apply(applied, command, 101L);

        assertEquals(EconomyMutationResult.Status.DUPLICATE, duplicate.status());
        assertSame(applied, duplicate.state());
        assertEquals(1, ledger.transactions().size());
    }

    @Test
    void duplicateCausalKeyIsRejectedEvenWithNewTransactionId() {
        ColonyEconomyLedger ledger = new ColonyEconomyLedger();
        ColonyEconomyState initial = ColonyEconomyState.empty(COLONY);
        ColonyEconomyState applied = ledger.apply(
            initial,
            command("00000000-0000-0000-0000-000000000205", "mint:period:4", EconomyTransactionKind.MINT, 7L),
            100L
        ).state();

        EconomyMutationResult duplicate = ledger.apply(
            applied,
            command("00000000-0000-0000-0000-000000000206", "mint:period:4", EconomyTransactionKind.MINT, 7L),
            101L
        );

        assertEquals(EconomyMutationResult.Status.DUPLICATE, duplicate.status());
        assertSame(applied, duplicate.state());
        assertEquals(1, ledger.transactions().size());
    }

    @Test
    void failedCommandDoesNotConsumeReplayIdentity() {
        ColonyEconomyLedger ledger = new ColonyEconomyLedger();
        EconomyCommand retire = command(
            "00000000-0000-0000-0000-000000000207",
            "retire:retryable",
            EconomyTransactionKind.RETIRE,
            5L
        );

        EconomyMutationResult failed = ledger.apply(ColonyEconomyState.empty(COLONY), retire, 100L);
        assertEquals(EconomyMutationResult.Status.INSUFFICIENT_TREASURY, failed.status());
        assertEquals(0, ledger.transactions().size());

        ColonyEconomyState funded = ledger.apply(
            ColonyEconomyState.empty(COLONY),
            command("00000000-0000-0000-0000-000000000208", "mint:fund-retry", EconomyTransactionKind.MINT, 10L),
            101L
        ).state();
        EconomyMutationResult retried = ledger.apply(funded, retire, 102L);

        assertEquals(EconomyMutationResult.Status.APPLIED, retried.status());
        assertEquals(5L, retried.state().treasuryBalance());
    }

    @Test
    void invalidAmountAndOverflowFailWithoutMutation() {
        ColonyEconomyLedger ledger = new ColonyEconomyLedger();
        ColonyEconomyState initial = ColonyEconomyState.empty(COLONY);

        EconomyMutationResult invalid = ledger.apply(
            initial,
            command("00000000-0000-0000-0000-000000000209", "mint:zero", EconomyTransactionKind.MINT, 0L),
            100L
        );
        assertEquals(EconomyMutationResult.Status.INVALID_AMOUNT, invalid.status());
        assertSame(initial, invalid.state());

        ColonyEconomyState nearOverflow = new ColonyEconomyState(
            COLONY, Long.MAX_VALUE, 0L, Long.MAX_VALUE, 0L, 0L, 100.0D, 0.10D, 1L, 0L, 1
        );
        EconomyMutationResult overflow = ledger.apply(
            nearOverflow,
            command("00000000-0000-0000-0000-000000000210", "mint:overflow", EconomyTransactionKind.MINT, 1L),
            101L
        );
        assertEquals(EconomyMutationResult.Status.OVERFLOW, overflow.status());
        assertSame(nearOverflow, overflow.state());
    }

    @Test
    void unsupportedKindsRemainFailClosed() {
        ColonyEconomyLedger ledger = new ColonyEconomyLedger();
        ColonyEconomyState initial = ColonyEconomyState.empty(COLONY);

        for (EconomyTransactionKind kind : new EconomyTransactionKind[] {
            EconomyTransactionKind.ADMIN_ADJUSTMENT,
            EconomyTransactionKind.TAX,
            EconomyTransactionKind.CONSTRUCTION_CHARGE,
            EconomyTransactionKind.REFUND,
            EconomyTransactionKind.TREASURY_DEPOSIT,
            EconomyTransactionKind.TREASURY_WITHDRAWAL
        }) {
            EconomyMutationResult result = ledger.apply(
                initial,
                new EconomyCommand(UUID.randomUUID(), "unsupported:" + kind.name(), kind, 1L),
                100L
            );
            assertEquals(EconomyMutationResult.Status.UNSUPPORTED_KIND, result.status());
            assertSame(initial, result.state());
        }
        assertEquals(0, ledger.transactions().size());
    }

    private static EconomyCommand command(String transactionId, String causalKey, EconomyTransactionKind kind, long amount) {
        return new EconomyCommand(UUID.fromString(transactionId), causalKey, kind, amount);
    }
}
