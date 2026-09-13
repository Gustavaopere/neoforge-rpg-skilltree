package dev.gustavopere.rpgskilltree.runtime.compat.create;

import com.simibubi.create.content.kinetics.belt.BeltBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;

/** Exact Create-side transport probe. This class is loaded only after the Create version gate. */
public final class A0079CreateTransportCompat {
    private A0079CreateTransportCompat() {}

    public static boolean onActiveBelt(ServerPlayer player) {
        BlockPos feet = player.blockPosition();
        return activeBelt(player, feet) || activeBelt(player, feet.below());
    }

    private static boolean activeBelt(ServerPlayer player, BlockPos pos) {
        if (!(player.level().getBlockEntity(pos) instanceof BeltBlockEntity belt)) return false;
        return Math.abs(belt.getSpeed()) >= 1.0F && Math.abs(belt.getBeltMovementSpeed()) > 0.0F;
    }
}
