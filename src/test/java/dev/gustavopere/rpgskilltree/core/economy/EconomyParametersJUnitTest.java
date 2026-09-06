package dev.gustavopere.rpgskilltree.core.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

final class EconomyParametersJUnitTest {
    @Test
    void defaultsMatchAuditedV1Calibration() {
        EconomyParameters parameters = EconomyParameters.defaults();

        assertEquals(2L, parameters.baseQ());
        assertEquals(2L, parameters.workerWeight());
        assertEquals(1L, parameters.buildingLevelWeight());
        assertEquals(0.10D, parameters.warehouseBonus(), 0.000001D);
        assertEquals(2, parameters.warehouseCap());
        assertEquals(1L, parameters.minQ());
        assertEquals(0.50D, parameters.beta(), 0.000001D);
        assertEquals(50.0D, parameters.minPriceIndex(), 0.000001D);
        assertEquals(500.0D, parameters.maxPriceIndex(), 0.000001D);
        assertEquals(5.0D, parameters.maxStepUp(), 0.000001D);
        assertEquals(3.0D, parameters.maxStepDown(), 0.000001D);
    }

    @Test
    void rejectsNonPositiveQBaselines() {
        assertThrows(IllegalArgumentException.class, () -> parameters(0L, 1L, 1L, 0.10D, 2, 1L,
            0.50D, 50.0D, 500.0D, 5.0D, 3.0D));
        assertThrows(IllegalArgumentException.class, () -> parameters(1L, 1L, 1L, 0.10D, 2, 0L,
            0.50D, 50.0D, 500.0D, 5.0D, 3.0D));
    }

    @Test
    void rejectsNegativeCapacityWeightsAndWarehouseCap() {
        assertThrows(IllegalArgumentException.class, () -> parameters(1L, -1L, 1L, 0.10D, 2, 1L,
            0.50D, 50.0D, 500.0D, 5.0D, 3.0D));
        assertThrows(IllegalArgumentException.class, () -> parameters(1L, 1L, -1L, 0.10D, 2, 1L,
            0.50D, 50.0D, 500.0D, 5.0D, 3.0D));
        assertThrows(IllegalArgumentException.class, () -> parameters(1L, 1L, 1L, 0.10D, -1, 1L,
            0.50D, 50.0D, 500.0D, 5.0D, 3.0D));
    }

    @Test
    void rejectsInvalidWarehouseBonus() {
        assertThrows(IllegalArgumentException.class, () -> parameters(1L, 1L, 1L, -0.01D, 2, 1L,
            0.50D, 50.0D, 500.0D, 5.0D, 3.0D));
        assertThrows(IllegalArgumentException.class, () -> parameters(1L, 1L, 1L, Double.NaN, 2, 1L,
            0.50D, 50.0D, 500.0D, 5.0D, 3.0D));
    }

    @Test
    void rejectsNonFiniteOrNonPositivePriceParameters() {
        assertThrows(IllegalArgumentException.class, () -> parameters(1L, 1L, 1L, 0.10D, 2, 1L,
            0.0D, 50.0D, 500.0D, 5.0D, 3.0D));
        assertThrows(IllegalArgumentException.class, () -> parameters(1L, 1L, 1L, 0.10D, 2, 1L,
            0.50D, Double.NaN, 500.0D, 5.0D, 3.0D));
        assertThrows(IllegalArgumentException.class, () -> parameters(1L, 1L, 1L, 0.10D, 2, 1L,
            0.50D, 50.0D, 0.0D, 5.0D, 3.0D));
        assertThrows(IllegalArgumentException.class, () -> parameters(1L, 1L, 1L, 0.10D, 2, 1L,
            0.50D, 50.0D, 500.0D, Double.POSITIVE_INFINITY, 3.0D));
        assertThrows(IllegalArgumentException.class, () -> parameters(1L, 1L, 1L, 0.10D, 2, 1L,
            0.50D, 50.0D, 500.0D, 5.0D, -1.0D));
    }

    @Test
    void rejectsInvertedPriceIndexBounds() {
        assertThrows(IllegalArgumentException.class, () -> parameters(1L, 1L, 1L, 0.10D, 2, 1L,
            0.50D, 501.0D, 500.0D, 5.0D, 3.0D));
    }

    private static EconomyParameters parameters(
        long baseQ,
        long workerWeight,
        long buildingLevelWeight,
        double warehouseBonus,
        int warehouseCap,
        long minQ,
        double beta,
        double minPriceIndex,
        double maxPriceIndex,
        double maxStepUp,
        double maxStepDown
    ) {
        return new EconomyParameters(
            baseQ,
            workerWeight,
            buildingLevelWeight,
            warehouseBonus,
            warehouseCap,
            minQ,
            beta,
            minPriceIndex,
            maxPriceIndex,
            maxStepUp,
            maxStepDown
        );
    }
}
