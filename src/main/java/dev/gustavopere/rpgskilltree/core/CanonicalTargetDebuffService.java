package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** Canonical transient target-side debuffs that must not be serialized in player progression. */
public final class CanonicalTargetDebuffService {
    private final Map<String, LinkedHashMap<String, DesyncContribution>> desyncByTarget = new HashMap<>();

    public synchronized void applyDesync(
        String sourceActorId,
        String targetId,
        CombatPerkFinalizationPolicy.BoneBreakerEffect effect
    ) {
        requireId(sourceActorId, "sourceActorId");
        requireId(targetId, "targetId");
        Objects.requireNonNull(effect);
        if (effect.expiresAtMillis() <= 0L) throw new IllegalArgumentException("expiresAtMillis must be positive");
        desyncByTarget
            .computeIfAbsent(targetId, ignored -> new LinkedHashMap<>())
            .put(sourceActorId, new DesyncContribution(
                effect.outgoingPhysicalDamageMultiplier(),
                effect.movementSpeedMultiplier(),
                effect.expiresAtMillis()
            ));
    }

    /**
     * Returns the strongest still-live Descompasso without multiplying multiple players' contributions.
     * Expiry uses the longest live contribution so one source disappearing cannot erase another source's effect.
     */
    public synchronized Optional<DesyncSnapshot> desync(String targetId, long nowMillis) {
        requireId(targetId, "targetId");
        if (nowMillis < 0L) throw new IllegalArgumentException("nowMillis must be non-negative");
        LinkedHashMap<String, DesyncContribution> contributions = desyncByTarget.get(targetId);
        if (contributions == null) return Optional.empty();
        contributions.entrySet().removeIf(entry -> entry.getValue().expiresAtMillis() <= nowMillis);
        if (contributions.isEmpty()) {
            desyncByTarget.remove(targetId);
            return Optional.empty();
        }

        double damageMultiplier = 1.0D;
        double movementMultiplier = 1.0D;
        long expiresAt = 0L;
        for (DesyncContribution contribution : contributions.values()) {
            damageMultiplier = Math.min(damageMultiplier, contribution.outgoingPhysicalDamageMultiplier());
            movementMultiplier = Math.min(movementMultiplier, contribution.movementSpeedMultiplier());
            expiresAt = Math.max(expiresAt, contribution.expiresAtMillis());
        }
        return Optional.of(new DesyncSnapshot(damageMultiplier, movementMultiplier, expiresAt));
    }

    public synchronized void clearSource(String sourceActorId) {
        requireId(sourceActorId, "sourceActorId");
        Iterator<Map.Entry<String, LinkedHashMap<String, DesyncContribution>>> iterator =
            desyncByTarget.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, LinkedHashMap<String, DesyncContribution>> entry = iterator.next();
            entry.getValue().remove(sourceActorId);
            if (entry.getValue().isEmpty()) iterator.remove();
        }
    }

    public synchronized void clearTarget(String targetId) {
        desyncByTarget.remove(requireId(targetId, "targetId"));
    }

    public synchronized void clear() {
        desyncByTarget.clear();
    }

    public record DesyncSnapshot(
        double outgoingPhysicalDamageMultiplier,
        double movementSpeedMultiplier,
        long expiresAtMillis
    ) {
        public DesyncSnapshot {
            requireMultiplier(outgoingPhysicalDamageMultiplier, "outgoingPhysicalDamageMultiplier");
            requireMultiplier(movementSpeedMultiplier, "movementSpeedMultiplier");
            if (expiresAtMillis <= 0L) throw new IllegalArgumentException("expiresAtMillis must be positive");
        }
    }

    private record DesyncContribution(
        double outgoingPhysicalDamageMultiplier,
        double movementSpeedMultiplier,
        long expiresAtMillis
    ) {}

    private static String requireId(String value, String name) {
        Objects.requireNonNull(value);
        if (value.isBlank()) throw new IllegalArgumentException(name + " must not be blank");
        return value;
    }

    private static void requireMultiplier(double value, String name) {
        if (!Double.isFinite(value) || value <= 0.0D || value > 1.0D) {
            throw new IllegalArgumentException(name + " must be finite in (0,1]");
        }
    }
}
