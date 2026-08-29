package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.EntityBehaviorKey;
import dev.gustavopere.rpgskilltree.core.EntityScalingState;
import java.util.ArrayList;
import java.util.Objects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

/** Replays the persisted canonical behavior selection through explicitly installed reconcilers. */
public final class EntityBehaviorRuntime {
    private EntityBehaviorRuntime() {}

    public static EntityBehaviorRuntimeResult reconcile(
        ServerLevel level,
        LivingEntity entity,
        EntityScalingState state
    ) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(entity, "entity");
        Objects.requireNonNull(state, "state");

        ArrayList<EntityBehaviorKey> applied = new ArrayList<>();
        ArrayList<EntityBehaviorKey> missing = new ArrayList<>();
        for (EntityBehaviorKey behavior : state.behaviors().behaviors()) {
            var reconciler = EntityBehaviorRuntimeCatalog.current(behavior);
            if (reconciler.isEmpty()) {
                missing.add(behavior);
                continue;
            }
            reconciler.orElseThrow().reconcile(level, entity, state);
            applied.add(behavior);
        }
        return new EntityBehaviorRuntimeResult(applied, missing);
    }
}
