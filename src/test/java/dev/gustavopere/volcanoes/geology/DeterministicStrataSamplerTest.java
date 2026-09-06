package dev.gustavopere.volcanoes.geology;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class DeterministicStrataSamplerTest {
    private static final int MIN_Y = -64;
    private static final int MAX_Y_EXCLUSIVE = 320;

    @Test
    void sameSeedAndCoordinatesAlwaysProduceSameColumn() {
        DeterministicStrataSampler sampler = new DeterministicStrataSampler(MIN_Y, MAX_Y_EXCLUSIVE);

        GeologyColumn first = sampler.sample(0x51A7A5EEDL, 12_345, -9_876);
        GeologyColumn second = sampler.sample(0x51A7A5EEDL, 12_345, -9_876);

        assertEquals(first, second);
        assertEquals(MIN_Y, first.minY());
        assertEquals(MAX_Y_EXCLUSIVE, first.maxYExclusive());
    }

    @Test
    void distantRegionsProvideGeologicalVariation() {
        DeterministicStrataSampler sampler = new DeterministicStrataSampler(MIN_Y, MAX_Y_EXCLUSIVE);
        long seed = 0xD15EA5EL;
        GeologyColumn origin = sampler.sample(seed, 0, 0);

        boolean foundDifferentColumn = false;
        for (int i = 1; i <= 16; i++) {
            GeologyColumn distant = sampler.sample(seed, i * 32_768, -i * 24_576);
            if (!origin.equals(distant)) {
                foundDifferentColumn = true;
                break;
            }
        }

        assertTrue(foundDifferentColumn, "Low-frequency geology must vary across distant regions");
    }

    @Test
    void validColumnIsOrderedContiguousAndCoversConfiguredVerticalRange() {
        GeologyColumn column = new GeologyColumn(
                MIN_Y,
                MAX_Y_EXCLUSIVE,
                List.of(
                        new Stratum(-64, 48, "volcanoes:granite"),
                        new Stratum(48, 176, "generic"),
                        new Stratum(176, 320, "volcanoes:tuff")
                ));

        assertEquals("volcanoes:granite", column.profileIdAt(-64));
        assertEquals("volcanoes:granite", column.profileIdAt(47));
        assertEquals("generic", column.profileIdAt(48));
        assertEquals("volcanoes:tuff", column.profileIdAt(319));
        assertThrows(IllegalArgumentException.class, () -> column.profileIdAt(320));
    }

    @Test
    void gapsOverlapsAndInvalidStrataAreRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Stratum(10, 10, "generic"));
        assertThrows(IllegalArgumentException.class,
                () -> new Stratum(0, 10, " "));

        assertThrows(IllegalArgumentException.class, () -> new GeologyColumn(
                0,
                30,
                List.of(
                        new Stratum(0, 10, "generic"),
                        new Stratum(11, 30, "volcanoes:granite")
                )));

        assertThrows(IllegalArgumentException.class, () -> new GeologyColumn(
                0,
                30,
                List.of(
                        new Stratum(0, 20, "generic"),
                        new Stratum(10, 30, "volcanoes:granite")
                )));
    }
}
