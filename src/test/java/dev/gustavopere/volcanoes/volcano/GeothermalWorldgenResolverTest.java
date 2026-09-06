package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import dev.gustavopere.volcanoes.tectonics.TectonicSample;
import dev.gustavopere.volcanoes.tectonics.TectonicService;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalWorldgenResolverTest {
    @Test
    void deterministicVolcanoProjectionRaisesPotentialWithoutPersistence() {
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
        BlockPos near = magmaCenter.offset(32, 72, 16);
        TectonicSample sample = tectonics.sample(seed, near.getX(), near.getZ());
        double tectonicOnly = activity.potential(sample);

        double first = resolver.potentialAt(seed, near);
        double second = resolver.potentialAt(seed, near);

        assertEquals(first, second);
        assertTrue(first > tectonicOnly,
                "a deterministic nearby magma chamber should raise geothermal potential");
        assertEquals(
                resolver.causalVolcanoAt(seed, near),
                resolver.causalVolcanoAt(seed, near),
                "causal volcano resolution must be deterministic");
        assertTrue(resolver.causalVolcanoAt(seed, near).isPresent(),
                "a magma-raised geothermal point must expose its causal volcano");
    }

    @Test
    void absentVolcanoCandidateFallsBackExactlyToStaticTectonics() {
        long seed = 0xBB67AE8584CAA73BL;
        TectonicService tectonics = lowInteriorTectonics();
        GeothermalActivityService activity = new GeothermalActivityService(512.0);
        VolcanoCandidateField field = new VolcanoCandidateField(512, 128);
        VolcanoWorldgenResolver volcanoes = new VolcanoWorldgenResolver(
                field,
                tectonics,
                new VolcanoSitePlanner(256.0, 1.0),
                512);
        GeothermalWorldgenResolver resolver = new GeothermalWorldgenResolver(
                tectonics,
                volcanoes,
                activity);

        BlockPos position = field.centerForCell(seed, 3L, -2L).offset(24, 90, -8);
        double expected = activity.potential(tectonics.sample(seed, position.getX(), position.getZ()));

        assertEquals(expected, resolver.potentialAt(seed, position));
        assertTrue(resolver.causalVolcanoAt(seed, position).isEmpty(),
                "tectonic-only geothermal potential must not invent a volcanic mineral cause");
    }

    @Test
    void resolverFailsClosedOnInvalidDependenciesAndPosition() {
        TectonicService tectonics = lowInteriorTectonics();
        GeothermalActivityService activity = new GeothermalActivityService(512.0);
        VolcanoWorldgenResolver volcanoes = new VolcanoWorldgenResolver(
                new VolcanoCandidateField(512, 128),
                tectonics,
                new VolcanoSitePlanner(256.0, 0.0),
                512);

        assertThrows(NullPointerException.class,
                () -> new GeothermalWorldgenResolver(null, volcanoes, activity));
        assertThrows(NullPointerException.class,
                () -> new GeothermalWorldgenResolver(tectonics, null, activity));
        assertThrows(NullPointerException.class,
                () -> new GeothermalWorldgenResolver(tectonics, volcanoes, null));

        GeothermalWorldgenResolver resolver = new GeothermalWorldgenResolver(
                tectonics,
                volcanoes,
                activity);
        assertThrows(NullPointerException.class, () -> resolver.potentialAt(1L, null));
        assertThrows(NullPointerException.class, () -> resolver.causalVolcanoAt(1L, null));
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
