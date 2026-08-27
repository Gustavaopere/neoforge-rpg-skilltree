package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Resolves canonical provider values into RPG effective values using explicit per-stat policies. */
public final class RpgEffectiveStatsService {
    private RpgEffectiveStatsService() {}

    public static RpgEffectiveStats resolve(
        long progressionLevel,
        CanonicalStatSnapshot providerSnapshot,
        Map<CanonicalStatKey, EffectiveStatPolicy> policies
    ) {
        if (progressionLevel < 0L) {
            throw new IllegalArgumentException("progressionLevel must be non-negative");
        }
        Objects.requireNonNull(providerSnapshot, "providerSnapshot");
        Objects.requireNonNull(policies, "policies");

        HashMap<CanonicalStatKey, BigDecimal> resolved = new HashMap<>();
        for (Map.Entry<CanonicalStatKey, BigDecimal> entry : providerSnapshot.values().entrySet()) {
            CanonicalStatKey key = entry.getKey();
            EffectiveStatPolicy policy = policies.get(key);
            if (policy == null) {
                throw new IllegalStateException("missing effective stat policy: " + key.serializedId());
            }
            BigDecimal effective = policy.resolve(new EffectiveStatContext(key, entry.getValue(), progressionLevel));
            if (effective == null) {
                throw new IllegalStateException("effective stat policy returned null: " + key.serializedId());
            }
            resolved.put(key, effective);
        }
        return new RpgEffectiveStats(progressionLevel, resolved);
    }
}
