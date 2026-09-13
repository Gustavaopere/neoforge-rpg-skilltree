package dev.gustavopere.rpgskilltree.core.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import org.junit.jupiter.api.Test;

final class ColonyEconomySettlementJUnitTest {
    private static final EconomyParameters DEFAULTS = EconomyParameters.defaults();

    @Test
    void equilibriumSettlementKeepsIndexAndRecordsCapacity() {
        ColonyEconomyState state = fundedState(key(301), 20L, 20L, 100.0D);
        ColonyEconomicInputs inputs = new ColonyEconomicInputs(5, 8, 0);

        ColonyEconomySnapshot snapshot = ColonyEconomySettlementService.settle(state, inputs, DEFAULTS, 200L);

        assertEquals(20L, snapshot.economicCapacity());
        assertEquals(20L, snapshot.effectiveSupply());
        assertEquals(100.0D, snapshot.targetPriceIndex(), 0.000001D);
        assertEquals(100.0D, snapshot.state().priceIndex(), 0.000001D);
        assertEquals(20L, snapshot.state().currentEconomicCapacity());
        assertEquals(200L, snapshot.state().lastSettlementTick());
    }

    @Test
    void excessSupplyRaisesTargetAndConvergesOnlyByConfiguredStep() {
        ColonyEconomyState state = fundedState(key(302), 100L, 100L, 100.0D);

        ColonyEconomySnapshot snapshot = ColonyEconomySettlementService.settle(
            state,
            new ColonyEconomicInputs(5, 8, 0),
            DEFAULTS,
            200L
        );

        assertTrue(snapshot.targetPriceIndex() > 100.0D);
        assertEquals(105.0D, snapshot.state().priceIndex(), 0.000001D);
        assertTrue(snapshot.periodInflationRate() > 0.0D);
    }

    @Test
    void realCapacityGrowthReducesMonetaryPressureWithoutChangingSupply() {
        ColonyEconomyState state = fundedState(key(303), 100L, 100L, 100.0D);

        ColonyEconomySnapshot small = ColonyEconomySettlementService.settle(
            state,
            new ColonyEconomicInputs(5, 8, 0),
            DEFAULTS,
            200L
        );
        ColonyEconomySnapshot larger = ColonyEconomySettlementService.settle(
            state,
            new ColonyEconomicInputs(25, 48, 2),
            DEFAULTS,
            200L
        );

        assertEquals(small.effectiveSupply(), larger.effectiveSupply());
        assertTrue(larger.economicCapacity() > small.economicCapacity());
        assertTrue(larger.targetPriceIndex() < small.targetPriceIndex());
    }

    @Test
    void mintPreflightIsReadOnlyAndProjectsPostMintPressure() {
        ColonyEconomyState state = fundedState(key(304), 20L, 20L, 100.0D);

        EconomyPreflight preflight = ColonyEconomySettlementService.simulateMint(
            state,
            80L,
            20L,
            DEFAULTS
        );

        assertSame(state, preflight.sourceState());
        assertEquals(20L, preflight.currentEffectiveSupply());
        assertEquals(100L, preflight.projectedEffectiveSupply());
        assertEquals(20L, preflight.economicCapacity());
        assertEquals(100.0D, preflight.currentPriceIndex(), 0.000001D);
        assertTrue(preflight.projectedTargetPriceIndex() > preflight.currentPriceIndex());
        assertEquals(20L, state.effectiveSupply());
        assertEquals(20L, state.treasuryBalance());
    }

    @Test
    void settlementKeepsTwoColoniesIndependent() {
        ColonyEconomyState first = fundedState(key(305), 20L, 20L, 100.0D);
        ColonyEconomyState second = fundedState(key(306), 100L, 100L, 100.0D);
        ColonyEconomicInputs inputs = new ColonyEconomicInputs(5, 8, 0);

        ColonyEconomySnapshot firstResult = ColonyEconomySettlementService.settle(first, inputs, DEFAULTS, 200L);
        ColonyEconomySnapshot secondResult = ColonyEconomySettlementService.settle(second, inputs, DEFAULTS, 200L);

        assertEquals(first.colonyKey(), firstResult.state().colonyKey());
        assertEquals(second.colonyKey(), secondResult.state().colonyKey());
        assertEquals(100.0D, firstResult.state().priceIndex(), 0.000001D);
        assertEquals(105.0D, secondResult.state().priceIndex(), 0.000001D);
    }

    private static ColonyEconomyState fundedState(
        EconomyColonyKey key,
        long issued,
        long treasury,
        double priceIndex
    ) {
        return new ColonyEconomyState(
            key, issued, 0L, treasury, 0L, 0L, priceIndex, 0.10D, 0L, 0L, 1
        );
    }

    private static EconomyColonyKey key(int suffix) {
        return new EconomyColonyKey(UUID.fromString(String.format("00000000-0000-0000-0000-%012d", suffix)));
    }
}
