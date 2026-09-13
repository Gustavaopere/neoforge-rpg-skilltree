package dev.gustavopere.rpgskilltree.runtime.compat.create;

/** Exact audited Create version gate for the Stage 04.03 engineering-mastery adapter. */
public final class CreateVersionContract {
    public static final String SUPPORTED_VERSION = "6.0.10";

    private CreateVersionContract() {}

    public static boolean supportsVersion(String version) {
        return SUPPORTED_VERSION.equals(version);
    }
}
