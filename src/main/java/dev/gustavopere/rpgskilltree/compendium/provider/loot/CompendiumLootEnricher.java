package dev.gustavopere.rpgskilltree.compendium.provider.loot;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Objects;

public final class CompendiumLootEnricher {
    private static final Comparator<CompendiumRelation> RELATION_ORDER = Comparator
        .comparing((CompendiumRelation relation) -> relation.type().name())
        .thenComparing(relation -> relation.target().serializedTarget())
        .thenComparing(relation -> relation.source().name())
        .thenComparing(relation -> relation.confidence().name())
        .thenComparing(relation -> relation.evidenceId() == null ? "" : relation.evidenceId());

    private CompendiumLootEnricher() {}

    public static CompendiumEntry enrich(CompendiumEntry baseEntry, CompendiumLootSnapshot snapshot) {
        Objects.requireNonNull(baseEntry, "baseEntry");
        Objects.requireNonNull(snapshot, "snapshot");
        if (baseEntry.id().kind() != CompendiumEntryKind.ENTITY) return baseEntry;

        String tableId = baseEntry.id().namespace() + ":entities/" + baseEntry.id().path();
        LootSummary summary = snapshot.find(tableId).orElse(null);
        if (summary == null) return baseEntry;

        LinkedHashSet<CompendiumRelation> merged = new LinkedHashSet<>(baseEntry.relations());
        merged.addAll(CompendiumLootProvider.relations(summary));
        ArrayList<CompendiumRelation> ordered = new ArrayList<>(merged);
        ordered.sort(RELATION_ORDER);
        return baseEntry.withCategoriesAndRelations(baseEntry.categoryIds(), ordered);
    }
}
