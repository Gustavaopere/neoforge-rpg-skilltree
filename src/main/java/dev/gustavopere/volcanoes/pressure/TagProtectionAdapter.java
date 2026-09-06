package dev.gustavopere.volcanoes.pressure;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Generic passive protection adapter driven by tags already resolved by the runtime bridge. */
public final class TagProtectionAdapter implements EquipmentProtectionAdapter {
    private final Map<String, Map<ProtectionCapability, Double>> mappings;

    public TagProtectionAdapter(Map<String, Map<ProtectionCapability, Double>> mappings) {
        Objects.requireNonNull(mappings, "mappings");
        LinkedHashMap<String, Map<ProtectionCapability, Double>> copy = new LinkedHashMap<>();
        for (Map.Entry<String, Map<ProtectionCapability, Double>> entry : mappings.entrySet()) {
            String tag = Objects.requireNonNull(entry.getKey(), "tag");
            if (tag.isBlank()) {
                throw new IllegalArgumentException("tag must not be blank");
            }
            Map<ProtectionCapability, Double> ratings = ProtectionContribution
                    .passive("tag-validation", Objects.requireNonNull(entry.getValue(), "ratings"))
                    .ratings();
            copy.put(tag, ratings);
        }
        this.mappings = Map.copyOf(copy);
    }

    @Override
    public List<ProtectionContribution> resolve(EquipmentProtectionContext context) {
        Objects.requireNonNull(context, "context");
        List<ProtectionContribution> contributions = new ArrayList<>();
        for (EquippedItemView item : context.equippedItems()) {
            for (String tag : item.tags()) {
                Map<ProtectionCapability, Double> ratings = mappings.get(tag);
                if (ratings != null && !ratings.isEmpty()) {
                    contributions.add(ProtectionContribution.passive(
                            "tag:" + tag + "@" + item.slot() + ":" + item.itemId(),
                            ratings));
                }
            }
        }
        return List.copyOf(contributions);
    }
}
