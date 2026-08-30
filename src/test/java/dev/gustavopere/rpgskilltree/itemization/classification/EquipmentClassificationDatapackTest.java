package dev.gustavopere.rpgskilltree.itemization.classification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dev.gustavopere.rpgskilltree.runtime.itemization.EquipmentClassificationOverrides;
import dev.gustavopere.rpgskilltree.runtime.itemization.EquipmentClassificationReloadService;
import dev.gustavopere.rpgskilltree.runtime.itemization.EquipmentClassificationRuleParser;
import dev.gustavopere.rpgskilltree.runtime.itemization.EquipmentClassificationService;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

    @Test
    void reloadPublishesCompleteCatalogAtomicallyAndPreservesLastValidSnapshotOnFailure() {
        EquipmentClassificationOverrides.replace(EquipmentOverrideCatalog.empty());
        ResourceLocation validId = ResourceLocation.fromNamespaceAndPath("example", "valid");
        Map<ResourceLocation, JsonElement> valid = Map.of(
            validId,
            JsonParser.parseString("""
                {
                  "items": ["example:focus"],
                  "eligibility": "WHITELIST",
                  "add_categories": ["MAGIC_FOCUS"]
                }
                """)
        );

        EquipmentClassificationReloadService.reload(valid);
        EquipmentOverrideCatalog lastValid = EquipmentClassificationOverrides.snapshot();
        assertEquals(1, lastValid.rules().size());
        assertEquals(validId, lastValid.rules().getFirst().id());

        Map<ResourceLocation, JsonElement> invalid = Map.of(
            ResourceLocation.fromNamespaceAndPath("example", "invalid"),
            JsonParser.parseString("""
                {
                  "items": ["example:broken"],
                  "add_categories": ["NOT_A_CATEGORY"]
                }
                """)
        );

        assertThrows(IllegalArgumentException.class, () -> EquipmentClassificationReloadService.reload(invalid));
        assertEquals(lastValid.rules(), EquipmentClassificationOverrides.snapshot().rules());
    }

    @Test
    void runtimeClassificationReadsTheCurrentlyPublishedDatapackSnapshot() {
        EquipmentClassificationOverrides.replace(EquipmentOverrideCatalog.empty());
        EquipmentClassification before = EquipmentClassificationService.classify(new ItemStack(Items.STICK));
        assertFalse(before.eligible());

        EquipmentClassificationReloadService.reload(Map.of(
            ResourceLocation.fromNamespaceAndPath("example", "stick_focus"),
            JsonParser.parseString("""
                {
                  "items": ["minecraft:stick"],
                  "eligibility": "WHITELIST",
                  "add_categories": ["MAGIC_FOCUS", "MAGIC_EQUIPMENT"]
                }
                """)
        ));

        EquipmentClassification after = EquipmentClassificationService.classify(new ItemStack(Items.STICK));
        assertTrue(after.eligible());
        assertEquals(Set.of(EquipmentCategory.MAGIC_FOCUS, EquipmentCategory.MAGIC_EQUIPMENT), after.categories());
        assertEquals(ResourceLocation.fromNamespaceAndPath("rpgskilltree", "override"), after.providerId());
    }
}
