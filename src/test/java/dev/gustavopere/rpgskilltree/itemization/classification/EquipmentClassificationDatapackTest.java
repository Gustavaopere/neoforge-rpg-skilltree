package dev.gustavopere.rpgskilltree.itemization.classification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonParser;
import dev.gustavopere.rpgskilltree.runtime.itemization.EquipmentClassificationRuleParser;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class EquipmentClassificationDatapackTest {
    @Test
    void parserBuildsComposableWhitelistRuleFromDatapackJson() {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath("example", "magic_focus");
        EquipmentClassificationRule rule = EquipmentClassificationRuleParser.parse(id, JsonParser.parseString("""
            {
              "priority": 25,
              "items": ["example:focus"],
              "tags": ["example:magic_equipment"],
              "eligibility": "WHITELIST",
              "replace_categories": true,
              "add_categories": ["MAGIC_FOCUS", "MAGIC_EQUIPMENT"],
              "remove_categories": ["UTILITY_TOOL"]
            }
            """).getAsJsonObject());

        assertEquals(id, rule.id());
        assertEquals(25, rule.priority());
        assertEquals(Set.of(ResourceLocation.parse("example:focus")), rule.items());
        assertEquals(Set.of(ResourceLocation.parse("example:magic_equipment")), rule.tags());
        assertEquals(EligibilityOverride.WHITELIST, rule.eligibility());
        assertTrue(rule.replaceCategories());
        assertEquals(Set.of(EquipmentCategory.MAGIC_FOCUS, EquipmentCategory.MAGIC_EQUIPMENT), rule.addCategories());
        assertEquals(Set.of(EquipmentCategory.UTILITY_TOOL), rule.removeCategories());
    }

    @Test
    void parserUsesSafeDefaultsAndRejectsInvalidCategories() {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath("example", "defaults");
        EquipmentClassificationRule rule = EquipmentClassificationRuleParser.parse(id, JsonParser.parseString("""
            {
              "items": ["example:tool"]
            }
            """).getAsJsonObject());

        assertEquals(0, rule.priority());
        assertEquals(EligibilityOverride.INHERIT, rule.eligibility());
        assertTrue(rule.tags().isEmpty());
        assertTrue(rule.addCategories().isEmpty());
        assertTrue(rule.removeCategories().isEmpty());

        assertThrows(
            IllegalArgumentException.class,
            () -> EquipmentClassificationRuleParser.parse(
                ResourceLocation.fromNamespaceAndPath("example", "invalid"),
                JsonParser.parseString("""
                    {
                      "items": ["example:bad"],
                      "add_categories": ["NOT_A_CATEGORY"]
                    }
                    """).getAsJsonObject()
            )
        );
    }
}
