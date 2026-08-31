package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

final class GeothermalPulseHistorySinkTest {
    @Test
    void removingPersistentSourcePrunesHistoricalPulseStateWithoutScanning() {
        UUID sourceId = UUID.fromString("0f81a870-6b08-4bd0-87fa-6d19f62a482a");
        Map<UUID, Long> history = new HashMap<>();
        history.put(sourceId, 1_234L);

        GeothermalPulseHistorySink sink = new GeothermalPulseHistorySink(history);
        sink.upsert(source(sourceId));
        assertEquals(1_234L, history.get(sourceId));

        sink.remove(sourceId);
        assertFalse(history.containsKey(sourceId),
                "removed geothermal sources must not remain forever in transient pulse history");
    }

    private static GeothermalSource source(UUID id) {
        GeothermalFeatureProfile profile = GeothermalFeatureProfile.defaults(GeothermalFeatureType.GEYSER);
        return new GeothermalSource(
                id,
                GeothermalFeatureType.GEYSER,
                new BlockPos(8, 80, 8),
                profile.radiusBlocks(),
                profile.heatSeverity(),
                profile.gasSeverity());
    }
}
