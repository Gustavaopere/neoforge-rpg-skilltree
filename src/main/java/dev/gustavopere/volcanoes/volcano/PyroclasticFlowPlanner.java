package dev.gustavopere.volcanoes.volcano;

import net.minecraft.world.phys.Vec3;

import java.util.Objects;
import java.util.Optional;
import java.util.SplittableRandom;

/** Deterministic flow-head seeding from one eruption signal and one explicitly allocated token. */
public final class PyroclasticFlowPlanner {
    private PyroclasticFlowPlanner() {
    }

    public static Optional<PyroclasticFlowState> seed(EruptionSignal signal, int flowSpawnWork, long gameTick) {
        Objects.requireNonNull(signal, "signal");
        if (flowSpawnWork < 0) {
            throw new IllegalArgumentException("flowSpawnWork must be non-negative");
        }
        if (gameTick < 0L) {
            throw new IllegalArgumentException("gameTick must be non-negative");
        }
        if (flowSpawnWork == 0 || signal.phase() != EruptionPhase.SUSTAINED || signal.intensity() <= 0.0) {
            return Optional.empty();
        }

        double relativeIntensity = clamp(signal.intensity() / signal.profile().peakIntensity());
        MagmaChamber chamber = signal.chamber();
        MagmaComposition composition = chamber.composition();
        double explosivity = clamp(
                composition.silicaFraction() * 0.40
                        + composition.volatileRichness() * 0.35
                        + chamber.gasFraction() * 0.25);
        SplittableRandom random = new SplittableRandom(seedValue(signal, gameTick));
        double angle = random.nextDouble(0.0, Math.PI * 2.0);
        double speed = 0.35 + 0.65 * Math.max(relativeIntensity, explosivity);
        speed = Math.min(PyroclasticFlowDynamics.MAX_HORIZONTAL_SPEED, speed);
        Vec3 velocity = new Vec3(Math.cos(angle) * speed, 0.0, Math.sin(angle) * speed);
        double radius = Math.max(2.0, signal.profile().innerRadiusBlocks() * (0.04 + 0.06 * explosivity));
        double heat = clamp(0.45 + 0.55 * relativeIntensity);
        double particulates = clamp(0.40 + 0.60 * explosivity);
        long lifetime = 80L + Math.round(160.0 * Math.max(relativeIntensity, explosivity));

        return Optional.of(new PyroclasticFlowState(
                signal.volcanoId(),
                signal.source().getCenter(),
                velocity,
                radius,
                heat,
                particulates,
                0L,
                lifetime));
    }

    private static long seedValue(EruptionSignal signal, long gameTick) {
        long idBits = signal.volcanoId().getMostSignificantBits()
                ^ Long.rotateLeft(signal.volcanoId().getLeastSignificantBits(), 31);
        return mix64(idBits ^ signal.source().asLong() ^ gameTick * 0x9E3779B97F4A7C15L);
    }

    private static long mix64(long value) {
        value = (value ^ (value >>> 30)) * 0xBF58476D1CE4E5B9L;
        value = (value ^ (value >>> 27)) * 0x94D049BB133111EBL;
        return value ^ (value >>> 31);
    }

    private static double clamp(double value) {
        return Math.max(0.0, Math.min(1.0, value));
    }
}
