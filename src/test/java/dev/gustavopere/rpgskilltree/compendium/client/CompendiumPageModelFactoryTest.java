package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumFact;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumProvenance;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelation;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationTarget;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationType;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumSection;
import dev.gustavopere.rpgskilltree.compendium.api.DiscoveryPolicy;
import dev.gustavopere.rpgskilltree.compendium.api.FactConfidence;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.api.FactVisibility;
import dev.gustavopere.rpgskilltree.compendium.api.VisibilityPolicy;
import dev.gustavopere.rpgskilltree.compendium.catalog.CoverageState;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialBlock;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialContent;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialSection;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialSource;
import dev.gustavopere.rpgskilltree.compendium.editorial.EditorialAvailability;
import dev.gustavopere.rpgskilltree.compendium.editorial.EditorialReviewStatus;
import dev.gustavopere.rpgskilltree.compendium.editorial.EditorialSourceType;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class CompendiumPageModelFactoryTest {
    private static final CompendiumEntryId WOLF_ID = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:wolf");
    private static final CompendiumEntryId TAIGA_ID = CompendiumEntryId.of(CompendiumEntryKind.BIOME, "minecraft:taiga");
    private static final CompendiumEntryId FOX_ID = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:fox");

    public static void main(String[] args) {
        hiddenEntryRequiresDiscoveryOrAdmin();
        hiddenDetailsKeepShellUntilDiscovery();
        factVisibilityAndUnavailableFactsAreApplied();
        onlyEntryRelationsBecomePageNavigation();
        mismatchedClientIdentityIsRejected();
        editorialOverlayFollowsVisibilityAndAuthorization();
        editorialIdentityMismatchIsRejected();
        legacyFactoryKeepsEditorialEmpty();
        System.out.println("CompendiumPageModelFactoryTest: PASS");
    }

    private static void hiddenEntryRequiresDiscoveryOrAdmin() {
        CompendiumEntry entry = entry(VisibilityPolicy.HIDE_ENTRY_UNTIL_DISCOVERED, List.of(), List.of());
        Optional<CompendiumPageModel> hidden = CompendiumPageModelFactory.create(entry, client(false), false);
        eq(Optional.empty(), hidden);

        CompendiumPageModel discovered = CompendiumPageModelFactory.create(entry, client(true), false).orElseThrow();
        isTrue(discovered.discovered());
        isTrue(discovered.detailsVisible());

        CompendiumPageModel admin = CompendiumPageModelFactory.create(entry, client(false), true).orElseThrow();
        isTrue(admin.detailsVisible());
    }

    private static void hiddenDetailsKeepShellUntilDiscovery() {
        CompendiumFact<Integer> publicFact = fact("health", 20, FactVisibility.PUBLIC, FactConfidence.EXACT);
        CompendiumEntry entry = entry(
            VisibilityPolicy.HIDE_DETAILS_UNTIL_DISCOVERED,
            List.of(new CompendiumSection("overview", List.of(publicFact))),
            List.of(new CompendiumRelation(
                CompendiumRelationType.SPAWNS_IN,
                TAIGA_ID,
                FactSource.REGISTRY,
                FactConfidence.EXACT
            ))
        );

        CompendiumPageModel hidden = CompendiumPageModelFactory.create(entry, client(false), false).orElseThrow();
        eq(WOLF_ID, hidden.id());
        eq("Lobo", hidden.displayName());
        eq("minecraft", hidden.sourceModId());
        isFalse(hidden.detailsVisible());
        eq(List.of(), hidden.sections());
        eq(List.of(), hidden.entryRelations());
    }

    private static void factVisibilityAndUnavailableFactsAreApplied() {
        CompendiumFact<Integer> publicFact = fact("health", 20, FactVisibility.PUBLIC, FactConfidence.EXACT);
        CompendiumFact<String> discoveredFact = fact("diet", "meat", FactVisibility.DISCOVERED_ONLY, FactConfidence.DERIVED);
        CompendiumFact<String> adminFact = fact("debug", "registry-backed", FactVisibility.ADMIN_ONLY, FactConfidence.EXACT);
        CompendiumFact<Object> unavailable = CompendiumFact.unavailable("unknown", FactSource.UNKNOWN, FactVisibility.PUBLIC);
        CompendiumEntry entry = entry(
            VisibilityPolicy.VISIBLE,
            List.of(
                new CompendiumSection("overview", List.of(publicFact, discoveredFact, adminFact, unavailable)),
                new CompendiumSection("empty_after_filter", List.of(unavailable))
            ),
            List.of()
        );

        CompendiumPageModel publicPage = CompendiumPageModelFactory.create(entry, client(false), false).orElseThrow();
        isTrue(publicPage.detailsVisible());
        eq(1, publicPage.sections().size());
        eq(List.of(publicFact), publicPage.sections().getFirst().facts());

        CompendiumPageModel discoveredPage = CompendiumPageModelFactory.create(entry, client(true), false).orElseThrow();
        eq(List.of(publicFact, discoveredFact), discoveredPage.sections().getFirst().facts());

        CompendiumPageModel adminPage = CompendiumPageModelFactory.create(entry, client(false), true).orElseThrow();
        eq(List.of(publicFact, discoveredFact, adminFact), adminPage.sections().getFirst().facts());
    }

    private static void onlyEntryRelationsBecomePageNavigation() {
        CompendiumRelation entryRelation = new CompendiumRelation(
            CompendiumRelationType.SPAWNS_IN,
            TAIGA_ID,
            FactSource.REGISTRY,
            FactConfidence.EXACT
        );
        CompendiumRelation itemRelation = new CompendiumRelation(
            CompendiumRelationType.EATS,
            CompendiumRelationTarget.item("minecraft:beef"),
            FactSource.ADAPTER,
            FactConfidence.DERIVED
        );
        CompendiumEntry entry = entry(VisibilityPolicy.VISIBLE, List.of(), List.of(itemRelation, entryRelation));

        CompendiumPageModel page = CompendiumPageModelFactory.create(entry, client(true), false).orElseThrow();
        eq(List.of(entryRelation), page.entryRelations());
        eq(TAIGA_ID, page.entryRelations().getFirst().target().entryId());
    }

    private static void mismatchedClientIdentityIsRejected() {
        CompendiumEntry entry = entry(VisibilityPolicy.VISIBLE, List.of(), List.of());
        CompendiumClientEntry wrong = new CompendiumClientEntry(
            FOX_ID,
            "Raposa", "minecraft", Set.of(), Set.of("fauna"), Set.of("minecraft:overworld"), Set.of("minecraft:taiga"),
            true, false, true, true, false, CoverageState.AUTO
        );
        throwsIllegal(() -> CompendiumPageModelFactory.create(entry, wrong, false));
    }

    private static void editorialOverlayFollowsVisibilityAndAuthorization() {
        CompendiumFact<Integer> health = fact("health", 20, FactVisibility.PUBLIC, FactConfidence.EXACT);
        CompendiumEntry visible = entry(
            VisibilityPolicy.VISIBLE,
            List.of(new CompendiumSection("overview", List.of(health))),
            List.of()
        );
        CompendiumEditorialContent editorial = editorial(WOLF_ID, List.of(TAIGA_ID, FOX_ID));

        CompendiumPageModel page = CompendiumPageModelFactory.create(
            visible,
            client(true),
            false,
            Optional.of(editorial),
            Set.of(WOLF_ID, TAIGA_ID)
        ).orElseThrow();

        CompendiumEditorialContent projected = page.editorialContent().orElseThrow();
        eq("Lobo cinzento", projected.title());
        eq(List.of(TAIGA_ID), projected.references());
        eq(List.of(new CompendiumSection("overview", List.of(health))), page.sections());

        CompendiumEntry hiddenDetails = entry(
            VisibilityPolicy.HIDE_DETAILS_UNTIL_DISCOVERED,
            List.of(new CompendiumSection("overview", List.of(health))),
            List.of()
        );
        CompendiumPageModel hidden = CompendiumPageModelFactory.create(
            hiddenDetails,
            client(false),
            false,
            Optional.of(editorial),
            Set.of(WOLF_ID, TAIGA_ID)
        ).orElseThrow();
        isFalse(hidden.detailsVisible());
        eq(Optional.empty(), hidden.editorialContent());
    }

    private static void editorialIdentityMismatchIsRejected() {
        CompendiumEntry entry = entry(VisibilityPolicy.VISIBLE, List.of(), List.of());
        CompendiumEditorialContent wrong = editorial(FOX_ID, List.of());
        throwsIllegal(() -> CompendiumPageModelFactory.create(
            entry,
            client(true),
            false,
            Optional.of(wrong),
            Set.of(WOLF_ID, FOX_ID)
        ));
    }

    private static void legacyFactoryKeepsEditorialEmpty() {
        CompendiumEntry entry = entry(VisibilityPolicy.VISIBLE, List.of(), List.of());
        CompendiumPageModel page = CompendiumPageModelFactory.create(entry, client(true), false).orElseThrow();
        eq(Optional.empty(), page.editorialContent());
    }

    private static CompendiumEditorialContent editorial(CompendiumEntryId id, List<CompendiumEntryId> references) {
        CompendiumEditorialSource source = new CompendiumEditorialSource(
            EditorialSourceType.RUNTIME,
            "minecraft:entity_type/minecraft:wolf",
            null
        );
        return new CompendiumEditorialContent(
            id,
            "Lobo cinzento",
            new CompendiumEditorialBlock("Canídeo registrado no catálogo.", List.of(source)),
            List.of(new CompendiumEditorialSection(
                "behavior",
                new CompendiumEditorialBlock("Comportamento confirmado.", List.of(source))
            )),
            references,
            EditorialReviewStatus.REVIEWED,
            EditorialAvailability.RUNTIME,
            null
        );
    }

    private static CompendiumEntry entry(
        VisibilityPolicy visibility,
        List<CompendiumSection> sections,
        List<CompendiumRelation> relations
    ) {
        return new CompendiumEntry(
            WOLF_ID,
            "minecraft",
            "entity.minecraft.wolf",
            Set.of("fauna"),
            sections,
            relations,
            DiscoveryPolicy.OBSERVATION,
            visibility,
            new CompendiumProvenance(FactSource.REGISTRY, "minecraft:entity_type"),
            1
        );
    }

    private static CompendiumClientEntry client(boolean discovered) {
        return new CompendiumClientEntry(
            WOLF_ID,
            "Lobo",
            "minecraft",
            Set.of("wolf"),
            Set.of("fauna"),
            Set.of("minecraft:overworld"),
            Set.of("minecraft:taiga"),
            discovered,
            false,
            true,
            true,
            false,
            CoverageState.AUTO
        );
    }

    private static <T> CompendiumFact<T> fact(
        String key,
        T value,
        FactVisibility visibility,
        FactConfidence confidence
    ) {
        return new CompendiumFact<>(key, value, null, FactSource.REGISTRY, confidence, visibility, null);
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }

    private static void isTrue(boolean value) {
        if (!value) throw new AssertionError("expected true");
    }

    private static void isFalse(boolean value) {
        if (value) throw new AssertionError("expected false");
    }

    private static void throwsIllegal(Runnable action) {
        try {
            action.run();
            throw new AssertionError("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
