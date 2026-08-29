package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.CappedEntityRewardScalingPolicy;
import dev.gustavopere.rpgskilltree.core.EntityRewardScalingContext;
import dev.gustavopere.rpgskilltree.core.EntityRewardScalingResult;
import dev.gustavopere.rpgskilltree.core.EntityScalingState;
import dev.gustavopere.rpgskilltree.runtime.EntityRewardExperienceRuntime;
import dev.gustavopere.rpgskilltree.runtime.EntityRewardScalingPolicyCatalog;
import dev.gustavopere.rpgskilltree.runtime.EntityScalingRuntime;
import java.util.Optional;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

/** Applies configured risk-based reward scaling to XP dropped by already-scaled living entities. */
public final class EntityRewardEvents {
    private EntityRewardEvents() {}

    @SubscribeEvent
    public static void onExperienceDrop(LivingExperienceDropEvent event) {
        if (event.getEntity().level().isClientSide()) return;

        Optional<CappedEntityRewardScalingPolicy> policy = EntityRewardScalingPolicyCatalog.current();
        if (policy.isEmpty()) return;

        Optional<EntityScalingState> state = EntityScalingRuntime.current(event.getEntity());
        if (state.isEmpty()) return;

        EntityScalingState persisted = state.orElseThrow();
        EntityRewardScalingResult scaling = policy.orElseThrow().resolve(new EntityRewardScalingContext(
            persisted.levelResolution(),
            persisted.rarity()
        ));
        event.setDroppedExperience(EntityRewardExperienceRuntime.scaleExperience(
            event.getDroppedExperience(),
            scaling
        ));
    }
}
