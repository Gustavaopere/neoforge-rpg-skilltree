package dev.gustavopere.rpgskilltree.compendium.flora;

import dev.gustavopere.rpgskilltree.compendium.integration.flora.TfcFloraAdapter;
import java.util.Map;
import java.util.Set;

public final class TfcFloraAdapterTest {
    public static void main(String[] args) {
        absentModIsNoOp();
        foreignNamespaceIsNoOp();
        stableCropMetadataProducesContribution();
        insufficientMetadataFailsSoft();
        System.out.println("TfcFloraAdapterTest: PASS");
    }

    private static void absentModIsNoOp() {
        check(TfcFloraAdapter.enrich(Set.of(), "tfc:wheat", Map.of("kind", "crop")).isEmpty(), "absent TFC must be no-op");
    }

    private static void foreignNamespaceIsNoOp() {
        check(TfcFloraAdapter.enrich(Set.of("tfc"), "minecraft:wheat", Map.of("kind", "crop")).isEmpty(), "foreign namespace");
    }

    private static void stableCropMetadataProducesContribution() {
        FloraAdapterContribution contribution = TfcFloraAdapter.enrich(
            Set.of("tfc"),
            "tfc:barley",
            Map.of("kind", "crop", "climate_profile", "tfc:temperate_cereal")
        ).orElseThrow();
        eq(FloraKind.CROP, contribution.kind());
        check(contribution.categories().contains("tfc"), "tfc category");
        eq("tfc:temperate_cereal", contribution.metadata().get("climate_profile"));
    }

    private static void insufficientMetadataFailsSoft() {
        check(TfcFloraAdapter.enrich(Set.of("tfc"), "tfc:unknown_plant", Map.of()).isEmpty(), "no stable metadata must not guess");
    }

    private static void check(boolean condition, String label) {
        if (!condition) throw new AssertionError(label);
    }

    private static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
