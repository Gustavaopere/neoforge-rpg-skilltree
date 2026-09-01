package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.volcano.AshPlumeEmission;
import dev.gustavopere.volcanoes.volcano.EruptionPhase;
import dev.gustavopere.volcanoes.volcano.GeothermalFeatureType;
import dev.gustavopere.volcanoes.volcano.GeothermalSource;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class AtmosphereOwnershipHeadroomIntegrationTest {
    @Test
    void saturatedOwnedQuotaStillAdmitsConcurrentAshAndGeothermalProjections() {
        AtmosphereField field = field(new AtmosphericSourceIndex(64, 4_096, 1, 2));

        assertEquals(AtmosphericSourceAdmission.ACCEPTED, field.tryUpsert(dynamicSource(new UUID(0L, 1L))));
        assertEquals(AtmosphericSourceAdmission.REJECTED_CAPACITY,
                field.tryUpsert(dynamicSource(new UUID(0L, 2L))),
                "Atmosphere-owned saturation must stop at its quota");

        UUID volcanoId = UUID.fromString("00000000-0000-0000-0000-000000000901");
        AshPlumeEmission ash = new AshPlumeEmission(
                AshPlumeEmission.sourceIdFor(volcanoId),
                volcanoId,
                new BlockPos(32, 80, 32),
                EruptionPhase.SUSTAINED,
                1.0,
                0.8,
                0.6,
                24.0,
                200L);
        AtmosphericSource ashSource = AshAtmosphereProjection.project(
                "minecraft:overworld", ash, AshAtmosphereProjectionPolicy.defaults());

        GeothermalSource geothermal = new GeothermalSource(
                UUID.fromString("00000000-0000-0000-0000-000000000902"),
                GeothermalFeatureType.FUMAROLE,
                new BlockPos(-32, 72, -32),
                12,
                0.4,
                0.7);
        AtmosphericSource geothermalSource = GeothermalAtmosphereProjection.project(
                "minecraft:overworld", geothermal, GeothermalAtmosphereProjectionPolicy.defaults()).orElseThrow();

        assertEquals(AtmosphericSourceAdmission.ACCEPTED, field.tryUpsert(ashSource));
        assertEquals(AtmosphericSourceAdmission.ACCEPTED, field.tryUpsert(geothermalSource));
        assertEquals(3, field.sourceCount());

        AshPlumeEmission updatedAsh = new AshPlumeEmission(
                ash.sourceId(),
                ash.volcanoId(),
                ash.source(),
                EruptionPhase.WANING,
                0.5,
                0.4,
                0.3,
                16.0,
                100L);
        assertEquals(AtmosphericSourceAdmission.ACCEPTED, field.tryUpsert(AshAtmosphereProjection.project(
                "minecraft:overworld", updatedAsh, AshAtmosphereProjectionPolicy.defaults())));
        assertEquals(3, field.sourceCount(),
                "stable EXTERNAL replacement must not consume a second headroom slot");
    }

    @Test
    void maximumConfiguredPersistenceCannotConsumeDefaultExternalReserve() {
        AtmospherePersistencePolicy persistence = AtmosphereConfig.persistencePolicy(
                true,
                AtmosphereConfig.MAX_ACTIVE_SOURCES);
        assertEquals(AtmosphericSourceIndex.DEFAULT_MAX_SOURCES, persistence.maxSources());

        AtmosphericSourceIndex index = new AtmosphericSourceIndex(64);
        for (int i = 0; i < persistence.maxSources(); i++) {
            assertTrue(index.tryRegister(dynamicSource(new UUID(1L, i + 1L))),
                    "every source accepted by the maximum persistence policy must fit the owned quota");
        }
        assertFalse(index.tryRegister(dynamicSource(new UUID(1L, persistence.maxSources() + 1L))));

        AtmosphericSource external = externalSource(new UUID(2L, 1L));
        assertTrue(index.tryRegister(external),
                "the default EXTERNAL reserve must remain available after the owned quota is completely full");
        assertEquals(persistence.maxSources() + 1, index.size());
    }

    private static AtmosphereField field(AtmosphericSourceIndex index) {
        return new AtmosphereField(
                AtmosphereBaselineProvider.standard(),
                index,
                AtmosphereDynamics.defaults(),
                AtmosphereTransportProvider.stillAir(),
                AtmosphericSourceLifecycleSink.none());
    }

    private static AtmosphericSource dynamicSource(UUID id) {
        return new AtmosphericSource(
                id,
                "minecraft:overworld",
                0.0,
                64.0,
                0.0,
                0.0,
                AtmosphereContribution.none(),
                1.0,
                true,
                AtmosphericSourceEvolution.DYNAMIC);
    }

    private static AtmosphericSource externalSource(UUID id) {
        return new AtmosphericSource(
                id,
                "minecraft:overworld",
                0.0,
                64.0,
                0.0,
                0.0,
                AtmosphereContribution.none(),
                1.0,
                false,
                AtmosphericSourceEvolution.EXTERNAL);
    }
}
