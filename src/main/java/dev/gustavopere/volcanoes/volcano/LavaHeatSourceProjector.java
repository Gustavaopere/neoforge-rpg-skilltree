package dev.gustavopere.volcanoes.volcano;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

/** Pure projection from one authoritative eruption signal into a bounded shared lava heat source. */
public final class LavaHeatSourceProjector {
    private static final String SOURCE_NAMESPACE = "volcanoes:lava:";
    private static final double MIN_RADIUS_BLOCKS = 8.0;
    private static final double MAX_RADIUS_BLOCKS = 64.0;

    private LavaHeatSourceProjector() {
    }

    public static UUID sourceId(UUID volcanoId) {
        Objects.requireNonNull(volcanoId, "volcanoId");
        return UUID.nameUUIDFromBytes((SOURCE_NAMESPACE + volcanoId).getBytes(StandardCharsets.UTF_8));
    }

    public static VolcanicHeatSource fromSignal(
            EruptionSignal signal,
            long gameTick,
            long ttlTicks
    ) {
        Objects.requireNonNull(signal, "signal");
        if (gameTick < 0L) {
            throw new IllegalArgumentException("gameTick must be non-negative");
        }
        if (ttlTicks <= 0L) {
            throw new IllegalArgumentException("ttlTicks must be positive");
        }
        long expiresAt = Math.addExact(gameTick, ttlTicks);
        double radius = Math.min(
                MAX_RADIUS_BLOCKS,
                Math.max(MIN_RADIUS_BLOCKS, signal.profile().innerRadiusBlocks() * 0.5));
        return new VolcanicHeatSource(
                sourceId(signal.volcanoId()),
                VolcanicHeatSource.Kind.LAVA,
                signal.source(),
                radius,
                signal.intensity(),
                expiresAt);
    }
}
