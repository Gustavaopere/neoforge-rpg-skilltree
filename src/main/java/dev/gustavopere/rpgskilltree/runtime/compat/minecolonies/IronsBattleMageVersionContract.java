package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies;

import java.util.Set;

/** Exact Iron's version boundary for the Battle Mage adapter's verified internal seams. */
public final class IronsBattleMageVersionContract {
    public static final String SUPPORTED_RUNTIME_VERSION = "3.16.3";
    public static final String SUPPORTED_ARTIFACT_VERSION = "1.21.1-3.16.3";

    private static final Set<String> SUPPORTED = Set.of(
        SUPPORTED_RUNTIME_VERSION,
        SUPPORTED_ARTIFACT_VERSION
    );

    private IronsBattleMageVersionContract() {}

    public static boolean supports(String version) {
        return version != null && SUPPORTED.contains(version.trim());
    }
}
