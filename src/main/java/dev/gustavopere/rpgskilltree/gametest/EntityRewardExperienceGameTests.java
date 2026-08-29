package dev.gustavopere.rpgskilltree.gametest;

import dev.gustavopere.rpgskilltree.core.CappedEntityRewardScalingPolicy;
import dev.gustavopere.rpgskilltree.core.CappedLinearScalingCurve;
import dev.gustavopere.rpgskilltree.core.EntityArchetype;
import dev.gustavopere.rpgskilltree.core.EntityBehaviorSelection;
import dev.gustavopere.rpgskilltree.core.EntityLevelResolution;
import dev.gustavopere.rpgskilltree.core.EntityRewardScalingResult;
import dev.gustavopere.rpgskilltree.core.EntityScalingState;
import dev.gustavopere.rpgskilltree.core.MobAffixSelection;
import dev.gustavopere.rpgskilltree.core.ScalingCurveFamily;
import dev.gustavopere.rpgskilltree.core.ScalingCurveSet;
import dev.gustavopere.rpgskilltree.core.TerritoryKey;
import dev.gustavopere.rpgskilltree.runtime.EntityRewardExperienceRuntime;
import dev.gustavopere.rpgskilltree.runtime.EntityRewardScalingPolicyCatalog;
import dev.gustavopere.rpgskilltree.runtime.EntityScalingRuntime;
import dev.gustavopere.rpgskilltree.runtime.events.EntityRewardEvents;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class EntityRewardExperienceGameTests {
    private EntityRewardExperienceGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void persistedRiskScalesCurrentExperienceWithoutReroll(GameTestHelper helper) {
        LivingEntity zombie = create(EntityType.ZOMBIE, helper);
        EntityScalingState state = state(EntityArchetype.HOSTILE, 10L);
        EntityScalingRuntime.getOrInitialize(zombie, () -> state);
        EntityRewardScalingPolicyCatalog.install(constantPolicy("1.5"));

        try {
            LivingExperienceDropEvent event = new LivingExperienceDropEvent(zombie, null, 10);
            // Respect upstream handlers: the RPG adapter must scale the value currently on the event,
            // not recompute from originalExperience.
            event.setDroppedExperience(12);
            EntityRewardEvents.onExperienceDrop(event);

            helper.assertTrue(event.getDroppedExperience() == 18, "12 XP at x1.5 must become 18");
            helper.assertTrue(
                EntityScalingRuntime.current(zombie).orElseThrow() == state,
                "reward handling must consume the persisted state without rerolling/replacing it"
            );
            helper.succeed();
        } finally {
            EntityRewardScalingPolicyCatalog.clear();
        }
    }

    @GameTest(template = "foundation_empty")
    public static void absentStateOrPolicyLeavesExperienceUntouched(GameTestHelper helper) {
        LivingEntity unscaled = create(EntityType.ZOMBIE, helper);
        EntityRewardScalingPolicyCatalog.install(constantPolicy("2"));
        try {
            LivingExperienceDropEvent noState = new LivingExperienceDropEvent(unscaled, null, 10);
            noState.setDroppedExperience(13);
            EntityRewardEvents.onExperienceDrop(noState);
            helper.assertTrue(noState.getDroppedExperience() == 13, "unscaled entities must keep current XP");
        } finally {
            EntityRewardScalingPolicyCatalog.clear();
        }

        LivingEntity scaled = create(EntityType.ZOMBIE, helper);
        EntityScalingRuntime.getOrInitialize(scaled, () -> state(EntityArchetype.HOSTILE, 10L));
        LivingExperienceDropEvent noPolicy = new LivingExperienceDropEvent(scaled, null, 10);
        noPolicy.setDroppedExperience(13);
        EntityRewardEvents.onExperienceDrop(noPolicy);
        helper.assertTrue(noPolicy.getDroppedExperience() == 13, "no installed policy must keep current XP");
        helper.succeed();
    }

    @GameTest(template = "foundation_empty")
    public static void integerExperienceScalingFloorsFractionsAndClampsOverflow(GameTestHelper helper) {
        EntityRewardScalingResult onePointFive = result("1.5");
        EntityRewardScalingResult three = result("3");

        helper.assertTrue(
            EntityRewardExperienceRuntime.scaleExperience(5, onePointFive) == 7,
            "fractional XP must use deterministic downward rounding"
        );
        helper.assertTrue(
            EntityRewardExperienceRuntime.scaleExperience(Integer.MAX_VALUE, three) == Integer.MAX_VALUE,
            "scaled XP must clamp to Minecraft's int boundary"
        );
        helper.succeed();
    }

    private static CappedEntityRewardScalingPolicy constantPolicy(String multiplier) {
        BigDecimal factor = new BigDecimal(multiplier);
        ScalingCurveSet curves = ScalingCurveSet.of(Map.of(
            ScalingCurveFamily.HEALTH, constantCurve(BigDecimal.ONE),
            ScalingCurveFamily.DAMAGE, constantCurve(BigDecimal.ONE),
            ScalingCurveFamily.DEFENSE, constantCurve(BigDecimal.ONE),
            ScalingCurveFamily.UTILITY, constantCurve(BigDecimal.ONE),
            ScalingCurveFamily.REWARD, constantCurve(factor)
        ));
        EnumMap<EntityArchetype, BigDecimal> archetypes = new EnumMap<>(EntityArchetype.class);
        for (EntityArchetype archetype : EntityArchetype.values()) {
            archetypes.put(archetype, BigDecimal.ONE);
        }
        return CappedEntityRewardScalingPolicy.of(
            curves,
            archetypes,
            Map.of(),
            BigDecimal.ONE,
            new BigDecimal("10")
        );
    }

    private static CappedLinearScalingCurve constantCurve(BigDecimal value) {
        return CappedLinearScalingCurve.of(value, BigDecimal.ZERO, value, value);
    }

    private static EntityRewardScalingResult result(String multiplier) {
        BigDecimal value = new BigDecimal(multiplier);
        return new EntityRewardScalingResult(value, BigDecimal.ONE, BigDecimal.ONE, value, value);
    }

    private static EntityScalingState state(EntityArchetype archetype, long level) {
        return new EntityScalingState(
            TerritoryKey.of("minecraft:overworld", 0L, 0L),
            new EntityLevelResolution(
                archetype,
                level,
                OptionalLong.empty(),
                level,
                level,
                level
            ),
            0L,
            Optional.empty(),
            1234L,
            Optional.empty(),
            MobAffixSelection.empty(),
            EntityBehaviorSelection.empty()
        );
    }

    private static <T extends LivingEntity> T create(EntityType<T> type, GameTestHelper helper) {
        return Objects.requireNonNull(type.create(helper.getLevel()), () -> "failed to create " + type);
    }
}
