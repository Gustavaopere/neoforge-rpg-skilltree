package dev.gustavopere.rpgskilltree.runtime.effects;

import dev.gustavopere.rpgskilltree.core.NodeBehaviorEffect;
import dev.gustavopere.rpgskilltree.core.NodeBehaviorEffectReconciler;
import dev.gustavopere.rpgskilltree.core.NodeEffectResolver;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.core.ResolvedNodeBehaviorEffect;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

/**
 * Server-side executor for behavioral node effects.
 *
 * <p>The runtime remembers the effects successfully reconciled for the current ServerPlayer
 * instance. A replacement player object with the same UUID is treated as a fresh session so
 * transient provider state is applied to the new entity exactly once.</p>
 */
public final class BehaviorNodeEffectRuntime {
    private final NodeBehaviorHandlerRegistry registry;
    private final Map<UUID, AppliedSession> appliedByPlayer = new LinkedHashMap<>();

    public BehaviorNodeEffectRuntime(NodeBehaviorHandlerRegistry registry) {
        this.registry = Objects.requireNonNull(registry, "registry");
    }

    public synchronized void refresh(
        ServerPlayer player,
        ProgressionState state,
        Collection<NodeBehaviorEffect> effects
    ) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(effects, "effects");

        UUID playerId = player.getUUID();
        AppliedSession session = appliedByPlayer.get(playerId);
        List<ResolvedNodeBehaviorEffect> applied = session != null && session.player() == player
            ? session.effects()
            : List.of();
        List<ResolvedNodeBehaviorEffect> desired = NodeEffectResolver.resolveBehaviors(state.passiveNodes(), effects);

        var reconciliation = NodeBehaviorEffectReconciler.reconcile(
            applied,
            desired,
            this::handlerAvailable
        );

        for (ResolvedNodeBehaviorEffect effect : reconciliation.removals()) {
            registry.resolveAvailable(ResourceLocation.parse(effect.handlerId()))
                .ifPresent(handler -> handler.remove(player, effect));
        }
        for (ResolvedNodeBehaviorEffect effect : reconciliation.applications()) {
            registry.resolveAvailable(ResourceLocation.parse(effect.handlerId()))
                .ifPresent(handler -> handler.apply(player, effect));
        }

        List<ResolvedNodeBehaviorEffect> availableDesired = new ArrayList<>();
        for (ResolvedNodeBehaviorEffect effect : desired) {
            if (handlerAvailable(effect.handlerId())) availableDesired.add(effect);
        }
        if (availableDesired.isEmpty()) {
            appliedByPlayer.remove(playerId);
        } else {
            appliedByPlayer.put(playerId, new AppliedSession(player, List.copyOf(availableDesired)));
        }
    }

    private boolean handlerAvailable(String handlerId) {
        return registry.resolveAvailable(ResourceLocation.parse(handlerId)).isPresent();
    }

    private record AppliedSession(ServerPlayer player, List<ResolvedNodeBehaviorEffect> effects) {
        private AppliedSession {
            Objects.requireNonNull(player, "player");
            effects = List.copyOf(Objects.requireNonNull(effects, "effects"));
        }
    }
}
