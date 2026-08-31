package dev.gustavopere.volcanoes.volcano;

import java.util.Objects;
import java.util.UUID;

/** Deterministic native pulse cadence for one persistent geyser source. */
public record GeothermalGeyserCycle(long periodTicks, long phaseTicks) {
    private static final long MIN_PERIOD_TICKS = 600L;
    private static final long PERIOD_VARIATION_TICKS = 600L;
    private static final long PHASE_SALT = 0x9E3779B97F4A7C15L;

    public GeothermalGeyserCycle {
        if (periodTicks < MIN_PERIOD_TICKS || periodTicks > MIN_PERIOD_TICKS + PERIOD_VARIATION_TICKS) {
            throw new IllegalArgumentException("periodTicks is outside the native geyser cadence bounds");
        }
        if (phaseTicks < 0L || phaseTicks >= periodTicks) {
            throw new IllegalArgumentException("phaseTicks must be within [0, periodTicks)");
        }
    }

    public static GeothermalGeyserCycle forSource(GeothermalSource source) {
        Objects.requireNonNull(source, "source");
        if (source.type() != GeothermalFeatureType.GEYSER) {
            throw new IllegalArgumentException("native geyser cadence requires a GEYSER source");
        }
        UUID id = source.persistenceId();
        long identity = id.getMostSignificantBits() ^ Long.rotateLeft(id.getLeastSignificantBits(), 17);
        long periodHash = mix64(identity);
        long period = MIN_PERIOD_TICKS
                + Math.floorMod(periodHash, PERIOD_VARIATION_TICKS + 1L);
        long phase = Math.floorMod(mix64(periodHash ^ PHASE_SALT), period);
        return new GeothermalGeyserCycle(period, phase);
    }

    public boolean pulsesAt(long gameTick) {
        if (gameTick < 0L) {
            throw new IllegalArgumentException("gameTick must be non-negative");
        }
        return Math.floorMod(gameTick - phaseTicks, periodTicks) == 0L;
    }

    /** Latest non-negative scheduled pulse tick, or {@code -1} when the first pulse has not occurred yet. */
    public long latestPulseTickAtOrBefore(long gameTick) {
        if (gameTick < 0L) {
            throw new IllegalArgumentException("gameTick must be non-negative");
        }
        if (gameTick < phaseTicks) {
            return -1L;
        }
        long cycles = Math.floorDiv(gameTick - phaseTicks, periodTicks);
        return phaseTicks + cycles * periodTicks;
    }

    /**
     * True when the latest scheduled pulse occurred within the last {@code detectionWindowTicks}.
     * A bounded observer sweep can therefore recover a pulse missed on its exact tick.
     */
    public boolean pulsesWithin(long gameTick, long detectionWindowTicks) {
        if (detectionWindowTicks <= 0L) {
            throw new IllegalArgumentException("detectionWindowTicks must be positive");
        }
        long latest = latestPulseTickAtOrBefore(gameTick);
        return latest >= 0L && gameTick - latest < detectionWindowTicks;
    }

    private static long mix64(long value) {
        value ^= value >>> 33;
        value *= 0xff51afd7ed558ccdL;
        value ^= value >>> 33;
        value *= 0xc4ceb9fe1a85ec53L;
        value ^= value >>> 33;
        return value;
    }
}
