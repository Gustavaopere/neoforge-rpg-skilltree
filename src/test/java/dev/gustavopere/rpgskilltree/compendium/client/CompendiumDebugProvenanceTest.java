package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumFact;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumProvenance;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumSection;
import dev.gustavopere.rpgskilltree.compendium.api.DiscoveryPolicy;
import dev.gustavopere.rpgskilltree.compendium.api.FactConfidence;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.api.FactVisibility;
import dev.gustavopere.rpgskilltree.compendium.api.VisibilityPolicy;
import dev.gustavopere.rpgskilltree.compendium.catalog.CoverageState;
import dev.gustavopere.rpgskilltree.compendium.provider.ProviderContribution;
import dev.gustavopere.rpgskilltree.compendium.provider.ProviderMerger;
import java.util.List;
import java.util.Map;
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
        advancedDebugPresentationUsesWinningFactProvenance();
        System.out.println("CompendiumDebugProvenanceTest: PASS");
    }

    private static void pageProjectsCanonicalDebugProvenance() {
        CompendiumPageModel page = page();
        CompendiumDebugInfo debug = page.debugInfo();

        eq("examplemod:forest_stag", debug.resourceLocation());
        eq("examplemod", debug.sourceModId());
        eq(FactSource.REGISTRY, debug.factSource());
        eq("registry:entity_type", debug.providerId());
        eq(CoverageState.ADAPTER, debug.coverageState());
    }

    private static void normalPresentationDoesNotExposeDebugFields() {
        eq(List.of(), CompendiumDebugPanelModel.fields(page(), false));
    }

    private static void advancedDebugPresentationUsesWinningFactProvenance() {
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
            "screen.rpgskilltree.compendium.debug.coverage",
            "ADAPTER"
        ), fields.get(2));
        eq(new CompendiumDebugField(
            "screen.rpgskilltree.compendium.debug.fact_source",
            "stats/base_health = RUNTIME"
        ), fields.get(3));
        eq(new CompendiumDebugField(
            "screen.rpgskilltree.compendium.debug.provider",
            "stats/base_health = runtime:scaled_stats"
        ), fields.get(4));
    }

    private static CompendiumPageModel page() {
        CompendiumEntry baseEntry = new CompendiumEntry(
            ID,
            "examplemod",
            "entity.examplemod.forest_stag",
            Set.of("fauna"),
            List.of(new CompendiumSection("stats", List.of(new CompendiumFact<>(
                "base_health",
                20.0D,
                "hp",
                FactSource.REGISTRY,
                FactConfidence.EXACT,
                FactVisibility.ALWAYS,
                null
            )))),
            List.of(),
            DiscoveryPolicy.OBSERVATION,
            VisibilityPolicy.VISIBLE,
            new CompendiumProvenance(FactSource.REGISTRY, "registry:entity_type"),
            1
        );
        ProviderContribution runtime = new ProviderContribution(
            "runtime:scaled_stats",
            100,
            Map.of("stats", List.of(new CompendiumFact<>(
                "base_health",
                30.0D,
                "hp",
                FactSource.RUNTIME,
                FactConfidence.EXACT,
                FactVisibility.ALWAYS,
                null
            ))),
            Set.of(),
            List.of()
        );
        CompendiumEntry entry = ProviderMerger.merge(baseEntry, List.of(runtime)).entry();
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
