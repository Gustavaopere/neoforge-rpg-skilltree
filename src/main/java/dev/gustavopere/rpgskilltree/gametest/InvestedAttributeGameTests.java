package dev.gustavopere.rpgskilltree.gametest;

import com.mojang.authlib.GameProfile;
import dev.gustavopere.rpgskilltree.core.AttributeId;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses;
import dev.gustavopere.rpgskilltree.core.AttributeRanks;
import dev.gustavopere.rpgskilltree.runtime.effects.InvestedAttributeEffectsRuntime;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class InvestedAttributeGameTests {
    private InvestedAttributeGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void cappedVanillaInvestmentIsIdempotentAndRemovable(GameTestHelper helper) {
        // Same real NeoForge ServerPlayer fixture used by AttributeModifierGameTests.
        ServerPlayer player = new ServerPlayer(helper.getLevel().getServer(), helper.getLevel(),
            new GameProfile(UUID.randomUUID(), "invested-attribute-test"), ClientInformation.createDefault());
        var healthHolder = BuiltInRegistries.ATTRIBUTE.getHolder(
            ResourceLocation.parse("minecraft:generic.max_health")).orElseThrow();
        var speedHolder = BuiltInRegistries.ATTRIBUTE.getHolder(
            ResourceLocation.parse("minecraft:generic.movement_speed")).orElseThrow();
        var knockbackHolder = BuiltInRegistries.ATTRIBUTE.getHolder(
            ResourceLocation.parse("minecraft:generic.knockback_resistance")).orElseThrow();
        var attackHolder = BuiltInRegistries.ATTRIBUTE.getHolder(
            ResourceLocation.parse("minecraft:generic.attack_speed")).orElseThrow();
        var health = player.getAttribute(healthHolder);
        var speed = player.getAttribute(speedHolder);
        var knockback = player.getAttribute(knockbackHolder);
        var attack = player.getAttribute(attackHolder);
        helper.assertTrue(health != null && speed != null && knockback != null && attack != null,
            "vanilla attributes must be available");
        double baselineHealth = health.getValue();
        double baselineSpeed = speed.getValue();
        double baselineKnockback = knockback.getValue();
        double baselineAttack = attack.getValue();
        var invested = AttributeRanks.of(Map.of(
            AttributeId.CONSTITUTION, 30L, AttributeId.AGILITY, 100L, AttributeId.STRENGTH, 300L));
        InvestedAttributeEffectsRuntime.refresh(player, invested);
        double effectiveHealth = health.getValue();
        double effectiveSpeed = speed.getValue();
        double effectiveKnockback = knockback.getValue();
        double effectiveAttack = attack.getValue();
        assertClose(helper, baselineHealth
            + AttributeInvestmentBonuses.value(AttributeInvestmentBonuses.Benefit.CONSTITUTION_MAX_HEALTH, 30L),
            effectiveHealth, "health bonus");
        helper.assertTrue(effectiveSpeed > baselineSpeed && effectiveKnockback > baselineKnockback
            && effectiveAttack > baselineAttack, "all four vanilla contributions must affect real stats");
        InvestedAttributeEffectsRuntime.refresh(player, invested);
        assertClose(helper, effectiveHealth, health.getValue(), "health idempotent");
        assertClose(helper, effectiveSpeed, speed.getValue(), "movement idempotent");
        assertClose(helper, effectiveKnockback, knockback.getValue(), "knockback idempotent");
        assertClose(helper, effectiveAttack, attack.getValue(), "attack speed idempotent");
        InvestedAttributeEffectsRuntime.refresh(player, AttributeRanks.empty());
        assertClose(helper, baselineHealth, health.getValue(), "health removal");
        assertClose(helper, baselineSpeed, speed.getValue(), "movement removal");
        assertClose(helper, baselineKnockback, knockback.getValue(), "knockback removal");
        assertClose(helper, baselineAttack, attack.getValue(), "attack speed removal");
        helper.succeed();
    }

    private static void assertClose(GameTestHelper helper, double expected, double actual, String stage) {
        helper.assertTrue(Math.abs(actual - expected) < 1e-8,
            stage + " expected=" + expected + ", actual=" + actual);
    }
}
