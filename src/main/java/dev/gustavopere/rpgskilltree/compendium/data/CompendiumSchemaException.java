package dev.gustavopere.rpgskilltree.compendium.data;

public final class CompendiumSchemaException extends IllegalArgumentException {
    public CompendiumSchemaException(String fileId, String fieldPath, String detail) {
        super("invalid compendium schema " + fileId + " at " + fieldPath + ": " + detail);
    }
}
