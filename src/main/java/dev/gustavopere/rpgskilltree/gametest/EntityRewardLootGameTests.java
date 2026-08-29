package dev.gustavopere.rpgskilltree.gametest;

import dev.gustavopere.rpgskilltree.core.CappedEntityLootQuantityPolicy;
import dev.gustavopere.rpgskilltree.core.CappedEntityRewardScalingPolicy;
import dev.gustavopere.rpgskilltree.core.CappedLinearScalingCurve;
import dev.gustavopere.rpgskilltree.core.EntityArchetype;
import dev.gustavopere.rpgskilltree.core.EntityLevelResolution;
import dev.gustavopere.rpgskilltree.core.EntityScalingState;
import dev.gustavopere.rpgskilltree.core.ScalingCurveFamily;
import dev.gustavopere.rpgskilltree.core.ScalingCurveSet;
import dev.gustavopere.rpgskilltree.core.TerritoryKey;
import dev.gustavopere.rpgskilltree.runtime.EntityLootQuantityPolicyCatalog;
import dev.gustavopere.rpgskilltree.runtime.EntityRewardScalingPolicyCatalog;
import dev.gustavopere.rpgskilltree.runtime.EntityScalingRuntime;
import dev.gustavopere.rpgskilltree.runtime.events.EntityRewardEvents;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class EntityRewardLootGameTests {
    private EntityRewardLootGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void persistedRiskScalesOnlyExistingStackableDropsWithinKillBudget(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Zombie zombie = EntityType.ZOMBIE.create(level);
        if (zombie == null) throw new AssertionError("failed to create zombie");
        EntityScalingRuntime.getOrInitialize(zombie, () -> stateAtLevel(10L));

        EntityRewardScalingPolicyCatalog.install(constantRewardPolicy("2"));
        EntityLootQuantityPolicyCatalog.install(CappedEntityLootQuantityPolicy.of(
            new BigDecimal("1.5"),
            2,
            3
        ));

        List<ItemEntity> drops = new ArrayList<>();
        ItemEntity flesh = drop(level, zombie, new ItemStack(Items.ROTTEN_FLESH, 4));
        ItemEntity ingots = drop(level, zombie, new ItemStack(Items.IRON_INGOT, 4));
        ItemEntity sword = drop(level, zombie, new ItemStack(Items.IRON_SWORD, 1));
        drops.add(flesh);
        drops.add(ingots);
        drops.add(sword);

        try {
            LivingDropsEvent event = new LivingDropsEvent(
                zombie,
                level.damageSources().generic(),
                drops,
                true
            );
            EntityRewardEvents.onDrops(event);

            eq(3, event.getDrops().size());
            eq(6, flesh.getItem().getCount());
            eq(5, ingots.getItem().getCount());
            eq(1, sword.getItem().getCount());
            helper.succeed();
        } finally {
            EntityLootQuantityPolicyCatalog.clear();
            EntityRewardScalingPolicyCatalog.clear();
        }
    }

    @GameTest(template = "foundation_empty")
    public static void absentStateOrLootPolicyLeavesDropsUntouched(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Zombie unscaled = EntityType.ZOMBIE.create(level);
        Zombie scaled = EntityType.ZOMBIE.create(level);
        if (unscaled == null || scaled == null) throw new AssertionError("failed to create zombies");
        EntityScalingRuntime.getOrInitialize(scaled, () -> stateAtLevel(10L));
        EntityRewardScalingPolicyCatalog.install(constantRewardPolicy("2"));
        EntityLootQuantityPolicyCatalog.install(CappedEntityLootQuantityPolicy.of(new BigDecimal("2"), 8, 16));

        try {
            ItemEntity unscaledDrop = drop(level, unscaled, new ItemStack(Items.ROTTEN_FLESH, 4));
            LivingDropsEvent noState = new LivingDropsEvent(
                unscaled,
                level.damageSources().generic(),
                new ArrayList<>(List.of(unscaledDrop)),
                true
            );
            EntityRewardEvents.onDrops(noState);
            eq(4, unscaledDrop.getItem().getCount());

            EntityLootQuantityPolicyCatalog.clear();
            ItemEntity scaledDrop = drop(level, scaled, new ItemStack(Items.ROTTEN_FLESH, 4));
            LivingDropsEvent noLootPolicy = new LivingDropsEvent(
                scaled,
                level.damageSources().generic(),
                new ArrayList<>(List.of(scaledDrop)),
                true
            );
            EntityRewardEvents.onDrops(noLootPolicy);
            eq(4, scaledDrop.getItem().getCount());
            helper.succeed();
        } finally {
            EntityLootQuantityPolicyCatalog.clear();
            EntityRewardScalingPolicyCatalog.clear();
        }
    }

    private static ItemEntity drop(ServerLevel level, Zombie owner, ItemStack stack) {
        return new ItemEntity(level, owner.getX(), owner.getY(), owner.getZ(), stack);
    }

    private static EntityScalingState stateAtLevel(long level) {
        return new EntityScalingState(
            TerritoryKey.of("minecraft:overworld", 0L, 0L),
            new EntityLevelResolution(
                EntityArchetype.HOSTILE,
                level,
                OptionalLong.empty(),
                level,
                level,
                level
            ),
            0L,
            Optional.empty(),
            123456789L
        );
    }

    private static CappedEntityRewardScalingPolicy constantRewardPolicy(String multiplier) {
        BigDecimal value = new BigDecimal(multiplier);
        EnumMap<EntityArchetype, BigDecimal> archetypes = new EnumMap<>(EntityArchetype.class);
        for (EntityArchetype archetype : EntityArchetype.values()) {
            archetypes.put(archetype, BigDecimal.ONE);
        }
        return CappedEntityRewardScalingPolicy.of(
            ScalingCurveSet.of(Map.of(
                ScalingCurveFamily.HEALTH, constantCurve(BigDecimal.ONE),
                ScalingCurveFamily.DAMAGE, constantCurve(BigDecimal.ONE),
                ScalingCurveFamily.DEFENSE, constantCurve(BigDecimal.ONE),
                ScalingCurveFamily.UTILITY, constantCurve(BigDecimal.ONE),
                ScalingCurveFamily.REWARD, constantCurve(value)
            )),
            archetypes,
            Map.of(),
            BigDecimal.ONE,
            new BigDecimal("10")
        );
    }

    private static CappedLinearScalingCurve constantCurve(BigDecimal value) {
        return CappedLinearScalingCurve.of(value, BigDecimal.ZERO, value, value);
    }

    private static void eq(int expected, int actual) {
        if (expected != actual) throw new AssertionError(expected + " != " + actual);
    }
}
