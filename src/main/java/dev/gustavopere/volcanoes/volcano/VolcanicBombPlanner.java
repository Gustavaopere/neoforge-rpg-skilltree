package dev.gustavopere.volcanoes.volcano;

import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.SplittableRandom;

/** Deterministic planner for bounded server-authoritative volcanic bomb launches. */
public final class VolcanicBombPlanner {
    public static final double MAX_LAUNCH_SPEED = 2.75;
    private static final double MIN_LAUNCH_SPEED = 1.10;
    private static final double MIN_ELEVATION_RADIANS = Math.toRadians(34.0);
    private static final double MAX_ELEVATION_RADIANS = Math.toRadians(68.0);

    public List<VolcanicBombLaunch> launches(
            EruptionSignal signal,
            EruptionScheduler.WorkGrant workGrant,
            long gameTick
    ) {
        Objects.requireNonNull(signal, "signal");
        Objects.requireNonNull(workGrant, "workGrant");
        if (gameTick < 0L) {
            throw new IllegalArgumentException("gameTick must be non-negative");
        }
        if (!bombProducingPhase(signal.phase()) || signal.intensity() <= 0.0 || workGrant.immediateEntities() <= 0) {
            return List.of();
        }

        double relativeIntensity = clamp(signal.intensity() / signal.profile().peakIntensity());
        int count = Math.min(
                workGrant.immediateEntities(),
                Math.max(1, (int) Math.ceil(workGrant.immediateEntities() * relativeIntensity)));
        double chemistry = explosivity(signal.chamber());
        SplittableRandom random = new SplittableRandom(seed(signal, gameTick));
        List<VolcanicBombLaunch> launches = new ArrayList<>(count);
        Vec3 origin = signal.source().getCenter();

        for (int index = 0; index < count; index++) {
            double azimuth = random.nextDouble(0.0, Math.PI * 2.0);
            double elevation = random.nextDouble(MIN_ELEVATION_RADIANS, MAX_ELEVATION_RADIANS);
            double speedFactor = 0.55 * relativeIntensity + 0.45 * chemistry;
            double speed = Math.min(
                    MAX_LAUNCH_SPEED,
                    MIN_LAUNCH_SPEED + (MAX_LAUNCH_SPEED - MIN_LAUNCH_SPEED) * speedFactor
                            * random.nextDouble(0.82, 1.01));
            double horizontal = Math.cos(elevation) * speed;
            Vec3 velocity = new Vec3(
                    Math.cos(azimuth) * horizontal,
                    Math.sin(elevation) * speed,
                    Math.sin(azimuth) * horizontal);
            long lifetime = 80L + Math.round(100.0 * (0.5 * relativeIntensity + 0.5 * chemistry));
            launches.add(new VolcanicBombLaunch(signal.volcanoId(), origin, velocity, lifetime));
        }
        return List.copyOf(launches);
    }

    private static boolean bombProducingPhase(EruptionPhase phase) {
        return switch (phase) {
            case OPENING, SUSTAINED, WANING -> true;
            case PRECURSORS, DORMANT -> false;
        };
    }

    private static double explosivity(MagmaChamber chamber) {
        MagmaComposition composition = chamber.composition();
        return clamp(
                composition.silicaFraction() * 0.40
                        + composition.volatileRichness() * 0.35
                        + chamber.gasFraction() * 0.25);
    }

    private static long seed(EruptionSignal signal, long gameTick) {
        long idBits = signal.volcanoId().getMostSignificantBits()
                ^ Long.rotateLeft(signal.volcanoId().getLeastSignificantBits(), 29);
        long sourceBits = signal.source().asLong();
        long tickBits = gameTick * 0x9E3779B97F4A7C15L;
        return mix64(idBits ^ Long.rotateLeft(sourceBits, 17) ^ tickBits ^ signal.phase().ordinal());
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
