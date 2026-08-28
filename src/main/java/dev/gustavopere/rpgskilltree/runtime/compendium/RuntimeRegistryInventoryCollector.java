package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.compendium.catalog.InventoryKind;
import dev.gustavopere.rpgskilltree.compendium.catalog.RegistryInventoryEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.fml.ModList;

public final class RuntimeRegistryInventoryCollector {
    private RuntimeRegistryInventoryCollector() {}

    public static List<RegistryInventoryEntry> collect(MinecraftServer server) {
        Map<String, String> displayNames = modDisplayNames();
        ArrayList<RegistryInventoryEntry> entries = new ArrayList<>();

        for (Map.Entry<ResourceKey<EntityType<?>>, EntityType<?>> entry : BuiltInRegistries.ENTITY_TYPE.entrySet()) {
            ResourceLocation id = entry.getKey().location();
            entries.add(record(
                InventoryKind.ENTITY,
                id,
                entry.getValue().getDescriptionId(),
                displayNames,
                Registries.ENTITY_TYPE.location().toString()
            ));
        }

        for (Map.Entry<ResourceKey<Block>, Block> entry : BuiltInRegistries.BLOCK.entrySet()) {
            ResourceLocation id = entry.getKey().location();
            BlockCatalogClassifier.classify(entry.getValue(), id).ifPresent(kind -> entries.add(record(
                kind,
                id,
                entry.getValue().getDescriptionId(),
                displayNames,
                Registries.BLOCK.location().toString()
            )));
        }

        collectDynamic(
            server.registryAccess().registryOrThrow(Registries.BIOME),
            InventoryKind.BIOME,
            Registries.BIOME.location().toString(),
            displayNames,
            entries
        );
        collectDynamic(
            server.registryAccess().registryOrThrow(Registries.STRUCTURE),
            InventoryKind.STRUCTURE,
            Registries.STRUCTURE.location().toString(),
            displayNames,
            entries
        );
        collectDynamic(
            server.registryAccess().registryOrThrow(Registries.LEVEL_STEM),
            InventoryKind.DIMENSION,
            Registries.LEVEL_STEM.location().toString(),
            displayNames,
            entries
        );

        entries.sort(Comparator.comparing(RegistryInventoryEntry::key));
        return List.copyOf(entries);
    }

    private static <T> void collectDynamic(
        Registry<T> registry,
        InventoryKind kind,
        String registrySource,
        Map<String, String> displayNames,
        List<RegistryInventoryEntry> destination
    ) {
        for (Map.Entry<ResourceKey<T>, T> entry : registry.entrySet()) {
            ResourceLocation id = entry.getKey().location();
            destination.add(record(
                kind,
                id,
                translationKey(kind, id),
                displayNames,
                registrySource
            ));
        }
    }

    private static RegistryInventoryEntry record(
        InventoryKind kind,
        ResourceLocation id,
        String translationKey,
        Map<String, String> displayNames,
        String registrySource
    ) {
        String namespace = id.getNamespace();
        return new RegistryInventoryEntry(
            kind,
            id.toString(),
            namespace,
            translationKey,
            displayNames.getOrDefault(namespace, namespace),
            registrySource,
            true
        );
    }

    private static String translationKey(InventoryKind kind, ResourceLocation id) {
        String prefix = switch (kind) {
            case BIOME -> "biome";
            case STRUCTURE -> "structure";
            case DIMENSION -> "dimension";
            default -> "compendium";
        };
        return prefix + "." + id.getNamespace() + "." + id.getPath().replace('/', '.');
    }

    private static Map<String, String> modDisplayNames() {
        HashMap<String, String> names = new HashMap<>();
        ModList.get().getMods().forEach(info -> names.put(info.getModId(), info.getDisplayName()));
        names.putIfAbsent("minecraft", "Minecraft");
        return Map.copyOf(names);
    }
}
