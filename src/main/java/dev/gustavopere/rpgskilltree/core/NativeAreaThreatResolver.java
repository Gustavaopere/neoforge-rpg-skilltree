package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Pure deterministic Native Area Level composer with explicit provenance and checked arithmetic. */
public final class NativeAreaThreatResolver {
    private NativeAreaThreatResolver() {}

    public static NativeAreaLevelBreakdown resolve(TerritoryKey territoryKey, NativeAreaLevelPlan plan) {
        Objects.requireNonNull(territoryKey, "territoryKey");
        Objects.requireNonNull(plan, "plan");

        long raw = plan.baseLevel();
        for (NativeAreaLevelContribution contribution : plan.contributions()) {
            raw = Math.addExact(raw, contribution.delta());
        }
        long clamped = Math.max(0L, raw);
        long resolved = plan.overrideLevel().isPresent() ? plan.overrideLevel().getAsLong() : clamped;

        return new NativeAreaLevelBreakdown(
            territoryKey,
            plan.baseLevel(),
            plan.contributions(),
            raw,
            clamped,
            plan.overrideLevel(),
            resolved
        );
    }
}
