package dev.gustavopere.volcanoes.volcano;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/** Pure Stage03 projection from an authoritative eruption signal into species-neutral gas metadata. */
public final class VolcanicGasEmissionProjector {
    private static final long EMISSION_TTL_TICKS = 400L;
    private static final double MIN_RADIUS_BLOCKS = 48.0;
    private static final double MAX_RADIUS_BLOCKS = 384.0;

    private VolcanicGasEmissionProjector() {
    }

    public static UUID sourceId(UUID volcanoId) {
        Objects.requireNonNull(volcanoId, "volcanoId");
        return UUID.nameUUIDFromBytes(
                ("volcanoes:volcanic_gas:" + volcanoId).getBytes(StandardCharsets.UTF_8));
    }

    /** Lifecycle-owned metadata remains active until an explicit update/removal replaces it. */
    public static Optional<VolcanicGasEmission> projectLifecycle(EruptionSignal signal) {
        return projectInternal(signal, Long.MAX_VALUE);
    }

    /** Tick-aware projection retained for focused tests and finite-lived diagnostic producers. */
    public static Optional<VolcanicGasEmission> project(EruptionSignal signal, long gameTick) {
        Objects.requireNonNull(signal, "signal");
        if (gameTick < 0L) {
            throw new IllegalArgumentException("gameTick must be non-negative");
        }
        long expiresAtTick = gameTick > Long.MAX_VALUE - EMISSION_TTL_TICKS
                ? Long.MAX_VALUE
                : gameTick + EMISSION_TTL_TICKS;
        return projectInternal(signal, expiresAtTick);
    }

    private static Optional<VolcanicGasEmission> projectInternal(EruptionSignal signal, long expiresAtTick) {
        Objects.requireNonNull(signal, "signal");
        if (signal.phase() == EruptionPhase.DORMANT || signal.intensity() <= 0.0) {
            return Optional.empty();
        }

        MagmaChamber chamber = signal.chamber();
        MagmaComposition magma = chamber.composition();
        double strength = clamp(signal.intensity() * chamber.gasFraction() * magma.volatileRichness());
        double radius = Math.max(
                MIN_RADIUS_BLOCKS,
                Math.min(MAX_RADIUS_BLOCKS, signal.profile().outerRadiusBlocks()));
        return Optional.of(new VolcanicGasEmission(
                sourceId(signal.volcanoId()),
                signal.volcanoId(),
                signal.source(),
                signal.phase(),
                strength,
                radius,
                expiresAtTick));
    }

    private static double clamp(double value) {
        return Math.max(0.0, Math.min(1.0, value));
    }
}
