package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.A0061A0080CombatState;
import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.StationaryStateService;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.server.level.ServerPlayer;

/** Server-only runtime owner for transient A0061-A0080 state. */
public final class A0061A0080RuntimeState {
    private static final A0061A0080CombatState STATE = new A0061A0080CombatState();
    private static final StationaryStateService STATIONARY = new StationaryStateService();
    private static final Map<String, Map<String, Integer>> LAST_EFFECTIVE_RANKS = new HashMap<>();

    private A0061A0080RuntimeState() {}

    public static A0061A0080CombatState state() { return STATE; }
    public static StationaryStateService stationary() { return STATIONARY; }

    /**
     * Returns only structurally available ranks and invalidates all transient combat state whenever
     * the effective rank snapshot changes after first observation. This covers purchase/respec,
     * rank loss, reconcile and rules/availability changes without allowing an old timed window or
     * stance to reappear after the progression mutation.
     */
    public static synchronized CombatPerkRanks ranks(ServerPlayer player) {
        CombatPerkRanks persisted = CombatPerkNodeBinding.ranks(PlayerProgressionRuntime.get(player).passiveNodes());
        CombatPerkRanks effective = CombatPerkAvailabilityRuntime.effectiveRanks(persisted);
        String actor = actorId(player);
        Map<String, Integer> current = effective.ranks();
        Map<String, Integer> previous = LAST_EFFECTIVE_RANKS.put(actor, current);
        if (previous != null && !previous.equals(current)) {
            STATE.clearActor(actor);
            STATIONARY.invalidate(actor);
        }
        return effective;
    }

    public static String actorId(ServerPlayer player) { return player.getUUID().toString(); }

    public static boolean isStationary(ServerPlayer player) {
        return STATIONARY.isStationary(actorId(player));
    }

    public static synchronized void clear(ServerPlayer player) {
        String actor = actorId(player);
        LAST_EFFECTIVE_RANKS.remove(actor);
        STATE.clearActor(actor);
        STATIONARY.invalidate(actor);
    }

    public static synchronized void clearAll() {
        LAST_EFFECTIVE_RANKS.clear();
        STATE.clearAll();
        STATIONARY.clearAll();
    }
}
