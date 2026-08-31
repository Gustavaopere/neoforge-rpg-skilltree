package dev.gustavopere.volcanoes.pressure;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

final class ProtectionUseRegistryContractTest {
    @Test
    void sameEntityAndTickReuseOneSessionAndDebitPhysicalResourceOnceAcrossCapabilities() {
        AtomicInteger debits = new AtomicInteger();
        ProtectionContribution sharedTank = ProtectionContribution.consumable(
                "create:backtank",
                "create:backtank:slot-2",
                Map.of(
                        ProtectionCapability.OXYGEN_SUPPLY, 1.0,
                        ProtectionCapability.PRESSURE_RATING, 1.0),
                () -> {
                    debits.incrementAndGet();
                    return true;
                });
        ProtectionSnapshot snapshot = ProtectionSnapshot.fromCandidates(Map.of(
                ProtectionCapability.OXYGEN_SUPPLY, java.util.List.of(sharedTank),
                ProtectionCapability.PRESSURE_RATING, java.util.List.of(sharedTank)));

        ProtectionUseRegistry registry = new ProtectionUseRegistry(128);
        UUID entity = UUID.randomUUID();
        ProtectionUseSession respiration = registry.session(entity, 120L, () -> snapshot);
        ProtectionUseSession pressure = registry.session(entity, 120L, () -> {
            throw new AssertionError("same-tick snapshot must be reused");
        });

        assertSame(respiration, pressure);
        assertEquals(1.0, respiration.activatedRating(ProtectionCapability.OXYGEN_SUPPLY), 1.0e-12);
        assertEquals(1.0, pressure.activatedRating(ProtectionCapability.PRESSURE_RATING), 1.0e-12);
        assertEquals(1, debits.get());
    }

    @Test
    void nextTickGetsANewTransactionAndClearRemovesEntityState() {
        ProtectionSnapshot passive = new ProtectionSnapshot(Map.of());
        ProtectionUseRegistry registry = new ProtectionUseRegistry(2);
        UUID entity = UUID.randomUUID();

        ProtectionUseSession tickOne = registry.session(entity, 1L, () -> passive);
        ProtectionUseSession tickTwo = registry.session(entity, 2L, () -> passive);
        assertNotSame(tickOne, tickTwo);

        registry.clear(entity);
        ProtectionUseSession afterClear = registry.session(entity, 2L, () -> passive);
        assertNotSame(tickTwo, afterClear);
    }

    @Test
    void registryIsGloballyBoundedAndEvictsOldestLogicalTick() {
        ProtectionSnapshot passive = new ProtectionSnapshot(Map.of());
        ProtectionUseRegistry registry = new ProtectionUseRegistry(2);
        UUID first = UUID.randomUUID();
        UUID second = UUID.randomUUID();
        UUID third = UUID.randomUUID();

        ProtectionUseSession firstSession = registry.session(first, 1L, () -> passive);
        registry.session(second, 2L, () -> passive);
        registry.session(third, 3L, () -> passive);

        assertEquals(2, registry.size());
        ProtectionUseSession reloaded = registry.session(first, 1L, () -> passive);
        assertNotSame(firstSession, reloaded);
        assertEquals(2, registry.size());
    }
}
