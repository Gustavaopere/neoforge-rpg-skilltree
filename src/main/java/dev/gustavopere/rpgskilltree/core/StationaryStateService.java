package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** The single server-side stationary detector shared by A0079 and A0099. */
public final class StationaryStateService {
    public static final int REQUIRED_CONSECUTIVE_TICKS = 30;
    public static final double MAX_ACCUMULATED_PATH = 0.10D;
    private final Map<String, Tracker> trackers = new HashMap<>();

    public synchronized State observe(Sample sample) {
        Objects.requireNonNull(sample);
        validate(sample);
        Tracker previous = trackers.get(sample.actorId());
        if (!sample.serverAuthoritative()) {
            trackers.remove(sample.actorId());
            return State.invalid();
        }
        if (previous == null) {
            trackers.put(sample.actorId(), Tracker.baseline(sample, 1));
            return new State(false, 1, 0.0D);
        }
        boolean transition = sample.teleported() || sample.forcedDisplacement()
            || sample.tick() != previous.tick + 1L
            || !sample.dimensionId().equals(previous.dimensionId)
            || sample.mounted() != previous.mounted;
        if (transition) {
            int nextCount = sample.teleported() || sample.forcedDisplacement()
                || !sample.dimensionId().equals(previous.dimensionId) || sample.mounted() != previous.mounted ? 0 : 1;
            trackers.put(sample.actorId(), Tracker.baseline(sample, nextCount));
            return new State(false, nextCount, 0.0D);
        }

        double dx = sample.x() - previous.x;
        double dy = sample.y() - previous.y;
        double dz = sample.z() - previous.z;
        double path = previous.accumulatedPath + Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (path > MAX_ACCUMULATED_PATH) {
            trackers.put(sample.actorId(), Tracker.baseline(sample, 1));
            return new State(false, 1, 0.0D);
        }
        int consecutive = previous.consecutiveTicks + 1;
        trackers.put(sample.actorId(), new Tracker(
            sample.tick(), sample.dimensionId(), sample.x(), sample.y(), sample.z(), sample.mounted(), consecutive, path
        ));
        return new State(consecutive >= REQUIRED_CONSECUTIVE_TICKS, consecutive, path);
    }

    public synchronized State state(String actorId) {
        Objects.requireNonNull(actorId);
        Tracker tracker = trackers.get(actorId);
        return tracker == null ? State.invalid() : new State(
            tracker.consecutiveTicks >= REQUIRED_CONSECUTIVE_TICKS,
            tracker.consecutiveTicks,
            tracker.accumulatedPath
        );
    }

    public synchronized void invalidate(String actorId) {
        Objects.requireNonNull(actorId);
        trackers.remove(actorId);
    }

    private static void validate(Sample sample) {
        if (sample.actorId().isBlank() || sample.dimensionId().isBlank()) throw new IllegalArgumentException("ids must not be blank");
        if (sample.tick() < 0L) throw new IllegalArgumentException("tick must be non-negative");
        if (!Double.isFinite(sample.x()) || !Double.isFinite(sample.y()) || !Double.isFinite(sample.z())) {
            throw new IllegalArgumentException("coordinates must be finite");
        }
    }

    public record Sample(
        String actorId,
        long tick,
        String dimensionId,
        double x,
        double y,
        double z,
        boolean mounted,
        boolean teleported,
        boolean forcedDisplacement,
        boolean serverAuthoritative
    ) { public Sample { Objects.requireNonNull(actorId); Objects.requireNonNull(dimensionId); } }

    public record State(boolean stationary, int consecutiveTicks, double accumulatedPath) {
        static State invalid() { return new State(false, 0, 0.0D); }
    }

    private record Tracker(
        long tick,
        String dimensionId,
        double x,
        double y,
        double z,
        boolean mounted,
        int consecutiveTicks,
        double accumulatedPath
    ) {
        static Tracker baseline(Sample sample, int consecutiveTicks) {
            return new Tracker(sample.tick(), sample.dimensionId(), sample.x(), sample.y(), sample.z(), sample.mounted(), consecutiveTicks, 0.0D);
        }
    }
}
