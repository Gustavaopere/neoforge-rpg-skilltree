package dev.gustavopere.rpgskilltree.compendium.provider.loot;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumFact;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelation;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumSection;
import dev.gustavopere.rpgskilltree.compendium.api.FactConfidence;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.api.FactVisibility;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
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
        ArrayList<CompendiumRelation> orderedRelations = new ArrayList<>(merged);
        orderedRelations.sort(RELATION_ORDER);

        ArrayList<CompendiumSection> sections = new ArrayList<>();
        baseEntry.sections().stream()
            .filter(section -> !section.sectionId().equals("loot"))
            .forEach(sections::add);
        if (!summary.entries().isEmpty()) sections.add(buildLootSection(summary));

        return baseEntry
            .withSections(sections)
            .withCategoriesAndRelations(baseEntry.categoryIds(), orderedRelations);
    }

    private static CompendiumSection buildLootSection(LootSummary summary) {
        ArrayList<LootEntrySummary> entries = new ArrayList<>(summary.entries());
        entries.sort(Comparator.comparing(LootEntrySummary::itemId));
        ArrayList<CompendiumFact<?>> facts = new ArrayList<>();
        for (int index = 0; index < entries.size(); index++) {
            LootEntrySummary entry = entries.get(index);
            String prefix = "drop." + index + ".";
            facts.add(exactFact(prefix + "item", entry.itemId(), null));
            addResolvedNumberFacts(facts, prefix + "count", entry.count(), null);
            addResolvedNumberFacts(facts, prefix + "chance", entry.chance(), "ratio");
            if (entry.count().resolution() != LootResolution.EXACT
                || entry.chance().resolution() != LootResolution.EXACT
                || !entry.conditions().isEmpty()) {
                List<String> context = entry.conditions().stream()
                    .map(condition -> condition.detail() == null ? condition.kind() : condition.kind() + ":" + condition.detail())
                    .sorted()
                    .toList();
                facts.add(new CompendiumFact<>(
                    prefix + "context",
                    context.isEmpty() ? "conditional" : context,
                    null,
                    FactSource.LOOT_TABLE,
                    FactConfidence.CONTEXTUAL,
                    FactVisibility.DISCOVERED_ONLY,
                    null
                ));
            }
        }
        return new CompendiumSection("loot", facts);
    }

    private static void addResolvedNumberFacts(
        List<CompendiumFact<?>> facts,
        String prefix,
        LootNumberSummary number,
        String unit
    ) {
        if (number.resolution() != LootResolution.EXACT) return;
        facts.add(exactFact(prefix + "_min", number.min(), unit));
        facts.add(exactFact(prefix + "_max", number.max(), unit));
    }

    private static <T> CompendiumFact<T> exactFact(String key, T value, String unit) {
        return new CompendiumFact<>(
            key,
            value,
            unit,
            FactSource.LOOT_TABLE,
            FactConfidence.EXACT,
            FactVisibility.DISCOVERED_ONLY,
            null
        );
    }
}
