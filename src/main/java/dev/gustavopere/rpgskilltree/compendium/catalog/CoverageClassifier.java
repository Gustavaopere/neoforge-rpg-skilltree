package dev.gustavopere.rpgskilltree.compendium.catalog;

public final class CoverageClassifier {
    private CoverageClassifier() {}

    public static CoverageDecision classify(RegistryInventoryEntry entry, CoverageOverride override) {
        if (entry == null) {
            return new CoverageDecision(CoverageState.ERROR, "registry entry is null");
        }
        if (!entry.hasRequiredMetadata()) {
            return new CoverageDecision(CoverageState.ERROR, "registry entry is missing required inventory metadata");
        }
        if (override == null) {
            return new CoverageDecision(CoverageState.AUTO, "registry/runtime-derived coverage");
        }
        if (override.state() == CoverageState.IGNORED && override.reason().isBlank()) {
            throw new IllegalArgumentException("IGNORED coverage requires an explicit reason for " + entry.key());
        }
        if (override.state() == CoverageState.ERROR && override.reason().isBlank()) {
            throw new IllegalArgumentException("ERROR coverage requires an explicit reason for " + entry.key());
        }
        return new CoverageDecision(override.state(), override.reason());
    }
}
