package dev.gustavopere.rpgskilltree.core;

import java.util.Collection;
import java.util.Objects;

/** Applies normalized mastery awards to the immutable mastery state. */
public final class MasteryAwardService {
    private MasteryAwardService() {}

    public static MasteryState apply(MasteryState state, Collection<MasteryAward> awards) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(awards);

        MasteryState current = state;
        for (MasteryAward award : awards) {
            Objects.requireNonNull(award);
            current = current.award(award.laneId(), award.experience());
        }
        return current;
    }
}
