package dev.gustavopere.rpgskilltree.compendium.provider;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumFact;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelation;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public record ProviderContribution(
    String providerId,
    int priority,
    Map<String, List<CompendiumFact<?>>> sectionFacts,
    Set<String> categories,
    List<CompendiumRelation> relations
) {
    public ProviderContribution {
        if (providerId == null || providerId.trim().isEmpty()) {
            throw new IllegalArgumentException("providerId must not be blank");
        }
        providerId = providerId.trim();
        LinkedHashMap<String, List<CompendiumFact<?>>> copiedFacts = new LinkedHashMap<>();
        if (sectionFacts != null) {
            sectionFacts.forEach((sectionId, facts) -> {
                if (sectionId == null || sectionId.trim().isEmpty()) {
                    throw new IllegalArgumentException("provider section id must not be blank");
                }
                copiedFacts.put(sectionId.trim(), List.copyOf(facts == null ? List.of() : facts));
            });
        }
        sectionFacts = Map.copyOf(copiedFacts);
        categories = Set.copyOf(categories == null ? Set.of() : categories);
        relations = List.copyOf(relations == null ? List.of() : relations);
    }

    public static ProviderContribution empty(String providerId, int priority) {
        return new ProviderContribution(providerId, priority, Map.of(), Set.of(), List.of());
    }
}
