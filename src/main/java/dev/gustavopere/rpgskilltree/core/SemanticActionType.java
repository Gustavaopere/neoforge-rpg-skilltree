package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Stable, extensible semantic action identifier used between adapters and XP policy. */
public record SemanticActionType(String id) {
    public static final SemanticActionType ADVANCEMENT_COMPLETED = canonical("advancement_completed");
    public static final SemanticActionType QUEST_COMPLETED = canonical("quest_completed");
    public static final SemanticActionType HOSTILE_KILLED = canonical("hostile_killed");
    public static final SemanticActionType PASSIVE_KILLED = canonical("passive_killed");
    public static final SemanticActionType BOSS_DEFEATED = canonical("boss_defeated");
    public static final SemanticActionType ORE_MINED = canonical("ore_mined");
    public static final SemanticActionType BLOCK_PROCESSED = canonical("block_processed");
    public static final SemanticActionType ITEM_CRAFTED = canonical("item_crafted");
    public static final SemanticActionType STRUCTURE_BUILT = canonical("structure_built");
    public static final SemanticActionType BIOME_DISCOVERED = canonical("biome_discovered");
    public static final SemanticActionType STRUCTURE_DISCOVERED = canonical("structure_discovered");
    public static final SemanticActionType DIMENSION_DISCOVERED = canonical("dimension_discovered");
    public static final SemanticActionType MACHINE_OPERATION_COMPLETED = canonical("machine_operation_completed");
    public static final SemanticActionType FARMING_ACTION = canonical("farming_action");

    public SemanticActionType {
        Objects.requireNonNull(id, "id");
        if (id.isBlank()) throw new IllegalArgumentException("semantic action type id must not be blank");
    }

    public static SemanticActionType canonical(String path) {
        if (path == null || path.isBlank()) throw new IllegalArgumentException("canonical action path must not be blank");
        return new SemanticActionType("rpgskilltree:" + path);
    }
}
