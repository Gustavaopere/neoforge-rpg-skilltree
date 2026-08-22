package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class NodeEffectResolver {
    private NodeEffectResolver() {}

    public static List<ResolvedNodeAttributeEffect> resolveAttributes(
        PassiveNodeProgress progress,
        Collection<NodeAttributeEffect> effects
    ) {
        Objects.requireNonNull(progress);
        Objects.requireNonNull(effects);
        List<ResolvedNodeAttributeEffect> resolved = new ArrayList<>();
        for (NodeAttributeEffect effect : effects) {
            int rank = progress.rank(effect.nodeId());
            if (rank <= 0) continue;
            resolved.add(new ResolvedNodeAttributeEffect(
                effect.effectId(),
                effect.nodeId(),
                effect.attributeId(),
                effect.operation(),
                effect.amountPerRank() * rank
            ));
        }
        resolved.sort(Comparator.comparing(ResolvedNodeAttributeEffect::effectId));
        return List.copyOf(resolved);
    }
}
