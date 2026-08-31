package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.performance.PerformanceConfig;
import dev.gustavopere.volcanoes.performance.PerformanceProfiler;
import dev.gustavopere.volcanoes.tectonics.SeismicEvent;
import dev.gustavopere.volcanoes.tectonics.TectonicRuntime;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;

/** Server-side orchestration for persistent magma lifecycles and bounded detailed eruptions. */
public final class VolcanoLifecycleRuntime {
    static final long SITE_RESCAN_INTERVAL_TICKS = 24_000L;
    private static final int MAX_DUE_UPDATES_PER_TICK = 8;
    private static final int MAX_ERUPTION_ENTITY_WORK_PER_TICK = 4;
    private static final int MAX_QUEUED_BLOCK_WORK_PER_ERUPTION = 256;
    private static final int MAX_QUEUED_ENTITY_WORK_PER_ERUPTION = 16;
    private static final EruptionDispatcher ERUPTION_DISPATCHER = new EruptionDispatcher();
    private static final Map<ServerLevel, RuntimeState> RUNTIMES =
            Collections.synchronizedMap(new WeakHashMap<>());
    private static boolean seismicBridgeRegistered;

    private VolcanoLifecycleRuntime() {
    }

    public static synchronized void registerSeismicBridge() {
        if (seismicBridgeRegistered) {
            return;
        }
        TectonicRuntime.registerDimensionalPerturbationSink(VolcanoLifecycleRuntime::onSeismicEvent);
        seismicBridgeRegistered = true;
    }

    public static boolean registerEruptionSink(EruptionSink sink) {
        return ERUPTION_DISPATCHER.register(sink);
    }

    public static boolean unregisterEruptionSink(EruptionSink sink) {
        return ERUPTION_DISPATCHER.unregister(sink);
    }

    static boolean shouldRunIn(ResourceKey<Level> dimension) {
        return Level.OVERWORLD.equals(dimension);
    }

    static boolean shouldRescanSites(long gameTime) {
        return gameTime >= 0L && Math.floorMod(gameTime, SITE_RESCAN_INTERVAL_TICKS) == 0L;
    }

    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel level) || !shouldRunIn(level.dimension())) {
            return;
        }
        RuntimeState runtime = runtimeFor(level);
        long gameTime = level.getGameTime();
        if (!runtime.initialized() || shouldRescanSites(gameTime)) {
            runtime.discoverSites(gameTime);
        }
        runtime.processDue(level, gameTime);
    }

    private static RuntimeState runtimeFor(ServerLevel level) {
        synchronized (RUNTIMES) {
            return RUNTIMES.computeIfAbsent(
                    level,
                    loadedLevel -> {
                        VolcanoSavedData data = VolcanoSavedData.get(loadedLevel);
                        return new RuntimeState(
                                data,
                                new VolcanoManager(data, TectonicRuntime.serviceForLevel(loadedLevel)));
                    });
        }
    }

    private static void onSeismicEvent(ResourceKey<Level> dimension, SeismicEvent event) {
        if (!shouldRunIn(dimension)) {
            return;
        }
        synchronized (RUNTIMES) {
            for (Map.Entry<ServerLevel, RuntimeState> entry : RUNTIMES.entrySet()) {
                ServerLevel level = entry.getKey();
                if (level != null && dimension.equals(level.dimension())) {
                    entry.getValue().applySeismicEvent(level, event);
                }
            }
        }
    }

    static final class RuntimeState {
        private final VolcanoSavedData data;
        private final VolcanoManager manager;
        private final VolcanoLifecycleStep lifecycleStep;
        private final VolcanoTickScheduler scheduler = new VolcanoTickScheduler();
        private final Set<UUID> knownSites = new HashSet<>();
        private final Map<UUID, Long> lastUpdateTicks = new HashMap<>();
        private boolean initialized;

        RuntimeState(VolcanoSavedData data, VolcanoManager manager) {
            this(
                    data,
                    manager,
                    new EruptionEffectRuntime(
                            new EruptionRuntimeCoordinator(),
                            new EruptionScheduler(
                                    PerformanceConfig.current().ashDepositionBlocksPerTick(),
                                    MAX_ERUPTION_ENTITY_WORK_PER_TICK,
                                    MAX_QUEUED_BLOCK_WORK_PER_ERUPTION,
                                    MAX_QUEUED_ENTITY_WORK_PER_ERUPTION),
                            ERUPTION_DISPATCHER));
        }

        RuntimeState(VolcanoSavedData data, VolcanoManager manager, EruptionEffectRuntime effects) {
            this.data = Objects.requireNonNull(data, "data");
            this.manager = Objects.requireNonNull(manager, "manager");
            this.lifecycleStep = new VolcanoLifecycleStep(manager, Objects.requireNonNull(effects, "effects"));
        }

        boolean initialized() {
            return initialized;
        }

        void discoverSites(long gameTime) {
            for (VolcanoSite site : data.all()) {
                if (!knownSites.add(site.persistenceId())) {
                    continue;
                }
                MagmaChamber chamber = manager.ensureChamber(site.persistenceId());
                scheduler.schedule(site.persistenceId(), site.state(), chamber, gameTime);
                lastUpdateTicks.put(site.persistenceId(), gameTime);
                republishPersistedAsh(site, chamber);
            }
            initialized = true;
        }

        private void republishPersistedAsh(VolcanoSite site, MagmaChamber chamber) {
            UUID persistenceId = site.persistenceId();
            data.eruption(persistenceId).ifPresentOrElse(event -> {
                AshPlumeEmission emission = AshPlumeEmission.from(EruptionSignal.from(site, chamber, event));
                if (emission.active()) {
                    VolcanicHazardWorldRuntime.ashIndex().upsert(emission);
                } else {
                    VolcanicHazardWorldRuntime.ashIndex().remove(emission.sourceId());
                }
            }, () -> VolcanicHazardWorldRuntime.ashIndex().remove(AshPlumeEmission.sourceIdFor(persistenceId)));
        }

        void processDue(ServerLevel level, long gameTime) {
            processDue(level.getSeed(), gameTime);
        }

        void processDue(long worldSeed, long gameTime) {
            List<UUID> due = scheduler.pollDue(gameTime, MAX_DUE_UPDATES_PER_TICK);
            PerformanceProfiler.recordVolcanoUpdates(due.size());
            for (UUID persistenceId : due) {
                VolcanoSite before = data.get(persistenceId).orElse(null);
                if (before == null) {
                    knownSites.remove(persistenceId);
                    lastUpdateTicks.remove(persistenceId);
                    continue;
                }
                long lastUpdate = lastUpdateTicks.getOrDefault(persistenceId, gameTime);
                long elapsedTicks = Math.max(0L, gameTime - lastUpdate);
                VolcanoLifecycleStep.StepResult result = lifecycleStep.advance(
                        worldSeed,
                        data,
                        persistenceId,
                        gameTime,
                        elapsedTicks,
                        0.0);
                lastUpdateTicks.put(persistenceId, gameTime);
                MagmaChamber chamber = data.chamber(persistenceId).orElseThrow();
                scheduler.schedule(persistenceId, result.state(), chamber, gameTime);
            }
        }

        void applySeismicEvent(ServerLevel level, SeismicEvent event) {
            if (manager.onSeismicEvent(event) == 0) {
                return;
            }
            long gameTime = level.getGameTime();
            for (VolcanoSite site : data.all()) {
                if (event.intensityAt(site.center().getX(), site.center().getZ()) <= 0.0) {
                    continue;
                }
                knownSites.add(site.persistenceId());
                lastUpdateTicks.putIfAbsent(site.persistenceId(), gameTime);
                MagmaChamber chamber = data.chamber(site.persistenceId()).orElseThrow();
                scheduler.schedule(site.persistenceId(), site.state(), chamber, gameTime);
            }
        }
    }
}
