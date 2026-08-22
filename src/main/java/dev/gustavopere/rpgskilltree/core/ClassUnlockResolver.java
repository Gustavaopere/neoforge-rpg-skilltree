package dev.gustavopere.rpgskilltree.core;

import java.util.EnumSet;
import java.util.Set;

public final class ClassUnlockResolver {
    private ClassUnlockResolver() {}

    public static ClassUnlockResult evaluate(FinalTriadProgress progress, ClassUnlockDefinition definition, int availablePassivePoints) {
        if (availablePassivePoints < 0) throw new IllegalArgumentException("availablePassivePoints must be >= 0");
        EnumSet<ProgressionDomain> missing = EnumSet.noneOf(ProgressionDomain.class);
        for (ProgressionDomain domain : definition.requiredCompletedDomains()) {
            if (!progress.complete(domain)) missing.add(domain);
        }
        boolean triadsMet = missing.isEmpty();
        int bridgeCost = definition.adjacentConfluence() ? 0 : definition.nonAdjacentBridgeCost();
        int missingBridge = Math.max(0, bridgeCost - availablePassivePoints);
        return new ClassUnlockResult(triadsMet && missingBridge == 0, triadsMet, bridgeCost, missingBridge, Set.copyOf(missing));
    }
}
