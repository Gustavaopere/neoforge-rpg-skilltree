package dev.gustavopere.rpgskilltree.runtime.compat;

import com.simibubi.create.content.kinetics.belt.BeltBlockEntity;
import dev.ryanhcode.sable.Sable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;

/**
 * Exact-version forced-transport boundary for A0079.
 *
 * <p>Unknown provider versions and linkage failures deliberately return {@code true}: the
 * stationary perk must fail closed rather than grant a bonus while an unclassified transport
 * system is moving the player.</p>
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
                // Standing inside a moving Sable sub-level is external transport even when the
                // player's local coordinates are unchanged. Local path length must not qualify.
                if (Sable.HELPER.getContaining(player) != null) return true;
            } catch (RuntimeException | LinkageError integrationFailure) {
                return true;
            }
        }

        if (OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.CREATE)) {
            if (!VERIFIED_CREATE_VERSION.equals(OptionalIntegrations.version(OptionalIntegrations.Provider.CREATE))) {
                return true;
            }
            try {
                if (onActiveCreateBelt(player)) return true;
            } catch (RuntimeException | LinkageError integrationFailure) {
                return true;
            }
        }

        return false;
    }

    private static boolean onActiveCreateBelt(ServerPlayer player) {
        BlockPos feet = player.blockPosition();
        return activeBelt(player, feet) || activeBelt(player, feet.below());
    }

    private static boolean activeBelt(ServerPlayer player, BlockPos pos) {
        if (!(player.level().getBlockEntity(pos) instanceof BeltBlockEntity belt)) return false;
        return Math.abs(belt.getSpeed()) >= 1.0F && Math.abs(belt.getBeltMovementSpeed()) > 0.0F;
    }
}
