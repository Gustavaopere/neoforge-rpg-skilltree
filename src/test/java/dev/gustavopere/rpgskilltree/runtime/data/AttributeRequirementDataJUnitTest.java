package dev.gustavopere.rpgskilltree.runtime.data;

import static org.junit.jupiter.api.Assertions.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dev.gustavopere.rpgskilltree.core.AttributeId;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

class AttributeRequirementDataJUnitTest {
    private static final ResourceLocation SOURCE = ResourceLocation.parse("rpgskilltree:attribute_test/rules");
    private static final ResourceLocation SKILL = ResourceLocation.parse("rpgskilltree:attribute_test/position");
    private static final ResourceLocation NODE = ResourceLocation.parse("rpgskilltree:attribute_test_node");

    @Test
    void datapackAcceptsCanonicalLongThresholds() {
        var prepared = parse("{\"INTELLIGENCE\":20,\"CHARISMA\":2147483648}");
        var requirement = prepared.nodeRules().getFirst().requirement();
        assertEquals(20L, requirement.requiredAttributeRanks().get(AttributeId.INTELLIGENCE));
        assertEquals(2_147_483_648L, requirement.requiredAttributeRanks().get(AttributeId.CHARISMA));
    }

    @Test
    void invalidNamesAndValuesAreRejectedWithFieldContext() {
        var badKey = assertThrows(SkillTreeDataValidationException.class,
            () -> parse("{\"LUCK\":20}"));
        assertEquals("requiredAttributes", badKey.field());
        assertEquals(SOURCE, badKey.resourceId());

        var zero = assertThrows(SkillTreeDataValidationException.class,
            () -> parse("{\"AGILITY\":0}"));
        assertEquals("requiredAttributes.AGILITY", zero.field());

        var negative = assertThrows(SkillTreeDataValidationException.class,
            () -> parse("{\"STRENGTH\":-10}"));
        assertEquals("requiredAttributes.STRENGTH", negative.field());

        var fraction = assertThrows(SkillTreeDataValidationException.class,
            () -> parse("{\"CONSTITUTION\":4.5}"));
        assertEquals("requiredAttributes.CONSTITUTION", fraction.field());

        var overflowing = assertThrows(SkillTreeDataValidationException.class,
            () -> parse("{\"INTELLIGENCE\":9223372036854775808}"));
        assertEquals("requiredAttributes.INTELLIGENCE", overflowing.field());
    }

    private static PreparedSkillTreeData parse(String attrs) {
        String json = """
            {"treeId":"rpgskilltree:other","nodes":[{
              "id":"rpgskilltree:attribute_test_node","maxRank":1,
              "costPerRank":1,"startingPoint":true,
              "requiredAttributes":ATTRS
            }]}
            """.replace("ATTRS", attrs);
        Map<ResourceLocation, JsonElement> rules = Map.of(SOURCE, JsonParser.parseString(json));
        Map<ResourceLocation, JsonElement> skills = Map.of(SKILL, JsonParser.parseString("""
            {"id":"rpgskilltree:attribute_test_node","positionX":0,"positionY":0,"buttonSize":20}
            """));
        return SkillTreeDataLoader.prepare(rules, Map.of(), skills, List.of());
    }
}
