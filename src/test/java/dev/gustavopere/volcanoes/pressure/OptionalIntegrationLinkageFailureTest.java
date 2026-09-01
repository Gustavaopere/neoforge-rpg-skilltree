package dev.gustavopere.volcanoes.pressure;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class OptionalIntegrationLinkageFailureTest {
    @Test
    void enclosedProviderLinkageFailureFailsClosedWithoutTryingLowerAuthority() {
        AtomicInteger lowerCalls = new AtomicInteger();
        EnclosedEnvironmentProvider lower = new EnclosedEnvironmentProvider() {
            @Override
            public int priority() {
                return 10;
            }

            @Override
            public Optional<EnclosedEnvironment> resolve(EnclosedEnvironmentQuery query) {
                lowerCalls.incrementAndGet();
                return Optional.of(EnclosedEnvironment.protectedDry(1.0, Optional.empty()));
            }
        };
        EnclosedEnvironmentProvider binaryMismatch = new EnclosedEnvironmentProvider() {
            @Override
            public int priority() {
                return 100;
            }

            @Override
            public Optional<EnclosedEnvironment> resolve(EnclosedEnvironmentQuery query) {
                throw new NoSuchMethodError("simulated optional host API drift");
            }
        };

        EnclosedEnvironmentResolver resolver = new EnclosedEnvironmentResolver(
                List.of(lower, binaryMismatch),
                20,
                32);

        assertTrue(resolver.resolve(enclosedQuery(), 0).isEmpty());
        assertEquals(0, lowerCalls.get(), "binary mismatch in the authoritative provider must fail closed");
    }

    @Test
    void equipmentAdapterLinkageFailureDoesNotSuppressIndependentProtection() {
        EquipmentProtectionAdapter binaryMismatch = context -> {
            throw new NoClassDefFoundError("simulated optional host API drift");
        };
        EquipmentProtectionAdapter independent = context -> List.of(
                ProtectionContribution.passive(
                        "independent-pressure-shell",
                        Map.of(ProtectionCapability.PRESSURE_RATING, 2.0)));

        ProtectionSnapshot snapshot = new EquipmentProtectionResolver(List.of(binaryMismatch, independent))
                .resolve(equipmentContext());

        assertEquals(2.0, snapshot.rating(ProtectionCapability.PRESSURE_RATING), 1.0e-9);
    }

    @Test
    void resourceConsumerLinkageFailureFailsClosedAndIsMemoizedForTheUpdate() {
        AtomicInteger calls = new AtomicInteger();
        ProtectionContribution brokenResource = ProtectionContribution.consumable(
                "host:broken-air-source",
                "host:physical-air-source",
                Map.of(ProtectionCapability.OXYGEN_SUPPLY, 1.0),
                () -> {
                    calls.incrementAndGet();
                    throw new NoSuchMethodError("simulated optional host resource API drift");
                });
        ProtectionUseSession update = new EquipmentProtectionResolver(List.of())
                .resolve(equipmentContext(), List.of(brokenResource))
                .beginUpdate();

        assertFalse(update.activate(ProtectionCapability.OXYGEN_SUPPLY));
        assertFalse(update.activate(ProtectionCapability.OXYGEN_SUPPLY));
        assertEquals(1, calls.get(), "one broken physical resource must not be invoked twice in one update");
    }

    private static EnclosedEnvironmentQuery enclosedQuery() {
        return new EnclosedEnvironmentQuery(
                UUID.randomUUID(),
                Optional.empty(),
                "minecraft:overworld",
                0.0,
                64.0,
                0.0);
    }

    private static EquipmentProtectionContext equipmentContext() {
        return new EquipmentProtectionContext(UUID.randomUUID(), List.of(), Optional.empty());
    }
}
