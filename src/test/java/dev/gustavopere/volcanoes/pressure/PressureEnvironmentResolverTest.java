package dev.gustavopere.volcanoes.pressure;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

final class PressureEnvironmentResolverTest {
    private static final PressureService PRESSURE = PressureService.fallback();

    @Test
    void reliableSealedDryInteriorReplacesExternalPressureWithoutConsumingEquipment() {
        PressureSample external = PRESSURE.sample(1.0, 50.0, 1_000.0);
        AtomicInteger consumes = new AtomicInteger();
        ProtectionSnapshot protection = snapshotWithPressureRating(4.0, () -> {
            consumes.incrementAndGet();
            return true;
        });
        ProtectionUseSession update = protection.beginUpdate();
        EnclosedEnvironment interior = EnclosedEnvironment.protectedDry(1.0, Optional.empty());

        PressureEnvironmentResult result = PressureEnvironmentResolver.resolve(
                external, Optional.of(interior), protection, update);

        assertTrue(result.sealedInterior());
        assertEquals(external.totalExternalAtm(), result.externalPressureAtm(), 1.0e-9);
        assertEquals(1.0, result.experiencedPressureAtm(), 1.0e-9);
        assertEquals(0.0, result.unprotectedOverpressureAtm(), 1.0e-9);
        assertEquals(0.0, result.protectedOverpressureAtm(), 1.0e-9);
        assertEquals(0.0, result.appliedPressureRatingAtm(), 1.0e-9);
        assertEquals(0, consumes.get(), "equipment must not be consumed when a sealed interior already removes external pressure");
    }

    @Test
    void unreliableOrUnsealedInteriorFailsClosedToExternalPressure() {
        PressureSample external = PRESSURE.sample(1.0, 30.0, 1_000.0);
        EnclosedEnvironment unreliable = new EnclosedEnvironment(true, true, false, 1.0, Optional.empty());
        ProtectionSnapshot none = emptySnapshot();

        PressureEnvironmentResult result = PressureEnvironmentResolver.resolve(
                external, Optional.of(unreliable), none, none.beginUpdate());

        assertFalse(result.sealedInterior());
        assertEquals(external.totalExternalAtm(), result.experiencedPressureAtm(), 1.0e-9);
        assertEquals(external.hydrostaticAtm(), result.unprotectedOverpressureAtm(), 1.0e-9);
        assertEquals(external.hydrostaticAtm(), result.protectedOverpressureAtm(), 1.0e-9);
    }

    @Test
    void pressureRatingReducesExposureButNeverChangesPhysicalPressure() {
        PressureSample external = PRESSURE.sample(0.8, 40.0, 1_000.0);
        ProtectionSnapshot protection = snapshotWithPressureRating(2.5, () -> true);

        PressureEnvironmentResult result = PressureEnvironmentResolver.resolve(
                external, Optional.empty(), protection, protection.beginUpdate());

        assertEquals(external.totalExternalAtm(), result.externalPressureAtm(), 1.0e-9);
        assertEquals(external.totalExternalAtm(), result.experiencedPressureAtm(), 1.0e-9);
        assertEquals(external.hydrostaticAtm(), result.unprotectedOverpressureAtm(), 1.0e-9);
        assertEquals(Math.max(0.0, external.hydrostaticAtm() - 2.5), result.protectedOverpressureAtm(), 1.0e-9);
        assertEquals(2.5, result.appliedPressureRatingAtm(), 1.0e-9);
    }

    @Test
    void sufficientPassiveRatingAvoidsStrongerConsumableDebitInRealComposition() {
        PressureSample external = PRESSURE.sample(1.0, 5.0, 1_000.0);
        AtomicInteger consumes = new AtomicInteger();
        ProtectionContribution strongConsumable = ProtectionContribution.consumable(
                "host:active-pressure-suit",
                Map.of(ProtectionCapability.PRESSURE_RATING, 3.0),
                () -> {
                    consumes.incrementAndGet();
                    return true;
                });
        ProtectionContribution sufficientPassive = ProtectionContribution.passive(
                "tag:passive-pressure-shell",
                Map.of(ProtectionCapability.PRESSURE_RATING, 0.75));
        ProtectionSnapshot protection = new EquipmentProtectionResolver(java.util.List.of())
                .resolve(context(), java.util.List.of(strongConsumable, sufficientPassive));

        PressureEnvironmentResult result = PressureEnvironmentResolver.resolve(
                external, Optional.empty(), protection, protection.beginUpdate());

        assertEquals(0, consumes.get(),
                "a passive rating that fully covers the actual overpressure must avoid a consumable debit");
        assertEquals(external.hydrostaticAtm(), result.appliedPressureRatingAtm(), 1.0e-9);
        assertEquals(0.0, result.protectedOverpressureAtm(), 1.0e-9);
    }

    @Test
    void failedConsumableProtectionFailsClosedAndIsDebitedOnlyOncePerUpdate() {
        PressureSample external = PRESSURE.sample(1.0, 40.0, 1_000.0);
        AtomicInteger consumes = new AtomicInteger();
        ProtectionSnapshot protection = snapshotWithPressureRating(3.0, () -> {
            consumes.incrementAndGet();
            return false;
        });
        ProtectionUseSession update = protection.beginUpdate();

        PressureEnvironmentResult first = PressureEnvironmentResolver.resolve(
                external, Optional.empty(), protection, update);
        PressureEnvironmentResult second = PressureEnvironmentResolver.resolve(
                external, Optional.empty(), protection, update);

        assertEquals(0.0, first.appliedPressureRatingAtm(), 1.0e-9);
        assertEquals(external.hydrostaticAtm(), first.protectedOverpressureAtm(), 1.0e-9);
        assertEquals(first, second);
        assertEquals(1, consumes.get(), "one update must attempt one debit per consumable source");
    }

    private static ProtectionSnapshot emptySnapshot() {
        return new EquipmentProtectionResolver(java.util.List.of()).resolve(context());
    }

    private static ProtectionSnapshot snapshotWithPressureRating(
            double rating,
            ProtectionResourceConsumer consumer
    ) {
        ProtectionContribution contribution = new ProtectionContribution(
                "test:pressure_suit",
                Map.of(ProtectionCapability.PRESSURE_RATING, rating),
                Optional.of(consumer));
        EquipmentProtectionAdapter adapter = context -> java.util.List.of(contribution);
        return new EquipmentProtectionResolver(java.util.List.of(adapter)).resolve(context());
    }

    private static EquipmentProtectionContext context() {
        return new EquipmentProtectionContext(UUID.randomUUID(), java.util.List.of(), Optional.empty());
    }
}
