package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Persisted payload associated with one recent replay-safe mastery emission. */
public record MasteryAwardReceipt(String laneId, int experience) {
    public MasteryAwardReceipt {
        Objects.requireNonNull(laneId, "laneId");
        if (laneId.isBlank()) throw new IllegalArgumentException("mastery receipt lane must not be blank");
        if (experience <= 0) throw new IllegalArgumentException("mastery receipt experience must be positive");
    }

    public static MasteryAwardReceipt from(MasteryAward award) {
        Objects.requireNonNull(award, "award");
        return new MasteryAwardReceipt(award.laneId(), award.experience());
    }
}
