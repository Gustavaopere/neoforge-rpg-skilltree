package dev.gustavopere.rpgskilltree.runtime.compendium;

import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.nio.file.Path;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import org.slf4j.Logger;

public final class CompendiumInventoryEvents {
    public static final String ENABLE_ENV = "RPGSKILLTREE_COMPENDIUM_INVENTORY";
    private static final Logger LOGGER = LogUtils.getLogger();

    private CompendiumInventoryEvents() {}

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        if (!enabled(System.getenv(ENABLE_ENV))) return;
        try {
            Path output = RuntimeInventoryReportWriter.write(
                RuntimeRegistryInventoryCollector.collect(event.getServer())
            );
            LOGGER.info("Compendium runtime inventory written to {}", output);
        } catch (IOException | RuntimeException exc) {
            LOGGER.error("Failed to write Compendium runtime inventory", exc);
            throw new IllegalStateException("Compendium runtime inventory collection failed", exc);
        }
    }

    static boolean enabled(String value) {
        if (value == null) return false;
        return switch (value.trim().toLowerCase()) {
            case "1", "true", "yes", "on" -> true;
            default -> false;
        };
    }
}
