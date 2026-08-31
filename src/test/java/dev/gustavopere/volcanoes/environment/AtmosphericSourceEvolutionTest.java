package dev.gustavopere.volcanoes.environment;

import net.minecraft.nbt.CompoundTag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class AtmosphericSourceEvolutionTest {
    @Test
    void externallyManagedSourceRemainsStableAndConsumesNoDynamicUpdateBudget() {
        AtmosphereField field = field();
        AtmosphericSource source = source(
                UUID.fromString("00000000-0000-0000-0000-000000000801"),
                1.0,
                AtmosphericSourceEvolution.EXTERNAL);

        field.upsert(source);

        assertEquals(0, field.tick(64));
        assertEquals(source, field.source(source.id()).orElseThrow());
    }

    @Test
    void switchingDynamicSourceToExternalClearsPreviouslyQueuedWork() {
        AtmosphereField field = field();
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000802");
        field.upsert(source(id, 1.0, AtmosphericSourceEvolution.DYNAMIC));
        AtmosphericSource external = source(id, 0.8, AtmosphericSourceEvolution.EXTERNAL);

        field.upsert(external);

        assertEquals(0, field.tick(64));
        assertEquals(external, field.source(id).orElseThrow());
    }

    @Test
    void switchingExternalSourceToDynamicSchedulesBoundedEvolution() {
        AtmosphereField field = field();
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000803");
        field.upsert(source(id, 1.0, AtmosphericSourceEvolution.EXTERNAL));
        field.upsert(source(id, 1.0, AtmosphericSourceEvolution.DYNAMIC));

        assertEquals(1, field.tick(1));
        assertEquals(0.50, field.source(id).orElseThrow().strength(), 1.0e-9);
    }

    @Test
    void evolutionModeRoundTripsAndLegacyNbtDefaultsToDynamic() {
        AtmosphericSource external = source(
                UUID.fromString("00000000-0000-0000-0000-000000000804"),
                1.0,
                AtmosphericSourceEvolution.EXTERNAL);
        assertEquals(external, AtmosphericSource.fromTag(external.toTag()));

        CompoundTag legacy = source(
                UUID.fromString("00000000-0000-0000-0000-000000000805"),
                1.0,
                AtmosphericSourceEvolution.DYNAMIC).toTag();
        legacy.remove("evolution_mode");

        assertEquals(AtmosphericSourceEvolution.DYNAMIC, AtmosphericSource.fromTag(legacy).evolution());
    }

    @Test
    void externalLifecycleCannotAlsoBePersistedByAtmosphere() {
        assertThrows(IllegalArgumentException.class, () -> new AtmosphericSource(
                UUID.fromString("00000000-0000-0000-0000-000000000806"),
                "minecraft:overworld",
                0.0,
                64.0,
                0.0,
                16.0,
                VolcanicSourceProfiles.geothermalToxic(10.0).contribution(),
                1.0,
                true,
                AtmosphericSourceEvolution.EXTERNAL));
    }

    private static AtmosphereField field() {
        return new AtmosphereField(
                AtmosphereBaselineProvider.standard(),
                new AtmosphericSourceIndex(64),
                new AtmosphereDynamics(0.50, 2.0, 0.01),
                AtmosphereTransportProvider.stillAir(),
                AtmosphericSourceLifecycleSink.none());
    }

    private static AtmosphericSource source(UUID id, double strength, AtmosphericSourceEvolution evolution) {
        return new AtmosphericSource(
                id,
                "minecraft:overworld",
                0.0,
                64.0,
                0.0,
                16.0,
                VolcanicSourceProfiles.geothermalToxic(10.0).contribution(),
                strength,
                evolution == AtmosphericSourceEvolution.DYNAMIC,
                evolution);
    }
}
