package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies;

import java.util.Set;

/** Exact version boundary for the MineColonies battle-mage adapter. */
public final class MineColoniesVersionContract {
    public static final String SUPPORTED_RUNTIME_VERSION = "1.1.1375";
    public static final String SUPPORTED_ARTIFACT_VERSION = "1.1.1375-1.21.1-snapshot";

    private static final Set<String> SUPPORTED = Set.of(
        SUPPORTED_RUNTIME_VERSION,
        SUPPORTED_ARTIFACT_VERSION
    );

    private MineColoniesVersionContract() {}

    public static boolean supports(String version) {
        return version != null && SUPPORTED.contains(version.trim());
    }
}
