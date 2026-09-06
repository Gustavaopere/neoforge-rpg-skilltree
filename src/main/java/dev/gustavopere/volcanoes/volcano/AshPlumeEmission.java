package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

/**
 * Authoritative Stage 03 descriptor for one volcanic ash plume.
 *
 * <p>This contract deliberately contains no Stage 04 atmosphere implementation types. Atmosphere
 * can adapt the stable source identity and particulate/smoke strengths later, while client particles
 * and surface deposition remain separate concerns.</p>
 */
public record AshPlumeEmission(
        UUID sourceId,
        UUID volcanoId,
        BlockPos source,
        EruptionPhase phase,
        double normalizedLoad,
        double particulateStrength,
        double smokeStrength,
        double radiusBlocks,
        long lifetimeTicks
) {
    private static final String SOURCE_NAMESPACE = "volcanoes:ash:";

    public AshPlumeEmission {
        sourceId = Objects.requireNonNull(sourceId, "sourceId");
        volcanoId = Objects.requireNonNull(volcanoId, "volcanoId");
        source = Objects.requireNonNull(source, "source").immutable();
        phase = Objects.requireNonNull(phase, "phase");
        normalizedLoad = requireUnit("normalizedLoad", normalizedLoad);
        particulateStrength = requireUnit("particulateStrength", particulateStrength);
        smokeStrength = requireUnit("smokeStrength", smokeStrength);
        if (!Double.isFinite(radiusBlocks) || radiusBlocks < 0.0) {
            throw new IllegalArgumentException("radiusBlocks must be finite and non-negative");
        }
        if (lifetimeTicks < 0L) {
            throw new IllegalArgumentException("lifetimeTicks must be non-negative");
        }
    }

    public static AshPlumeEmission from(EruptionSignal signal) {
        Objects.requireNonNull(signal, "signal");
        boolean emittingPhase = switch (signal.phase()) {
            case OPENING, SUSTAINED, WANING -> true;
            case PRECURSORS, DORMANT -> false;
        };
        double load = emittingPhase ? clamp(signal.intensity()) : 0.0;
        if (load <= 0.0) {
            return new AshPlumeEmission(
                    sourceIdFor(signal.volcanoId()),
                    signal.volcanoId(),
                    signal.source(),
                    signal.phase(),
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0L);
        }

        MagmaComposition composition = signal.chamber().composition();
        double explosivity = clamp(
                composition.silicaFraction() * 0.40
                        + composition.volatileRichness() * 0.35
                        + signal.chamber().gasFraction() * 0.25);
        double particulate = clamp(load * (0.35 + 0.65 * explosivity));
        double smoke = clamp(load * (
                0.30
                        + signal.chamber().gasFraction() * 0.45
                        + composition.volatileRichness() * 0.25));
        double relativeIntensity = signal.profile().peakIntensity() <= 0.0
                ? 0.0
                : clamp(signal.intensity() / signal.profile().peakIntensity());
        double radius = signal.profile().innerRadiusBlocks()
                + (signal.profile().outerRadiusBlocks() - signal.profile().innerRadiusBlocks()) * relativeIntensity;
        long lifetime = Math.max(
                20L,
                Math.round(signal.profile().waningTicks() * (0.25 + 0.75 * load)));

        return new AshPlumeEmission(
                sourceIdFor(signal.volcanoId()),
                signal.volcanoId(),
                signal.source(),
                signal.phase(),
                load,
                particulate,
                smoke,
                radius,
                lifetime);
    }

    public static UUID sourceIdFor(UUID volcanoId) {
        Objects.requireNonNull(volcanoId, "volcanoId");
        return UUID.nameUUIDFromBytes((SOURCE_NAMESPACE + volcanoId).getBytes(StandardCharsets.UTF_8));
    }

    public boolean active() {
        return normalizedLoad > 0.0;
    }

    /** Compatibility name used by downstream atmosphere/deposition planning. */
    public double plumeRadiusBlocks() {
        return radiusBlocks;
    }

    private static double requireUnit(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException(name + " must be within [0, 1]");
        }
        return value;
    }

    private static double clamp(double value) {
        return Math.max(0.0, Math.min(1.0, value));
    }
}
