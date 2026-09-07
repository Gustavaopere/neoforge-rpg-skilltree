package dev.gustavopere.rpgskilltree.runtime.compat.eidolon;

import java.util.Objects;

/** Exact audited provider contract for the optional Eidolon Repraised mastery adapter. */
public final class EidolonVersionContract {
    public static final String SUPPORTED_VERSION = "0.5.0.2";

    private EidolonVersionContract() {
    }

    public static boolean supports(String version) {
        return Objects.equals(SUPPORTED_VERSION, version);
    }
}
