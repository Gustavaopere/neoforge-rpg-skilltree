package dev.gustavopere.volcanoes.compat.destroy;

import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

final class DestroyPollutionWriterContractTest {
    @Test
    void successfulComponentMutationIsAppliedOnlyOnce() {
        DestroyPollutionApplicationLedger ledger = new DestroyPollutionApplicationLedger(8);
        AtomicInteger mutations = new AtomicInteger();
        UUID emissionId = UUID.fromString("00000000-0000-0000-0000-000000000401");

        assertTrue(ledger.applyOnce(emissionId, "acid_rain", mutations::incrementAndGet));
        assertFalse(ledger.applyOnce(emissionId, "acid_rain", mutations::incrementAndGet));
        assertEquals(1, mutations.get());
        assertEquals(1, ledger.size());
    }

    @Test
    void failedComponentMutationIsNotReservedAndCanRetry() {
        DestroyPollutionApplicationLedger ledger = new DestroyPollutionApplicationLedger(8);
        AtomicInteger attempts = new AtomicInteger();
        UUID emissionId = UUID.fromString("00000000-0000-0000-0000-000000000402");

        assertThrows(IllegalStateException.class, () -> ledger.applyOnce(emissionId, "smog", () -> {
            attempts.incrementAndGet();
            throw new IllegalStateException("simulated host failure");
        }));
        assertEquals(0, ledger.size());

        assertTrue(ledger.applyOnce(emissionId, "smog", attempts::incrementAndGet));
        assertEquals(2, attempts.get());
        assertEquals(1, ledger.size());
    }

    @Test
    void fractionalHostRoundingIsStableForSamePulseAndComponent() {
        UUID emissionId = UUID.fromString("00000000-0000-0000-0000-000000000403");
        int first = DestroyNeoForgePollutionWriter.hostDelta(emissionId, "greenhouse", 0.42);
        int retry = DestroyNeoForgePollutionWriter.hostDelta(emissionId, "greenhouse", 0.42);

        assertEquals(first, retry);
        assertTrue(first == 0 || first == 1);
        assertEquals(1, DestroyNeoForgePollutionWriter.hostDelta(emissionId, "greenhouse", 1.0));
        assertEquals(0, DestroyNeoForgePollutionWriter.hostDelta(emissionId, "greenhouse", 0.0));
    }

    @Test
    void componentLedgerRemainsBounded() {
        DestroyPollutionApplicationLedger ledger = new DestroyPollutionApplicationLedger(2);
        for (int index = 0; index < 5; index++) {
            assertTrue(ledger.applyOnce(new UUID(0L, index + 1L), "acid_rain", () -> {}));
        }
        assertEquals(2, ledger.size());
    }
}
