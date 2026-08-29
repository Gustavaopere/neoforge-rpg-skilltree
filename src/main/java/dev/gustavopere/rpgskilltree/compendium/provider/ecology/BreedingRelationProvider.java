package dev.gustavopere.rpgskilltree.compendium.provider.ecology;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelation;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationTarget;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationType;
import dev.gustavopere.rpgskilltree.compendium.api.FactConfidence;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class BreedingRelationProvider {
    private BreedingRelationProvider() {}

    public static List<CompendiumRelation> relations(
        List<CompendiumRelationTarget> targets,
        FactSource source,
        FactConfidence confidence,
        String evidenceId
    ) {
        ArrayList<CompendiumRelation> relations = new ArrayList<>();
        for (CompendiumRelationTarget target : targets == null ? List.<CompendiumRelationTarget>of() : targets) {
            relations.add(new CompendiumRelation(CompendiumRelationType.BREEDS_WITH_ITEM, target, source, confidence, evidenceId));
        }
        relations.sort(Comparator.comparing(relation -> relation.target().serializedTarget()));
        return List.copyOf(relations);
    }

    public record Profile(boolean canBreed, boolean requiresAdult, int cooldownTicks, String offspringEntryId) {
        public Profile {
            if (cooldownTicks < -1) throw new IllegalArgumentException("cooldownTicks must be -1 or non-negative");
            if (offspringEntryId != null) {
                offspringEntryId = offspringEntryId.trim();
                if (offspringEntryId.isEmpty()) offspringEntryId = null;
                else CompendiumEntryId.parse(offspringEntryId);
            }
        }
    }
}
