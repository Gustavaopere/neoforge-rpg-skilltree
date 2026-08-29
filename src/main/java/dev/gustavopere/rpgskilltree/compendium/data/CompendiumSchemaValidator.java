package dev.gustavopere.rpgskilltree.compendium.data;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationTarget;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationTargetKind;
import dev.gustavopere.rpgskilltree.compendium.api.FactConfidence;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
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
        String sourceName = requiredString(document, "source", fileId);

        boolean hasLegacy = hasNonBlankString(document, "to");
        boolean hasTargetKind = hasNonBlankString(document, "target_kind");
        boolean hasTarget = hasNonBlankString(document, "target");
        if (hasLegacy && (hasTargetKind || hasTarget)) {
            throw invalid(fileId, "$.target", "relation must use exactly one target format");
        }
        if (!hasLegacy && !(hasTargetKind && hasTarget)) {
            throw invalid(fileId, "$.target", "relation requires legacy to or target_kind + target");
        }

        if (hasLegacy) {
            try {
                CompendiumEntryId.parse(requiredString(document, "to", fileId));
            } catch (IllegalArgumentException exception) {
                throw invalid(fileId, "$.to", exception.getMessage());
            }
        } else {
            String kindName = requiredString(document, "target_kind", fileId);
            String target = requiredString(document, "target", fileId);
            final CompendiumRelationTargetKind kind;
            try {
                kind = CompendiumRelationTargetKind.valueOf(kindName);
            } catch (IllegalArgumentException exception) {
                throw invalid(fileId, "$.target_kind", "unknown relation target kind: " + kindName);
            }
            try {
                if (kind == CompendiumRelationTargetKind.ENTRY) {
                    CompendiumEntryId.parse(target);
                } else {
                    CompendiumRelationTarget.resource(kind, target);
                }
            } catch (IllegalArgumentException exception) {
                throw invalid(fileId, "$.target", exception.getMessage());
            }
        }

        FactSource source;
        try {
            source = FactSource.valueOf(sourceName);
        } catch (IllegalArgumentException exception) {
            throw invalid(fileId, "$.source", "unknown fact source: " + sourceName);
        }
        String confidenceName = optionalString(document, "confidence");
        FactConfidence confidence = FactConfidence.EXACT;
        if (confidenceName != null) {
            try {
                confidence = FactConfidence.valueOf(confidenceName);
            } catch (IllegalArgumentException exception) {
                throw invalid(fileId, "$.confidence", "unknown fact confidence: " + confidenceName);
            }
        }
        if (confidence == FactConfidence.UNAVAILABLE) {
            throw invalid(fileId, "$.confidence", "published relation cannot be UNAVAILABLE");
        }
        if (source == FactSource.CURATED_EDITORIAL && confidence == FactConfidence.EXACT && optionalString(document, "evidence_id") == null) {
            throw invalid(fileId, "$.evidence_id", "curated EXACT relation requires evidence_id");
        }
    }

    private static void validateDiscovery(String fileId, Map<String, Object> document) {
        requiredString(document, "id", fileId);
        requiredString(document, "entry", fileId);
        requiredString(document, "trigger", fileId);
    }

    private static boolean hasNonBlankString(Map<String, Object> document, String field) {
        Object value = document.get(field);
        return value instanceof String text && !text.trim().isEmpty();
    }

    private static String optionalString(Map<String, Object> document, String field) {
        Object value = document.get(field);
        if (value == null) return null;
        if (!(value instanceof String text) || text.trim().isEmpty()) return null;
        return text.trim();
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
