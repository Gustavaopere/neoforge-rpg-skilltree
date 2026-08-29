package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.runtime.diagnostics.RuntimeDiagnostics;
import dev.gustavopere.rpgskilltree.runtime.diagnostics.RuntimeDiagnostics.Category;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CompendiumWorldCatalogEvents {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompendiumWorldCatalogEvents.class);

    private CompendiumWorldCatalogEvents() {}

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        var snapshot = RuntimeCompendiumWorldCatalog.publish(event.getServer());
        RuntimeDiagnostics.info(
            LOGGER,
            Category.COMPENDIUM,
            "world_catalog_published",
            "Compendium world catalog published: {} entries",
            snapshot.entries().size()
        );
    }
}
