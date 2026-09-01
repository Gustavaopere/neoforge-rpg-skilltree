package dev.gustavopere.volcanoes.compat.minecolonies;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.fail;

final class MineColoniesOptionalAdapterArchitectureTest {
    @Test
    void mineColoniesImportsStayInsideOptionalAdapter() throws Exception {
        Path sourceRoot = Path.of("src/main/java");
        Path allowedRoot = sourceRoot.resolve(
                "dev/gustavopere/volcanoes/compat/minecolonies").normalize();

        try (var paths = Files.walk(sourceRoot)) {
            for (Path path : paths.filter(p -> p.toString().endsWith(".java")).toList()) {
                String source = Files.readString(path);
                if (source.contains("import com.minecolonies.")
                        && !path.normalize().startsWith(allowedRoot)) {
                    fail("MineColonies import escaped optional adapter boundary: " + path);
                }
            }
        }
    }
}
