package dev.gustavopere.volcanoes.material;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class MaterialCoverageTest {
    private static final int EXPECTED_CANONICAL_TOP_LEVEL_JAR_COUNT = 607;
    private static final String AUDIT_RESOURCE = "data/volcanoes/materials/audit/modlist-audit.json";

    @Test
    void everyCanonicalJarHasAuditDisposition() throws Exception {
        try (InputStream input = Objects.requireNonNull(
            MaterialCoverageTest.class.getClassLoader().getResourceAsStream(AUDIT_RESOURCE),
            "missing canonical material audit resource: " + AUDIT_RESOURCE
        )) {
            ModlistAudit audit = ModlistAudit.load(input);

            assertEquals(EXPECTED_CANONICAL_TOP_LEVEL_JAR_COUNT, audit.entries().size());
            assertTrue(audit.entries().stream().allMatch(ModAuditEntry::isComplete));

            long uniqueEntries = audit.entries().stream()
                .map(entry -> entry.jar() + "\u0000" + entry.modId() + "\u0000" + entry.version())
                .distinct()
                .count();
            assertEquals(audit.entries().size(), uniqueEntries, "duplicate jar/modId/version audit tuple");
        }
    }
}
