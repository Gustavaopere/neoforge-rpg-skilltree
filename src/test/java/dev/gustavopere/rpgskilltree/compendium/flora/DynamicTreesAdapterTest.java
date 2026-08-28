package dev.gustavopere.rpgskilltree.compendium.flora;

import dev.gustavopere.rpgskilltree.compendium.integration.flora.DynamicTreesFloraAdapter;
import java.util.Map;
import java.util.Set;

public final class DynamicTreesAdapterTest {
    public static void main(String[] args) {
        absentModIsNoOp();
        familyMetadataProducesTreeContribution();
        missingFamilyFailsSoft();
        System.out.println("DynamicTreesAdapterTest: PASS");
    }

    private static void absentModIsNoOp() {
        check(DynamicTreesFloraAdapter.enrich(Set.of(), "dynamictrees:oak", Map.of("family_id", "dynamictrees:oak")).isEmpty(), "absent mod");
    }

    private static void familyMetadataProducesTreeContribution() {
        FloraAdapterContribution contribution = DynamicTreesFloraAdapter.enrich(
            Set.of("dynamictrees"),
            "dynamictrees:oak",
            Map.of("family_id", "dynamictrees:oak", "species_id", "dynamictrees:oak")
        ).orElseThrow();
        eq(FloraKind.TREE_COMPONENT, contribution.kind());
        eq("dynamictrees:oak", contribution.metadata().get("family_id"));
        check(contribution.categories().contains("dynamic_tree"), "dynamic tree category");
    }

    private static void missingFamilyFailsSoft() {
        check(DynamicTreesFloraAdapter.enrich(Set.of("dynamictrees"), "dynamictrees:unknown", Map.of()).isEmpty(), "family metadata required");
    }

    private static void check(boolean condition, String label) {
        if (!condition) throw new AssertionError(label);
    }

    private static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
