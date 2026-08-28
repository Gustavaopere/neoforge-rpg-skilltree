package dev.gustavopere.rpgskilltree.compendium.catalog;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class RegistryInventoryTest {
    public static void main(String[] args) {
        vanillaNamespaceIsEnumerated();
        optionalNamespacePresentAppears();
        optionalNamespaceAbsentIsSafe();
        everyEntryReceivesCoverage();
        System.out.println("RegistryInventoryTest: PASS");
    }

    private static void vanillaNamespaceIsEnumerated() {
        RegistryInventory inventory = new RegistryInventory(List.of(
            entry(InventoryKind.ENTITY, "minecraft:zombie"),
            entry(InventoryKind.BIOME, "minecraft:plains")
        ));
        eq(2, inventory.byNamespace("minecraft").size());
    }

    private static void optionalNamespacePresentAppears() {
        RegistryInventory inventory = new RegistryInventory(List.of(
            entry(InventoryKind.ENTITY, "alexsmobs:grizzly_bear")
        ));
        eq(1, inventory.byNamespace("alexsmobs").size());
    }

    private static void optionalNamespaceAbsentIsSafe() {
        RegistryInventory inventory = new RegistryInventory(List.of(
            entry(InventoryKind.ENTITY, "minecraft:zombie")
        ));
        eq(List.of(), inventory.byNamespace("not_installed"));
    }

    private static void everyEntryReceivesCoverage() {
        RegistryInventory inventory = new RegistryInventory(List.of(
            entry(InventoryKind.ENTITY, "minecraft:zombie"),
            entry(InventoryKind.FLORA, "futureflora:blue_flower"),
            entry(InventoryKind.STRUCTURE, "futurestructures:tower")
        ));
        Map<String, CoverageDecision> coverage = inventory.classifyCoverage(Map.of(
            "STRUCTURE|futurestructures:tower",
            new CoverageOverride(CoverageState.ADAPTER, "structure metadata provider required")
        ));
        eq(3, coverage.size());
        eq(CoverageState.AUTO, coverage.get("ENTITY|minecraft:zombie").state());
        eq(CoverageState.AUTO, coverage.get("FLORA|futureflora:blue_flower").state());
        eq(CoverageState.ADAPTER, coverage.get("STRUCTURE|futurestructures:tower").state());
    }

    private static RegistryInventoryEntry entry(InventoryKind kind, String id) {
        String namespace = id.substring(0, id.indexOf(':'));
        return new RegistryInventoryEntry(kind, id, namespace, "translation." + id.replace(':', '.'), namespace, "fixture", true);
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
