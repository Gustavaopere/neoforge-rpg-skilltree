package dev.gustavopere.rpgskilltree.runtime.compat;

import dev.gustavopere.rpgskilltree.core.ArcaneAccessPolicy;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

/** Runtime-facing shared magic gate. Keeps provider adapters consistent. */
public final class MagicAccessRuntime {
    private static final Component LOCKED_MESSAGE = Component.literal(
        "Desbloqueie o Despertar Arcano na Árvore de Habilidades RPG para conjurar."
    );

    private MagicAccessRuntime() {}

    public static boolean hasArcaneAccess(ServerPlayer player) {
        return player.isCreative()
            || ArcaneAccessPolicy.canCast(PlayerProgressionRuntime.get(player).passiveNodes());
    }

    public static boolean requireArcaneAccess(ServerPlayer player) {
        if (hasArcaneAccess(player)) return true;
        player.displayClientMessage(LOCKED_MESSAGE, true);
        return false;
    }
}
