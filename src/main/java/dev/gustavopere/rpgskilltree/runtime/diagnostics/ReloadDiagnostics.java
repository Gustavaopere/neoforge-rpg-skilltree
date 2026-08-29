package dev.gustavopere.rpgskilltree.runtime.diagnostics;

import dev.gustavopere.rpgskilltree.runtime.diagnostics.RuntimeDiagnostics.Category;
import java.util.Map;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

/** Shared fail-visible boundary for data reload parsing. */
public final class ReloadDiagnostics {
    private ReloadDiagnostics() {
    }

    public static void run(
        Logger logger,
        String dataPath,
        Map<ResourceLocation, ?> resources,
        Runnable reload
    ) {
        Objects.requireNonNull(logger, "logger");
        Objects.requireNonNull(dataPath, "dataPath");
        Objects.requireNonNull(resources, "resources");
        Objects.requireNonNull(reload, "reload");
        try {
            reload.run();
        } catch (RuntimeException failure) {
            var ids = resources.keySet().stream()
                .map(ResourceLocation::toString)
                .sorted()
                .toList();
            RuntimeDiagnostics.error(
                logger,
                Category.DATA,
                "reload_failed",
                "Data reload failed for path " + dataPath + " resources=" + ids,
                failure
            );
            throw failure;
        }
    }
}
