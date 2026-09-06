package dev.gustavopere.volcanoes.compat.minecolonies;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

final class MineColoniesOptionalAdapterArchitectureTest {
    @Test
    void mineColoniesImportsStayInsideOptionalAdapters() throws Exception {
        Path sourceRoot = Path.of("src/main/java");
        List<Path> allowedRoots = List.of(
                sourceRoot.resolve("dev/gustavopere/volcanoes/compat/minecolonies").normalize(),
                sourceRoot.resolve("dev/gustavopere/rpgskilltree/runtime/compat/minecolonies").normalize()
        );

        try (var paths = Files.walk(sourceRoot)) {
            for (Path path : paths.filter(p -> p.toString().endsWith(".java")).toList()) {
                String source = Files.readString(path);
                boolean insideOptionalAdapter = allowedRoots.stream()
                        .anyMatch(root -> path.normalize().startsWith(root));
                if (source.contains("import com.minecolonies.") && !insideOptionalAdapter) {
                    fail("MineColonies import escaped optional adapter boundaries: " + path);
                }
            }
        }
    }
}
