package dev.gustavopere.rpgskilltree.runtime.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.core.economy.EconomyParameters;
import org.junit.jupiter.api.Test;

final class ColonyEconomyConfigSnapshotJUnitTest {
    @Test
    void defaultsPreserveAuditedEconomyParametersWithoutInventingMonetaryCap() {
        ColonyEconomyConfigSnapshot config = ColonyEconomyConfigSnapshot.defaults();

        assertTrue(config.enabled());
        assertEquals(1_200L, config.settlementIntervalTicks());
        assertEquals(Long.MAX_VALUE, config.maxMutationAmount());
        assertEquals(EconomyParameters.defaults(), config.parameters());
    }

    @Test
    void invalidRuntimeLimitsFailClosed() {
        EconomyParameters parameters = EconomyParameters.defaults();

        assertThrows(IllegalArgumentException.class, () ->
            new ColonyEconomyConfigSnapshot(true, 0L, 1L, parameters));
        assertThrows(IllegalArgumentException.class, () ->
            new ColonyEconomyConfigSnapshot(true, 20L, 0L, parameters));
        assertThrows(NullPointerException.class, () ->
            new ColonyEconomyConfigSnapshot(true, 20L, 1L, null));
    }
}
