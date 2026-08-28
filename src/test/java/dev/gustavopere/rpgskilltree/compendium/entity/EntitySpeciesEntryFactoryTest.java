package dev.gustavopere.rpgskilltree.compendium.entity;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumFact;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumSection;
import dev.gustavopere.rpgskilltree.compendium.api.FactConfidence;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class EntitySpeciesEntryFactoryTest {
    public static void main(String[] args) {
        createsCanonicalEntityEntry();
        omitsUnknownAttributesInsteadOfInventingZeroes();
        separatesBaseStatsFromDimensions();
        System.out.println("EntitySpeciesEntryFactoryTest: PASS");
    }

    private static void createsCanonicalEntityEntry() {
        EntitySpeciesFacts facts = sampleFacts(Map.of(
            EntityFactKeys.MAX_HEALTH, 20.0,
            EntityFactKeys.ARMOR, 2.0
        ));
        CompendiumEntry entry = EntitySpeciesEntryFactory.create(facts);

        eq(CompendiumEntryKind.ENTITY, entry.id().kind());
        eq("minecraft:zombie", entry.id().resourceLocation());
        eq("minecraft", entry.sourceModId());
        eq("entity.minecraft.zombie", entry.translationKey());
        check(entry.categoryIds().contains("hostil"), "hostile category missing");
        check(entry.categoryIds().contains("morto_vivo"), "undead category missing");
    }

    private static void omitsUnknownAttributesInsteadOfInventingZeroes() {
        CompendiumEntry entry = EntitySpeciesEntryFactory.create(sampleFacts(Map.of(
            EntityFactKeys.MAX_HEALTH, 20.0
        )));
        Map<String, CompendiumFact<?>> facts = facts(entry, "base_stats");

        eq(20.0, facts.get(EntityFactKeys.MAX_HEALTH).value());
        check(!facts.containsKey(EntityFactKeys.ATTACK_DAMAGE), "missing attack damage must not become zero");
        check(!facts.containsKey(EntityFactKeys.ARMOR), "missing armor must not become zero");
        eq(FactSource.REGISTRY, facts.get(EntityFactKeys.MAX_HEALTH).source());
        eq(FactConfidence.EXACT, facts.get(EntityFactKeys.MAX_HEALTH).confidence());
    }

    private static void separatesBaseStatsFromDimensions() {
        CompendiumEntry entry = EntitySpeciesEntryFactory.create(sampleFacts(Map.of(
            EntityFactKeys.MAX_HEALTH, 20.0
        )));
        Map<String, CompendiumFact<?>> dimensions = facts(entry, "dimensions");
        Map<String, CompendiumFact<?>> base = facts(entry, "base_stats");

        eq(0.6, dimensions.get(EntityFactKeys.HITBOX_WIDTH).value());
        eq(1.95, dimensions.get(EntityFactKeys.HITBOX_HEIGHT).value());
        check(!base.containsKey(EntityFactKeys.HITBOX_WIDTH), "width leaked into base_stats");
    }

    private static EntitySpeciesFacts sampleFacts(Map<String, Double> attributes) {
        return new EntitySpeciesFacts(
            "minecraft:zombie",
            "minecraft",
            "entity.minecraft.zombie",
            Set.of(EntityGameplayCategory.HOSTIL, EntityGameplayCategory.MORTO_VIVO),
            "monster",
            0.6,
            1.95,
            attributes
        );
    }

    private static Map<String, CompendiumFact<?>> facts(CompendiumEntry entry, String sectionId) {
        CompendiumSection section = entry.sections().stream()
            .filter(candidate -> candidate.sectionId().equals(sectionId))
            .findFirst()
            .orElseThrow(() -> new AssertionError("missing section " + sectionId));
        return section.facts().stream().collect(Collectors.toMap(CompendiumFact::factKey, fact -> fact));
    }

    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
