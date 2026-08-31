package dev.gustavopere.volcanoes.geology;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/** Strict parser that turns merged datapack JSON resources into one immutable registry snapshot. */
public final class RockProfileDataLoader {
    private RockProfileDataLoader() {
    }

    public static RockProfileRegistry load(Map<ResourceLocation, JsonElement> definitions) {
        Objects.requireNonNull(definitions, "definitions");

        RockProfileRegistry.Builder builder = RockProfileRegistry.builder()
                .profile(RockProfile.GENERIC_STONE);

        definitions.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getKey().toString()))
                .forEach(entry -> addDefinition(builder, entry.getKey(), entry.getValue()));

        return builder.build();
    }

    private static void addDefinition(
            RockProfileRegistry.Builder builder,
            ResourceLocation definitionId,
            JsonElement element
    ) {
        if (element == null || !element.isJsonObject()) {
            throw invalid(definitionId, "definition must be a JSON object");
        }

        JsonObject json = element.getAsJsonObject();
        RockCategory category;
        try {
            category = RockCategory.valueOf(requiredString(json, "category", definitionId).toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw invalid(definitionId, "unknown rock category", exception);
        }

        RockProfile profile = new RockProfile(
                definitionId.toString(),
                category,
                requiredDouble(json, "hardness", definitionId),
                requiredDouble(json, "permeability", definitionId),
                requiredDouble(json, "thermal_conductivity", definitionId),
                requiredDouble(json, "lava_flow_multiplier", definitionId),
                requiredDouble(json, "erosion_resistance", definitionId),
                requiredDouble(json, "hydrothermal_reactivity", definitionId));

        builder.profile(profile);
        for (ResourceLocation block : resourceList(json, "blocks", definitionId)) {
            builder.bindBlock(block, profile.id());
        }
        for (ResourceLocation tag : resourceList(json, "tags", definitionId)) {
            builder.bindTag(tag, profile.id());
        }
    }

    private static String requiredString(JsonObject json, String field, ResourceLocation definitionId) {
        JsonElement element = json.get(field);
        if (element == null || !element.isJsonPrimitive()) {
            throw invalid(definitionId, "missing or invalid string field '" + field + "'");
        }
        try {
            String value = element.getAsString();
            if (value.isBlank()) {
                throw invalid(definitionId, "field '" + field + "' must not be blank");
            }
            return value;
        } catch (RuntimeException exception) {
            throw invalid(definitionId, "invalid string field '" + field + "'", exception);
        }
    }

    private static double requiredDouble(JsonObject json, String field, ResourceLocation definitionId) {
        JsonElement element = json.get(field);
        if (element == null || !element.isJsonPrimitive()) {
            throw invalid(definitionId, "missing or invalid numeric field '" + field + "'");
        }
        try {
            return element.getAsDouble();
        } catch (RuntimeException exception) {
            throw invalid(definitionId, "invalid numeric field '" + field + "'", exception);
        }
    }

    private static List<ResourceLocation> resourceList(
            JsonObject json,
            String field,
            ResourceLocation definitionId
    ) {
        JsonElement element = json.get(field);
        if (element == null) {
            return List.of();
        }
        if (!element.isJsonArray()) {
            throw invalid(definitionId, "field '" + field + "' must be an array");
        }

        JsonArray array = element.getAsJsonArray();
        List<ResourceLocation> values = new ArrayList<>(array.size());
        for (JsonElement item : array) {
            if (!item.isJsonPrimitive()) {
                throw invalid(definitionId, "field '" + field + "' must contain resource-location strings");
            }
            String raw = item.getAsString();
            try {
                values.add(ResourceLocation.parse(raw));
            } catch (RuntimeException exception) {
                throw invalid(definitionId, "invalid resource location '" + raw + "' in field '" + field + "'", exception);
            }
        }
        return List.copyOf(values);
    }

    private static IllegalArgumentException invalid(ResourceLocation id, String message) {
        return new IllegalArgumentException("Invalid rock profile " + id + ": " + message);
    }

    private static IllegalArgumentException invalid(ResourceLocation id, String message, Throwable cause) {
        return new IllegalArgumentException("Invalid rock profile " + id + ": " + message, cause);
    }
}
