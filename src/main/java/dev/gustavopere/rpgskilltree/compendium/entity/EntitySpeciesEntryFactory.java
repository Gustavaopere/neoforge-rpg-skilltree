package dev.gustavopere.rpgskilltree.compendium.entity;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumFact;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumProvenance;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumSection;
import dev.gustavopere.rpgskilltree.compendium.api.DiscoveryPolicy;
import dev.gustavopere.rpgskilltree.compendium.api.FactConfidence;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.api.FactVisibility;
import dev.gustavopere.rpgskilltree.compendium.api.VisibilityPolicy;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class EntitySpeciesEntryFactory {
    private EntitySpeciesEntryFactory() {}

    public static CompendiumEntry create(EntitySpeciesFacts species) {
        LinkedHashSet<String> categories = new LinkedHashSet<>();
        categories.add("fauna");
        species.gameplayCategories().stream()
            .map(EntityGameplayCategory::id)
            .sorted()
            .forEach(categories::add);

        List<CompendiumFact<?>> identity = new ArrayList<>();
        identity.add(fact(EntityFactKeys.RESOURCE_LOCATION, species.resourceLocation(), null));
        identity.add(fact(EntityFactKeys.SOURCE_MOD_ID, species.sourceModId(), null));
        if (species.mobCategory() != null) identity.add(fact(EntityFactKeys.MOB_CATEGORY, species.mobCategory(), null));

        List<CompendiumFact<?>> baseStats = new ArrayList<>();
        species.baseAttributes().entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> baseStats.add(fact(entry.getKey(), entry.getValue(), unitFor(entry.getKey()))));

        List<CompendiumFact<?>> dimensions = List.of(
            fact(EntityFactKeys.HITBOX_WIDTH, species.hitboxWidth(), "blocks"),
            fact(EntityFactKeys.HITBOX_HEIGHT, species.hitboxHeight(), "blocks")
        );

        return new CompendiumEntry(
            CompendiumEntryId.of(CompendiumEntryKind.ENTITY, species.resourceLocation()),
            species.sourceModId(),
            species.translationKey(),
            Set.copyOf(categories),
            List.of(
                new CompendiumSection("identity", identity),
                new CompendiumSection("base_stats", baseStats),
                new CompendiumSection("dimensions", dimensions)
            ),
            List.of(),
            DiscoveryPolicy.OBSERVATION,
            VisibilityPolicy.HIDE_DETAILS_UNTIL_DISCOVERED,
            new CompendiumProvenance(FactSource.REGISTRY, "runtime:entity_type"),
            1
        );
    }

    private static <T> CompendiumFact<T> fact(String key, T value, String unit) {
        return new CompendiumFact<>(
            key,
            value,
            unit,
            FactSource.REGISTRY,
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
