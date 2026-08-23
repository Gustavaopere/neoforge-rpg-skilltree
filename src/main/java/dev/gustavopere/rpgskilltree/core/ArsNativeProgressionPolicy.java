package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/**
 * Provider-native Ars Nouveau effects derived from the shared tree and emergent Sorcerer identity.
 * Values are kept here so server and client calculations use identical rules.
 */
public final class ArsNativeProgressionPolicy {
    public static final String ARCANE_AWAKENING = "rpgskilltree:arcane_000";
    public static final String ARCANE_REGEN = "rpgskilltree:arcane_002";
    public static final String ARCANE_MANA_CAPSTONE = "rpgskilltree:arcane_037";
    public static final String SUMMONING_ENTRY = "rpgskilltree:summoning_000";

    private ArsNativeProgressionPolicy() {}

    public static int adjustMaxMana(int nativeMax, PassiveNodeProgress nodes, boolean sorcererIdentity) {
        Objects.requireNonNull(nodes);
        if (nativeMax < 0) throw new IllegalArgumentException("nativeMax must be >= 0");
        int flat = nodes.rank(ARCANE_AWAKENING) * 20
            + nodes.rank(ARCANE_MANA_CAPSTONE) * 35;
        double multiplier = sorcererIdentity ? 1.10D : 1.0D;
        return Math.max(0, (int) Math.round((nativeMax + flat) * multiplier));
    }

    public static double adjustManaRegen(double nativeRegen, PassiveNodeProgress nodes, boolean sorcererIdentity) {
        Objects.requireNonNull(nodes);
        if (nativeRegen < 0) throw new IllegalArgumentException("nativeRegen must be >= 0");
        double multiplier = 1.0D
            + nodes.rank(ARCANE_REGEN) * 0.03D
            + (sorcererIdentity ? 0.05D : 0.0D);
        return Math.max(0.0D, nativeRegen * multiplier);
    }

    public static boolean canSummonFamiliar(PassiveNodeProgress nodes) {
        Objects.requireNonNull(nodes);
        return nodes.learned(SUMMONING_ENTRY);
    }
}
