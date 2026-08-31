package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/** Pure deterministic selection/safety rules for battle-mage spell profiles. */
public final class BattleMageSpellPolicy {
    private static final Comparator<BattleMageSpellProfile> ORDER =
        Comparator.comparingInt(BattleMageSpellProfile::priority).reversed()
            .thenComparing(BattleMageSpellProfile::spellId);

    private BattleMageSpellPolicy() {}

    public static boolean isSupported(BattleMageSpellProfile profile) {
        return profile != null;
    }

    public static List<BattleMageSpellProfile> orderCandidates(List<BattleMageSpellProfile> profiles) {
        Objects.requireNonNull(profiles, "profiles");
        return profiles.stream()
            .filter(Objects::nonNull)
            .sorted(ORDER)
            .toList();
    }

    public static boolean isAreaSafe(BattleMageSpellProfile profile, boolean protectedAllyInRadius) {
        if (profile == null) return false;
        if (profile.targetMode() != BattleMageTargetMode.HOSTILE_AREA) return true;
        if (!protectedAllyInRadius) return true;
        return profile.allySafe() || profile.friendlyFireRadius() <= 0.0;
    }
}
