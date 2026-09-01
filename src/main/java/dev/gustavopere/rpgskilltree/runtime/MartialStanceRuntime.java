package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.A0061A0080CombatState;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.runtime.compat.A0079ForcedMovementCompat;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.util.FakePlayer;

/** Server-authoritative transition boundary for the RPG-owned MARTIAL_STANCE slot. */
public final class MartialStanceRuntime {
    private MartialStanceRuntime() {}

    public static boolean cycle(ServerPlayer player) {
        if (!eligible(player)) return false;

        CombatPerkRanks ranks = A0061A0080RuntimeState.ranks(player);
        boolean aggressiveAvailable = ranks.rank("A0076") > 0
            && CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0076");
        boolean cautiousAvailable = ranks.rank("A0077") > 0
            && CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0077");

        String actor = A0061A0080RuntimeState.actorId(player);
        A0061A0080CombatState state = A0061A0080RuntimeState.state();
        if (!aggressiveAvailable && !cautiousAvailable) {
            state.resetStance(actor);
            return false;
        }

        A0061A0080CombatState.Stance requested = next(
            state.stance(actor),
            aggressiveAvailable,
            cautiousAvailable
        );
        return state.switchStance(actor, requested, now(player));
    }

    /** Clears stale stance state and invalidates stationary progress on proven/unknown transport. */
    public static void reconcile(ServerPlayer player) {
        if (!eligible(player)) return;
        CombatPerkRanks ranks = A0061A0080RuntimeState.ranks(player);
        A0061A0080CombatState state = A0061A0080RuntimeState.state();
        String actor = A0061A0080RuntimeState.actorId(player);
        A0061A0080CombatState.Stance current = state.stance(actor);
        if (current == A0061A0080CombatState.Stance.AGGRESSIVE && ranks.rank("A0076") <= 0) {
            state.resetStance(actor);
        } else if (current == A0061A0080CombatState.Stance.CAUTIOUS && ranks.rank("A0077") <= 0) {
            state.resetStance(actor);
        }

        if (A0079ForcedMovementCompat.forcedOrUnclassified(player)) {
            A0061A0080RuntimeState.stationary().invalidate(actor);
        }
    }

    private static A0061A0080CombatState.Stance next(
        A0061A0080CombatState.Stance current,
        boolean aggressiveAvailable,
        boolean cautiousAvailable
    ) {
        if (aggressiveAvailable && cautiousAvailable) {
            return switch (current) {
                case NONE -> A0061A0080CombatState.Stance.AGGRESSIVE;
                case AGGRESSIVE -> A0061A0080CombatState.Stance.CAUTIOUS;
                case CAUTIOUS -> A0061A0080CombatState.Stance.NONE;
            };
        }
        if (aggressiveAvailable) {
            return current == A0061A0080CombatState.Stance.AGGRESSIVE
                ? A0061A0080CombatState.Stance.NONE
                : A0061A0080CombatState.Stance.AGGRESSIVE;
        }
        return current == A0061A0080CombatState.Stance.CAUTIOUS
            ? A0061A0080CombatState.Stance.NONE
            : A0061A0080CombatState.Stance.CAUTIOUS;
    }

    private static boolean eligible(ServerPlayer player) {
        return player != null
            && !player.level().isClientSide()
            && !player.isCreative()
            && !player.isSpectator()
            && !(player instanceof FakePlayer);
    }

    private static long now(ServerPlayer player) {
        return Math.multiplyExact(player.level().getGameTime(), 50L);
    }
}
