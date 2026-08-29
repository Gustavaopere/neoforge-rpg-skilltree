package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumFact;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumSection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Pure presentation policy: administrative provenance stays absent unless debug mode is explicitly enabled. */
public final class CompendiumDebugPanelModel {
    private CompendiumDebugPanelModel() {}

    public static List<CompendiumDebugField> fields(CompendiumPageModel page, boolean debugEnabled) {
        Objects.requireNonNull(page, "page");
        if (!debugEnabled) return List.of();

        CompendiumDebugInfo debug = page.debugInfo();
        ArrayList<CompendiumDebugField> fields = new ArrayList<>();
        fields.add(new CompendiumDebugField(
            "screen.rpgskilltree.compendium.debug.resource_location",
            debug.resourceLocation()
        ));
        fields.add(new CompendiumDebugField(
            "screen.rpgskilltree.compendium.debug.source_mod",
            debug.sourceModId()
        ));
        fields.add(new CompendiumDebugField(
            "screen.rpgskilltree.compendium.debug.coverage",
            debug.coverageState().name()
        ));

        for (CompendiumSection section : page.sections()) {
            for (CompendiumFact<?> fact : section.facts()) {
                if (!fact.isConfirmed()) continue;
                String factPath = section.sectionId() + "/" + fact.factKey();
                String providerId = fact.providerId() == null ? debug.providerId() : fact.providerId();
                fields.add(new CompendiumDebugField(
                    "screen.rpgskilltree.compendium.debug.fact_source",
                    factPath + " = " + fact.source().name()
                ));
                fields.add(new CompendiumDebugField(
                    "screen.rpgskilltree.compendium.debug.provider",
                    factPath + " = " + providerId
                ));
            }
        }
        return List.copyOf(fields);
    }
}
