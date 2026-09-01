package dev.gustavopere.rpgskilltree.runtime.compat;

import dev.gustavopere.rpgskilltree.runtime.compat.create.A0079CreateTransportCompat;
import dev.gustavopere.rpgskilltree.runtime.compat.sable.A0079SableTransportCompat;
import net.minecraft.server.level.ServerPlayer;

/**
 * Exact-version forced-transport boundary for A0079.
 *
 * <p>Unknown provider versions and linkage failures deliberately return {@code true}: the
 * stationary perk must fail closed rather than grant a bonus while an unclassified transport
 * system is moving the player. Provider API types remain isolated behind their version gates.</p>
 */
public final class A0079ForcedMovementCompat {
    private static final String VERIFIED_CREATE_VERSION = "6.0.10";
    private static final String VERIFIED_SABLE_VERSION = "2.0.5";

    private A0079ForcedMovementCompat() {}

    public static boolean forcedOrUnclassified(ServerPlayer player) {
        if (player == null) return true;

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
