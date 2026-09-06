package dev.gustavopere.rpgskilltree.itemization.blacksmith.compat.productivemetalworks;

import java.util.Set;

/** Exact audited runtime boundary for the Blacksmith Productive Metalworks adapter. */
public final class ProductiveMetalworksVersionContract {
    public static final String SUPPORTED_ARTIFACT_VERSION = "1.21.1-1.15.1";

    private static final Set<String> SUPPORTED = Set.of(SUPPORTED_ARTIFACT_VERSION);

    private ProductiveMetalworksVersionContract() {}

    public static boolean supports(String version) {
        return version != null && SUPPORTED.contains(version.trim());
    }
}
