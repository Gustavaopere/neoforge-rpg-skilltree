package dev.gustavopere.rpgskilltree.core.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class EconomyMathJUnitTest {
    private static final EconomyParameters DEFAULTS = EconomyParameters.defaults();

    @Test
    void fiveWorkersAndEightBuildingLevelsProduceAcceptanceCapacityTwenty() {
        ColonyEconomicInputs inputs = new ColonyEconomicInputs(5, 8, 0);

        assertEquals(20L, EconomyMath.capacity(inputs, DEFAULTS));
    }

    @Test
    void warehouseMultiplierIsBoundedByConfiguredCap() {
        ColonyEconomicInputs atCap = new ColonyEconomicInputs(5, 8, 2);
        ColonyEconomicInputs aboveCap = new ColonyEconomicInputs(5, 8, 99);

        assertEquals(EconomyMath.capacity(atCap, DEFAULTS), EconomyMath.capacity(aboveCap, DEFAULTS));
        assertEquals(24L, EconomyMath.capacity(atCap, DEFAULTS));
    }

    @Test
    void moneyAtEconomicCapacityTargetsIndexOneHundred() {
        assertEquals(100.0D, EconomyMath.targetPriceIndex(20L, 20L, DEFAULTS), 0.000001D);
    }

    @Test
    void excessMoneyRaisesTargetPriceIndex() {
        assertTrue(EconomyMath.targetPriceIndex(100L, 20L, DEFAULTS) > 100.0D);
    }

    @Test
    void scarceMoneyLowersTargetWithoutCrossingFloor() {
        double target = EconomyMath.targetPriceIndex(5L, 20L, DEFAULTS);

        assertTrue(target < 100.0D);
        assertTrue(target >= DEFAULTS.minPriceIndex());
    }

    @Test
    void zeroSupplyUsesConfiguredPriceFloor() {
        assertEquals(DEFAULTS.minPriceIndex(), EconomyMath.targetPriceIndex(0L, 20L, DEFAULTS));
    }

    @Test
    void convergenceUsesAsymmetricStepLimits() {
        assertEquals(105.0D, EconomyMath.convergePriceIndex(100.0D, 200.0D, DEFAULTS), 0.000001D);
        assertEquals(97.0D, EconomyMath.convergePriceIndex(100.0D, 50.0D, DEFAULTS), 0.000001D);
    }

    @Test
    void positivePriceNeverRoundsToZero() {
        assertEquals(1L, EconomyMath.nominalPrice(1L, DEFAULTS.minPriceIndex()));
    }

    @Test
    void invalidEconomicInputsFailClosed() {
        assertThrows(IllegalArgumentException.class, () -> new ColonyEconomicInputs(-1, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> EconomyMath.targetPriceIndex(-1L, 20L, DEFAULTS));
        assertThrows(IllegalArgumentException.class, () -> EconomyMath.targetPriceIndex(20L, 0L, DEFAULTS));
        assertThrows(IllegalArgumentException.class, () -> EconomyMath.nominalPrice(-1L, 100.0D));
    }
}
