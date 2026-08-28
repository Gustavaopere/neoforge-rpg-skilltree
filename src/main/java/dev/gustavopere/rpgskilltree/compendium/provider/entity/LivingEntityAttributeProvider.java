package dev.gustavopere.rpgskilltree.compendium.provider.entity;

import dev.gustavopere.rpgskilltree.compendium.entity.EntityBaseAttribute;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class LivingEntityAttributeProvider {
    private LivingEntityAttributeProvider() {}

    public static Map<String, Double> toFactValues(Map<EntityBaseAttribute, Double> attributes) {
        if (attributes == null || attributes.isEmpty()) return Map.of();

        LinkedHashMap<String, Double> facts = new LinkedHashMap<>();
        attributes.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> {
                EntityBaseAttribute attribute = Objects.requireNonNull(entry.getKey(), "attribute");
                Double value = Objects.requireNonNull(entry.getValue(), "attribute value");
                if (!Double.isFinite(value)) throw new IllegalArgumentException("attribute value must be finite");
                facts.put(attribute.factKey(), value);
            });
        return Map.copyOf(facts);
    }
}
