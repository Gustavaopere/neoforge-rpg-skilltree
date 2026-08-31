package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanicTerrainCreditLedgerTest {
    private static final UUID VOLCANO_A = UUID.fromString("e18bcf5c-2533-48fd-8f6b-4b15851eaa12");
    private static final UUID VOLCANO_B = UUID.fromString("579ec21d-a604-4a36-9086-a2174178a560");

    @Test
    void creditsAreCappedPerVolcanoAndConsumedExactlyOnce() {
        VolcanicTerrainCreditLedger ledger = new VolcanicTerrainCreditLedger(2, 1);

        ledger.add(VOLCANO_A, 1, 1);
        ledger.add(VOLCANO_A, 2, 1);

        assertEquals(2, ledger.bombCredits(VOLCANO_A));
        assertEquals(1, ledger.flowCredits(VOLCANO_A));
        assertTrue(ledger.tryConsumeBomb(VOLCANO_A));
        assertTrue(ledger.tryConsumeBomb(VOLCANO_A));
        assertFalse(ledger.tryConsumeBomb(VOLCANO_A));
        assertTrue(ledger.tryConsumeFlow(VOLCANO_A));
        assertFalse(ledger.tryConsumeFlow(VOLCANO_A));
        assertEquals(0, ledger.size());
    }

    @Test
    void failedMutationAttemptRetainsCreditUntilARealMutationSucceeds() {
        VolcanicTerrainCreditLedger ledger = new VolcanicTerrainCreditLedger(1, 1);
        ledger.add(VOLCANO_A, 1, 1);
        AtomicInteger attempts = new AtomicInteger();

        assertFalse(ledger.trySpendBomb(VOLCANO_A, () -> {
            attempts.incrementAndGet();
            return false;
        }));
        assertEquals(1, ledger.bombCredits(VOLCANO_A));

        assertTrue(ledger.trySpendBomb(VOLCANO_A, () -> {
            attempts.incrementAndGet();
            return true;
        }));
        assertEquals(0, ledger.bombCredits(VOLCANO_A));
        assertEquals(2, attempts.get());

        assertTrue(ledger.trySpendFlow(VOLCANO_A, () -> true));
        assertEquals(0, ledger.flowCredits(VOLCANO_A));
        assertFalse(ledger.trySpendFlow(VOLCANO_A, () -> {
            attempts.incrementAndGet();
            return true;
        }));
        assertEquals(2, attempts.get(), "mutation callback must not run when no credit exists");
    }

    @Test
    void dormantCleanupCanRetireOneVolcanoWithoutTouchingAnother() {
        VolcanicTerrainCreditLedger ledger = new VolcanicTerrainCreditLedger(3, 2);
        ledger.add(VOLCANO_A, 2, 1);
        ledger.add(VOLCANO_B, 1, 2);

        ledger.clear(VOLCANO_A);

        assertEquals(0, ledger.bombCredits(VOLCANO_A));
        assertEquals(0, ledger.flowCredits(VOLCANO_A));
        assertEquals(1, ledger.bombCredits(VOLCANO_B));
        assertEquals(2, ledger.flowCredits(VOLCANO_B));
        assertEquals(1, ledger.size());
    }
}
