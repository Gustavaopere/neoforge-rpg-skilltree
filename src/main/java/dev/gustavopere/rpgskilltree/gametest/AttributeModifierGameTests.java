package dev.gustavopere.rpgskilltree.gametest;

import dev.gustavopere.rpgskilltree.core.ModifierOperation;
import dev.gustavopere.rpgskilltree.core.NodeAttributeEffect;
import dev.gustavopere.rpgskilltree.core.PassiveNodeProgress;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.runtime.data.NodeEffectCatalog;
import dev.gustavopere.rpgskilltree.runtime.effects.AttributeNodeEffectRuntime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
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

    @SuppressWarnings("removal")
    @GameTest(template = "foundation_empty")
    public static void applyingRemovingAndReapplyingBuildIsIdempotent(GameTestHelper helper) {
        var player = helper.makeMockServerPlayerInLevel();
        var attributeHolder = BuiltInRegistries.ATTRIBUTE.getHolder(ResourceLocation.parse(ATTRIBUTE_ID)).orElseThrow();
        var instance = Objects.requireNonNull(player.getAttribute(attributeHolder), "mock player attack damage attribute");

        List<NodeAttributeEffect> originalCatalog = NodeEffectCatalog.attributeEffects();
        var effect = new NodeAttributeEffect(
            EFFECT_ID,
            NODE_ID,
            ATTRIBUTE_ID,
            ModifierOperation.ADD_FLAT,
            2.5
        );
        var learnedBuild = ProgressionState.empty().withPassiveNodes(
            PassiveNodeProgress.of(Map.of(NODE_ID, 2))
        );
        var emptyBuild = ProgressionState.empty().withPassiveNodes(PassiveNodeProgress.empty());
        double baseline = instance.getValue();

        NodeEffectCatalog.replace(List.of(effect));
        try {
            AttributeNodeEffectRuntime.refresh(player, learnedBuild);
            double firstApply = instance.getValue();
            assertClose(helper, baseline + 5.0, firstApply, "first apply");

            AttributeNodeEffectRuntime.refresh(player, learnedBuild);
            double repeatedApply = instance.getValue();
            assertClose(helper, firstApply, repeatedApply, "repeated apply must not stack");

            AttributeNodeEffectRuntime.refresh(player, emptyBuild);
            double removed = instance.getValue();
            assertClose(helper, baseline, removed, "removal must restore baseline");

            AttributeNodeEffectRuntime.refresh(player, learnedBuild);
            double reapplied = instance.getValue();
            assertClose(helper, firstApply, reapplied, "reapply must reproduce the same attribute value");

            helper.succeed();
        } finally {
            NodeEffectCatalog.replace(originalCatalog);
            AttributeNodeEffectRuntime.refresh(player, ProgressionState.empty());
        }
    }

    private static void assertClose(GameTestHelper helper, double expected, double actual, String stage) {
        helper.assertTrue(
            Math.abs(expected - actual) <= EPSILON,
            stage + ": expected=" + expected + ", actual=" + actual
        );
    }
}
