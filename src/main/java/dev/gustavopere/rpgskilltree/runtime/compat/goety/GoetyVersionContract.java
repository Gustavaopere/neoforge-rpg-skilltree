package dev.gustavopere.rpgskilltree.runtime.compat.goety;

/** Exact audited Goety version gate for the Stage 04.03 mastery adapter. */
public final class GoetyVersionContract {
    public static final String SUPPORTED_VERSION = "3.1.4";

    private GoetyVersionContract() {}

    public static boolean supportsVersion(String version) {
        return SUPPORTED_VERSION.equals(version);
    }
}
