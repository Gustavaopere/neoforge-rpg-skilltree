package dev.gustavopere.rpgskilltree.compendium.integration.rpg;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumFact;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumSection;
import dev.gustavopere.rpgskilltree.compendium.api.FactConfidence;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.api.FactVisibility;
import dev.gustavopere.rpgskilltree.compendium.entity.EntityFactKeys;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Projects canonical RPG scaling metadata without redefining species base statistics. */
public final class RpgEntityScalingCompendiumProvider {
    private RpgEntityScalingCompendiumProvider() {}

    public static CompendiumSection createSection(
        long entityLevel,
        String rarity,
        String archetype,
        Map<String, Double> effectiveAttributes
    ) {
        if (entityLevel < 0L) throw new IllegalArgumentException("entityLevel must be non-negative");
        if (rarity == null || rarity.isBlank()) throw new IllegalArgumentException("rarity must not be blank");
        if (archetype == null || archetype.isBlank()) throw new IllegalArgumentException("archetype must not be blank");

        ArrayList<CompendiumFact<?>> facts = new ArrayList<>();
        facts.add(adapterFact("rpg.entity_level", entityLevel, "level"));
        facts.add(adapterFact("rpg.rarity", rarity.trim(), null));
        facts.add(adapterFact("rpg.archetype", archetype.trim(), null));

        if (effectiveAttributes != null) {
            effectiveAttributes.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    String key = entry.getKey();
                    Double value = entry.getValue();
                    if (key == null || key.isBlank()) throw new IllegalArgumentException("effective attribute key must not be blank");
                    if (value == null || !Double.isFinite(value)) throw new IllegalArgumentException("effective attribute value must be finite");
                    facts.add(runtimeFact("rpg.effective." + key, value, unitFor(key)));
                });
        }

        return new CompendiumSection("rpg_scaling", List.copyOf(facts));
    }

    private static <T> CompendiumFact<T> adapterFact(String key, T value, String unit) {
        return new CompendiumFact<>(
            key,
            value,
            unit,
            FactSource.ADAPTER,
            FactConfidence.EXACT,
            FactVisibility.DISCOVERED_ONLY,
            null
        );
    }

    private static <T> CompendiumFact<T> runtimeFact(String key, T value, String unit) {
        return new CompendiumFact<>(
            key,
            value,
            unit,
            FactSource.RUNTIME_ENTITY,
            FactConfidence.EXACT,
            FactVisibility.DISCOVERED_ONLY,
            null
        );
    }

    private static String unitFor(String key) {
        return switch (key) {
            case EntityFactKeys.MAX_HEALTH, EntityFactKeys.ARMOR, EntityFactKeys.ARMOR_TOUGHNESS,
                 EntityFactKeys.ATTACK_DAMAGE, EntityFactKeys.ATTACK_KNOCKBACK, EntityFactKeys.FOLLOW_RANGE,
                 EntityFactKeys.JUMP_STRENGTH -> "points";
            case EntityFactKeys.MOVEMENT_SPEED, EntityFactKeys.FLYING_SPEED -> "blocks_per_tick";
            case EntityFactKeys.KNOCKBACK_RESISTANCE -> "ratio";
            default -> null;
        };
    }
}
