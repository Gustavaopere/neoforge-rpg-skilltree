package dev.gustavopere.volcanoes.pressure;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Per-update transaction that makes resource consumption idempotent by physical debit key. */
public final class ProtectionUseSession {
    private final ProtectionSnapshot snapshot;
    private final Map<String, Boolean> consumptionResults = new HashMap<>();

    ProtectionUseSession(ProtectionSnapshot snapshot) {
        this.snapshot = Objects.requireNonNull(snapshot, "snapshot");
    }

    /** Snapshot resolved once for this transaction; package-private so the Pressure core can reuse it. */
    ProtectionSnapshot snapshot() {
        return snapshot;
    }

    /** Backward-compatible boolean view: true when any candidate for the capability activated. */
    public boolean activate(ProtectionCapability capability) {
        return activatedRating(capability) > 0.0;
    }

    /** Resolves the strongest usable rating without a smaller demand target. */
    public double activatedRating(ProtectionCapability capability) {
        Objects.requireNonNull(capability, "capability");
        return resolveActivatedRating(capability, Double.POSITIVE_INFINITY);
    }

    /**
     * Resolves protection for a known required rating while avoiding pointless resource consumption.
     *
     * <p>If a passive candidate already covers the full requirement, it is preferred over a stronger
     * consumable candidate because both have the same effective applied rating for this update. If no passive
     * candidate fully covers the requirement, candidates are tried in normal strength order so a consumable
     * may still provide materially stronger protection.</p>
     *
     * <p>The selected rating is deliberately not memoized by capability because callers in the same update
     * may have different requirements. Physical resource results are memoized separately by
     * {@link ProtectionContribution#resourceDebitKey()}, so re-evaluating candidates cannot debit the same
     * tank/filter/resource twice.</p>
     */
    public double activatedRating(ProtectionCapability capability, double requiredRating) {
        Objects.requireNonNull(capability, "capability");
        if (!Double.isFinite(requiredRating) || requiredRating < 0.0) {
            throw new IllegalArgumentException("requiredRating must be finite and non-negative");
        }
        if (requiredRating == 0.0) {
            return 0.0;
        }
        return resolveActivatedRating(capability, requiredRating);
    }

    private double resolveActivatedRating(ProtectionCapability capability, double requiredRating) {
        if (Double.isFinite(requiredRating)) {
            for (ProtectionContribution contribution : snapshot.candidates(capability)) {
                double rating = contribution.ratings().getOrDefault(capability, 0.0);
                if (rating < requiredRating) {
                    break;
                }
                if (contribution.resourceConsumer().isEmpty()) {
                    return rating;
                }
            }
        }

        for (ProtectionContribution contribution : snapshot.candidates(capability)) {
            double rating = contribution.ratings().getOrDefault(capability, 0.0);
            if (rating <= 0.0) {
                continue;
            }
            if (contribution.resourceConsumer().isEmpty()) {
                return rating;
            }

            boolean consumed = consumptionResults.computeIfAbsent(
                    contribution.resourceDebitKey(),
                    ignored -> consumeFailClosed(contribution));
            if (consumed) {
                return rating;
            }
        }
        return 0.0;
    }

    private static boolean consumeFailClosed(ProtectionContribution contribution) {
        try {
            return contribution.resourceConsumer().orElseThrow().consume();
        } catch (RuntimeException | LinkageError failure) {
            return false;
        }
    }
}
