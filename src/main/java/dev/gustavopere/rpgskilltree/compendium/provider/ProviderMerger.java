package dev.gustavopere.rpgskilltree.compendium.provider;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumFact;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelation;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumSection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public final class ProviderMerger {
    private static final String BASE_PROVIDER = "__base__";
    private static final Comparator<ProviderContribution> CONTRIBUTION_ORDER = Comparator
        .comparingInt(ProviderContribution::priority).reversed()
        .thenComparing(ProviderContribution::providerId);
    private static final Comparator<CompendiumRelation> RELATION_ORDER = Comparator
        .comparing((CompendiumRelation relation) -> relation.type().name())
        .thenComparing(relation -> relation.target().serializedId())
        .thenComparing(relation -> relation.source().name())
        .thenComparing(relation -> relation.confidence().name());

    private ProviderMerger() {}

    public static ProviderResult merge(CompendiumEntry baseEntry, List<ProviderContribution> contributions) {
        Objects.requireNonNull(baseEntry, "baseEntry");
        List<ProviderContribution> ordered = new ArrayList<>(contributions == null ? List.of() : contributions);
        LinkedHashSet<String> providerIds = new LinkedHashSet<>();
        for (ProviderContribution contribution : ordered) {
            Objects.requireNonNull(contribution, "provider contribution");
            if (!providerIds.add(contribution.providerId())) {
                throw new IllegalArgumentException("duplicate compendium provider id: " + contribution.providerId());
            }
        }
        ordered.sort(CONTRIBUTION_ORDER);

        TreeMap<String, TreeMap<String, Candidate>> selectedFacts = new TreeMap<>();
        for (CompendiumSection section : baseEntry.sections()) {
            TreeMap<String, Candidate> sectionFacts = selectedFacts.computeIfAbsent(section.sectionId(), ignored -> new TreeMap<>());
            for (CompendiumFact<?> fact : section.facts()) {
                sectionFacts.put(fact.factKey(), new Candidate(BASE_PROVIDER, Integer.MIN_VALUE, fact));
            }
        }

        TreeSet<String> categories = new TreeSet<>(baseEntry.categoryIds());
        LinkedHashSet<CompendiumRelation> relations = new LinkedHashSet<>(baseEntry.relations());
        ArrayList<ProviderDiagnostic> diagnostics = new ArrayList<>();
        Set<String> diagnosticKeys = new LinkedHashSet<>();

        for (ProviderContribution contribution : ordered) {
            categories.addAll(contribution.categories());
            relations.addAll(contribution.relations());
            contribution.sectionFacts().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(sectionEntry -> {
                    TreeMap<String, Candidate> sectionFacts = selectedFacts.computeIfAbsent(
                        sectionEntry.getKey(), ignored -> new TreeMap<>()
                    );
                    sectionEntry.getValue().stream()
                        .sorted(Comparator.comparing(CompendiumFact::factKey))
                        .forEach(fact -> mergeFact(sectionEntry.getKey(), fact, contribution, sectionFacts, diagnostics, diagnosticKeys));
                });
        }

        ArrayList<CompendiumSection> sections = new ArrayList<>();
        selectedFacts.forEach((sectionId, facts) -> {
            ArrayList<CompendiumFact<?>> values = new ArrayList<>();
            facts.values().forEach(candidate -> values.add(candidate.fact()));
            sections.add(new CompendiumSection(sectionId, values));
        });

        ArrayList<CompendiumRelation> orderedRelations = new ArrayList<>(relations);
        orderedRelations.sort(RELATION_ORDER);
        diagnostics.sort(Comparator.comparing(ProviderDiagnostic::message));

        CompendiumEntry merged = new CompendiumEntry(
            baseEntry.id(),
            baseEntry.sourceModId(),
            baseEntry.translationKey(),
            categories,
            sections,
            orderedRelations,
            baseEntry.discoveryPolicy(),
            baseEntry.visibilityPolicy(),
            baseEntry.provenance(),
            baseEntry.contentVersion()
        );
        return new ProviderResult(merged, diagnostics);
    }

    public static ProviderResult mergeProviders(
        CompendiumEntry baseEntry,
        ProviderContext context,
        List<CompendiumProvider> providers
    ) {
        Objects.requireNonNull(context, "context");
        ArrayList<ProviderContribution> contributions = new ArrayList<>();
        for (CompendiumProvider provider : providers == null ? List.<CompendiumProvider>of() : providers) {
            Objects.requireNonNull(provider, "provider");
            ProviderContribution contribution = Objects.requireNonNull(
                provider.contribute(context, baseEntry),
                "provider contribution"
            );
            if (!provider.providerId().equals(contribution.providerId()) || provider.priority() != contribution.priority()) {
                throw new IllegalArgumentException("provider contribution identity/priority mismatch: " + provider.providerId());
            }
            contributions.add(contribution);
        }
        return merge(baseEntry, contributions);
    }

    private static void mergeFact(
        String sectionId,
        CompendiumFact<?> fact,
        ProviderContribution contribution,
        Map<String, Candidate> sectionFacts,
        List<ProviderDiagnostic> diagnostics,
        Set<String> diagnosticKeys
    ) {
        Candidate candidate = new Candidate(contribution.providerId(), contribution.priority(), fact);
        Candidate current = sectionFacts.get(fact.factKey());
        if (current == null || candidate.priority() > current.priority()) {
            sectionFacts.put(fact.factKey(), candidate);
            return;
        }
        if (candidate.priority() < current.priority() || Objects.equals(candidate.fact(), current.fact())) return;

        Candidate winner;
        Candidate loser;
        if (candidate.providerId().compareTo(current.providerId()) < 0) {
            winner = candidate;
            loser = current;
            sectionFacts.put(fact.factKey(), candidate);
        } else {
            winner = current;
            loser = candidate;
        }

        String diagnosticKey = sectionId + "\u0000" + fact.factKey() + "\u0000" + winner.priority()
            + "\u0000" + winner.providerId() + "\u0000" + loser.providerId();
        if (diagnosticKeys.add(diagnosticKey)) {
            diagnostics.add(new ProviderDiagnostic(
                "FACT_CONFLICT",
                "conflicting fact " + sectionId + "/" + fact.factKey() + " at priority " + winner.priority()
                    + "; selected " + winner.providerId() + " over " + loser.providerId(),
                List.of(winner.providerId(), loser.providerId())
            ));
        }
    }

    private record Candidate(String providerId, int priority, CompendiumFact<?> fact) {}
}
