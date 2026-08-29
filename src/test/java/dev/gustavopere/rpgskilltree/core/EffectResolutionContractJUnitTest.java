package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class EffectResolutionContractJUnitTest {
    @Test
    void effectLayersHaveOneDeterministicPrecedenceOrder() {
        assertEquals(
            List.of(
                NodeEffectSource.INLINE_BONUS,
                NodeEffectSource.NODE_EFFECTS,
                NodeEffectSource.BEHAVIOR_HANDLER
            ),
            NodeEffectSource.precedence()
        );
        assertFalse(NodeEffectSource.INLINE_BONUS.serverAuthoritative());
        assertTrue(NodeEffectSource.NODE_EFFECTS.serverAuthoritative());
        assertTrue(NodeEffectSource.BEHAVIOR_HANDLER.serverAuthoritative());
    }

    @Test
    void generatedModifierIdentityIsStableAcrossRankAndSensitiveToOrigin() {
        ResourceLocation nodeId = ResourceLocation.parse("rpgskilltree:test/child");
        ResourceLocation sourceA = ResourceLocation.parse("pack_a:node_effects/test.json");
        ResourceLocation sourceB = ResourceLocation.parse("pack_b:node_effects/test.json");

        ResourceLocation first = NodeEffectIdPolicy.attribute(
            sourceA,
            nodeId,
            ResourceLocation.parse("minecraft:generic.attack_damage"),
            ModifierOperation.ADD_FLAT
        );
        ResourceLocation repeated = NodeEffectIdPolicy.attribute(
            sourceA,
            nodeId,
            ResourceLocation.parse("minecraft:generic.attack_damage"),
            ModifierOperation.ADD_FLAT
        );
        ResourceLocation otherOrigin = NodeEffectIdPolicy.attribute(
            sourceB,
            nodeId,
            ResourceLocation.parse("minecraft:generic.attack_damage"),
            ModifierOperation.ADD_FLAT
        );

        assertEquals(first, repeated, "rank changes must reuse the same modifier identity");
        assertNotEquals(first, otherOrigin, "external pack origin must participate in generated identity");
        assertEquals("rpgskilltree", first.getNamespace());
        assertTrue(first.getPath().startsWith("generated/node_effect/"));
    }

    @Test
    void behavioralEffectsResolveCurrentRankOnceAndInStableOrder() {
        PassiveNodeProgress progress = PassiveNodeProgress.of(Map.of(
            "rpgskilltree:test/a", 2,
            "rpgskilltree:test/b", 1
        ));
        List<NodeBehaviorEffect> effects = List.of(
            new NodeBehaviorEffect(
                "rpgskilltree:behavior/z",
                "rpgskilltree:test/b",
                "rpgskilltree:handler/z"
            ),
            new NodeBehaviorEffect(
                "rpgskilltree:behavior/a",
                "rpgskilltree:test/a",
                "rpgskilltree:handler/a"
            ),
            new NodeBehaviorEffect(
                "rpgskilltree:behavior/inactive",
                "rpgskilltree:test/inactive",
                "rpgskilltree:handler/inactive"
            )
        );

        List<ResolvedNodeBehaviorEffect> resolved = NodeEffectResolver.resolveBehaviors(progress, effects);

        assertEquals(2, resolved.size());
        assertEquals("rpgskilltree:behavior/a", resolved.get(0).effectId());
        assertEquals(2, resolved.get(0).rank());
        assertEquals("rpgskilltree:behavior/z", resolved.get(1).effectId());
        assertEquals(1, resolved.get(1).rank());
    }
}
