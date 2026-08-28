package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.EntityScalingState;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

/**
 * Server-authoritative initializer for one entity's first persistent scaling decision.
 *
 * <p>This contract is invoked from {@code EntityJoinLevelEvent}. Implementations must use
 * already-available/cached inputs and must not force chunk loading from the join callback.</p>
 */
@FunctionalInterface
public interface EntityScalingInitializer {
    EntityScalingState initialize(ServerLevel level, LivingEntity entity);
}
