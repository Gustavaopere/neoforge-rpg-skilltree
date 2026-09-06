package dev.gustavopere.volcanoes.volcano;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

/** Projects one periodic geyser eruption into a short-lived heat-index source. */
public final class GeothermalGeyserPulseProjector {
    private static final String SOURCE_NAMESPACE = "volcanoes:geyser-pulse:";

    private GeothermalGeyserPulseProjector() {
    }

    public static VolcanicHeatSource project(GeothermalSource source, long gameTick, long ttlTicks) {
        Objects.requireNonNull(source, "source");
        if (source.type() != GeothermalFeatureType.GEYSER) {
            throw new IllegalArgumentException("geyser heat pulse requires a GEYSER source");
        }
        if (gameTick < 0L) {
            throw new IllegalArgumentException("gameTick must be non-negative");
        }
        if (ttlTicks <= 0L) {
            throw new IllegalArgumentException("ttlTicks must be positive");
        }
        UUID pulseId = UUID.nameUUIDFromBytes(
                (SOURCE_NAMESPACE + source.persistenceId()).getBytes(StandardCharsets.UTF_8));
        return new VolcanicHeatSource(
                pulseId,
                VolcanicHeatSource.Kind.GEOTHERMAL,
                source.center(),
                Math.max(source.radiusBlocks(), source.radiusBlocks() * 1.5),
                Math.min(1.0, source.heatSeverity() + 0.10),
                Math.addExact(gameTick, ttlTicks));
    }
}
