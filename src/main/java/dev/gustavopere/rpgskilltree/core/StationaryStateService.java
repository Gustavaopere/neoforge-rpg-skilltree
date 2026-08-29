package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Canonical server-authoritative stationary detector used by A0079 and future consumers.
 *
 * <p>A player becomes stationary after 30 consecutive samples whose accumulated 3D path is at
 * most 0.10 block. Identified forced transitions invalidate the state immediately.</p>
 */
public final class StationaryStateService {
    public static final int REQUIRED_TICKS = 30;
    public static final double MAX_PATH_LENGTH = 0.10D;

    private final Map<String, Track> tracks = new HashMap<>();

    public synchronized boolean sample(String actorId, double x, double y, double z, boolean forcedTransition) {
        require(actorId);
        if (!finite(x) || !finite(y) || !finite(z)) throw new IllegalArgumentException("position must be finite");
        Track track = tracks.computeIfAbsent(actorId, ignored -> new Track());
        if (forcedTransition || !track.initialized) {
            track.reset(x, y, z);
            if (forcedTransition) track.consecutiveTicks = 0;
            return false;
        }

        double dx = x - track.x;
        double dy = y - track.y;
        double dz = z - track.z;
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
        track.x = x;
        track.y = y;
        track.z = z;

        if (!Double.isFinite(distance) || track.pathLength + distance > MAX_PATH_LENGTH) {
            track.pathLength = 0.0D;
            track.consecutiveTicks = 1;
            track.stationary = false;
            return false;
        }

        track.pathLength += distance;
        track.consecutiveTicks++;
        track.stationary = track.consecutiveTicks >= REQUIRED_TICKS;
        return track.stationary;
    }

    public synchronized boolean isStationary(String actorId) {
        Track track = tracks.get(require(actorId));
        return track != null && track.stationary;
    }

    public synchronized void invalidate(String actorId) {
        tracks.remove(require(actorId));
    }

    public synchronized void clearAll() {
        tracks.clear();
    }

    private static boolean finite(double value) { return Double.isFinite(value); }

    private static String require(String value) {
        Objects.requireNonNull(value);
        if (value.isBlank()) throw new IllegalArgumentException("blank actor id");
        return value;
    }

    private static final class Track {
        boolean initialized;
        double x;
        double y;
        double z;
        double pathLength;
        int consecutiveTicks;
        boolean stationary;

        void reset(double x, double y, double z) {
            initialized = true;
            this.x = x;
            this.y = y;
            this.z = z;
            pathLength = 0.0D;
            consecutiveTicks = 1;
            stationary = false;
        }
    }
}
