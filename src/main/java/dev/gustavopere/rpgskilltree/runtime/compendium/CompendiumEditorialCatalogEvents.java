package dev.gustavopere.rpgskilltree.runtime.compendium;

import com.mojang.logging.LogUtils;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.runtime.diagnostics.RuntimeDiagnostics;
import dev.gustavopere.rpgskilltree.runtime.diagnostics.RuntimeDiagnostics.Category;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import org.slf4j.Logger;

/** Publishes the Stage 10.10 pt-BR editorial overlay after technical Compendium catalogs are ready. */
public final class CompendiumEditorialCatalogEvents {
    private static final Logger LOGGER = LogUtils.getLogger();

    private CompendiumEditorialCatalogEvents() {}

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        Objects.requireNonNull(event, "event");
        RuntimeCompendiumEditorialCatalog.beginServerLifecycle();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onServerStarted(ServerStartedEvent event) {
        RuntimeCompendiumEditorialCatalog.PublicationResult result =
            RuntimeCompendiumEditorialCatalog.loadAndPublish(
                event.getServer().getResourceManager(),
                technicalEntries(),
                loadedProviderNamespaces()
            );

        if (result.published()) {
            RuntimeDiagnostics.info(
                LOGGER,
                Category.COMPENDIUM,
                "editorial_catalog_published",
                "Compendium editorial catalog published with {} entries",
                result.snapshot().entries().size()
            );
        } else {
            RuntimeDiagnostics.warn(
                LOGGER,
                Category.COMPENDIUM,
                "editorial_catalog_rejected",
                "Compendium editorial catalog rejected; preserving previous snapshot: {}",
                result.diagnostic()
            );
        }
    }

    static List<CompendiumEntry> technicalEntries() {
        return mergeTechnicalEntries(
            RuntimeCompendiumEntityCatalog.snapshot().entries(),
            RuntimeCompendiumFloraCatalog.snapshot().entries(),
            RuntimeCompendiumWorldCatalog.snapshot().entries()
        );
    }

    static Set<String> loadedProviderNamespaces() {
        HashSet<String> loaded = new HashSet<>();
        loaded.add("minecraft");
        ModList.get().getMods().forEach(mod -> loaded.add(mod.getModId()));
        return Set.copyOf(loaded);
    }

    @SafeVarargs
    static List<CompendiumEntry> mergeTechnicalEntries(Collection<CompendiumEntry>... catalogs) {
        ArrayList<CompendiumEntry> merged = new ArrayList<>();
        HashSet<dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId> seen = new HashSet<>();
        for (Collection<CompendiumEntry> catalog : catalogs) {
            Objects.requireNonNull(catalog, "catalog");
            for (CompendiumEntry entry : catalog) {
                Objects.requireNonNull(entry, "entry");
                if (!seen.add(entry.id())) {
                    throw new IllegalArgumentException(
                        "duplicate technical Compendium entry: " + entry.id().serializedId()
                    );
                }
                merged.add(entry);
            }
        }
        merged.sort(Comparator.comparing(entry -> entry.id().serializedId()));
        return List.copyOf(merged);
    }
}
