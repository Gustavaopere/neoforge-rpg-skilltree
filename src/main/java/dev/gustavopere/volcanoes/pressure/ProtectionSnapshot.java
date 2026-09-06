package dev.gustavopere.volcanoes.pressure;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Resolved immutable capability candidates for one equipment snapshot. */
public final class ProtectionSnapshot {
    private final Map<ProtectionCapability, List<ProtectionContribution>> candidates;

    /** Backward-compatible single-winner constructor used by older tests/callers. */
    ProtectionSnapshot(Map<ProtectionCapability, ProtectionContribution> winners) {
        Objects.requireNonNull(winners, "winners");
        EnumMap<ProtectionCapability, List<ProtectionContribution>> converted =
                new EnumMap<>(ProtectionCapability.class);
        for (Map.Entry<ProtectionCapability, ProtectionContribution> entry : winners.entrySet()) {
            converted.put(
                    Objects.requireNonNull(entry.getKey(), "capability"),
                    List.of(Objects.requireNonNull(entry.getValue(), "contribution")));
        }
        this.candidates = immutableCandidates(converted);
    }

    private ProtectionSnapshot(Map<ProtectionCapability, List<ProtectionContribution>> candidates, boolean ignored) {
        this.candidates = immutableCandidates(candidates);
    }

    static ProtectionSnapshot fromCandidates(Map<ProtectionCapability, List<ProtectionContribution>> candidates) {
        return new ProtectionSnapshot(Objects.requireNonNull(candidates, "candidates"), true);
    }

    /**
     * Returns the strongest advertised rating in this immutable equipment snapshot.
     *
     * <p>This method does not activate or debit resource-backed equipment and therefore must not be used as
     * proof that a consumable capability is currently usable. Runtime consumers that need actual protection
     * or oxygen availability must open one shared {@link ProtectionUseSession} with {@link #beginUpdate()} and
     * use {@link ProtectionUseSession#activatedRating(ProtectionCapability)} or its demand-aware overload.</p>
     */
    public double rating(ProtectionCapability capability) {
        Objects.requireNonNull(capability, "capability");
        List<ProtectionContribution> ordered = candidates.get(capability);
        if (ordered == null || ordered.isEmpty()) {
            return 0.0;
        }
        return ordered.getFirst().ratings().getOrDefault(capability, 0.0);
    }

    /** Opens the per-update activation/debit transaction for this candidate snapshot. */
    public ProtectionUseSession beginUpdate() {
        return new ProtectionUseSession(this);
    }

    ProtectionContribution winner(ProtectionCapability capability) {
        List<ProtectionContribution> ordered = candidates(capability);
        return ordered.isEmpty() ? null : ordered.getFirst();
    }

    List<ProtectionContribution> candidates(ProtectionCapability capability) {
        Objects.requireNonNull(capability, "capability");
        return candidates.getOrDefault(capability, List.of());
    }

    private static Map<ProtectionCapability, List<ProtectionContribution>> immutableCandidates(
            Map<ProtectionCapability, List<ProtectionContribution>> source
    ) {
        Objects.requireNonNull(source, "source");
        EnumMap<ProtectionCapability, List<ProtectionContribution>> copy =
                new EnumMap<>(ProtectionCapability.class);
        for (Map.Entry<ProtectionCapability, List<ProtectionContribution>> entry : source.entrySet()) {
            ProtectionCapability capability = Objects.requireNonNull(entry.getKey(), "capability");
            List<ProtectionContribution> raw = Objects.requireNonNull(entry.getValue(), "candidate list");
            ArrayList<ProtectionContribution> entries = new ArrayList<>(raw.size());
            for (ProtectionContribution contribution : raw) {
                entries.add(Objects.requireNonNull(contribution, "candidate contribution"));
            }
            copy.put(capability, List.copyOf(entries));
        }
        return Map.copyOf(copy);
    }
}
