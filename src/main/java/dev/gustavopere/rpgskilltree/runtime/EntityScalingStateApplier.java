package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.EntityScalingState;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

/**
 * Server-authoritative application boundary for an already-resolved entity scaling state.
 *
 * <p>The state may have been initialized during this join or loaded from the entity's persisted
 * attachment. Implementations must treat the supplied selection as authoritative: they must not
 * reroll level, rarity, affixes or behaviors. Repeated application of the same state is expected
 * to be idempotent so reload/re-add cannot accumulate effects.</p>
 */
@FunctionalInterface
public interface EntityScalingStateApplier {
    void apply(ServerLevel level, LivingEntity entity, EntityScalingState state);
}
