package dev.gustavopere.volcanoes.pressure;

import dev.gustavopere.volcanoes.environment.AtmosphereTags;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class CanonicalRespirationEquipmentIntegrationTest {
    @Test
    void canonicalAtmosphereFilterTagsResolveForArmorAndCuriosWithoutInventedCapabilities() {
        EquipmentProtectionContext context = new EquipmentProtectionContext(
                UUID.randomUUID(),
                List.of(
                        new EquippedItemView("head", "example:ash_mask", Set.of(
                                AtmosphereTags.PARTICULATE_FILTERS.location().toString())),
                        new EquippedItemView("curios:mask", "example:chemical_mask", Set.of(
                                AtmosphereTags.ACID_GAS_FILTERS.location().toString(),
                                AtmosphereTags.TOXIC_GAS_FILTERS.location().toString()))),
                Optional.empty());

        ProtectionSnapshot snapshot = new EquipmentProtectionResolver(
                List.of(CanonicalRespirationProtectionAdapter.create()))
                .resolve(context);

        assertEquals(1.0, snapshot.rating(ProtectionCapability.PARTICULATE_FILTER), 0.0);
        assertEquals(1.0, snapshot.rating(ProtectionCapability.ACID_GAS_FILTER), 0.0);
        assertEquals(1.0, snapshot.rating(ProtectionCapability.TOXIC_GAS_FILTER), 0.0);
        assertEquals(0.0, snapshot.rating(ProtectionCapability.OXYGEN_SUPPLY), 0.0);
        assertEquals(0.0, snapshot.rating(ProtectionCapability.PRESSURE_RATING), 0.0);
        assertEquals(0.0, snapshot.rating(ProtectionCapability.THERMAL_PROTECTION), 0.0);
    }
}
