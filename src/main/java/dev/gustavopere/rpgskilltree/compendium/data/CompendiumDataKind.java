package dev.gustavopere.rpgskilltree.compendium.data;

public enum CompendiumDataKind {
    ENTRY("entries"),
    CATEGORY("categories"),
    RELATION("relations"),
    DISCOVERY("discovery");

    private final String directory;

    CompendiumDataKind(String directory) {
        this.directory = directory;
    }

    public String directory() {
        return directory;
    }

    public static CompendiumDataKind fromDirectory(String value) {
        String directory = value == null ? "" : value.trim();
        for (CompendiumDataKind kind : values()) {
            if (kind.directory.equals(directory)) {
                return kind;
            }
        }
        throw new IllegalArgumentException("unknown compendium data directory: " + directory);
    }
}
