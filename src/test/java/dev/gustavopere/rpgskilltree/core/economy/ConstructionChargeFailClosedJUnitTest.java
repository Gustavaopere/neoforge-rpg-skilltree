package dev.gustavopere.rpgskilltree.core.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;
import org.junit.jupiter.api.Test;

final class ConstructionChargeFailClosedJUnitTest {
    private static final EconomyColonyKey COLONY = new EconomyColonyKey(
        UUID.fromString("00000000-0000-0000-0000-000000001701")
    );

    @Test
    void constructionChargeAndRefundRemainNonExecutableInV1() {
        ColonyEconomyLedger ledger = new ColonyEconomyLedger();
        ColonyEconomyState state = ColonyEconomyState.empty(COLONY);

        EconomyMutationResult charge = ledger.apply(
            state,
            new EconomyCommand(
                UUID.fromString("00000000-0000-0000-0000-000000001702"),
                "construction:blocked:charge",
                EconomyTransactionKind.CONSTRUCTION_CHARGE,
                5L
            ),
            100L
        );
        EconomyMutationResult refund = ledger.apply(
            state,
            new EconomyCommand(
                UUID.fromString("00000000-0000-0000-0000-000000001703"),
                "construction:blocked:refund",
                EconomyTransactionKind.REFUND,
                5L
            ),
            101L
        );

        assertEquals(EconomyMutationResult.Status.UNSUPPORTED_KIND, charge.status());
        assertEquals(EconomyMutationResult.Status.UNSUPPORTED_KIND, refund.status());
        assertEquals(state, charge.state());
        assertEquals(state, refund.state());
        assertNull(charge.transaction());
        assertNull(refund.transaction());
        assertEquals(0, ledger.transactions().size());
    }
}
