package dev.gustavopere.rpgskilltree.core;

import java.util.List;

/**
 * Canonical precedence of effect sources. Passive Skill Tree inline bonuses are
 * presentation-only in this mod; authoritative gameplay effects are resolved
 * from the server-side node_effects catalog and behavioral handlers.
 */
public enum NodeEffectSource {
    INLINE_BONUS(false),
    NODE_EFFECTS(true),
    BEHAVIOR_HANDLER(true);

    private static final List<NodeEffectSource> PRECEDENCE = List.of(values());

    private final boolean serverAuthoritative;

    NodeEffectSource(boolean serverAuthoritative) {
        this.serverAuthoritative = serverAuthoritative;
    }

    public boolean serverAuthoritative() {
        return serverAuthoritative;
    }

    public static List<NodeEffectSource> precedence() {
        return PRECEDENCE;
    }
}
