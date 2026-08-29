package dev.gustavopere.rpgskilltree.runtime.compendium;

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
        LOGGER.info("Compendium world catalog published: {} entries", snapshot.entries().size());
    }
}
