package dev.gustavopere.rpgskilltree.compendium.flora;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumFact;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumSection;
import dev.gustavopere.rpgskilltree.compendium.provider.flora.FloraRegistryProvider;
import dev.gustavopere.rpgskilltree.compendium.provider.flora.TreeProvider;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class TreeGroupingTest {
    public static void main(String[] args) {
        groupsVanillaTreeComponentsIntoOneSpecies();
        preservesModdedSpeciesNamespace();
        strippedLogRemainsComponentNotSpecies();
        groupingIsDeterministicAcrossInputOrder();
        conflictingComponentRolesFailClosed();
        duplicateIdenticalComponentIsDeduplicated();
        rawTreeComponentCannotBecomeStandaloneFloraEntry();
        System.out.println("TreeGroupingTest: PASS");
    }

    private static void groupsVanillaTreeComponentsIntoOneSpecies() {
        CompendiumEntry entry = TreeProvider.create(oak(List.of(
            component(TreeComponentRole.SAPLING, "minecraft:oak_sapling"),
            component(TreeComponentRole.LOG, "minecraft:oak_log"),
            component(TreeComponentRole.WOOD, "minecraft:oak_wood"),
            component(TreeComponentRole.STRIPPED_LOG, "minecraft:stripped_oak_log"),
            component(TreeComponentRole.LEAVES, "minecraft:oak_leaves")
        )));
        eq(CompendiumEntryKind.TREE, entry.id().kind());
        eq("minecraft:oak", entry.id().resourceLocation());
        check(entry.categoryIds().contains("arvore"), "tree category");
        Map<String, CompendiumFact<?>> components = facts(entry, "components");
        eq("minecraft:oak_sapling", components.get("tree_component.sapling").value());
        eq("minecraft:oak_log", components.get("tree_component.log").value());
        eq("minecraft:stripped_oak_log", components.get("tree_component.stripped_log").value());
    }

    private static void preservesModdedSpeciesNamespace() {
        TreeSpeciesDescriptor descriptor = new TreeSpeciesDescriptor(
            "example:redwood", "example", "block.example.redwood_sapling", Set.of("conifera"),
            List.of(component(TreeComponentRole.SAPLING, "example:redwood_sapling"), component(TreeComponentRole.LOG, "example:redwood_log"))
        );
        CompendiumEntry entry = TreeProvider.create(descriptor);
        eq("example:redwood", entry.id().resourceLocation());
        eq("example", entry.sourceModId());
        check(entry.categoryIds().contains("conifera"), "editorial category");
    }

    private static void strippedLogRemainsComponentNotSpecies() {
        CompendiumEntry entry = TreeProvider.create(oak(List.of(
            component(TreeComponentRole.LOG, "minecraft:oak_log"),
            component(TreeComponentRole.STRIPPED_LOG, "minecraft:stripped_oak_log")
        )));
        eq("minecraft:oak", entry.id().resourceLocation());
        check(entry.sections().stream().flatMap(section -> section.facts().stream())
            .anyMatch(fact -> "minecraft:stripped_oak_log".equals(fact.value())), "stripped component retained");
    }

    private static void groupingIsDeterministicAcrossInputOrder() {
        CompendiumEntry first = TreeProvider.create(oak(List.of(
            component(TreeComponentRole.LEAVES, "minecraft:oak_leaves"),
            component(TreeComponentRole.SAPLING, "minecraft:oak_sapling"),
            component(TreeComponentRole.LOG, "minecraft:oak_log")
        )));
        CompendiumEntry second = TreeProvider.create(oak(List.of(
            component(TreeComponentRole.LOG, "minecraft:oak_log"),
            component(TreeComponentRole.LEAVES, "minecraft:oak_leaves"),
            component(TreeComponentRole.SAPLING, "minecraft:oak_sapling")
        )));
        eq(factsList(first, "components"), factsList(second, "components"));
    }

    private static void conflictingComponentRolesFailClosed() {
        try {
            oak(List.of(
                component(TreeComponentRole.LOG, "minecraft:oak_log"),
                component(TreeComponentRole.LOG, "minecraft:birch_log")
            ));
            throw new AssertionError("conflicting role must fail");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void duplicateIdenticalComponentIsDeduplicated() {
        TreeSpeciesDescriptor descriptor = oak(List.of(
            component(TreeComponentRole.LOG, "minecraft:oak_log"),
            component(TreeComponentRole.LOG, "minecraft:oak_log")
        ));
        eq(1, descriptor.components().size());
    }

    private static void rawTreeComponentCannotBecomeStandaloneFloraEntry() {
        try {
            FloraRegistryProvider.create(new FloraSpeciesFacts(
                "minecraft:oak_sapling", "minecraft", "block.minecraft.oak_sapling", FloraKind.TREE_COMPONENT,
                Set.of(), Set.of("minecraft:oak_sapling"), Set.of("minecraft:oak_sapling"),
                null, null, null, null
            ));
            throw new AssertionError("tree component must not create a standalone species page");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static TreeSpeciesDescriptor oak(List<TreeComponent> components) {
        return new TreeSpeciesDescriptor("minecraft:oak", "minecraft", "block.minecraft.oak_sapling", Set.of(), components);
    }

    private static TreeComponent component(TreeComponentRole role, String id) {
        return new TreeComponent(role, id);
    }

    private static Map<String, CompendiumFact<?>> facts(CompendiumEntry entry, String sectionId) {
        return factsList(entry, sectionId).stream().collect(Collectors.toMap(CompendiumFact::factKey, fact -> fact));
    }

    private static List<CompendiumFact<?>> factsList(CompendiumEntry entry, String sectionId) {
        CompendiumSection section = entry.sections().stream()
            .filter(candidate -> candidate.sectionId().equals(sectionId))
            .findFirst().orElseThrow(() -> new AssertionError("missing section " + sectionId));
        return section.facts();
    }

    private static void check(boolean condition, String label) {
        if (!condition) throw new AssertionError(label);
    }

    private static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
