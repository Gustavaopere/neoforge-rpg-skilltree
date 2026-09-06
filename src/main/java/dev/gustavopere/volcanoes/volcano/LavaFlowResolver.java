package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.geology.RockProfile;
import dev.gustavopere.volcanoes.geology.RockProfileResolver;

import java.util.Objects;

/** Resolves bounded lava spread and cooling behavior from the canonical geology contract. */
public final class LavaFlowResolver {
    private final RockProfileResolver rockProfiles;

    public LavaFlowResolver(RockProfileResolver rockProfiles) {
        this.rockProfiles = Objects.requireNonNull(rockProfiles, "rockProfiles");
    }

    public LavaEnvironmentSample sample(long worldSeed, int x, int y, int z) {
        RockProfile profile = rockProfiles.resolve(worldSeed, x, y, z);
        if (profile == null) {
            profile = RockProfile.GENERIC;
        }

        boolean vanillaFallback = RockProfile.GENERIC.id().equals(profile.id());
        if (vanillaFallback) {
            return new LavaEnvironmentSample(profile, 1.0, 1.0, true);
        }

        double spread = clamp(
                profile.lavaFlowMultiplier(),
                LavaEnvironmentSample.MIN_SPREAD_MULTIPLIER,
                LavaEnvironmentSample.MAX_SPREAD_MULTIPLIER);
        double cooling = clamp(
                profile.thermalConductivity() / RockProfile.GENERIC.thermalConductivity(),
                LavaEnvironmentSample.MIN_COOLING_MULTIPLIER,
                LavaEnvironmentSample.MAX_COOLING_MULTIPLIER);
        return new LavaEnvironmentSample(profile, spread, cooling, false);
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
