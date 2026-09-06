package dev.gustavopere.volcanoes.pressure;

import java.util.Objects;

/** Complete pressure result for one entity update before world-facing effects are applied. */
public record PressureEntityUpdate(
        PressureSample externalPressure,
        PressureEnvironmentResult environment,
        PressureExposureResult exposure,
        PressureEntityEffectPlan effects
) {
    public PressureEntityUpdate {
        externalPressure = Objects.requireNonNull(externalPressure, "externalPressure");
        environment = Objects.requireNonNull(environment, "environment");
        exposure = Objects.requireNonNull(exposure, "exposure");
        effects = Objects.requireNonNull(effects, "effects");
    }
}
