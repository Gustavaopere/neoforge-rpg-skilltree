package dev.gustavopere.rpgskilltree.itemization.domain;

import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/** Persisted reference to a modifier definition plus its definitive numeric rolls. */
public record RolledModifier(ResourceLocation definitionId, Map<String, Double> rolls) {
    public RolledModifier {
        Objects.requireNonNull(definitionId, "definitionId");
        Objects.requireNonNull(rolls, "rolls");

        LinkedHashMap<String, Double> copy = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : rolls.entrySet()) {
            String key = Objects.requireNonNull(entry.getKey(), "roll key").trim();
            if (key.isEmpty()) {
                throw new IllegalArgumentException("roll key must not be blank");
            }
            Double value = Objects.requireNonNull(entry.getValue(), "roll value");
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("roll value must be finite: " + key);
            }
            if (copy.put(key, value) != null) {
                throw new IllegalArgumentException("duplicate roll key: " + key);
            }
        }
        rolls = Map.copyOf(copy);
    }
}
