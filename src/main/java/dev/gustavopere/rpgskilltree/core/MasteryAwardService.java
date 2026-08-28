package dev.gustavopere.rpgskilltree.core;

import java.util.Collection;
import java.util.Objects;

/** Applies normalized replay-safe mastery awards to the immutable mastery state. */
public final class MasteryAwardService {
    private MasteryAwardService() {}

    public static MasteryState apply(MasteryState state, Collection<MasteryAward> awards) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(awards);

        MasteryState current = state;
        for (MasteryAward award : awards) {
            current = current.award(Objects.requireNonNull(award));
        }
        return current;
    }
}
