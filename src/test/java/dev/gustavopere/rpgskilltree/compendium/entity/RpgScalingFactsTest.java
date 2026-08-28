package dev.gustavopere.rpgskilltree.compendium.entity;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumFact;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumSection;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.integration.rpg.RpgEntityScalingCompendiumProvider;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class RpgScalingFactsTest {
    public static void main(String[] args) {
        scalingFactsAreSeparatedFromSpeciesBaseStats();
        effectiveAttributesAreMarkedRuntime();
        System.out.println("RpgScalingFactsTest: PASS");
    }

    private static void scalingFactsAreSeparatedFromSpeciesBaseStats() {
        CompendiumSection section = RpgEntityScalingCompendiumProvider.createSection(
            42L,
            "rpgskilltree:rare",
            "HOSTILE",
            Map.of(EntityFactKeys.MAX_HEALTH, 60.0D, EntityFactKeys.ATTACK_DAMAGE, 9.5D)
        );
        check(section.sectionId().equals("rpg_scaling"), "dedicated scaling section");
        Set<String> keys = section.facts().stream().map(CompendiumFact::factKey).collect(Collectors.toSet());
        check(keys.contains("rpg.entity_level"), "entity level fact");
        check(keys.contains("rpg.rarity"), "rarity fact");
        check(keys.contains("rpg.archetype"), "archetype fact");
        check(keys.contains("rpg.effective.max_health"), "effective health fact");
        check(keys.contains("rpg.effective.attack_damage"), "effective damage fact");
        check(!keys.contains(EntityFactKeys.MAX_HEALTH), "must not overwrite species max health");
        check(!keys.contains(EntityFactKeys.ATTACK_DAMAGE), "must not overwrite species attack damage");
    }

    private static void effectiveAttributesAreMarkedRuntime() {
        CompendiumSection section = RpgEntityScalingCompendiumProvider.createSection(
            7L,
            "rpgskilltree:common",
            "PASSIVE",
            Map.of(EntityFactKeys.ARMOR, 3.0D)
        );
        CompendiumFact<?> effectiveArmor = section.facts().stream()
            .filter(fact -> fact.factKey().equals("rpg.effective.armor"))
            .findFirst()
            .orElseThrow();
        CompendiumFact<?> level = section.facts().stream()
            .filter(fact -> fact.factKey().equals("rpg.entity_level"))
            .findFirst()
            .orElseThrow();
        check(effectiveArmor.source() == FactSource.RUNTIME_ENTITY, "effective values are runtime observations");
        check(level.source() == FactSource.ADAPTER, "canonical scaling metadata comes from adapter");
    }

    private static void check(boolean condition, String label) {
        if (!condition) throw new AssertionError(label);
    }
}
