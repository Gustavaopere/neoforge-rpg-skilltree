package dev.gustavopere.rpgskilltree.runtime.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

final class ColonyEconomyRuntimeJUnitTest {
    @Test
    void settlementRunsOnlyAtBoundariesAndNeverTwiceForSameTick() {
        ColonyEconomyRuntime runtime = new ColonyEconomyRuntime(1_200L);
        AtomicInteger calls = new AtomicInteger();

        assertFalse(runtime.tryRun(true, 0L, calls::incrementAndGet));
        assertFalse(runtime.tryRun(true, 1_199L, calls::incrementAndGet));
        assertTrue(runtime.tryRun(true, 1_200L, calls::incrementAndGet));
        assertFalse(runtime.tryRun(true, 1_200L, calls::incrementAndGet));
        assertFalse(runtime.tryRun(true, 2_399L, calls::incrementAndGet));
        assertTrue(runtime.tryRun(true, 2_400L, calls::incrementAndGet));
        assertEquals(2, calls.get());
    }

    @Test
    void absentProviderNeverInvokesSettlementPass() {
        ColonyEconomyRuntime runtime = new ColonyEconomyRuntime(20L);
        AtomicInteger calls = new AtomicInteger();

        assertFalse(runtime.tryRun(false, 20L, calls::incrementAndGet));
        assertFalse(runtime.tryRun(false, 40L, calls::incrementAndGet));
        assertEquals(0, calls.get());

        assertTrue(runtime.tryRun(true, 40L, calls::incrementAndGet));
        assertEquals(1, calls.get());
    }

    @Test
    void failedPassDoesNotAdvanceSuccessfulSettlementCursor() {
        ColonyEconomyRuntime runtime = new ColonyEconomyRuntime(20L);

        assertThrows(IllegalStateException.class, () -> runtime.tryRun(true, 20L, () -> {
            throw new IllegalStateException("provider read failed");
        }));

        assertTrue(runtime.tryRun(true, 20L, () -> {}));
        assertEquals(20L, runtime.lastSuccessfulSettlementTick());
    }

    @Test
    void invalidIntervalAndBackwardsTimeAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new ColonyEconomyRuntime(0L));

        ColonyEconomyRuntime runtime = new ColonyEconomyRuntime(20L);
        assertTrue(runtime.tryRun(true, 40L, () -> {}));
        assertThrows(IllegalArgumentException.class, () -> runtime.tryRun(true, 39L, () -> {}));
    }
}
