package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

/** Pure bounded selection of native geothermal effects relevant to nearby observers. */
public final class GeothermalNativeEffects {
    public static final double OBSERVER_QUERY_RADIUS_BLOCKS = 48.0;
    public static final int MAX_HEAT_SOURCES_PER_OBSERVER = 8;

    private GeothermalNativeEffects() {
    }

    public static List<GeothermalSource> dueGeysers(
            VolcanicHeatSourceIndex heatIndex,
            GeothermalSourceRegistry sources,
            List<BlockPos> observers,
            long gameTick,
            int maxObservers,
            int maxGeysers
    ) {
        return dueGeysers(
                heatIndex,
                sources,
                observers,
                gameTick,
                maxObservers,
                maxGeysers,
                1L);
    }

    public static List<GeothermalSource> dueGeysers(
            VolcanicHeatSourceIndex heatIndex,
            GeothermalSourceRegistry sources,
            List<BlockPos> observers,
            long gameTick,
            int maxObservers,
            int maxGeysers,
            long detectionWindowTicks
    ) {
        return dueGeysers(
                heatIndex,
                sources,
                observers,
                gameTick,
                maxObservers,
                maxGeysers,
                detectionWindowTicks,
                source -> true);
    }

    /**
     * Returns unique GEYSER sources whose latest deterministic pulse falls within the bounded
     * detection window, passes {@code admission}, and is near the bounded observer sample.
     * Admission is evaluated before the per-observer result cap, so already-consumed pulses cannot
     * starve pending geysers. No registry-wide scan occurs.
     */
    public static List<GeothermalSource> dueGeysers(
            VolcanicHeatSourceIndex heatIndex,
            GeothermalSourceRegistry sources,
            List<BlockPos> observers,
            long gameTick,
            int maxObservers,
            int maxGeysers,
            long detectionWindowTicks,
            Predicate<GeothermalSource> admission
    ) {
        Objects.requireNonNull(heatIndex, "heatIndex");
        Objects.requireNonNull(sources, "sources");
        Objects.requireNonNull(observers, "observers");
        Objects.requireNonNull(admission, "admission");
        if (gameTick < 0L) {
            throw new IllegalArgumentException("gameTick must be non-negative");
        }
        if (maxObservers < 0 || maxGeysers < 0) {
            throw new IllegalArgumentException("effect bounds must be non-negative");
        }
        if (detectionWindowTicks <= 0L) {
            throw new IllegalArgumentException("detectionWindowTicks must be positive");
        }
        if (observers.isEmpty() || maxObservers == 0 || maxGeysers == 0) {
            return List.of();
        }

        int observerCount = Math.min(maxObservers, observers.size());
        int start = Math.floorMod((int) (gameTick % observers.size()), observers.size());
        Set<UUID> seen = new LinkedHashSet<>();
        List<GeothermalSource> due = new ArrayList<>();

        for (int offset = 0; offset < observerCount && due.size() < maxGeysers; offset++) {
            BlockPos observer = Objects.requireNonNull(
                    observers.get((start + offset) % observers.size()),
                    "observer");
            for (VolcanicHeatSource heat : heatIndex.nearbyMatching(
                    observer,
                    OBSERVER_QUERY_RADIUS_BLOCKS,
                    MAX_HEAT_SOURCES_PER_OBSERVER,
                    gameTick,
                    candidate -> candidate.kind() == VolcanicHeatSource.Kind.GEOTHERMAL
                            && sources.get(candidate.sourceId())
                            .map(source -> source.type() == GeothermalFeatureType.GEYSER
                                    && GeothermalGeyserCycle.forSource(source)
                                    .pulsesWithin(gameTick, detectionWindowTicks)
                                    && admission.test(source))
                            .orElse(false))) {
                if (!seen.add(heat.sourceId())) {
                    continue;
                }
                GeothermalSource source = sources.get(heat.sourceId()).orElse(null);
                if (source != null) {
                    due.add(source);
                    if (due.size() >= maxGeysers) {
                        break;
                    }
                }
            }
        }
        return List.copyOf(due);
    }
}
