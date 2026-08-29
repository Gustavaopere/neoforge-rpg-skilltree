package dev.gustavopere.rpgskilltree.runtime.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.gustavopere.rpgskilltree.core.EntityRewardScalingContext;
import dev.gustavopere.rpgskilltree.core.EntityRewardScalingResult;
import dev.gustavopere.rpgskilltree.core.EntityScalingState;
import dev.gustavopere.rpgskilltree.runtime.EntityRewardScalingPolicyCatalog;
import dev.gustavopere.rpgskilltree.runtime.EntityScalingRuntime;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Optional;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

/** Global loot modifier that applies the canonical persisted reward-risk multiplier to entity loot. */
public final class RewardRiskLootModifier extends LootModifier {
    public static final MapCodec<RewardRiskLootModifier> CODEC = RecordCodecBuilder.mapCodec(instance ->
        LootModifier.codecStart(instance)
            .and(Codec.intRange(0, 64)
                .fieldOf("max_extra_stacks_per_input")
                .forGetter(RewardRiskLootModifier::maxExtraStacksPerInput))
            .apply(instance, RewardRiskLootModifier::new)
    );

    private final int maxExtraStacksPerInput;

    public RewardRiskLootModifier(LootItemCondition[] conditions, int maxExtraStacksPerInput) {
        super(conditions);
        if (maxExtraStacksPerInput < 0) {
            throw new IllegalArgumentException("maxExtraStacksPerInput must be non-negative");
        }
        this.maxExtraStacksPerInput = maxExtraStacksPerInput;
    }

    public int maxExtraStacksPerInput() {
        return maxExtraStacksPerInput;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        Entity source = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        if (!(source instanceof LivingEntity livingEntity)) return generatedLoot;

        Optional<EntityScalingState> persisted = EntityScalingRuntime.current(livingEntity);
        if (persisted.isEmpty()) return generatedLoot;

        var policy = EntityRewardScalingPolicyCatalog.current();
        if (policy.isEmpty()) return generatedLoot;

        EntityScalingState state = persisted.orElseThrow();
        EntityRewardScalingResult scaling = policy.orElseThrow().resolve(
            new EntityRewardScalingContext(state.levelResolution(), state.rarity())
        );
        return EntityRewardLootRuntime.scaleGeneratedLoot(
            generatedLoot,
            scaling,
            state.deterministicSeed(),
            maxExtraStacksPerInput
        );
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
