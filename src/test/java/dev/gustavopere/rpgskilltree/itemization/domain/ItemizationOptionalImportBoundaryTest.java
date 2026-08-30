package dev.gustavopere.rpgskilltree.itemization.domain;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

final class ItemizationOptionalImportBoundaryTest {
    private static final Path DOMAIN_ROOT = Path.of("src/main/java/dev/gustavopere/rpgskilltree/itemization/domain");
    private static final List<String> ALLOWED_IMPORT_PREFIXES = List.of(
        "import java.",
        "import net.minecraft.",
        "import net.neoforged."
    );
    private static final List<String> ALLOWED_QUALIFIED_PREFIXES = List.of(
        "java.",
        "net.minecraft.",
        "net.neoforged."
    );
    private static final Pattern QUALIFIED_REFERENCE = Pattern.compile(
        "\\b(?:[a-z_][A-Za-z0-9_$]*\\.){2,}[A-Za-z_$][A-Za-z0-9_$]*\\b"
    );

    @Test
    void itemizationDomainDoesNotReferenceOptionalOrInternalCompatibilityClasses() throws IOException {
        if (!Files.isDirectory(DOMAIN_ROOT)) {
            fail("itemization domain source directory is missing");
        }

        try (var files = Files.walk(DOMAIN_ROOT)) {
            for (Path file : files.filter(path -> path.toString().endsWith(".java")).toList()) {
                int lineNumber = 0;
                for (String line : Files.readAllLines(file)) {
                    lineNumber++;
                    assertAllowedSourceLine(file, lineNumber, line);
                }
            }
        }
    }

    @Test
    void scannerRejectsFullyQualifiedProviderReferencesWithoutImports() {
        assertThrows(
            AssertionError.class,
            () -> assertAllowedSourceLine(
                Path.of("Synthetic.java"),
                1,
                "ru.ironsspellbooks.api.spells.AbstractSpell spell;"
            )
        );
        assertThrows(
            AssertionError.class,
            () -> assertAllowedSourceLine(
                Path.of("Synthetic.java"),
                1,
                "dev.gustavopere.rpgskilltree.runtime.compat.IronsCompat bridge;"
            )
        );
        assertDoesNotThrow(
            () -> assertAllowedSourceLine(
                Path.of("Synthetic.java"),
                1,
                "java.util.Objects.requireNonNull(value);"
            )
        );
    }

    @Test
    void scannerRejectsQualifiedProviderReferencesSplitAcrossLines() {
        assertThrows(
            AssertionError.class,
            () -> {
                assertAllowedSourceLine(
                    Path.of("Synthetic.java"),
                    1,
                    "ru.ironsspellbooks.api.spells."
                );
                assertAllowedSourceLine(
                    Path.of("Synthetic.java"),
                    2,
                    "AbstractSpell spell;"
                );
            }
        );
    }

    private static void assertAllowedSourceLine(Path file, int lineNumber, String line) {
        String trimmed = line.trim();
        if (trimmed.isEmpty()
            || trimmed.startsWith("package ")
            || trimmed.startsWith("//")
            || trimmed.startsWith("/*")
            || trimmed.startsWith("*")
            || trimmed.startsWith("*/")) {
            return;
        }

        if (trimmed.startsWith("import ")) {
            if (ALLOWED_IMPORT_PREFIXES.stream().noneMatch(trimmed::startsWith)) {
                fail("non-domain import in " + file + ":" + lineNumber + " -> " + trimmed);
            }
            return;
        }

        Matcher matcher = QUALIFIED_REFERENCE.matcher(trimmed);
        while (matcher.find()) {
            String reference = matcher.group();
            if (ALLOWED_QUALIFIED_PREFIXES.stream().noneMatch(reference::startsWith)) {
                fail("fully qualified non-domain reference in " + file + ":" + lineNumber + " -> " + reference);
            }
        }
    }
}