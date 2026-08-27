package dev.gustavopere.rpgskilltree.core;

/** Classification of an attempted authoritative progression-rules replacement. */
public enum ProgressionRulesTransition {
    INITIAL_INSTALL,
    IDENTICAL_RELOAD,
    SAME_VERSION_CONTENT_CHANGED,
    VERSION_CHANGED,
    RULES_ID_CHANGED
}
