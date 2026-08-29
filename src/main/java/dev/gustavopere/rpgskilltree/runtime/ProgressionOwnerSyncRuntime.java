package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CanonicalPlayerAttachmentData;
import dev.gustavopere.rpgskilltree.core.CoreProgressionBootstrap;
import dev.gustavopere.rpgskilltree.core.CoreProgressionState;
import dev.gustavopere.rpgskilltree.core.ProgressionDirtyReason;
import dev.gustavopere.rpgskilltree.core.ProgressionDirtySet;
import dev.gustavopere.rpgskilltree.core.ProgressionRulesSnapshot;
import dev.gustavopere.rpgskilltree.core.ProgressionSyncCoalescer;
import dev.gustavopere.rpgskilltree.runtime.data.CoreProgressionRulesCatalog;
import dev.gustavopere.rpgskilltree.runtime.network.ModNetworking;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

/**
 * Runtime queue that turns many confirmed progression mutations in one tick into
 * one final owner synchronization per affected progression section.
 *
 * <p>Login/respawn/dimension lifecycle synchronization remains immediate. This
 * queue is fed only by committed canonical mutations.</p>
 */
public final class ProgressionOwnerSyncRuntime {
    private static final Object LOCK = new Object();
    private static final ProgressionSyncCoalescer COALESCER = new ProgressionSyncCoalescer();
    private static final Map<UUID, EnumSet<ProgressionMutationEvent.Section>> SECTIONS = new HashMap<>();
    private static boolean initialized;

    private ProgressionOwnerSyncRuntime() {}

    /** Installs the canonical mutation-event subscription exactly once for the mod lifetime. */
    public static void initialize() {
        synchronized (LOCK) {
            if (initialized) return;
            ProgressionMutationEvents.subscribe(ProgressionOwnerSyncRuntime::mark);
            initialized = true;
        }
    }

    public static void mark(ProgressionMutationEvent event) {
        Objects.requireNonNull(event, "event");
        synchronized (LOCK) {
            COALESCER.mark(
                event.playerId(),
                ProgressionDirtySet.of(ProgressionDirtyReason.PERSISTENT_STATE)
            );
            SECTIONS.computeIfAbsent(
                event.playerId(),
                ignored -> EnumSet.noneOf(ProgressionMutationEvent.Section.class)
            ).add(event.section());
        }
    }

    /** Flushes one final current snapshot for every player dirtied since the previous flush. */
    public static void flush(MinecraftServer server) {
        Objects.requireNonNull(server, "server");

        Map<UUID, ProgressionDirtySet> dirty;
        Map<UUID, Set<ProgressionMutationEvent.Section>> sections = new HashMap<>();
        synchronized (LOCK) {
            dirty = COALESCER.drainAll();
            for (UUID playerId : dirty.keySet()) {
                EnumSet<ProgressionMutationEvent.Section> selected = SECTIONS.remove(playerId);
                if (selected != null && !selected.isEmpty()) {
                    sections.put(playerId, Set.copyOf(selected));
                }
            }
        }

        for (UUID playerId : dirty.keySet()) {
            ServerPlayer player = server.getPlayerList().getPlayer(playerId);
            if (player == null) continue;

            Set<ProgressionMutationEvent.Section> selected = sections.getOrDefault(playerId, Set.of());
            if (selected.isEmpty()) continue;

            CanonicalPlayerAttachmentData current = CanonicalPlayerAttachmentRuntime.observe(player);
            if (selected.contains(ProgressionMutationEvent.Section.COMPATIBILITY)) {
                ModNetworking.syncToOwner(player, current.compatibilityProgression());
            }
            if (selected.contains(ProgressionMutationEvent.Section.CORE)) {
                syncCoreIfAvailable(player, current);
            }
        }
    }

    private static void syncCoreIfAvailable(
        ServerPlayer player,
        CanonicalPlayerAttachmentData current
    ) {
        var core = current.coreProgression();
        if (!core.isInitialized()) return;
        var currentRules = CoreProgressionRulesCatalog.current();
        if (currentRules.isEmpty()) return;

        ProgressionRulesSnapshot rules = currentRules.orElseThrow();
        CoreProgressionState state = core.state().orElseThrow();
        CoreProgressionBootstrap.resume(state, rules);
        ModNetworking.syncCoreToOwner(player, state, rules);
    }

    public static void clear(UUID playerId) {
        Objects.requireNonNull(playerId, "playerId");
        synchronized (LOCK) {
            COALESCER.clear(playerId);
            SECTIONS.remove(playerId);
        }
    }

    public static void clearAll() {
        synchronized (LOCK) {
            COALESCER.clear();
            SECTIONS.clear();
        }
    }
}
