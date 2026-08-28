package dev.gustavopere.rpgskilltree.compendium.flora;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumFact;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumSection;
import dev.gustavopere.rpgskilltree.compendium.provider.flora.CropProvider;
import dev.gustavopere.rpgskilltree.compendium.provider.flora.FloraRegistryProvider;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class CropProviderTest {
    public static void main(String[] args) {
        createsFungusAsFloraEntry();
        preservesAquaticEditorialCategory();
        createsCropWithVerifiedGrowthMetadata();
        omitsUnknownGrowthTime();
        keepsSeedAndProduceAsExplicitFacts();
        cropProviderRejectsNonCropDescriptor();
        System.out.println("CropProviderTest: PASS");
    }

    private static void createsFungusAsFloraEntry() {
        CompendiumEntry entry = FloraRegistryProvider.create(new FloraSpeciesFacts(
            "minecraft:red_mushroom", "minecraft", "block.minecraft.red_mushroom", FloraKind.FUNGUS,
            Set.of("fungo"), Set.of("minecraft:red_mushroom"), Set.of("minecraft:red_mushroom"),
            null, null, null, null
        ));
        eq(CompendiumEntryKind.FLORA, entry.id().kind());
        check(entry.categoryIds().contains("flora"), "flora category");
        check(entry.categoryIds().contains("fungo"), "fungus category");
    }

    private static void preservesAquaticEditorialCategory() {
        CompendiumEntry entry = FloraRegistryProvider.create(new FloraSpeciesFacts(
            "minecraft:seagrass", "minecraft", "block.minecraft.seagrass", FloraKind.AQUATIC_FLORA,
            Set.of(), Set.of("minecraft:seagrass"), Set.of(), null, null, null, null
        ));
        check(entry.categoryIds().contains("flora_aquatica"), "aquatic category");
    }

    private static void createsCropWithVerifiedGrowthMetadata() {
        CompendiumEntry entry = CropProvider.create(new FloraSpeciesFacts(
            "minecraft:wheat", "minecraft", "block.minecraft.wheat", FloraKind.CROP,
            Set.of("agricultura"), Set.of("minecraft:wheat"), Set.of("minecraft:wheat_seeds", "minecraft:wheat"),
            7, null, "minecraft:wheat_seeds", "minecraft:wheat"
        ));
        eq(CompendiumEntryKind.CROP, entry.id().kind());
        Map<String, CompendiumFact<?>> growth = facts(entry, "growth");
        eq(7, growth.get(FloraFactKeys.MAX_GROWTH_STAGE).value());
        check(!growth.containsKey(FloraFactKeys.DETERMINISTIC_GROWTH_TICKS), "unknown random growth time must be omitted");
    }

    private static void omitsUnknownGrowthTime() {
        CompendiumEntry entry = CropProvider.create(new FloraSpeciesFacts(
            "example:crop", "example", "block.example.crop", FloraKind.CROP,
            Set.of(), Set.of("example:crop"), Set.of(), 3, null, null, null
        ));
        check(!facts(entry, "growth").containsKey(FloraFactKeys.DETERMINISTIC_GROWTH_TICKS), "unknown time omitted");
    }

    private static void keepsSeedAndProduceAsExplicitFacts() {
        CompendiumEntry entry = CropProvider.create(new FloraSpeciesFacts(
            "example:tomato", "example", "block.example.tomato", FloraKind.CROP,
            Set.of(), Set.of("example:tomato_crop"), Set.of("example:tomato_seed", "example:tomato"),
            5, 1200L, "example:tomato_seed", "example:tomato"
        ));
        Map<String, CompendiumFact<?>> products = facts(entry, "products");
        eq("example:tomato_seed", products.get(FloraFactKeys.SEED_ITEM).value());
        eq("example:tomato", products.get(FloraFactKeys.PRODUCE_ITEM).value());
        eq(1200L, facts(entry, "growth").get(FloraFactKeys.DETERMINISTIC_GROWTH_TICKS).value());
    }

    private static void cropProviderRejectsNonCropDescriptor() {
        try {
            CropProvider.create(new FloraSpeciesFacts(
                "example:flower", "example", "block.example.flower", FloraKind.FLORA,
                Set.of(), Set.of("example:flower"), Set.of(), null, null, null, null
            ));
            throw new AssertionError("non-crop must fail");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static Map<String, CompendiumFact<?>> facts(CompendiumEntry entry, String sectionId) {
        CompendiumSection section = entry.sections().stream()
            .filter(candidate -> candidate.sectionId().equals(sectionId))
            .findFirst()
            .orElseThrow(() -> new AssertionError("missing section " + sectionId));
        return section.facts().stream().collect(Collectors.toMap(CompendiumFact::factKey, fact -> fact));
    }

    private static void check(boolean condition, String label) {
        if (!condition) throw new AssertionError(label);
    }

    private static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
