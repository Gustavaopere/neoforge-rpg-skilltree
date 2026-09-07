package dev.gustavopere.rpgskilltree.runtime.compat.malum;

import java.util.Objects;

public final class MalumVersionContract {
    public static final String SUPPORTED_VERSION = "1.8.2";

    private MalumVersionContract() {
    }

    public static boolean supports(String version) {
        return Objects.equals(SUPPORTED_VERSION, version);
    }
}
