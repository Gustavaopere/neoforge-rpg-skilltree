package dev.gustavopere.rpgskilltree.compendium.client;

/** One localized-label/value pair in the optional Compendium debug provenance panel. */
public record CompendiumDebugField(String translationKey, String value) {
    public CompendiumDebugField {
        translationKey = requireText(translationKey, "translationKey");
        value = requireText(value, "value");
    }

    private static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value.trim();
    }
}
