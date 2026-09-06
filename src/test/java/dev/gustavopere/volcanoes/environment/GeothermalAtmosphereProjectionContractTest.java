package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.volcano.GeothermalFeatureType;
import dev.gustavopere.volcanoes.volcano.GeothermalSource;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalAtmosphereProjectionContractTest {
    private static final String DIMENSION = "minecraft:overworld";

    @Test
    void defaultsExposeFinitePositiveGasScales() {
        GeothermalAtmosphereProjectionPolicy policy = GeothermalAtmosphereProjectionPolicy.defaults();
        assertTrue(Double.isFinite(policy.maxToxicGasPpm()));
        assertTrue(policy.maxToxicGasPpm() > 0.0);
        assertTrue(Double.isFinite(policy.maxSulfurDioxidePpm()));
        assertTrue(policy.maxSulfurDioxidePpm() > 0.0);
    }

    @Test
    void fumaroleProjectsOneStableExternalToxicSource() {
        GeothermalAtmosphereProjectionPolicy policy = GeothermalAtmosphereProjectionPolicy.defaults();
        GeothermalSource source = source(GeothermalFeatureType.FUMAROLE, 0.52);

        AtmosphericSource projected = GeothermalAtmosphereProjection.project(DIMENSION, source, policy)
                .orElseThrow();

        assertEquals(source.persistenceId(), projected.id());
        assertEquals(DIMENSION, projected.dimensionId());
        assertEquals(source.center().getX(), projected.x());
        assertEquals(source.center().getY(), projected.y());
        assertEquals(source.center().getZ(), projected.z());
        assertEquals(source.radiusBlocks(), projected.radiusBlocks());
        assertFalse(projected.persistent());
        assertEquals(AtmosphericSourceEvolution.EXTERNAL, projected.evolution());
        assertEquals(policy.maxToxicGasPpm() * source.gasSeverity(), projected.contribution().toxicGasPpm());
        assertEquals(0.0, projected.contribution().sulfurDioxidePpm());
        assertUnrelatedChannelsZero(projected.contribution());
    }

    @Test
    void sulfurousVentProjectsOneStableAcidGasSource() {
        GeothermalAtmosphereProjectionPolicy policy = GeothermalAtmosphereProjectionPolicy.defaults();
        GeothermalSource source = source(GeothermalFeatureType.SULFUROUS_VENT, 0.78);

        AtmosphericSource projected = GeothermalAtmosphereProjection.project(DIMENSION, source, policy)
                .orElseThrow();

        assertEquals(source.persistenceId(), projected.id());
        assertEquals(policy.maxSulfurDioxidePpm() * source.gasSeverity(), projected.contribution().sulfurDioxidePpm());
        assertEquals(0.0, projected.contribution().toxicGasPpm());
        assertUnrelatedChannelsZero(projected.contribution());
    }

    @Test
    void waterAndMudExpressionsDoNotInventAtmosphericGasFamilies() {
        GeothermalAtmosphereProjectionPolicy policy = GeothermalAtmosphereProjectionPolicy.defaults();
        assertTrue(GeothermalAtmosphereProjection.project(DIMENSION, source(GeothermalFeatureType.HOT_SPRING, 0.12), policy).isEmpty());
        assertTrue(GeothermalAtmosphereProjection.project(DIMENSION, source(GeothermalFeatureType.GEYSER, 0.36), policy).isEmpty());
        assertTrue(GeothermalAtmosphereProjection.project(DIMENSION, source(GeothermalFeatureType.MUD_POT, 0.30), policy).isEmpty());
    }

    private static GeothermalSource source(GeothermalFeatureType type, double gasSeverity) {
        return new GeothermalSource(
                UUID.nameUUIDFromBytes(("projection:" + type).getBytes()),
                type,
                new BlockPos(96, 72, -144),
                12,
                0.6,
                gasSeverity);
    }

    private static void assertUnrelatedChannelsZero(AtmosphereContribution contribution) {
        assertEquals(0.0, contribution.pressureDeltaAtm());
        assertEquals(0.0, contribution.oxygenFractionDelta());
        assertEquals(0.0, contribution.carbonDioxideFraction());
        assertEquals(0.0, contribution.particulatesMgM3());
        assertEquals(0.0, contribution.smokeMgM3());
        assertEquals(0.0, contribution.relativeHumidityDelta());
        assertEquals(0.0, contribution.thermalModifierDeltaC());
        assertEquals(0.0, contribution.oxygenDisplacementFraction());
    }
}
