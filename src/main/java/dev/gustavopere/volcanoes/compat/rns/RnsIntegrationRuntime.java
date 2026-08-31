package dev.gustavopere.volcanoes.compat.rns;

import dev.gustavopere.volcanoes.geology.DepositRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Production lifecycle bridge for the optional Create: Rock & Stone projection adapter.
 *
 * <p>The host integration is attached to the authoritative per-level {@link DepositRegistry}, not
 * to a test-owned registry. Installation is retried only for a short bounded startup window so the
 * exact RNS/KubeJS custom-projection surface can settle without turning host probing into a permanent
 * hot path. Native RNS worldgen remains enabled; this runtime contributes prospecting metadata only
 * for already-authoritative Volcanoes hydrothermal deposits.</p>
 */
public final class RnsIntegrationRuntime {
    static final int INSTALL_RETRY_INTERVAL_TICKS = 20;
    static final int MAX_INSTALL_ATTEMPTS = 20;

    private static final Map<ServerLevel, LevelState> STATES =
            Collections.synchronizedMap(new IdentityHashMap<>());
    private static boolean registered;

    private RnsIntegrationRuntime() {
    }

    public static synchronized void register() {
        if (registered) {
            return;
        }
        registered = true;
        NeoForge.EVENT_BUS.addListener(RnsIntegrationRuntime::onLevelTick);
        NeoForge.EVENT_BUS.addListener(RnsIntegrationRuntime::onLevelUnload);
    }

    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel level)
                || !Level.OVERWORLD.equals(level.dimension())) {
            return;
        }

        LevelState state = stateFor(level);
        if (state.resolved || state.bridge != null || state.attempts >= MAX_INSTALL_ATTEMPTS) {
            return;
        }

        long gameTick = level.getGameTime();
        if (gameTick < state.nextAttemptTick) {
            return;
        }

        if (!ModList.get().isLoaded(RnsCompat.MOD_ID)) {
            state.resolved = true;
            return;
        }

        state.attempts++;
        Optional<RnsDepositLifecycleBridge> installed =
                RnsCompat.installIfAvailable(level, DepositRegistry.get(level));
        if (installed.isPresent()) {
            state.bridge = installed.orElseThrow();
            state.resolved = true;
            return;
        }

        state.nextAttemptTick = gameTick + INSTALL_RETRY_INTERVAL_TICKS;
        if (state.attempts >= MAX_INSTALL_ATTEMPTS) {
            state.resolved = true;
        }
    }

    public static void onLevelUnload(LevelEvent.Unload event) {
        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }

        LevelState removed;
        synchronized (STATES) {
            removed = STATES.remove(level);
        }
        if (removed == null || removed.bridge == null) {
            return;
        }

        try {
            DepositRegistry.get(level).unregisterLifecycleSink(removed.bridge);
        } catch (RuntimeException | LinkageError ignored) {
            // Optional host teardown must never become a core world-unload failure.
        }
    }

    static int attemptsFor(ServerLevel level) {
        synchronized (STATES) {
            LevelState state = STATES.get(level);
            return state == null ? 0 : state.attempts;
        }
    }

    private static LevelState stateFor(ServerLevel level) {
        synchronized (STATES) {
            return STATES.computeIfAbsent(level, ignored -> new LevelState());
        }
    }

    private static final class LevelState {
        private int attempts;
        private long nextAttemptTick;
        private boolean resolved;
        private RnsDepositLifecycleBridge bridge;
    }
}
