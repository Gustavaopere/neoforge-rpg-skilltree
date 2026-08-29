package dev.gustavopere.rpgskilltree.gametest;

import dev.gustavopere.rpgskilltree.core.CappedEntityRewardScalingPolicy;
import dev.gustavopere.rpgskilltree.core.CappedLinearScalingCurve;
import dev.gustavopere.rpgskilltree.core.EntityArchetype;
import dev.gustavopere.rpgskilltree.core.EntityLevelResolution;
import dev.gustavopere.rpgskilltree.core.EntityScalingState;
import dev.gustavopere.rpgskilltree.core.MobRarityKey;
import dev.gustavopere.rpgskilltree.core.MobRaritySelection;
import dev.gustavopere.rpgskilltree.core.ScalingCurveFamily;
import dev.gustavopere.rpgskilltree.core.ScalingCurveSet;
import dev.gustavopere.rpgskilltree.core.TerritoryKey;
import dev.gustavopere.rpgskilltree.runtime.EntityRewardScalingPolicyCatalog;
import dev.gustavopere.rpgskilltree.runtime.EntityScalingRuntime;
import dev.gustavopere.rpgskilltree.runtime.events.EntityRewardScalingEvents;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class EntityRewardScalingGameTests {
    private EntityRewardScalingGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void persistedScalingStateDrivesBoundedXpReward(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Zombie scaled = EntityType.ZOMBIE.create(level);
        Zombie unscaled = EntityType.ZOMBIE.create(level);
        if (scaled == null || unscaled == null) throw new AssertionError("failed to create zombies");

        MobRarityKey elite = MobRarityKey.of("rpgskilltree:elite");
        EntityScalingRuntime.getOrInitialize(scaled, () -> stateAtLevel(10L, elite));
        EntityRewardScalingPolicyCatalog.install(policy(elite));

        try {
            LivingExperienceDropEvent scaledEvent = new LivingExperienceDropEvent(scaled, null, 11);
            EntityRewardScalingEvents.onExperienceDrop(scaledEvent);
            eq(19, scaledEvent.getDroppedExperience());

            LivingExperienceDropEvent unscaledEvent = new LivingExperienceDropEvent(unscaled, null, 11);
            EntityRewardScalingEvents.onExperienceDrop(unscaledEvent);
            eq(11, unscaledEvent.getDroppedExperience());

            EntityRewardScalingPolicyCatalog.clear();
            LivingExperienceDropEvent noPolicyEvent = new LivingExperienceDropEvent(scaled, null, 11);
            EntityRewardScalingEvents.onExperienceDrop(noPolicyEvent);
            eq(11, noPolicyEvent.getDroppedExperience());
            helper.succeed();
        } finally {
            EntityRewardScalingPolicyCatalog.clear();
        }
    }

    private static EntityScalingState stateAtLevel(long level, MobRarityKey rarity) {
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
            Optional.of(new MobRaritySelection(rarity, 0L)),
            123456789L
        );
    }

    private static CappedEntityRewardScalingPolicy policy(MobRarityKey elite) {
        EnumMap<EntityArchetype, BigDecimal> archetypes = new EnumMap<>(EntityArchetype.class);
        for (EntityArchetype archetype : EntityArchetype.values()) {
            archetypes.put(archetype, BigDecimal.ONE);
        }
        return CappedEntityRewardScalingPolicy.of(
            ScalingCurveSet.of(Map.of(
                ScalingCurveFamily.HEALTH, curve("1", "0", "1", "1"),
                ScalingCurveFamily.DAMAGE, curve("1", "0", "1", "1"),
                ScalingCurveFamily.DEFENSE, curve("1", "0", "1", "1"),
                ScalingCurveFamily.UTILITY, curve("1", "0", "1", "1"),
                ScalingCurveFamily.REWARD, curve("1", "0.05", "1", "2")
            )),
            archetypes,
            Map.of(elite, new BigDecimal("1.2")),
            BigDecimal.ONE,
            new BigDecimal("3")
        );
    }

    private static CappedLinearScalingCurve curve(String base, String perLevel, String minimum, String maximum) {
        return CappedLinearScalingCurve.of(
            new BigDecimal(base),
            new BigDecimal(perLevel),
            new BigDecimal(minimum),
            new BigDecimal(maximum)
        );
    }

    private static void eq(int expected, int actual) {
        if (expected != actual) throw new AssertionError(expected + " != " + actual);
    }
}
