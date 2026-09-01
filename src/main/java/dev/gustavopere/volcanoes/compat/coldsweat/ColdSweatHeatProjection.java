package dev.gustavopere.volcanoes.compat.coldsweat;

import dev.gustavopere.volcanoes.volcano.VolcanicHeatSource;
import net.minecraft.core.BlockPos;

import java.util.List;
import java.util.Objects;

/** Pure bounded mapping from canonical Stage03 heat sources into a Cold Sweat WORLD delta. */
public final class ColdSweatHeatProjection {
    private ColdSweatHeatProjection() {
    }

    public static double projectMcDelta(
            BlockPos sample,
            List<VolcanicHeatSource> sources,
            ColdSweatHeatProjectionPolicy policy
    ) {
        Objects.requireNonNull(sample, "sample");
        Objects.requireNonNull(sources, "sources");
        Objects.requireNonNull(policy, "policy");

        double delta = 0.0;
        int limit = Math.min(sources.size(), policy.maxSourcesPerSample());
        for (int index = 0; index < limit; index++) {
            VolcanicHeatSource source = Objects.requireNonNull(sources.get(index), "source");
            double attenuation = attenuation(sample, source);
            if (attenuation <= 0.0 || source.severity() <= 0.0) {
                continue;
            }
            delta += policy.fullSeverityDeltaMc() * source.severity() * attenuation;
            if (delta >= policy.maxWorldDeltaMc()) {
                return policy.maxWorldDeltaMc();
            }
        }
        return Math.min(policy.maxWorldDeltaMc(), delta);
    }

    private static double attenuation(BlockPos sample, VolcanicHeatSource source) {
        double dx = (double) sample.getX() - source.center().getX();
        double dy = (double) sample.getY() - source.center().getY();
        double dz = (double) sample.getZ() - source.center().getZ();
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (distance >= source.radiusBlocks()) {
            return 0.0;
        }
        return Math.max(0.0, 1.0 - distance / source.radiusBlocks());
    }
}
