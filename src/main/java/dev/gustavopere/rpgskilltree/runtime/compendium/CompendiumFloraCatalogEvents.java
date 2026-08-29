package dev.gustavopere.rpgskilltree.runtime.compendium;

import com.mojang.logging.LogUtils;
import dev.gustavopere.rpgskilltree.compendium.catalog.CompendiumCatalogSnapshot;
import dev.gustavopere.rpgskilltree.runtime.diagnostics.RuntimeDiagnostics;
import dev.gustavopere.rpgskilltree.runtime.diagnostics.RuntimeDiagnostics.Category;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import org.slf4j.Logger;

/** Publishes the finalized flora/tree/crop catalog once per server startup. */
public final class CompendiumFloraCatalogEvents {
    private static final Logger LOGGER = LogUtils.getLogger();

    private CompendiumFloraCatalogEvents() {}

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        CompendiumCatalogSnapshot snapshot = RuntimeCompendiumFloraCatalog.publishFromRegistries();
        RuntimeDiagnostics.info(
            LOGGER,
            Category.COMPENDIUM,
            "flora_catalog_published",
            "Compendium flora catalog published with {} entries",
            snapshot.entries().size()
        );
    }
}
