package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy;

/** Exact provider-version boundary for the MineColonies economy adapter. */
public final class MineColoniesEconomyVersionContract {
    public static final String SUPPORTED_ARTIFACT_VERSION = "1.1.1375-1.21.1-snapshot";

    private MineColoniesEconomyVersionContract() {}

    public static boolean supports(String version) {
        return version != null && SUPPORTED_ARTIFACT_VERSION.equals(version.trim());
    }
}
