package dev.gustavopere.rpgskilltree.core.economy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import org.junit.jupiter.api.Test;

final class ColonyEconomyStateInvariantJUnitTest {
    private static final EconomyColonyKey COLONY = new EconomyColonyKey(
        UUID.fromString("00000000-0000-0000-0000-000000001801")
    );

    @Test
    void everyEffectiveUnitMustBeAssignedToExactlyOneMonetaryBucket() {
        assertDoesNotThrow(() -> new ColonyEconomyState(
            COLONY, 100L, 0L, 50L, 20L, 30L, 100.0D, 0.10D, 1L, 0L, 1
        ));

        assertThrows(IllegalArgumentException.class, () -> new ColonyEconomyState(
            COLONY, 100L, 0L, 50L, 0L, 0L, 100.0D, 0.10D, 1L, 0L, 1
        ));
        assertThrows(IllegalArgumentException.class, () -> new ColonyEconomyState(
            COLONY, 100L, 0L, 101L, 0L, 0L, 100.0D, 0.10D, 1L, 0L, 1
        ));
    }
}
