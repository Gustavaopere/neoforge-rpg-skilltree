package dev.gustavopere.rpgskilltree.runtime.compat.identity2;

import dev.gustavopere.rpgskilltree.core.MorphHostilityMemory;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import net.minecraft.server.level.ServerPlayer;

/** Session-scoped fallback cache for faction disguise break memory. */
public final class MorphHostilityMemoryRuntime {
    private static final Map<ServerPlayer, MorphHostilityMemory> MEMORIES =
        Collections.synchronizedMap(new WeakHashMap<>());

    private MorphHostilityMemoryRuntime() {}

    public static MorphHostilityMemory memory(ServerPlayer player) {
        if (player == null) return MorphHostilityMemory.empty();
        long now = System.currentTimeMillis();
        synchronized (MEMORIES) {
            MorphHostilityMemory memory = MEMORIES.getOrDefault(player, MorphHostilityMemory.empty()).prune(now);
            if (memory.compromisedUntilMillis().isEmpty()) MEMORIES.remove(player);
            else MEMORIES.put(player, memory);
            return memory;
        }
    }

    public static void compromise(ServerPlayer player, Set<String> factions) {
        if (player == null || factions == null || factions.isEmpty()) return;
        long now = System.currentTimeMillis();
        long durationMillis = Math.multiplyExact((long) MorphCategoryCatalog.hostilityMemorySeconds(), 1_000L);
        synchronized (MEMORIES) {
            MorphHostilityMemory current = MEMORIES.getOrDefault(player, MorphHostilityMemory.empty());
            MEMORIES.put(player, current.compromise(factions, now, durationMillis));
        }
    }

    public static void clear(ServerPlayer player) {
        if (player != null) MEMORIES.remove(player);
    }
}
