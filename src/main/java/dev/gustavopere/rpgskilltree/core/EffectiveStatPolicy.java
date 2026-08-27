package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;

/** Stateless policy that derives one RPG effective value from provider-normalized input. */
@FunctionalInterface
public interface EffectiveStatPolicy {
    BigDecimal resolve(EffectiveStatContext context);
}
