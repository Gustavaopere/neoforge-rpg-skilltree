package dev.gustavopere.rpgskilltree.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class TerritoryGridResolverJUnitTest {
    @Test
    void resolvesStableCellsAcrossPositiveAndNegativeCoordinates() {
        long cellSize = 512L;

        assertEquals(TerritoryKey.of("minecraft:overworld", 0L, 0L),
            TerritoryGridResolver.resolve("minecraft:overworld", 0L, 0L, cellSize));
        assertEquals(TerritoryKey.of("minecraft:overworld", 0L, 0L),
            TerritoryGridResolver.resolve("minecraft:overworld", 511L, 511L, cellSize));
        assertEquals(TerritoryKey.of("minecraft:overworld", 1L, 1L),
            TerritoryGridResolver.resolve("minecraft:overworld", 512L, 512L, cellSize));

        assertEquals(TerritoryKey.of("minecraft:overworld", -1L, -1L),
            TerritoryGridResolver.resolve("minecraft:overworld", -1L, -1L, cellSize));
        assertEquals(TerritoryKey.of("minecraft:overworld", -1L, -1L),
            TerritoryGridResolver.resolve("minecraft:overworld", -512L, -512L, cellSize));
        assertEquals(TerritoryKey.of("minecraft:overworld", -2L, -2L),
            TerritoryGridResolver.resolve("minecraft:overworld", -513L, -513L, cellSize));
    }

    @Test
    void supportsTechnicalLongCoordinateLimitsWithoutOverflow() {
        long cellSize = 512L;

        assertEquals(Math.floorDiv(Long.MAX_VALUE, cellSize),
            TerritoryGridResolver.resolve("minecraft:overworld", Long.MAX_VALUE, 0L, cellSize).cellX());
        assertEquals(Math.floorDiv(Long.MIN_VALUE, cellSize),
            TerritoryGridResolver.resolve("minecraft:overworld", Long.MIN_VALUE, 0L, cellSize).cellX());
    }

    @Test
    void cellSizeIsExplicitPolicyAndMustBePositive() {
        assertThrows(IllegalArgumentException.class,
            () -> TerritoryGridResolver.resolve("minecraft:overworld", 0L, 0L, 0L));
        assertThrows(IllegalArgumentException.class,
            () -> TerritoryGridResolver.resolve("minecraft:overworld", 0L, 0L, -1L));
    }
}
