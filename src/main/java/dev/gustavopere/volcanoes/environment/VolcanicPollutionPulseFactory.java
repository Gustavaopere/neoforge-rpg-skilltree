package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.volcano.AshPlumeEmission;
import dev.gustavopere.volcanoes.volcano.VolcanicGasEmission;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Projects authoritative Stage03 source state into idempotent pollution pulses.
 *
 * <p>A source identity is continuous, while pollution publication is periodic. The pulse identity
 * therefore includes the atmosphere interval bucket: retrying the same interval reuses the same
 * UUID, while a later interval for the same source receives a fresh UUID.</p>
 */
public final class VolcanicPollutionPulseFactory {
    private static final String GAS_NAMESPACE = "volcanoes:pollution:gas:";
    private static final String ASH_NAMESPACE = "volcanoes:pollution:ash:";

    private VolcanicPollutionPulseFactory() {
    }

    public static Optional<PollutionEmission> gasPulse(
            String dimensionId,
            VolcanicGasEmission emission,
            long gameTime,
            int intervalTicks
    ) {
        Objects.requireNonNull(emission, "emission");
        double strength = emission.normalizedEmissionStrength();
        if (strength <= 0.0) {
            return Optional.empty();
        }
        return Optional.of(pulse(
                GAS_NAMESPACE,
                dimensionId,
                emission.sourceId(),
                emission.source().getX() + 0.5,
                emission.source().getY() + 0.5,
                emission.source().getZ() + 0.5,
                gameTime,
                intervalTicks,
                new PollutionLoad(strength, 0.0, 0.0, strength, 0.0)));
    }

    public static Optional<PollutionEmission> ashPulse(
            String dimensionId,
            AshPlumeEmission emission,
            long gameTime,
            int intervalTicks
    ) {
        Objects.requireNonNull(emission, "emission");
        if (!emission.active() || (emission.particulateStrength() <= 0.0 && emission.smokeStrength() <= 0.0)) {
            return Optional.empty();
        }
        return Optional.of(pulse(
                ASH_NAMESPACE,
                dimensionId,
                emission.sourceId(),
                emission.source().getX() + 0.5,
                emission.source().getY() + 0.5,
                emission.source().getZ() + 0.5,
                gameTime,
                intervalTicks,
                new PollutionLoad(0.0, emission.particulateStrength(), emission.smokeStrength(), 0.0, 0.0)));
    }

    private static PollutionEmission pulse(
            String namespace,
            String dimensionId,
            UUID sourceId,
            double x,
            double y,
            double z,
            long gameTime,
            int intervalTicks,
            PollutionLoad load
    ) {
        Objects.requireNonNull(dimensionId, "dimensionId");
        Objects.requireNonNull(sourceId, "sourceId");
        if (gameTime < 0L) {
            throw new IllegalArgumentException("gameTime must be non-negative");
        }
        if (intervalTicks <= 0) {
            throw new IllegalArgumentException("intervalTicks must be positive");
        }
        long bucket = gameTime / intervalTicks;
        UUID pulseId = UUID.nameUUIDFromBytes(
                (namespace + sourceId + ':' + bucket).getBytes(StandardCharsets.UTF_8));
        return new PollutionEmission(pulseId, dimensionId, x, y, z, load);
    }
}
