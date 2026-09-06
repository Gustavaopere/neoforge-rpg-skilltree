package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class PollutionEmissionSpatialContractTest {
    @Test
    void emissionCarriesExplicitLoaderNeutralSpatialContextForChunkAndLevelAuthorities() {
        PollutionEmission emission = new PollutionEmission(
                UUID.fromString("00000000-0000-0000-0000-000000000211"),
                "minecraft:overworld",
                12.5,
                72.0,
                -33.25,
                new PollutionLoad(4.0, 3.0, 2.0, 1.0, 0.5));

        assertEquals("minecraft:overworld", emission.dimensionId());
        assertEquals(12.5, emission.x(), 0.0);
        assertEquals(72.0, emission.y(), 0.0);
        assertEquals(-33.25, emission.z(), 0.0);
    }

    @Test
    void emissionRejectsMissingOrNonFiniteSpatialContext() {
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000212");
        PollutionLoad load = PollutionLoad.none();

        assertThrows(NullPointerException.class,
                () -> new PollutionEmission(id, null, 0.0, 64.0, 0.0, load));
        assertThrows(IllegalArgumentException.class,
                () -> new PollutionEmission(id, " ", 0.0, 64.0, 0.0, load));
        assertThrows(IllegalArgumentException.class,
                () -> new PollutionEmission(id, "minecraft:overworld", Double.NaN, 64.0, 0.0, load));
        assertThrows(IllegalArgumentException.class,
                () -> new PollutionEmission(id, "minecraft:overworld", 0.0, Double.POSITIVE_INFINITY, 0.0, load));
    }
}
