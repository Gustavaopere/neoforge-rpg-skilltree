package dev.gustavopere.volcanoes.volcano;

import java.util.Objects;

/** Explicit entity-hazard profile emitted by a pyroclastic flow or trail sample. */
public record PyroclasticExposure(
        double radiusBlocks,
        double heatSeverity,
        double particulateSeverity
) {
    public PyroclasticExposure {
        if (!Double.isFinite(radiusBlocks) || radiusBlocks < 0.0) {
            throw new IllegalArgumentException("radiusBlocks must be finite and non-negative");
        }
        heatSeverity = requireUnit("heatSeverity", heatSeverity);
        particulateSeverity = requireUnit("particulateSeverity", particulateSeverity);
    }

    public static PyroclasticExposure from(PyroclasticFlowState state) {
        Objects.requireNonNull(state, "state");
        return new PyroclasticExposure(
                state.radiusBlocks(),
                state.heatSeverity(),
                state.particulateSeverity());
    }

    public static PyroclasticExposure from(PyroclasticTrailState state) {
        Objects.requireNonNull(state, "state");
        return new PyroclasticExposure(
                state.radiusBlocks(),
                state.heatSeverity(),
                state.particulateSeverity());
    }

    private static double requireUnit(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException(name + " must be within [0, 1]");
        }
        return value;
    }
}
