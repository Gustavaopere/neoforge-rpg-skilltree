package dev.gustavopere.rpgskilltree.runtime.compendium;

import com.mojang.logging.LogUtils;
import dev.gustavopere.rpgskilltree.compendium.catalog.CompendiumCatalogSnapshot;
import dev.gustavopere.rpgskilltree.runtime.diagnostics.RuntimeDiagnostics;
import dev.gustavopere.rpgskilltree.runtime.diagnostics.RuntimeDiagnostics.Category;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import org.slf4j.Logger;

/** Publishes the finalized entity catalog once the dedicated/integrated server has started. */
public final class CompendiumEntityCatalogEvents {
    private static final Logger LOGGER = LogUtils.getLogger();

    private CompendiumEntityCatalogEvents() {}

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        CompendiumCatalogSnapshot snapshot = RuntimeCompendiumEntityCatalog.publishFromRegistries();
        RuntimeDiagnostics.info(
            LOGGER,
            Category.COMPENDIUM,
            "entity_catalog_published",
            "Compendium entity catalog published with {} entries",
            snapshot.entries().size()
        );
    }
}
