package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

final class AtmospherePersistenceTest {
    @Test
    void atmosphericSourceRoundTripsThroughNbt() {
        AtmosphericSource source = new AtmosphericSource(
                UUID.fromString("00000000-0000-0000-0000-000000000301"),
                "minecraft:overworld",
                10.5, 70.0, -4.25,
                48.0,
                new AtmosphereContribution(0.0, 0.0, 0.08, 12.0, 5.0, 3.0, 1.0, 0.1, 4.0, 0.08),
                0.75,
                true);

        assertEquals(source, AtmosphericSource.fromTag(source.toTag()));
    }

    @Test
    void persistencePolicyIsConfigurableAndHonorsPerSourcePersistence() {
        AtmosphericSource persistent = new AtmosphericSource(
                UUID.fromString("00000000-0000-0000-0000-000000000302"),
                "minecraft:overworld", 0, 64, 0, 16,
                AtmosphereContribution.none(), 1.0, true);
        AtmosphericSource transientSource = new AtmosphericSource(
                UUID.fromString("00000000-0000-0000-0000-000000000303"),
                "minecraft:overworld", 0, 64, 0, 16,
                AtmosphereContribution.none(), 1.0, false);

        AtmospherePersistencePolicy enabled = new AtmospherePersistencePolicy(true, 128);
        AtmospherePersistencePolicy disabled = new AtmospherePersistencePolicy(false, 128);
        assertTrue(enabled.shouldPersist(persistent));
        assertFalse(enabled.shouldPersist(transientSource));
        assertFalse(disabled.shouldPersist(persistent));
    }

    @Test
    void directPersistencePolicyCannotExceedActiveRuntimeCapacity() {
        assertEquals(AtmosphereConfig.MAX_ACTIVE_SOURCES,
                new AtmospherePersistencePolicy(true, AtmosphereConfig.MAX_ACTIVE_SOURCES).maxSources());
        assertThrows(IllegalArgumentException.class,
                () -> new AtmospherePersistencePolicy(true, AtmosphereConfig.MAX_ACTIVE_SOURCES + 1));
    }
}
