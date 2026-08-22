package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

public record BossRewardDefinition(String id, int points) {
    public BossRewardDefinition {
        Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("boss reward id must not be blank");
        if (points < 0) throw new IllegalArgumentException("boss reward points must be >= 0");
    }
}
