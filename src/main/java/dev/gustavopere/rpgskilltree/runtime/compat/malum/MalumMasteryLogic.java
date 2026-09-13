package dev.gustavopere.rpgskilltree.runtime.compat.malum;

import java.util.List;

/** Pure policy checks for Malum mastery authority and confirmed spirit evidence. */
final class MalumMasteryLogic {
    private MalumMasteryLogic() {
    }

    static boolean isEligiblePlayer(boolean fakePlayer, boolean creative, boolean spectator) {
        return !fakePlayer && !creative && !spectator;
    }

    static boolean hasConfirmedSpiritEvidence(List<String> spiritItemIds, int totalSpirits) {
        return totalSpirits > 0 && !spiritItemIds.isEmpty();
    }
}
