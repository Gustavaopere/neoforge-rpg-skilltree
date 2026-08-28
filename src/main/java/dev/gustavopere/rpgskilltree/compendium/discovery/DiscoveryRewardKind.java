package dev.gustavopere.rpgskilltree.compendium.discovery;

/** Reward kinds emitted by discovery criteria. Runtime bridges decide which kinds are currently executable. */
public enum DiscoveryRewardKind {
    CHARACTER_XP,
    VANILLA_XP,
    MASTERY_XP,
    CORE_POINTS,
    MAIN_PERK_BUDGET,
    COMMAND,
    ADVANCEMENT,
    ITEM
}
