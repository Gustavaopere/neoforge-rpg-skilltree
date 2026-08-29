package dev.gustavopere.rpgskilltree.compendium.ecology;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelation;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationTarget;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationType;
import dev.gustavopere.rpgskilltree.compendium.api.FactConfidence;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.provider.ecology.EcologyAdapterContribution;
import dev.gustavopere.rpgskilltree.compendium.provider.ecology.EcologyRelationProvider;
import java.util.List;
import java.util.Objects;

public final class EcologyRelationTest {
    public static void main(String[] args) {
        predatorRelationTargetsCanonicalEntry();
        curatedExactEcologyRequiresEvidence();
        optionalAdapterIsFailSoftWhenAbsent();
        optionalAdapterReturnsContributionWhenPresent();
        System.out.println("EcologyRelationTest: PASS");
    }

    private static void predatorRelationTargetsCanonicalEntry() {
        CompendiumRelation relation = EcologyRelationProvider.relation(
            CompendiumRelationType.PREDATOR_OF,
            CompendiumRelationTarget.entry(CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:rabbit")),
            FactSource.ADAPTER,
            FactConfidence.EXACT,
            "minecraft:wolf_target_goal"
        );
        eq(CompendiumRelationType.PREDATOR_OF, relation.type());
        eq("ENTRY|ENTITY|minecraft:rabbit", relation.target().serializedTarget());
    }

    private static void curatedExactEcologyRequiresEvidence() {
        try {
            EcologyRelationProvider.relation(
                CompendiumRelationType.RELATED_ENTRY,
                CompendiumRelationTarget.entry(CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:bee")),
                FactSource.CURATED_EDITORIAL,
                FactConfidence.EXACT,
                null
            );
            throw new AssertionError("expected curated evidence failure");
        } catch (IllegalArgumentException expected) {
            truth(expected.getMessage().contains("evidence"));
        }
    }

    private static void optionalAdapterIsFailSoftWhenAbsent() {
        truth(EcologyAdapterContribution.optional(false, "animal_husbandry", List.of()).isEmpty());
    }

    private static void optionalAdapterReturnsContributionWhenPresent() {
        CompendiumRelation relation = EcologyRelationProvider.relation(
            CompendiumRelationType.RELATED_ENTRY,
            CompendiumRelationTarget.block("minecraft:beehive"),
            FactSource.ADAPTER,
            FactConfidence.EXACT,
            "example:stable_api"
        );
        EcologyAdapterContribution contribution = EcologyAdapterContribution.optional(
            true,
            "animal_husbandry",
            List.of(relation)
        ).orElseThrow();
        eq("animal_husbandry", contribution.adapterId());
        eq(List.of(relation), contribution.relations());
    }

    private static void truth(boolean value) { if (!value) throw new AssertionError("expected true"); }
    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
