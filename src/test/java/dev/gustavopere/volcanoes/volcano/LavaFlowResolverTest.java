package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.geology.RockCategory;
import dev.gustavopere.volcanoes.geology.RockProfile;
import dev.gustavopere.volcanoes.geology.RockProfileResolver;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class LavaFlowResolverTest {
    private static final RockProfile BASALT = new RockProfile(
            "basalt",
            RockCategory.IGNEOUS_EXTRUSIVE,
            0.82,
            0.18,
            1.9,
            1.25,
            0.85,
            0.30);
    private static final RockProfile TUFF = new RockProfile(
            "tuff",
            RockCategory.VOLCANIC_FRAGMENTAL,
            0.45,
            0.65,
            1.3,
            0.85,
            0.35,
            0.80);
    private static final RockProfile GRANITE = new RockProfile(
            "granite",
            RockCategory.IGNEOUS_INTRUSIVE,
            0.90,
            0.08,
            2.8,
            0.65,
            0.90,
            0.35);

    @Test
    void geologyProfilesProduceDistinctBoundedFlowAndCoolingModifiers() {
        LavaEnvironmentSample basalt = sample(BASALT);
        LavaEnvironmentSample tuff = sample(TUFF);
        LavaEnvironmentSample granite = sample(GRANITE);

        assertTrue(basalt.spreadMultiplier() > 1.0);
        assertTrue(tuff.spreadMultiplier() < 1.0);
        assertTrue(granite.spreadMultiplier() < tuff.spreadMultiplier());

        assertTrue(granite.coolingMultiplier() > basalt.coolingMultiplier());
        assertTrue(basalt.coolingMultiplier() > tuff.coolingMultiplier());

        for (LavaEnvironmentSample sample : new LavaEnvironmentSample[]{basalt, tuff, granite}) {
            assertTrue(sample.spreadMultiplier() >= LavaEnvironmentSample.MIN_SPREAD_MULTIPLIER);
            assertTrue(sample.spreadMultiplier() <= LavaEnvironmentSample.MAX_SPREAD_MULTIPLIER);
            assertTrue(sample.coolingMultiplier() >= LavaEnvironmentSample.MIN_COOLING_MULTIPLIER);
            assertTrue(sample.coolingMultiplier() <= LavaEnvironmentSample.MAX_COOLING_MULTIPLIER);
            assertFalse(sample.usesVanillaFallback());
        }
    }

    @Test
    void genericProfileIsNeutralVanillaFallback() {
        LavaEnvironmentSample sample = new LavaFlowResolver(RockProfileResolver.fallback())
                .sample(17L, 4, 63, -9);

        assertSame(RockProfile.GENERIC, sample.rockProfile());
        assertEquals(1.0, sample.spreadMultiplier(), 1.0e-9);
        assertEquals(1.0, sample.coolingMultiplier(), 1.0e-9);
        assertTrue(sample.usesVanillaFallback());
    }

    @Test
    void resolverUsesCanonicalGeologyCoordinates() {
        AtomicBoolean invoked = new AtomicBoolean();
        RockProfileResolver geology = (worldSeed, x, y, z) -> {
            assertEquals(991L, worldSeed);
            assertEquals(-32, x);
            assertEquals(71, y);
            assertEquals(144, z);
            invoked.set(true);
            return BASALT;
        };

        LavaEnvironmentSample sample = new LavaFlowResolver(geology).sample(991L, -32, 71, 144);

        assertTrue(invoked.get());
        assertSame(BASALT, sample.rockProfile());
    }

    @Test
    void extremeDatapackValuesAreClampedToSafetyEnvelope() {
        RockProfile extreme = new RockProfile(
                "extreme",
                RockCategory.IGNEOUS_EXTRUSIVE,
                1.0,
                0.0,
                100.0,
                100.0,
                1.0,
                0.0);

        LavaEnvironmentSample sample = sample(extreme);

        assertEquals(LavaEnvironmentSample.MAX_SPREAD_MULTIPLIER, sample.spreadMultiplier(), 1.0e-9);
        assertEquals(LavaEnvironmentSample.MAX_COOLING_MULTIPLIER, sample.coolingMultiplier(), 1.0e-9);
    }

    private static LavaEnvironmentSample sample(RockProfile profile) {
        return new LavaFlowResolver((worldSeed, x, y, z) -> profile).sample(1L, 0, 64, 0);
    }
}
