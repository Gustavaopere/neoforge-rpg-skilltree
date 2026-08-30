package dev.gustavopere.rpgskilltree.gametest;

import com.mojang.authlib.GameProfile;
import dev.gustavopere.rpgskilltree.core.ModifierOperation;
import dev.gustavopere.rpgskilltree.core.NodeAttributeEffect;
import dev.gustavopere.rpgskilltree.core.PassiveNodeProgress;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.runtime.data.NodeEffectCatalog;
import dev.gustavopere.rpgskilltree.runtime.effects.AttributeNodeEffectRuntime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
public final class AttributeModifierGameTests {
    private static final double EPSILON = 1.0e-9;
    private static final String NODE_ID = "rpgskilltree:gametest_attribute_modifier";
    private static final String EFFECT_ID = "rpgskilltree:gametest/attribute_modifier/attack_damage";
    private static final String ATTRIBUTE_ID = "minecraft:generic.attack_damage";

    private AttributeModifierGameTests() {
    }

    @GameTest(template = "foundation_empty")
    public static void allModifierOperationsAreDeterministicAndIdempotent(GameTestHelper helper) {
        List<NodeAttributeEffect> originalCatalog = NodeEffectCatalog.attributeEffects();
        try {
            verifyFlatOperation(helper);
            verifyPercentageOperation(helper, ModifierOperation.ADD_PERCENT_BASE, 1.20D, "percent-base");
            verifyPercentageOperation(helper, ModifierOperation.MULTIPLY_TOTAL, 1.21D, "multiply-total");
            helper.succeed();
        } finally {
            NodeEffectCatalog.replace(originalCatalog);
        }
    }

    private static void verifyFlatOperation(GameTestHelper helper) {
        var player = newTestPlayer(helper, "attribute-flat-test");
        var instance = attackDamage(player);
        var effect = new NodeAttributeEffect(
            EFFECT_ID,
            NODE_ID,
            ATTRIBUTE_ID,
            ModifierOperation.ADD_FLAT,
            2.5D
        );
        var learnedBuild = learnedBuild(2);
        var emptyBuild = ProgressionState.empty().withPassiveNodes(PassiveNodeProgress.empty());
        double baseline = instance.getValue();

        NodeEffectCatalog.replace(List.of(effect));
        AttributeNodeEffectRuntime.refresh(player, learnedBuild);
        double firstApply = instance.getValue();
        assertClose(helper, baseline + 5.0D, firstApply, "flat first apply");

        AttributeNodeEffectRuntime.refresh(player, learnedBuild);
        assertClose(helper, firstApply, instance.getValue(), "flat repeated apply must not stack");

        AttributeNodeEffectRuntime.refresh(player, emptyBuild);
        assertClose(helper, baseline, instance.getValue(), "flat removal must restore baseline");

        AttributeNodeEffectRuntime.refresh(player, learnedBuild);
        assertClose(helper, firstApply, instance.getValue(), "flat reapply must be deterministic");
        AttributeNodeEffectRuntime.refresh(player, emptyBuild);
    }

    private static void verifyPercentageOperation(
        GameTestHelper helper,
        ModifierOperation operation,
        double expectedMultiplier,
        String label
    ) {
        var player = newTestPlayer(helper, "attribute-" + label + "-test");
        var instance = attackDamage(player);
        var effects = List.of(
            new NodeAttributeEffect(
                "rpgskilltree:gametest/attribute_modifier/" + label + "/a",
                NODE_ID,
                ATTRIBUTE_ID,
                operation,
                0.10D
            ),
            new NodeAttributeEffect(
                "rpgskilltree:gametest/attribute_modifier/" + label + "/b",
                NODE_ID,
                ATTRIBUTE_ID,
                operation,
                0.10D
            )
        );
        var learnedBuild = learnedBuild(1);
        var emptyBuild = ProgressionState.empty().withPassiveNodes(PassiveNodeProgress.empty());
        double baseline = instance.getValue();

        NodeEffectCatalog.replace(effects);
        AttributeNodeEffectRuntime.refresh(player, learnedBuild);
        double firstApply = instance.getValue();
        assertClose(helper, baseline * expectedMultiplier, firstApply, label + " first apply");

        AttributeNodeEffectRuntime.refresh(player, learnedBuild);
        assertClose(helper, firstApply, instance.getValue(), label + " repeated apply must not stack");

        AttributeNodeEffectRuntime.refresh(player, emptyBuild);
        assertClose(helper, baseline, instance.getValue(), label + " removal must restore baseline");

        AttributeNodeEffectRuntime.refresh(player, learnedBuild);
        assertClose(helper, firstApply, instance.getValue(), label + " reapply must be deterministic");
        AttributeNodeEffectRuntime.refresh(player, emptyBuild);
    }

    private static ServerPlayer newTestPlayer(GameTestHelper helper, String name) {
        // Construct a real ServerPlayer/AttributeMap without registering it in PlayerList.
        // GameTestHelper.makeMockServerPlayerInLevel() fires the normal login lifecycle,
        // including owner-sync packets that require a negotiated client channel. That
        // lifecycle is outside this modifier-runtime acceptance and makes the fixture
        // fail before any attribute assertion executes.
        return new ServerPlayer(
            helper.getLevel().getServer(),
            helper.getLevel(),
            new GameProfile(UUID.randomUUID(), name),
            ClientInformation.createDefault()
        );
    }

    private static net.minecraft.world.entity.ai.attributes.AttributeInstance attackDamage(ServerPlayer player) {
        var attributeHolder = BuiltInRegistries.ATTRIBUTE.getHolder(ResourceLocation.parse(ATTRIBUTE_ID)).orElseThrow();
        return Objects.requireNonNull(player.getAttribute(attributeHolder), "test player attack damage attribute");
    }

    private static ProgressionState learnedBuild(int rank) {
        return ProgressionState.empty().withPassiveNodes(
            PassiveNodeProgress.of(Map.of(NODE_ID, rank))
        );
    }

    private static void assertClose(GameTestHelper helper, double expected, double actual, String stage) {
        helper.assertTrue(
            Math.abs(expected - actual) <= EPSILON,
            stage + ": expected=" + expected + ", actual=" + actual
        );
    }
}
