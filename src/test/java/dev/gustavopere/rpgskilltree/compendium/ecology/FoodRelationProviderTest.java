package dev.gustavopere.rpgskilltree.compendium.ecology;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelation;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationTarget;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationType;
import dev.gustavopere.rpgskilltree.compendium.api.FactConfidence;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.provider.ecology.FoodRelationProvider;
import dev.gustavopere.rpgskilltree.compendium.provider.ecology.TemptationRelationProvider;
import java.util.List;
import java.util.Objects;

public final class FoodRelationProviderTest {
    public static void main(String[] args) {
        foodDoesNotImplyTemptationOrBreeding();
        temptationRemainsExplicit();
        System.out.println("FoodRelationProviderTest: PASS");
    }

    private static void foodDoesNotImplyTemptationOrBreeding() {
        List<CompendiumRelation> relations = FoodRelationProvider.relations(
            List.of(CompendiumRelationTarget.item("minecraft:wheat")),
            FactSource.REGISTRY,
            FactConfidence.EXACT,
            "minecraft:animal.isFood"
        );
        eq(1, relations.size());
        eq(CompendiumRelationType.EATS, relations.getFirst().type());
        truth(relations.stream().noneMatch(relation -> relation.type() == CompendiumRelationType.ATTRACTED_BY));
        truth(relations.stream().noneMatch(relation -> relation.type() == CompendiumRelationType.BREEDS_WITH_ITEM));
    }

    private static void temptationRemainsExplicit() {
        CompendiumRelation relation = TemptationRelationProvider.relations(
            List.of(CompendiumRelationTarget.itemTag("minecraft:cow_food")),
            FactSource.ADAPTER,
            FactConfidence.EXACT,
            "example:tempt_goal"
        ).getFirst();
        eq(CompendiumRelationType.ATTRACTED_BY, relation.type());
        eq("ITEM_TAG|minecraft:cow_food", relation.target().serializedTarget());
    }

    private static void truth(boolean value) { if (!value) throw new AssertionError("expected true"); }
    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
