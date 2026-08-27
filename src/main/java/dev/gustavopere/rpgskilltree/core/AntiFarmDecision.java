package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Result of anti-farm evaluation before any XP policy is consulted. */
public record AntiFarmDecision(boolean allowed, String reason) {
    public AntiFarmDecision {
        Objects.requireNonNull(reason, "reason");
        if (!allowed && reason.isBlank()) throw new IllegalArgumentException("rejected anti-farm decision needs a reason");
    }

    public static AntiFarmDecision allow() {
        return new AntiFarmDecision(true, "");
    }

    public static AntiFarmDecision reject(String reason) {
        return new AntiFarmDecision(false, reason);
    }
}
