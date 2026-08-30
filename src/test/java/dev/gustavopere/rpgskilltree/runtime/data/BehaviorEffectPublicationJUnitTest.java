package dev.gustavopere.rpgskilltree.runtime.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class BehaviorEffectPublicationJUnitTest {
    @Test
    void behaviorEffectsSurviveTheAtomicPublicationBoundary() {
        SkillTreeDataSnapshot before = SkillTreeDataCatalog.current();
        PreparedSkillTreeData prepared = SkillTreeDataLoader.prepare(
            Map.of(RULE_SOURCE, json("""
                {"treeId":"rpgskilltree:main","nodes":[{
                  "id":"rpgskilltree:test/root","maxRank":1,"costPerRank":1,"startingPoint":true
                }]}
                """)),
            Map.of(EFFECT_SOURCE, json("""
                {"attributes":[],"behaviors":[{
                  "nodeId":"rpgskilltree:test/root",
                  "handlerId":"rpgskilltree:test_handler"
                }]}
                """)),
            Map.of(SKILL_SOURCE, json("""
                {
                  "id":"rpgskilltree:test/root",
                  "positionX":0,
                  "positionY":0,
                  "buttonSize":20,
                  "bonuses":[]
                }
                """)),
            List.of()
        );

        try {
            SkillTreeDataCatalog.publish(prepared);
            assertEquals(prepared.behaviorEffects(), SkillTreeDataCatalog.current().behaviorEffects());
            assertEquals(prepared.behaviorEffects(), NodeEffectCatalog.behaviorEffects());
        } finally {
            SkillTreeDataCatalog.publish(new PreparedSkillTreeData(
                before.nodeRules(),
                before.treeIdsByNode(),
                before.attributeEffects(),
                before.behaviorEffects(),
                before.positions()
            ));
        }
    }

    private static JsonElement json(String value) {
        return JsonParser.parseString(value);
    }

    private static final ResourceLocation RULE_SOURCE = ResourceLocation.parse("rpgskilltree:node_rules/behavior_publication.json");
    private static final ResourceLocation EFFECT_SOURCE = ResourceLocation.parse("rpgskilltree:node_effects/behavior_publication.json");
    private static final ResourceLocation SKILL_SOURCE = ResourceLocation.parse("rpgskilltree:skills/main/behavior_publication.json");
}
