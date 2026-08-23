package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Set;

/** Normalized spiritual-practice action for non-spell providers such as Malum. */
public record SpiritPracticeAction(ActionOrigin origin, String provider, String actionId, Set<String> tags, int magnitude) {
    public SpiritPracticeAction {
        Objects.requireNonNull(origin);
        Objects.requireNonNull(provider);
        Objects.requireNonNull(actionId);
        tags = Set.copyOf(tags);
        if (provider.isBlank() || actionId.isBlank() || magnitude < 0) throw new IllegalArgumentException();
    }

    public String stableActionId() {
        return provider + ":" + actionId;
    }

    public SpiritPracticeAction withOrigin(ActionOrigin newOrigin) {
        return new SpiritPracticeAction(newOrigin, provider, actionId, tags, magnitude);
    }
}
