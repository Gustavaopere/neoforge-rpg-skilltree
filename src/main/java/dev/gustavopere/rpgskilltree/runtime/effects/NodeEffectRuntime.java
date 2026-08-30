package dev.gustavopere.rpgskilltree.runtime.effects;

import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.runtime.data.NodeEffectCatalog;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

/**
 * Canonical server-side boundary for applying all effects derived from learned skill-tree nodes.
 * Attribute modifiers and behavioral handlers are reconciled together from the same progression
 * snapshot so purchase, respec, login and datapack reload cannot leave partial effect state.
 */
public final class NodeEffectRuntime {
    private static final NodeBehaviorHandlerRegistry BEHAVIOR_HANDLERS = new NodeBehaviorHandlerRegistry();
    private static final BehaviorNodeEffectRuntime BEHAVIOR_RUNTIME = new BehaviorNodeEffectRuntime(BEHAVIOR_HANDLERS);

    private NodeEffectRuntime() {}

    public static void registerBehaviorHandler(ResourceLocation handlerId, NodeBehaviorHandler handler) {
        BEHAVIOR_HANDLERS.register(
            Objects.requireNonNull(handlerId, "handlerId"),
            Objects.requireNonNull(handler, "handler")
        );
    }

    public static void refresh(ServerPlayer player, ProgressionState state) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(state, "state");
        AttributeNodeEffectRuntime.refresh(player, state);
        BEHAVIOR_RUNTIME.refresh(player, state, NodeEffectCatalog.behaviorEffects());
    }

    public static void clearPlayer(UUID playerId) {
        BEHAVIOR_RUNTIME.clearPlayer(Objects.requireNonNull(playerId, "playerId"));
    }

    public static void clearAll() {
        BEHAVIOR_RUNTIME.clearAll();
    }
}
