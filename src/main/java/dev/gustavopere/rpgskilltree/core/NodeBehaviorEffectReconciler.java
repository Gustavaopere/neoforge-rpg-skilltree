package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/** Pure planner for idempotent behavioral-effect transitions. */
public final class NodeBehaviorEffectReconciler {
    private NodeBehaviorEffectReconciler() {}

    public static NodeBehaviorReconciliation reconcile(
        Collection<ResolvedNodeBehaviorEffect> applied,
        Collection<ResolvedNodeBehaviorEffect> desired,
        Predicate<String> handlerAvailable
    ) {
        Objects.requireNonNull(applied, "applied");
        Objects.requireNonNull(desired, "desired");
        Objects.requireNonNull(handlerAvailable, "handlerAvailable");

        Map<String, ResolvedNodeBehaviorEffect> appliedById = index(applied, "applied");
        Map<String, ResolvedNodeBehaviorEffect> desiredById = new LinkedHashMap<>();
        for (ResolvedNodeBehaviorEffect effect : desired) {
            Objects.requireNonNull(effect, "desired effect");
            if (!handlerAvailable.test(effect.handlerId())) continue;
            if (desiredById.put(effect.effectId(), effect) != null) {
                throw new IllegalArgumentException("duplicate desired behavior effect id: " + effect.effectId());
            }
        }

        var removals = new ArrayList<ResolvedNodeBehaviorEffect>();
        for (ResolvedNodeBehaviorEffect previous : appliedById.values()) {
            ResolvedNodeBehaviorEffect next = desiredById.get(previous.effectId());
            if (!previous.equals(next)) removals.add(previous);
        }

        var applications = new ArrayList<ResolvedNodeBehaviorEffect>();
        for (ResolvedNodeBehaviorEffect next : desiredById.values()) {
            ResolvedNodeBehaviorEffect previous = appliedById.get(next.effectId());
            if (!next.equals(previous)) applications.add(next);
        }

        Comparator<ResolvedNodeBehaviorEffect> byEffectId = Comparator.comparing(ResolvedNodeBehaviorEffect::effectId);
        removals.sort(byEffectId);
        applications.sort(byEffectId);
        return new NodeBehaviorReconciliation(removals, applications);
    }

    private static Map<String, ResolvedNodeBehaviorEffect> index(
        Collection<ResolvedNodeBehaviorEffect> effects,
        String label
    ) {
        Map<String, ResolvedNodeBehaviorEffect> byId = new LinkedHashMap<>();
        for (ResolvedNodeBehaviorEffect effect : effects) {
            Objects.requireNonNull(effect, label + " effect");
            if (byId.put(effect.effectId(), effect) != null) {
                throw new IllegalArgumentException("duplicate " + label + " behavior effect id: " + effect.effectId());
            }
        }
        return byId;
    }
}
