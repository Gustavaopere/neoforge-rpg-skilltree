package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy;

import java.util.Set;

/** Exact audited provider-version boundary for the MineColonies economy adapter. */
public final class MineColoniesEconomyVersionContract {
    public static final String CURRENT_SUPPORTED_ARTIFACT_VERSION = "1.1.1376-1.21.1-snapshot";

    private static final Set<String> AUDITED_ARTIFACT_VERSIONS = Set.of(
        "1.1.1375-1.21.1-snapshot",
        CURRENT_SUPPORTED_ARTIFACT_VERSION
    );

    private MineColoniesEconomyVersionContract() {}

    public static boolean supports(String version) {
        return version != null && AUDITED_ARTIFACT_VERSIONS.contains(version.trim());
    }
}
