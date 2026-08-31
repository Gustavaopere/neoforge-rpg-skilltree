package dev.gustavopere.volcanoes.tectonics;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VoronoiPlateFieldTest {
    @Test
    void sameSeedAndCoordinatesProduceSamePlateSample() {
        PlateField field = new VoronoiPlateField();

        PlateSample first = field.sample(0x7EC70A1CL, 12_345.5, -9_876.25);
        PlateSample second = field.sample(0x7EC70A1CL, 12_345.5, -9_876.25);

        assertEquals(first, second);
        assertEquals(1.0, first.motion().length(), 1.0e-12,
                "plate motion must be normalized");
        assertTrue(Double.isFinite(first.boundaryDistanceBlocks()));
        assertTrue(first.boundaryDistanceBlocks() >= 0.0);
    }

    @Test
    void plateCentersHaveStableInteriorContinuity() {
        PlateField field = new VoronoiPlateField();
        long seed = 0xC01171A17L;
        PlateSample initial = field.sample(seed, 23_000.0, -41_000.0);

        PlateSample atCenter = field.sample(seed, initial.centerX(), initial.centerZ());
        assertEquals(initial.plateId(), atCenter.plateId());

        for (double offsetX : new double[]{-32.0, 0.0, 32.0}) {
            for (double offsetZ : new double[]{-32.0, 0.0, 32.0}) {
                PlateSample nearby = field.sample(
                        seed,
                        initial.centerX() + offsetX,
                        initial.centerZ() + offsetZ);
                assertEquals(initial.plateId(), nearby.plateId(),
                        "small offsets around a generated plate center must remain in that plate");
            }
        }
    }

    @Test
    void distantCoordinatesAndDifferentSeedsProducePlateVariation() {
        PlateField field = new VoronoiPlateField();
        long seed = 0xA11CE5EEDL;
        Set<PlateId> ids = new HashSet<>();

        for (int x = -4; x <= 4; x++) {
            for (int z = -4; z <= 4; z++) {
                ids.add(field.sample(seed, x * 32_768.0, z * 32_768.0).plateId());
            }
        }

        assertTrue(ids.size() >= 16, "large-scale sampling must expose multiple tectonic plates");
        assertNotEquals(
                field.sample(seed, 12_000.0, 12_000.0).plateId(),
                field.sample(seed + 1L, 12_000.0, 12_000.0).plateId(),
                "world seed must participate in plate identity");
    }

    @Test
    void hotspotFieldIsSparseDeterministicAndCanOccurInsidePlates() {
        PlateField field = new VoronoiPlateField();
        long seed = 0x4075A07L;
        boolean foundInactive = false;
        boolean foundInteriorHotspot = false;

        for (int x = -16; x <= 16; x++) {
            for (int z = -16; z <= 16; z++) {
                double worldX = x * 8_192.0;
                double worldZ = z * 8_192.0;
                PlateSample sample = field.sample(seed, worldX, worldZ);
                assertEquals(
                        sample.hotspotIntensity(),
                        field.sample(seed, worldX, worldZ).hotspotIntensity(),
                        0.0);

                foundInactive |= sample.hotspotIntensity() == 0.0;
                foundInteriorHotspot |= sample.hotspotIntensity() > 0.25
                        && sample.boundaryDistanceBlocks() > 512.0;
            }
        }

        assertTrue(foundInactive, "hotspots must be sparse rather than covering the whole world");
        assertTrue(foundInteriorHotspot,
                "hotspots must be independent enough from plate boundaries to allow intraplate volcanism");
    }

    @Test
    void coarseMetadataCacheIsBounded() {
        VoronoiPlateField field = new VoronoiPlateField(32);

        for (int i = 0; i < 128; i++) {
            field.sample(1234L, i * 65_536.0, -i * 49_152.0);
        }

        assertTrue(field.cachedPlateMetadataCount() <= 32);
    }

    @Test
    void rejectsNonFiniteCoordinatesAndInvalidCacheCapacity() {
        PlateField field = new VoronoiPlateField();

        assertThrows(IllegalArgumentException.class, () -> field.sample(1L, Double.NaN, 0.0));
        assertThrows(IllegalArgumentException.class, () -> field.sample(1L, 0.0, Double.POSITIVE_INFINITY));
        assertThrows(IllegalArgumentException.class, () -> new VoronoiPlateField(0));
    }
}
