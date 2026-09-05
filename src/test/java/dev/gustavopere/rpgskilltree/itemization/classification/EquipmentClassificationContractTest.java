package dev.gustavopere.rpgskilltree.itemization.classification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class EquipmentClassificationContractTest {
    private static final ResourceLocation STRUCTURAL = id("structural");
    private static final ResourceLocation FALLBACK = id("fallback");
    private static final ResourceLocation OVERRIDE = id("override");

    @Test
    void structuralCategoriesAreComposable() {
        EquipmentClassifier classifier = new EquipmentClassifier(EquipmentOverrideCatalog.empty(), List.of());
        EquipmentProbe probe = probe(
            "minecraft:diamond_axe",
            true,
            false,
            false,
            true,
            true,
            Set.of(EquipmentCategory.MELEE_AXE, EquipmentCategory.MELEE_GENERIC, EquipmentCategory.UTILITY_TOOL)
        );

        EquipmentClassification result = classifier.classify(probe);

        assertTrue(result.eligible());
        assertEquals(
            Set.of(EquipmentCategory.MELEE_AXE, EquipmentCategory.MELEE_GENERIC, EquipmentCategory.UTILITY_TOOL),
            result.categories()
        );
        assertEquals(STRUCTURAL, result.providerId());
        assertFalse(result.fallbackUsed());
    }

    @Test
    void unknownDurableEquipmentGetsConservativeGenericFallback() {
        EquipmentClassifier classifier = new EquipmentClassifier(EquipmentOverrideCatalog.empty(), List.of());
        EquipmentClassification result = classifier.classify(probe(
            "example:unknown_tool",
            true,
            false,
            false,
            false,
            true,
            Set.of()
        ));

        assertTrue(result.eligible());
        assertEquals(Set.of(EquipmentCategory.GENERIC_EQUIPMENT), result.categories());
        assertEquals(FALLBACK, result.providerId());
        assertTrue(result.fallbackUsed());
    }

    @Test
    void commonBlocksAndConsumablesAreNotEligibleByIncidentalSignals() {
        EquipmentClassifier classifier = new EquipmentClassifier(EquipmentOverrideCatalog.empty(), List.of());

        EquipmentClassification block = classifier.classify(probe(
            "minecraft:stone",
            false,
            true,
            false,
            false,
            false,
            Set.of()
        ));
        EquipmentClassification food = classifier.classify(probe(
            "minecraft:apple",
            false,
            false,
            true,
            false,
            false,
            Set.of()
        ));

        assertFalse(block.eligible());
        assertTrue(block.categories().isEmpty());
        assertFalse(food.eligible());
        assertTrue(food.categories().isEmpty());
    }

    @Test
    void dataDrivenWhitelistAndBlacklistHaveFinalAuthority() {
        EquipmentClassificationRule whitelist = new EquipmentClassificationRule(
            id("whitelist_focus"),
            10,
            Set.of(ResourceLocation.parse("example:focus")),
            Set.of(),
            EligibilityOverride.WHITELIST,
            false,
            Set.of(EquipmentCategory.MAGIC_FOCUS, EquipmentCategory.MAGIC_EQUIPMENT),
            Set.of()
        );
        EquipmentClassificationRule blacklist = new EquipmentClassificationRule(
            id("blacklist_sword"),
            10,
            Set.of(ResourceLocation.parse("example:forbidden_sword")),
            Set.of(),
            EligibilityOverride.BLACKLIST,
            false,
            Set.of(),
            Set.of()
        );
        EquipmentClassifier classifier = new EquipmentClassifier(
            new EquipmentOverrideCatalog(List.of(whitelist, blacklist)),
            List.of()
        );

        EquipmentClassification focus = classifier.classify(probe(
            "example:focus",
            false,
            false,
            false,
            false,
            false,
            Set.of()
        ));
        EquipmentClassification sword = classifier.classify(probe(
            "example:forbidden_sword",
            true,
            false,
            false,
            true,
            true,
            Set.of(EquipmentCategory.MELEE_SWORD, EquipmentCategory.MELEE_GENERIC)
        ));

        assertTrue(focus.eligible());
        assertEquals(Set.of(EquipmentCategory.MAGIC_FOCUS, EquipmentCategory.MAGIC_EQUIPMENT), focus.categories());
        assertEquals(OVERRIDE, focus.providerId());
        assertEquals(List.of(id("whitelist_focus")), focus.matchedOverrideIds());
        assertFalse(sword.eligible());
        assertTrue(sword.categories().isEmpty());
        assertEquals(OVERRIDE, sword.providerId());
    }

    @Test
    void higherPriorityOverridesApplyLastDeterministically() {
        ResourceLocation tag = ResourceLocation.parse("example:tools");
        EquipmentClassificationRule low = new EquipmentClassificationRule(
            id("low"),
            1,
            Set.of(),
            Set.of(tag),
            EligibilityOverride.WHITELIST,
            false,
            Set.of(EquipmentCategory.UTILITY_TOOL),
            Set.of()
        );
        EquipmentClassificationRule high = new EquipmentClassificationRule(
            id("high"),
            100,
            Set.of(),
            Set.of(tag),
            EligibilityOverride.BLACKLIST,
            false,
            Set.of(),
            Set.of()
        );
        EquipmentClassifier classifier = new EquipmentClassifier(new EquipmentOverrideCatalog(List.of(high, low)), List.of());
        EquipmentProbe probe = new EquipmentProbe(
            ResourceLocation.parse("example:tool"),
            Set.of(tag),
            true,
            false,
            false,
            true,
            true,
            Set.of(EquipmentCategory.UTILITY_TOOL)
        );

        EquipmentClassification result = classifier.classify(probe);

        assertFalse(result.eligible());
        assertEquals(List.of(id("low"), id("high")), result.matchedOverrideIds());
    }

    @Test
    void adaptersCanClassifyNonConventionalEquipmentWithoutOwningCoreTypes() {
        EquipmentClassificationAdapter adapter = new EquipmentClassificationAdapter() {
            @Override
            public ResourceLocation providerId() {
                return ResourceLocation.parse("example:adapter");
            }

            @Override
            public int priority() {
                return 50;
            }

            @Override
            public Optional<EquipmentAdapterContribution> classify(EquipmentProbe probe) {
                if (!probe.itemId().equals(ResourceLocation.parse("example:jetpack"))) {
                    return Optional.empty();
                }
                return Optional.of(new EquipmentAdapterContribution(
                    EligibilityOverride.WHITELIST,
                    Set.of(EquipmentCategory.MOBILITY_JETPACK, EquipmentCategory.TECHNOLOGY_ENERGY)
                ));
            }
        };
        EquipmentClassifier classifier = new EquipmentClassifier(EquipmentOverrideCatalog.empty(), List.of(adapter));

        EquipmentClassification result = classifier.classify(probe(
            "example:jetpack",
            false,
            false,
            false,
            false,
            true,
            Set.of()
        ));

        assertTrue(result.eligible());
        assertEquals(Set.of(EquipmentCategory.MOBILITY_JETPACK, EquipmentCategory.TECHNOLOGY_ENERGY), result.categories());
        assertEquals(ResourceLocation.parse("example:adapter"), result.providerId());
        assertFalse(result.fallbackUsed());
    }

    @Test
    void coverageReportIsDeterministicAndFlagsPotentialEquipmentThatWasIgnored() {
        EquipmentClassifier classifier = new EquipmentClassifier(EquipmentOverrideCatalog.empty(), List.of());
        EquipmentProbe ignored = probe("example:odd_wearable", false, false, false, false, true, Set.of());
        EquipmentProbe sword = probe(
            "minecraft:diamond_sword",
            true,
            false,
            false,
            true,
            true,
            Set.of(EquipmentCategory.MELEE_SWORD, EquipmentCategory.MELEE_GENERIC)
        );

        EquipmentCoverageReport report = EquipmentCoverageReport.generate(List.of(ignored, sword), classifier);

        assertEquals(List.of(ResourceLocation.parse("example:odd_wearable")), report.ignoredPotentialEquipment());
        assertEquals(
            List.of(ResourceLocation.parse("example:odd_wearable"), ResourceLocation.parse("minecraft:diamond_sword")),
            report.entries().stream().map(EquipmentCoverageEntry::itemId).toList()
        );
        assertFalse(report.entries().getFirst().classification().eligible());
        assertEquals(STRUCTURAL, report.entries().get(1).classification().providerId());
    }

    @Test
    void returnedSetsAndListsAreDefensivelyImmutable() {
        EquipmentClassifier classifier = new EquipmentClassifier(EquipmentOverrideCatalog.empty(), List.of());
        EquipmentClassification result = classifier.classify(probe(
            "minecraft:diamond_sword",
            true,
            false,
            false,
            true,
            true,
            Set.of(EquipmentCategory.MELEE_SWORD)
        ));

        assertThrows(UnsupportedOperationException.class, () -> result.categories().add(EquipmentCategory.MELEE_AXE));
        assertThrows(UnsupportedOperationException.class, () -> result.matchedOverrideIds().add(id("illegal")));
    }

    private static EquipmentProbe probe(
        String itemId,
        boolean durable,
        boolean blockItem,
        boolean consumable,
        boolean explicitEquipmentSignal,
        boolean potentiallyEquipment,
        Set<EquipmentCategory> structuralCategories
    ) {
        return new EquipmentProbe(
            ResourceLocation.parse(itemId),
            Set.of(),
            durable,
            blockItem,
            consumable,
            explicitEquipmentSignal,
            potentiallyEquipment,
            structuralCategories
        );
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath("rpgskilltree", path);
    }
}
