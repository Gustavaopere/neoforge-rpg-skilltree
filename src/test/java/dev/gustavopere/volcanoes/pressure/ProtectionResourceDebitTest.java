package dev.gustavopere.volcanoes.pressure;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ProtectionResourceDebitTest {
    @Test
    void samePhysicalResourceIsDebitedOnceAcrossDifferentWinningContributions() {
        AtomicInteger firstDebits = new AtomicInteger();
        AtomicInteger secondDebits = new AtomicInteger();

        ProtectionContribution oxygen = ProtectionContribution.consumable(
                "host:create_helmet_air",
                "host:create_backtank:player/main",
                Map.of(ProtectionCapability.OXYGEN_SUPPLY, 1.0),
                () -> {
                    firstDebits.incrementAndGet();
                    return true;
                });
        ProtectionContribution pressure = ProtectionContribution.consumable(
                "host:create_suit_air",
                "host:create_backtank:player/main",
                Map.of(ProtectionCapability.PRESSURE_RATING, 2.0),
                () -> {
                    secondDebits.incrementAndGet();
                    return true;
                });

        ProtectionSnapshot snapshot = new EquipmentProtectionResolver(List.of())
                .resolve(emptyContext(), List.of(oxygen, pressure));
        ProtectionUseSession update = snapshot.beginUpdate();

        assertTrue(update.activate(ProtectionCapability.OXYGEN_SUPPLY));
        assertTrue(update.activate(ProtectionCapability.PRESSURE_RATING));
        assertEquals(1, firstDebits.get() + secondDebits.get(),
                "one physical resource key must be consumed at most once per update");
    }

    @Test
    void physicalResourceKeysAndSourceIdsAreCanonicalizedBeforeDebitDeduplication() {
        AtomicInteger firstDebits = new AtomicInteger();
        AtomicInteger secondDebits = new AtomicInteger();
        ProtectionContribution oxygen = ProtectionContribution.consumable(
                "  host:oxygen-source  ",
                "  host:shared-tank  ",
                Map.of(ProtectionCapability.OXYGEN_SUPPLY, 1.0),
                () -> {
                    firstDebits.incrementAndGet();
                    return true;
                });
        ProtectionContribution pressure = ProtectionContribution.consumable(
                "host:pressure-source",
                "host:shared-tank",
                Map.of(ProtectionCapability.PRESSURE_RATING, 2.0),
                () -> {
                    secondDebits.incrementAndGet();
                    return true;
                });

        ProtectionUseSession update = new EquipmentProtectionResolver(List.of())
                .resolve(emptyContext(), List.of(oxygen, pressure))
                .beginUpdate();

        assertTrue(update.activate(ProtectionCapability.OXYGEN_SUPPLY));
        assertTrue(update.activate(ProtectionCapability.PRESSURE_RATING));
        assertEquals(1, firstDebits.get() + secondDebits.get(),
                "whitespace differences must not split one physical resource into two debit keys");
        assertEquals("host:oxygen-source", oxygen.sourceId());
        assertEquals("host:shared-tank", oxygen.resourceDebitKey());
    }

    @Test
    void failedSharedResourceIsAttemptedOnceBeforeIndependentFallback() {
        AtomicInteger firstDebits = new AtomicInteger();
        AtomicInteger duplicateDebits = new AtomicInteger();
        String sharedKey = "host:shared-empty-tank";

        ProtectionContribution strongest = ProtectionContribution.consumable(
                "strongest-shared",
                sharedKey,
                Map.of(ProtectionCapability.PRESSURE_RATING, 3.0),
                () -> {
                    firstDebits.incrementAndGet();
                    return false;
                });
        ProtectionContribution duplicateShared = ProtectionContribution.consumable(
                "weaker-same-tank",
                sharedKey,
                Map.of(ProtectionCapability.PRESSURE_RATING, 2.5),
                () -> {
                    duplicateDebits.incrementAndGet();
                    return true;
                });
        ProtectionContribution independentFallback = ProtectionContribution.passive(
                "independent-fallback",
                Map.of(ProtectionCapability.PRESSURE_RATING, 2.0));

        ProtectionUseSession update = new EquipmentProtectionResolver(List.of())
                .resolve(emptyContext(), List.of(strongest, duplicateShared, independentFallback))
                .beginUpdate();

        assertEquals(2.0, update.activatedRating(ProtectionCapability.PRESSURE_RATING), 1.0e-9);
        assertEquals(1, firstDebits.get());
        assertEquals(0, duplicateDebits.get(),
                "a failed physical resource key must not invoke a second callback for the same tank");
    }

    @Test
    void legacyConsumableFactoryKeepsSourceIdAsDefaultDebitKey() {
        AtomicInteger debits = new AtomicInteger();
        ProtectionContribution legacy = ProtectionContribution.consumable(
                "legacy-source",
                Map.of(ProtectionCapability.OXYGEN_SUPPLY, 1.0),
                () -> {
                    debits.incrementAndGet();
                    return true;
                });

        ProtectionUseSession update = new EquipmentProtectionResolver(List.of())
                .resolve(emptyContext(), List.of(legacy))
                .beginUpdate();

        assertTrue(update.activate(ProtectionCapability.OXYGEN_SUPPLY));
        assertTrue(update.activate(ProtectionCapability.OXYGEN_SUPPLY));
        assertEquals(1, debits.get());
        assertEquals("legacy-source", legacy.resourceDebitKey());
    }

    @Test
    void explicitResourceDebitKeyMustNotBeBlank() {
        assertThrows(IllegalArgumentException.class, () -> ProtectionContribution.consumable(
                "source",
                "   ",
                Map.of(ProtectionCapability.OXYGEN_SUPPLY, 1.0),
                () -> true));
    }

    private static EquipmentProtectionContext emptyContext() {
        return new EquipmentProtectionContext(UUID.randomUUID(), List.of(), Optional.empty());
    }
}
