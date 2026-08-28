package dev.gustavopere.rpgskilltree.compendium.data;

import java.util.Map;
import java.util.Objects;

public final class CompendiumSchemaValidator {
    public static final int CURRENT_SCHEMA_VERSION = 1;

    private CompendiumSchemaValidator() {}

    public static void validate(CompendiumDataKind kind, String fileId, Map<String, Object> document) {
        Objects.requireNonNull(kind, "kind");
        String source = requireFileId(fileId);
        if (document == null) {
            throw invalid(source, "$", "document must be an object");
        }

        int schemaVersion = requiredPositiveInteger(document, "schema_version", source);
        if (schemaVersion != CURRENT_SCHEMA_VERSION) {
            throw invalid(
                source,
                "$.schema_version",
                "unsupported version " + schemaVersion + "; expected " + CURRENT_SCHEMA_VERSION
            );
        }

        switch (kind) {
            case ENTRY -> validateEntry(source, document);
            case CATEGORY -> validateCategory(source, document);
            case RELATION -> validateRelation(source, document);
            case DISCOVERY -> validateDiscovery(source, document);
        }
    }

    private static void validateEntry(String fileId, Map<String, Object> document) {
        requiredString(document, "id", fileId);
        requiredString(document, "source_mod_id", fileId);
        requiredString(document, "translation_key", fileId);
        requiredPositiveInteger(document, "content_version", fileId);
    }

    private static void validateCategory(String fileId, Map<String, Object> document) {
        requiredString(document, "id", fileId);
        requiredString(document, "translation_key", fileId);
    }

    private static void validateRelation(String fileId, Map<String, Object> document) {
        requiredString(document, "type", fileId);
        requiredString(document, "from", fileId);
        requiredString(document, "to", fileId);
        requiredString(document, "source", fileId);
    }

    private static void validateDiscovery(String fileId, Map<String, Object> document) {
        requiredString(document, "id", fileId);
        requiredString(document, "entry", fileId);
        requiredString(document, "trigger", fileId);
    }

    private static String requiredString(Map<String, Object> document, String field, String fileId) {
        Object value = document.get(field);
        if (!(value instanceof String text) || text.trim().isEmpty()) {
            throw invalid(fileId, "$." + field, "required non-blank string");
        }
        return text.trim();
    }

    private static int requiredPositiveInteger(Map<String, Object> document, String field, String fileId) {
        Object value = document.get(field);
        if (!(value instanceof Number number)) {
            throw invalid(fileId, "$." + field, "required integer");
        }
        double floating = number.doubleValue();
        long integral = number.longValue();
        if (!Double.isFinite(floating) || floating != integral || integral <= 0L || integral > Integer.MAX_VALUE) {
            throw invalid(fileId, "$." + field, "required positive integer");
        }
        return (int) integral;
    }

    private static String requireFileId(String fileId) {
        if (fileId == null || fileId.trim().isEmpty()) {
            throw new IllegalArgumentException("fileId must not be blank");
        }
        return fileId.trim();
    }

    private static CompendiumSchemaException invalid(String fileId, String fieldPath, String detail) {
        return new CompendiumSchemaException(fileId, fieldPath, detail);
    }
}
