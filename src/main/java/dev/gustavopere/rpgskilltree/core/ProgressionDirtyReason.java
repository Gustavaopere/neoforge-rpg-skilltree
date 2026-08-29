package dev.gustavopere.rpgskilltree.core;

/**
 * Semantic reasons a committed progression mutation may require downstream work.
 *
 * <p>These are intentionally broader than packet types so runtime layers can decide
 * whether to refresh effects, recompute presentation, or coalesce owner sync.</p>
 */
public enum ProgressionDirtyReason {
    PERSISTENT_STATE,
    EFFECTS,
    CLASS_RESOLUTION,
    TREE_AVAILABILITY,
    MASTERY_DISPLAY,
    CLIENT_RULES_REVISION
}
