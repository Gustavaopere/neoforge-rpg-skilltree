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
    private static final Pattern PACKAGE_DECLARATION = Pattern.compile("(?m)^\\s*package\\s+[^;]+;");
    private static final Pattern DOT_WHITESPACE = Pattern.compile("\\s*\\.\\s*");
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
                assertAllowedSource(file, Files.readString(file));
            }
        }
    }

    @Test
    void scannerRejectsFullyQualifiedProviderReferencesWithoutImports() {
        assertThrows(
            AssertionError.class,
            () -> assertAllowedSource(
                Path.of("Synthetic.java"),
                "final class Synthetic { ru.ironsspellbooks.api.spells.AbstractSpell spell; }"
            )
        );
        assertThrows(
            AssertionError.class,
            () -> assertAllowedSource(
                Path.of("Synthetic.java"),
                "final class Synthetic { dev.gustavopere.rpgskilltree.runtime.compat.IronsCompat bridge; }"
            )
        );
        assertDoesNotThrow(
            () -> assertAllowedSource(
                Path.of("Synthetic.java"),
                "final class Synthetic { void run() { java.util.Objects.requireNonNull(value); } }"
            )
        );
    }

    @Test
    void scannerRejectsQualifiedProviderReferencesSplitAcrossLines() {
        assertThrows(
            AssertionError.class,
            () -> assertAllowedSource(
                Path.of("Synthetic.java"),
                """
                    final class Synthetic {
                        ru.ironsspellbooks.
                            api.spells.
                            AbstractSpell spell;
                    }
                    """
            )
        );
    }

    @Test
    void scannerIgnoresQualifiedNamesInsideCommentsAndLiterals() {
        assertDoesNotThrow(
            () -> assertAllowedSource(
                Path.of("Synthetic.java"),
                """
                    final class Synthetic {
                        // ru.ironsspellbooks.api.spells.AbstractSpell
                        String text = "dev.gustavopere.rpgskilltree.runtime.compat.IronsCompat";
                        char marker = '.';
                    }
                    """
            )
        );
    }

    private static void assertAllowedSource(Path file, String source) {
        int lineNumber = 0;
        for (String line : source.split("\\R", -1)) {
            lineNumber++;
            String trimmed = line.trim();
            if (!trimmed.startsWith("import ")) {
                continue;
            }
            if (ALLOWED_IMPORT_PREFIXES.stream().noneMatch(trimmed::startsWith)) {
                fail("non-domain import in " + file + ":" + lineNumber + " -> " + trimmed);
            }
        }

        String codeOnly = stripCommentsAndLiterals(source);
        codeOnly = PACKAGE_DECLARATION.matcher(codeOnly).replaceAll(" ");
        String normalized = DOT_WHITESPACE.matcher(codeOnly).replaceAll(".");

        Matcher matcher = QUALIFIED_REFERENCE.matcher(normalized);
        while (matcher.find()) {
            String reference = matcher.group();
            if (ALLOWED_QUALIFIED_PREFIXES.stream().noneMatch(reference::startsWith)) {
                fail("fully qualified non-domain reference in " + file + " -> " + reference);
            }
        }
    }

    private static String stripCommentsAndLiterals(String source) {
        StringBuilder result = new StringBuilder(source.length());
        int index = 0;
        while (index < source.length()) {
            if (source.startsWith("//", index)) {
                index = maskUntilLineEnd(source, result, index);
                continue;
            }
            if (source.startsWith("/*", index)) {
                index = maskBlockComment(source, result, index);
                continue;
            }
            if (source.startsWith("\"\"\"", index)) {
                index = maskTextBlock(source, result, index);
                continue;
            }

            char current = source.charAt(index);
            if (current == '\"') {
                index = maskQuotedLiteral(source, result, index, '\"');
                continue;
            }
            if (current == '\'') {
                index = maskQuotedLiteral(source, result, index, '\'');
                continue;
            }

            result.append(current);
            index++;
        }
        return result.toString();
    }

    private static int maskUntilLineEnd(String source, StringBuilder result, int start) {
        int index = start;
        while (index < source.length()) {
            char current = source.charAt(index);
            if (current == '\n' || current == '\r') {
                return index;
            }
            result.append(' ');
            index++;
        }
        return index;
    }

    private static int maskBlockComment(String source, StringBuilder result, int start) {
        int index = start;
        while (index < source.length()) {
            if (source.startsWith("*/", index)) {
                result.append("  ");
                return index + 2;
            }
            appendMasked(result, source.charAt(index));
            index++;
        }
        return index;
    }

    private static int maskTextBlock(String source, StringBuilder result, int start) {
        int index = start;
        result.append("   ");
        index += 3;
        while (index < source.length()) {
            if (source.startsWith("\"\"\"", index)) {
                result.append("   ");
                return index + 3;
            }
            appendMasked(result, source.charAt(index));
            index++;
        }
        return index;
    }

    private static int maskQuotedLiteral(String source, StringBuilder result, int start, char quote) {
        int index = start;
        result.append(' ');
        index++;
        boolean escaped = false;
        while (index < source.length()) {
            char current = source.charAt(index);
            appendMasked(result, current);
            index++;

            if (escaped) {
                escaped = false;
                continue;
            }
            if (current == '\\') {
                escaped = true;
                continue;
            }
            if (current == quote) {
                break;
            }
        }
        return index;
    }

    private static void appendMasked(StringBuilder result, char current) {
        if (current == '\n' || current == '\r') {
            result.append(current);
        } else {
            result.append(' ');
        }
    }
}
