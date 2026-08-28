package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Builds the single read-only player RPG projection from the canonical envelope. */
public final class CanonicalPlayerQueryService {
    private CanonicalPlayerQueryService() {}

    public static CanonicalPlayerSnapshot snapshot(
        CanonicalPlayerState state,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(rules, "rules");

        CoreProgressionQuerySnapshot progression = CoreProgressionQueryService.snapshot(
            state.coreProgression(),
            rules
        );
        ProgressionState compatibility = state.compatibilityProgression();

        return new CanonicalPlayerSnapshot(
            progression,
            compatibility.bossProgress(),
            compatibility.classProgression(),
            compatibility.mastery(),
            compatibility.classChoices(),
            compatibility.specializations(),
            compatibility.finalTriads(),
            compatibility.passiveNodes(),
            compatibility.discoveries()
        );
    }
}
