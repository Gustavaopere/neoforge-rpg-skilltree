package dev.gustavopere.rpgskilltree.core;

/**
 * Supplies the auditable Native Area plan for one stable territory key.
 *
 * <p>Pure sources such as dimension, distance/noise and datapack tags should be
 * recomputed deterministically instead of persisted per cell. Providers backed by
 * genuinely stateful exploration/milestone data own persistence of that source state
 * and expose its current snapshot through this boundary.</p>
 */
@FunctionalInterface
public interface NativeAreaLevelPlanProvider {
    NativeAreaLevelPlan plan(TerritoryKey territoryKey);
}
