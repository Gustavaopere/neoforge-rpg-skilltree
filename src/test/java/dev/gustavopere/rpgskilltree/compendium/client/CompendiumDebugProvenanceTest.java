package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumProvenance;
import dev.gustavopere.rpgskilltree.compendium.api.DiscoveryPolicy;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.api.VisibilityPolicy;
import dev.gustavopere.rpgskilltree.compendium.catalog.CoverageState;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class CompendiumDebugProvenanceTest {
    private static final CompendiumEntryId ID = CompendiumEntryId.of(
        CompendiumEntryKind.ENTITY,
        "examplemod:forest_stag"
    );

    public static void main(String[] args) {
        pageProjectsCanonicalDebugProvenance();
        normalPresentationDoesNotExposeDebugFields();
        advancedDebugPresentationExposesFiveCanonicalFields();
        System.out.println("CompendiumDebugProvenanceTest: PASS");
    }

    private static void pageProjectsCanonicalDebugProvenance() {
        CompendiumPageModel page = page();
        CompendiumDebugInfo debug = page.debugInfo();

        eq("examplemod:forest_stag", debug.resourceLocation());
        eq("examplemod", debug.sourceModId());
        eq(FactSource.ADAPTER, debug.factSource());
        eq("adapter:examplemod_entities", debug.providerId());
        eq(CoverageState.ADAPTER, debug.coverageState());
    }

    private static void normalPresentationDoesNotExposeDebugFields() {
        eq(List.of(), CompendiumDebugPanelModel.fields(page(), false));
    }

    private static void advancedDebugPresentationExposesFiveCanonicalFields() {
        List<CompendiumDebugField> fields = CompendiumDebugPanelModel.fields(page(), true);
        eq(5, fields.size());
        eq(new CompendiumDebugField(
            "screen.rpgskilltree.compendium.debug.resource_location",
            "examplemod:forest_stag"
        ), fields.get(0));
        eq(new CompendiumDebugField(
            "screen.rpgskilltree.compendium.debug.source_mod",
            "examplemod"
        ), fields.get(1));
        eq(new CompendiumDebugField(
            "screen.rpgskilltree.compendium.debug.fact_source",
            "ADAPTER"
        ), fields.get(2));
        eq(new CompendiumDebugField(
            "screen.rpgskilltree.compendium.debug.provider",
            "adapter:examplemod_entities"
        ), fields.get(3));
        eq(new CompendiumDebugField(
            "screen.rpgskilltree.compendium.debug.coverage",
            "ADAPTER"
        ), fields.get(4));
    }

    private static CompendiumPageModel page() {
        CompendiumEntry entry = new CompendiumEntry(
            ID,
            "examplemod",
            "entity.examplemod.forest_stag",
            Set.of("fauna"),
            List.of(),
            List.of(),
            DiscoveryPolicy.OBSERVATION,
            VisibilityPolicy.VISIBLE,
            new CompendiumProvenance(FactSource.ADAPTER, "adapter:examplemod_entities"),
            1
        );
        CompendiumClientEntry clientEntry = new CompendiumClientEntry(
            ID,
            "Cervo da Floresta",
            "examplemod",
            Set.of("cervo"),
            Set.of("fauna"),
            Set.of("minecraft:overworld"),
            Set.of("examplemod:forest"),
            true,
            false,
            false,
            true,
            false,
            CoverageState.ADAPTER
        );
        return CompendiumPageModelFactory.create(entry, clientEntry, false).orElseThrow();
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
