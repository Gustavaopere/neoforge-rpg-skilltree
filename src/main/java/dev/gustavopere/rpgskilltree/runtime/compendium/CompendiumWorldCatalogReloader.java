package dev.gustavopere.rpgskilltree.runtime.compendium;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

/** Rebuilds the Stage 10.08 snapshot after server data-pack reloads. */
public final class CompendiumWorldCatalogReloader implements ResourceManagerReloadListener {
    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new CompendiumWorldCatalogReloader());
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null || !server.getAllLevels().iterator().hasNext()) return;
        RuntimeCompendiumWorldCatalog.publish(server);
    }
}
