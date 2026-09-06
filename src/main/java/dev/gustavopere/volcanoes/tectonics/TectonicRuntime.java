package dev.gustavopere.volcanoes.tectonics;

import dev.gustavopere.volcanoes.performance.PerformanceProfiler;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/** Server-side orchestration for coarse tectonic evolution and safe seismic releases. */
public final class TectonicRuntime {
    static final int PROCESS_INTERVAL_TICKS = TectonicStressService.DEFAULT_UPDATE_INTERVAL_TICKS;
    private static final int MAX_ACTIVE_PLAYER_SAMPLES = 32;
    private static final PlateField RAW_PLATE_FIELD = new VoronoiPlateField();
    private static final PlateField PLATE_FIELD = (worldSeed, x, z) -> {
        PerformanceProfiler.recordPlateSample();
        return RAW_PLATE_FIELD.sample(worldSeed, x, z);
    };
    private static final CopyOnWriteArrayList<SeismicPerturbationSink> PERTURBATION_SINKS =
            new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<DimensionalSeismicPerturbationSink> DIMENSIONAL_PERTURBATION_SINKS =
            new CopyOnWriteArrayList<>();
    private static final Map<ServerLevel, TectonicStressService> SERVICES =
            Collections.synchronizedMap(new WeakHashMap<>());

    private TectonicRuntime() {
    }

    static boolean shouldProcess(long gameTime) {
        return gameTime >= 0L && Math.floorMod(gameTime, (long) PROCESS_INTERVAL_TICKS) == 0L;
    }

    public static AutoCloseable registerPerturbationSink(SeismicPerturbationSink sink) {
        Objects.requireNonNull(sink, "sink");
        PERTURBATION_SINKS.addIfAbsent(sink);
        return () -> PERTURBATION_SINKS.remove(sink);
    }

    public static AutoCloseable registerDimensionalPerturbationSink(DimensionalSeismicPerturbationSink sink) {
        Objects.requireNonNull(sink, "sink");
        DIMENSIONAL_PERTURBATION_SINKS.addIfAbsent(sink);
        return () -> DIMENSIONAL_PERTURBATION_SINKS.remove(sink);
    }

    static int dispatchPerturbations(SeismicEvent event) {
        Objects.requireNonNull(event, "event");
        return new SeismicEventDispatcher(List.copyOf(PERTURBATION_SINKS))
                .dispatch(event)
                .perturbationSinksNotified();
    }

    public static int dispatchDimensionalPerturbations(ResourceKey<Level> dimension, SeismicEvent event) {
        Objects.requireNonNull(dimension, "dimension");
        Objects.requireNonNull(event, "event");
        for (DimensionalSeismicPerturbationSink sink : DIMENSIONAL_PERTURBATION_SINKS) {
            sink.onSeismicEvent(dimension, event);
        }
        return DIMENSIONAL_PERTURBATION_SINKS.size();
    }

    public static TectonicService serviceForLevel(ServerLevel level) {
        return serviceFor(Objects.requireNonNull(level, "level"));
    }

    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }
        long gameTime = level.getGameTime();
        if (!shouldProcess(gameTime)) {
            return;
        }

        TectonicStressService service = serviceFor(level);
        long worldSeed = level.getSeed();
        List<ServerPlayer> sampledPlayers = boundedPlayers(level.players());
        for (ServerPlayer player : sampledPlayers) {
            service.sample(worldSeed, player.getX(), player.getZ());
        }
        service.tick(worldSeed, gameTime);
        for (ServerPlayer player : sampledPlayers) {
            service.tryReleaseStress(worldSeed, player.getX(), player.getZ()).ifPresent(eventData -> {
                dispatchPerturbations(eventData);
                dispatchDimensionalPerturbations(level.dimension(), eventData);
                SeismicServerEffects.apply(level, eventData);
            });
        }
    }

    private static TectonicStressService serviceFor(ServerLevel level) {
        synchronized (SERVICES) {
            return SERVICES.computeIfAbsent(
                    level,
                    loadedLevel -> new TectonicStressService(
                            PLATE_FIELD,
                            TectonicRegionState.get(loadedLevel)));
        }
    }

    private static List<ServerPlayer> boundedPlayers(List<ServerPlayer> players) {
        int count = Math.min(MAX_ACTIVE_PLAYER_SAMPLES, players.size());
        if (count == 0) {
            return List.of();
        }
        return new ArrayList<>(players.subList(0, count));
    }
}
