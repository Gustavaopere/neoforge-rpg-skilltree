package dev.gustavopere.rpgskilltree.runtime.compat.irons;

/** Eligibility boundary for Iron's semantic mastery awards. */
final class IronMasterySourcePolicy {
    private IronMasterySourcePolicy() {}

    enum CastKind {
        SPELLBOOK,
        SCROLL,
        OTHER
    }

    static boolean counts(boolean creative, boolean spectator, boolean fakePlayer, CastKind castKind) {
        if (creative || spectator || fakePlayer) return false;
        return castKind == CastKind.SPELLBOOK || castKind == CastKind.SCROLL;
    }
}
