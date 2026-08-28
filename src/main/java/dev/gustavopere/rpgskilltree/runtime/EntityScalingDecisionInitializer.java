package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.EntityScalingDecisionRequest;
import dev.gustavopere.rpgskilltree.core.EntityScalingDecisionService;
import dev.gustavopere.rpgskilltree.core.EntityScalingState;
import java.util.Objects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

/** Entity-scaling initializer that delegates the complete decision to the canonical Core service. */
public final class EntityScalingDecisionInitializer implements EntityScalingInitializer {
    private final EntityScalingDecisionRequestFactory requestFactory;

    public EntityScalingDecisionInitializer(EntityScalingDecisionRequestFactory requestFactory) {
        this.requestFactory = Objects.requireNonNull(requestFactory, "requestFactory");
    }

    @Override
    public EntityScalingState initialize(ServerLevel level, LivingEntity entity) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(entity, "entity");
        EntityScalingDecisionRequest request = Objects.requireNonNull(
            requestFactory.create(level, entity),
            "requestFactory returned null"
        );
        return EntityScalingDecisionService.resolve(request).state();
    }
}
