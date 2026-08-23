package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Shared gate for player-driven arcane casting across supported magic systems. */
public final class ArcaneAccessPolicy {
    public static final String ARCANE_AWAKENING_NODE = "rpgskilltree:arcane_000";

    private ArcaneAccessPolicy() {}

    public static boolean canCast(PassiveNodeProgress nodes) {
        Objects.requireNonNull(nodes);
        return nodes.learned(ARCANE_AWAKENING_NODE);
    }
}
