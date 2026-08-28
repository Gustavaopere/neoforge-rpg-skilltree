package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.flora.FloraClassification;
import dev.gustavopere.rpgskilltree.compendium.flora.FloraClassificationEvidence;
import dev.gustavopere.rpgskilltree.compendium.flora.FloraKind;
import dev.gustavopere.rpgskilltree.compendium.flora.FloraSpeciesFacts;
import dev.gustavopere.rpgskilltree.compendium.flora.TreeComponent;
import dev.gustavopere.rpgskilltree.compendium.flora.TreeComponentRole;
import dev.gustavopere.rpgskilltree.compendium.flora.TreeSpeciesDescriptor;
import dev.gustavopere.rpgskilltree.compendium.provider.flora.CropProvider;
import dev.gustavopere.rpgskilltree.compendium.provider.flora.FloraClassifier;
import dev.gustavopere.rpgskilltree.compendium.provider.flora.FloraRegistryProvider;
import dev.gustavopere.rpgskilltree.compendium.provider.flora.TreeProvider;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FungusBlock;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.KelpPlantBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.TallSeagrassBlock;

/**
 * Server-safe registry collector. It never places blocks or simulates growth.
 * Unknown botanical shapes fail closed instead of being classified from names.
 */
public final class RuntimeFloraCatalogCollector {
    private RuntimeFloraCatalogCollector() {}

    public record CollectionResult(
        List<CompendiumEntry> entries,
        List<CompendiumEntryId> expectedEntryIds,
        List<String> ambiguousBlockIds
    ) {
        public CollectionResult {
            entries = List.copyOf(entries);
            expectedEntryIds = List.copyOf(expectedEntryIds);
            ambiguousBlockIds = List.copyOf(ambiguousBlockIds);
        }
    }

    public static CollectionResult collect() {
        List<CompendiumEntry> entries = new ArrayList<>();
        List<CompendiumEntryId> expected = new ArrayList<>();
        List<String> ambiguous = new ArrayList<>();

        for (Block block : BuiltInRegistries.BLOCK) {
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
            if (id == null) continue;

            FloraClassification classification = FloraClassifier.classify(evidence(id, block));
            if (classification.ambiguous()) {
                ambiguous.add(id.toString());
                continue;
            }
            if (!classification.classified()) continue;

            CompendiumEntry entry = createEntry(id, block, classification);
            if (entry != null) {
                entries.add(entry);
                expected.add(entry.id());
            }
        }

        entries.sort(Comparator.comparing(entry -> entry.id().serializedId()));
        expected.sort(Comparator.comparing(CompendiumEntryId::serializedId));
        ambiguous.sort(String::compareTo);
        return new CollectionResult(entries, expected, ambiguous);
    }

    private static FloraClassificationEvidence evidence(ResourceLocation id, Block block) {
        boolean crop = block instanceof CropBlock;
        boolean sapling = block instanceof SaplingBlock;
        boolean flower = block instanceof FlowerBlock;
        boolean fungus = block instanceof MushroomBlock || block instanceof FungusBlock;
        boolean aquatic = block instanceof SeagrassBlock || block instanceof TallSeagrassBlock
            || block instanceof KelpBlock || block instanceof KelpPlantBlock;
        boolean genericBush = block instanceof BushBlock && !crop && !sapling && !flower && !fungus && !aquatic;

        return new FloraClassificationEvidence(
            id.toString(),
            Set.of(),
            crop,
            sapling,
            flower,
            fungus,
            aquatic,
            false,
            genericBush ? FloraKind.FLORA : null,
            false
        );
    }

    private static CompendiumEntry createEntry(ResourceLocation id, Block block, FloraClassification classification) {
        if (classification.kind() == FloraKind.TREE_COMPONENT) {
            TreeSpeciesDescriptor tree = new TreeSpeciesDescriptor(
                id.toString(),
                id.getNamespace(),
                translationKey(id),
                Set.of(),
                List.of(new TreeComponent(TreeComponentRole.SAPLING, id.toString()))
            );
            return TreeProvider.create(tree);
        }

        Integer maxGrowthStage = block instanceof CropBlock crop ? crop.getMaxAge() : null;
        Set<String> relatedItems = relatedItems(block);
        FloraSpeciesFacts facts = new FloraSpeciesFacts(
            id.toString(),
            id.getNamespace(),
            translationKey(id),
            classification.kind(),
            classification.categories(),
            Set.of(id.toString()),
            relatedItems,
            maxGrowthStage,
            null,
            null,
            null
        );
        return classification.kind() == FloraKind.CROP ? CropProvider.create(facts) : FloraRegistryProvider.create(facts);
    }

    private static Set<String> relatedItems(Block block) {
        if (block.asItem() == Items.AIR) return Set.of();
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(block.asItem());
        if (itemId == null) return Set.of();
        LinkedHashSet<String> result = new LinkedHashSet<>();
        result.add(itemId.toString());
        return Set.copyOf(result);
    }

    private static String translationKey(ResourceLocation id) {
        return "block." + id.getNamespace() + "." + id.getPath().replace('/', '.');
    }
}
