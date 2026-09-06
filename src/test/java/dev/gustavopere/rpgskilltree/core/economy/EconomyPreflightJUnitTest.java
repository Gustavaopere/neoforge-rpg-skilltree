package dev.gustavopere.rpgskilltree.core.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import org.junit.jupiter.api.Test;

final class EconomyPreflightJUnitTest {
    @Test
    void acceptsConsistentMintProjection() {
        ColonyEconomyState state = state();

        EconomyPreflight preflight = new EconomyPreflight(state, 80L, 100L, 50L, 120.0D, 150.0D);

        assertEquals(state, preflight.sourceState());
        assertEquals(80L, preflight.currentEffectiveSupply());
        assertEquals(100L, preflight.projectedEffectiveSupply());
        assertEquals(50L, preflight.economicCapacity());
        assertEquals(120.0D, preflight.currentPriceIndex(), 0.000001D);
        assertEquals(150.0D, preflight.projectedTargetPriceIndex(), 0.000001D);
    }

    @Test
    void rejectsNegativeSupplyValues() {
        ColonyEconomyState state = state();

        assertThrows(IllegalArgumentException.class,
            () -> new EconomyPreflight(state, -1L, 100L, 50L, 120.0D, 150.0D));
        assertThrows(IllegalArgumentException.class,
            () -> new EconomyPreflight(state, 80L, -1L, 50L, 120.0D, 150.0D));
    }

    @Test
    void rejectsProjectionThatReducesSupply() {
        ColonyEconomyState state = state();

        assertThrows(IllegalArgumentException.class,
            () -> new EconomyPreflight(state, 80L, 79L, 50L, 120.0D, 150.0D));
    }

    @Test
    void rejectsNonPositiveEconomicCapacity() {
        ColonyEconomyState state = state();

        assertThrows(IllegalArgumentException.class,
            () -> new EconomyPreflight(state, 80L, 100L, 0L, 120.0D, 150.0D));
    }

    @Test
    void rejectsInvalidPriceIndexes() {
        ColonyEconomyState state = state();

        assertThrows(IllegalArgumentException.class,
            () -> new EconomyPreflight(state, 80L, 100L, 50L, 0.0D, 150.0D));
        assertThrows(IllegalArgumentException.class,
            () -> new EconomyPreflight(state, 80L, 100L, 50L, Double.NaN, 150.0D));
        assertThrows(IllegalArgumentException.class,
            () -> new EconomyPreflight(state, 80L, 100L, 50L, 120.0D, 0.0D));
        assertThrows(IllegalArgumentException.class,
            () -> new EconomyPreflight(state, 80L, 100L, 50L, 120.0D, Double.POSITIVE_INFINITY));
    }

    @Test
    void rejectsProjectionForDifferentSourceSupply() {
        ColonyEconomyState state = state();

        assertThrows(IllegalArgumentException.class,
            () -> new EconomyPreflight(state, 79L, 100L, 50L, 120.0D, 150.0D));
    }

    @Test
    void rejectsProjectionForDifferentSourcePriceIndex() {
        ColonyEconomyState state = state();

        assertThrows(IllegalArgumentException.class,
            () -> new EconomyPreflight(state, 80L, 100L, 50L, 121.0D, 150.0D));
    }

    private static ColonyEconomyState state() {
        return new ColonyEconomyState(
            new EconomyColonyKey(UUID.fromString("00000000-0000-0000-0000-000000000415")),
            100L,
            20L,
            40L,
            10L,
            30L,
            120.0D,
            0.10D,
            50L,
            0L,
            1
        );
    }
}
