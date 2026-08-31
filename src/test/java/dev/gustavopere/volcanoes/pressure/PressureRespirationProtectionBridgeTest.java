package dev.gustavopere.volcanoes.pressure;

import dev.gustavopere.volcanoes.environment.AtmosphereState;
import dev.gustavopere.volcanoes.environment.RespirationProtection;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class PressureRespirationProtectionBridgeTest {
    @Test
    void hazardousAtmosphereActivatesOnlyDemandedCapabilitiesAndSharesOneDebit() {
        AtomicInteger oxygenDebits = new AtomicInteger();
        ProtectionSnapshot snapshot = new EquipmentProtectionResolver(List.of()).resolve(
                new EquipmentProtectionContext(UUID.randomUUID(), List.of(), Optional.empty()),
                List.of(
                        ProtectionContribution.passive("filters", Map.of(
                                ProtectionCapability.PARTICULATE_FILTER, 1.0,
                                ProtectionCapability.ACID_GAS_FILTER, 1.0,
                                ProtectionCapability.TOXIC_GAS_FILTER, 1.0)),
                        ProtectionContribution.consumable(
                                "oxygen",
                                "tank:one",
                                Map.of(ProtectionCapability.OXYGEN_SUPPLY, 0.16),
                                () -> {
                                    oxygenDebits.incrementAndGet();
                                    return true;
                                })));
        ProtectionUseSession session = snapshot.beginUpdate();
        AtmosphereState hazardous = new AtmosphereState(
                0.5, 0.10, 0.0004, 20.0, 50.0, 10.0, 10.0, 0.5, 0.0);

        RespirationProtection first = PressureRespirationProtectionBridge.fromSession(session, hazardous);
        RespirationProtection second = PressureRespirationProtectionBridge.fromSession(session, hazardous);

        assertEquals(1.0, first.particulateFilterEfficiency(), 0.0);
        assertEquals(1.0, first.acidGasFilterEfficiency(), 0.0);
        assertEquals(1.0, first.toxicGasFilterEfficiency(), 0.0);
        assertEquals(0.16, first.oxygenSupplyPartialPressureAtm(), 0.0);
        assertEquals(first, second);
        assertEquals(1, oxygenDebits.get(), "one shared session must debit one physical tank at most once");
    }

    @Test
    void safeAtmosphereDoesNotConsumeAvailableProtectionResources() {
        AtomicInteger oxygenDebits = new AtomicInteger();
        ProtectionSnapshot snapshot = new EquipmentProtectionResolver(List.of()).resolve(
                new EquipmentProtectionContext(UUID.randomUUID(), List.of(), Optional.empty()),
                List.of(ProtectionContribution.consumable(
                        "oxygen",
                        "tank:safe",
                        Map.of(ProtectionCapability.OXYGEN_SUPPLY, 0.16),
                        () -> {
                            oxygenDebits.incrementAndGet();
                            return true;
                        })));

        RespirationProtection protection = PressureRespirationProtectionBridge.fromSession(
                snapshot.beginUpdate(),
                AtmosphereState.standardOverworld());

        assertEquals(RespirationProtection.NONE, protection);
        assertEquals(0, oxygenDebits.get());
    }
}
