package dev.gustavopere.rpgskilltree.compendium.catalog;

import java.util.Objects;

public final class CoverageClassifierTest {
    public static void main(String[] args) {
        validUnknownEntryDefaultsToAuto();
        ignoredRequiresExplicitReason();
        adapterOverrideIsPreserved();
        malformedEntryBecomesError();
        System.out.println("CoverageClassifierTest: PASS");
    }

    private static void validUnknownEntryDefaultsToAuto() {
        RegistryInventoryEntry entry = entry(InventoryKind.ENTITY, "somefuturemod:creature");
        CoverageDecision decision = CoverageClassifier.classify(entry, null);
        eq(CoverageState.AUTO, decision.state());
    }

    private static void ignoredRequiresExplicitReason() {
        RegistryInventoryEntry entry = entry(InventoryKind.STRUCTURE, "minecolonies:work_camp");
        expect(IllegalArgumentException.class, () -> CoverageClassifier.classify(
            entry,
            new CoverageOverride(CoverageState.IGNORED, "")
        ));
    }

    private static void adapterOverrideIsPreserved() {
        RegistryInventoryEntry entry = entry(InventoryKind.ENTITY, "complexmod:scripted_entity");
        CoverageDecision decision = CoverageClassifier.classify(
            entry,
            new CoverageOverride(CoverageState.ADAPTER, "provider-specific ecology data")
        );
        eq(CoverageState.ADAPTER, decision.state());
        eq("provider-specific ecology data", decision.reason());
    }

    private static void malformedEntryBecomesError() {
        RegistryInventoryEntry entry = new RegistryInventoryEntry(
            InventoryKind.BIOME,
            "",
            "",
            "",
            "",
            "fixture",
            true
        );
        CoverageDecision decision = CoverageClassifier.classify(entry, null);
        eq(CoverageState.ERROR, decision.state());
        if (decision.reason() == null || decision.reason().isBlank()) {
            throw new AssertionError("ERROR coverage must explain the failure");
        }
    }

    private static RegistryInventoryEntry entry(InventoryKind kind, String id) {
        String namespace = id.substring(0, id.indexOf(':'));
        return new RegistryInventoryEntry(kind, id, namespace, "translation." + id.replace(':', '.'), namespace, "fixture", true);
    }

    private static void expect(Class<? extends Throwable> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable thrown) {
            if (type.isInstance(thrown)) return;
            throw new AssertionError("expected " + type.getSimpleName() + " but got " + thrown, thrown);
        }
        throw new AssertionError("expected " + type.getSimpleName());
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
