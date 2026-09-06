package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;

import java.util.Objects;
import java.util.UUID;

/**
 * Authoritative Stage03 descriptor for one active volcanic-gas source.
 *
 * <p>Stage03 owns lifecycle, location, radius and normalized gas availability. It deliberately does
 * not infer CO2/SO2 species ratios from the coarse magma model; Atmosphere owns that hazard
 * projection policy.</p>
 */
public record VolcanicGasEmission(
        UUID sourceId,
        UUID volcanoId,
        BlockPos source,
        EruptionPhase phase,
        double normalizedEmissionStrength,
        double radiusBlocks,
        long expiresAtTick
) {
    public VolcanicGasEmission {
        sourceId = Objects.requireNonNull(sourceId, "sourceId");
        volcanoId = Objects.requireNonNull(volcanoId, "volcanoId");
        source = Objects.requireNonNull(source, "source").immutable();
        phase = Objects.requireNonNull(phase, "phase");
        if (phase == EruptionPhase.DORMANT) {
            throw new IllegalArgumentException("dormant eruptions must remove their gas source");
        }
        if (!Double.isFinite(normalizedEmissionStrength)
                || normalizedEmissionStrength < 0.0
                || normalizedEmissionStrength > 1.0) {
            throw new IllegalArgumentException("normalizedEmissionStrength must be within [0, 1]");
        }
        if (!Double.isFinite(radiusBlocks) || radiusBlocks <= 0.0) {
            throw new IllegalArgumentException("radiusBlocks must be finite and positive");
        }
        if (expiresAtTick < 0L) {
            throw new IllegalArgumentException("expiresAtTick must be non-negative");
        }
    }
}
