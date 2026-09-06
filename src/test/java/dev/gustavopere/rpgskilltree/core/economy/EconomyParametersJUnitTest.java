package dev.gustavopere.rpgskilltree.core.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
