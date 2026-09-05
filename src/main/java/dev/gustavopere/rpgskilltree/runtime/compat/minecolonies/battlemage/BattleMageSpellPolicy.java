package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/** Pure deterministic selection/safety rules for battle-mage spell profiles. */
public final class BattleMageSpellPolicy {
    private BattleMageSpellPolicy() {}

    /** Stable candidate identity; book index preserves the actual spellbook order. */
    public record Candidate(BattleMageSpellProfile profile, int bookIndex) {
        public Candidate {
            Objects.requireNonNull(profile, "profile");
            if (bookIndex < 0) throw new IllegalArgumentException("bookIndex must be >= 0");
        }
    }

    public static boolean isSupported(BattleMageSpellProfile profile) {
        return profile != null;
    }

    /** Initial runtime excludes ally-target handlers and all world-effect profiles fail-closed. */
    public static boolean isRuntimeSupported(BattleMageSpellProfile profile) {
        if (profile == null || profile.worldEffect()) return false;
        return switch (profile.targetMode()) {
            case SELF, HOSTILE_ENTITY, HOSTILE_AREA -> true;
            case ALLY_ENTITY -> false;
        };
    }

    public static boolean inRange(BattleMageSpellProfile profile, double distance) {
        return profile != null
            && Double.isFinite(distance)
            && distance >= profile.minRange()
            && distance <= profile.maxRange();
    }

    /**
     * Tactical order is deterministic: urgent SELF first, hostile profiles next, unsupported ally
     * lane after that, then non-urgent SELF. Within a lane profile priority wins, followed by real
     * spellbook position and spell id.
     */
    public static List<Candidate> orderTacticalCandidates(List<Candidate> candidates, boolean selfCritical) {
        Objects.requireNonNull(candidates, "candidates");
        return candidates.stream()
            .filter(Objects::nonNull)
            .sorted(
                Comparator.comparingInt((Candidate candidate) -> tacticalLane(candidate.profile(), selfCritical))
                    .thenComparing(Comparator.comparingInt((Candidate candidate) -> candidate.profile().priority()).reversed())
                    .thenComparingInt(Candidate::bookIndex)
                    .thenComparing(candidate -> candidate.profile().spellId().toString())
            )
            .toList();
    }

    /**
     * Provider-free friendly-fire floor. A missing profile is unknown and therefore fails closed.
     * Provider adapters may only widen this radius when their runtime spell footprint is larger.
     */
    public static double configuredFriendlyFireRadius(BattleMageSpellProfile profile) {
        return profile == null ? Double.POSITIVE_INFINITY : profile.friendlyFireRadius();
    }

    public static boolean isAreaSafe(BattleMageSpellProfile profile, boolean protectedAllyInRadius) {
        if (profile == null) return false;
        if (profile.targetMode() != BattleMageTargetMode.HOSTILE_AREA) return true;
        if (profile.allySafe()) return true;
        if (profile.friendlyFireRadius() <= 0.0) return false;
        return !protectedAllyInRadius;
    }

    private static int tacticalLane(BattleMageSpellProfile profile, boolean selfCritical) {
        if (selfCritical && profile.targetMode() == BattleMageTargetMode.SELF) return 0;
        if (profile.targetMode() == BattleMageTargetMode.HOSTILE_ENTITY
            || profile.targetMode() == BattleMageTargetMode.HOSTILE_AREA) return 1;
        if (profile.targetMode() == BattleMageTargetMode.ALLY_ENTITY) return 2;
        return 3;
    }
}
