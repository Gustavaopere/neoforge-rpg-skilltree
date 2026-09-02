package dev.gustavopere.rpgskilltree.runtime.compat;

import dev.gustavopere.rpgskilltree.runtime.compat.create.A0079CreateTransportCompat;
import dev.gustavopere.rpgskilltree.runtime.compat.sable.A0079SableTransportCompat;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.server.level.ServerPlayer;

/**
 * Exact-version forced/passive movement boundary shared by A0079, A0098 and A0099.
 *
 * <p>Unknown provider versions and linkage failures deliberately return {@code true}: movement
 * perks must fail closed rather than grant a bonus while an unclassified transport system is
 * moving the player. Explicit knockback/teleport receipts latch forced movement while vanilla
 * sprint remains asserted; the latch clears only after the server observes sprint reset.</p>
 */
public final class A0079ForcedMovementCompat {
    private static final String VERIFIED_CREATE_VERSION = "6.0.10";
    private static final String VERIFIED_SABLE_VERSION = "2.0.5";
    private static final Set<UUID> FORCED_UNTIL_SPRINT_RESET = new HashSet<>();

    private A0079ForcedMovementCompat() {}

    public static synchronized void markForcedTransition(ServerPlayer player) {
        if (player != null) FORCED_UNTIL_SPRINT_RESET.add(player.getUUID());
    }

    public static synchronized void clearPlayer(ServerPlayer player) {
        if (player != null) FORCED_UNTIL_SPRINT_RESET.remove(player.getUUID());
    }

    public static synchronized void clearAll() {
        FORCED_UNTIL_SPRINT_RESET.clear();
    }

    public static boolean selfPropelledSprintEligible(ServerPlayer player) {
        if (player == null || !player.isSprinting()) return false;
        if (player.isFallFlying() || player.fallDistance > 0.0F) return false;
        return !forcedOrUnclassified(player);
    }

    public static synchronized boolean forcedOrUnclassified(ServerPlayer player) {
        if (player == null) return true;

        UUID playerId = player.getUUID();
        if (FORCED_UNTIL_SPRINT_RESET.contains(playerId)) {
            if (player.isSprinting()) return true;
            FORCED_UNTIL_SPRINT_RESET.remove(playerId);
        }

        if (player.isPassenger()) return true;

        if (OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.SABLE)) {
            if (!VERIFIED_SABLE_VERSION.equals(OptionalIntegrations.version(OptionalIntegrations.Provider.SABLE))) {
                return true;
            }
            try {
                if (A0079SableTransportCompat.insideMovingSubLevel(player)) return true;
            } catch (RuntimeException | LinkageError integrationFailure) {
                return true;
            }
        }

        if (OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.CREATE)) {
            if (!VERIFIED_CREATE_VERSION.equals(OptionalIntegrations.version(OptionalIntegrations.Provider.CREATE))) {
                return true;
            }
            try {
                if (A0079CreateTransportCompat.onActiveBelt(player)) return true;
            } catch (RuntimeException | LinkageError integrationFailure) {
                return true;
            }
        }

        return false;
    }
}
