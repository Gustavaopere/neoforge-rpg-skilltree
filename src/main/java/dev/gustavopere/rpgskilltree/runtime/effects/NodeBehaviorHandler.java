package dev.gustavopere.rpgskilltree.runtime.effects;

import dev.gustavopere.rpgskilltree.core.ResolvedNodeBehaviorEffect;
import net.minecraft.server.level.ServerPlayer;

/** Runtime adapter for one server-side behavioral node-effect provider. */
public interface NodeBehaviorHandler {
    /** False when an optional provider/mod required by this handler is not available. */
    boolean available();

    void apply(ServerPlayer player, ResolvedNodeBehaviorEffect effect);

    void remove(ServerPlayer player, ResolvedNodeBehaviorEffect effect);
}
