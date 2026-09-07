package dev.gustavopere.rpgskilltree.runtime.compat.sable;

import dev.ryanhcode.sable.Sable;
import net.minecraft.server.level.ServerPlayer;

/** Exact Sable-side transport probe. This class is loaded only after the Sable version gate. */
public final class A0079SableTransportCompat {
    private A0079SableTransportCompat() {}

    public static boolean insideMovingSubLevel(ServerPlayer player) {
        return Sable.HELPER.getContaining(player) != null;
    }
}
