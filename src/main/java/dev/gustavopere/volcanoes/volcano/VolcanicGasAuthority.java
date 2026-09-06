package dev.gustavopere.volcanoes.volcano;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

/**
 * Canonical metadata-only authority for volcanic gas emissions.
 *
 * <p>The existing {@link VolcanicHazardSink} remains the sole work-budgeted eruption consumer.
 * Gas observers subscribe here, so metadata publication cannot change eruption work allocation.</p>
 */
public final class VolcanicGasAuthority {
    private static final VolcanicGasEmissionIndex INDEX = new VolcanicGasEmissionIndex();
    private static final Map<ServerLevel, Boolean> HYDRATED =
            Collections.synchronizedMap(new WeakHashMap<>());

    private VolcanicGasAuthority() {
    }

    static VolcanicGasEmissionLifecycleSink lifecycleSink() {
        return INDEX;
    }

    public static boolean registerLifecycleSink(VolcanicGasEmissionLifecycleSink sink) {
        return INDEX.registerLifecycleSink(Objects.requireNonNull(sink, "sink"));
    }

    public static boolean unregisterLifecycleSink(VolcanicGasEmissionLifecycleSink sink) {
        return INDEX.unregisterLifecycleSink(Objects.requireNonNull(sink, "sink"));
    }

    /** Rebuilds active metadata once from persisted Stage03 eruption state after a level load. */
    public static void hydrate(ServerLevel level) {
        Objects.requireNonNull(level, "level");
        if (!Level.OVERWORLD.equals(level.dimension())) {
            return;
        }
        synchronized (HYDRATED) {
            if (HYDRATED.putIfAbsent(level, Boolean.TRUE) != null) {
                return;
            }
        }

        VolcanoSavedData data = VolcanoSavedData.get(level);
        long gameTick = Math.max(0L, level.getGameTime());
        for (VolcanoSite site : data.all()) {
            data.chamber(site.persistenceId()).flatMap(chamber ->
                    data.eruption(site.persistenceId()).flatMap(event ->
                            VolcanicGasEmissionProjector.project(
                                    EruptionSignal.from(site, chamber, event),
                                    gameTick)))
                    .ifPresentOrElse(
                            INDEX::upsert,
                            () -> INDEX.remove(VolcanicGasEmissionProjector.sourceId(site.persistenceId())));
        }
    }

    /** Removes metadata belonging to one unloading world and permits a later hydration replay. */
    public static void forget(ServerLevel level) {
        Objects.requireNonNull(level, "level");
        synchronized (HYDRATED) {
            HYDRATED.remove(level);
        }
        if (!Level.OVERWORLD.equals(level.dimension())) {
            return;
        }
        for (VolcanoSite site : VolcanoSavedData.get(level).all()) {
            INDEX.remove(VolcanicGasEmissionProjector.sourceId(site.persistenceId()));
        }
    }
}
