package dev.gustavopere.rpgskilltree.itemization.domain;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

final class ItemizationOptionalImportBoundaryTest {
    private static final Path DOMAIN_ROOT = Path.of("src/main/java/dev/gustavopere/rpgskilltree/itemization/domain");
    private static final List<String> ALLOWED_IMPORT_PREFIXES = List.of(
        "import java.",
        "import net.minecraft.",
        "import net.neoforged."
    );

    @Test
    void itemizationDomainDoesNotImportOptionalOrInternalCompatibilityClasses() throws IOException {
        if (!Files.isDirectory(DOMAIN_ROOT)) {
            fail("itemization domain source directory is missing");
        }

        try (var files = Files.walk(DOMAIN_ROOT)) {
            for (Path file : files.filter(path -> path.toString().endsWith(".java")).toList()) {
                int lineNumber = 0;
                for (String line : Files.readAllLines(file)) {
                    lineNumber++;
                    String trimmed = line.trim();
                    if (!trimmed.startsWith("import ")) continue;
                    if (ALLOWED_IMPORT_PREFIXES.stream().noneMatch(trimmed::startsWith)) {
                        fail("non-domain import in " + file + ":" + lineNumber + " -> " + trimmed);
                    }
                }
            }
        }
    }
}