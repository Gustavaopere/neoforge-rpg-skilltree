package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.volcano.AshEmissionLifecycleSink;
import dev.gustavopere.volcanoes.volcano.AshPlumeEmission;
import dev.gustavopere.volcanoes.volcano.VolcanicGasAuthority;
import dev.gustavopere.volcanoes.volcano.VolcanicGasEmission;
import dev.gustavopere.volcanoes.volcano.VolcanicGasEmissionLifecycleSink;
import dev.gustavopere.volcanoes.volcano.VolcanicHazardWorldRuntime;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.function.Function;

/**
 * Periodic pollution router fed by the same authoritative Stage03 gas and ash lifecycle metadata
 * used by Atmosphere.
 *
 * <p>The existing Atmosphere gas/ash bridges remain the standalone respiratory/environmental
 * fallback. This router therefore uses a no-op internal callback: publishing the same load back
 * into Atmosphere here would double-apply the source. When an optional host is authoritative the
 * coordinator publishes externally instead.</p>
 */
public final class VolcanicPollutionRuntime {
    static final int MAX_PULSES_PER_INTERVAL = 64;

    private static final SourceBridge SOURCES = new SourceBridge();
    private static final Map<ServerLevel, LevelState> LEVEL_STATES =
            Collections.synchronizedMap(new WeakHashMap<>());
    private static volatile Function<ServerLevel, PollutionAdapter> adapterFactory =
            ignored -> PollutionAdapter.none();
    private static boolean gasRegistered;
    private static boolean ashRegistered;

    private VolcanicPollutionRuntime() {
    }

    public static synchronized void register() {
        if (!gasRegistered) {
            gasRegistered = VolcanicGasAuthority.registerLifecycleSink(SOURCES);
        }
        if (!ashRegistered) {
            ashRegistered = VolcanicHazardWorldRuntime.registerAshEmissionLifecycleSink(SOURCES);
        }
    }

    public static synchronized void installAdapterFactory(Function<ServerLevel, PollutionAdapter> factory) {
        adapterFactory = Objects.requireNonNull(factory, "factory");
        synchronized (LEVEL_STATES) {
            LEVEL_STATES.clear();
        }
    }

    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel level) || !Level.OVERWORLD.equals(level.dimension())) {
            return;
        }
        long gameTime = level.getGameTime();
        if (!AtmosphereRuntime.shouldProcessDiffusion(gameTime)) {
            return;
        }
        VolcanicGasAuthority.hydrate(level);

        List<PollutionEmission> pulses = SOURCES.pulses(
                level.dimension().location().toString(),
                gameTime,
                AtmosphereRuntime.DIFFUSION_INTERVAL_TICKS);
        if (pulses.isEmpty()) {
            return;
        }
        LevelState state = stateFor(level);
        int count = Math.min(MAX_PULSES_PER_INTERVAL, pulses.size());
        int start = selectionStart(gameTime, pulses.size(), count);
        for (int offset = 0; offset < count; offset++) {
            PollutionEmission pulse = pulses.get((start + offset) % pulses.size());
            if (!state.route(pulse)) {
                break;
            }
        }
    }

    public static void onLevelUnload(LevelEvent.Unload event) {
        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }
        synchronized (LEVEL_STATES) {
            LEVEL_STATES.remove(level);
        }
    }

    static int selectionStart(long gameTime, int pulseCount, int limit) {
        if (pulseCount <= 0 || limit <= 0 || limit >= pulseCount) {
            return 0;
        }
        long bucket = gameTime / AtmosphereRuntime.DIFFUSION_INTERVAL_TICKS;
        return (int) Math.floorMod(bucket * (long) limit, (long) pulseCount);
    }

    private static LevelState stateFor(ServerLevel level) {
        synchronized (LEVEL_STATES) {
            return LEVEL_STATES.computeIfAbsent(level, ignored ->
                    new LevelState(new PollutionCoordinator(adapterFactory.apply(level))));
        }
    }

    private static final class LevelState {
        private final PollutionCoordinator coordinator;

        private LevelState(PollutionCoordinator coordinator) {
            this.coordinator = Objects.requireNonNull(coordinator, "coordinator");
        }

        /** Returns false after an optional-host failure so the current interval stops immediately. */
        private boolean route(PollutionEmission pulse) {
            try {
                coordinator.route(pulse, ignored -> {
                    // Atmosphere gas/ash bridges already own the standalone fallback for this source.
                });
                return true;
            } catch (RuntimeException | LinkageError optionalHostFailure) {
                return false;
            }
        }
    }

    private static final class SourceBridge
            implements VolcanicGasEmissionLifecycleSink, AshEmissionLifecycleSink {
        private final Map<UUID, VolcanicGasEmission> gases = new LinkedHashMap<>();
        private final Map<UUID, AshPlumeEmission> ashes = new LinkedHashMap<>();

        @Override
        public void upsert(VolcanicGasEmission emission) {
            VolcanicGasEmission value = Objects.requireNonNull(emission, "emission");
            synchronized (this) {
                gases.put(value.sourceId(), value);
            }
        }

        @Override
        public void upsert(AshPlumeEmission emission) {
            AshPlumeEmission value = Objects.requireNonNull(emission, "emission");
            synchronized (this) {
                if (value.active()) {
                    ashes.put(value.sourceId(), value);
                } else {
                    ashes.remove(value.sourceId());
                }
            }
        }

        @Override
        public void remove(UUID sourceId) {
            UUID id = Objects.requireNonNull(sourceId, "sourceId");
            synchronized (this) {
                gases.remove(id);
                ashes.remove(id);
            }
        }

        private synchronized List<PollutionEmission> pulses(
                String dimensionId,
                long gameTime,
                int intervalTicks
        ) {
            List<PollutionEmission> result = new ArrayList<>(gases.size() + ashes.size());
            for (VolcanicGasEmission gas : gases.values()) {
                VolcanicPollutionPulseFactory.gasPulse(dimensionId, gas, gameTime, intervalTicks)
                        .ifPresent(result::add);
            }
            for (AshPlumeEmission ash : ashes.values()) {
                VolcanicPollutionPulseFactory.ashPulse(dimensionId, ash, gameTime, intervalTicks)
                        .ifPresent(result::add);
            }
            result.sort(Comparator.comparing(PollutionEmission::id));
            return List.copyOf(result);
        }
    }
}
