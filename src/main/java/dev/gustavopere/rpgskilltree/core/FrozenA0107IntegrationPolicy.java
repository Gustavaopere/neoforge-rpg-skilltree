package dev.gustavopere.rpgskilltree.core;

/** Explicit fail-closed boundary while certified P-0035 is developed on its dedicated branch. */
public final class FrozenA0107IntegrationPolicy {
    private FrozenA0107IntegrationPolicy() {}

    public static boolean providerCertified() { return false; }

    public static double maximumConvertibleFraction() { return 0.0D; }
}
