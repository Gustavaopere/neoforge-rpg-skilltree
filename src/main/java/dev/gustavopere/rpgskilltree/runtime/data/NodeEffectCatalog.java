package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.NodeAttributeEffect;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class NodeEffectCatalog {
    static final class PreparedSnapshot {
        private final List<NodeAttributeEffect> attributeEffects;

        private PreparedSnapshot(List<NodeAttributeEffect> attributeEffects) {
            this.attributeEffects = List.copyOf(attributeEffects);
        }
    }

    private static volatile List<NodeAttributeEffect> attributeEffects = List.of();
    private static volatile List<NodeAttributeEffect> clearableAttributeEffects = List.of();

    private NodeEffectCatalog() {}

    public static synchronized void replace(Collection<NodeAttributeEffect> next) {
        publish(prepare(next));
    }

    static PreparedSnapshot prepare(Collection<NodeAttributeEffect> next) {
        Objects.requireNonNull(next);
        Map<String, NodeAttributeEffect> currentById = new LinkedHashMap<>();
        for (NodeAttributeEffect effect : next) {
            Objects.requireNonNull(effect, "node attribute effect");
            if (currentById.put(effect.effectId(), effect) != null) {
                throw new IllegalArgumentException("duplicate node effect id: " + effect.effectId());
            }
        }
        List<NodeAttributeEffect> sorted = currentById.values().stream()
            .sorted(Comparator.comparing(NodeAttributeEffect::effectId))
            .toList();
        return new PreparedSnapshot(sorted);
    }

    static synchronized void publish(PreparedSnapshot snapshot) {
        Objects.requireNonNull(snapshot, "snapshot");
        Map<ClearableKey, NodeAttributeEffect> clearable = new LinkedHashMap<>();
        clearableAttributeEffects.forEach(effect -> clearable.put(ClearableKey.of(effect), effect));
        snapshot.attributeEffects.forEach(effect -> clearable.put(ClearableKey.of(effect), effect));
        attributeEffects = snapshot.attributeEffects;
        clearableAttributeEffects = clearable.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .map(Map.Entry::getValue)
            .toList();
    }

    public static List<NodeAttributeEffect> attributeEffects() {
        return attributeEffects;
    }

    public static List<NodeAttributeEffect> clearableAttributeEffects() {
        return clearableAttributeEffects;
    }

    private record ClearableKey(String effectId, String attributeId) implements Comparable<ClearableKey> {
        private ClearableKey {
            Objects.requireNonNull(effectId);
            Objects.requireNonNull(attributeId);
        }

        private static ClearableKey of(NodeAttributeEffect effect) {
            return new ClearableKey(effect.effectId(), effect.attributeId());
        }

        @Override
        public int compareTo(ClearableKey other) {
            int byEffectId = effectId.compareTo(other.effectId);
            return byEffectId != 0 ? byEffectId : attributeId.compareTo(other.attributeId);
        }
    }
}
