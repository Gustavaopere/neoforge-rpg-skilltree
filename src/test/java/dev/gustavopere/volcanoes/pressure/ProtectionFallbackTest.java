package dev.gustavopere.volcanoes.pressure;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ProtectionFallbackTest {
    @Test
    void failedStrongerConsumableFallsBackToWeakerIndependentProtection() {
        AtomicInteger failedDebits = new AtomicInteger();
        ProtectionContribution depletedStrongSuit = ProtectionContribution.consumable(
                "depleted-strong-suit",
                Map.of(ProtectionCapability.PRESSURE_RATING, 3.0),
                () -> {
                    failedDebits.incrementAndGet();
                    return false;
                });
        ProtectionContribution passiveFallback = ProtectionContribution.passive(
                "passive-fallback-suit",
                Map.of(ProtectionCapability.PRESSURE_RATING, 2.0));
        EquipmentProtectionAdapter adapter = context -> List.of(depletedStrongSuit, passiveFallback);
        EquipmentProtectionResolver resolver = new EquipmentProtectionResolver(List.of(adapter));

        ProtectionUseSession update = resolver.resolve(context()).beginUpdate();

        assertEquals(2.0, update.activatedRating(ProtectionCapability.PRESSURE_RATING), 1.0e-9);
        assertTrue(update.activate(ProtectionCapability.PRESSURE_RATING));
        assertEquals(1, failedDebits.get(), "failed stronger resource must be attempted at most once per update");
    }

    @Test
    void pressureEnvironmentUsesTheRatingThatActuallyActivated() {
        ProtectionContribution depletedStrongSuit = ProtectionContribution.consumable(
                "depleted-strong-suit",
                Map.of(ProtectionCapability.PRESSURE_RATING, 3.0),
                () -> false);
        ProtectionContribution passiveFallback = ProtectionContribution.passive(
                "passive-fallback-suit",
                Map.of(ProtectionCapability.PRESSURE_RATING, 2.0));
        ProtectionSnapshot snapshot = new EquipmentProtectionResolver(List.of(
                context -> List.of(depletedStrongSuit, passiveFallback)))
                .resolve(context());

        PressureEnvironmentResult result = PressureEnvironmentResolver.resolve(
                new PressureSample(1.0, 3.0),
                Optional.empty(),
                snapshot,
                snapshot.beginUpdate());

        assertEquals(2.0, result.appliedPressureRatingAtm(), 1.0e-9);
        assertEquals(1.0, result.protectedOverpressureAtm(), 1.0e-9);
    }

    @Test
    void sufficientPassiveProtectionAvoidsPointlessConsumableDebit() {
        AtomicInteger debits = new AtomicInteger();
        ProtectionContribution strongerConsumable = ProtectionContribution.consumable(
                "strong-consumable",
                Map.of(ProtectionCapability.PRESSURE_RATING, 3.0),
                () -> {
                    debits.incrementAndGet();
                    return true;
                });
        ProtectionContribution sufficientPassive = ProtectionContribution.passive(
                "sufficient-passive",
                Map.of(ProtectionCapability.PRESSURE_RATING, 2.0));
        ProtectionSnapshot snapshot = new EquipmentProtectionResolver(List.of(
                context -> List.of(strongerConsumable, sufficientPassive)))
                .resolve(context());

        PressureEnvironmentResult result = PressureEnvironmentResolver.resolve(
                new PressureSample(1.0, 1.0),
                Optional.empty(),
                snapshot,
                snapshot.beginUpdate());

        assertEquals(1.0, result.appliedPressureRatingAtm(), 1.0e-9);
        assertEquals(0.0, result.protectedOverpressureAtm(), 1.0e-9);
        assertEquals(0, debits.get(), "passive protection that fully covers the need must avoid resource consumption");
    }

    private static EquipmentProtectionContext context() {
        return new EquipmentProtectionContext(UUID.randomUUID(), List.of(), Optional.empty());
    }
}
