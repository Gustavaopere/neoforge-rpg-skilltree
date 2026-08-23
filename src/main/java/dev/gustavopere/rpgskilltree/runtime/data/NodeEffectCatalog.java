package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.NodeAttributeEffect;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class NodeEffectCatalog {
    private static volatile List<NodeAttributeEffect> attributeEffects = List.of();
    private static volatile List<NodeAttributeEffect> clearableAttributeEffects = List.of();

    private NodeEffectCatalog() {}

    public static synchronized void replace(Collection<NodeAttributeEffect> next) {
        Objects.requireNonNull(next);
        Map<String, NodeAttributeEffect> currentById = new LinkedHashMap<>();
        for (NodeAttributeEffect effect : next) {
            if (currentById.put(effect.effectId(), effect) != null) {
                throw new IllegalArgumentException("duplicate node effect id: " + effect.effectId());
            }
        }
        List<NodeAttributeEffect> sorted = currentById.values().stream()
            .sorted(Comparator.comparing(NodeAttributeEffect::effectId))
            .toList();

        Map<String, NodeAttributeEffect> clearable = new LinkedHashMap<>();
        clearableAttributeEffects.forEach(effect -> clearable.put(effect.effectId(), effect));
        sorted.forEach(effect -> clearable.put(effect.effectId(), effect));
        attributeEffects = List.copyOf(sorted);
        clearableAttributeEffects = List.copyOf(new ArrayList<>(clearable.values()));
    }

    public static List<NodeAttributeEffect> attributeEffects() {
        return attributeEffects;
    }

    public static List<NodeAttributeEffect> clearableAttributeEffects() {
        return clearableAttributeEffects;
    }
}
