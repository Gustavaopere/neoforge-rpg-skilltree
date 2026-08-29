package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.compendium.world.WorldCatalogCoverage;
import dev.gustavopere.rpgskilltree.compendium.world.WorldCatalogSnapshot;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;

/** Server-authoritative, atomically replaced snapshot for Stage 10.08 world geography. */
public final class RuntimeCompendiumWorldCatalog {
    private static volatile WorldCatalogSnapshot CURRENT = WorldCatalogSnapshot.empty();

    private RuntimeCompendiumWorldCatalog() {}

    public static WorldCatalogSnapshot snapshot() {
        return CURRENT;
    }

    public static WorldCatalogSnapshot publish(MinecraftServer server) {
        WorldCatalogSnapshot candidate = RuntimeWorldCatalogCollector.collect(server);
        Set<String> biomeIds = server.registryAccess().registryOrThrow(Registries.BIOME).keySet().stream()
            .map(Object::toString).collect(java.util.stream.Collectors.toUnmodifiableSet());
        Set<String> structureIds = server.registryAccess().registryOrThrow(Registries.STRUCTURE).keySet().stream()
            .map(Object::toString).collect(java.util.stream.Collectors.toUnmodifiableSet());
        HashSet<String> dimensionIds = new HashSet<>();
        server.getAllLevels().forEach(level -> dimensionIds.add(level.dimension().location().toString()));

        WorldCatalogCoverage coverage = WorldCatalogCoverage.compare(biomeIds, structureIds, Set.copyOf(dimensionIds), candidate);
        if (!coverage.complete()) {
            throw new IllegalStateException("Incomplete Compendium world catalog: " + coverage);
        }
        CURRENT = candidate;
        return candidate;
    }
}
