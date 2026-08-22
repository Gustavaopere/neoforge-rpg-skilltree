package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;

public record MasteryState(Map<String,Integer> experience) {
    public MasteryState {
        experience = Map.copyOf(experience);
        if (experience.keySet().stream().anyMatch(k -> k == null || k.isBlank())) throw new IllegalArgumentException("mastery lane must not be blank");
        if (experience.values().stream().anyMatch(v -> v == null || v < 0)) throw new IllegalArgumentException("mastery XP must be >= 0");
    }

    public static MasteryState empty() { return new MasteryState(Map.of()); }
    public static MasteryState of(Map<String,Integer> experience) { return new MasteryState(experience); }
    public int experience(String lane) { return experience.getOrDefault(lane,0); }

    public MasteryState award(String lane, int amount) {
        if (lane == null || lane.isBlank()) throw new IllegalArgumentException("mastery lane must not be blank");
        if (amount <= 0) throw new IllegalArgumentException("mastery award must be positive");
        Map<String,Integer> next = new HashMap<>(experience);
        next.merge(lane, amount, Math::addExact);
        return new MasteryState(next);
    }
}
