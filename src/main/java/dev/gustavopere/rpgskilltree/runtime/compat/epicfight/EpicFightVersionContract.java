package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

/** Exact audited Epic Fight version gate shared by the A0001-A0020 runtime and bootstrap. */
public final class EpicFightVersionContract {
    public static final String SUPPORTED_VERSION = "21.17.3.1";

    private EpicFightVersionContract() {}

    public static boolean supportsVersion(String version) {
        return SUPPORTED_VERSION.equals(version);
    }
}
