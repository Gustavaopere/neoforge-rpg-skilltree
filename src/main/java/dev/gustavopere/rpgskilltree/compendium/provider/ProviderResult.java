package dev.gustavopere.rpgskilltree.compendium.provider;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import java.util.List;
import java.util.Objects;

public record ProviderResult(CompendiumEntry entry, List<ProviderDiagnostic> diagnostics) {
    public ProviderResult {
        Objects.requireNonNull(entry, "entry");
        diagnostics = List.copyOf(diagnostics == null ? List.of() : diagnostics);
    }
}
