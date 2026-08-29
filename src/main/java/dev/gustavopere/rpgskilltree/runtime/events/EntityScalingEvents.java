package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.EntityScalingState;
import dev.gustavopere.rpgskilltree.runtime.EntityScalingInitializer;
import dev.gustavopere.rpgskilltree.runtime.EntityScalingInitializerCatalog;
import dev.gustavopere.rpgskilltree.runtime.EntityScalingRuntime;
import dev.gustavopere.rpgskilltree.runtime.EntityScalingStateApplierCatalog;
import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

/** Canonical server-side spawn/load entry point for persistent entity scaling. */
public final class EntityScalingEvents {
    private EntityScalingEvents() {}

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        if (!(event.getEntity() instanceof LivingEntity entity)) return;
        if (entity instanceof Player) return;

        Optional<EntityScalingState> persisted = EntityScalingRuntime.current(entity);
        EntityScalingState state;
        if (persisted.isPresent()) {
            // Persisted state always wins, including entities reloaded from disk or re-added to a level.
            state = persisted.orElseThrow();
        } else {
            // No balance/world-threat policy is implied by this adapter. New entities remain unscaled
            // until the server has explicitly installed an authoritative initializer.
            Optional<EntityScalingInitializer> initializer = EntityScalingInitializerCatalog.current();
            if (initializer.isEmpty()) return;

            state = EntityScalingRuntime.getOrInitialize(
                entity,
                () -> initializer.orElseThrow().initialize(serverLevel, entity)
            );
        }

        // Application is intentionally separate from initialization: persisted decisions are replayed
        // without rerolling, while idempotent appliers can reconstruct transient runtime effects.
        EntityScalingState resolvedState = state;
        EntityScalingStateApplierCatalog.current().ifPresent(
            applier -> applier.apply(serverLevel, entity, resolvedState)
        );
    }
}
