package dev.gustavopere.rpgskilltree.itemization.domain;

/** Causal origin of the first, definitive itemization generation. */
public enum GenerationSource {
    CRAFT,
    SMITHING,
    LOOT,
    MOB_EQUIPMENT,
    MOB_DROP,
    REWARD,
    TRADE,
    MACHINE,
    MIGRATION,
    ADMIN,
    FALLBACK
}
