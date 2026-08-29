package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.A0061A0080CombatState;
import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.StationaryStateService;
import net.minecraft.server.level.ServerPlayer;

/** Server-only runtime owner for transient A0061-A0080 state. */
public final class A0061A0080RuntimeState {
    private static final A0061A0080CombatState STATE = new A0061A0080CombatState();
    private static final StationaryStateService STATIONARY = new StationaryStateService();

    private A0061A0080RuntimeState() {}

    public static A0061A0080CombatState state() { return STATE; }
    public static StationaryStateService stationary() { return STATIONARY; }

    public static CombatPerkRanks ranks(ServerPlayer player) {
        return CombatPerkNodeBinding.ranks(PlayerProgressionRuntime.get(player).passiveNodes());
    }

    public static String actorId(ServerPlayer player) { return player.getUUID().toString(); }

    public static boolean isStationary(ServerPlayer player) {
        return STATIONARY.isStationary(actorId(player));
    }

    public static void clear(ServerPlayer player) {
        String actor = actorId(player);
        STATE.clearActor(actor);
        STATIONARY.invalidate(actor);
    }

    public static void clearAll() {
        STATE.clearAll();
        STATIONARY.clearAll();
    }
}
