package dev.gustavopere.rpgskilltree.runtime.itemization;

import com.google.gson.JsonElement;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentClassificationRule;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentOverrideCatalog;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

/**
 * Parses a complete datapack payload before atomically publishing a replacement catalog.
 * Any invalid entry aborts the reload and leaves the last valid snapshot active.
 */
public final class EquipmentClassificationReloadService {
    private EquipmentClassificationReloadService() {}

    public static EquipmentOverrideCatalog reload(Map<ResourceLocation, JsonElement> resources) {
        Objects.requireNonNull(resources, "resources");

        ArrayList<Map.Entry<ResourceLocation, JsonElement>> ordered = new ArrayList<>(resources.entrySet());
        ordered.sort(Comparator.comparing(entry -> entry.getKey().toString()));

        ArrayList<EquipmentClassificationRule> parsed = new ArrayList<>(ordered.size());
        for (Map.Entry<ResourceLocation, JsonElement> entry : ordered) {
            ResourceLocation id = Objects.requireNonNull(entry.getKey(), "classification resource id");
            JsonElement value = Objects.requireNonNull(entry.getValue(), "classification resource json");
            if (!value.isJsonObject()) {
                throw new IllegalArgumentException("classification rule '" + id + "' must be a JSON object");
            }
            parsed.add(EquipmentClassificationRuleParser.parse(id, value.getAsJsonObject()));
        }

        EquipmentOverrideCatalog complete = new EquipmentOverrideCatalog(parsed);
        EquipmentClassificationOverrides.replace(complete);
        return complete;
    }
}
