package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Time-bounded hostility memory for ecological disguises.
 *
 * <p>This value object is deliberately independent from save serialization. Runtime
 * adapters can keep it per player without changing progression save format.</p>
 */
public record MorphHostilityMemory(Map<String, Long> compromisedUntilMillis) {
    public MorphHostilityMemory {
        Objects.requireNonNull(compromisedUntilMillis);
        Map<String, Long> copy = new HashMap<>();
        for (Map.Entry<String, Long> entry : compromisedUntilMillis.entrySet()) {
            String faction = entry.getKey();
            Long until = entry.getValue();
            if (faction == null || faction.isBlank()) throw new IllegalArgumentException("blank hostility faction");
            if (until == null || until < 0L) throw new IllegalArgumentException("invalid hostility expiry for " + faction);
            copy.put(faction, until);
        }
        compromisedUntilMillis = Map.copyOf(copy);
    }

    public static MorphHostilityMemory empty() {
        return new MorphHostilityMemory(Map.of());
    }

    public boolean compromised(String faction, long nowMillis) {
        if (faction == null || faction.isBlank()) return false;
        Long until = compromisedUntilMillis.get(faction);
        return until != null && until > nowMillis;
    }

    public boolean compromisesAny(Set<String> factions, long nowMillis) {
        Objects.requireNonNull(factions);
        return factions.stream().anyMatch(faction -> compromised(faction, nowMillis));
    }

    public MorphHostilityMemory compromise(Set<String> factions, long nowMillis, long durationMillis) {
        Objects.requireNonNull(factions);
        if (nowMillis < 0L) throw new IllegalArgumentException("nowMillis must be >= 0");
        if (durationMillis <= 0L) throw new IllegalArgumentException("durationMillis must be > 0");
        long expiry;
        try {
            expiry = Math.addExact(nowMillis, durationMillis);
        } catch (ArithmeticException overflow) {
            expiry = Long.MAX_VALUE;
        }
        Map<String, Long> next = new HashMap<>(prune(nowMillis).compromisedUntilMillis());
        for (String faction : factions) {
            if (faction == null || faction.isBlank()) throw new IllegalArgumentException("blank hostility faction");
            next.merge(faction, expiry, Math::max);
        }
        return new MorphHostilityMemory(next);
    }

    public MorphHostilityMemory prune(long nowMillis) {
        if (nowMillis < 0L) throw new IllegalArgumentException("nowMillis must be >= 0");
        Map<String, Long> active = new HashMap<>();
        compromisedUntilMillis.forEach((faction, until) -> {
            if (until > nowMillis) active.put(faction, until);
        });
        return active.size() == compromisedUntilMillis.size() ? this : new MorphHostilityMemory(active);
    }
}
