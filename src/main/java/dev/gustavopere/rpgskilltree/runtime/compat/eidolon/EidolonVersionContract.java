package dev.gustavopere.rpgskilltree.runtime.compat.eidolon;

import java.util.Objects;

public final class EidolonVersionContract {
    public static final String SUPPORTED_VERSION = "0.5.0.2";

    private EidolonVersionContract() {
    }

    public static boolean supports(String version) {
        return Objects.equals(SUPPORTED_VERSION, version);
    }
}
