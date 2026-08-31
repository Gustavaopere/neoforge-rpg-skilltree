package dev.gustavopere.volcanoes.pressure;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

final class TagProtectionAdapterTest {
    @Test
    void mappedEquipmentTagsProducePassiveModularCapabilities() {
        TagProtectionAdapter adapter = new TagProtectionAdapter(Map.of(
                "volcanoes:pressure/particulate_filter", Map.of(ProtectionCapability.PARTICULATE_FILTER, 1.0),
                "volcanoes:pressure/pressure_suit", Map.of(ProtectionCapability.PRESSURE_RATING, 3.0)));
        EquipmentProtectionContext context = new EquipmentProtectionContext(
                UUID.randomUUID(),
                List.of(
                        new EquippedItemView("head", "example:mask", Set.of("volcanoes:pressure/particulate_filter")),
                        new EquippedItemView("chest", "example:suit", Set.of("volcanoes:pressure/pressure_suit")),
                        new EquippedItemView("curio:ring", "example:ring", Set.of("example:unrelated"))),
                Optional.empty());

        ProtectionSnapshot snapshot = new EquipmentProtectionResolver(List.of(adapter)).resolve(context);

        assertEquals(1.0, snapshot.rating(ProtectionCapability.PARTICULATE_FILTER), 1.0e-9);
        assertEquals(3.0, snapshot.rating(ProtectionCapability.PRESSURE_RATING), 1.0e-9);
        assertEquals(0.0, snapshot.rating(ProtectionCapability.OXYGEN_SUPPLY), 1.0e-9);
    }

    @Test
    void invalidTagMappingsAreRejectedBeforeRuntimeResolution() {
        assertThrows(IllegalArgumentException.class, () ->
                new TagProtectionAdapter(Map.of("", Map.of(ProtectionCapability.PRESSURE_RATING, 1.0))));
        assertThrows(IllegalArgumentException.class, () ->
                new TagProtectionAdapter(Map.of(
                        "volcanoes:pressure/bad", Map.of(ProtectionCapability.PRESSURE_RATING, -1.0))));
    }
}
