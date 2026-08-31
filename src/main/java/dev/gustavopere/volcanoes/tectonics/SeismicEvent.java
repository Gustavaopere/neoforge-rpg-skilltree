package dev.gustavopere.volcanoes.tectonics;

import java.util.Objects;

/** Immutable description of one safe seismic release. */
public record SeismicEvent(
        double epicenterX,
        double epicenterZ,
        double magnitude,
        double radiusBlocks,
        double decayExponent,
        SeismicDamagePolicy damagePolicy
) {
    public SeismicEvent {
        if (!Double.isFinite(epicenterX) || !Double.isFinite(epicenterZ)) {
            throw new IllegalArgumentException("epicenter coordinates must be finite");
        }
        if (!Double.isFinite(magnitude) || magnitude <= 0.0 || magnitude > 10.0) {
            throw new IllegalArgumentException("magnitude must be finite and within (0, 10]");
        }
        if (!Double.isFinite(radiusBlocks) || radiusBlocks <= 0.0) {
            throw new IllegalArgumentException("radiusBlocks must be finite and positive");
        }
        if (!Double.isFinite(decayExponent) || decayExponent <= 0.0) {
            throw new IllegalArgumentException("decayExponent must be finite and positive");
        }
        damagePolicy = Objects.requireNonNull(damagePolicy, "damagePolicy");
    }

    public double intensityAt(double x, double z) {
        if (!Double.isFinite(x) || !Double.isFinite(z)) {
            throw new IllegalArgumentException("sample coordinates must be finite");
        }
        double distance = Math.hypot(x - epicenterX, z - epicenterZ);
        if (distance >= radiusBlocks) {
            return 0.0;
        }
        double magnitudeFactor = Math.min(1.0, magnitude / 8.0);
        double radialFactor = Math.pow(1.0 - distance / radiusBlocks, decayExponent);
        return Math.max(0.0, Math.min(1.0, magnitudeFactor * radialFactor));
    }
}
