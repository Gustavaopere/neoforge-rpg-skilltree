package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.A0101A0110DefenseState;
import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import net.minecraft.server.level.ServerPlayer;

/** Server-only owner for state introduced by the canonical A0101-A0110 implementation batch. */
public final class A0101A0110RuntimeState {
    private static final A0101A0110DefenseState DEFENSE = new A0101A0110DefenseState();

    private A0101A0110RuntimeState() {}

    public static A0101A0110DefenseState defense() {
        return DEFENSE;
    }

    public static CombatPerkRanks ranks(ServerPlayer player) {
        CombatPerkRanks persisted = CombatPerkNodeBinding.ranks(PlayerProgressionRuntime.get(player).passiveNodes());
        CombatPerkRanks effective = CombatPerkAvailabilityRuntime.effectiveRanks(persisted);
        DEFENSE.reconcileRanks(
            actorId(player),
            effective.rank("A0104") > 0,
            effective.rank("A0105") > 0,
            effective.rank("A0106") > 0
        );
        return effective;
    }

    public static String actorId(ServerPlayer player) {
        return player.getUUID().toString();
    }

    /**
     * Ordinary player-object boundaries reconcile active windows but preserve cooldown deadlines so
     * death, dimension transfer and reconnect cannot be used as a cooldown reset.
     */
    public static void reconcilePlayerBoundary(ServerPlayer player) {
        DEFENSE.reconcilePlayerBoundary(actorId(player));
    }

    public static void clearAll() {
        DEFENSE.clearAll();
    }
}
