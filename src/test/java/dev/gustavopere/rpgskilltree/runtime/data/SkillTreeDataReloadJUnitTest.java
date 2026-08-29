package dev.gustavopere.rpgskilltree.runtime.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class SkillTreeDataReloadJUnitTest {
    @Test
    void publishesRulesEffectsAndPositionsAsOneRevision() {
        long before = SkillTreeDataCatalog.current().revision();
        SkillTreeDataCatalog.publish(SkillTreeDataLoader.prepare(
            rules(validRules()),
            effects(validEffects()),
            skills(validRootSkill(), validChildSkill()),
            List.of()
        ));

        SkillTreeDataSnapshot snapshot = SkillTreeDataCatalog.current();
        assertEquals(before + 1L, snapshot.revision());
        assertEquals("rpgskilltree:main", snapshot.treeId(ROOT).toString());
        assertEquals(2, snapshot.definitions().size());
        assertEquals(1, snapshot.attributeEffects().size());
        assertEquals(12.5D, snapshot.positions().get(ROOT).x());
        assertEquals(-8.0D, snapshot.positions().get(ROOT).y());
        assertNotNull(TreeRuleCatalog.definition(ROOT).orElse(null));
        assertEquals("rpgskilltree:test/effect", NodeEffectCatalog.attributeEffects().getFirst().effectId());
    }

    @Test
    void invalidEffectReferencePreservesLastGoodSnapshotAndReportsResourceIdAndField() {
        SkillTreeDataCatalog.publish(SkillTreeDataLoader.prepare(
            rules(validRules()),
            effects(validEffects()),
            skills(validRootSkill(), validChildSkill()),
            List.of()
        ));
        SkillTreeDataSnapshot before = SkillTreeDataCatalog.current();

        SkillTreeDataValidationException failure = assertThrows(
            SkillTreeDataValidationException.class,
            () -> SkillTreeDataLoader.prepare(
                rules(validRules()),
                effects("""
                    {"attributes":[{
                      "effectId":"rpgskilltree:test/bad_effect",
                      "nodeId":"rpgskilltree:missing",
                      "attributeId":"minecraft:max_health",
                      "operation":"ADD_FLAT",
                      "amountPerRank":1.0
                    }]}
                    """),
                skills(validRootSkill(), validChildSkill()),
                List.of()
            )
        );

        assertEquals(EFFECT_SOURCE, failure.resourceId());
        assertEquals("rpgskilltree:test/bad_effect", failure.entryId());
        assertEquals("nodeId", failure.field());
        assertTrue(failure.getMessage().contains("rpgskilltree:missing"));
        assertEquals(before, SkillTreeDataCatalog.current(), "failed candidate must not publish partial state");
    }

    @Test
    void duplicateNodeIdReportsTheSecondSourceWithoutPublishing() {
        SkillTreeDataSnapshot before = SkillTreeDataCatalog.current();
        Map<ResourceLocation, JsonElement> duplicateRules = Map.of(
            RULE_SOURCE, json(validRules()),
            DUPLICATE_RULE_SOURCE, json("""
                {"treeId":"rpgskilltree:other","nodes":[{
                  "id":"rpgskilltree:test/root",
                  "maxRank":1,
                  "costPerRank":1,
                  "startingPoint":true
                }]}
                """)
        );

        SkillTreeDataValidationException failure = assertThrows(
            SkillTreeDataValidationException.class,
            () -> SkillTreeDataLoader.prepare(
                duplicateRules,
                effects(validEffects()),
                skills(validRootSkill(), validChildSkill()),
                List.of()
            )
        );

        assertEquals(DUPLICATE_RULE_SOURCE, failure.resourceId());
        assertEquals("rpgskilltree:test/root", failure.entryId());
        assertEquals("id", failure.field());
        assertTrue(failure.getMessage().contains(RULE_SOURCE.toString()));
        assertEquals(before, SkillTreeDataCatalog.current());
    }

    @Test
    void requiredRankCannotExceedReferencedNodeMaxRank() {
        SkillTreeDataValidationException failure = assertThrows(
            SkillTreeDataValidationException.class,
            () -> SkillTreeDataLoader.prepare(
                rules("""
                    {"treeId":"rpgskilltree:main","nodes":[
                      {"id":"rpgskilltree:test/root","maxRank":1,"costPerRank":1,"startingPoint":true},
                      {"id":"rpgskilltree:test/child","maxRank":1,"costPerRank":1,"startingPoint":false,
                       "requiredNodeRanks":{"rpgskilltree:test/root":2}}
                    ]}
                    """),
                effects(validEffects()),
                skills(validRootSkill(), validChildSkill()),
                List.of()
            )
        );

        assertEquals(RULE_SOURCE, failure.resourceId());
        assertEquals("rpgskilltree:test/child", failure.entryId());
        assertEquals("requiredNodeRanks.rpgskilltree:test/root", failure.field());
    }

    @Test
    void mainTreeNodeRequiresFiniteServerLayoutPosition() {
        SkillTreeDataValidationException missing = assertThrows(
            SkillTreeDataValidationException.class,
            () -> SkillTreeDataLoader.prepare(
                rules(validRules()),
                effects(validEffects()),
                skills(validRootSkill()),
                List.of()
            )
        );
        assertEquals(RULE_SOURCE, missing.resourceId());
        assertEquals("rpgskilltree:test/child", missing.entryId());
        assertEquals("position", missing.field());

        SkillTreeDataValidationException nonFinite = assertThrows(
            SkillTreeDataValidationException.class,
            () -> SkillTreeDataLoader.prepare(
                rules(validRules()),
                effects(validEffects()),
                skills(validRootSkill(), """
                    {"id":"rpgskilltree:test/child","positionX":1e400,"positionY":0,"buttonSize":20}
                    """),
                List.of()
            )
        );
        assertEquals("positionX", nonFinite.field());
    }

    @Test
    void invalidOperationAndZeroAmountAreFieldAware() {
        SkillTreeDataValidationException operation = assertThrows(
            SkillTreeDataValidationException.class,
            () -> SkillTreeDataLoader.prepare(
                rules(validRules()),
                effects("""
                    {"attributes":[{
                      "effectId":"rpgskilltree:test/effect","nodeId":"rpgskilltree:test/child",
                      "attributeId":"minecraft:max_health","operation":"OVERRIDE","amountPerRank":1.0
                    }]}
                    """),
                skills(validRootSkill(), validChildSkill()),
                List.of()
            )
        );
        assertEquals("operation", operation.field());

        SkillTreeDataValidationException amount = assertThrows(
            SkillTreeDataValidationException.class,
            () -> SkillTreeDataLoader.prepare(
                rules(validRules()),
                effects("""
                    {"attributes":[{
                      "effectId":"rpgskilltree:test/effect","nodeId":"rpgskilltree:test/child",
                      "attributeId":"minecraft:max_health","operation":"ADD_FLAT","amountPerRank":0.0
                    }]}
                    """),
                skills(validRootSkill(), validChildSkill()),
                List.of()
            )
        );
        assertEquals("amountPerRank", amount.field());
    }

    private static final ResourceLocation RULE_SOURCE = ResourceLocation.parse("rpgskilltree:node_rules/test.json");
    private static final ResourceLocation DUPLICATE_RULE_SOURCE = ResourceLocation.parse("rpgskilltree:node_rules/duplicate.json");
    private static final ResourceLocation EFFECT_SOURCE = ResourceLocation.parse("rpgskilltree:node_effects/test.json");
    private static final ResourceLocation ROOT_SKILL_SOURCE = ResourceLocation.parse("rpgskilltree:skills/main/root.json");
    private static final ResourceLocation CHILD_SKILL_SOURCE = ResourceLocation.parse("rpgskilltree:skills/main/child.json");
    private static final ResourceLocation ROOT = ResourceLocation.parse("rpgskilltree:test/root");

    private static Map<ResourceLocation, JsonElement> rules(String value) {
        return Map.of(RULE_SOURCE, json(value));
    }

    private static Map<ResourceLocation, JsonElement> effects(String value) {
        return Map.of(EFFECT_SOURCE, json(value));
    }

    private static Map<ResourceLocation, JsonElement> skills(String... values) {
        java.util.LinkedHashMap<ResourceLocation, JsonElement> result = new java.util.LinkedHashMap<>();
        if (values.length > 0) result.put(ROOT_SKILL_SOURCE, json(values[0]));
        if (values.length > 1) result.put(CHILD_SKILL_SOURCE, json(values[1]));
        return Map.copyOf(result);
    }

    private static JsonElement json(String value) {
        return JsonParser.parseString(value);
    }

    private static String validRules() {
        return """
            {"treeId":"rpgskilltree:main","nodes":[
              {"id":"rpgskilltree:test/root","maxRank":1,"costPerRank":1,"startingPoint":true,
               "neighbors":["rpgskilltree:test/child"]},
              {"id":"rpgskilltree:test/child","maxRank":2,"costPerRank":1,"startingPoint":false,
               "neighbors":["rpgskilltree:test/root"],
               "requiredNodes":["rpgskilltree:test/root"],
               "requiredNodeRanks":{"rpgskilltree:test/root":1}}
            ]}
            """;
    }

    private static String validEffects() {
        return """
            {"attributes":[{
              "effectId":"rpgskilltree:test/effect",
              "nodeId":"rpgskilltree:test/child",
              "attributeId":"minecraft:max_health",
              "operation":"ADD_FLAT",
              "amountPerRank":2.0
            }]}
            """;
    }

    private static String validRootSkill() {
        return """
            {"id":"rpgskilltree:test/root","positionX":12.5,"positionY":-8.0,"buttonSize":20}
            """;
    }

    private static String validChildSkill() {
        return """
            {"id":"rpgskilltree:test/child","positionX":40.0,"positionY":16.0,"buttonSize":20}
            """;
    }
}