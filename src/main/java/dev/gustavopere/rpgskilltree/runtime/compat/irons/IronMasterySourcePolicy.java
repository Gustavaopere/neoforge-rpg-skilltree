package dev.gustavopere.rpgskilltree.runtime.compat.irons;

/** Eligibility boundary for Iron's semantic mastery awards. */
interface IronMasterySourcePolicy {
    static boolean counts(boolean creative, boolean spectator, boolean fakePlayer, String castSource) {
        if (creative || spectator || fakePlayer) return false;
        return "SPELLBOOK".equals(castSource) || "SCROLL".equals(castSource);
    }
}
