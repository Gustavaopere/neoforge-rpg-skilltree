package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;

import java.util.Objects;
import java.util.UUID;

/** Immutable volcanic/environmental heat source consumed through the bounded spatial index. */
public record VolcanicHeatSource(
        UUID sourceId,
        Kind kind,
        BlockPos center,
        double radiusBlocks,
        double severity,
        long expiresAtTick
) {
    public VolcanicHeatSource {
        sourceId = Objects.requireNonNull(sourceId, "sourceId");
        kind = Objects.requireNonNull(kind, "kind");
        center = Objects.requireNonNull(center, "center").immutable();
        if (!Double.isFinite(radiusBlocks) || radiusBlocks <= 0.0) {
            throw new IllegalArgumentException("radiusBlocks must be finite and positive");
        }
        if (!Double.isFinite(severity) || severity < 0.0 || severity > 1.0) {
            throw new IllegalArgumentException("severity must be within [0, 1]");
        }
        if (expiresAtTick < 0L) {
            throw new IllegalArgumentException("expiresAtTick must be non-negative");
        }
    }

    public boolean isExpired(long gameTick) {
        if (gameTick < 0L) {
            throw new IllegalArgumentException("gameTick must be non-negative");
        }
        return expiresAtTick != Long.MAX_VALUE && gameTick >= expiresAtTick;
    }

    public enum Kind {
        LAVA,
        PYROCLASTIC,
        GEOTHERMAL
    }
}
