package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.A0041A0060CombatState;
import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import net.minecraft.server.level.ServerPlayer;

/** Server-only transient owner for A0041-A0060. */
public final class A0041A0060RuntimeState {
    private static final A0041A0060CombatState STATE = new A0041A0060CombatState();

    private A0041A0060RuntimeState() {}

    public static A0041A0060CombatState state() {
        return STATE;
    }

    public static CombatPerkRanks ranks(ServerPlayer player) {
        CombatPerkRanks persisted = CombatPerkNodeBinding.ranks(PlayerProgressionRuntime.get(player).passiveNodes());
        CombatPerkRanks effective = CombatPerkAvailabilityRuntime.effectiveRanks(persisted);
        STATE.reconcileForRanks(actorId(player), effective, player.level().getGameTime() * 50L);
        return effective;
    }

    public static String actorId(ServerPlayer player) {
        return player.getUUID().toString();
    }

    public static int mastery(ServerPlayer player, String key) {
        return PlayerProgressionRuntime.get(player).mastery().experience(key);
    }

    public static void clear(ServerPlayer player) {
        STATE.clearActor(actorId(player));
    }

    public static void clearAll() {
        STATE.clearAll();
    }
}
