package dev.gustavopere.rpgskilltree.compendium.provider.loot;

public record LootConditionSummary(String kind, String detail) {
    public LootConditionSummary {
        if (kind == null || kind.trim().isEmpty()) throw new IllegalArgumentException("loot condition kind must not be blank");
        kind = kind.trim();
        detail = detail == null || detail.trim().isEmpty() ? null : detail.trim();
    }

    public static LootConditionSummary playerKill() {
        return new LootConditionSummary("PLAYER_KILL", "minecraft:killed_by_player");
    }

    public static LootConditionSummary looting() {
        return new LootConditionSummary("LOOTING", "minecraft:looting_enchant");
    }

    public static LootConditionSummary unsupported(String id) {
        return new LootConditionSummary("UNSUPPORTED", id);
    }
}
