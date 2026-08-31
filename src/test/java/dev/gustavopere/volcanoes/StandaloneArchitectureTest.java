package dev.gustavopere.volcanoes;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

final class StandaloneArchitectureTest {
    private static final List<String> FORBIDDEN_IMPORT_PREFIXES = List.of(
            "import net.dries007.tfc.",
            "import static net.dries007.tfc.",
            "import tfcregistryapi.",
            "import static tfcregistryapi."
    );

    @Test
    void forbidsTfcRegistryApiNamespace() {
        assertTrue(FORBIDDEN_IMPORT_PREFIXES.contains("import tfcregistryapi."));
        assertTrue(FORBIDDEN_IMPORT_PREFIXES.contains("import static tfcregistryapi."));
    }

    @Test
    void productionSourceDoesNotImportTerraFirmaCraft() throws IOException {
        Path sourceRoot = Path.of("src/main/java");
        if (!Files.exists(sourceRoot)) {
            return;
        }
        try (var paths = Files.walk(sourceRoot)) {
            for (Path path : paths.filter(p -> p.toString().endsWith(".java")).toList()) {
                String source = Files.readString(path);
                for (String prefix : FORBIDDEN_IMPORT_PREFIXES) {
                    if (source.contains(prefix)) {
                        fail("Standalone production source imports TFC in " + path + ": " + prefix);
                    }
                }
            }
        }
    }
}
