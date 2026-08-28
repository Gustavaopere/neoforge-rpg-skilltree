package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.EntityScalingDecisionRequest;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

/**
 * Builds the complete authoritative input for one entity's initial scaling decision.
 *
 * <p>Implementations own world/provider lookup policy and must respect the join-boundary
 * constraint: do not force chunk loading from {@code EntityJoinLevelEvent}.</p>
 */
@FunctionalInterface
public interface EntityScalingDecisionRequestFactory {
    EntityScalingDecisionRequest create(ServerLevel level, LivingEntity entity);
}
