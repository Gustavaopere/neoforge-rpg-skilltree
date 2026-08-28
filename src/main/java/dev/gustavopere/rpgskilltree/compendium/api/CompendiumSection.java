package dev.gustavopere.rpgskilltree.compendium.api;

import java.util.HashSet;
import java.util.List;

public record CompendiumSection(String sectionId, List<CompendiumFact<?>> facts) {
    public CompendiumSection {
        if (sectionId == null || sectionId.trim().isEmpty()) {
            throw new IllegalArgumentException("sectionId must not be blank");
        }
        sectionId = sectionId.trim();
        facts = List.copyOf(facts == null ? List.of() : facts);
        HashSet<String> keys = new HashSet<>();
        for (CompendiumFact<?> fact : facts) {
            if (fact == null) throw new IllegalArgumentException("section fact must not be null");
            if (!keys.add(fact.factKey())) {
                throw new IllegalArgumentException("duplicate fact key in section " + sectionId + ": " + fact.factKey());
            }
        }
    }
}
