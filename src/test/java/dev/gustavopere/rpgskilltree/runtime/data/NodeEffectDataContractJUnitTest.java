package dev.gustavopere.rpgskilltree.runtime.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dev.gustavopere.rpgskilltree.core.ModifierOperation;
import dev.gustavopere.rpgskilltree.core.NodeEffectIdPolicy;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class NodeEffectDataContractJUnitTest {
    @Test
    void omittedEffectIdsAreGeneratedFromOriginAndBehaviorsJoinTheAtomicCandidate() {
        PreparedSkillTreeData first = prepare(EFFECT_SOURCE_A);
        PreparedSkillTreeData repeated = prepare(EFFECT_SOURCE_A);
        PreparedSkillTreeData otherOrigin = prepare(EFFECT_SOURCE_B);

        String expectedAttribute = NodeEffectIdPolicy.attribute(
            EFFECT_SOURCE_A.toString(),
            NODE_ID.toString(),
            "minecraft:generic.attack_damage",
            ModifierOperation.ADD_FLAT
        );
        String expectedBehavior = NodeEffectIdPolicy.behavior(
            EFFECT_SOURCE_A.toString(),
            NODE_ID.toString(),
            "rpgskilltree:test_handler"
        );

        assertEquals(expectedAttribute, first.attributeEffects().getFirst().effectId());
        assertEquals(expectedBehavior, first.behaviorEffects().getFirst().effectId());
        assertEquals("rpgskilltree:test_handler", first.behaviorEffects().getFirst().handlerId());
        assertEquals(first.attributeEffects().getFirst().effectId(), repeated.attributeEffects().getFirst().effectId());
        assertEquals(first.behaviorEffects().getFirst().effectId(), repeated.behaviorEffects().getFirst().effectId());
        assertNotEquals(first.attributeEffects().getFirst().effectId(), otherOrigin.attributeEffects().getFirst().effectId());
        assertNotEquals(first.behaviorEffects().getFirst().effectId(), otherOrigin.behaviorEffects().getFirst().effectId());
    }

    @Test
    void behaviorCannotReferenceUnknownNode() {
        SkillTreeDataValidationException failure = assertThrows(
            SkillTreeDataValidationException.class,
            () -> SkillTreeDataLoader.prepare(
                rules(),
                Map.of(EFFECT_SOURCE_A, json("""
                    {"attributes":[],"behaviors":[{
                      "nodeId":"rpgskilltree:test/missing",
                      "handlerId":"rpgskilltree:test_handler"
                    }]}
                    """)),
                skills(),
                List.of()
            )
        );
        assertEquals("nodeId", failure.field());
    }

    private static PreparedSkillTreeData prepare(ResourceLocation effectSource) {
        return SkillTreeDataLoader.prepare(
            rules(),
            Map.of(effectSource, json("""
                {
                  "attributes":[{
                    "nodeId":"rpgskilltree:test/root",
                    "attributeId":"minecraft:generic.attack_damage",
                    "operation":"ADD_FLAT",
                    "amountPerRank":1.5
                  }],
                  "behaviors":[{
                    "nodeId":"rpgskilltree:test/root",
                    "handlerId":"rpgskilltree:test_handler"
                  }]
                }
                """)),
            skills(),
            List.of()
        );
    }

    private static Map<ResourceLocation, JsonElement> rules() {
        return Map.of(RULE_SOURCE, json("""
            {"treeId":"rpgskilltree:main","nodes":[{
              "id":"rpgskilltree:test/root","maxRank":3,"costPerRank":1,"startingPoint":true
            }]}
            """));
    }

    private static Map<ResourceLocation, JsonElement> skills() {
        return Map.of(SKILL_SOURCE, json("""
            {
              "id":"rpgskilltree:test/root",
              "positionX":0,
              "positionY":0,
              "buttonSize":20,
              "bonuses":[]
            }
            """));
    }

    private static JsonElement json(String value) {
        return JsonParser.parseString(value);
    }

    private static final ResourceLocation NODE_ID = ResourceLocation.parse("rpgskilltree:test/root");
    private static final ResourceLocation RULE_SOURCE = ResourceLocation.parse("rpgskilltree:node_rules/effect_contract.json");
    private static final ResourceLocation SKILL_SOURCE = ResourceLocation.parse("rpgskilltree:skills/main/effect_contract.json");
    private static final ResourceLocation EFFECT_SOURCE_A = ResourceLocation.parse("rpgskilltree:node_effects/effect_contract_a.json");
    private static final ResourceLocation EFFECT_SOURCE_B = ResourceLocation.parse("externalpack:node_effects/effect_contract_a.json");
}
