package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class AtmosphericSourceOwnershipCapacityTest {
    @Test
    void atmosphereOwnedCapacityCannotConsumeReservedExternalHeadroom() {
        AtmosphericSourceIndex index = new AtmosphericSourceIndex(64, 4_096, 2, 2);

        assertTrue(index.tryRegister(source(1, AtmosphericSourceEvolution.DYNAMIC, 1.0)));
        assertTrue(index.tryRegister(source(2, AtmosphericSourceEvolution.DYNAMIC, 1.0)));
        assertFalse(index.tryRegister(source(3, AtmosphericSourceEvolution.DYNAMIC, 1.0)),
                "owned capacity must stop before consuming EXTERNAL headroom");

        assertTrue(index.tryRegister(source(101, AtmosphericSourceEvolution.EXTERNAL, 1.0)));
        assertTrue(index.tryRegister(source(102, AtmosphericSourceEvolution.EXTERNAL, 1.0)));
        assertFalse(index.tryRegister(source(103, AtmosphericSourceEvolution.EXTERNAL, 1.0)),
                "external sources remain bounded by their own finite capacity");
        assertEquals(4, index.size());
    }

    @Test
    void replacementAndOwnershipTransferRespectDestinationQuotaAtomically() {
        AtmosphericSourceIndex index = new AtmosphericSourceIndex(64, 4_096, 1, 1);
        AtmosphericSource dynamic = source(1, AtmosphericSourceEvolution.DYNAMIC, 1.0);
        AtmosphericSource external = source(101, AtmosphericSourceEvolution.EXTERNAL, 1.0);
        index.register(dynamic);
        index.register(external);

        index.replace(source(1, AtmosphericSourceEvolution.DYNAMIC, 0.5));
        index.replace(source(101, AtmosphericSourceEvolution.EXTERNAL, 0.5));
        assertEquals(0.5, index.source(dynamic.id()).orElseThrow().strength(), 1.0e-9);
        assertEquals(0.5, index.source(external.id()).orElseThrow().strength(), 1.0e-9);

        AtmosphericSource ownershipChange = sourceWithId(
                dynamic.id(), AtmosphericSourceEvolution.EXTERNAL, 0.75);
        assertThrows(IllegalStateException.class, () -> index.replace(ownershipChange),
                "ownership transfer must fail before mutation when the destination quota is full");
        assertEquals(AtmosphericSourceEvolution.DYNAMIC,
                index.source(dynamic.id()).orElseThrow().evolution());
        assertEquals(0.5, index.source(dynamic.id()).orElseThrow().strength(), 1.0e-9);

        assertTrue(index.remove(external.id()));
        index.replace(ownershipChange);
        assertEquals(AtmosphericSourceEvolution.EXTERNAL,
                index.source(dynamic.id()).orElseThrow().evolution());
        assertEquals(0.75, index.source(dynamic.id()).orElseThrow().strength(), 1.0e-9);

        assertTrue(index.tryRegister(source(2, AtmosphericSourceEvolution.DYNAMIC, 1.0)),
                "successful transfer must release the previous ownership quota");
    }

    @Test
    void defaultExternalReserveMatchesMaximumConfiguredOwnedCapacity() {
        assertEquals(AtmosphereConfig.MAX_ACTIVE_SOURCES, AtmosphericSourceIndex.DEFAULT_MAX_EXTERNAL_SOURCES,
                "default runtime must reserve one full configured owned-capacity worth of EXTERNAL headroom");
    }

    private static AtmosphericSource source(long lowBits, AtmosphericSourceEvolution evolution, double strength) {
        return sourceWithId(new UUID(0L, lowBits), evolution, strength);
    }

    private static AtmosphericSource sourceWithId(
            UUID id,
            AtmosphericSourceEvolution evolution,
            double strength
    ) {
        return new AtmosphericSource(
                id,
                "minecraft:overworld",
                Math.abs(id.getLeastSignificantBits()) * 4.0,
                64.0,
                0.0,
                1.0,
                AtmosphereContribution.none(),
                strength,
                false,
                evolution);
    }
}
