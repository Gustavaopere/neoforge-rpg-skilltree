package dev.gustavopere.rpgskilltree.runtime.compat.ars;

/** Exact audited Ars Nouveau version gate for Stage 06.04. */
public final class ArsNouveauVersionContract {
    public static final String SUPPORTED_VERSION = "5.13.1";

    private ArsNouveauVersionContract() {}

    public static boolean supports(String version) {
        return SUPPORTED_VERSION.equals(version);
    }
}
