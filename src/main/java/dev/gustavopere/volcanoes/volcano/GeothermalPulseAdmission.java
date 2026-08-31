package dev.gustavopere.volcanoes.volcano;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/** Pure admission rule for bounded native-geyser pulse recovery and deduplication. */
final class GeothermalPulseAdmission {
    private GeothermalPulseAdmission() {
    }

    static boolean isPending(
            GeothermalSource source,
            long gameTick,
            long activatedAtGameTick,
            Map<UUID, Long> lastPulseTicks
    ) {
        Objects.requireNonNull(source, "source");
        Objects.requireNonNull(lastPulseTicks, "lastPulseTicks");
        if (gameTick < 0L || activatedAtGameTick < 0L) {
            throw new IllegalArgumentException("game ticks must be non-negative");
        }
        if (gameTick < activatedAtGameTick) {
            return false;
        }

        long scheduledPulseTick = GeothermalGeyserCycle.forSource(source).latestPulseTickAtOrBefore(gameTick);
        if (scheduledPulseTick < activatedAtGameTick) {
            return false;
        }
        Long previousPulseTick = lastPulseTicks.get(source.persistenceId());
        return previousPulseTick == null || previousPulseTick < scheduledPulseTick;
    }
}
