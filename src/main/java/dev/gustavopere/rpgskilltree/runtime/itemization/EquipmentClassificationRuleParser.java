package dev.gustavopere.rpgskilltree.runtime.itemization;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.gustavopere.rpgskilltree.itemization.classification.EligibilityOverride;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentCategory;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentClassificationRule;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

public final class EquipmentClassificationRuleParser {
    private EquipmentClassificationRuleParser() {}

    public static EquipmentClassificationRule parse(ResourceLocation id, JsonObject root) {
        if (id == null) {
            throw new IllegalArgumentException("classification rule id cannot be null");
        }
        if (root == null) {
            throw new IllegalArgumentException("classification rule json cannot be null");
        }

        int priority = root.has("priority") ? root.get("priority").getAsInt() : 0;
        Set<ResourceLocation> items = resourceLocations(root, "items");
        Set<ResourceLocation> tags = resourceLocations(root, "tags");
        EligibilityOverride eligibility = root.has("eligibility")
            ? enumValue(EligibilityOverride.class, root.get("eligibility").getAsString(), "eligibility")
            : EligibilityOverride.INHERIT;
        boolean replaceCategories = root.has("replace_categories") && root.get("replace_categories").getAsBoolean();
        Set<EquipmentCategory> addCategories = categories(root, "add_categories");
        Set<EquipmentCategory> removeCategories = categories(root, "remove_categories");

        return new EquipmentClassificationRule(
            id,
            priority,
            items,
            tags,
            eligibility,
            replaceCategories,
            addCategories,
            removeCategories
        );
    }

    private static Set<ResourceLocation> resourceLocations(JsonObject root, String field) {
        if (!root.has(field)) {
            return Set.of();
        }
        JsonArray values = requireArray(root, field);
        Set<ResourceLocation> result = new HashSet<>();
        for (JsonElement value : values) {
            try {
                result.add(ResourceLocation.parse(value.getAsString()));
            } catch (RuntimeException exception) {
                throw new IllegalArgumentException("invalid resource location in '" + field + "': " + value, exception);
            }
        }
        return Set.copyOf(result);
    }

    private static Set<EquipmentCategory> categories(JsonObject root, String field) {
        if (!root.has(field)) {
            return Set.of();
        }
        JsonArray values = requireArray(root, field);
        EnumSet<EquipmentCategory> result = EnumSet.noneOf(EquipmentCategory.class);
        for (JsonElement value : values) {
            result.add(enumValue(EquipmentCategory.class, value.getAsString(), field));
        }
        return Set.copyOf(result);
    }

    private static JsonArray requireArray(JsonObject root, String field) {
        JsonElement value = root.get(field);
        if (!value.isJsonArray()) {
            throw new IllegalArgumentException("'" + field + "' must be an array");
        }
        return value.getAsJsonArray();
    }

    private static <E extends Enum<E>> E enumValue(Class<E> type, String raw, String field) {
        try {
            return Enum.valueOf(type, raw);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("invalid " + field + " value '" + raw + "'", exception);
        }
    }
}
