package dev.gustavopere.rpgskilltree.compendium.provider.flora;

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
import dev.gustavopere.rpgskilltree.compendium.flora.FloraFactKeys;
import dev.gustavopere.rpgskilltree.compendium.flora.TreeComponent;
import dev.gustavopere.rpgskilltree.compendium.flora.TreeSpeciesDescriptor;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** Creates exactly one TREE entry for a verified species grouping. */
public final class TreeProvider {
    private TreeProvider() {}

    public static CompendiumEntry create(TreeSpeciesDescriptor species) {
        LinkedHashSet<String> categories = new LinkedHashSet<>();
        categories.add("arvore");
        species.categories().stream().sorted().forEach(categories::add);

        List<CompendiumFact<?>> identity = List.of(
            fact(FloraFactKeys.RESOURCE_LOCATION, species.resourceLocation()),
            fact(FloraFactKeys.SOURCE_MOD_ID, species.sourceModId())
        );
        List<CompendiumFact<?>> components = new ArrayList<>();
        for (TreeComponent component : species.components()) {
            components.add(fact("tree_component." + component.role().id(), component.resourceLocation()));
        }

        return new CompendiumEntry(
            CompendiumEntryId.of(CompendiumEntryKind.TREE, species.resourceLocation()),
            species.sourceModId(),
            species.translationKey(),
            Set.copyOf(categories),
            List.of(new CompendiumSection("identity", identity), new CompendiumSection("components", components)),
            List.of(),
            DiscoveryPolicy.OBSERVATION,
            VisibilityPolicy.HIDE_DETAILS_UNTIL_DISCOVERED,
            new CompendiumProvenance(FactSource.REGISTRY, "runtime:block_registry:tree_group"),
            1
        );
    }

    private static <T> CompendiumFact<T> fact(String key, T value) {
        return new CompendiumFact<>(key, value, null, FactSource.REGISTRY, FactConfidence.EXACT,
            FactVisibility.DISCOVERED_ONLY, null);
    }
}
