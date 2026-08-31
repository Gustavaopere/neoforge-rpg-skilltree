package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

/** Pure projection from one authoritative pyroclastic flow head into the shared heat-source model. */
public final class PyroclasticHeatSourceProjector {
    private static final String SOURCE_NAMESPACE = "volcanoes:pyroclastic:";

    private PyroclasticHeatSourceProjector() {
    }

    public static UUID sourceId(UUID volcanoId) {
        Objects.requireNonNull(volcanoId, "volcanoId");
        return UUID.nameUUIDFromBytes((SOURCE_NAMESPACE + volcanoId).getBytes(StandardCharsets.UTF_8));
    }

    public static VolcanicHeatSource fromFlow(
            PyroclasticFlowState flow,
            long gameTick,
            long ttlTicks
    ) {
        Objects.requireNonNull(flow, "flow");
        if (gameTick < 0L) {
            throw new IllegalArgumentException("gameTick must be non-negative");
        }
        if (ttlTicks <= 0L) {
            throw new IllegalArgumentException("ttlTicks must be positive");
        }
        long expiresAt = Math.addExact(gameTick, ttlTicks);
        return new VolcanicHeatSource(
                sourceId(flow.volcanoId()),
                VolcanicHeatSource.Kind.PYROCLASTIC,
                BlockPos.containing(flow.position()),
                Math.max(0.25, flow.radiusBlocks()),
                flow.heatSeverity(),
                expiresAt);
    }
}
