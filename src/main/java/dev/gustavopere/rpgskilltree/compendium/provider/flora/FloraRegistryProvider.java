package dev.gustavopere.rpgskilltree.compendium.provider.flora;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumFact;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumProvenance;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumSection;
import dev.gustavopere.rpgskilltree.compendium.api.DiscoveryPolicy;
import dev.gustavopere.rpgskilltree.compendium.api.FactConfidence;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.api.FactVisibility;
import dev.gustavopere.rpgskilltree.compendium.api.VisibilityPolicy;
import dev.gustavopere.rpgskilltree.compendium.flora.FloraFactKeys;
import dev.gustavopere.rpgskilltree.compendium.flora.FloraKind;
import dev.gustavopere.rpgskilltree.compendium.flora.FloraSpeciesFacts;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** Projects verified non-tree botanical registry facts into immutable Compendium entries. */
public final class FloraRegistryProvider {
    private FloraRegistryProvider() {}

    public static CompendiumEntry create(FloraSpeciesFacts species) {
        if (species.floraKind() == FloraKind.CROP) {
            throw new IllegalArgumentException("crop descriptors must use CropProvider");
        }
        if (species.floraKind() == FloraKind.TREE_COMPONENT) {
            throw new IllegalArgumentException("tree components must be grouped into a species before entry creation");
        }
        return buildEntry(species);
    }

    static CompendiumEntry buildEntry(FloraSpeciesFacts species) {
        LinkedHashSet<String> categories = new LinkedHashSet<>(species.floraKind().defaultCategories());
        species.categories().stream().sorted().forEach(categories::add);

        List<CompendiumFact<?>> identity = new ArrayList<>();
        identity.add(fact(FloraFactKeys.RESOURCE_LOCATION, species.resourceLocation(), null));
        identity.add(fact(FloraFactKeys.SOURCE_MOD_ID, species.sourceModId(), null));
        if (!species.relatedBlockIds().isEmpty()) {
            identity.add(fact(FloraFactKeys.RELATED_BLOCKS, species.relatedBlockIds().stream().sorted().toList(), null));
        }
        if (!species.relatedItemIds().isEmpty()) {
            identity.add(fact(FloraFactKeys.RELATED_ITEMS, species.relatedItemIds().stream().sorted().toList(), null));
        }

        List<CompendiumFact<?>> growth = new ArrayList<>();
        if (species.maxGrowthStage() != null) {
            growth.add(fact(FloraFactKeys.MAX_GROWTH_STAGE, species.maxGrowthStage(), "stage"));
        }
        if (species.deterministicGrowthTicks() != null) {
            growth.add(fact(FloraFactKeys.DETERMINISTIC_GROWTH_TICKS, species.deterministicGrowthTicks(), "ticks"));
        }

        List<CompendiumFact<?>> products = new ArrayList<>();
        if (species.seedItemId() != null) products.add(fact(FloraFactKeys.SEED_ITEM, species.seedItemId(), null));
        if (species.produceItemId() != null) products.add(fact(FloraFactKeys.PRODUCE_ITEM, species.produceItemId(), null));

        return new CompendiumEntry(
            CompendiumEntryId.of(species.floraKind().canonicalEntryKind(), species.resourceLocation()),
            species.sourceModId(),
            species.translationKey(),
            Set.copyOf(categories),
            List.of(
                new CompendiumSection("identity", identity),
                new CompendiumSection("growth", growth),
                new CompendiumSection("products", products)
            ),
            List.of(),
            DiscoveryPolicy.OBSERVATION,
            VisibilityPolicy.HIDE_DETAILS_UNTIL_DISCOVERED,
            new CompendiumProvenance(FactSource.REGISTRY, "runtime:block_registry"),
            1
        );
    }

    private static <T> CompendiumFact<T> fact(String key, T value, String unit) {
        return new CompendiumFact<>(key, value, unit, FactSource.REGISTRY, FactConfidence.EXACT,
            FactVisibility.DISCOVERED_ONLY, null);
    }
}
