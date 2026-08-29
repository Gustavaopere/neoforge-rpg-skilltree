package dev.gustavopere.rpgskilltree.gametest;

import dev.gustavopere.rpgskilltree.core.CanonicalStatKey;
import dev.gustavopere.rpgskilltree.core.EntityArchetype;
import dev.gustavopere.rpgskilltree.core.EntityBehaviorSelection;
import dev.gustavopere.rpgskilltree.core.EntityEffectiveStatsSnapshot;
import dev.gustavopere.rpgskilltree.core.EntityLevelResolution;
import dev.gustavopere.rpgskilltree.core.EntityScalingState;
import dev.gustavopere.rpgskilltree.core.EntityScalingStateCodec;
import dev.gustavopere.rpgskilltree.core.MobAffixSelection;
import dev.gustavopere.rpgskilltree.core.TerritoryKey;
import dev.gustavopere.rpgskilltree.runtime.EntityEffectiveStatsRuntime;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class EntityEffectiveStatsGameTests {
    private static final double EPSILON = 1.0e-9;
    private static final CanonicalStatKey MAX_HEALTH = CanonicalStatKey.of("minecraft:max_health");
    private static final CanonicalStatKey ATTACK_DAMAGE = CanonicalStatKey.of("minecraft:attack_damage");
    private static final ResourceLocation MAX_HEALTH_MODIFIER = ResourceLocation.parse(
        "rpgskilltree:entity_scaling/max_health"
    );
    private static final ResourceLocation ATTACK_DAMAGE_MODIFIER = ResourceLocation.parse(
        "rpgskilltree:entity_scaling/attack_damage"
    );

    private EntityEffectiveStatsGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void persistedEffectiveStatsReapplyWithoutStacking(GameTestHelper helper) {
        LivingEntity original = create(EntityType.ZOMBIE, helper);
        var originalHealth = Objects.requireNonNull(original.getAttribute(Attributes.MAX_HEALTH), "zombie max health");
        var originalDamage = Objects.requireNonNull(original.getAttribute(Attributes.ATTACK_DAMAGE), "zombie attack damage");
        double targetHealth = originalHealth.getBaseValue() + 15.0;
        double targetDamage = originalDamage.getBaseValue() + 4.0;

        EntityScalingState state = stateWith(EntityEffectiveStatsSnapshot.of(Map.of(
            MAX_HEALTH, BigDecimal.valueOf(targetHealth),
            ATTACK_DAMAGE, BigDecimal.valueOf(targetDamage)
        )));

        EntityEffectiveStatsRuntime.refresh(original, state);
        assertApplied(helper, original, targetHealth, targetDamage, "first apply");
        EntityEffectiveStatsRuntime.refresh(original, state);
        assertApplied(helper, original, targetHealth, targetDamage, "repeated apply");

        EntityScalingState reloadedState = EntityScalingStateCodec.decodeState(EntityScalingStateCodec.encode(state));
        LivingEntity reloaded = create(EntityType.ZOMBIE, helper);
        EntityEffectiveStatsRuntime.refresh(reloaded, reloadedState);
        assertApplied(helper, reloaded, targetHealth, targetDamage, "save/load reapply");
        EntityEffectiveStatsRuntime.refresh(reloaded, reloadedState);
        assertApplied(helper, reloaded, targetHealth, targetDamage, "save/load repeated apply");

        helper.succeed();
    }

    private static EntityScalingState stateWith(EntityEffectiveStatsSnapshot snapshot) {
        return new EntityScalingState(
            TerritoryKey.of("minecraft:overworld", 0L, 0L),
            new EntityLevelResolution(
                EntityArchetype.HOSTILE,
                10L,
                OptionalLong.empty(),
                10L,
                10L,
                10L
            ),
            0L,
            Optional.empty(),
            1234L,
            Optional.of(snapshot),
            MobAffixSelection.empty(),
            EntityBehaviorSelection.empty()
        );
    }

    private static <T extends LivingEntity> T create(EntityType<T> type, GameTestHelper helper) {
        return Objects.requireNonNull(type.create(helper.getLevel()), () -> "failed to create " + type);
    }

    private static void assertApplied(
        GameTestHelper helper,
        LivingEntity entity,
        double targetHealth,
        double targetDamage,
        String stage
    ) {
        var health = Objects.requireNonNull(entity.getAttribute(Attributes.MAX_HEALTH), "zombie max health");
        var damage = Objects.requireNonNull(entity.getAttribute(Attributes.ATTACK_DAMAGE), "zombie attack damage");
        helper.assertTrue(health.hasModifier(MAX_HEALTH_MODIFIER), stage + ": max health modifier missing");
        helper.assertTrue(damage.hasModifier(ATTACK_DAMAGE_MODIFIER), stage + ": attack damage modifier missing");
        assertClose(helper, targetHealth, health.getValue(), stage + " max health");
        assertClose(helper, targetDamage, damage.getValue(), stage + " attack damage");
    }

    private static void assertClose(GameTestHelper helper, double expected, double actual, String stage) {
        helper.assertTrue(
            Math.abs(expected - actual) <= EPSILON,
            stage + ": expected=" + expected + ", actual=" + actual
        );
    }
}
