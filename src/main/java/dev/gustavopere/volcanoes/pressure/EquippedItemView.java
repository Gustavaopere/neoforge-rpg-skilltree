package dev.gustavopere.volcanoes.pressure;

import java.util.Objects;
import java.util.Set;

/** Loader-neutral equipment view for armor, accessory/Curios and integration adapters. */
public record EquippedItemView(String slot, String itemId, Set<String> tags) {
    public EquippedItemView {
        slot = requireNonBlank(slot, "slot");
        itemId = requireNonBlank(itemId, "itemId");
        Objects.requireNonNull(tags, "tags");
        tags = Set.copyOf(tags);
        if (tags.stream().anyMatch(tag -> tag == null || tag.isBlank())) {
            throw new IllegalArgumentException("tags must not contain null or blank values");
        }
    }

    private static String requireNonBlank(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }
}
