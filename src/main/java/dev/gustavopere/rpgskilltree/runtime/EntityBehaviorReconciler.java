package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.EntityScalingState;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

/**
 * Reconciles one already-selected behavior package onto a live entity.
 *
 * <p>The supplied scaling state is authoritative and must never be rerolled. Implementations are
 * expected to be idempotent: repeated calls for the same entity/state must converge on the same
 * runtime behavior instead of stacking duplicate goals, modifiers or listeners.</p>
 */
@FunctionalInterface
public interface EntityBehaviorReconciler {
    void reconcile(ServerLevel level, LivingEntity entity, EntityScalingState state);
}
