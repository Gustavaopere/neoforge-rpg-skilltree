package dev.gustavopere.rpgskilltree.compendium.ecology;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelation;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationTarget;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationType;
import dev.gustavopere.rpgskilltree.compendium.api.FactConfidence;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.provider.ecology.BreedingRelationProvider;
import dev.gustavopere.rpgskilltree.compendium.provider.ecology.TamingFacts;
import java.util.List;
import java.util.Objects;

public final class BreedingProviderTest {
    public static void main(String[] args) {
        breedingTargetsItemTagsExplicitly();
        breedingProfileCarriesAdultAndCooldownContext();
        tamingCapabilityIsSeparateFromInstanceOwner();
        System.out.println("BreedingProviderTest: PASS");
    }

    private static void breedingTargetsItemTagsExplicitly() {
        CompendiumRelation relation = BreedingRelationProvider.relations(
            List.of(CompendiumRelationTarget.itemTag("minecraft:cow_food")),
            FactSource.ADAPTER,
            FactConfidence.EXACT,
            "example:breeding_predicate"
        ).getFirst();
        eq(CompendiumRelationType.BREEDS_WITH_ITEM, relation.type());
        eq("ITEM_TAG|minecraft:cow_food", relation.target().serializedTarget());
    }

    private static void breedingProfileCarriesAdultAndCooldownContext() {
        BreedingRelationProvider.Profile profile = new BreedingRelationProvider.Profile(
            true,
            true,
            6000,
            "ENTITY|minecraft:cow"
        );
        truth(profile.canBreed());
        truth(profile.requiresAdult());
        eq(6000, profile.cooldownTicks());
        eq("ENTITY|minecraft:cow", profile.offspringEntryId());
    }

    private static void tamingCapabilityIsSeparateFromInstanceOwner() {
        TamingFacts capability = TamingFacts.species(
            true,
            List.of(CompendiumRelationTarget.item("minecraft:bone"))
        );
        eq(Boolean.TRUE, capability.tameable());
        eq(null, capability.tamed());
        eq(null, capability.ownerId());

        TamingFacts instance = TamingFacts.instance(true, "player-uuid");
        eq(null, instance.tameable());
        eq(Boolean.TRUE, instance.tamed());
        eq("player-uuid", instance.ownerId());
    }

    private static void truth(boolean value) { if (!value) throw new AssertionError("expected true"); }
    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
