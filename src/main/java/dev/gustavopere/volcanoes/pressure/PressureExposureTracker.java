package dev.gustavopere.volcanoes.pressure;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/** Server-side state machine for progressive pressure exposure. */
public final class PressureExposureTracker {
    private final Map<UUID, Integer> exposureTicks = new HashMap<>();

    public PressureExposureResult update(UUID entityId, double unprotectedOverpressureAtm, PressureExposureConfig config) {
        Objects.requireNonNull(entityId, "entityId");
        Objects.requireNonNull(config, "config");
        requireNonNegative("unprotectedOverpressureAtm", unprotectedOverpressureAtm);

        if (unprotectedOverpressureAtm == 0.0
                || unprotectedOverpressureAtm < config.discomfortOverpressureAtm()) {
            exposureTicks.remove(entityId);
            return result(PressureExposureStage.NORMAL, 0, 1.0, 0.0, 0.0);
        }

        int ticks = exposureTicks.compute(entityId, (ignored, previous) -> {
            if (previous == null) {
                return 1;
            }
            return previous == Integer.MAX_VALUE ? Integer.MAX_VALUE : previous + 1;
        });

        if (ticks <= config.graceTicks()) {
            return result(PressureExposureStage.GRACE, ticks, 1.0, 0.0, 0.0);
        }
        if (unprotectedOverpressureAtm >= config.barotraumaOverpressureAtm()) {
            return result(PressureExposureStage.BAROTRAUMA, ticks,
                    config.impairedMovementMultiplier(), config.neurologicalPenalty(), config.barotraumaDamagePerUpdate());
        }
        if (unprotectedOverpressureAtm >= config.impairmentOverpressureAtm()) {
            return result(PressureExposureStage.IMPAIRED, ticks,
                    config.impairedMovementMultiplier(), config.neurologicalPenalty(), 0.0);
        }
        return result(PressureExposureStage.DISCOMFORT, ticks, 1.0, 0.0, 0.0);
    }

    public void clear(UUID entityId) {
        exposureTicks.remove(Objects.requireNonNull(entityId, "entityId"));
    }

    public static double unprotectedOverpressureAtm(
            double totalExternalAtm,
            double surfaceAtmosphericAtm,
            double pressureRatingAtm
    ) {
        requireNonNegative("totalExternalAtm", totalExternalAtm);
        requireNonNegative("surfaceAtmosphericAtm", surfaceAtmosphericAtm);
        requireNonNegative("pressureRatingAtm", pressureRatingAtm);
        return Math.max(0.0, totalExternalAtm - surfaceAtmosphericAtm - pressureRatingAtm);
    }

    private static PressureExposureResult result(
            PressureExposureStage stage,
            int ticks,
            double movement,
            double neurological,
            double damage
    ) {
        return new PressureExposureResult(stage, ticks, movement, neurological, damage);
    }

    private static void requireNonNegative(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0) {
            throw new IllegalArgumentException(name + " must be finite and non-negative");
        }
    }
}
