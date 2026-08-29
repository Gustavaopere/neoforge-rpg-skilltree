package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.CappedEntityLootQuantityPolicy;
import dev.gustavopere.rpgskilltree.core.CappedEntityRewardScalingPolicy;
import dev.gustavopere.rpgskilltree.core.EntityRewardScalingContext;
import dev.gustavopere.rpgskilltree.core.EntityRewardScalingResult;
import dev.gustavopere.rpgskilltree.core.EntityScalingState;
import dev.gustavopere.rpgskilltree.runtime.EntityLootQuantityPolicyCatalog;
import dev.gustavopere.rpgskilltree.runtime.EntityRewardExperienceRuntime;
import dev.gustavopere.rpgskilltree.runtime.EntityRewardScalingPolicyCatalog;
import dev.gustavopere.rpgskilltree.runtime.EntityScalingRuntime;
import java.util.Optional;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

/** Applies configured risk-based reward scaling to existing rewards of already-scaled living entities. */
public final class EntityRewardEvents {
    private EntityRewardEvents() {}

    @SubscribeEvent
    public static void onExperienceDrop(LivingExperienceDropEvent event) {
        if (event.getEntity().level().isClientSide()) return;

        Optional<CappedEntityRewardScalingPolicy> policy = EntityRewardScalingPolicyCatalog.current();
        if (policy.isEmpty()) return;

        Optional<EntityScalingState> state = EntityScalingRuntime.current(event.getEntity());
        if (state.isEmpty()) return;

        EntityRewardScalingResult scaling = resolveScaling(policy.orElseThrow(), state.orElseThrow());
        event.setDroppedExperience(EntityRewardExperienceRuntime.scaleExperience(
            event.getDroppedExperience(),
            scaling
        ));
    }

    @SubscribeEvent
    public static void onDrops(LivingDropsEvent event) {
        if (event.getEntity().level().isClientSide()) return;

        Optional<CappedEntityRewardScalingPolicy> rewardPolicy = EntityRewardScalingPolicyCatalog.current();
        if (rewardPolicy.isEmpty()) return;

        Optional<CappedEntityLootQuantityPolicy> lootPolicy = EntityLootQuantityPolicyCatalog.current();
        if (lootPolicy.isEmpty()) return;

        Optional<EntityScalingState> state = EntityScalingRuntime.current(event.getEntity());
        if (state.isEmpty()) return;

        EntityRewardScalingResult scaling = resolveScaling(rewardPolicy.orElseThrow(), state.orElseThrow());
        CappedEntityLootQuantityPolicy quantityPolicy = lootPolicy.orElseThrow();
        int extraGrantedThisKill = 0;
        for (ItemEntity drop : event.getDrops()) {
            ItemStack stack = drop.getItem();
            int currentCount = stack.getCount();
            int scaledCount = quantityPolicy.scaleCount(
                currentCount,
                stack.getMaxStackSize(),
                extraGrantedThisKill,
                scaling
            );
            if (scaledCount > currentCount) {
                stack.setCount(scaledCount);
                extraGrantedThisKill += scaledCount - currentCount;
            }
            if (extraGrantedThisKill >= quantityPolicy.maxExtraPerKill()) break;
        }
    }

    private static EntityRewardScalingResult resolveScaling(
        CappedEntityRewardScalingPolicy policy,
        EntityScalingState persisted
    ) {
        return policy.resolve(new EntityRewardScalingContext(
            persisted.levelResolution(),
            persisted.rarity()
        ));
    }
}
