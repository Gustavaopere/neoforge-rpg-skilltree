package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;

/** Resolves the bounded reward multiplier for one persisted entity-scaling decision. */
@FunctionalInterface
public interface EntityRewardRiskPolicy {
    BigDecimal multiplier(EntityScalingState state);
}
