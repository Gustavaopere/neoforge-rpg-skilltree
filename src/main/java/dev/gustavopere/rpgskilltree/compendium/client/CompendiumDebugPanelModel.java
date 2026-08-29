package dev.gustavopere.rpgskilltree.compendium.client;

import java.util.List;
import java.util.Objects;

/** Pure presentation policy: administrative provenance stays absent unless debug mode is explicitly enabled. */
public final class CompendiumDebugPanelModel {
    private CompendiumDebugPanelModel() {}

    public static List<CompendiumDebugField> fields(CompendiumPageModel page, boolean debugEnabled) {
        Objects.requireNonNull(page, "page");
        if (!debugEnabled) return List.of();

        CompendiumDebugInfo debug = page.debugInfo();
        return List.of(
            new CompendiumDebugField(
                "screen.rpgskilltree.compendium.debug.resource_location",
                debug.resourceLocation()
            ),
            new CompendiumDebugField(
                "screen.rpgskilltree.compendium.debug.source_mod",
                debug.sourceModId()
            ),
            new CompendiumDebugField(
                "screen.rpgskilltree.compendium.debug.fact_source",
                debug.factSource().name()
            ),
            new CompendiumDebugField(
                "screen.rpgskilltree.compendium.debug.provider",
                debug.providerId()
            ),
            new CompendiumDebugField(
                "screen.rpgskilltree.compendium.debug.coverage",
                debug.coverageState().name()
            )
        );
    }
}
