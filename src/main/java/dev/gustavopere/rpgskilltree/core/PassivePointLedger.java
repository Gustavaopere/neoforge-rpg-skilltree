package dev.gustavopere.rpgskilltree.core;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public final class PassivePointLedger {
    private final Map<PassivePointSource, Integer> earnedBySource;
    private final int spent;

    private PassivePointLedger(Map<PassivePointSource, Integer> earnedBySource, int spent) {
        EnumMap<PassivePointSource, Integer> copy = new EnumMap<>(PassivePointSource.class);
        copy.putAll(earnedBySource);
        if (copy.values().stream().anyMatch(v -> v < 0)) throw new IllegalArgumentException("earned points must be >= 0");
        int total = copy.values().stream().mapToInt(Integer::intValue).sum();
        if (spent < 0 || spent > total) throw new IllegalArgumentException("invalid spent points");
        this.earnedBySource = Map.copyOf(copy);
        this.spent = spent;
    }

    public static PassivePointLedger empty() {
        return new PassivePointLedger(Map.of(), 0);
    }

    public static PassivePointLedger of(Map<PassivePointSource, Integer> earnedBySource, int spent) {
        Objects.requireNonNull(earnedBySource);
        return new PassivePointLedger(earnedBySource, spent);
    }

    public PassivePointLedger award(PassivePointSource source, int points) {
        Objects.requireNonNull(source);
        if (points <= 0) throw new IllegalArgumentException("award must be positive");
        EnumMap<PassivePointSource, Integer> next = new EnumMap<>(PassivePointSource.class);
        next.putAll(earnedBySource);
        next.merge(source, points, Integer::sum);
        return new PassivePointLedger(next, spent);
    }

    public PassivePointLedger spend(int points) {
        if (points <= 0 || points > available()) throw new IllegalArgumentException("cannot spend " + points + " points");
        return new PassivePointLedger(earnedBySource, spent + points);
    }

    public PassivePointLedger refund(int points) {
        if (points <= 0 || points > spent) throw new IllegalArgumentException("cannot refund " + points + " points");
        return new PassivePointLedger(earnedBySource, spent - points);
    }

    public int earned(PassivePointSource source) {
        return earnedBySource.getOrDefault(source, 0);
    }

    public Map<PassivePointSource, Integer> earnedBySource() {
        return earnedBySource;
    }

    public int totalEarned() {
        return earnedBySource.values().stream().mapToInt(Integer::intValue).sum();
    }

    public int spent() {
        return spent;
    }

    public int available() {
        return totalEarned() - spent;
    }
}
