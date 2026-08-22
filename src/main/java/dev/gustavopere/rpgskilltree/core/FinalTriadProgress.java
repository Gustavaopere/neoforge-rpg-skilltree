package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class FinalTriadProgress {
    private final Map<ProgressionDomain, List<Integer>> ranks;

    private FinalTriadProgress(Map<ProgressionDomain, List<Integer>> ranks) {
        EnumMap<ProgressionDomain, List<Integer>> copy = new EnumMap<>(ProgressionDomain.class);
        ranks.forEach((domain, values) -> {
            if (domain == null) throw new IllegalArgumentException("final triad domain must not be null");
            if (values == null || values.size() != 3) throw new IllegalArgumentException("final triad requires exactly three capstone ranks");
            List<Integer> normalized = List.copyOf(values);
            if (normalized.stream().anyMatch(v -> v == null || v < 0 || v > 3)) {
                throw new IllegalArgumentException("final triad ranks must be in 0..3");
            }
            copy.put(domain, normalized);
        });
        this.ranks = Map.copyOf(copy);
    }

    public static FinalTriadProgress empty() {
        return new FinalTriadProgress(Map.of());
    }

    public static FinalTriadProgress of(Map<ProgressionDomain, List<Integer>> ranks) {
        return new FinalTriadProgress(ranks);
    }

    public boolean complete(ProgressionDomain domain) {
        List<Integer> values = ranks.get(domain);
        return values != null && values.stream().allMatch(v -> v == 3);
    }

    public int pointsInFinalTriad(ProgressionDomain domain) {
        return ranks.getOrDefault(domain, List.of(0, 0, 0)).stream().mapToInt(Integer::intValue).sum();
    }

    public List<Integer> ranks(ProgressionDomain domain) {
        return ranks.getOrDefault(domain, List.of(0, 0, 0));
    }

    public Map<ProgressionDomain, List<Integer>> allRanks() {
        return ranks;
    }

    public FinalTriadProgress increase(ProgressionDomain domain, int slot) {
        if (domain == null) throw new IllegalArgumentException("domain must not be null");
        if (slot < 0 || slot >= 3) throw new IllegalArgumentException("final triad slot must be in 0..2");
        List<Integer> current = ranks(domain);
        if (current.get(slot) >= 3) throw new IllegalArgumentException("final triad rank is already capped");

        EnumMap<ProgressionDomain, List<Integer>> next = new EnumMap<>(ProgressionDomain.class);
        next.putAll(ranks);
        ArrayList<Integer> updated = new ArrayList<>(current);
        updated.set(slot, updated.get(slot) + 1);
        next.put(domain, List.copyOf(updated));
        return new FinalTriadProgress(next);
    }

    public FinalTriadProgress decrease(ProgressionDomain domain, int slot, int amount) {
        if (domain == null) throw new IllegalArgumentException("domain must not be null");
        if (slot < 0 || slot >= 3) throw new IllegalArgumentException("final triad slot must be in 0..2");
        if (amount <= 0) throw new IllegalArgumentException("amount must be positive");
        List<Integer> current = ranks(domain);
        if (current.get(slot) < amount) throw new IllegalArgumentException("cannot remove more final triad ranks than invested");

        EnumMap<ProgressionDomain, List<Integer>> next = new EnumMap<>(ProgressionDomain.class);
        next.putAll(ranks);
        ArrayList<Integer> updated = new ArrayList<>(current);
        updated.set(slot, updated.get(slot) - amount);
        next.put(domain, List.copyOf(updated));
        return new FinalTriadProgress(next);
    }
}
