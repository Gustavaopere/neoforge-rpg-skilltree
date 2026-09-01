package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.geology.DepositOrigin;
import dev.gustavopere.volcanoes.geology.GeologyResourceTags;
import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import dev.gustavopere.volcanoes.tectonics.TectonicSample;
import dev.gustavopere.volcanoes.tectonics.TectonicService;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class HydrothermalDepositProjectorTest {
    @Test
    void guaranteedProjectionCreatesStableHydrothermalMineralDepositBelowSurface() {
        GeothermalFeaturePlacement placement = new GeothermalFeaturePlacement(
                GeothermalFeatureType.HOT_SPRING,
                new BlockPos(120, 84, -72),
                4,
                0.80,
                0.30,
                1.0);

        HydrothermalDepositProjector projector = new HydrothermalDepositProjector();
        var first = projector.project(91L, placement).orElseThrow();
        var second = projector.project(91L, placement).orElseThrow();

        assertEquals(first, second);
        assertEquals(DepositOrigin.HYDROTHERMAL, first.origin());
        assertEquals(first.resourceTag(), second.resourceTag());
        assertEquals(placement.center().getX(), first.center().getX());
        assertEquals(placement.center().getZ(), first.center().getZ());
        assertTrue(first.center().getY() < placement.center().getY());
        assertTrue(first.center().getY() + Math.ceil(first.radius()) <= placement.center().getY() - 1,
                "hydrothermal influence volume must remain fully below the sampled surface");
        assertTrue(first.radius() >= placement.radiusBlocks());
        assertTrue(first.richness() > 0.0 && first.richness() <= 1.0);
    }

    @Test
    void volcanicProjectionUsesTheCanonicalCausalMetalIdentity() {
        long seed = 0x6A09E667F3BCC909L;
        TectonicService tectonics = lowInteriorTectonics();
        GeothermalActivityService activity = new GeothermalActivityService(512.0);
        VolcanoCandidateField field = new VolcanoCandidateField(512, 128);
        VolcanoWorldgenResolver volcanoes = new VolcanoWorldgenResolver(
                field,
                tectonics,
                new VolcanoSitePlanner(256.0, 0.0),
                512);
        GeothermalWorldgenResolver resolver = new GeothermalWorldgenResolver(
                tectonics,
                volcanoes,
                activity);

        BlockPos magmaCenter = field.centerForCell(seed, 0L, 0L);
        BlockPos center = magmaCenter.offset(32, 72, 16);
        GeothermalFeaturePlacement placement = new GeothermalFeaturePlacement(
                GeothermalFeatureType.HOT_SPRING,
                center,
                4,
                0.80,
                0.30,
                1.0);

        VolcanoSite causal = resolver.causalVolcanoAt(seed, center).orElseThrow();
        var projected = new HydrothermalDepositProjector(resolver).project(seed, placement).orElseThrow();
        var expected = HydrothermalMineralizationPolicy.resourceFor(Optional.of(causal.type()));

        assertEquals(expected, projected.resourceTag());
        assertNotEquals(GeologyResourceTags.MINERAL_RESOURCES.location(), projected.resourceTag());
    }

    @Test
    void zeroChanceNeverCreatesADeposit() {
        GeothermalFeaturePlacement placement = new GeothermalFeaturePlacement(
                GeothermalFeatureType.FUMAROLE,
                BlockPos.ZERO,
                1,
                0.55,
                0.60,
                0.0);

        assertFalse(new HydrothermalDepositProjector().project(123L, placement).isPresent());
    }

    @Test
    void fractionalChanceIsDeterministicAndActuallySelectsSomeSeeds() {
        GeothermalFeaturePlacement placement = new GeothermalFeaturePlacement(
                GeothermalFeatureType.SULFUROUS_VENT,
                new BlockPos(8, 70, 8),
                2,
                0.52,
                0.78,
                0.50);
        HydrothermalDepositProjector projector = new HydrothermalDepositProjector();

        boolean sawPresent = false;
        boolean sawAbsent = false;
        for (long seed = 0; seed < 256; seed++) {
            Optional<?> first = projector.project(seed, placement);
            Optional<?> second = projector.project(seed, placement);
            assertEquals(first, second);
            sawPresent |= first.isPresent();
            sawAbsent |= first.isEmpty();
        }

        assertTrue(sawPresent, "a fractional chance must admit some deterministic seeds");
        assertTrue(sawAbsent, "a fractional chance must reject some deterministic seeds");
    }

    private static TectonicService lowInteriorTectonics() {
        return (seed, x, z) -> new TectonicSample(
                17L,
                19L,
                TectonicContext.INTERIOR,
                0.10,
                0.20,
                8_192.0,
                0.0,
                0.0);
    }
}
